package com.theanhdev97.fragment.Presenter.LoginFragment;

import com.theanhdev97.fragment.View.LoginFragment.LoginFragment;
import com.theanhdev97.fragment.View.LoginFragment.LoginFragmentView;

/**
 * Created by DELL on 12/01/2018.
 */


public class LoginFragmentPresenterImpl implements LoginFragmentPresenter {
  LoginFragmentView mView;

  public LoginFragmentPresenterImpl(LoginFragment loginFragment) {
    this.mView = loginFragment;
  }

  @Override
  public void loginToTwitter() {
    if (mView.isInternetAvailable()) {
      mView.connectToTwitter();
    } else {
      mView.showErrorNetwork();
    }
  }

  @Override
  public void onLoginButtonClicked() {
    if (!mView.checkAuthenticated()) {
      loginToTwitter();
    } else {
      mView.callbackConnectSuccessToHomeActivity();
    }
  }

  @Override
  public void handleLoginSuccess() {
    mView.callbackConnectSuccessToHomeActivity();
  }

  @Override
  public void handleLoginFailure(String error) {
    mView.showErrorConnection(error);
  }
}
