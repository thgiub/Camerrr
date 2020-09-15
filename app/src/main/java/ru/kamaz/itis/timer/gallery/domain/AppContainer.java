package ru.kamaz.itis.timer.gallery.domain;

import java.util.List;

import ru.kamaz.itis.timer.gallery.domain.AppData;

public class AppContainer {
  public List<AppData> appData;
  public int height = 1;
  public int id;
  public int left;
  public int pageNumber = 0;
  public String title;
  public int top;
  public int width = 1;

  public AppContainer(int i, String str, int i2, int i3, int i4, int i5, int i6, List<AppData> list) {
    this.id = i;
    this.title = str;
    this.top = i2;
    this.left = i3;
    if (i4 < 1) {
      i4 = 1;
    }
    this.width = i4;
    if (i5 < 1) {
      i5 = 1;
    }
    this.height = i5;
    this.pageNumber = i6;
    this.appData = list;
  }

  public void setAppData(List<AppData> appData) {
    this.appData = appData;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeft(int left) {
    this.left = left;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTop(int top) {
    this.top = top;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public List<AppData> getAppData() {
    return appData;
  }

  public int getHeight() {
    return height;
  }

  public int getId() {
    return id;
  }

  public int getLeft() {
    return left;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public String getTitle() {
    return title;
  }

  public int getTop() {
    return top;
  }

  public int getWidth() {
    return width;
  }

  public AppContainer() {
  }
}
