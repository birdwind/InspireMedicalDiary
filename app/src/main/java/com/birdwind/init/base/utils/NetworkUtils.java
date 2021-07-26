package com.birdwind.init.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    /**
     * 判斷網路是否連線
     * <p>
     * 需添加權限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}
     * </p>
     *
     * @param context 上下文
     * @return {@code true}: 是<br>
     *         {@code false}: 否
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    /**
     * 獲取網路訊息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
