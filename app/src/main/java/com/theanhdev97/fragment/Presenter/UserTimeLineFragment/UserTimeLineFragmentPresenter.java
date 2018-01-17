package com.theanhdev97.fragment.Presenter.UserTimeLineFragment;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserTimeLineFragmentPresenter {
  void onLoadMore(int page);

  void loadFirstDataToView();

  void getUserTimeLineAndShowToView(int page);

  void onRefresh();
}
