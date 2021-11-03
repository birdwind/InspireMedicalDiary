package com.birdwind.inspire.medical.diary.base.network;

import java.util.HashMap;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiServer {

    @GET
    Observable<ResponseBody> executeGet(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @HeaderMap HashMap<String, Object> headerMap);

    @POST
    Observable<ResponseBody> executePost(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @Body RequestBody requestBody, @HeaderMap HashMap<String, Object> headerMap);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> executePostFormUrlEncode(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @FieldMap HashMap<String, Object> fieldMap, @HeaderMap HashMap<String, Object> headerMap);

    @PUT
    Observable<ResponseBody> executePut(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @Body RequestBody requestBody, @HeaderMap HashMap<String, Object> headerMap);

    @FormUrlEncoded
    @PUT
    Observable<ResponseBody> executePutFormUrlEncode(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @FieldMap HashMap<String, Object> fieldMap, @HeaderMap HashMap<String, Object> headerMap);

    @DELETE
    Observable<RequestBody> executeDelete(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @Body RequestBody requestBody, @HeaderMap HashMap<String, Object> headerMap);

    @FormUrlEncoded
    @DELETE
    Observable<RequestBody> executeDeleteFormUrlEncode(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @FieldMap HashMap<String, Object> fieldMap, @HeaderMap HashMap<String, Object> headerMap);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @HeaderMap HashMap<String, Object> headerMap, @Part MultipartBody.Part part);

    /**
     * 上傳文件
     */
    @POST
    Observable<ResponseBody> executePost(@Url String url, @QueryMap HashMap<String, Object> paramMap,
        @Body MultipartBody multipartBody, @HeaderMap HashMap<String, Object> headerMap);

    @POST
    Observable<ResponseBody> uploadFiles(@Url String url, @Part("filename") String description,
        @PartMap HashMap<String, Object> maps, @HeaderMap HashMap<String, Object> headerMap);
}
