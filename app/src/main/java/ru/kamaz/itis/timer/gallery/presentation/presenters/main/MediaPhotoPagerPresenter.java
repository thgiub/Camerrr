package ru.kamaz.itis.timer.gallery.presentation.presenters.main;

import android.net.sip.SipSession;

import ru.kamaz.itis.timer.gallery.domain.interactors.MainInteractor;
import ru.kamaz.itis.timer.gallery.domain.interfaces.interactors.MainInteractorInterface;
import ru.kamaz.itis.timer.gallery.domain.interfaces.main.MediaPhotoPagerInterface;


public class MediaPhotoPagerPresenter implements MediaPhotoPagerInterface.Presenter, MainInteractorInterface.MediaListener {
    private MediaPhotoPagerInterface.View view;
    private MainInteractor interactor;


    @Override
    public void init() {
        view.initVars();
        view.setListeners();
       interactor = MainInteractor.getInstance();
    }

    @Override
    public void setView(MediaPhotoPagerInterface.View view) {
        this.view=view;
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
    public void onShareButtonClicked() {
        interactor.shareSelectedItems();
    }

    @Override
    public void onDeleteButtonClicked() {
        interactor.deleteSelectedItems();
    }

    @Override
    public void onListenerPagerPosition(boolean bool) {
      interactor.listenersInfo(bool);
      interactor.listenersAdd(bool);
    }

    @Override
    public void setEditMode(boolean enable) {
        if (enable)
            view.showEditButton();
        else
            view.showPagerButton();
    }

    @Override
    public void shareSelectedItems() {


    }

    @Override
    public void deleteSelectedItems() {

    }

    @Override
    public void removeListener(boolean bool) {

    }

    @Override
    public void addListener(boolean bool) {

    }
}
