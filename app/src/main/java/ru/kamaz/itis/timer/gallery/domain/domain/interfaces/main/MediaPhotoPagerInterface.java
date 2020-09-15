package ru.kamaz.itis.timer.gallery.domain.domain.interfaces.main;

public interface MediaPhotoPagerInterface {
    interface View{
        void setListeners();
        void initVars();
        void showEditButton();
        void showPagerButton();
    }
    interface Presenter{
        void init();
        void setView(View view);
        void onResume();
        void onPause();
        void onShareButtonClicked();
        void onDeleteButtonClicked();
    }
}
