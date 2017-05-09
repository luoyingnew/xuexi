package com.qinmr.mylibrary.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.View;


public class Utils {

    public static Drawable getDrawble(Context conetxt, @DrawableRes int id) {
        return ContextCompat.getDrawable(conetxt, id);
    }

    public static int getColor(Context conetxt, @ColorRes int id) {
        return ContextCompat.getColor(conetxt, id);
    }

    public static String getString(Context conetxt, @StringRes int id) {
        return conetxt.getResources().getString(id);
    }

    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * getDensity(context) + 0.5f);
    }

    public static int px2sp(float pxValue, Context context) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    public static int dp2px(Context context, int dip) {
        return (int) (dip * getDensity(context) + 0.5f);
    }

    public static int dp2px(float dipValue, float scale) {
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue, float scale) {
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static <T extends View> T findViewById(View v, int id) {
        return (T) v.findViewById(id);
    }

}