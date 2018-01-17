package com.theanhdev97.fragment.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theanhdev97.fragment.Model.Mention;
import com.theanhdev97.fragment.Model.Tweet;
import com.theanhdev97.fragment.Model.User;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by DELL on 03/01/2018.
 */

public class JsonHelper {
  public static List<Tweet> convertJsonToListTweet(JSONArray response){
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Tweet>>() {}.getType();
    List<Tweet> listTweet = gson.fromJson(String.valueOf(response), listType);
    return listTweet;
  }

  public static List<Mention> convertJsonToListMention(JSONArray response){
    Gson gson = new Gson();
    Type listType = new TypeToken<List<Mention>>() {}.getType();
    List<Mention> mentions = gson.fromJson(String.valueOf(response), listType);
    return mentions;
  }

  public static List<User> convertJsonToListUsers(JSONArray response){
    Gson gson = new Gson();
    Type listType = new TypeToken<List<User>>() {}.getType();
    List<User> users = gson.fromJson(String.valueOf(response), listType);
    return users;
  }
}
