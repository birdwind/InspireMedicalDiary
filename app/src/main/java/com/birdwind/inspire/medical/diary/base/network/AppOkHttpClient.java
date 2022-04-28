package com.birdwind.inspire.medical.diary.base.network;

import android.content.Context;

import com.birdwind.inspire.medical.diary.base.network.interceptor.CacheNoNetInterceptor;
import com.birdwind.inspire.medical.diary.base.network.interceptor.CacheOnNetInterceptor;
import com.birdwind.inspire.medical.diary.base.network.interceptor.HeaderInterceptor;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

public class AppOkHttpClient {

    private static AppOkHttpClient AppOkHttpClient;

    private static OkHttpClient okHttpClient;

    private static CookieManger cookieManger;

    private static final int DEFAULT_TIMEOUT = 10;

    private static final int CACHE_SIZE = 10240 * 1024;

    private static final int CACHE_TIMEOUT = 5000;

    private boolean isCache = false;

    private boolean isShowLog = true;

    private boolean isNeedHeader = false;

    private Map<String, String> headers;

    public AppOkHttpClient() {}

    public AppOkHttpClient getInstance() {
        if (AppOkHttpClient == null) {
            AppOkHttpClient = new AppOkHttpClient();
        }
        return AppOkHttpClient;
    }

    public AppOkHttpClient setIsCache(boolean isCache) {
        this.isCache = isCache;
        return AppOkHttpClient;
    }

    public AppOkHttpClient setIsShowLog(boolean isShowLog) {
        this.isShowLog = isShowLog;
        return AppOkHttpClient;
    }

    public AppOkHttpClient setIsNeedHeader(boolean isNeedHeader, Map<String, String> headers) {
        this.isNeedHeader = isNeedHeader;
        this.headers = headers;
        return AppOkHttpClient;
    }

    public OkHttpClient init(Context context) {
        if (cookieManger == null) {
            cookieManger = new CookieManger(context);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            // .addInterceptor(new ConnectRetryInterceptor())
            // .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
            .cookieJar(new CookieManger(context)).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true);
        if (isCache) {
            builder.addNetworkInterceptor(new CacheOnNetInterceptor(CACHE_TIMEOUT));
            builder.addInterceptor(new CacheNoNetInterceptor(CACHE_TIMEOUT, context));
        }

        if (isShowLog) {
            builder.addInterceptor(interceptor);
        }

        if (isNeedHeader) {
            builder.addNetworkInterceptor(new HeaderInterceptor(headers));
        }

        builder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));

        okHttpClient = builder.build();
        return okHttpClient;
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String responseBody = response.body().string();
            RequestBody requestBody = request.body();
            String requestBodyString = "";
            if (requestBody != null) {
                final Buffer buffer = new Buffer();
                try {
                    Objects.requireNonNull(requestBody).writeTo(buffer);
                    requestBodyString = buffer.readUtf8();
                } catch (IOException e) {
                    LogUtils.exceptionTAG("AppOkHttpClient", e);
                }
            }

            String responseHeader = response.headers().toString();
            String cookies = cookieManger.getCookies().toString();

            LogUtils.d("----------Request Start----------------");
            LogUtils.d("| Cookies: " + cookies);
            LogUtils.d("| Request: " + request);
            LogUtils.d("| RequestUrl: " + request.url());
            LogUtils.d("| RequestMethod: " + request.method());
            LogUtils.d("| RequestHeader: " + request.headers().toString());
            LogUtils.d("| RequestBody: " + requestBodyString);
            LogUtils.d("| ResponseHeader: " + responseHeader);
            LogUtils.d("| ResponseBody: " + responseBody);
            LogUtils.d("----------Request End:" + duration + "毫秒----------");
            return response.newBuilder().body(ResponseBody.create(responseBody, mediaType)).build();
        }
    };
}
