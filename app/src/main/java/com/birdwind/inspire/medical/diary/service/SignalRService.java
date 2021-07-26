package com.birdwind.inspire.medical.diary.service;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

public class SignalRService extends Service {

    private static HubConnection hubConnection;

    private Handler handler; // to display Toast message

    private final IBinder binder = new LocalBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initSignalR();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if(hubConnection != null){
            if (hubConnection.getConnectionState() != HubConnectionState.DISCONNECTED) {
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
        public SignalRService getService() {
            return SignalRService.this;
        }
    }

    private void initSignalR() {
        if (hubConnection == null) {
//            hubConnection = HubConnectionBuilder.create(Config.BASE_URL + "chatHub")
//                .withHeader("header", App.userModel.getToken()).build();
//            hubConnection.on("ReceiveMessage", (user, message) -> {
//                Intent intent = new Intent("com.dltech.dlclinic.account.logout");
//                intent.putExtra("logout", true);
//                sendBroadcast(intent);
//                LogUtils.d("測試", user + "[,]" + message);
//            }, String.class, String.class);
//            hubConnection.start();
        }
    }
}
