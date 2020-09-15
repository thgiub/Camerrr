package ru.kamaz.itis.timer.gallery.domain;

public enum EnumFileType {
  IMAGE("image", 0),
  AUDIO("audio", 1),
  DOCUMENT("text", 2),
  VIDEO("video", 3),
  DIRECTORY("", 4),
  APP("application/vnd.android.package-archive", 5),
  UNKNOWN("*/*", 6);

  private int intValue;
  private String stringValue;

  EnumFileType(String str, int i) {
    this.stringValue = str;
    this.intValue = i;
  }

  public String toString() {
    return this.stringValue;
  }

  public int getId() {
    return this.intValue;
  }
}
