package com.theanhdev97.fragment.Interactor.InteractorListener;

import com.theanhdev97.fragment.Model.Tweet;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public interface OnGetUserTimeLineListener {
  void onSuccess(List<Tweet> tweets);

  void onFailure(String error);
}
