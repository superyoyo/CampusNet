package com.campus.campusnet.ui.util;

import android.content.Context;
import com.campus.campusnet.R;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间转换工具
 */
public class TimeUtil {


    private TimeUtil() {
    }

    /**
     * 时间转化为显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String getTimeStr(Context context, long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        if (calendar.before(inputTime)) {
            //今天23:59在输入时间之前，解决一些时间误差，把当天时间显示到这里
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + 
                    context.getResources().getString(R.string.time_year)
                    + "MM" + context.getResources().getString(R.string.time_month) 
                    + "dd" + context.getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return context.getResources().getString(R.string.time_yesterday) + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M"
                        + context.getResources().getString(R.string.time_month)
                        + "d" + context.getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy"
                        + context.getResources().getString(R.string.time_year)
                        + "MM" + context.getResources().getString(R.string.time_month)
                        + "dd" + context.getResources().getString(R.string.time_day));
                return sdf.format(currenTimeZone);

            }

        }

    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为毫秒
     */
    public static String getChatTimeStr(Context context, long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        if (!calendar.after(inputTime)) {
            //当前时间在输入时间之前
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy"
                    + context.getResources().getString(R.string.time_year)
                    + "MM" + context.getResources().getString(R.string.time_month)
                    + "dd" + context.getResources().getString(R.string.time_day));
            return sdf.format(currenTimeZone);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return context.getResources().getString(R.string.time_yesterday) + " " + sdf.format(currenTimeZone);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("M"
                        + context.getResources().getString(R.string.time_month)
                        + "d" + context.getResources().getString(R.string.time_day) + " HH:mm");
                return sdf.format(currenTimeZone);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy"
                        + context.getResources().getString(R.string.time_year)
                        + "MM" + context.getResources().getString(R.string.time_month)
                        + "dd" + context.getResources().getString(R.string.time_day) + " HH:mm");
                return sdf.format(currenTimeZone);
            }

        }

    }

    /**
     * 时间转化为聊天界面显示字符串
     *
     * @param timeStamp 单位为秒
     */
    public static String[] getAlbumTimeStr(Context context, long timeStamp) {
        if (timeStamp == 0) return null;
        Calendar inputTime = Calendar.getInstance();
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        if (calendar.before(inputTime)) {
            //今天23:59在输入时间之前，解决一些时间误差，把当天时间显示到这里
            return new String[]{"", context.getResources().getString(R.string.time_today), ""};
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return new String[]{"", context.getResources().getString(R.string.time_today), ""};
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            return new String[]{"", context.getResources().getString(R.string.time_yesterday), ""};
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("d:" + "M" + context.getResources().getString(R.string.time_month));
                String[] timeStr = sdf.format(currenTimeZone).split(":");
                String[] result = new String[3];
                result[0] = "";
                result[1] = timeStr[0];
                result[2] = timeStr[1];
                return result;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + context.getResources().getString(R.string.time_year)
                        + ":dd:MM" + context.getResources().getString(R.string.time_month));

                String[] timeStr = sdf.format(currenTimeZone).split(":");
                return timeStr;
            }

        }
    }

    /**
     * 获取附近的人的活跃时间
     *
     * @param time
     * @return
     */
    public static String getNearPeopleTime(Long time) {
        long delta = System.currentTimeMillis() - time;

        if (delta <= 1000 * 60 * 2) {
            return "刚才";
        }

        if (delta <= 1000 * 60 * 60) {
            return delta / (1000 * 60) + "分钟前";
        }

        if (delta <= 1000 * 60 * 60 * 24) {
            return delta / (1000 * 60 * 60) + "小时前";
        }

        return delta / (1000 * 60 * 60 * 24) + "天前";
    }

    /**
     * 计算总共的事件
     * @param time
     * @return
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else retStr = "" + i;
        return retStr;
    }

    /**
     * 计算还剩多少时间
     * @param milliseconds
     * @return
     */
    public static long getSecondsByMilliseconds(long milliseconds) {
        long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000)).setScale(0,
                BigDecimal.ROUND_HALF_UP).intValue();
        // if (seconds == 0) {
        // seconds = 1;
        // }
        return seconds;
    }
}
