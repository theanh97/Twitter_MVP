package com.theanhdev97.fragment.View.UserTimeLineFragment;

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

import com.theanhdev97.fragment.Presenter.UserTimeLineFragment.UserTimeLineFragmentPresenter;
import com.theanhdev97.fragment.Presenter.UserTimeLineFragment.UserTimeLineFragmentPresenterImpl;
import com.theanhdev97.fragment.View.FragmentInterface;
import com.theanhdev97.fragment.View.UserActivity.UserActivity;
import com.theanhdev97.fragment.Adapter.TweetAdapter;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Utils.EndlessRecyclerViewScrollListener;
import com.theanhdev97.fragment.Utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DELL on 01/02/2018.
 */

public class UserTimelineFragment extends Fragment
    implements TweetAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener,
    FragmentInterface, UserTimeLineFragmentView {

  @Bind(R.id.rclv_posts)
  RecyclerView mPostsRecyclerView;
  @Bind(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @Bind(R.id.pb_loadmore)
  ProgressBar mProgressBarLoadMore;
  @Bind(R.id.layout_no_data)
  LinearLayout mLinearLayoutNoData;

  TweetAdapter mTweetAdapter;
  List<Tweet> mListTweet;
  TwitterApiClient mTwitterApi;
  UserTimeLineFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home_timeline, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mTwitterApi = TwitterApplication.getRestClient();
    setEventListener(view);
    repairPostRecyclerView();
    mPresenter = new UserTimeLineFragmentPresenterImpl(this);
  }

  @Override
  public void setEventListener(View view) {
//    mPostsRecyclerView = view.findViewById(R.id.rclv_posts);
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
  public void repairPostRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mListTweet = new ArrayList<Tweet>();
    mTweetAdapter = new TweetAdapter(mListTweet, getActivity());
    mTweetAdapter.setItemClickListener(this);

    mPostsRecyclerView.setAdapter(mTweetAdapter);
    mPostsRecyclerView.setLayoutManager(layoutManager);
    mPostsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        mPresenter.onLoadMore(page);
      }
    });
  }

  @Override
  public boolean isNetworkAvailable() {
    if (NetworkUtils.isNetworkAvailable(getActivity()))
      return true;
    return false;
  }

  @Override
  public void showViewNoInternet() {
    Toast.makeText(getActivity(), "Network is not available !!!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showViewErrorConnection(String error) {
    Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
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
  public void showRefreshing() {
    mSwipeRefreshLayout.setRefreshing(true);
  }

  @Override
  public void dissmissRefreshing() {
    mSwipeRefreshLayout.setRefreshing(false);
  }

  @Override
  public void onClick(View view, int position) {
    Toast.makeText(getActivity(), "Position : " + position, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onRefresh() {
    mPresenter.onRefresh();
  }

  @Override
  public void showUserTimeLine(int page, List<Tweet> tweets) {
    if (page == 0) {
      mListTweet.clear();
    }
    mListTweet.addAll(tweets);
    mTweetAdapter.notifyDataSetChanged();
    mTweetAdapter.notifyItemRangeInserted(mListTweet.size() - tweets.size(), mListTweet.size());

    if (mListTweet.size() == 0)
      showViewNoData(true);
    else
      showViewNoData(false);
  }

  @Override
  public long getUserID() {
    return UserActivity.sUserInformation.getUserID();
  }

  @Override
  public void showViewNoData(boolean show) {
    mProgressBarLoadMore.setVisibility(View.INVISIBLE);
    mSwipeRefreshLayout.setRefreshing(false);
    if (show) {
      mLinearLayoutNoData.setVisibility(View.VISIBLE);
      mPostsRecyclerView.setVisibility(View.GONE);
    } else {
      mLinearLayoutNoData.setVisibility(View.GONE);
      mPostsRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public String getIconUrl() {
    if (mListTweet != null && mListTweet.size() > 0)
      return mListTweet.get(0).getUser().getProfileImageUrl();
    return "";
  }
}
