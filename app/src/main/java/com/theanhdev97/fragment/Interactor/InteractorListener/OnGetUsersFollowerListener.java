package com.theanhdev97.fragment.Interactor.InteractorListener;

import com.theanhdev97.fragment.Model.UserFollowers;

/**
 * Created by DELL on 13/01/2018.
 */

public interface OnGetUsersFollowerListener {
  void onSuccess(UserFollowers userFollowers);

  void onFailure(String error);
}
