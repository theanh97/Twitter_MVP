package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.Tweet;

import io.realm.RealmObject;

/**
 * Created by DELL on 03/01/2018.
 */

public class TweetRealm extends RealmObject {
  @SerializedName("created_at")
  private String create_at;
  @SerializedName("text")
  private String text;
  @SerializedName("user")
  private UserRealm user;
  @SerializedName("entities")
  private EntitiesRealm entities;
  @SerializedName("retweet_count")
  private int retweetCount;
  @SerializedName("favorite_count")
  private int favoriteCount;
  @SerializedName("favourites_count")
  private int favouritesCount;
  @SerializedName("extended_entities")
  private ExtendedEntitiesRealm extendedEntities;


  public ExtendedEntitiesRealm getExtendedEntities() {
    return extendedEntities;
  }

  public void setExtendedEntities(ExtendedEntitiesRealm extendedEntities) {
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

  public TweetRealm() {

  }

  public EntitiesRealm getEntities() {
    return entities;
  }

  public void setEntities(EntitiesRealm entities) {
    this.entities = entities;
  }

  public UserRealm getUser() {
    return user;
  }

  public void setUser(UserRealm user) {
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
