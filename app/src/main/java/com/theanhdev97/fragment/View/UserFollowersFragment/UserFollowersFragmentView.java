package com.theanhdev97.fragment.View.UserFollowersFragment;

import android.view.View;

import com.theanhdev97.fragment.Model.UserFollowers;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserFollowersFragmentView {
  void showUsersFollower(int page, UserFollowers userFollowers);

  long getUserID();

  void showViewNoData(boolean show);

  void setEventListener(View view);

  void repairPostRecyclerView();

  boolean isNetworkAvailable();

  void showViewNoInternet();

  void showViewErrorConnection(String error);

  void showProgressBarLoadMore();

  void dissmissProgressBarLoadMore();

  void showRefreshing();

  void dissmissRefreshing();
}
