package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.User;

import io.realm.RealmObject;

/**
 * Created by DELL on 05/01/2018.
 */

public class MentionRealm extends RealmObject{
  @SerializedName("created_at")
  private String create_at;
  @SerializedName("text")
  private String text;
  @SerializedName("user")
  private UserRealm user;
  @SerializedName("retweet_count")
  private int retweetCount;



  public String getCreate_at() {
    return create_at;
  }

  public void setCreate_at(String create_at) {
    this.create_at = create_at;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public UserRealm getUser() {
    return user;
  }

  public void setUser(UserRealm user) {
    this.user = user;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }
}
