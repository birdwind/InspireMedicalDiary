package com.birdwind.inspire.medical.diary.base.utils.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.network.AppOkHttpClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

@GlideModule
@Excludes(OkHttpLibraryGlideModule.class) // initialize OkHttp manually
public class GlobalGlideConfig extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        Map<String, String> headers = new HashMap<>();
        headers.put("OS", "A");
        headers.put("Ver", Config.APP_VERSION);
        headers.put("Token",
            App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() : "0000");
        OkHttpClient okHttpClient =
            new AppOkHttpClient().getInstance().setIsCache(true).setIsNeedHeader(true, headers).init(context);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);

        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。 DiskCacheStrategy.DATA： 表示只缓存原始图片。 DiskCacheStrategy.RESOURCE：
         * 表示只缓存转换过后的图片。 DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。 DiskCacheStrategy.AUTOMATIC：
         * 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        builder.setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE));

        /**
         * 优先外部存储作为磁盘缓存目录，防止内部存储文件过大 外部存储目录默认地址为：/sdcard/Android/data/com.sina.weibolite/cache/image_manager_disk_cache
         */
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context));
    }
}
