package bz.adore.androidbaseapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import bz.adore.androidbaseapp.AppController;

public class Preference {
    // 初回フラグ デフォルト値
    public static final boolean DEFAULT_INIT = false;
    // 通知 ON/OFF デフォルト値
    public static final boolean DEFAULT_PUSH_NOTICE = true;

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(AppController.getApplication());
    }

    private static void setInt(String key, int value){
        SharedPreferences preference = getSharedPreferences();
        preference.edit().putInt(key, value).commit();
    }

    private static int getInt(String key, int default_value){
        SharedPreferences preference = getSharedPreferences();
        return preference.getInt(key, default_value);
    }

    private static void setBool(String key, boolean value){
        SharedPreferences preference = getSharedPreferences();
        preference.edit().putBoolean(key, value).commit();
    }

    private static boolean getBool(String key, boolean default_value){
        SharedPreferences preference = getSharedPreferences();
        return preference.getBoolean(key, default_value);
    }

    private static void setString(String key, String value){
        SharedPreferences preference = getSharedPreferences();
        preference.edit().putString(key, value).commit();
    }

    private static String getString(String key, String default_value){
        SharedPreferences preference = getSharedPreferences();
        return preference.getString(key, default_value);
    }

    public static boolean getInit() {
        return getBool("pref_init", DEFAULT_INIT);
    }

    public static void setInit(boolean value) {
        setBool("pref_init", value);
    }

    public static boolean getPushNotice() {
        return getBool("pref_push_notice", DEFAULT_PUSH_NOTICE);
    }

    public static void setPushNotice(boolean value) {
        setBool("pref_push_notice", value);
    }
}
