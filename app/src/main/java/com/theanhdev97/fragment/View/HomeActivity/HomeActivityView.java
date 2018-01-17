package com.theanhdev97.fragment.View.HomeActivity;

import android.support.v4.app.Fragment;

import com.theanhdev97.fragment.Model.User;

/**
 * Created by DELL on 11/01/2018.
 */

public interface HomeActivityView {
  void initToolbar();

  void initProgressDialog();

  void initViewPagerFragmentAndTabhost();

  void replaceFragment(Fragment fragment);

  void showLogOutDialog();

  void showNewTweetDialog(String profileImageUrl);

  void showMenu(boolean isShow);

  void showLogoutView();

  void navigateToUserActivity(User userInformation);

  int getCurrentFragmentPosition();

  void refreshHomeTimeLineFragment();

  void refreshMentionFragment();

  void showWaitingDialog();

  void dismissWaitingDialog();

  boolean checkNetworkIsAvailable();

  void showErrorMessage(String message);
}
