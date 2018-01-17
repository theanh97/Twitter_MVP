package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 05/01/2018.
 */

public class Entities {
  public List<MediaUrl> getMediaUrls() {
    return mediaUrls;
  }

  public void setMediaUrls(List<MediaUrl> mediaUrls) {
    this.mediaUrls = mediaUrls;
  }

  @SerializedName("media")
  private List<MediaUrl> mediaUrls;

  public Entities(List<MediaUrl> mediaUrls) {
    this.mediaUrls = mediaUrls;
  }

  public Entities() {
  }
}
