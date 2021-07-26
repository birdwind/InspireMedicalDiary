package com.birdwind.init.base.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.text.TextUtils;

public class GsonUtils {

    public static <T> T parseJsonToBean(String json, Type type) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").serializeNulls().create();
        T t = null;
        try {
            t = gson.fromJson(json, type);
        } catch (Exception e) {
            LogUtils.exception(e);
            return null;
        }
        return t;

    }

    public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").serializeNulls().create();
        T t = null;
        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            LogUtils.exception(e);
            return null;
        }
        return t;
    }

    public static <T> List<T> parseJsonFromJsonArray(String json, Class<T> clazz) {
        List<T> lst = null;
        try {
            lst = new ArrayList<T>();

            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                if (elem instanceof JsonObject) {
                    lst.add(parseJsonToBean(elem.getAsJsonObject().getAsString(), clazz));
                } else {
                    lst.add(parseJsonToBean(elem.getAsString(), clazz));
                }
            }
        } catch (JsonSyntaxException e) {
            LogUtils.exception(e);
            return null;
        }

        return lst;
    }

    public static String toJson(Object target) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(target);
    }

    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            LogUtils.exception(e);
            // e.printStackTrace();
            return "";
        }

        return value;
    }

    public static Map<String, Object> parseJsonToMap(String json) {
        return new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
    }
}
