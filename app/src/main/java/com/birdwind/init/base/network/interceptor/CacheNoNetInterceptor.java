package com.birdwind.init.base.network.interceptor;

import com.birdwind.init.base.utils.NetworkUtils;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import androidx.annotation.NonNull;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheNoNetInterceptor implements Interceptor {

    private int maxCacheTimeSecond = 0;
    private Context applicationContext;

    public CacheNoNetInterceptor(int maxCacheTimeSecond, Context applicationContext) {
        this.maxCacheTimeSecond = maxCacheTimeSecond;
        this.applicationContext = applicationContext;
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtils.isConnected(applicationContext)) {
            CacheControl tempCacheControl = new CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(maxCacheTimeSecond, TimeUnit.SECONDS)
                    .build();
            request = request.newBuilder()
                    .cacheControl(tempCacheControl)
                    .build();
        }
        return chain.proceed(request);
    }
//    @NotNull
//    @Override
//    public Response intercept(@NotNull Chain chain) throws IOException {
//        Request request = chain.request();
//        Response response = chain.proceed(request);
//        return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
//            .header("Cache-Control", "max-age=" + 3600 * 24 * 30).build();
//    }
}
