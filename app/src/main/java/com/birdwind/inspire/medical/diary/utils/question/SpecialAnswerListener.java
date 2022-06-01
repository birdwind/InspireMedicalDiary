package com.birdwind.inspire.medical.diary.utils.question;

import com.birdwind.inspire.medical.diary.model.QuestionModel;

public interface SpecialAnswerListener {
    void drawing(QuestionModel questionModel, int position);

    void recordVideo(QuestionModel questionModel, int position);

    void previewVideo(String videoUrl);

    void previewImage(String imageUrl);
}
