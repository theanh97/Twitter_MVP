package com.theanhdev97.fragment.View.HomeActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.theanhdev97.fragment.Adapter.FragmentPagerAdapter;
import com.theanhdev97.fragment.Presenter.HomeActivity.HomeActivityPresenter;
import com.theanhdev97.fragment.Presenter.HomeActivity.HomeActivityPresenterImpl;
import com.theanhdev97.fragment.View.UserActivity.UserActivity;
import com.theanhdev97.fragment.View.CallbackHomeInterface;
import com.theanhdev97.fragment.View.LoginFragment.LoginFragment;
import com.theanhdev97.fragment.View.MentionFragment.MentionFragment;
import com.theanhdev97.fragment.View.NewTweetFragment.NewTweetFragment;
import com.theanhdev97.fragment.View.HomeTimeLineFragment.HomeTimelineFragment;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Application.TwitterApplication;
import com.theanhdev97.fragment.Utils.Const;
import com.theanhdev97.fragment.Utils.NetworkUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
    implements LoginFragment.OnLoginFragmentInteraction,
    CallbackHomeInterface,
    HomeActivityView {

  @Bind(R.id.toolbar)
  Toolbar mToolbar;
  @Bind(R.id.tabs)
  TabLayout mTabLayout;
  @Bind(R.id.vpg)
  ViewPager mViewPager;
  @Bind(R.id.fr_container)
  FrameLayout frLogin;

  HomeActivityPresenter mPresenter;
  ProgressDialog mProgressDialogWaiting;

  FragmentPagerAdapter mFragmentPagerAdapter;
  Menu mMenu;

  static boolean sFirstLogin = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    ButterKnife.bind(this);
    mPresenter = new HomeActivityPresenterImpl(this);
  }

  @Override
  public void initViewPagerFragmentAndTabhost() {
    mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
    mFragmentPagerAdapter.addFragment(new HomeTimelineFragment(), "Home");
    mFragmentPagerAdapter.addFragment(new MentionFragment(), "Mention");
    mViewPager.setAdapter(mFragmentPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  public void initProgressDialog() {
    mProgressDialogWaiting = new ProgressDialog(this);
    mProgressDialogWaiting.setCancelable(false);
    mProgressDialogWaiting.setIndeterminate(true);
    mProgressDialogWaiting.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    mProgressDialogWaiting.setTitle("Waiting ...");
  }

  @Override
  public void initToolbar() {
    setSupportActionBar(mToolbar);
    mToolbar.setTitleTextColor(Color.BLACK);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.home_menu, menu);

    mMenu = menu;

    Drawable postIcon = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
    postIcon.mutate();
    postIcon.setColorFilter(getResources().getColor(R.color.color_item_menu), PorterDuff.Mode
        .SRC_IN);

    Drawable infoIcon = menu.getItem(1).getIcon(); // change 0 with 1,2 ...
    infoIcon.mutate();
    infoIcon.setColorFilter(getResources().getColor(R.color.color_item_menu), PorterDuff.Mode
        .SRC_IN);

    Drawable logout = menu.getItem(2).getIcon(); // change 0 with 1,2 ...
    logout.mutate();
    logout.setColorFilter(getResources().getColor(R.color.color_item_menu), PorterDuff.Mode
        .SRC_IN);
    if (Const.isFirstLogin(this))
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          showMenu(false);
        }
      }, 50);
    else {
      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          if (!TwitterApplication.getRestClient().isAuthenticated()) {
            showMenu(false);
          }
        }
      }, 50);
    }
    return true;
  }

  @Override
  public void showMenu(final boolean isShow) {
    if (mMenu != null)
      if (isShow) {
        Log.d(Const.TAG, "Authenticated");
        mMenu.getItem(0).setVisible(true);
        mMenu.getItem(1).setVisible(true);
        mMenu.getItem(2).setVisible(true);
      } else {
        Log.d(Const.TAG, "Not authenticated");
        mMenu.getItem(0).setVisible(false);
        mMenu.getItem(1).setVisible(false);
        mMenu.getItem(2).setVisible(false);
      }
  }

  @Override
  public void replaceFragment(android.support.v4.app.Fragment fragment) {
    if (fragment instanceof LoginFragment) {
      mTabLayout.setVisibility(ViewPager.GONE);
      mViewPager.setVisibility(ViewPager.GONE);
      frLogin.setVisibility(View.VISIBLE);
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fr_container, fragment)
          .commit();
    } else {
      mTabLayout.setVisibility(ViewPager.VISIBLE);
      mViewPager.setVisibility(ViewPager.VISIBLE);
      frLogin.setVisibility(View.GONE);

    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_info:
//        handleActionUserInfo();
        mPresenter.onClickMenuInfo();
        break;
      case R.id.action_post:
        mPresenter.onClickMenuNewTweet();
        break;
      case R.id.action_logout:
        mPresenter.onClickMenuLogOut();
        break;
      default:
        break;
    }
    return true;
  }

  @Override
  public void connectSuccess() {
    mPresenter.onConnectSuccess();
//    showMenu(true);
//    replaceFragment(new HomeTimelineFragment());
  }

  @Override
  public void showLogoutView() {
    LoginFragment loginFragment = new LoginFragment();
    replaceFragment(loginFragment);
    showMenu(false);
  }

  @Override
  public void showLogOutDialog() {
    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
    builder1.setMessage("Do you want to log out Twitter ?");
    builder1.setCancelable(true);
    builder1.setPositiveButton(
        "Yes",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            mPresenter.handlingLogOutTwitter();
          }
        });

    builder1.setNegativeButton(
        "No",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });

    AlertDialog alert11 = builder1.create();
    alert11.show();
  }

  @Override
  public void showNewTweetDialog(String profileImageUrl) {
    new NewTweetFragment().show(getSupportFragmentManager(), profileImageUrl);
  }


  @Override
  public void callbackTweetSuccessfull() {
    mPresenter.onCallBackTweetSuccessFull();
  }

  @Override
  public void showWaitingDialog() {
    mProgressDialogWaiting.show();
  }

  @Override
  public void dismissWaitingDialog() {
    mProgressDialogWaiting.dismiss();
  }

  @Override
  public void refreshHomeTimeLineFragment() {
    HomeTimelineFragment fragment = (HomeTimelineFragment) mFragmentPagerAdapter.getItem(0);
    fragment.callFromHomeToRefreshListTweets();
  }

  @Override
  public void refreshMentionFragment() {
    MentionFragment fragment = (MentionFragment) mFragmentPagerAdapter.getItem(1);
    fragment.getMentions(0);
  }

  @Override
  public int getCurrentFragmentPosition() {
    return mViewPager.getCurrentItem();
  }

  @Override
  public void callBackUserActivity(User userInformation) {
    mPresenter.onCallBackShowUserActivity(userInformation);
  }

  @Override
  public void navigateToUserActivity(User userInformation) {
    Intent i = new Intent(HomeActivity.this, UserActivity.class);
    Bundle bundle = new Bundle();
    bundle.putSerializable(Const.KEY_USER_INFORMATION, userInformation);
    i.putExtra(Const.KEY_BUNDLE, bundle);
    startActivity(i);
  }

  @Override
  public void callbackLogoutToHideMenu() {

  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    mMenu = menu;
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean checkNetworkIsAvailable() {
    if (NetworkUtils.isNetworkAvailable(this))
      return true;
    else
      showErrorMessage("Network is not available !!!");
    return false;
  }

  @Override
  public void showErrorMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
