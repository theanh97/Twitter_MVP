package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 08/01/2018.
 */

public class Media {

  public Media(String type, String media_url, VideoInfo video_info) {
    this.type = type;
    this.media_url = media_url;
    this.mVideo_info = video_info;
  }

  public String getType() {
    return type;
  }

  public VideoInfo getVideo_info() {
    return mVideo_info;
  }

  @SerializedName("type")
  private String type;

  @SerializedName("media_url")
  private String media_url;

  @SerializedName("mVideo_info")
  private VideoInfo mVideo_info;


  public void setType(String type) {
    this.type = type;
  }

  public void setMedia_url(String media_url) {
    this.media_url = media_url;
  }

  public void setVideo_info(VideoInfo video_info) {
    this.mVideo_info = video_info;
  }

  public String getMedia_url() {
    return media_url;
  }

 /*   public VideoInfo getVideo_info() {
        return mVideo_info;
    }*/

  public Media() {
    super();
  }
}

