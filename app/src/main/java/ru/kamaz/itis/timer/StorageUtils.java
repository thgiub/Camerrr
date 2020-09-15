package ru.kamaz.itis.timer;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.ContextCompat;


import java.util.Locale;

class StorageUtils {
    private final Context context;
    private static final String TAG = "StorageUtils";
    private final MyApplicationInterface applicationInterface;
    private Uri last_media_scanned;

    StorageUtils(Context context, MyApplicationInterface applicationInterface) {
        this.context = context;
        this.applicationInterface = applicationInterface;
    }
    static class Media {
        final long id;
        final boolean video;
        final Uri uri;
        final long date;
        final int orientation;
        final String path;

        Media(long id, boolean video, Uri uri, long date, int orientation, String path) {
            this.id = id;
            this.video = video;
            this.uri = uri;
            this.date = date;
            this.orientation = orientation;
            this.path = path;
        }
    }
    void clearLastMediaScanned() {
        last_media_scanned = null;
    }
    private Media getLatestMedia(boolean video) {
        if( MyDebug.LOG )
            Log.d(TAG, "getLatestMedia: " + (video ? "video" : "images"));
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            // needed for Android 6, in case users deny storage permission, otherwise we get java.lang.SecurityException from ContentResolver.query()
            // see https://developer.android.com/training/permissions/requesting.html
            // we now request storage permission before opening the camera, but keep this here just in case
            // we restrict check to Android 6 or later just in case, see note in LocationSupplier.setupLocationListener()
            if( MyDebug.LOG )
                Log.e(TAG, "don't have READ_EXTERNAL_STORAGE permission");
            return null;
        }
        Media media = null;
        Uri baseUri = video ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        final int column_id_c = 0;
        final int column_date_taken_c = 1;
        final int column_data_c = 2; // full path and filename, including extension
        final int column_orientation_c = 3; // for images only
        String [] projection = video ? new String[] {MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns.DATA} : new String[] {MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
        // for images, we need to search for JPEG/etc and RAW, to support RAW only mode (even if we're not currently in that mode, it may be that previously the user did take photos in RAW only mode)
        String selection = video ? "" : MediaStore.Images.ImageColumns.MIME_TYPE + "='image/jpeg' OR " +
                MediaStore.Images.ImageColumns.MIME_TYPE + "='image/webp' OR " +
                MediaStore.Images.ImageColumns.MIME_TYPE + "='image/png' OR " +
                MediaStore.Images.ImageColumns.MIME_TYPE + "='image/x-adobe-dng'";
        String order = video ? MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC," + MediaStore.Video.VideoColumns._ID + " DESC" : MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC," + MediaStore.Images.ImageColumns._ID + " DESC";
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(baseUri, projection, selection, null, order);
            if( cursor != null && cursor.moveToFirst() ) {
                if( MyDebug.LOG )
                    Log.d(TAG, "found: " + cursor.getCount());
                // now sorted in order of date - scan to most recent one in the Open Camera save folder
                boolean found = false;
                //File save_folder = getImageFolder(); // may be null if using SAF
               // String save_folder_string = save_folder == null ? null : save_folder.getAbsolutePath() + File.separator;
                if( MyDebug.LOG )
                   // Log.d(TAG, "save_folder_string: " + save_folder_string);
                do {

                }
                while( cursor.moveToNext() );
                if( found ) {
                    // make sure we prefer JPEG/etc (non RAW) if there's a JPEG/etc version of this image
                    // this is because we want to support RAW only and JPEG+RAW modes
                    String path = cursor.getString(column_data_c);
                    if( MyDebug.LOG )
                        Log.d(TAG, "path: " + path);
                    // path may be null on Android 4.4, see above!
                    if( path != null && path.toLowerCase(Locale.US).endsWith(".dng") ) {
                        if( MyDebug.LOG )
                            Log.d(TAG, "try to find a non-RAW version of the DNG");
                        int dng_pos = cursor.getPosition();
                        boolean found_non_raw = false;
                        String path_without_ext = path.toLowerCase(Locale.US);
                        if( path_without_ext.indexOf(".") > 0 )
                            path_without_ext = path_without_ext.substring(0, path_without_ext.lastIndexOf("."));
                        if( MyDebug.LOG )
                            Log.d(TAG, "path_without_ext: " + path_without_ext);
                        while( cursor.moveToNext() ) {
                            String next_path = cursor.getString(column_data_c);
                            if( MyDebug.LOG )
                                Log.d(TAG, "next_path: " + next_path);
                            if( next_path == null )
                                break;
                            String next_path_without_ext = next_path.toLowerCase(Locale.US);
                            if( next_path_without_ext.indexOf(".") > 0 )
                                next_path_without_ext = next_path_without_ext.substring(0, next_path_without_ext.lastIndexOf("."));
                            if( MyDebug.LOG )
                                Log.d(TAG, "next_path_without_ext: " + next_path_without_ext);
                            if( !path_without_ext.equals(next_path_without_ext) )
                                break;
                            // so we've found another file with matching filename - is it a JPEG/etc?
                            if( next_path.toLowerCase(Locale.US).endsWith(".jpg") ) {
                                if( MyDebug.LOG )
                                    Log.d(TAG, "found equivalent jpeg");
                                found_non_raw = true;
                                break;
                            }
                            else if( next_path.toLowerCase(Locale.US).endsWith(".webp") ) {
                                if( MyDebug.LOG )
                                    Log.d(TAG, "found equivalent webp");
                                found_non_raw = true;
                                break;
                            }
                            else if( next_path.toLowerCase(Locale.US).endsWith(".png") ) {
                                if( MyDebug.LOG )
                                    Log.d(TAG, "found equivalent png");
                                found_non_raw = true;
                                break;
                            }
                        }
                        if( !found_non_raw ) {
                            if( MyDebug.LOG )
                                Log.d(TAG, "can't find equivalent jpeg/etc");
                            cursor.moveToPosition(dng_pos);
                        }
                    }
                }
                if( !found ) {
                    if( MyDebug.LOG )
                        Log.d(TAG, "can't find suitable in Open Camera folder, so just go with most recent");
                    cursor.moveToFirst();
                }
                long id = cursor.getLong(column_id_c);
                long date = cursor.getLong(column_date_taken_c);
                int orientation = video ? 0 : cursor.getInt(column_orientation_c);
                Uri uri = ContentUris.withAppendedId(baseUri, id);
                String path = cursor.getString(column_data_c);
                if( MyDebug.LOG )
                    Log.d(TAG, "found most recent uri for " + (video ? "video" : "images") + ": " + uri);
                media = new Media(id, video, uri, date, orientation, path);
            }
        }
        catch(Exception e) {
            // have had exceptions such as SQLiteException, NullPointerException reported on Google Play from within getContentResolver().query() call
            if( MyDebug.LOG )
                Log.e(TAG, "Exception trying to find latest media");
            e.printStackTrace();
        }
        finally {
            if( cursor != null ) {
                cursor.close();
            }
        }
        return media;
    }

    Media getLatestMedia() {
        Media image_media = getLatestMedia(false);
        Media video_media = getLatestMedia(true);
        Media media = null;
        if( image_media != null && video_media == null ) {
            if( MyDebug.LOG )
                Log.d(TAG, "only found images");
            media = image_media;
        }
        else if( image_media == null && video_media != null ) {
            if( MyDebug.LOG )
                Log.d(TAG, "only found videos");
            media = video_media;
        }
        else if( image_media != null && video_media != null ) {
            if( MyDebug.LOG ) {
                Log.d(TAG, "found images and videos");
                Log.d(TAG, "latest image date: " + image_media.date);
                Log.d(TAG, "latest video date: " + video_media.date);
            }
            if( image_media.date >= video_media.date ) {
                if( MyDebug.LOG )
                    Log.d(TAG, "latest image is newer");
                media = image_media;
            }
            else {
                if( MyDebug.LOG )
                    Log.d(TAG, "latest video is newer");
                media = video_media;
            }
        }
        if( MyDebug.LOG )
            Log.d(TAG, "return latest media: " + media);
        return media;
    }

}
