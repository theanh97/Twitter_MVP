package com.theanhdev97.fragment.Presenter.UserActivity;

import com.theanhdev97.fragment.View.UserActivity.UserActivity;
import com.theanhdev97.fragment.View.UserActivity.UserActivityView;

/**
 * Created by DELL on 11/01/2018.
 */

public class UserActivityPresenterImpl implements UserActivityPresenter {
  UserActivityView mView ;

  public UserActivityPresenterImpl(UserActivity userActivity){
    this.mView = userActivity;
  }

  @Override
  public void onClickBackNavigation() {
    mView.backActivity();
  }
}
