package com.birdwind.inspire.medical.diary.service;

import static com.microsoft.signalr.HubConnectionState.DISCONNECTED;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.Config;
import com.birdwind.inspire.medical.diary.base.utils.GsonUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.model.PainterDiseaseModel;
import com.birdwind.inspire.medical.diary.model.response.ChatResponse;
import com.birdwind.inspire.medical.diary.receiver.BroadcastReceiverAction;
import com.birdwind.inspire.medical.diary.sqlLite.service.ChatService;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class InspireDiaryWebSocketService extends Service {

    private static HubConnection hubConnection;

    private Handler handler; // to display Toast message

    private final IBinder binder = new LocalBinder();

    private ChatService chatService;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        chatService = new ChatService(App.getAppContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSignalR();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (hubConnection != null) {
            if (hubConnection.getConnectionState() != DISCONNECTED) {
                hubConnection.stop();
            }
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        initSignalR();
        return binder;
    }

    public class LocalBinder extends Binder {
        public InspireDiaryWebSocketService getService() {
            return InspireDiaryWebSocketService.this;
        }
    }

    private void initSignalR() {
        if (hubConnection == null) {
            if (App.userModel != null && !App.userModel.getToken().isEmpty()) {
                hubConnection =
                    HubConnectionBuilder.create(Config.BASE_URL + "MessageHub?Token=" + App.userModel.getToken())
                        .withHeader("Token", App.userModel.getToken()).build();
                hubConnection.setKeepAliveInterval(5 * 1000);
                hubConnection.on("ReceiveMessage", (json) -> {
                    // {"ID":17,"PID":78,"FromUID":6,"Content":"yyyy","Identity":2,"FromName":"\u8521\u52DD\u8C48","PhotoUrl":"https://toxto.top/Images/F2.png","Self":false,"TimeC":"2021-08-22T01:38:42.6201791+08:00"}
                    LogUtils.d("WebSocket-ReceiveMessage", json);

                    ChatResponse.Response chat = GsonUtils.parseJsonToBean(json, ChatResponse.Response.class);
                    chatService.save(chat);

                    Intent intent = new Intent(BroadcastReceiverAction.LISTENER_CHAT_MESSAGE);
                    Bundle bundle = new Bundle();
                    bundle.putLong("chatID", chat.getID());
                    intent.putExtras(bundle);

                    sendBroadcast(intent);

                }, String.class);

                hubConnection.on("CreatePatientSuccess", (json) -> {
                    LogUtils.d("WebSocket-CreatePatientSuccess", json);

                    PainterDiseaseModel painterDiseaseModel =
                        GsonUtils.parseJsonToBean(json, PainterDiseaseModel.class);

                    Intent intent = new Intent(BroadcastReceiverAction.PAINTER_HAVE_DOCTOR);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("painterDiseaseModel", painterDiseaseModel);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);

                }, String.class);

                hubConnection.onClosed(exception -> {
                    LogUtils.e(exception.getMessage());
                    reLink();
                });

                try {
                    hubConnection.start().blockingAwait();
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }
            }
        }
    }

    private void reLink() {
        if (hubConnection != null && hubConnection.getConnectionState() == DISCONNECTED) {
            hubConnection.start();
        } else {
            reLink();
        }
    }

    public static boolean isConnected() {
        if (hubConnection != null) {
            return hubConnection.getConnectionState() != DISCONNECTED;
        }
        return false;
    }
}
