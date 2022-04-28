package com.birdwind.inspire.medical.diary.enums;

public enum QuestionTypeEnums {
    BOOLEAN(0), MULTIPLE(1), NUMBER(2), TEXT(3), IMAGE(4), AUDIO(5), VIDEO(6);

    private int value;

    QuestionTypeEnums(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static QuestionTypeEnums parseEnumsByValue(int value) {
        for (QuestionTypeEnums questionTypeEnums : QuestionTypeEnums.values()) {
            if (questionTypeEnums.value == value) {
                return questionTypeEnums;
            }
        }
        return null;
    }
}
