package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class Variants {
  @SerializedName("content_type")
  private String content_type;
  @SerializedName("url")
  private String url;

  public Variants(String content_type, String url) {
    this.content_type = content_type;
    this.url = url;
  }

  public void setContent_type(String content_type) {
    this.content_type = content_type;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  public String getContent_type() {
    return content_type;
  }

  public String getUrl() {
    return url;
  }
}

