package com.theanhdev97.fragment.Model;

import com.google.gson.annotations.SerializedName;
import com.theanhdev97.fragment.Model.Realm.VariantsRealm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class VideoInfo {

  public VideoInfo(List<Variants> variants) {
    this.variants = variants;
  }

  public List<Variants> getVariants() {
    return variants;
  }

  @SerializedName("variants")
  private List<Variants> variants;

  public void setVariants(List<Variants> variants) {
    this.variants = variants;
  }
}

