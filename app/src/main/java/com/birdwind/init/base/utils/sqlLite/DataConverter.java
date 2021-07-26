package com.birdwind.init.base.utils.sqlLite;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.birdwind.init.base.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import androidx.room.TypeConverter;

public class DataConverter {

    @TypeConverter
    public Date parseToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public Long parseToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public List<Object> stringToListObject(String value) {
        Type type = new TypeToken<List<Object>>() {}.getType();
        return GsonUtils.parseJsonToBean(value, type);
    }

    @TypeConverter
    public String listObjectToString(List<Object> list) {
        return GsonUtils.toJson(list);
    }

    @TypeConverter
    public List<String> stringToListString(String value) {
        Type type = new TypeToken<List<String>>() {}.getType();
        return GsonUtils.parseJsonToBean(value, type);
    }

    @TypeConverter
    public String listStringToString(List<String> list) {
        return GsonUtils.toJson(list);
    }

}
