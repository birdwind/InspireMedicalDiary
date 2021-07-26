package com.birdwind.init.utils;

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
