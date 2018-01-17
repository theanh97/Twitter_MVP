package com.theanhdev97.fragment.Interactor;

import com.theanhdev97.fragment.Model.Tweet;

import java.util.List;

/**
 * Created by DELL on 14/01/2018.
 */

public interface RealmInteractor {
  void clearTweetsInLocalDataBase();

  List<Tweet> getTweetsFromLocalDataBase();

  void writeTweetsToLocalDataBase(List<Tweet> tweets);
}
