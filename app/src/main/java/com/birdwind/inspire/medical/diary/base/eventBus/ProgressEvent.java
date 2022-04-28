package com.birdwind.inspire.medical.diary.base.eventBus;

public class ProgressEvent {
    private int position;

    public ProgressEvent(int position) {
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
