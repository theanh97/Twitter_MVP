package com.theanhdev97.fragment.Presenter.MentionFragment;

import com.theanhdev97.fragment.View.MentionFragment.MentionFragment;
import com.theanhdev97.fragment.View.MentionFragment.MentionFragmentView;

/**
 * Created by DELL on 13/01/2018.
 */

public class MentionFragmentPresenterImpl implements MentionFragmentPresenter {
  MentionFragmentView mView;

  public MentionFragmentPresenterImpl(MentionFragment mentionFragment) {
    this.mView = mentionFragment;
  }

  @Override
  public void onRefreshMentions() {
    mView.handleRefreshMentions();
  }
}
