package com.theanhdev97.fragment.Utils;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 08/01/2018.
 */

public class TextViewHelper {
  public static HashMap<Integer, Integer> getTagOrLinkPosition(String text) {
    HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

//    // check Web url :
//    int index = -1;
//    index = text.indexOf("http");
//    if (index != -1) {
//      for (int i = index; i < text.length(); i++) {
//        if (text.charAt(i) == ' ' || i == (text.length() - 1)) {
//          hashMap.put(index, i);
//          break;
//        }
//      }
//    }

    // Check Tag
    int startIndex = -1;

    for (int i = 0; i < text.length(); i++) {
      if (startIndex != -1 &&
          (text.charAt(i) == '@' || text.charAt(i) == ' ' || (i == text.length() - 1))) {
//        hashMap.put(startIndex, i - 1);
        hashMap.put(startIndex, i);
        startIndex = -1;
      } else {
        if (text.charAt(i) == '@') {
          startIndex = i;
        }
      }
    }
    return hashMap;
  }

  public static String getStringByIndex(String text, int begin, int end) {
    StringBuilder str = new StringBuilder();
    for (int i = begin; i <= end; i++) {
      str.append(text.charAt(i));
    }
    return str.toString();
  }

  public static void makeUrlHighlightAndClickable(String text, TextView textView) {
    // check Web url :
    int startUrlIndex = -1;
    int finishUrlIndex = -1;
    startUrlIndex = text.indexOf("http");
    if (startUrlIndex != -1) {
      for (int i = startUrlIndex; i < text.length(); i++) {
        if (text.charAt(i) == ' ' || i == (text.length() - 1)) {
          finishUrlIndex = i;
          break;
        }
      }
    }

    String result = text;
    StringBuilder strBuilder = new StringBuilder();

    if (startUrlIndex != -1 && finishUrlIndex != -1) {
      String first = text.substring(0, startUrlIndex);
      String second = text.substring(finishUrlIndex, text.length() - 1);
      String url = getStringByIndex(text, startUrlIndex, finishUrlIndex);

      strBuilder = connectString(first, url, second);
      result = strBuilder.toString();

      Log.d("result123", "Before : " + text + "\nAfter : " + result);

      String ex = result;
      HashMap<Integer, Integer> hashMap = getTagOrLinkPosition(ex);

      int len = 0;
      for (Map.Entry<Integer, Integer> map : hashMap.entrySet()) {
        int start = map.getKey();
        int end = map.getValue();
        strBuilder.insert(end + len,"</i>");
        strBuilder.insert(start + len,"<i>");
        len += 7;
      }
      Log.d("result123", "Final : " + strBuilder.toString());
    }

    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setText(Html.fromHtml(result));
  }

  public static StringBuilder connectString(String s1, String url, String s2) {
    StringBuilder str = new StringBuilder();
    str.append("<p> ")
        .append(s1)
        .append("<a href = \"")
        .append(url)
        .append("\">")
        .append(url)
        .append("</a>")
        .append(s2)
        .append("</p>");
    return str;
  }
}
