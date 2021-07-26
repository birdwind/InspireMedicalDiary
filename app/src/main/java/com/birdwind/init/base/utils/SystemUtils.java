package com.birdwind.init.base.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.jsoup.Jsoup;

import com.birdwind.init.App;
import com.birdwind.init.base.Config;
import com.birdwind.init.base.utils.fcm.MyFirebaseMessagingService;
import com.birdwind.init.base.view.AbstractDialog;
import com.birdwind.init.view.dialog.CommonDialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class SystemUtils {

    /**
     * 獲取AndroidSDK版本
     */
    public static int getAndroidVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 獲取Android SDK release
     */
    public static String getAndroidRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 獲取完整Android SDK
     */
    public static String getAndroidSDK() {
        return getAndroidVersion() + "_" + getAndroidRelease();
    }

    /**
     * 獲取當前系統語言
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 獲取系統語言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 獲取手機型號
     */
    public static String getSystemModel() {
        return Build.MODEL.replace(" ", "_");
    }

    /**
     * 獲取手機廠商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 獲取IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Activity.TELEPHONY_SERVICE);
        String IMEI = null;

        try {
            if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    IMEI = telephonyManager.getImei();
                } else {
                    IMEI = telephonyManager.getDeviceId();
                }
            }
        } catch (Exception e) {
            LogUtils.exception(e);
        }

        IMEI = IMEI == null ? SharedPreferencesUtils.get("IMEI", "") : IMEI;
        if (IMEI.equals("")) {
            IMEI = UUID.randomUUID().toString();
        }
        SharedPreferencesUtils.put("IMEI", IMEI);
        return IMEI;
    }

    public static String getAndroidId() {
        String androidId =
            Settings.Secure.getString(App.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

            Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

            Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

            Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

            Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

            Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

            Build.USER.length() % 10; // 13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    // public static String initKMTUniquePass() {
    // String pass = MyFirebaseMessagingService.getFCMToken();
    // if (TextUtils.isEmpty(pass)) {
    // pass = getUniquePsuedoID();
    // }
    // SharedPreferencesUtils.put(Config.KMT_UNI_PASS, pass);
    // return pass;
    // }
    //
    // public static String getKMTUniquePass() {
    // return SharedPreferencesUtils.get(Config.KMT_UNI_PASS, "");
    // }

    // 設定忽略系統文字大小
    public static void setSystemTextDefault(Context context) {
        Resources res = context.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    // 調整文字大小
    public static void adjustFontScale(Context context, Configuration configuration) {
        if (configuration.fontScale != 1) {
            configuration.fontScale = 1;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                wm.getDefaultDisplay().getMetrics(metrics);
            }
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            context.getResources().updateConfiguration(configuration, metrics);
        }
    }

    // 設定忽略系統顯示大小
    public static void setDefaultDisplay(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Configuration origConfig = context.getResources().getConfiguration();
            origConfig.densityDpi = getDefaultDisplayDensity();

            LogUtils.e("DensityDpi", String.valueOf(origConfig.densityDpi));
            context.getResources().updateConfiguration(origConfig, context.getResources().getDisplayMetrics());
        }
    }

    public static int getDefaultDisplayDensity() {
        try {
            Class clazz = Class.forName("android.view.WindowManagerGlobal");
            Method method = clazz.getMethod("getWindowManagerService");
            method.setAccessible(true);
            Object iwm = method.invoke(clazz);
            Method getInitialDisplayDensity = iwm.getClass().getMethod("getInitialDisplayDensity", int.class);
            getInitialDisplayDensity.setAccessible(true);
            Object densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY);
            return (int) densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getCurrentApkVersion() {
        return Config.APP_PACKAGE_NAME;
    }

    public static boolean isServiceRunning(Class<?> serviceClass, Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void isNeedUploadApk(String currentVersion, CommonDialog commonDialog) {
        CheckGoogleApkVersion checkGoogleApkVersion = new CheckGoogleApkVersion(getCurrentApkVersion(), commonDialog);
    }

    public static void openGooglePlayCurrentApp(Context context) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + Config.APP_PACKAGE_NAME));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {

            Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + Config.APP_PACKAGE_NAME));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static void openAppRating(Context context) {
        // you can also use BuildConfig.APPLICATION_ID
        String appId = context.getPackageName();
        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appId));
        boolean marketFound = false;

        // find all applications able to handle our rateIntent
        final List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {

                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName =
                    new ComponentName(otherAppActivity.applicationInfo.packageName, otherAppActivity.name);
                // make sure it does NOT open in the stack of your activity
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // task reparenting if needed
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                // if the Google Play was already open in a search result
                // this make sure it still go to the app page you requested
                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this make sure only the Google Play app is allowed to
                // intercept the intent
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;

            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent =
                new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appId));
            context.startActivity(webIntent);
        }
    }

    private static class CheckGoogleApkVersion extends AsyncTask<Void, String, String> {
        private String currentVersion;

        private AbstractDialog dialog;

        public CheckGoogleApkVersion(String currentVersion, AbstractDialog dialog) {
            this.currentVersion = currentVersion;
            this.dialog = dialog;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String newVersion;
            try {
                newVersion =
                    Jsoup.connect("https://play.google.com/store/apps/details?id=" + Config.APP_PACKAGE_NAME + "&hl=it")
                        .timeout(30000)
                        .userAgent(
                            "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com").get().select(".hAyfc .htlgb").get(7).ownText();
                return newVersion;
            } catch (Exception e) {
                LogUtils.exception(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);
            if (version != null && !version.isEmpty()) {
                if (Float.parseFloat(currentVersion) < Float.parseFloat(version)) {
                    LogUtils.e("需要更新");
                    dialog.show();
                }
            }
        }
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

    public static String initUniquePass() {
        String pass = MyFirebaseMessagingService.getFCMToken();
        if (TextUtils.isEmpty(pass)) {
            pass = getUniquePsuedoID();
        }
        SharedPreferencesUtils.put(Config.UNI_PASS, pass);
        return pass;
    }
}
