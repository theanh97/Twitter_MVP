package com.theanhdev97.fragment.Presenter.UserFollowersFragment;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserFollowersFragmentPresenter {
    void onLoadMore(int page);

    void loadFirstDataToView();

    void getUsersFollowersAndShowToView(int page);

    void onRefresh();
}
