package com.theanhdev97.fragment.View.HomeTimeLineFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theanhdev97.fragment.Adapter.TweetAdapter;
import com.theanhdev97.fragment.Model.Realm.TweetRealm;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Presenter.HomeTimeLineFragment.HomeTimeLineFragmentPresenter;
import com.theanhdev97.fragment.Presenter.HomeTimeLineFragment.HomeTimeLineFragmentPresenterImpl;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.Utils.EndlessRecyclerViewScrollListener;
import com.theanhdev97.fragment.Utils.NetworkUtils;
import com.theanhdev97.fragment.Utils.RealmHelper;
import com.theanhdev97.fragment.View.CallbackHomeInterface;
import com.theanhdev97.fragment.View.FragmentInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by DELL on 01/02/2018.
 */

public class HomeTimelineFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
    FragmentInterface,
    TweetAdapter.UserIconClickListener,
    HomeTimeLineFragmentView {

  @Bind(R.id.rclv_posts)
  RecyclerView mTweetRecyclerView;
  @Bind(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @Bind(R.id.pb_loadmore)
  ProgressBar mProgressBarLoadMore;
  @Bind(R.id.layout_no_data)
  LinearLayout mLinearLayoutNoData;

  TweetAdapter mTweetAdapter;
  List<Tweet> mListTweet;
  TwitterApiClient mTwitterApi;
  CallbackHomeInterface mCallbackHomeInterface;
  Realm mRealm;

  HomeTimeLineFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home_timeline, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
//    mTwitterApi = TwitterApplication.getRestClient();
    setEventListener(view);
    repairPostRecyclerView();
    mPresenter = new HomeTimeLineFragmentPresenterImpl(this);
//    initRealm();
//
//    RealmResults<TweetRealm> results = mRealm.where(TweetRealm.class).findAll();
//    Log.d(Const.TAG, "Local DB Tweets size : " + results.size());
  }

//  public void initRealm() {
//    mRealm = Realm.getInstance(
//        new RealmConfiguration.Builder(getActivity())
//            .name("Tweet_1")
//            .build());
//  }
//
//  private void showTweetsFromLocalDataBase() {
//    RealmResults<TweetRealm> results = mRealm.where(TweetRealm.class).findAll();
//    Log.d(Const.TAG, "local tweets size : " + results.size());
//    ArrayList<TweetRealm> tweetRealms = new ArrayList<TweetRealm>();
//    for (TweetRealm tweetRealm : results) {
//      tweetRealms.add(tweetRealm);
//    }
//    ArrayList<Tweet> tweets = RealmHelper.convertToListTweet(tweetRealms);
//    if (tweets.size() > 0) {
//      showViewNoData(false);
//      mListTweet.clear();
//      mListTweet.addAll(tweets);
//      mTweetAdapter.notifyDataSetChanged();
//      mTweetAdapter.notifyItemRangeInserted(mListTweet.size() - mListTweet.size(), mListTweet.size());
//    } else
//      showViewNoData(true);
//  }

//  @Override
//  public void loadTweetsToView(List<Tweet> tweets) {
//
//  }

  private void clearTweetsFromLocalDataBase() {
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<TweetRealm> result = realm.where(TweetRealm.class).findAll();
        result.clear();
      }
    });
  }

  private void writeTweetsToLocalDataBase() {
    // clear
    clearTweetsFromLocalDataBase();

    // add new
    final ArrayList<TweetRealm> tweetRealms = RealmHelper.convertToListTweetRealm(mListTweet);
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        for (TweetRealm tweetRealm : tweetRealms)
          mRealm.copyToRealm(tweetRealm);
      }
    });

    RealmResults<TweetRealm> results = mRealm.where(TweetRealm.class).findAll();
    Log.d(Const.TAG, "Local DB Tweets size : " + results.size());

  }

  @Override
  public void setEventListener(View view) {
//    mTweetRecyclerView = view.findViewById(R.id.rclv_posts);
//    mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    mSwipeRefreshLayout.setOnRefreshListener(this);
//    mProgressBarLoadMore = view.findViewById(R.id.pb_loadmore);
//    mLinearLayoutNoData = view.findViewById(R.id.layout_no_data);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallbackHomeInterface = null;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context != null)
      mCallbackHomeInterface = (CallbackHomeInterface) context;
  }

//  @Override
//  public void getPosts(final int page) {
//    Log.d(Const.TAG, "Page : " + page);
//    if (NetworkUtils.isNetworkAvailable(getActivity())) {
//      if (page == 0) {
//        mSwipeRefreshLayout.setRefreshing(true);
//      } else {
//        mProgressBarLoadMore.setVisibility(View.VISIBLE);
//      }
//
//      mTwitterApi.getHomeTimeline(page, new JsonHttpResponseHandler() {
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//          super.onSuccess(statusCode, headers, response);
//          Log.d(Const.TAG, "Home time line Response : " + response.toString());
//          List<Tweet> listTweet = JsonHelper.convertJsonToListTweet(response);
//          Log.d(Const.TAG, "Tweest size : " + listTweet.size());
//          // count time
//          for (Tweet p : listTweet) {
//            p.setCreate_at(DateTimeHelper.countTimeBetweenTwoDay(p.getCreate_at()));
//          }
//          if (page == 0) {
//            mListTweet.clear();
//          }
//          mListTweet.addAll(listTweet);
//          // write to local Database ( REALM )
//          // ----------------------
//          writeTweetsToLocalDataBase();
//
//          // ---------------------
//          mTweetAdapter.notifyDataSetChanged();
//          mTweetAdapter.notifyItemRangeInserted(mListTweet.size() - listTweet.size(), mListTweet.size
//              ());
//          if (mListTweet.size() == 0)
//            showViewNoData(true);
//          else
//            showViewNoData(false);
//
//
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//          super.onFailure(statusCode, headers, throwable, errorResponse);
//          Log.d(Const.TAG, "Error response : " + errorResponse.toString());
//          Toast.makeText(getActivity(), "Failure : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//          showViewNoData(true);
//        }
//      });
//    } else {
//      Toast.makeText(getActivity(), "Network is not available", Toast.LENGTH_SHORT).show();
//      mProgressBarLoadMore.setVisibility(View.INVISIBLE);
//      mSwipeRefreshLayout.setRefreshing(false);
////      showViewNoData(true);
//      showTweetsFromLocalDataBase();
//    }
//  }

  @Override
  public void repairPostRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mListTweet = new ArrayList<Tweet>();
    mTweetAdapter = new TweetAdapter(mListTweet, getActivity());
    mTweetAdapter.setUserIconClickListener(this);
    mTweetAdapter.mFlagUserIconClickListener = true;
    mTweetRecyclerView.setAdapter(mTweetAdapter);
    mTweetRecyclerView.setLayoutManager(layoutManager);
    mTweetRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        mPresenter.onLoadMore(page);
      }
    });
  }

  @Override
  public void loadTweetsToView(List<Tweet> tweets) {
    if (tweets.size() > 0) {
      showViewNoData(false);
      mListTweet.clear();
      mListTweet.addAll(tweets);
      mTweetAdapter.notifyDataSetChanged();
      mTweetAdapter.notifyItemRangeInserted(mListTweet.size() - mListTweet.size(), mListTweet.size());
    } else
      showViewNoData(true);
  }

  @Override
  public void callFromHomeToRefreshListTweets() {
    mPresenter.onRefresh();
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
      mTweetRecyclerView.setVisibility(View.GONE);
    } else {
      mLinearLayoutNoData.setVisibility(View.GONE);
      mTweetRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public String getIconUrl() {
    if (mListTweet != null && mListTweet.size() > 0)
      return mListTweet.get(0).getUser().getProfileImageUrl();
    return "";
  }

  @Override
  public void onUserIconClickListener(View view, int position) {
    mPresenter.handleUserIconClicked(position);
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
  public void showHomeTimeLine(int page, List<Tweet> tweets) {
    if (page == 0) {
      mListTweet.clear();
    }
    mListTweet.addAll(tweets);
    // ---------------------
    mTweetAdapter.notifyDataSetChanged();
    mTweetAdapter.notifyItemRangeInserted(mListTweet.size() - tweets.size(), mListTweet.size
        ());
    if (mListTweet.size() == 0)
      showViewNoData(true);
    else
      showViewNoData(false);

  }

  @Override
  public List<Tweet> getCurrentListTweets() {
    return mListTweet;
  }

  @Override
  public void callBackNavigateToUserActivity(User userInformation) {
    mCallbackHomeInterface.callBackUserActivity(userInformation);
  }
}
