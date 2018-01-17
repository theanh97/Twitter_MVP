package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Model.UserFollowers;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class UserFollowersRealm extends RealmObject {
  @SerializedName("users")
  private RealmList<UserRealm> users;


  public RealmList<UserRealm> getUsers() {
    return users;
  }

  public void setUsers(RealmList<UserRealm> users) {
    this.users = users;
  }
}
