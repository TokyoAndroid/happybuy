package com.zw.happybuy.utils.rxbus;

import android.content.Context;
import android.content.SharedPreferences;

import com.zw.happybuy.common.App;

/**
 * Created by Administrator on 2017/8/27.
 */

public class SPUtils {

    private SPUtils(){}

    private static SharedPreferences getSharedPreferences() {
        return App.getAppContext()
                .getSharedPreferences(App.getAppContext().getPackageName(), Context.MODE_PRIVATE);
    }

    /**
     * 保存boolean类型配置信息
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        getSharedPreferences().edit()
                .putBoolean(key, value)
                .apply();
    }


    /**
     * 获取boolean类型配置信息
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 保存int类型配置信息
     * @param key
     * @param value
     */
    public static void saveInt(String key, int value) {
        getSharedPreferences().edit()
                .putInt(key, value)
                .apply();
    }

    /**
     * 获取int类型配置信息
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        return getSharedPreferences().getInt(key, defValue);
    }
    /**
     * 保存long类型配置信息
     * @param key
     * @param value
     */
    public static void saveLong(String key, long value) {
        getSharedPreferences().edit()
                .putLong(key, value)
                .apply();
    }

    /**
     * 获取long类型配置信息
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String key, Long defValue) {
        return getSharedPreferences().getLong(key, defValue);
    }


    /**
     * 保存String类型配置信息
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        getSharedPreferences().edit()
                .putString(key, value)
                .apply();
    }

    /**
     * 获取String类型配置信息
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return getSharedPreferences().getString(key, defValue);
    }

}
