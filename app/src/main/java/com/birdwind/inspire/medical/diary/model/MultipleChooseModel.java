package com.birdwind.inspire.medical.diary.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class MultipleChooseModel implements Serializable, MultiItemEntity {
    private int ChoiceID;

    private String AnswerText;

    private String MediaLink;

    private int answerType;

    private boolean isChoose;

    private int value;

    public int getChoiceID() {
        return ChoiceID;
    }

    public void setChoiceID(int choiceID) {
        ChoiceID = choiceID;
    }

    public String getAnswerText() {
        return AnswerText;
    }

    public void setAnswerText(String answerText) {
        AnswerText = answerText;
    }

    public String getMediaLink() {
        return MediaLink;
    }

    public void setMediaLink(String mediaLink) {
        MediaLink = mediaLink;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getItemType() {
        return answerType;
    }
}
