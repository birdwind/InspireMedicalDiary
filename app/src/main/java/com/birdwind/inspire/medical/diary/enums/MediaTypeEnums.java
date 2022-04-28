package com.birdwind.inspire.medical.diary.enums;

public enum MediaTypeEnums {
    NULL(0), IMAGE(1), AUDIO(2), VIDEO(3);

    private int value;

    MediaTypeEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MediaTypeEnums parseEnumsByValue(int value) {
        for (MediaTypeEnums mediaTypeEnums : MediaTypeEnums.values()) {
            if (mediaTypeEnums.value == value) {
                return mediaTypeEnums;
            }
        }
        return null;
    }
}
