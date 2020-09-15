package ru.kamaz.itis.timer.gallery.domain;


import androidx.annotation.Nullable;

public class Media {

  public long id;
  @Nullable
  public String title;
  @Nullable
  public String displayName;
  public double duration;
  public String path;
  @Nullable
  public String artist;
  @Nullable
  public String thumb;
  public EnumFileType type;

  public Media(long _id, @Nullable String _title, @Nullable String _displayName, double _duration,
               String _path, @Nullable String _artist, @Nullable String _thumb, EnumFileType _type) {
    this.id = _id;
    this.title = _title;
    this.displayName = _displayName;
    this.duration = _duration;
    this.path = _path;
    this.artist = _artist;
    this.thumb = _thumb;
    this.type = _type;
  }

  public Media(long _id, @Nullable String _title, @Nullable String _displayName, double _duration,
               String _path, @Nullable String _artist, EnumFileType _type) {
    this.id = _id;
    this.title = _title;
    this.displayName = _displayName;
    this.duration = _duration;
    this.path = _path;
    this.artist = _artist;
    this.type = _type;
  }

  public Media() {
    super();
  }
}
