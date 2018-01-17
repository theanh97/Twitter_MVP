package com.theanhdev97.fragment.Utils;

import com.theanhdev97.fragment.Model.Entities;
import com.theanhdev97.fragment.Model.ExtendedEntities;
import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.MediaUrl;
import com.theanhdev97.fragment.Model.Realm.EntitiesRealm;
import com.theanhdev97.fragment.Model.Realm.ExtendedEntitiesRealm;
import com.theanhdev97.fragment.Model.Realm.MediaRealm;
import com.theanhdev97.fragment.Model.Realm.MediaUrlRealm;
import com.theanhdev97.fragment.Model.Realm.TweetRealm;
import com.theanhdev97.fragment.Model.Realm.UserFollowersRealm;
import com.theanhdev97.fragment.Model.Realm.UserRealm;
import com.theanhdev97.fragment.Model.Realm.VariantsRealm;
import com.theanhdev97.fragment.Model.Realm.VideoInfoRealm;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.Model.UserFollowers;
import com.theanhdev97.fragment.Model.Variants;
import com.theanhdev97.fragment.Model.VideoInfo;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by DELL on 11/01/2018.
 */

public class RealmHelper {
  public static ArrayList<TweetRealm> convertToListTweetRealm(List<Tweet> tweets) {
    ArrayList<TweetRealm> realms = new ArrayList<TweetRealm>();
    for (Tweet tweet : tweets) {
      TweetRealm realm = new TweetRealm();
      // created_at
      realm.setCreate_at(tweet.getCreate_at());
      // text
      realm.setText(tweet.getText());
      // user
      UserRealm userRealm = new UserRealm();
      User user = tweet.getUser();
      userRealm.setFavouritesCount(user.getFavouritesCount());
      userRealm.setFollowersCount(user.getFollowersCount());
      userRealm.setFollowingCount(user.getFollowingCount());
      userRealm.setName(user.getName());
      userRealm.setProfileBannerUrl(user.getProfileBannerUrl());
      userRealm.setProfileImageUrl(user.getProfileImageUrl());
      userRealm.setScreenName(user.getScreenName());
      userRealm.setUserID(user.getUserID());
      realm.setUser(userRealm);

      // entities
      EntitiesRealm entitiesRealm = new EntitiesRealm();
      RealmList<MediaUrlRealm> mediaUrlRealms = new RealmList<MediaUrlRealm>();
      Entities entities = tweet.getEntities();
      if (entities.getMediaUrls() != null && entities.getMediaUrls().size() > 0) {
        for (MediaUrl mediaUrl : entities.getMediaUrls()) {
          MediaUrlRealm mediaUrlRealm = new MediaUrlRealm();
          mediaUrlRealm.setMediaUrl(mediaUrl.getMediaUrl());
          mediaUrlRealms.add(mediaUrlRealm);
        }
      }
      entitiesRealm.setMediaUrls(mediaUrlRealms);
      realm.setEntities(entitiesRealm);

      // retweet count
      realm.setRetweetCount(tweet.getRetweetCount());
      // favourite count
      realm.setFavoriteCount(tweet.getFavoriteCount());
      // extended entities
      ExtendedEntitiesRealm extendedEntitiesRealm = new ExtendedEntitiesRealm();
      RealmList<MediaRealm> mediaRealms = new RealmList<MediaRealm>();

      ExtendedEntities extendedEntities = tweet.getExtendedEntities();
      if (extendedEntities != null) {
        List<Media> medias = extendedEntities.getMedia();
        if (medias != null && medias.size() > 0) {
          for (Media media : medias) {
            MediaRealm mediaRealm = new MediaRealm();
            mediaRealm.setMedia_url(media.getMedia_url());
            mediaRealm.setType(media.getType());
            // video info realm
            VideoInfoRealm videoInfoRealm = new VideoInfoRealm();
            VideoInfo videoInfo = media.getVideo_info();
            if (videoInfo != null) {
              RealmList<VariantsRealm> variantsRealms = new RealmList<VariantsRealm>();
              List<Variants> variants = videoInfo.getVariants();
              for (Variants v : variants) {
                VariantsRealm variantsRealm = new VariantsRealm();
                variantsRealm.setContent_type(v.getContent_type());
                variantsRealm.setUrl(v.getUrl());
                variantsRealms.add(variantsRealm);
              }

              videoInfoRealm.setVariants(variantsRealms);
            }

            mediaRealm.setVideo_info(videoInfoRealm);
            mediaRealms.add(mediaRealm);
          }
        }
        extendedEntitiesRealm.setMedia(mediaRealms);
      }

      realm.setExtendedEntities(extendedEntitiesRealm);

      // Add to list Realms
      realms.add(realm);
    }
    return realms;
  }

  public static ArrayList<Tweet> convertToListTweet(ArrayList<TweetRealm> tweetRealms) {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    for (TweetRealm tweetRealm : tweetRealms) {
      tweets.add(toTweet(tweetRealm));
    }
    return tweets;
  }

  public static Media toMedia(MediaRealm m) {
    VideoInfo videoInfo = toVideoInfo(m.getVideo_info());
    return new Media(m.getType(), m.getMedia_url(), videoInfo);
  }


  public static Tweet toTweet(TweetRealm t){
    return new Tweet(
        t.getCreate_at(),
        t.getText(),
        toUser(t.getUser()),
        toEntities(t.getEntities()),
        t.getRetweetCount(),
        t.getFavoriteCount(),
        t.getFavouritesCount(),
        toExtendedEntities(t.getExtendedEntities()));
  }


  public static ExtendedEntities toExtendedEntities(ExtendedEntitiesRealm e){
    List<Media> mediaList = new ArrayList<Media>();
    for(MediaRealm mediaRealm : e.getMedia()){
      mediaList.add(toMedia(mediaRealm));
    }
    return new ExtendedEntities(mediaList);
  }


  public static UserFollowers toUserFollowers(UserFollowersRealm u){
    ArrayList<User> userArrayList = new ArrayList<User>();
    for(UserRealm user : u.getUsers()){
      userArrayList.add(toUser(user));
    }
    return new UserFollowers(userArrayList);
  }


  public static Entities toEntities(EntitiesRealm e) {
    ArrayList<MediaUrl> mediaUrlArrayList = new ArrayList<MediaUrl>();
    for (MediaUrlRealm mediaUrlRealm : e.getMediaUrls()) {
      mediaUrlArrayList.add(toMediaUrl(mediaUrlRealm));
    }
    return new Entities(mediaUrlArrayList);
  }

  public static User toUser(UserRealm u){
    return new User(
        u.getName(),
        u.getProfileImageUrl(),
        u.getScreenName(),
        u.getFavouritesCount(),
        u.getProfileBannerUrl(),
        u.getFollowersCount(),
        u.getFollowingCount(),
        u.getUserID());
  }

  public static Variants toVariants(VariantsRealm v){
    return new Variants(v.getContent_type(),v.getUrl());
  }


  public static MediaUrl toMediaUrl(MediaUrlRealm m){
    return new MediaUrl(m.getMediaUrl());
  }

  public static VideoInfo toVideoInfo(VideoInfoRealm v){
    ArrayList<Variants> variantsArrayList = new ArrayList<Variants>();
    for(VariantsRealm variantsRealm : v.getVariants()){
      variantsArrayList.add(toVariants(variantsRealm));
    }
    return new VideoInfo(variantsArrayList);
  }
}
