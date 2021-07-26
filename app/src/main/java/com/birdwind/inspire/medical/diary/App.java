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

public class App extends Application {

    private static App App;

    public static boolean isLoginError;

    public static UserModel userModel;

    @Override
    public void onCreate() {
        super.onCreate();
        App = this;
        isLoginError = false;
        LogUtils.e("FirebaseToken", MyFirebaseMessagingService.getFCMToken());
        if (BuildConfig.DEBUG) {
            // Stetho.initializeWithDefaults(this);
        }

        userModel = GsonUtils.parseJsonToBean(SharedPreferencesUtils.get(Config.USER_MODEL_NAME, ""), UserModel.class);
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
}
