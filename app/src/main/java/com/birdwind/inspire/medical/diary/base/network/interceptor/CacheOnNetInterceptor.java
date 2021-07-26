package com.birdwind.inspire.medical.diary.base.network.interceptor;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheOnNetInterceptor implements Interceptor {

    private int maxCacheTimeSecond = 0;

    public CacheOnNetInterceptor(int maxCacheTimeSecond) {
        this.maxCacheTimeSecond = maxCacheTimeSecond;
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
                .removeHeader("Pragma")// 清除header，因為服務器如果不支持，會返回一些干擾，造成cache不成功
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=" + maxCacheTimeSecond)
                .build();
    }
}
