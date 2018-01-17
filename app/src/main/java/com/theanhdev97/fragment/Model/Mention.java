package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by DELL on 05/01/2018.
 */

public class Mention {
  @SerializedName("created_at")
  private String create_at;
  @SerializedName("text")
  private String text;
  @SerializedName("user")
  private User user;
  @SerializedName("retweet_count")
  private int retweetCount;

  public Mention(String create_at, String text, User user, int retweetCount) {
    this.create_at = create_at;
    this.text = text;
    this.user = user;
    this.retweetCount = retweetCount;
  }


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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }
}
