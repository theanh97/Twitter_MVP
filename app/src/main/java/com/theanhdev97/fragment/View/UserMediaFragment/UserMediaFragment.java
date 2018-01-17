package com.theanhdev97.fragment.View.UserMediaFragment;

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
import com.loopj.android.http.RequestParams;
import com.theanhdev97.fragment.Presenter.UserMediaFragment.UserMediaFragmentPresenter;
import com.theanhdev97.fragment.Presenter.UserMediaFragment.UserMediaFragmentPresenterImpl;
import com.theanhdev97.fragment.View.UserActivity.UserActivity;
import com.theanhdev97.fragment.Adapter.MediaAdapter;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.Utils.EndlessRecyclerViewScrollListener;
import com.theanhdev97.fragment.Utils.JsonHelper;
import com.theanhdev97.fragment.Utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by DELL on 01/02/2018.
 */

public class UserMediaFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, UserMediaFragmentView {
  @Bind(R.id.rclv_medias)
  RecyclerView mMeidaRecyclerview;
  @Bind(R.id.swipe_refresh_layout)
  SwipeRefreshLayout mSwipeRefreshLayout;
  @Bind(R.id.pb_loadmore)
  ProgressBar mProgressBarLoadMore;
  @Bind(R.id.layout_no_data)
  LinearLayout mLinearLayoutNoData;

  MediaAdapter mMediaAdapter;
  List<Media> mMedias;
  TwitterApiClient mTwitterApi;
  UserMediaFragmentPresenter mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_user_media, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    mTwitterApi = TwitterApplication.getRestClient();
    setEventListener(view);
    repairPostRecyclerView();
    mPresenter = new UserMediaFragmentPresenterImpl(this);
    getTweets(0);
  }

  @Override
  public void showUserMedias(int page, List<Media> medias) {
    if (page == 0) {
      mMedias.clear();
    }
    mMedias.addAll(medias);
    mMediaAdapter.notifyDataSetChanged();
    mMediaAdapter.notifyItemRangeInserted(mMedias.size() - medias.size(), mMedias.size());

    if (mMedias.size() == 0)
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
      mMeidaRecyclerview.setVisibility(View.GONE);
    } else {
      mLinearLayoutNoData.setVisibility(View.GONE);
      mMeidaRecyclerview.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void setEventListener(View view) {
//    mMeidaRecyclerview = view.findViewById(R.id.rclv_medias);
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

  public void getTweets(final int page) {
    Log.d(Const.TAG, "Page : " + page);
    if (NetworkUtils.isNetworkAvailable(getActivity())) {
      if (page == 0) {
        mSwipeRefreshLayout.setRefreshing(true);
      } else {
        mProgressBarLoadMore.setVisibility(View.VISIBLE);
      }

      RequestParams params = new RequestParams();
      params.put("user_id", UserActivity.sUserInformation.getUserID());
      params.put("page", String.valueOf(page));
      mTwitterApi.getUserTimeLine(params, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
          super.onSuccess(statusCode, headers, response);

          Log.d(Const.TAG, response.toString());

          com.theanhdev97.fragment.Utils.Log.d("result123", response.toString());
          List<Tweet> listTweet = JsonHelper.convertJsonToListTweet(response);
          // count time
          if (page == 0) {
            mMedias.clear();
          }

          for (int i = 0; i < listTweet.size(); i++) {
            if (listTweet.get(i).getExtendedEntities() != null) {
              if (listTweet.get(i).getExtendedEntities().getMedia().size() > 0) {
                mMedias.add(listTweet.get(i).getExtendedEntities().getMedia().get(0));
              }
            }
          }
//          Log.d(Const.TAG, "Media size : " + mMedias.size());
//          for (Media media : mMedias) {
//            Log.d(Const.TAG, "\nType : " + media.getType()
//                + "\nURL : " + media.getMedia_url() +
//                "\nVideo info : " + media.getVideo_info());
//          }

          mMediaAdapter.notifyDataSetChanged();
          mMediaAdapter.notifyItemRangeInserted(mMedias.size() - listTweet.size(), mMedias.size());

          if (mMedias.size() == 0)
            showViewNoData(true);
          else
            showViewNoData(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
          super.onFailure(statusCode, headers, throwable, errorResponse);
          Log.d(Const.TAG, "Media Error response : " + errorResponse.toString());
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
  public void repairPostRecyclerView() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mMedias = new ArrayList<Media>();
    mMediaAdapter = new MediaAdapter(mMedias, getActivity());
    mMeidaRecyclerview.setAdapter(mMediaAdapter);
    mMeidaRecyclerview.setLayoutManager(layoutManager);
    mMeidaRecyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
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
  public void onRefresh() {
    mPresenter.onRefresh();
  }
}
