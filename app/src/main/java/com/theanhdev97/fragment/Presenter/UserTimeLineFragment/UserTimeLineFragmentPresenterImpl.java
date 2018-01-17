package com.theanhdev97.fragment.Presenter.UserTimeLineFragment;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserTimeLineListener;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.View.UserTimeLineFragment.UserTimelineFragment;
import com.theanhdev97.fragment.View.UserTimeLineFragment.UserTimeLineFragmentView;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public class UserTimeLineFragmentPresenterImpl implements UserTimeLineFragmentPresenter {
  UserTimeLineFragmentView mView;
  TwitterInteractor mTwitterInteractor;

  public UserTimeLineFragmentPresenterImpl(UserTimelineFragment userTimelineFragment) {
    this.mView = userTimelineFragment;
    this.mTwitterInteractor = new TwitterInteractorImpl();
    loadFirstDataToView();
  }

  @Override
  public void onLoadMore(int page) {
    if (page + 1 < Const.MAX_PAGE) {
      getUserTimeLineAndShowToView(page + 1);
    } else {
      mView.dissmissProgressBarLoadMore();
    }
  }

  @Override
  public void loadFirstDataToView() {
    getUserTimeLineAndShowToView(0);
  }

  @Override
  public void getUserTimeLineAndShowToView(final int page) {
    if (mView.isNetworkAvailable()) {
      if (page == 0)
        mView.showRefreshing();
      else
        mView.showProgressBarLoadMore();
      mTwitterInteractor.getUserTimeLine(mView.getUserID(), page, new OnGetUserTimeLineListener() {
        @Override
        public void onSuccess(List<Tweet> tweets) {
          mView.showUserTimeLine(page, tweets);
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
    getUserTimeLineAndShowToView(0);
  }
}
