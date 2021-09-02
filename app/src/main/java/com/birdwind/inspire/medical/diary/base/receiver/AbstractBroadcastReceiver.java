package com.birdwind.inspire.medical.diary.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class AbstractBroadcastReceiver extends BroadcastReceiver implements BaseBroadcastReceiver {

    public boolean isRegistered;

    @Override
    public Intent register(Context context, IntentFilter intentFilter) {
        try {
            return !isRegistered ? context.registerReceiver(this, intentFilter) : null;
        } finally {
            isRegistered = true;
        }
    }

    @Override
    public boolean unregister(Context context) {
        return isRegistered && unregisterInternal(context);
    }

    protected boolean unregisterInternal(Context context) {
        context.unregisterReceiver(this);
        isRegistered = false;
        return true;
    }
}
