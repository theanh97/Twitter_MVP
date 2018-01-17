package com.theanhdev97.fragment.View.LoginFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginFragment;
import com.theanhdev97.fragment.Presenter.LoginFragment.LoginFragmentPresenter;
import com.theanhdev97.fragment.Presenter.LoginFragment.LoginFragmentPresenterImpl;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Utils.NetworkUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DELL on 01/02/2018.
 */

public class LoginFragment extends OAuthLoginFragment<TwitterApiClient>
    implements View.OnClickListener,
    LoginFragmentView {
  @Bind(R.id.btn_login)
  Button mLoginButton;

  OnLoginFragmentInteraction mCallback;
  LoginFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context != null)
      mCallback = (OnLoginFragmentInteraction) context;
    else
      throw new RuntimeException("implement interface");
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallback = null;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mPresenter = new LoginFragmentPresenterImpl(this);
//    mLoginButton = view.findViewById(R.id.btn_login);
    mLoginButton.setOnClickListener(this);
  }

  @Override
  public void onLoginSuccess() {
    mPresenter.handleLoginSuccess();
  }

  @Override
  public void onLoginFailure(Exception e) {
    mPresenter.handleLoginFailure(e.getMessage());
  }

  @Override
  public void onClick(View view) {
    mPresenter.onLoginButtonClicked();
  }

  @Override
  public void callbackConnectSuccessToHomeActivity() {
    mCallback.connectSuccess();
  }

  @Override
  public boolean isInternetAvailable() {
    if (NetworkUtils.isNetworkAvailable(getActivity()))
      return true;
    return false;
  }

  @Override
  public void showErrorNetwork() {
    Toast.makeText(getActivity(), "Network is not available !!!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showErrorConnection(String error) {
    Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void connectToTwitter() {
    TwitterApplication.getRestClient().connect();
  }

  @Override
  public boolean checkAuthenticated() {
    if (getClient().isAuthenticated())
      return true;
    return false;
  }

  public interface OnLoginFragmentInteraction {
    void connectSuccess();

    void callbackLogoutToHideMenu();
  }
}
