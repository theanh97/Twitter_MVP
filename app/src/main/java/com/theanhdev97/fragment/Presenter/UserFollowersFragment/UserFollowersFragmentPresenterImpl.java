package com.theanhdev97.fragment.Presenter.UserFollowersFragment;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUsersFollowerListener;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.Model.UserFollowers;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.View.UserFollowersFragment.UserFollowersFragment;
import com.theanhdev97.fragment.View.UserFollowersFragment.UserFollowersFragmentView;

/**
 * Created by DELL on 13/01/2018.
 */

public class UserFollowersFragmentPresenterImpl implements UserFollowersFragmentPresenter {
  UserFollowersFragmentView mView;
  TwitterInteractor mTwitterInteractor;

  public UserFollowersFragmentPresenterImpl(UserFollowersFragment userFollowersFragment) {
    this.mView = userFollowersFragment;
    this.mTwitterInteractor = new TwitterInteractorImpl();
    loadFirstDataToView();
  }


  @Override
  public void onLoadMore(int page) {
    if (page + 1 < Const.MAX_PAGE) {
      getUsersFollowersAndShowToView(page + 1);
    } else {
      mView.dissmissProgressBarLoadMore();
    }
  }

  @Override
  public void loadFirstDataToView() {
    getUsersFollowersAndShowToView(0);
  }

  @Override
  public void getUsersFollowersAndShowToView(final int page) {
    if (mView.isNetworkAvailable()) {
      if (page == 0)
        mView.showRefreshing();
      else
        mView.showProgressBarLoadMore();

      mTwitterInteractor.getUsersFollower(mView.getUserID(), page, new OnGetUsersFollowerListener() {
        @Override
        public void onSuccess(UserFollowers userFollowers) {
          mView.showUsersFollower(page, userFollowers);
        }

        @Override
        public void onFailure(String error) {
          mView.showViewErrorConnection(error);
        }
      });
    } else
      mView.showViewNoInternet();
  }

  @Override
  public void onRefresh() {
    getUsersFollowersAndShowToView(0);
  }
}
