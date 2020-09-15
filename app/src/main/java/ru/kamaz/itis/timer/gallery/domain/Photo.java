package ru.kamaz.itis.timer.gallery.domain;

import java.io.Serializable;

public class Photo implements Serializable {

  private String imageTitle , imagePath;
  private long bucketId, photoId;

  /**
   * @return the imageTitle
   */
  public String getImageTitle() {
    return imageTitle;
  }

  /**
   * @param imageTitle the imageTitle to set
   */
  public void setImageTitle(String imageTitle) {
    this.imageTitle = imageTitle;
  }

  /**
   * @return the imagePath
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * @param imagePath the imagePath to set
   */
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public void setBucketId(long _bucketId){
    this.bucketId = _bucketId;
  }

  public long getBucketId(){
    return bucketId;
  }

  public void setPhotoId(long _photoId){
    this.photoId = _photoId;
  }

  public long getPhotoId(){
    return photoId;
  }

  public Photo(String imageTitle, String imagePath, long _bucketId, long _photoId) {
    super();
    this.imageTitle = imageTitle;
    this.imagePath = imagePath;
    this.bucketId = _bucketId;
    this.photoId = _photoId;
  }
}
