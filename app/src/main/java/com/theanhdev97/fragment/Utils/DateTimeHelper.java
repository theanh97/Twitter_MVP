package com.theanhdev97.fragment.Utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DELL on 04/01/2018.
 */

public class DateTimeHelper {
  public static String countTimeBetweenTwoDay(String fromDate) {
    String result = "";
    Date firstDate = convertStringToDate(fromDate);
    Date secondDate = Calendar.getInstance().getTime();

    //milliseconds
    long different = secondDate.getTime() - firstDate.getTime() - 7 * 1000 * 60 * 60;

    long secondsInMilli = 1000;
    long minutesInMilli = secondsInMilli * 60;
    long hoursInMilli = minutesInMilli * 60;
    long daysInMilli = hoursInMilli * 24;

    long elapsedDays = different / daysInMilli;
    different = different % daysInMilli;

    long elapsedHours = different / hoursInMilli;
    different = different % hoursInMilli;

    long elapsedMinutes = different / minutesInMilli;
    different = different % minutesInMilli;

    long elapsedSeconds = different / secondsInMilli;

    if (elapsedDays > 0)
      return elapsedDays + "d";
    if (elapsedHours > 0)
      return elapsedHours + "h";
    if (elapsedMinutes > 0)
      return elapsedMinutes + "m";
    else
      return elapsedSeconds + "s";
  }

  public static Date convertStringToDate(String date) {
//    String date2 = "Thu Jan 04 05:53:47 +0000 2018";
    String format = "dd/MM/yyyy HH:mm:ss";
//    date = "26/06/1997 00:55:32";
    date = splitStringToValidFormatDate(date);
    SimpleDateFormat spf = new SimpleDateFormat(format);
    Date newDate = null;
    try {
      newDate = spf.parse(date);
    } catch (ParseException e) {
      Log.e(Const.TAG, e.getMessage());
    }
    return newDate;
  }

  public static String splitStringToValidFormatDate(String date) {
    StringBuilder newDate = new StringBuilder();
    String day = date.split(" ")[2];
    String month = date.split(" ")[1];
    switch (month) {
      case "Jan":
        month = "01";
        break;
      case "Feb":
        month = "02";
        break;
      default:
        month = "12";
        break;
    }
    String year = date.split(" ")[5];
    String hour = date.split(" ")[3];
    newDate.append(day + "/")
        .append(month + "/")
        .append(year + " ")
        .append(hour);
    return newDate.toString();
  }
}
