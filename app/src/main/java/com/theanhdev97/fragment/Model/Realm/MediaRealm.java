package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class MediaRealm extends RealmObject{

  public String getType() {
    return type;
  }

  public VideoInfoRealm getVideo_info() {
    return video_info;
  }


  @SerializedName("type")
  private String type;

  @SerializedName("media_url")
  private String media_url;

  @SerializedName("video_info")
  private VideoInfoRealm video_info;


  public void setType(String type) {
    this.type = type;
  }

  public void setMedia_url(String media_url) {
    this.media_url = media_url;
  }

  public void setVideo_info(VideoInfoRealm video_info) {
    this.video_info = video_info;
  }

  public String getMedia_url() {
    return media_url;
  }

 /*   public VideoInfoRealm getVideo_info() {
        return video_info;
    }*/

  public MediaRealm() {
    super();
  }
}

