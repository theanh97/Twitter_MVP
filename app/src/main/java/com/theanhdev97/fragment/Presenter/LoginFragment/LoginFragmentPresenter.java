package com.theanhdev97.fragment.Presenter.LoginFragment;

/**
 * Created by DELL on 12/01/2018.
 */

public interface LoginFragmentPresenter {
  void loginToTwitter();

  void onLoginButtonClicked();

  void handleLoginSuccess();

  void handleLoginFailure(String error);
}
