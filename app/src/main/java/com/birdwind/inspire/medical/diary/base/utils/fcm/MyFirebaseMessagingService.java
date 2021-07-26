package com.diary.init.base.utils.fcm;

import org.jetbrains.annotations.NotNull;

import com.diary.init.App;
import com.diary.init.base.Config;
import com.diary.init.base.utils.LogUtils;
import com.diary.init.base.utils.SharedPreferencesUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.text.TextUtils;

import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;

    private static String FCM;

    public static String getFCMToken() {
        FCM = SharedPreferencesUtils.get(Config.FCM_NAME, "");
        if (TextUtils.isEmpty(FCM)) {
//            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//                if (!task.isSuccessful()) {
//                    LogUtils.d("FCM產生失敗 : " + task.getException());
//                    return;
//                }
//
//                FCM = task.getResult();
//            });
        }
        return FCM;
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        LogUtils.e("更新FirebaseToken :" + token);
        SharedPreferencesUtils.put(Config.FCM_NAME, token);
        if (App.userModel != null) {
            App.userModel.setUpdateFCM(true);
            App.updateUserModel();
        }
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        notificationUtils = new NotificationUtils(this);
        notificationUtils.createNotification(remoteMessage);
    }
}
