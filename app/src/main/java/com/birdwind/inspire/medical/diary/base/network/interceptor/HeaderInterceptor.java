package com.birdwind.inspire.medical.diary.base.network.interceptor;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers;

    public HeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
