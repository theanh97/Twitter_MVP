package com.theanhdev97.fragment.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 05/01/2018.
 */

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
  private List<String> mTitles;
  private List<Fragment> mFragments;

  public FragmentPagerAdapter(FragmentManager fm) {
    super(fm);
    mTitles = new ArrayList<String>();
    mFragments = new ArrayList<Fragment>();
  }

  @Override
  public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getCount() {
    return mFragments.size();
  }

  public void addFragment(Fragment fragment, String title) {
    mFragments.add(fragment);
    mTitles.add(title);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }
}
