package com.birdwind.inspire.medical.diary.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.birdwind.inspire.medical.diary.base.receiver.AbstractBroadcastReceiver;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;

public class PainterBroadcastReceiver extends AbstractBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d("尚未實作");
    }

    public Intent register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastReceiverAction.PAINTER_HAVE_DOCTOR);
        return !isRegistered ? register(context, intentFilter) : null;
    }

}
