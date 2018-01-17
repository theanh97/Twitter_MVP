package com.theanhdev97.fragment.View.MentionFragment;

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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.theanhdev97.fragment.Adapter.TweetAdapter;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Presenter.MentionFragment.MentionFragmentPresenter;
import com.theanhdev97.fragment.Presenter.MentionFragment.MentionFragmentPresenterImpl;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.Utils.DateTimeHelper;
import com.theanhdev97.fragment.Utils.EndlessRecyclerViewScrollListener;
import com.theanhdev97.fragment.Utils.JsonHelper;
import com.theanhdev97.fragment.Utils.NetworkUtils;
import com.theanhdev97.fragment.View.FragmentInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by DELL on 05/01/2018.
 */

public class MentionFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
    FragmentInterface,
    MentionFragmentView {
  @Bind(R.id.rclv_posts)
  RecyclerView mMentionsRecyclerView;
  @Bind(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @Bind(R.id.pb_loadmore)
  ProgressBar mProgressBarLoadMore;
  @Bind(R.id.layout_no_data)
  LinearLayout mLinearLayoutNoData;

  TweetAdapter mMentionAdapter;
  List<Tweet> mMentions;
  TwitterApiClient mTwitterApi;
  MentionFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_mention, container, false);
  }


  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mPresenter = new MentionFragmentPresenterImpl(this);
    mTwitterApi = TwitterApplication.getRestClient();
    setEventListener(view);
    repairMentionRecyclerView();
    getMentions(0);
  }

  @Override
  public void setEventListener(View view) {
//    mMentionsRecyclerView = view.findViewById(R.id.rclv_posts);
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
  public void getMentions(final int page) {
    Log.d(Const.TAG, "Page : " + page);
    if (NetworkUtils.isNetworkAvailable(getActivity())) {
      if (page == 0) {
        mSwipeRefreshLayout.setRefreshing(true);
      } else {
        mProgressBarLoadMore.setVisibility(View.VISIBLE);
      }

      mTwitterApi.getHomeMention(page, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
          super.onSuccess(statusCode, headers, response);
          Log.d(Const.TAG, "Response : " + response.toString());
          List<Tweet> mentions = JsonHelper.convertJsonToListTweet(response);
//          // count time
          for (Tweet p : mMentions) {
            p.setCreate_at(DateTimeHelper.countTimeBetweenTwoDay(p.getCreate_at()));
          }
          if (page == 0) {
            mMentions.clear();

          }
          mMentions.addAll(mMentions);
          mMentionAdapter.notifyDataSetChanged();
          mMentionAdapter.notifyItemRangeInserted(
              mMentions.size() - mMentions.size(), mMentions.size());
          if (mMentions.size() == 0)
            showViewNoData(true);
          else
            showViewNoData(false);

//          for (TweetRealm p : mentions) {
//            Log.d(Const.TAG, "\nText : " + p.getText()
//                + "\nUser name : " + p.getUser().getName()
//                + "\nScreen name : " + p.getUser().getScreenName()
//                + "\nFavourite count : " + p.getUser().getFavouritesCount()
//                + "\nRetweet count " + p.getRetweetCount());
//          }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
          super.onFailure(statusCode, headers, throwable, errorResponse);
          Toast.makeText(getActivity(), "Failure : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
          showViewNoData(true);
        }
      });
    } else {
      Toast.makeText(getActivity(), "Network is not available", Toast.LENGTH_SHORT).show();
      mProgressBarLoadMore.setVisibility(View.INVISIBLE);
      mSwipeRefreshLayout.setRefreshing(false);
      showViewNoData(true);
    }
  }

  @Override
  public void repairMentionRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mMentions = new ArrayList<Tweet>();
    mMentionAdapter = new TweetAdapter(mMentions, getActivity());
    mMentionsRecyclerView.setAdapter(mMentionAdapter);
    mMentionsRecyclerView.setLayoutManager(layoutManager);
    mMentionsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        Log.d(Const.TAG, "Page : " + (page + 1));
        if (page + 1 < Const.MAX_PAGE) {
          getMentions(page + 1);
        } else {
          mProgressBarLoadMore.setVisibility(View.INVISIBLE);
        }
      }
    });
  }

  @Override
  public void onRefresh() {
    mPresenter.onRefreshMentions();
  }

  @Override
  public void showViewNoData(boolean show) {
    mProgressBarLoadMore.setVisibility(View.INVISIBLE);
    mSwipeRefreshLayout.setRefreshing(false);
    if (show) {
      mLinearLayoutNoData.setVisibility(View.VISIBLE);
      mMentionsRecyclerView.setVisibility(View.GONE);
    } else {
      mLinearLayoutNoData.setVisibility(View.GONE);
      mMentionsRecyclerView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public String getIconUrl() {
    if (mMentions != null && mMentions.size() > 0)
      return mMentions.get(0).getUser().getProfileImageUrl();
    return "";
  }

  @Override
  public void handleRefreshMentions() {
    getMentions(0);
  }
}
