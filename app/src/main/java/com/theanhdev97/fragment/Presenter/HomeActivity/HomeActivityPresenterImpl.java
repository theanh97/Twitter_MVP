package com.theanhdev97.fragment.Presenter.HomeActivity;

import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserListener;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.View.HomeActivity.HomeActivity;
import com.theanhdev97.fragment.View.HomeActivity.HomeActivityView;
import com.theanhdev97.fragment.View.HomeTimeLineFragment.HomeTimelineFragment;
import com.theanhdev97.fragment.View.LoginFragment.LoginFragment;

/**
 * Created by DELL on 11/01/2018.
 */

public class HomeActivityPresenterImpl implements HomeActivityPresenter,
    OnGetUserListener {
  HomeActivityView mView;
  TwitterInteractor mTwitterInteractor;
  boolean mFlagNewTweet;

  public HomeActivityPresenterImpl(HomeActivity homeActivity) {
    this.mView = homeActivity;
    this.mTwitterInteractor = new TwitterInteractorImpl();

    mView.initToolbar();
    mView.initProgressDialog();
    mView.initViewPagerFragmentAndTabhost();
    mView.replaceFragment(new LoginFragment());
  }

  @Override
  public void handlingLogOutTwitter() {
    clearTwitterAccessToken();
    mView.showLogoutView();
  }

  @Override
  public void clearTwitterAccessToken() {
    mTwitterInteractor.clearAccessToken();
  }

  @Override
  public void onClickMenuLogOut() {
    mView.showLogOutDialog();
  }

  @Override
  public void onClickMenuNewTweet() {
    mFlagNewTweet = true;
    mTwitterInteractor.getUserInfo(this);
  }

  @Override
  public void onClickMenuInfo() {
    mFlagNewTweet = false;
    if (mView.checkNetworkIsAvailable()) {
      mView.showWaitingDialog();
      mTwitterInteractor.getUserInfo(this);
    }
  }

  @Override
  public void onCallBackShowUserActivity(User userInformation) {
    mView.navigateToUserActivity(userInformation);
  }

  @Override
  public void onCallBackTweetSuccessFull() {
    // HomeTimeLine fragment
    if (mView.getCurrentFragmentPosition() == 0)
      mView.refreshHomeTimeLineFragment();
      // Mention fragment
    else
      mView.refreshMentionFragment();
  }


  @Override
  public void onSuccess(User user) {
    // post new Tweet
    if (mFlagNewTweet)
      mView.showNewTweetDialog(user.getProfileImageUrl());
      // Navigate User Activity
    else {
      mView.dismissWaitingDialog();
      mView.navigateToUserActivity(user);
    }
  }

  @Override
  public void onFailure(String error) {
    mView.dismissWaitingDialog();
    mView.showErrorMessage(error);
  }

  @Override
  public void onConnectSuccess() {
    mView.showMenu(true);
    mView.replaceFragment(new HomeTimelineFragment());
  }
}
