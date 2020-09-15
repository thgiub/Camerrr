package ru.kamaz.itis.timer.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings.Global;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ru.kamaz.itis.timer.BuildConfig;
import ru.kamaz.itis.timer.gallery.constants.ConstantUtils;
import ru.kamaz.itis.timer.gallery.domain.AppContainer;
import ru.kamaz.itis.timer.gallery.domain.AppData;
import ru.kamaz.itis.timer.gallery.domain.EnumFileType;
import ru.kamaz.itis.timer.gallery.domain.GalleryPhotoAlbum;
import ru.kamaz.itis.timer.gallery.domain.domain.Media;
import ru.kamaz.itis.timer.gallery.domain.Photo;


public class HelperUtils {

  private static HelperUtils instance;

  public static HelperUtils getInstance() {
    if (instance == null) {
      instance = new HelperUtils();
    }
    return instance;
  }

  public static String getPreviewRandomImage() {
    int nextInt = new Random().nextInt(3);
    String[] strArr = new String[]{"radio_prev_1", "radio_prev_2", "radio_prev_3"};
    Log.d(ConstantUtils.LOG_TAG, "getPreviewRandomImage ".concat(strArr[nextInt]));
    return strArr[nextInt];
  }

  public static String secondToPlayCurrentPosition(long second) {
    long minutes = TimeUnit.SECONDS.toMinutes(second);
    second -= TimeUnit.MINUTES.toSeconds(minutes);
    long seconds = TimeUnit.SECONDS.toSeconds(second);
    String responce = "";
    responce = String.format("%02d", minutes);
    responce = responce + ":" + String.format("%02d", seconds);
    return responce;
  }

  public static String frequentToString(String freq) {
    if (freq.length() > 3) {
      String f = String
          .valueOf(Integer.valueOf(freq.substring(freq.length() - 2, freq.length() - 1)));
      String f2 = String.valueOf(Integer.valueOf(freq.substring(0, freq.length() - 2)));
      return f2 + "." + f;
    }
    return "";
  }

  public static Uri getUri(Context context, String imagePath) {
    if (imagePath == null)
      return null;
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return FileProvider
            .getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(imagePath));
      } else {
        return Uri.fromFile(new File(imagePath));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void showSystemUI(Activity activity) {
    View decorView = activity.getWindow().getDecorView();
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }

  public static void hideSystemUI(Activity activity) {
    View decorView = activity.getWindow().getDecorView();
    decorView.setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  public static void setTranslucentStatus(boolean on, Activity activity) {
    Window win = activity.getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  public static String floatToFormatString(float currentValue, String command) {
    if (command == null) command = "#.#";
    DecimalFormat df = new DecimalFormat(command);

    return df.format(currentValue);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  public boolean isAutomaticTimeZone(Context context) {
    return Global.getInt(context.getContentResolver(), Global.AUTO_TIME_ZONE, 0) == 1;
  }

  private static List getUserInstalledApplications(Context context) {
    List<ApplicationInfo> installedApplications = context.getPackageManager()
        .getInstalledApplications(PackageManager.GET_META_DATA);
    Iterator it = installedApplications.iterator();
    while (it.hasNext()) {
      ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
      if ((applicationInfo.flags & 1) != 0 ||
          applicationInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
        it.remove();
      }
    }
    return installedApplications;
  }

  public static void changeGMT(Context context, String data) {
    int parseInt = Integer.parseInt(data.split("\\+")[1].split(":")[0]);
    @SuppressLint("WrongConstant") AlarmManager alarmManager = (AlarmManager) context
        .getSystemService(NotificationCompat.CATEGORY_ALARM);
    String stringBuilder = "Etc/GMT-" + parseInt;
    alarmManager.setTimeZone(stringBuilder);
  }

  /*public static @Nullable
  List<Audio> getMusicList(Context context, @Nullable String selection) {
    List<Audio> musicList = null;
    try {
      ContentResolver contentResolver = context.getContentResolver();
      Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
      Cursor songCursor = contentResolver
          .query(songUri, null, selection, null, MediaStore.Audio.Media.TITLE);

      if (songCursor != null && songCursor.moveToFirst()) {
        musicList = new ArrayList();
        int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int songDisplayName = songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int songData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

        do {
          if (Looper.myLooper() != Looper.getMainLooper() &&
              Thread.currentThread().isInterrupted()) {
            Thread.currentThread().interrupt();
            return null;
          }
          long currentId = songCursor.getLong(songId);
          String currentTitle = songCursor.getString(songTitle);
          String displayName = songCursor.getString(songDisplayName);
          Double duration = songCursor.getDouble(songDuration);
          String path = songCursor.getString(songData);
          String artist = songCursor.getString(songArtist);
          String albumId = songCursor.getString(songAlbum);

          String albumCoverPath = null;
          Cursor cursor = context.getContentResolver()
              .query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                  new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                  MediaStore.Audio.Albums._ID + "=?", new String[]{String.valueOf(albumId)}, null);

          if (cursor != null && cursor.moveToFirst()) {
            albumCoverPath = cursor
                .getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            cursor.close();
          }
          musicList.add(new Audio(currentId, currentTitle, displayName, duration, path, artist,
              albumCoverPath));
        } while (songCursor.moveToNext());

        Log.d(ConstantUtils.LOG_TAG, "songCursor size = " + musicList.size());
      } else {
        Log.d(ConstantUtils.LOG_TAG, "songCursor size = 0 or null");
      }
      if (songCursor != null) {
        songCursor.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return musicList;
  }*/

  public static int getMusicCount(Context context, @Nullable String selection) {
    int count = 0;
    try {
      ContentResolver contentResolver = context.getContentResolver();
      Cursor countCursor = contentResolver
          .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"count(*) AS count"},
              selection, null, null);
      if (countCursor != null && countCursor.moveToFirst()) {
        count = countCursor.getCount();
      }
      if (countCursor != null) {
        countCursor.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return count;
  }

  /*@Nullable
  public static List<AudioAlbum> getAlbums(Context context) {
    List<AudioAlbum> dataList = null;
    ContentResolver contentResolver = context.getContentResolver();
    Uri songUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    Cursor cursor = contentResolver.query(songUri, null, null, null, null);
    if (cursor != null && cursor.moveToFirst()) {
      dataList = new ArrayList();
      do {
        AudioAlbum audioAlbum = new AudioAlbum();
        audioAlbum.setAlbumId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
        audioAlbum
            .setAlbumTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
        audioAlbum.setAlbumArtist(
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
        audioAlbum.setSoundCount(
            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));

        dataList.add(audioAlbum);
      } while (cursor.moveToNext());

      Log.d(ConstantUtils.LOG_TAG, "getAlbums size = " + dataList.size());
    } else {
      Log.d(ConstantUtils.LOG_TAG, "getAlbums size = 0 or null");
    }
    if (cursor != null) {
      cursor.close();
    }
    return dataList;
  }

  @Nullable
  public static List<AudioArtist> getArtists(Context context) {
    List<AudioArtist> dataList = null;
    ContentResolver contentResolver = context.getContentResolver();
    Uri songUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    Cursor cursor = contentResolver
        .query(songUri, null, null, null, MediaStore.Audio.Artists.ARTIST);
    if (cursor != null && cursor.moveToFirst()) {
      dataList = new ArrayList();
      do {
        AudioArtist data = new AudioArtist();
        data.setArtistId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
        data.setArtistTitle(
            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
        data.setArtistAlbumsCount(
            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS)));
        data.setArtistSoundsCount(
            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));

        dataList.add(data);
      } while (cursor.moveToNext());

      Log.d(ConstantUtils.LOG_TAG, "getArtists size = " + dataList.size());
    } else {
      Log.d(ConstantUtils.LOG_TAG, "getArtists size = 0 or null");
    }
    if (cursor != null) {
      cursor.close();
    }
    return dataList;
  }*/

  public static String getMacAddress(){
    try{
      List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());

      for(NetworkInterface networkInterface : all){
        if(!networkInterface.getName().equalsIgnoreCase("wlan0")) continue;

        byte[] macBytes = networkInterface.getHardwareAddress();
        if(macBytes == null)
          return "";

        StringBuilder version = new StringBuilder();
        for(byte b : macBytes){
          String hex = Integer.toHexString(b & 0xFF);
          if(hex.length() == 1)
            hex = "0".concat(hex);
          version.append(hex.concat(":"));
        }

        if(version.length() > 0)
          version.deleteCharAt(version.length() - 1);

        return version.toString();
      }
    } catch (Exception e) {
      Log.d(ConstantUtils.LOG_TAG, e.getMessage());
      e.printStackTrace();
    }

    return "";
  }


  @Nullable public static List<Photo> getPhotoList (Context context, @Nullable Long _bucketId)
  {
    List<Photo> list = null;
    ContentResolver contentResolver = context.getContentResolver();
    Uri songUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    Cursor cursor = null;
    if (_bucketId != null && _bucketId != 0) {
      String selectionArg = MediaStore.Images.Media.BUCKET_ID + "=" + _bucketId;
      cursor = contentResolver.query(songUri, null, selectionArg, null, null);
    } else {
      cursor = contentResolver.query(songUri, null, null, null, null);
    }

    if (cursor != null && cursor.moveToFirst()) {
      list = new ArrayList();
      int songId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
      int songTitle = cursor.getColumnIndex(MediaStore.Images.Media.TITLE);
      int songDisplayName = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
      int songData = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
      int songBucketId = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);

      do {
        long currentId = cursor.getLong(songId);
        String currentTitle = cursor.getString(songTitle);
        String displayName = cursor.getString(songDisplayName);
        String path = cursor.getString(songData);
        long bucketId = cursor.getLong(songBucketId);
        if (path != null && path.length() > 0) {
          list.add(new Photo(currentTitle, path, bucketId, currentId));
        }
      } while (cursor.moveToNext());

      Log.d(ConstantUtils.LOG_TAG, "getPhotoList size = " + list.size());
    } else {
      Log.d(ConstantUtils.LOG_TAG, "getPhotoList size = 0 or null");
    }
    if (cursor != null) {
      cursor.close();
    }

    if(list!=null){
      Collections.reverse(list);
    }

    return list;
  }

  public static void deleteFile(String filePath, Context context) {
    // Set up the projection (we only need the ID)
    String[] projection = { MediaStore.Images.Media._ID };

// Match on the file path
    String selection = MediaStore.Images.Media.DATA + " = ?";
    File file  = new File(filePath);
    String[] selectionArgs = new String[] { file.getAbsolutePath() };

// Query for the ID of the media matching the file path
    Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    ContentResolver contentResolver = context.getContentResolver();
    Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
    if (c.moveToFirst()) {
      // We found the ID. Deleting the item via the content provider will also remove the file
      long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
      Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
      contentResolver.delete(deleteUri, null, null);
    } else {
      Log.d("ss", "ss");
      int a=5;
    }
    c.close();
  }
  public static void deleteFileVideo(String filePath, Context context) {
    // Set up the projection (we only need the ID)
    String[] projection = { MediaStore.Video.Media._ID };

// Match on the file path
    String selection = MediaStore.Video.Media.DATA + " = ?";
    File file  = new File(filePath);
    String[] selectionArgs = new String[] { file.getAbsolutePath() };

// Query for the ID of the media matching the file path
    Uri queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    ContentResolver contentResolver = context.getContentResolver();
    Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
    if (c.moveToFirst()) {
      // We found the ID. Deleting the item via the content provider will also remove the file
      long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
      Uri deleteUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
      contentResolver.delete(deleteUri, null, null);
    } else {
      Log.d("ss", "ss");
      int a=5;
    }
    c.close();
  }
  public static List<String> getPhotoListPaths(Context context, @Nullable Long _bucketId) {
    List<Photo> photoList = getPhotoList(context, _bucketId);
    List<String> pathsList = new ArrayList<>();

    if (photoList == null)
      return pathsList;

    for (Photo photo : photoList)
      pathsList.add(photo.getImagePath());
Collections.reverse(photoList);
    return pathsList;
  }

  public static List<String> getVideoListPaths(Context context, @Nullable String path) {
    List<Media> mediaList = getVideoList(context, path);
    List<String> pathsList = new ArrayList<>();

    if (mediaList == null)
      return pathsList;

    for (Media media : mediaList)
      pathsList.add(media.path);

    return pathsList;
  }

  public static @Nullable List<Photo> getPhotoListFromDirectory (Context context, String
      directory){
    List<Photo> list = null;
    ContentResolver contentResolver = context.getContentResolver();
    Uri songUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    Cursor cursor = null;
    String selectionArg = MediaStore.Images.Media.DATA + " like '" + directory + "%'";
    cursor = contentResolver.query(songUri, null, selectionArg, null, null);

    if (cursor != null && cursor.moveToFirst()) {
      list = new ArrayList();
      int songId = cursor.getColumnIndex(MediaStore.Images.Media._ID);
      int songTitle = cursor.getColumnIndex(MediaStore.Images.Media.TITLE);
      int songDisplayName = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
      int songData = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
      int songBucketId = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);

      do {
        long currentId = cursor.getLong(songId);
        String currentTitle = cursor.getString(songTitle);
        String displayName = cursor.getString(songDisplayName);
        String path = cursor.getString(songData);
        long bucketId = cursor.getLong(songBucketId);
        if (path != null && path.length() > 0) {
          list.add(new Photo(currentTitle, path, bucketId, currentId));
        }
      } while (cursor.moveToNext());

      Log.d(ConstantUtils.LOG_TAG, "getPhotoList size = " + list.size());
    } else {
      Log.d(ConstantUtils.LOG_TAG, "getPhotoList size = 0 or null");
    }
    if (cursor != null) {
      cursor.close();
    }
    return list;
  }

  public static List<GalleryPhotoAlbum> getPhotosAlbumsList (Context context){
    String[] PROJECTION_BUCKET = {MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATA};
    String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
    String BUCKET_ORDER_BY = "MAX(datetaken) DESC";
    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    Cursor cur = context.getContentResolver()
        .query(images, PROJECTION_BUCKET, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);
    if (cur != null && cur.moveToFirst()) {
      List<GalleryPhotoAlbum> arrayListAlbums = new ArrayList<>();
      String bucket;
      String date;
      String data;
      long bucketId;
      int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
      int dateColumn = cur.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
      int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);
      int bucketIdColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
      do {
        // Get the field values
        bucket = cur.getString(bucketColumn);
        date = cur.getString(dateColumn);
        data = cur.getString(dataColumn);
        bucketId = cur.getInt(bucketIdColumn);

        if (bucket != null && bucket.length() > 0) {
          GalleryPhotoAlbum album = new GalleryPhotoAlbum();
          album.setBucketId(bucketId);
          album.setBucketName(bucket);
          album.setTakenDate(date);
          album.setData(data);
          album.setPhotoCountByAlbum(photoCountByAlbum(context, bucketId));
          arrayListAlbums.add(album);
          // Do something with the values.
          Log.v("ListingImages",
              " bucket=" + bucket + "  date_taken=" + date + "  _data=" + data +
                  " bucket_id=" + bucketId);

        }
      } while (cur.moveToNext());

      if (cur != null) {
        cur.close();
      }
      return arrayListAlbums;
    }
    if (cur != null) {
      cur.close();
    }
    return null;
  }

  private static int photoCountByAlbum (Context context,long bucketId){
    try {
      final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
      String searchParams = null;
      searchParams = MediaStore.Images.Media.BUCKET_ID + "=" + String.valueOf(bucketId);
      // final String[] columns = { MediaStore.Images.Media.DATA,
      // MediaStore.Images.Media._ID };
      Cursor mPhotoCursor = context.getContentResolver()
          .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, searchParams, null,
              orderBy + " DESC");
      if (mPhotoCursor.getCount() > 0) {
        return mPhotoCursor.getCount();
      }
      if (mPhotoCursor != null) {
        mPhotoCursor.close();
      }
      mPhotoCursor.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static  @Nullable
  List<Media> getVideoList(Context context, @Nullable String path) {
    List<Media> list = null;
    ContentResolver contentResolver = context.getContentResolver();
    Uri curUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    String selection = path != null ? MediaStore.Video.Media.DATA + "='" + path + "'": null;
    Cursor curCursor = contentResolver.query(curUri, null, selection, null, null);

    if(curCursor != null && curCursor.moveToFirst())
    {
      list = new ArrayList();
      do {
        long id = curCursor.getLong(curCursor.getColumnIndex(MediaStore.Video.Media._ID));
        String title = curCursor.getString(curCursor.getColumnIndex(MediaStore.Video.Media.TITLE));
        String displayName = curCursor.getString(curCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        Double duration = curCursor.getDouble(curCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        String dataPath = curCursor.getString(curCursor.getColumnIndex(MediaStore.Video.Media.DATA));
        String artist = curCursor.getString(curCursor.getColumnIndex(MediaStore.Video.Media.ARTIST));

        list.add(new Media(id, title, displayName, duration, dataPath, artist, EnumFileType.VIDEO));
      } while(curCursor.moveToNext());

      Log.d(ConstantUtils.LOG_TAG, "curCursor size = " + list.size());
    } else {
      Log.d(ConstantUtils.LOG_TAG, "curCursor size = 0 or null");
    }
    if(list!=null){
      Collections.reverse(list);
    }
    return list;
  }

  public static void showToast (Context context, String msg){
    Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    View view = toast.getView();
    view.setPadding(20, 15, 10, 15);
    //view.setBackground(ContextCompat.getDrawable(context, R.drawable.toast_border_radius));

    toast.show();
  }

  public static void setClipboard (Context context, String text){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      ClipboardManager clipboardManager = (ClipboardManager) context
          .getSystemService(Context.CLIPBOARD_SERVICE);
      clipboardManager.setText(text);
    } else {
      ClipboardManager clipboardManager = (ClipboardManager) context
          .getSystemService(Context.CLIPBOARD_SERVICE);
      ClipData clipData = ClipData.newPlainText("Copied Text", text);
      clipboardManager.setPrimaryClip(clipData);
    }

    //showToast(context, context.getString(R.string.copiedToClipboard));
  }

  public static String getMimeType(Context context, Uri uri) {
    String mimeType = null;
    if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
      ContentResolver cr = context.getContentResolver();
      mimeType = cr.getType(uri);
    } else {
      String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
          .toString());
      mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
          fileExtension.toLowerCase());
    }
    if (mimeType == null) mimeType = URLConnection.guessContentTypeFromName(uri.getPath());
    return mimeType;
  }

  public static List<AppContainer> getAppContainer(Context context){

    List<ApplicationInfo> aList;
    List<AppContainer> appContainers;
    appContainers = new ArrayList<>();
    aList = getUserInstalledApplications(context);

    List<AppData> appdataList1 = new ArrayList<>();
    for (int y = 0; y < aList.size(); y++) {
      appdataList1.add(new AppData(0, aList.get(y).packageName, aList.get(y)));
    }

    AppContainer container1 = new AppContainer();
    container1.id = 0;
    container1.left = 1;
    container1.top = 0;
    container1.title = "Приложения";
    container1.pageNumber = 0;
    container1.appData = appdataList1;
    appContainers.add(container1);
    return appContainers;
  }

  public static Date stringToDate(String sDate, String sFormat) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat);
    try {
      Date date = simpleDateFormat.parse(sDate);
      return date;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /*public static ArrayList<AlertTimeItem> getAlertTimeItems(Context context) {
    ArrayList<AlertTimeItem> alertItems = new ArrayList<>();
    alertItems.add(new AlertTimeItem(1, context.getString(R.string.calendarNo), null));
    alertItems.add(new AlertTimeItem(2, context.getString(R.string.calendarOnSetTime), 0));
    alertItems.add(new AlertTimeItem(3, context.getString(R.string.in5Min), -300));
    alertItems.add(new AlertTimeItem(4, context.getString(R.string.in15Min), -900));
    alertItems.add(new AlertTimeItem(5, context.getString(R.string.in30Min), -1800));
    alertItems.add(new AlertTimeItem(6, context.getString(R.string.in1Hour), -3600));
    alertItems.add(new AlertTimeItem(7, context.getString(R.string.in2Hour), -7200));
    alertItems.add(new AlertTimeItem(8, context.getString(R.string.in1Day), -86400));
    alertItems.add(new AlertTimeItem(9, context.getString(R.string.in2Day), -172800));
    alertItems.add(new AlertTimeItem(10, context.getString(R.string.in1Week), -604800));
    return alertItems;
  }*/
}

