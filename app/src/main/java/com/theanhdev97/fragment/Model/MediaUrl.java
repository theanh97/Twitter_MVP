package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DELL on 09/01/2018.
 */

public class MediaUrl  {
  @SerializedName("media_url")
  private String mediaUrl;

  public MediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public MediaUrl() {
  }

  public String getMediaUrl() {
    return mediaUrl;
  }
}
