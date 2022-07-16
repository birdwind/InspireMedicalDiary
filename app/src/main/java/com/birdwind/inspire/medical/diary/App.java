package com.birdwind.inspire.medical.diary;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;

import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.fcm.MyFirebaseMessagingService;
import com.birdwind.inspire.medical.diary.model.UserModel;
import com.fxn.stash.Stash;

public class App extends Application {

    private static App App;

    public static UserModel userModel;

    public static boolean doubleBackToExitPressedOnce;

    public static boolean isShowUpdated;

    public static boolean isStepByStep;

    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);

        App = this;
        LogUtils.e("FirebaseToken", MyFirebaseMessagingService.getFCMToken());
        if (BuildConfig.DEBUG) {
            // Stetho.initializeWithDefaults(this);
        }
        userModel = (UserModel) Stash.getObject(Config.USER_MODEL_NAME, UserModel.class);
        doubleBackToExitPressedOnce = false;
        isShowUpdated = false;
        isStepByStep = false;
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
        Stash.put(Config.USER_MODEL_NAME, userModel);
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
