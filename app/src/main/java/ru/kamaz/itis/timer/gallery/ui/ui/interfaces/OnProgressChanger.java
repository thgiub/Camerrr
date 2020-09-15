package ru.kamaz.itis.timer.gallery.ui.ui.interfaces;

import java.io.File;

public interface OnProgressChanger {
    void progress(int percent);

    void progressTitle(String title);

    void operationStepSuccess(EnumFileOperationsMode mode, File file);
}

enum EnumFileOperationsMode {
    COPY(0),
    CUT(1),
    DELETE(2);

    private int intValue;

    EnumFileOperationsMode(int i) {
        this.intValue = i;
    }

    public int getId() {
        return this.intValue;
    }
}
