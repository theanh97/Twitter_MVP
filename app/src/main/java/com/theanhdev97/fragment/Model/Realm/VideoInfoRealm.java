package com.theanhdev97.fragment.Model.Realm;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DELL on 08/01/2018.
 */

public class VideoInfoRealm extends RealmObject {

  public RealmList<VariantsRealm> getVariants() {
    return variants;
  }

  @SerializedName("variants")
  private RealmList<VariantsRealm> variants;



  public void setVariants(RealmList<VariantsRealm> variants) {
    this.variants = variants;
  }
}

