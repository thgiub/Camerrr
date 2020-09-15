package ru.kamaz.itis.timer.gallery.domain.domain;


import android.content.pm.ApplicationInfo;

public class AppData {
  public ApplicationInfo appInfo;
  public int containerId;
  public String packageName;

  public AppData(){}

  public AppData(int i, String str, ApplicationInfo appInfo) {
    this.containerId = i;
    this.packageName = str;
    this.appInfo = appInfo;
  }
}
