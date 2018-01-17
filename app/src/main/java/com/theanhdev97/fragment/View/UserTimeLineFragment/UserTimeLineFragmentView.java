package com.theanhdev97.fragment.View.UserTimeLineFragment;

import android.view.View;

import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.Tweet;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserTimeLineFragmentView {
  void showUserTimeLine(int page, List<Tweet> tweets);

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
