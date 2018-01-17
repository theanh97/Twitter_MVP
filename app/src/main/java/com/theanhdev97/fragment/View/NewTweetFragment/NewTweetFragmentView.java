package com.theanhdev97.fragment.View.NewTweetFragment;

import android.view.View;

/**
 * Created by DELL on 13/01/2018.
 */

public interface NewTweetFragmentView {
  void setIcon();

  void initProgressDialog();

  void setEventListener();

  void showError(String error);

  void dismissNewTweetDialog();

  void dismissWaitingProgressDialog();

  void showWaitingProgressDialog();

  boolean isNetworkAvailable();

  void callbackTweetSuccessfulToHomeActivity();

  String getTweetContent();
}
