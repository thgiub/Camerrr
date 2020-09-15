package ru.kamaz.itis.timer.gallery.domain.domain.interfaces.interactors;

public interface MainInteractorInterface  {
    interface MediaListener{
        void setEditMode(boolean enable);
        void shareSelectedItems();
        void deleteSelectedItems();
    }

}
