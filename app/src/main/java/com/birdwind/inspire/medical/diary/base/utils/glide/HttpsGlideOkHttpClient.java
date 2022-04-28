package com.birdwind.inspire.medical.diary.base.utils.glide;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.network.interceptor.HeaderInterceptor;

import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class HttpsGlideOkHttpClient {
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {}

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {}

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            Map<String, String> headers = new HashMap<>();
            headers.put("OS", "A");
            headers.put("Ver", Config.APP_VERSION);
            headers.put("Token",
                App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() : "0000");
            builder.addNetworkInterceptor(new HeaderInterceptor(headers));

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
