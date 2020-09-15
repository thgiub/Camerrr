package ru.kamaz.itis.timer.gallery.ui.ui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;






import java.io.File;
import java.io.IOException;
import java.util.List;

import ru.kamaz.itis.timer.gallery.HelperUtils;
import ru.kamaz.itis.timer.gallery.domain.interfaces.video.VideoInterface;

public class VideoDeleteUtils implements Runnable{
    List<String> selectedItems;
    VideoInterface.Presenter presenter;
    Context context;

    public VideoDeleteUtils(List<String> selectedItems, VideoInterface.Presenter presenter, Context context) {
        this.selectedItems = selectedItems;
        this.presenter = presenter;
        this.context = context;
    }
    @Override
    public void run() {
        for (String filePath : selectedItems){
            HelperUtils.deleteFileVideo(filePath, context);
          /*  try {
                FileUtils.forceDelete(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                presenter.onFilesDeleted();
            }
        });

    }
}
