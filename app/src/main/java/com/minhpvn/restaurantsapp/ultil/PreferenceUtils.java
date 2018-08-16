package com.minhpvn.restaurantsapp.ultil;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class PreferenceUtils {
    private static final String SHARED_DEFAULT = "app_shared_default";
    private static final String IS_START_APP = "is_start_app";
    private static final String SAVE_FB_DATA = "save_fb_data";
    private static final String SAVE_FB_DATA_NAME = "save_fb_data_name";
    private static final String SAVE_FB_DATA_ID = "save_fb_data_id";

    public static void saveStartApp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_DEFAULT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_START_APP, false);
        editor.apply();
    }


    public static boolean isStartApp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_DEFAULT, Context.MODE_PRIVATE);
        boolean isFirtsLauncher = sharedPreferences.getBoolean(IS_START_APP, true);
        return isFirtsLauncher;
    }

    public static void saveFBData(Context context, String name,String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_FB_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_FB_DATA_NAME, name);
        editor.putString(SAVE_FB_DATA_ID,id);
        editor.apply();
    }

    public static List<String> getFBData(Context context) {
        List<String> data = new ArrayList<>();
        data.clear();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVE_FB_DATA, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(SAVE_FB_DATA_NAME, "dvName");
        String id = sharedPreferences.getString(SAVE_FB_DATA_ID,"dvId");
        data.add(name);
        data.add(id);
        return data;
    }

}
