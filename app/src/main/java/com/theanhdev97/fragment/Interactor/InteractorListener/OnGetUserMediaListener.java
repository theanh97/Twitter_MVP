package com.theanhdev97.fragment.Interactor.InteractorListener;

import com.theanhdev97.fragment.Model.Media;
import com.theanhdev97.fragment.Model.Tweet;

import java.util.List;

/**
 * Created by DELL on 13/01/2018.
 */

public interface OnGetUserMediaListener {
  void onSuccess(List<Media> medias);

  void onFailure(String error);
}
