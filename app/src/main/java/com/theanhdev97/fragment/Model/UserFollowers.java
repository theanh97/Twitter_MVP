package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class UserFollowers {
  @SerializedName("users")
  private List<User> users;

  public UserFollowers(List<User> users) {
    this.users = users;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
