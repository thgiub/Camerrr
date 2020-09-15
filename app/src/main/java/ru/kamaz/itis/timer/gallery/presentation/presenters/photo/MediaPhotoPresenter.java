package ru.kamaz.itis.timer.gallery.presentation.presenters.photo;



import java.io.IOException;
import java.util.List;

import ru.kamaz.itis.timer.gallery.domain.interactors.MainInteractor;
import ru.kamaz.itis.timer.gallery.domain.interfaces.interactors.MainInteractorInterface;
import ru.kamaz.itis.timer.gallery.domain.interfaces.photo.MediaPhotoInterface;


public class MediaPhotoPresenter implements MediaPhotoInterface.Presenter, MainInteractorInterface.MediaListener{
    private MediaPhotoInterface.View view;
    private MainInteractor interactor;
    private boolean isEditMode = false;

    @Override
    public void setView(MediaPhotoInterface.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        view.initVars();
        view.initContent();
        view.setListeners();
        interactor= MainInteractor.getInstance();
    }

    @Override
    public void onPhotoClicked(int itemID) {
        if (!isEditMode)
            view.openPhotoInFullScreen(itemID);
    }

    @Override
    public void onPhotoLongClicked(int itemID) {
        interactor.setEditMode(true);
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
    public void removeListener() {
        interactor.removeListener(this);
    }

    @Override
    public void addListener() {
        interactor.addListener(this);
    }

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public void enableEditMode(boolean enable) {
        interactor.setEditMode(enable);
    }

    @Override
    public void setEditMode(boolean enable) {
        isEditMode = enable;
        view.updatePhotoList();
    }

    @Override
    public void shareSelectedItems() {
        view.shareSelectedItems();
    }

    @Override
    public void deleteSelectedItems() {
        try {
            view.deleteSelectedItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeListener(boolean bool) {
        if (true){
            interactor.removeListener(this);
        }
    }

    @Override
    public void addListener(boolean bool) {
        if (false){
            interactor.listenersAdd(bool);
        }
    }

    @Override
    public void onPhotoCheckedChanged(int itemID, boolean isChecked) {
        view.onPhotoCheckedChanged(itemID, isChecked);
    }

    @Override
    public void onFileSuccessDeleted(String fileName) {
        //view.onFileDeleted(fileName);
        //view.updateGalleryList();
    }

    @Override
    public void onFilesDeleted() {
        view.updateGalleryList();
    }

    @Override
    public void onFileDeleteFailed(String fileName) {
        view.showToast("Error deleting files: " + fileName);
    }
}
