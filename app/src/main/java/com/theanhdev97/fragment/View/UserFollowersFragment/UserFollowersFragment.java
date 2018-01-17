package com.theanhdev97.fragment.View.UserFollowersFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theanhdev97.fragment.Presenter.UserFollowersFragment.UserFollowersFragmentPresenter;
import com.theanhdev97.fragment.Presenter.UserFollowersFragment.UserFollowersFragmentPresenterImpl;
import com.theanhdev97.fragment.View.UserActivity.UserActivity;
import com.theanhdev97.fragment.Adapter.UserAdapter;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Model.UserFollowers;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Utils.EndlessRecyclerViewScrollListener;
import com.theanhdev97.fragment.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DELL on 01/02/2018.
 */

public class UserFollowersFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, UserFollowersFragmentView {

  @Bind(R.id.rclv_followers)
  RecyclerView mUserRecyclerView;
  @Bind(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @Bind(R.id.pb_loadmore)
  ProgressBar mProgressBarLoadMore;
  @Bind(R.id.layout_no_data)
  LinearLayout mLinearLayoutNoData;

  UserAdapter mUserAdapter;
  List<User> mUsers;
  TwitterApiClient mTwitterApi;
  UserFollowersFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_user_followers, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mTwitterApi = TwitterApplication.getRestClient();
    setEventListener(view);
    repairPostRecyclerView();
    mPresenter = new UserFollowersFragmentPresenterImpl(this);
  }

  @Override
  public void setEventListener(View view) {
//    mUserRecyclerView = view.findViewById(R.id.rclv_followers);
//    mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    mSwipeRefreshLayout.setOnRefreshListener(this);
//    mProgressBarLoadMore = view.findViewById(R.id.pb_loadmore);
//    mLinearLayoutNoData = view.findViewById(R.id.layout_no_data);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public long getUserID() {
    return UserActivity.sUserInformation.getUserID();
  }

  @Override
  public void showRefreshing() {
    mSwipeRefreshLayout.setRefreshing(true);
  }

  @Override
  public void dissmissRefreshing() {
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void showProgressBarLoadMore() {
    mProgressBarLoadMore.setVisibility(View.VISIBLE);
  }

  @Override
  public void dissmissProgressBarLoadMore() {
    mProgressBarLoadMore.setVisibility(View.GONE);
  }

  @Override
  public void showUsersFollower(int page, UserFollowers userFollowers) {
    if (page == 0) {
      mUsers.clear();
    }
    mUsers.addAll(userFollowers.getUsers());
    mUserAdapter.notifyDataSetChanged();
    mUserAdapter.notifyItemRangeInserted(mUsers.size() - userFollowers.getUsers().size(), mUsers.size());

    if (mUsers.size() == 0)
      showViewNoData(true);
    else
      showViewNoData(false);
  }

  @Override
  public void showViewErrorConnection(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    showViewNoData(true);
  }

  @Override
  public void showViewNoInternet() {
    mProgressBarLoadMore.setVisibility(View.INVISIBLE);
    mSwipeRefreshLayout.setRefreshing(false);
    showViewNoData(true);
  }

  @Override
  public void repairPostRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mUsers = new ArrayList<User>();
    mUserAdapter = new UserAdapter(mUsers, getActivity());
    mUserRecyclerView.setAdapter(mUserAdapter);
    mUserRecyclerView.setLayoutManager(layoutManager);
    mUserRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        mPresenter.onLoadMore(page);
      }
    });
  }

  @Override
  public void onRefresh() {
    mPresenter.onRefresh();
  }

  @Override
  public void showViewNoData(boolean show) {
    mProgressBarLoadMore.setVisibility(View.INVISIBLE);
    mSwipeRefreshLayout.setRefreshing(false);
    if (show) {
      mLinearLayoutNoData.setVisibility(View.VISIBLE);
      mUserRecyclerView.setVisibility(View.GONE);
    } else {
      mLinearLayoutNoData.setVisibility(View.GONE);
      mUserRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public boolean isNetworkAvailable() {
    if (NetworkUtils.isNetworkAvailable(getActivity()))
      return true;
    return false;
  }
}
