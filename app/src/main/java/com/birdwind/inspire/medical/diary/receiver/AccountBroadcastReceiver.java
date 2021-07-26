package com.birdwind.inspire.medical.diary.receiver;

import com.birdwind.inspire.medical.diary.base.utils.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class AccountBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.birdwind.init.account.logout")) {
            // TODO:登出
            LogUtils.d("登出");
        }
    }
}
