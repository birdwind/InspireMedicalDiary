package com.birdwind.init.base.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;

public class ToastUtils {

    private static Toast toast;

    public static void show(Context context, @StringRes int resId) {
        show(context, context.getResources().getString(resId));
    }

    public static void show(Context context, CharSequence text) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
