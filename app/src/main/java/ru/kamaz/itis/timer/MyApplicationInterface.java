package ru.kamaz.itis.timer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

class MyApplicationInterface {
    private final StorageUtils storageUtils;
    private final DrawPreview drawPreview;
    private final SharedPreferences sharedPreferences;
    private boolean has_set_cameraId;
    private final static String nr_mode_default = "preference_nr_mode_normal";
    private final static int cameraId_default = 0;
    private int cameraId = cameraId_default;
    private String nr_mode = nr_mode_default;

    MyApplicationInterface(MainActivity main_activity, Bundle savedInstanceState) {
        this.drawPreview = new DrawPreview(main_activity, this);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(main_activity);
        this.storageUtils = new StorageUtils(main_activity, this);
        if( savedInstanceState != null ) {
            // load the things we saved in onSaveInstanceState().
            if( MyDebug.LOG )
                Log.d(TAG, "read from savedInstanceState");
            has_set_cameraId = true;
            cameraId = savedInstanceState.getInt("cameraId", cameraId_default);
            if( MyDebug.LOG )
                Log.d(TAG, "found cameraId: " + cameraId);
            nr_mode = savedInstanceState.getString("nr_mode", nr_mode_default);
            if( MyDebug.LOG )
                Log.d(TAG, "found nr_mode: " + nr_mode);
        }
    }

    StorageUtils getStorageUtils() {
        return storageUtils;
    }
    public DrawPreview getDrawPreview() {
        return drawPreview;
    }
    public boolean getThumbnailAnimationPref() {
        return sharedPreferences.getBoolean(PreferenceKeys.ThumbnailAnimationPreferenceKey, true);
    }
}
