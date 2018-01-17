package com.theanhdev97.fragment.Presenter.HomeTimeLineFragment;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetHomeTimeLine;
import com.theanhdev97.fragment.Interactor.RealmInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Interactor.RealmInteractorImpl;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.View.HomeTimeLineFragment.HomeTimelineFragment;
import com.theanhdev97.fragment.View.HomeTimeLineFragment.HomeTimeLineFragmentView;

import java.util.List;

/**
 * Created by DELL on 11/01/2018.
 */

public class HomeTimeLineFragmentPresenterImpl implements HomeTimeLineFragmentPresenter {
  private HomeTimeLineFragmentView mView;
  private TwitterInteractor mTwitterInteractor;
  private RealmInteractor mRealmInteractor;

  public HomeTimeLineFragmentPresenterImpl(HomeTimelineFragment homeTimelineFragment) {
    this.mView = homeTimelineFragment;
    this.mTwitterInteractor = new TwitterInteractorImpl();
    this.mRealmInteractor = new RealmInteractorImpl(homeTimelineFragment.getActivity());
    loadFirstDataToView();
  }


  @Override
  public void showTweetsFromLocalDataBase() {
    List<Tweet> tweets = getTweetFromLocalDataBase();
    mView.loadTweetsToView(tweets);
  }

  @Override
  public List<Tweet> getTweetFromLocalDataBase() {
    return mRealmInteractor.getTweetsFromLocalDataBase();
  }

  @Override
  public void clearTweetInLocalDataBase() {
    mRealmInteractor.clearTweetsInLocalDataBase();
  }

  @Override
  public void writeTweetsToLocalDataBase(List<Tweet> tweets) {
    mRealmInteractor.writeTweetsToLocalDataBase(tweets);
  }

  @Override
  public void onLoadMore(int page) {
    if (page + 1 < Const.MAX_PAGE) {
      getHomeTimeLineAndShowToView(page + 1);
    } else {
      mView.dissmissProgressBarLoadMore();
    }
  }


  @Override
  public void loadFirstDataToView() {
    getHomeTimeLineAndShowToView(0);
  }

  @Override
  public void getHomeTimeLineAndShowToView(final int page) {
    if (mView.isNetworkAvailable()) {
      if (page == 0)
        mView.showRefreshing();
      else
        mView.showProgressBarLoadMore();
      mTwitterInteractor.getHomeTimeLine(page, new OnGetHomeTimeLine() {
        @Override
        public void onSuccess(List<Tweet> tweets) {
          mView.showHomeTimeLine(page, tweets);
          mRealmInteractor.clearTweetsInLocalDataBase();
          mRealmInteractor.writeTweetsToLocalDataBase(getCurrentListTweets());
        }

        @Override
        public void onFailure(String error) {
          mView.showViewErrorConnection(error);
          mView.showViewNoData(true);
        }
      });
    } else {
      mView.dissmissProgressBarLoadMore();
      mView.dissmissRefreshing();
      showTweetsFromLocalDataBase();
      mView.showViewNoInternet();
    }
  }

  @Override
  public void onRefresh() {
    getHomeTimeLineAndShowToView(0);
  }

  @Override
  public void handleUserIconClicked(int position) {
    Tweet tweet = getCurrentListTweets().get(position);
    if (tweet != null) {
      User userInformation = tweet.getUser();
      mView.callBackNavigateToUserActivity(userInformation);
    }
  }

  @Override
  public List<Tweet> getCurrentListTweets() {
    return mView.getCurrentListTweets();
  }
}
