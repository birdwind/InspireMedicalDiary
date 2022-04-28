package com.birdwind.inspire.medical.diary.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class QuestionModel implements Serializable, MultiItemEntity {
    private int QuestionID;

    private String QuestionText;

    private int AnswerType;

    private LinkedList<MultipleChooseModel> MultipleChoose;

    private String MediaLink;

    private int MediaType;

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }

    public int getAnswerType() {
        return AnswerType;
    }

    public void setAnswerType(int answerType) {
        AnswerType = answerType;
    }

    public LinkedList<MultipleChooseModel> getMultipleChoose() {
        return MultipleChoose;
    }

    public void setMultipleChoose(LinkedList<MultipleChooseModel> multipleChoose) {
        MultipleChoose = multipleChoose;
    }

    public String getMediaLink() {
        return MediaLink;
    }

    public void setMediaLink(String mediaLink) {
        MediaLink = mediaLink;
    }

    public int getMediaType() {
        return MediaType;
    }

    public void setMediaType(int mediaType) {
        this.MediaType = mediaType;
    }

    @Override
    public int getItemType() {
        return AnswerType;
    }
}
