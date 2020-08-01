package com.example.proyekakhir_khoirulanam.AppController;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {


    static final String id ="id", poin ="poin";
    static final String KEY_USERNAME_SEDANG_LOGIN = "username";
    static final String KEY_EMAIL_SEDANG_LOGIN = "email";
    static final String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";
    static final String FIREBASE = "id";
    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setid(Context context, String idku){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(id, idku);
        editor.apply();
    }

    public static String getId(Context context){
        return getSharedPreference(context).getString(id,"errors");
    }


    public static void setLoggedInUser(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_EMAIL_SEDANG_LOGIN, id);
        editor.apply();
    }

    public static String getLoggedInUser(Context context){
        return getSharedPreference(context).getString(KEY_EMAIL_SEDANG_LOGIN,"null");
    }
    public static void setLoggedInName(Context context, String id){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME_SEDANG_LOGIN, id);
        editor.apply();
    }
    public static String getLoggedInName(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME_SEDANG_LOGIN,"null");
    }
    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putBoolean(KEY_STATUS_SEDANG_LOGIN,status);
        editor.apply();
    }

    public static boolean getLoggedInStatus(Context context){
        return getSharedPreference(context).getBoolean(KEY_STATUS_SEDANG_LOGIN,false);
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USERNAME_SEDANG_LOGIN);
        editor.remove(KEY_STATUS_SEDANG_LOGIN);
        editor.apply();
    }

}