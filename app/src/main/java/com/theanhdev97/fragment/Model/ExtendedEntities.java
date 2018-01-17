package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class ExtendedEntities {
  public static final int TYPE_PHOTO=0;
  public static final int TYPE_VIDEO=1;
  public static final int TYPE_TEXT=2;

  public ExtendedEntities(List<Media> media) {
    this.media = media;
  }

  public List<Media> getMedia() {
    return media;
  }

  @SerializedName("media")
  private List<Media> media;

  public void setMedia(List<Media> media) {
    this.media = media;
  }

  public ExtendedEntities() {
    super();
  }

  private static int clasifyTypeKey(String type){
    switch (type){
      case "photo":{
        return TYPE_PHOTO;
      }
      case "animated_gif":{
        return  TYPE_VIDEO;
      }
      case "video":
        return TYPE_VIDEO;
      default:
        return TYPE_TEXT;
    }
  }
  private static String clasifyTypeValue(String type,JSONObject jsonObject){
    switch (type){
      case "photo":{
        return getUrlForPhoto(jsonObject);
      }
      case "animated_gif":{
        return  getUrlForVideo(jsonObject);
      }
      case "video":{
        return  getUrlForVideo(jsonObject);
      }
      default:
        return "";
    }
  }

  private static String getUrlForPhoto(JSONObject jsonObject){
    String media_url="";
    try {
      media_url = jsonObject.has("media_url") ? jsonObject.getString("media_url") : "";
    }catch (JSONException e){
      e.printStackTrace();
    }
    return media_url;
  }
  private static String getUrlForVideo(JSONObject jsonObject){
    String media_url="";
    try {
      if (jsonObject.getJSONObject("video_info").has("variants")){
        JSONArray jsonArray=jsonObject.getJSONObject("video_info").getJSONArray("variants");
        for (int i=0;i<jsonArray.length();i++){
          JSONObject variants=jsonArray.getJSONObject(i);
          if (variants.has("content_type")){
            String content_type=variants.getString("content_type");
            if (content_type=="video\\/mp4"){
              if (variants.has("url")){
                media_url=variants.getString("url");
              }
            }
          }
        }
      }
    }catch (JSONException e){
      e.printStackTrace();
    }
    return media_url;
  }
}
