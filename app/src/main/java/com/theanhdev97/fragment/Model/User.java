package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by DELL on 03/01/2018.
 */

public class User implements Serializable {
  @SerializedName("name")
  private String name;
  @SerializedName("profile_image_url")
  private String profileImageUrl;
  @SerializedName("screen_name")
  private String screenName;
  @SerializedName("favourites_count")
  private int favouritesCount;

  @SerializedName("profile_banner_url")
  private String profileBannerUrl;
  @SerializedName("followers_count")
  private int followersCount;
  @SerializedName("friends_count")
  private int followingCount;
  @SerializedName("id")
  private long userID;

  public User(String name, String profileImageUrl, String screenName, int favouritesCount, String profileBannerUrl, int followersCount, int followingCount, long userID) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.screenName = screenName;
    this.favouritesCount = favouritesCount;
    this.profileBannerUrl = profileBannerUrl;
    this.followersCount = followersCount;
    this.followingCount = followingCount;
    this.userID = userID;
  }

  public long getUserID() {
    return userID;
  }

  public void setUserID(long userID) {
    this.userID = userID;
  }

  public String getProfileBannerUrl() {
    return profileBannerUrl;
  }

  public void setProfileBannerUrl(String profileBannerUrl) {
    this.profileBannerUrl = profileBannerUrl;
  }

  public int getFollowingCount() {
    return followingCount;
  }

  public void setFollowingCount(int followingCount) {
    this.followingCount = followingCount;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public void setFollowersCount(int followersCount) {
    this.followersCount = followersCount;
  }

  public int getFavouritesCount() {
    return favouritesCount;
  }

  public void setFavouritesCount(int favouritesCount) {
    this.favouritesCount = favouritesCount;
  }

  public User() {

  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }


  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
