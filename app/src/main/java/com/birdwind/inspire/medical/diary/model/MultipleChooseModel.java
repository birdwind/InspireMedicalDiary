package com.birdwind.inspire.medical.diary.model;

import java.io.Serializable;

public class MultipleChooseModel implements Serializable {
    private int ChoiceID;

    private String AnswerText;

    private String MediaLink;

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
}
