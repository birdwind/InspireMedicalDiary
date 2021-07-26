package com.birdwind.init.enums;

public enum ExampleEnums {
    NOTIFY(0), ANNOUNCEMENT(1);

    private int type;

    ExampleEnums(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
