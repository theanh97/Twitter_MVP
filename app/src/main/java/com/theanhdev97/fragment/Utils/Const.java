package com.theanhdev97.fragment.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by DELL on 01/02/2018.
 */

public class Const {
  public static final String TAG = "tag123";

  public static final int MAX_PAGE = 10;

  public static int POST_CURRENT_PAGE = 0;

  public static String KEY_USER_INFORMATION = "101";

  public static String KEY_BUNDLE = "100";

  public static String FIRST_LOGIN = "first_login";

  public static boolean isFirstLogin(Context context) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    boolean isFirstLogin = prefs.getBoolean(FIRST_LOGIN, false);
    if (isFirstLogin) {
      SharedPreferences.Editor editor = prefs.edit();
      editor.putBoolean(FIRST_LOGIN, false);
      editor.commit();
      return true;
    } else {
      return false;
    }
  }
}
