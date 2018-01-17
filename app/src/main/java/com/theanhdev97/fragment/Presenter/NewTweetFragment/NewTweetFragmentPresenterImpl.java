package com.theanhdev97.fragment.Presenter.NewTweetFragment;

import com.theanhdev97.fragment.Interactor.InteractorListener.OnPostNewTweetListener;
import com.theanhdev97.fragment.Interactor.TwitterInteractor;
import com.theanhdev97.fragment.Interactor.TwitterInteractorImpl;
import com.theanhdev97.fragment.View.NewTweetFragment.NewTweetFragment;
import com.theanhdev97.fragment.View.NewTweetFragment.NewTweetFragmentView;

/**
 * Created by DELL on 13/01/2018.
 */

public class NewTweetFragmentPresenterImpl implements NewTweetFragmentPresenter {
  NewTweetFragmentView mView;
  TwitterInteractor mTwitterInteractor;

  public NewTweetFragmentPresenterImpl(NewTweetFragment newTweetFragment) {
    this.mView = newTweetFragment;
    mTwitterInteractor = new TwitterInteractorImpl();
  }

  @Override
  public void onCloseDialogClicked() {
    mView.dismissNewTweetDialog();
  }

  @Override
  public void onNewTweetClicked() {
    if (mView.isNetworkAvailable()) {
      mView.showWaitingProgressDialog();
      String tweetContent = mView.getTweetContent();
      mTwitterInteractor.postNewTweet(tweetContent, new OnPostNewTweetListener() {
        @Override
        public void onSuccess() {
          mView.dismissWaitingProgressDialog();
          mView.callbackTweetSuccessfulToHomeActivity();
          mView.dismissNewTweetDialog();
        }

        @Override
        public void onFailure(String error) {
          mView.dismissWaitingProgressDialog();
          mView.showError(error);
        }
      });
    } else {
      mView.showError("");
    }
  }
}
