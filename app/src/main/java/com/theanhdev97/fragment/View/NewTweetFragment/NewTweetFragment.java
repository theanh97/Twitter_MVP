package com.theanhdev97.fragment.View.NewTweetFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theanhdev97.fragment.Presenter.NewTweetFragment.NewTweetFragmentPresenter;
import com.theanhdev97.fragment.Presenter.NewTweetFragment.NewTweetFragmentPresenterImpl;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Utils.CircleTransform;
import com.theanhdev97.fragment.Utils.NetworkUtils;
import com.theanhdev97.fragment.View.CallbackHomeInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DELL on 06/01/2018.
 */

public class NewTweetFragment extends android.support.v4.app.DialogFragment implements View.OnClickListener,
    NewTweetFragmentView {
  @Bind(R.id.imv_icon)
  ImageView mImageViewIcon;
  @Bind(R.id.tv_content_tweet)
  EditText mTextViewTweetContent;
  @Bind(R.id.btn_close)
  Button mButtonClose;
  @Bind(R.id.btn_tweet)
  Button mButtonTweet;

  ProgressDialog mProgressDialogWaiting;
  CallbackHomeInterface mCallback;
  NewTweetFragmentPresenter mPresenter;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context != null) {
      mCallback = (CallbackHomeInterface) context;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mCallback = null;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_new_tweet, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    ButterKnife.bind(this,view);
    mPresenter = new NewTweetFragmentPresenterImpl(this);
    setIcon();
    initProgressDialog();
    setEventListener();
  }

  @Override
  public void initProgressDialog() {
    mProgressDialogWaiting = new ProgressDialog(getActivity());
    mProgressDialogWaiting.setCancelable(false);
    mProgressDialogWaiting.setIndeterminate(true);
    mProgressDialogWaiting.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    mProgressDialogWaiting.setTitle("Waiting ...");
  }

  @Override
  public void setEventListener() {
    mButtonClose.setOnClickListener(this);
    mButtonTweet.setOnClickListener(this);
  }

  @Override
  public void setIcon() {
    String iconUrl = getTag();
    Log.d("fatal", "Icon url :" + iconUrl);
    Glide.with(getActivity())
        .load(iconUrl)
        .centerCrop()
        .transform(new CircleTransform(getActivity()))
        .placeholder(android.R.drawable.ic_menu_info_details)
        .into(mImageViewIcon);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_close:
        mPresenter.onCloseDialogClicked();
        break;
      case R.id.btn_tweet:
        mPresenter.onNewTweetClicked();
        break;
      default:
        break;
    }
  }


  @Override
  public String getTweetContent() {
    return mTextViewTweetContent.getText().toString();
  }

  @Override
  public void callbackTweetSuccessfulToHomeActivity() {
    mCallback.callbackTweetSuccessfull();
  }

  @Override
  public void showError(String error) {
    if (error.isEmpty()) {
      Toast.makeText(getActivity(), "Error : " + error, Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(getActivity(), "Network is not available", Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean isNetworkAvailable() {
    if(NetworkUtils.isNetworkAvailable(getActivity()))
      return true;
    return false;
  }

  @Override
  public void dismissNewTweetDialog() {
    getDialog().dismiss();
  }

  @Override
  public void dismissWaitingProgressDialog() {
    mProgressDialogWaiting.dismiss();
  }

  @Override
  public void showWaitingProgressDialog() {
    mProgressDialogWaiting.show();
  }
}
