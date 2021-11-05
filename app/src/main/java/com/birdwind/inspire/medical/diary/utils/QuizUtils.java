package com.birdwind.inspire.medical.diary.utils;

import com.warkiz.widget.IndicatorSeekBar;
import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.LinearLayout;

public class QuizUtils {
    public static List<Integer> parseAnswerList(LinearLayout view) {
        List<Integer> answers = new ArrayList<>();

        for (int i = 0; i < view.getChildCount(); i++) {
            LinearLayout questionLinearLayout = (LinearLayout) view.getChildAt(i);

            for (int j = 0; j < questionLinearLayout.getChildCount(); j++) {
                View questionView = questionLinearLayout.getChildAt(j);
                if (questionView instanceof IndicatorSeekBar) {
                    IndicatorSeekBar indicatorSeekBar = (IndicatorSeekBar) questionView;
                    answers.add(indicatorSeekBar.getProgress());
                }
            }
        }

        return answers;
    }
}
