package bz.adore.androidbaseapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 現在の日時を取得
     * @return 日時文字列
     */
    public String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("JST"));
        return formatter.format(new Date());
    }

    /**
     * java.util.Date型日付を文字列へ変換
     * @param date 変換対象の日付
     * @return 変換後の文字列
     */
    public static String toString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        return formatter.format(date);
    }

    /**
     * java.util.Date型日付を文字列へ変換
     * @param date 変換対象の日付
     * @param fomatStr 出力形式
     * @return 変換後の文字列
     */
    public static String format(Date date, String fomatStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(fomatStr);
        return formatter.format(date);
    }

    /**
     * 日付文字列をjava.util.Date型へ変換
     * @param str 変換対象の文字列
     * @return 変換後のjava.util.Dateオブジェクト
     * @throws ParseException 日付文字列が"yyyy-MM-dd"以外の場合
     */
    public static Date toDate(String str) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return  dateFormat.parse(str);
    }

    public static String getWeekdayShortName(int weekday) {
        String[] weekdayShortNames = {"日", "月", "火", "水", "木", "金", "土"};
        weekday -= 1;
        if(weekday < 0) {
            weekday = 0;
        } else if(weekday > 6) {
            weekday = 6;
        }
        return weekdayShortNames[weekday];
    }
}