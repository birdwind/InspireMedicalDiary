package com.birdwind.init.base.utils.fcm;

import java.util.Map;

import com.birdwind.init.App;
import com.birdwind.init.R;
import com.birdwind.init.base.Config;
import com.birdwind.init.base.utils.GsonUtils;
import com.birdwind.init.base.utils.LogUtils;
import com.birdwind.init.view.activity.BottomNavigationActivity;
import com.google.firebase.messaging.RemoteMessage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationUtils {

    private static NotificationManager notificationManager;

    private static int pendingNotificationsCount = 0;

    private Context context;

    private String channelId;

    private String packageName;

    private int smallIcon;

    private Bitmap largeIcon;

    private long[] vibrate;

    private NotificationCompat.Builder builder;

    private PendingIntent pendingIntent;

    public NotificationUtils() {}

    public NotificationUtils(Context context) {
        this.context = context;
        channelId = context.getString(R.string.default_notification_channel_id);
        packageName = Config.APP_PACKAGE_NAME;
        smallIcon = R.mipmap.ic_launcher;
        largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        vibrate = new long[] {100, 200, 300, 400, 500, 400, 300, 200, 100};
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                createChannel(channelId, "default", NotificationManager.IMPORTANCE_HIGH, true);
            }
        }
    }

    public void createChannel(String id, String name, int importLevel, boolean isShowBadge) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, importLevel);
            channel.setShowBadge(isShowBadge);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            channel.setVibrationPattern(vibrate);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(RemoteMessage remoteMessage) {
        String title;
        String body;
        String sound;
        String action;
        String id;
        String timestamp;
        Intent notificationIntent;

        LogUtils.e("FCM Body", GsonUtils.toJson(remoteMessage.getData()));
        Map<String, String> notificationBodyMap = remoteMessage.getData();
        title = notificationBodyMap.get("title");
        body = notificationBodyMap.get("body");
        sound = notificationBodyMap.get("sound");
        action = notificationBodyMap.get("action");
        id = notificationBodyMap.get("id");
        timestamp = String.valueOf(remoteMessage.getSentTime());

        int pendingNotificationsCount = App.getUnreadCount() + 1;
        // App.setUnreadCount(pendingNotificationsCount);

        // icon badge

        ShortcutBadger.applyCount(App.getAppContext(), App.getUnreadCount());
        // BadgeNumberManager.from(App.getAppContext()).setBadgeNumber(App.getUnreadCount());

        notificationIntent = new Intent(context, BottomNavigationActivity.class);
        if (action.equals("Apply") || action.equals("Restore")) {
//            notificationIntent = new Intent(context, SplashActivity.class);
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notificationIntent.putExtra("body", body);
        notificationIntent.putExtra("action", action);
        notificationIntent.putExtra("id", id);

        showNotification(title, body, timestamp, notificationIntent);

    }

    public void showNotification(final String title, final String message, final String timeStamp, Intent intent) {
        showNotification(title, message, timeStamp, intent, null);
    }

    public void showNotification(final String title, final String message, final String timeStamp, Intent intent,
        String imageUrl) {
        pendingNotificationsCount += 1;
        builder = new NotificationCompat.Builder(context, channelId);
        pendingIntent = PendingIntent.getActivity(context, pendingNotificationsCount, intent,
            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        builder.setContentTitle(title).setSmallIcon(smallIcon)
            .setColor(ContextCompat.getColor(context, R.color.colorBlue_004EA2)).setContentText(message)
            .setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setTicker(title).setAutoCancel(true)
            .setVibrate(vibrate).setWhen(System.currentTimeMillis());
        Notification notification = builder.build();
        // 小米icon badge
        // BadgeNumberManagerXiaoMi.setBadgeNumber(notification, App.getUnreadCount());
        notificationManager.notify(pendingNotificationsCount, notification);
    }

    public void clearNotifications() {
        pendingNotificationsCount = 0;
        notificationManager.cancelAll();
    }

    public boolean checkNotificationEnable() {
        // check if global notification switch is ON or OFF
        if (NotificationManagerCompat.from(context).areNotificationsEnabled())
            // if its ON then we need to check for individual channels in OREO
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = manager.getNotificationChannel(channelId);
                return channel.getImportance() != NotificationManager.IMPORTANCE_HIGH;
            } else {
                // if this less then OREO it means that notifications are enabled
                return true;
            }
        // if this is less then OREO it means that notifications are disabled
        return false;
    }
}
