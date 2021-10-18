package com.birdwind.inspire.medical.diary.base.utils;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.network.AppOkHttpClient;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import okhttp3.OkHttpClient;

public class CustomPicasso {
    private static Picasso sPicasso;

    private static String token = "";

    private static boolean isSameLoginKey;

    private CustomPicasso() {

    }

    public static Picasso getImageLoader(final Context context) {
        if (token.equals(App.userModel.getToken())) {
            isSameLoginKey = true;
        } else {
            isSameLoginKey = false;
            token = App.userModel.getToken();
        }
        if (sPicasso == null || !isSameLoginKey) {
            Map<String, String> header = new HashMap<>();
            header.put("OS", "A");
            header.put("Ver", Config.APP_VERSION);
            header.put("Token",
                App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() : "0000");
            OkHttpClient okHttpClient = new AppOkHttpClient().getInstance().setIsNeedHeader(true, header)
                .setIsCache(false).setIsShowLog(false).init(context);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(okHttpClient));
            sPicasso = builder.build();
        }
        return sPicasso;
    }

}
