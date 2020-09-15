package ru.kamaz.itis.timer.gallery.domain.interfaces.interactors;

public interface MainInteractorInterface  {


    interface MediaListener{
        void setEditMode(boolean enable);
        void shareSelectedItems();
        void deleteSelectedItems();
        void removeListener(boolean bool);
        void addListener(boolean bool);
    }



}
