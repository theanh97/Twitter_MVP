package com.theanhdev97.fragment.Interactor;

import android.content.Context;
import android.util.Log;

import com.theanhdev97.fragment.Model.Realm.TweetRealm;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.Utils.RealmHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by DELL on 12/01/2018.
 */

public class RealmInteractorImpl implements RealmInteractor {
  private Realm mRealm;

  public RealmInteractorImpl(Context context) {
    RealmConfiguration config = new RealmConfiguration
        .Builder(context)
        .name("Tweet_1")
        .deleteRealmIfMigrationNeeded()
        .build();
//    mRealm = Realm.getInstance(
//        new RealmConfiguration.Builder(context)
//            .name("Tweet_1")
//            .build());
    mRealm = Realm.getInstance(config);
  }

  @Override
  public void clearTweetsInLocalDataBase() {
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        RealmResults<TweetRealm> result = realm.where(TweetRealm.class).findAll();
        result.clear();
      }
    });
  }

  @Override
  public List<Tweet> getTweetsFromLocalDataBase() {

    RealmResults<TweetRealm> results = mRealm.where(TweetRealm.class).findAll();
    ArrayList<TweetRealm> tweetRealms = new ArrayList<TweetRealm>();
    for (TweetRealm tweetRealm : results) {
      tweetRealms.add(tweetRealm);
    }
    ArrayList<Tweet> tweets = RealmHelper.convertToListTweet(tweetRealms);
    return tweets;
  }

  @Override
  public void writeTweetsToLocalDataBase(List<Tweet> tweets) {
    final ArrayList<TweetRealm> tweetRealms = RealmHelper.convertToListTweetRealm(tweets);
    mRealm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        for (TweetRealm tweetRealm : tweetRealms)
          mRealm.copyToRealm(tweetRealm);
      }
    });
  }
}
