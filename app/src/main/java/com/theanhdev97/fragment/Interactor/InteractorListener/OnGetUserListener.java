package com.theanhdev97.fragment.Interactor.InteractorListener;

import android.os.Message;

import com.theanhdev97.fragment.Model.User;

/**
 * Created by DELL on 11/01/2018.
 */

public interface OnGetUserListener {
  void onSuccess(User user);

  void onFailure(String error);
}
