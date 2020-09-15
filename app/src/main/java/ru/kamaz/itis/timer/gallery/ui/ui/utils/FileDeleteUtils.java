package ru.kamaz.itis.timer.gallery.ui.ui.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;





import java.util.List;

import ru.kamaz.itis.timer.gallery.HelperUtils;
import ru.kamaz.itis.timer.gallery.domain.interfaces.photo.MediaPhotoInterface;


public class FileDeleteUtils implements Runnable {
    List<String> filesList;
    MediaPhotoInterface.Presenter presenter;
    Context context;

    public FileDeleteUtils(List<String> filesList, MediaPhotoInterface.Presenter presenter, Context context) {
        this.filesList = filesList;
        this.presenter = presenter;
        this.context = context;
    }


    @Override
    public void run() {
        for (String filePath : filesList)
            HelperUtils.deleteFile(filePath, context);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                presenter.onFilesDeleted();
            }
        });
    }
}
