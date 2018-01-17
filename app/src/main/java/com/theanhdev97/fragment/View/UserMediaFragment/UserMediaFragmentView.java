package com.theanhdev97.fragment.View.UserMediaFragment;

import android.view.View;

import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.UserFollowers;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserMediaFragmentView {
  void showUserMedias(int page, List<Media> medias);

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
