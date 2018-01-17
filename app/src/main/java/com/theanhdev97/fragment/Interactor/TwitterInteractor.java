package com.theanhdev97.fragment.Interactor;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetHomeTimeLine;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserMediaListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUserTimeLineListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnGetUsersFollowerListener;
import com.theanhdev97.fragment.Interactor.InteractorListener.OnPostNewTweetListener;

/**
 * Created by DELL on 11/01/2018.
 */

public interface TwitterInteractor {
  void clearAccessToken();

  void getUserInfo(OnGetUserListener listener);

  void getHomeTimeLine(int page, OnGetHomeTimeLine listener);

  void postNewTweet(String tweetContent, OnPostNewTweetListener listener);

  void getUsersFollower(long userID, int page, final OnGetUsersFollowerListener listener);

  void getUserMedia(long userID, int page, final OnGetUserMediaListener listener);

  void getUserTimeLine(long userID , int page , final OnGetUserTimeLineListener listener);
}
