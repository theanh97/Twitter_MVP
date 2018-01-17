package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.Variants;

import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class VariantsRealm extends RealmObject {
  @SerializedName("content_type")
  private String content_type;
  @SerializedName("url")
  private String url;



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

