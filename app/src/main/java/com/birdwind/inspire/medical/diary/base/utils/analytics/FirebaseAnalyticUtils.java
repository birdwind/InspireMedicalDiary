package com.diary.init.base.utils.analytics;

import com.diary.init.App;
import com.diary.init.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.Activity;
import android.os.Bundle;

public class FirebaseAnalyticUtils {
    private static FirebaseAnalytics firebaseAnalytics;

    private static SimpleDateFormat format;

    private static void initFirebaseAnalytic() {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(App.getAppContext());
        }
    }

    public static void Log(String key, Bundle value) {
        if (BuildConfig.DEBUG) {
            return;
        }
        initFirebaseAnalytic();
        if (format == null) {
            format = new SimpleDateFormat("yyyy/MM/dd-hh:mm", Locale.TAIWAN);
        }
        if (value == null) {
            value = new Bundle();
        }
        String time = format.format(System.currentTimeMillis());
        value.putString("time", time);
        firebaseAnalytics.logEvent(key, value);
    }

    public static void Log(String key) {
        Log(key, null);
    }

    public static void setCurrentScreen(Activity activity, String screenName, String screenClassNameOverride) {
        initFirebaseAnalytic();
        firebaseAnalytics.setCurrentScreen(activity, screenName, screenClassNameOverride);
    }

}
