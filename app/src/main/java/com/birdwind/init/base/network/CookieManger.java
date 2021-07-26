package com.birdwind.init.base.network;

import com.birdwind.init.base.network.cookie.PersistentCookieStore;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import android.content.Context;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieManger implements CookieJar {

    private static final String TAG = "CookieManager";

    private static PersistentCookieStore cookieStore;

    public CookieManger(Context context) {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(context);
        }
    }

    public List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }

    public void removeCookies() {
        cookieStore.removeAll();
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        return cookieStore.get(httpUrl);
    }

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> cookies) {
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(httpUrl, item);
            }
        }
    }
}
