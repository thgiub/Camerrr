package ru.kamaz.itis.timer.ui;

public interface MainActivityInterface {
    interface View{
        void setListeners();
        void initVars();

    }
    interface Presenter{
        void init();
        void setView(View view);
    }
}
