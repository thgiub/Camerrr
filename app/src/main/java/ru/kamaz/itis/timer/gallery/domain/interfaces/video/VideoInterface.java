package ru.kamaz.itis.timer.gallery.domain.interfaces.video;

import java.io.IOException;

public interface VideoInterface {
    interface View{
        void setListeners();
        void initVars();
        void initContent();
        void updateVideoList();
        void updateGalleryList();
        void deleteSelectedItem()throws IOException;
        void onVideoCheckedChanged(int itemID, boolean isChecked);
        void showToast(String message);
        void onFileDeleted(String filePath);
        void shareSelectedItems();
    }
    interface Presenter{
        void setView(View view);
        void init();
        void onVideoLongClicked(int itemID);
        boolean isEditMode();
        void onResume();
        void onPause();
        void enableEditMode(boolean enable);
        void onVideoCheckedChanged(int itemID, boolean isChecked);
        void removeListener();
        void addListener();
        void onFileSuccessDeleted(String fileName);
        void onFilesDeleted();
        void onFileDeleteFailed(String fileName);
    }
}
