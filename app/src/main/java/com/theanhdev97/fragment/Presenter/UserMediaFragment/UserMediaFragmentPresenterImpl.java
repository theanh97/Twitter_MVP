package com.theanhdev97.fragment.Presenter.UserMediaFragment;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserMediaListener;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.View.UserMediaFragment.UserMediaFragment;
import com.theanhdev97.fragment.View.UserMediaFragment.UserMediaFragmentView;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public class UserMediaFragmentPresenterImpl implements UserMediaFragmentPresenter {
  private UserMediaFragmentView mView;
  private TwitterInteractor mTwitterInteractor;

  public UserMediaFragmentPresenterImpl(UserMediaFragment userMediaFragment) {
    this.mView = userMediaFragment;
    this.mTwitterInteractor = new TwitterInteractorImpl();
    loadFirstDataToView();
  }

  @Override
  public void onLoadMore(int page) {
    if (page + 1 < Const.MAX_PAGE) {
      getUserMediaAndShowToView(page + 1);
    } else {
      mView.dissmissProgressBarLoadMore();
    }
  }

  @Override
  public void loadFirstDataToView() {
    getUserMediaAndShowToView(0);
  }

  @Override
  public void getUserMediaAndShowToView(final int page) {
    if (mView.isNetworkAvailable()) {
      if (page == 0)
        mView.showRefreshing();
      else
        mView.showProgressBarLoadMore();
        mTwitterInteractor.getUserMedia(mView.getUserID(), page, new OnGetUserMediaListener() {
          @Override
          public void onSuccess(List<Media> medias) {
            mView.showUserMedias(page,medias);
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
    getUserMediaAndShowToView(0);
  }
}
