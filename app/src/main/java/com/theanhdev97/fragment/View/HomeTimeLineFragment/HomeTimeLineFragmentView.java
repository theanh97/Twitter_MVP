package com.theanhdev97.fragment.View.HomeTimeLineFragment;

import android.util.Log;
import android.view.View;

import com.theanhdev97.fragment.Model.Realm.TweetRealm;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Utils.Const;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by DELL on 11/01/2018.
 */

public interface HomeTimeLineFragmentView {
  void setEventListener(View view);

  void repairPostRecyclerView();

  void loadTweetsToView(List<Tweet> tweets);

  void showViewNoData(boolean show);

  boolean isNetworkAvailable();

  void showViewErrorConnection(String error);

  void showViewNoInternet();

  void showProgressBarLoadMore();

  void dissmissProgressBarLoadMore();

  void showRefreshing();

  void dissmissRefreshing();

  void showHomeTimeLine(int page, List<Tweet> tweets);

  List<Tweet> getCurrentListTweets();

  void callBackNavigateToUserActivity(User userInformation);

  void callFromHomeToRefreshListTweets();
}
