package com.diary.init.base.network.interceptor;

import com.diary.init.base.utils.LogUtils;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectRetryInterceptor implements Interceptor {
    private int retryNum = 0;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum <= 5) {
            retryNum++;
            LogUtils.e("ConnectRetry", String.valueOf(retryNum));
            response = chain.proceed(request);
        }
        return response;
    }
}
