package com.theanhdev97.fragment.Presenter.UserMediaFragment;

/**
 * Created by DELL on 13/01/2018.
 */

public interface UserMediaFragmentPresenter {
    void onLoadMore(int page);

    void loadFirstDataToView();

    void getUserMediaAndShowToView(int page);

    void onRefresh();
}
