package com.theanhdev97.fragment.View.UserActivity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.theanhdev97.fragment.Adapter.FragmentPagerAdapter;
import com.theanhdev97.fragment.Presenter.UserActivity.UserActivityPresenter;
import com.theanhdev97.fragment.Presenter.UserActivity.UserActivityPresenterImpl;
import com.theanhdev97.fragment.View.UserFollowersFragment.UserFollowersFragment;
import com.theanhdev97.fragment.View.UserMediaFragment.UserMediaFragment;
import com.theanhdev97.fragment.View.UserTimeLineFragment.UserTimelineFragment;
import com.theanhdev97.fragment.Model.User;
import com.theanhdev97.fragment.R;
import com.theanhdev97.fragment.Utils.CircleTransform;
import com.theanhdev97.fragment.Utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity
    implements UserActivityView {
  @Bind(R.id.imv_banner)
  ImageView mImageViewBanner;
  @Bind(R.id.imv_icon)
  ImageView mImageViewIcon;
  @Bind(R.id.tv_name)
  TextView mTextViewUserName;
  @Bind(R.id.tv_screen_name)
  TextView mTextViewScreenName;
  @Bind(R.id.tv_follower_count)
  TextView mTextViewFollowerCount;
  @Bind(R.id.tv_following_count)
  TextView mTextViewFollowingCount;

  @Bind(R.id.tabs)
  TabLayout mTabLayout;
  @Bind(R.id.vpg)
  ViewPager mViewPager;
  @Bind(R.id.toolbar)
  android.support.v7.widget.Toolbar mToolbar;

  FragmentPagerAdapter mFragmentPagerAdapter;
  public static User sUserInformation;
  private UserActivityPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);
    ButterKnife.bind(this);
    mPresenter = new UserActivityPresenterImpl(this);
    initUserInformation();
    initToolbar();
    loadDataToView();
    initViewPagerFragmentAndTabhost();
  }

  @Override
  public void initToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setTitle(sUserInformation.getName());
    getSupportActionBar().setDisplayShowTitleEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mPresenter.onClickBackNavigation();
      }
    });
  }

  @Override
  public void initUserInformation() {
    sUserInformation = (User) getIntent().getBundleExtra(Const.KEY_BUNDLE).getSerializable(Const
        .KEY_USER_INFORMATION);
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void loadDataToView() {
    mTextViewUserName.setText(sUserInformation.getName());
    mTextViewScreenName.setText(sUserInformation.getScreenName());

    int followersCounts = sUserInformation.getFollowersCount();
    int followingCounts = sUserInformation.getFollowingCount();

    Spannable followerText = new SpannableString(followersCounts + " Followers");
    Spannable followingText = new SpannableString(followingCounts + " Followings");

    followerText.setSpan(new ForegroundColorSpan(Color.parseColor("#35B0AB")), 0, followerText.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    followingText.setSpan(new ForegroundColorSpan(Color.parseColor("#35B0AB")), 0,
        followingText.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    mTextViewFollowerCount.setText(followerText);
    mTextViewFollowingCount.setText(followingText);

    Glide.with(this)
        .load(sUserInformation.getProfileBannerUrl())
        .placeholder(R.drawable.backgrounddefault)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(mImageViewBanner);

    Glide.with(this)
        .load(sUserInformation.getProfileImageUrl())
        .centerCrop()
        .transform(new CircleTransform(this))
        .placeholder(android.R.drawable.ic_menu_info_details)
        .into(mImageViewIcon);
  }

  @Override
  public void initViewPagerFragmentAndTabhost() {
    mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
    mFragmentPagerAdapter.addFragment(new UserTimelineFragment(), "Tweets");
    mFragmentPagerAdapter.addFragment(new UserMediaFragment(), "Media");
    mFragmentPagerAdapter.addFragment(new UserFollowersFragment(), "Followers");
    mViewPager.setAdapter(mFragmentPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  public void backActivity() {
    this.finish();
  }
}
