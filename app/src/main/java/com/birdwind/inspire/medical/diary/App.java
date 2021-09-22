package com.birdwind.inspire.medical.diary;

import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.SharedPreferencesUtils;
import com.birdwind.inspire.medical.diary.base.utils.fcm.MyFirebaseMessagingService;
import com.birdwind.inspire.medical.diary.model.UserModel;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

public class App extends Application {

    private static App App;

    public static UserModel userModel;

    public static boolean doubleBackToExitPressedOnce;

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        LogUtils.e("FirebaseToken", MyFirebaseMessagingService.getFCMToken());
        if (BuildConfig.DEBUG) {
            // Stetho.initializeWithDefaults(this);
        }

        userModel = GsonUtils.parseJsonToBean(SharedPreferencesUtils.get(Config.USER_MODEL_NAME, ""), UserModel.class);
        doubleBackToExitPressedOnce = false;
    }

    /**
     * 獲得未讀取數量
     *
     * @return 未讀取數量
     */
    public static int getUnreadCount() {
        return 0;
    }

    /**
     * 獲得 App Context
     *
     * @return App Context
     */
    public static Context getAppContext() {
        return App;
    }

    /**
     * 獲得App Resource
     *
     * @return App Resource
     */
    public static Resources getAppResources() {
        return App.getResources();
    }

    public static void updateUserModel() {
        SharedPreferencesUtils.put(Config.USER_MODEL_NAME, userModel);
    }

    public static boolean isDoubleBack() {
        if (doubleBackToExitPressedOnce) {
            return true;
        }
        doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        return false;
    }
}
