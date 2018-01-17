package com.theanhdev97.fragment.Presenter.HomeActivity;

import com.theanhdev97.fragment.Model.User;

/**
 * Created by DELL on 11/01/2018.
 */

public interface HomeActivityPresenter {
  void handlingLogOutTwitter();

  void clearTwitterAccessToken();

  void onClickMenuLogOut();

  void onClickMenuNewTweet();

  void onClickMenuInfo();

  void onCallBackShowUserActivity(User UserInformation);

  void onCallBackTweetSuccessFull();

  void onConnectSuccess();
}
