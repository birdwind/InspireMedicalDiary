package com.birdwind.inspire.medical.diary.base.network;

import com.birdwind.inspire.medical.diary.base.utils.LogUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    private Request request;
    private OkHttpClient client;
    private WebSocket webSocket;

    public WebSocketClient(String url) {
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(url)
                .build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void start(WebSocketListener listener) {
        client.dispatcher().cancelAll();
        LogUtils.d("RequestId", request.toString());
        LogUtils.d("ListenerId", listener.toString());
        webSocket = client.newWebSocket(request, listener);
        LogUtils.d("WebSocket", webSocket.toString());
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, null);
        }
        client.dispatcher().executorService().shutdown();
    }
}
