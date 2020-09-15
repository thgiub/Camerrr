package ru.kamaz.itis.timer.presenter;

import ru.kamaz.itis.timer.ui.MainActivityInterface;

public class MainActivityPresenter implements MainActivityInterface.Presenter {
    private MainActivityInterface.View view;
    @Override
    public void setView(MainActivityInterface.View view) {
        this.view=view;
    }

    @Override
    public void init() {
        view.initVars();
        view.setListeners();
    }
}
