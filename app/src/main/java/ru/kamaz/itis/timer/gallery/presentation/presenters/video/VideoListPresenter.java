package ru.kamaz.itis.timer.gallery.presentation.presenters.video;



import java.io.IOException;

import ru.kamaz.itis.timer.gallery.domain.interactors.MainInteractor;
import ru.kamaz.itis.timer.gallery.domain.interfaces.interactors.MainInteractorInterface;
import ru.kamaz.itis.timer.gallery.domain.interfaces.video.VideoInterface;

public class VideoListPresenter implements VideoInterface.Presenter,  MainInteractorInterface.MediaListener{
    private MainInteractor interactor;
    private VideoInterface.View view;
    private boolean isEditMode = false;

    @Override
    public void setView(VideoInterface.View view) {
       this.view=view;
    }

    @Override
    public void init() {
        interactor= MainInteractor.getInstance();
        view.initContent();
        view.initVars();
        view.setListeners();
    }

    @Override
    public void onVideoLongClicked(int itemID) {
        interactor.setEditMode(true);
    }

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public void onResume() {
        interactor.addListener(this);
    }

    @Override
    public void onPause() {
        interactor.removeListener(this);

    }

    @Override
    public void enableEditMode(boolean enable) {
        interactor.setEditMode(enable);
    }

    @Override
    public void onVideoCheckedChanged(int itemID, boolean isChecked) {
        view.onVideoCheckedChanged(itemID,isChecked);
    }

    @Override
    public void removeListener() {
        if(true)
        interactor.removeListener(this);
    }

    @Override
    public void addListener() {
        interactor.addListener(this);
    }

    @Override
    public void onFileSuccessDeleted(String fileName) {

    }

    @Override
    public void onFilesDeleted() {
     view.updateGalleryList();
    }

    @Override
    public void onFileDeleteFailed(String fileName) {
        view.showToast("Error deleting files: " + fileName);
    }

    @Override
    public void setEditMode(boolean enable) {
        isEditMode = enable;
        view.updateVideoList();
    }

    @Override
    public void shareSelectedItems() {
        view.shareSelectedItems();

    }

    @Override
    public void deleteSelectedItems() {
        try{
            view.deleteSelectedItem();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    @Override
    public void removeListener(boolean bool) {
        if (false){
            interactor.removeListener(this);
        }
    }

    @Override
    public void addListener(boolean bool) {
        if(true){
           interactor.addListener(this);
        }
    }
}
