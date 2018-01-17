package com.theanhdev97.fragment.Application;

import android.app.Application;
import android.content.Context;

import com.theanhdev97.fragment.TwitterApi.TwitterApiClient;

/**
 * Created by DELL on 05/01/2018.
 */

public class TwitterApplication extends Application {
  private static Context sContext ;

  @Override
  public void onCreate() {
    super.onCreate();
    TwitterApplication.sContext = this;
  }

  public static TwitterApiClient getRestClient(){
    return (TwitterApiClient) TwitterApiClient.getInstance(TwitterApiClient.class,TwitterApplication.sContext);
  }
}
