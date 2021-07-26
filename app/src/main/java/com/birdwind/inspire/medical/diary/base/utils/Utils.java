package com.birdwind.inspire.medical.diary.base.utils;

import android.content.Context;

public class Utils {
    public static int dp2px(Context context, float dpVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

    public static int sp2px(Context context, float spVal) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * fontScale + 0.5f);
    }
}
