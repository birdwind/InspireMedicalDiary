package com.birdwind.inspire.medical.diary.base.basic;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.network.ApiServer;
import com.birdwind.inspire.medical.diary.base.network.RetrofitManager;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

public abstract class AbstractBasePresenter<V extends BaseView> implements BasePresenter {

    public CompositeDisposable compositeDisposable;

    protected HashMap<String, Object> headerMap;

    protected HashMap<String, Object> fieldMap;

    protected HashMap<String, Object> paramsMap;

    public V baseView;

    protected RetrofitManager retrofitManager;

    protected ApiServer apiServer;

    protected Context context;

    public AbstractBasePresenter(V baseView) {
        this.baseView = baseView;
        this.headerMap = new HashMap<>();
        this.fieldMap = new HashMap<>();
        this.paramsMap = new HashMap<>();
        this.retrofitManager = RetrofitManager.getInstance();
        this.apiServer = retrofitManager.getApiService();
        this.context = App.getAppContext();
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    public void addDisposable(Observable<ResponseBody> flowable, DisposableObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(
            flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected void removeCookie() {
        retrofitManager.removeCookies();
    }

    protected void initMap() {
        initParamMap();
        initHeaderMap();
        initFieldMap();
    }

    private void initParamMap() {
        paramsMap.clear();
    }

    private void initHeaderMap() {
        headerMap.clear();
        headerMap.put("Token",
            App.userModel != null && App.userModel.getToken() != null ? App.userModel.getToken() : "dltech");
    }

    private void initFieldMap() {
        fieldMap.clear();
    }

    protected RequestBody packageToRequestBody(Object obj) {
        String json = GsonUtils.toJson(obj);
        // ObjectMapper oMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // String json = "";
        // try {
        // json = oMapper.writeValueAsString(obj);
        // } catch (IOException e) {
        // LogUtils.exception(e);
        // // e.printStackTrace();
        // }
        return RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
    }

    protected HashMap<String, Object> parseObjectToHashMap(Object object) {
        HashMap tempMap = new HashMap<>();
        ObjectMapper oMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        tempMap = oMapper.convertValue(object, HashMap.class);
        try {
            for (Object key : tempMap.keySet()) {
                Object obj = tempMap.get(key);
                if (obj instanceof List) {
                    String temp = oMapper.writeValueAsString(obj).replace("{", "").replace("}", "").replace("[", "")
                        .replace("]", "").replace("\"", "");
                    tempMap.put(key, temp);
                }
            }
        } catch (Exception e) {
            LogUtils.exception(e);
            // e.printStackTrace();
        }

        return tempMap;
    }

    public String requestBodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
