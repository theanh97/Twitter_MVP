package com.theanhdev97.fragment.Presenter.HomeTimeLineFragment;

import com.theanhdev97.fragment.Model.Tweet;

import java.util.List;

/**
 * Created by DELL on 11/01/2018.
 */

public interface HomeTimeLineFragmentPresenter {
  void showTweetsFromLocalDataBase();

  List<Tweet> getTweetFromLocalDataBase();

  void clearTweetInLocalDataBase();

  void writeTweetsToLocalDataBase(List<Tweet> tweets);

  void onLoadMore(int page);

  void onRefresh();

  void handleUserIconClicked(int position);

  void loadFirstDataToView();

  void getHomeTimeLineAndShowToView(final int page);

  List<Tweet> getCurrentListTweets();
}
