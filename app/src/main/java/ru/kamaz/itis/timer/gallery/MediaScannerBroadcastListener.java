package ru.kamaz.itis.timer.gallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class MediaScannerBroadcast extends BroadcastReceiver {

    private final MediaScannerBroadcast.MediaScannerBroadcastListener listener;

    public MediaScannerBroadcast(MediaScannerBroadcast.MediaScannerBroadcastListener listener) {
        this.listener = listener;
    }

    public interface MediaScannerBroadcastListener {
        void mediaScannerBroadcastCallback();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
            listener.mediaScannerBroadcastCallback();
        }
    }
}