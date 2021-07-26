package com.birdwind.inspire.medical.diary.utils;

public class SignalClient {
    private static SignalClient signalClient;

    public SignalClient() {

    }

    public static SignalClient getInstance(){
        if(signalClient == null) {
            signalClient = new SignalClient();
        }
        return signalClient;
    }

    public void start(){

    }
}
