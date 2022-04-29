package com.birdwind.inspire.medical.diary.base.utils.fcm;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.fxn.stash.Stash;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationUtils notificationUtils;

    private static String FCM;

    public static String getFCMToken() {
        FCM = Stash.getString(Config.FCM_NAME, "");
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
        Stash.put(Config.FCM_NAME, token);
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
