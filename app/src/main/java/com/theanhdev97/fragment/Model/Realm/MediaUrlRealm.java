package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.MediaUrl;

import io.realm.RealmObject;

/**
 * Created by DELL on 09/01/2018.
 */

public class MediaUrlRealm extends RealmObject {
  @SerializedName("media_url")
  private String mediaUrl;

  public void setMediaUrl(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public MediaUrlRealm() {
  }

  public String getMediaUrl() {
    return mediaUrl;
  }

}
