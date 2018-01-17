package com.theanhdev97.fragment.TwitterApi;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theanhdev97.fragment.Utils.Const;

/**
 * Created by DELL on 01/01/2018.
 */

public class TwitterApiClient extends OAuthBaseClient {

  public static final BaseApi REST_API_INSTANCE = TwitterApi.instance();
  public static final String REST_URL = "https://api.twitter.com/1.1";
  public static final String REST_CONSUMER_KEY = "pqKNiow0lIdcNfKKmnOv10HGh";
  public static final String REST_CONSUMER_SECRET = "2fR2erd8TjEnH4M1pM7MTXDzp4zjYFO3Hu5WQ5S3BD3mBhB2xK";
  //  public static final String REST_CALLBACK_URL = "x-oauthflow-twitter://twitter.com";
  public static final String REST_CALLBACK_URL = "oauth://twitter.com";

  public TwitterApiClient(Context context) {
    super(context, REST_API_INSTANCE, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
  }

  public void getHomeTimeline(int page, JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/home_timeline.json");
    RequestParams params = new RequestParams();
    params.put("page", String.valueOf(page));
    client.get(apiUrl, params, handler);
  }

  public void getHomeMention(int page, JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/mentions_timeline.json");
    RequestParams params = new RequestParams();
    params.put("page", String.valueOf(page));
    client.get(apiUrl, params, handler);
  }

  public void getUserInformation(JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("account/verify_credentials.json");
    RequestParams params = new RequestParams();
    client.get(apiUrl, params, handler);
  }

  public void postNewTweet(String tweetContent, JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("/statuses/update.json");
    RequestParams params = new RequestParams();
    params.put("status", tweetContent);
    client.post(apiUrl, params, handler);
  }

  public void getUserTimeLine(RequestParams params, JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/user_timeline.json");
    client.get(apiUrl, params, handler);
  }

  public void getUserFollowers(RequestParams params, JsonHttpResponseHandler handler) {
    String apiUrl = getApiUrl("followers/list.json");
    client.get(apiUrl, params, handler);
  }
}
