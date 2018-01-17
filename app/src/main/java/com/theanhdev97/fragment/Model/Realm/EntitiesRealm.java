package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.Entities;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.MediaUrl;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 05/01/2018.
 */

public class EntitiesRealm extends RealmObject {
  public RealmList<MediaUrlRealm> getMediaUrls() {
    return mediaUrls;
  }

  public void setMediaUrls(RealmList<MediaUrlRealm> mediaUrls) {
    this.mediaUrls = mediaUrls;
  }

  @SerializedName("media")
  private RealmList<MediaUrlRealm> mediaUrls;

  public EntitiesRealm() {
  }

}
