package ru.kamaz.itis.timer.gallery.domain;

public class GalleryPhotoAlbum {
  private long bucketId;
  private String bucketName;
  private String takenDate;
  private int photoCountByAlbum;
  private String data;

  public long getBucketId() {
    return bucketId;
  }

  public void setBucketId(long bucketId) {
    this.bucketId = bucketId;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public String getTakenDate() {
    return takenDate;
  }

  public void setTakenDate(String takenDate) {
    this.takenDate = takenDate;
  }

  public int getPhotoCountByAlbum() {
    return photoCountByAlbum;
  }

  public void setPhotoCountByAlbum(int photoCountByAlbum) {
    this.photoCountByAlbum = photoCountByAlbum;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
