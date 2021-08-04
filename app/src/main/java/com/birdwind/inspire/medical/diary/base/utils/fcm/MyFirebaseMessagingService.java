package com.birdwind.inspire.medical.diary.base.utils.fcm;

import org.jetbrains.annotations.NotNull;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.SharedPreferencesUtils;
import com.google.firebase.messaging.FirebaseMessaging;
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
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    LogUtils.d("FCM產生失敗 : " + task.getException());
                    return;
                }

                FCM = task.getResult();
            });
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
