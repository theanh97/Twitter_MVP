package com.theanhdev97.fragment.Interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetHomeTimeLine;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserMediaListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserTimeLineListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUsersFollowerListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnPostNewTweetListener;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Model.UserFollowers;
import com.theanhdev97.fragment.Utils.DateTimeHelper;
import com.theanhdev97.fragment.Utils.JsonHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DELL on 11/01/2018.
 */

public class TwitterInteractorImpl implements TwitterInteractor {

  @Override
  public void clearAccessToken() {
    TwitterApplication.getRestClient().clearAccessToken();
  }

  @Override
  public void getUserInfo(final OnGetUserListener listener) {
    TwitterApplication.getRestClient().getUserInformation(new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        User user = new Gson().fromJson(response.toString(), User.class);
        listener.onSuccess(user);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.getMessage());
      }
    });
  }

  @Override
  public void postNewTweet(String tweetContent, final OnPostNewTweetListener listener) {
    TwitterApplication.getRestClient().postNewTweet(tweetContent, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        listener.onSuccess();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.getMessage());
      }
    });
  }

  @Override
  public void getUsersFollower(long userID, int page, final OnGetUsersFollowerListener listener) {
    RequestParams params = new RequestParams();
    params.put("user_id", userID);
    params.put("page", String.valueOf(page));
    TwitterApplication.getRestClient().getUserFollowers(params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        UserFollowers userFollowers =
            new Gson().fromJson(response.toString(), UserFollowers.class);
        listener.onSuccess(userFollowers);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject
          errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.getMessage());
      }
    });
  }

  @Override
  public void getUserMedia(long userID, int page, final OnGetUserMediaListener listener) {
    RequestParams params = new RequestParams();
    params.put("user_id", userID);
    params.put("page", String.valueOf(page));
    TwitterApplication.getRestClient().getUserTimeLine(params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        List<Tweet> listTweet = JsonHelper.convertJsonToListTweet(response);
        List<Media> medias = new ArrayList<Media>();
        for (int i = 0; i < listTweet.size(); i++) {
          if (listTweet.get(i).getExtendedEntities() != null) {
            if (listTweet.get(i).getExtendedEntities().getMedia().size() > 0) {
              medias.add(listTweet.get(i).getExtendedEntities().getMedia().get(0));
            }
          }
        }
        listener.onSuccess(medias);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.toString());
      }
    });
  }

  @Override
  public void getUserTimeLine(long userID, int page, final OnGetUserTimeLineListener listener) {
    RequestParams params = new RequestParams();
    params.put("user_id", userID);
    params.put("page", String.valueOf(page));
    TwitterApplication.getRestClient().getUserTimeLine(params, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        List<Tweet> tweets = JsonHelper.convertJsonToListTweet(response);
        for (Tweet p : tweets) {
          p.setCreate_at(DateTimeHelper.countTimeBetweenTwoDay(p.getCreate_at()));
        }
        listener.onSuccess(tweets);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.toString());
      }
    });
  }

  @Override
  public void getHomeTimeLine(int page, final OnGetHomeTimeLine listener) {
    TwitterApplication.getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        List<Tweet> tweets = JsonHelper.convertJsonToListTweet(response);
        for (Tweet p : tweets) {
          p.setCreate_at(DateTimeHelper.countTimeBetweenTwoDay(p.getCreate_at()));
        }
        listener.onSuccess(tweets);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        listener.onFailure(throwable.toString());
      }
    });
  }
}
