package com.rasset.wflaunch.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by andman on 2015-11-30.
 */
public class Prefer {

    private static final String SHARED_NAME = "ValueUpPad";

    protected Prefer(){

    }


    public static void setSharedPreference(String strKey, String strVal, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(strKey, strVal);
        editor.apply();
    }

    public static String getPreferenceString(String strKey, Context context) {
        String strRet = null;
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);

        strRet = pref.getString(strKey, null);

        return strRet;
    }

    public static void setSharedPreference(String strKey, boolean bTrue, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(strKey, bTrue);
        editor.apply();
    }

    public static boolean getPreferenceBoolean(String strKey, Context context) {
        Boolean bBool = false;
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);

        bBool = pref.getBoolean(strKey, false);

        return bBool;
    }

    public static void setSharedPreference(String strKey, int nNum, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(strKey, nNum);
        editor.apply();
    }

    public static int getPreferenceInteger(String strKey, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        int nNum = pref.getInt(strKey, 0);

        return nNum;
    }

    public static void setSharedPreference(String strKey, Long lNum, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putLong(strKey, lNum);
        editor.apply();
    }

    public static Long getPreferenceLong(String strKey, Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        Long nNum = 0l;
        try {
            nNum = pref.getLong(strKey, 0);
        } catch (ClassCastException e) {
            String strValue = pref.getString(strKey, "0");
            nNum = Long.parseLong(strValue);
            setSharedPreference(strKey, nNum, context);
        }

        return nNum;
    }

    public static void removePreference(String strKey, Context context) {
        SharedPreferences.Editor prefEditor = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE).edit();
        prefEditor.remove(strKey);
        prefEditor.apply();
    }

    public static void clearPreference(Context context) {
        SharedPreferences.Editor prefEditor = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE).edit();
        prefEditor.clear();
        prefEditor.apply();
    }

    /**
     * Man is true Default , Woman is false Default  By
     * @param context
     * @return
     */
    public static boolean getPrefenceBooleanUseByGender(String strKey, Context context){
        boolean bBool = false;
        SharedPreferences pref = context.getSharedPreferences(Prefer.SHARED_NAME, Context.MODE_PRIVATE);

        bBool = pref.getBoolean(strKey, false);

        return bBool;
    }

    /**
     * 로그아웃해도 안지워지는 Prefrence 단, 설정의 앱 Data 삭제시는 지워짐.
     * @param strKey
     * @param bTrue
     * @param context
     */
    public static void setPermanentPrefer(String strKey, boolean bTrue, Context context) {
        SharedPreferences pref = context.getSharedPreferences("permanent", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(strKey, bTrue);
        editor.apply();
    }

    public static boolean getPermanentPreferBoolean(String strKey, Context context) {
        Boolean bBool = false;
        SharedPreferences pref = context.getSharedPreferences("permanent", Context.MODE_PRIVATE);

        bBool = pref.getBoolean(strKey, false);

        return bBool;
    }
}
