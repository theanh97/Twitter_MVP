package com.theanhdev97.fragment.View.LoginFragment;

/**
 * Created by DELL on 12/01/2018.
 */

public interface LoginFragmentView {
  void callbackConnectSuccessToHomeActivity();

  void showErrorNetwork();

  void connectToTwitter();

  boolean checkAuthenticated();

  boolean isInternetAvailable();

  void showErrorConnection(String error);
}
