package com.example.myapplicationh.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MSP {
    private final String SP_FILE = "SP_FILE";
    public  final String SP_KEY_NAME ="SP_KEY_NAME";

    private static MSP me;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String playerName;

    public static MSP getMe() {
        return me;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    private MSP(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static MSP initHelper(Context context) {
        if (me == null) {
            me = new MSP(context);
        }
        return me;
    }

    public void putDouble(String KEY, double defValue) {
        putString(KEY, String.valueOf(defValue));
    }

    public double getDouble(String KEY, double defValue) {
        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
    }

    public int getInt(String KEY, int defValue) {
        return sharedPreferences.getInt(KEY, defValue);
    }

    public void putInt(String KEY, int value) {
        editor.putInt(KEY, value).apply();
    }

    public String getString(String KEY, String defValue) {
        return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String KEY, String value) {
        editor.putString(KEY, value).apply();
    }

    public String getSP_FILE() {
        return SP_FILE;
    }

    public String getSP_KEY_NAME() {
        return SP_KEY_NAME;
    }
}
