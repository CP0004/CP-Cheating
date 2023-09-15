package com.cp.jo.tool;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private final static String FILE_SAVE_APP = "save";

    //-- Set and Get Value String
    public static void setValue(Context context, String key, String value) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edValue = setValue.edit();
        edValue.putString(key, value);
        edValue.apply();
    }

    public static String getValueString(Context context, String key) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        return setValue.getString(key, null);
    }

    //-- Set and Get Value Int
    public static void setValue(Context context, String key, int value) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edValue = setValue.edit();
        edValue.putInt(key, value);
        edValue.apply();
    }

    public static int getValueInt(Context context, String key) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        return setValue.getInt(key, 0);
    }

    //-- Set and Get Value Float
    public static void setValue(Context context, String key, float value) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edValue = setValue.edit();
        edValue.putFloat(key, value);
        edValue.apply();
    }

    public static float getValueFloat(Context context, String key) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        return setValue.getFloat(key, 0.0f);
    }

    //-- Set and Get Value Boolean
    public static void setValue(Context context, String key, boolean value) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor edValue = setValue.edit();
        edValue.putBoolean(key, value);
        edValue.apply();
    }

    public static Boolean getValueBoolean(Context context, String key) {
        SharedPreferences setValue = context.getSharedPreferences(FILE_SAVE_APP, Context.MODE_PRIVATE);
        return setValue.getBoolean(key, false);
    }
}