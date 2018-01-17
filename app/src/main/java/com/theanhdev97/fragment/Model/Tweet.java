package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by DELL on 03/01/2018.
 */

public class Tweet {
  @SerializedName("created_at")
  private String create_at;
  @SerializedName("text")
  private String text;
  @SerializedName("user")
  private User user;
  @SerializedName("entities")
  private Entities entities;
  @SerializedName("retweet_count")
  private int retweetCount;
  @SerializedName("favorite_count")
  private int favoriteCount;

  @SerializedName("favourites_count")
  private int favouritesCount;

  @SerializedName("extended_entities")
  private ExtendedEntities extendedEntities;

  public Tweet(String create_at, String text, User user, Entities entities, int retweetCount, int favoriteCount, int favouritesCount, ExtendedEntities extendedEntities) {
    this.create_at = create_at;
    this.text = text;
    this.user = user;
    this.entities = entities;
    this.retweetCount = retweetCount;
    this.favoriteCount = favoriteCount;
    this.favouritesCount = favouritesCount;
    this.extendedEntities = extendedEntities;
  }

  public ExtendedEntities getExtendedEntities() {
    return extendedEntities;
  }

  public void setExtendedEntities(ExtendedEntities extendedEntities) {
    this.extendedEntities = extendedEntities;
  }

  public int getFavouritesCount() {
    return favouritesCount;
  }

  public void setFavouritesCount(int favouritesCount) {
    this.favouritesCount = favouritesCount;
  }

  public int getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public Tweet() {

  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
}
