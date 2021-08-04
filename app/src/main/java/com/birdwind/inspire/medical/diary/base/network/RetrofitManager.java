package com.birdwind.inspire.medical.diary.base.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;

import android.content.Context;
import android.text.TextUtils;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitManager {

    private String baseUrl = Config.BASE_URL;

    private ApiServer apiServer;

    private Context context = App.getAppContext();

    private static CookieManger cookieManger;

    private static class SingletonHolder {
        private static RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitManager getInstanceWithUrl(String url) {
        return new RetrofitManager(url);
    }

    private RetrofitManager() {
        this(null);
    }

    private RetrofitManager(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = this.baseUrl;
        }
        if (cookieManger == null) {
            cookieManger = new CookieManger(context);
        }
        Map<String, String> headers = new HashMap<>();
//        headers.put("OS", "A");
//        headers.put("Ver", Config.APP_VERSION);
        // headers.put("Token",App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() :
        // "dltech");
        OkHttpClient okHttpClient =
            new AppOkHttpClient().getInstance().setIsCache(true).setIsNeedHeader(true, headers).init(context);

        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(baseUrl).build();
        apiServer = retrofit.create(ApiServer.class);
    }

    public ApiServer getApiService() {
        return apiServer;
    }

    public void removeCookies() {
        cookieManger.removeCookies();
    }

    public List<Cookie> getCookies() {
        return cookieManger.getCookies();
    }
}
