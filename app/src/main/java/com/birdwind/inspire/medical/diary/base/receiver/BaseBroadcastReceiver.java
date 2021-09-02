package com.birdwind.inspire.medical.diary.base.receiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public interface BaseBroadcastReceiver {

    Intent register(Context context, IntentFilter intentFilter);

    boolean unregister(Context context);
}
