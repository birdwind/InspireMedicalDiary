package com.birdwind.inspire.medical.diary.base.utils;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.google.gson.Gson;
import java.lang.reflect.Method;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

public class SharedPreferencesUtils {

    private static Context context = App.getAppContext();

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences init(Context context) {
        return sharedPreferences = context.getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 存
     *
     * @param key 键
     * @param value 值
     * @param <E> 泛型，自动根据值进行处理
     */
    public static <E> void put(@NonNull String key, @NonNull E value) {
        put(context, key, value);
    }

    /**
     * 取
     *
     * @param key 键
     * @param defaultValue 默认值
     * @param <E> 泛型，自动根据值进行处理
     * @return
     */
    public static <E> E get(@NonNull String key, @NonNull E defaultValue) {
        return get(context, key, defaultValue);
    }

    /**
     * 插件間和宿主共用數據 必须 傳入context
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static <E> void put(Context context, @NonNull String key, @NonNull E value) {
        SharedPreferences.Editor editor = init(context).edit();
        if (value instanceof String || value instanceof Integer || value instanceof Boolean || value instanceof Float
                || value instanceof Long || value instanceof Double) {
            editor.putString(key, String.valueOf(value));
        } else {
            editor.putString(key, GsonUtils.toJson(value));
        }
        SPCompat.apply(editor);
    }

    /**
     * 插件間和宿主共用數據 必须 傳入context
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static <E> E get(Context context, @NonNull String key, @NonNull E defaultValue) {
        String value = init(context).getString(key, String.valueOf(defaultValue));

        if (defaultValue instanceof String) {
            return (E) value;
        }
        if (defaultValue instanceof Integer) {
            return (E) Integer.valueOf(value);
        }
        if (defaultValue instanceof Boolean) {
            return (E) Boolean.valueOf(value);
        }
        if (defaultValue instanceof Float) {
            return (E) Float.valueOf(value);
        }
        if (defaultValue instanceof Long) {
            return (E) Long.valueOf(value);
        }
        if (defaultValue instanceof Double) {
            return (E) Double.valueOf(value);
        }
        // json为null的时候返回对象为null,gson已处理
        return (E) new Gson().fromJson(value, defaultValue.getClass());
    }

    /**
     * 移除某個key值已經對應的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.remove(key);
        SPCompat.apply(editor);
    }

    public static void remove(String key) {
        remove(context, key);
    }

    /**
     * 清除所有數據
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = init(context).edit();
        editor.clear();
        SPCompat.apply(editor);
    }

    public static void clear() {
        clear(context);
    }

    /**
     * 查詢某個key已經存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        return init(context).contains(key);
    }

    public static boolean contains(String key) {
        return contains(context, key);
    }

    /**
     * 返回所有的键值對
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        return init(context).getAll();
    }

    public static Map<String, ?> getAll() {
        return init(context).getAll();
    }

    /**
     * 保存對象到sharedPreferences文件中 被保存的對象需要實現 Serializable 接口
     *
     * @param key
     * @param value
     */
//    public static void saveObject(String key, Object value) {
//        put(key, value);
//    }

    /**
     * desc:獲取保存的Object對象
     *
     * @param key
     * @return modified:
     */
    public static <T> T readObject(String key, Class<T> clazz) {
        try {
            return (T) get(key, clazz.newInstance());
        } catch (Exception e) {
            LogUtils.exception(e);
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 創建一个解決SharedPreferencesCompat.apply方法的一个兼容類
     *
     * @author
     */
    private static class SPCompat {
        private static final Method S_APPLY_METHOD = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                LogUtils.print("SharedPreferencesUtils", e.getMessage());
            }
            return null;
        }

        /**
         * 如果找到則使用apply執行，否則使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (S_APPLY_METHOD != null) {
                    S_APPLY_METHOD.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                LogUtils.print("SharedPreferencesUtils", e.getMessage());
            }
            editor.commit();
        }
    }
}
