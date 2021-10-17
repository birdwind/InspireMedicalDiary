package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizAkzhimerBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

import java.util.ArrayList;
import java.util.List;

public class QuizAkzhimerFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizAkzhimerBinding>
    implements QuizContentView, ToolbarCallback, View.OnClickListener {

    @Override
    public QuizContentPresenter createPresenter() {
        return new QuizContentPresenter(this);
    }

    @Override
    public FragmentQuizAkzhimerBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuizAkzhimerBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {}

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}

    @Override
    public String setRightButtonText() {
        return getString(R.string.quiz_submit);
    }

    @Override
    public ToolbarCallback setToolbarCallback() {
        return this;
    }

    @Override
    public void clickTopBarRightTextButton(View view) {
        List<Integer> answers = new ArrayList<>();
        boolean isCanSubmit = true;
        int option1_Id = binding.rgOption1QuizAkzhimerFragment.getCheckedRadioButtonId();
        int option2_Id = binding.rgOption2QuizAkzhimerFragment.getCheckedRadioButtonId();
        int option3_Id = binding.rgOption3QuizAkzhimerFragment.getCheckedRadioButtonId();
        int option4_Id = binding.rgOption4QuizAkzhimerFragment.getCheckedRadioButtonId();
        int option5_Id = binding.rgOption5QuizAkzhimerFragment.getCheckedRadioButtonId();

        int answer_1 = 0;
        int answer_2 = 0;
        int answer_3 = 0;
        int answer_4 = 0;
        int answer_5 = 0;
        int answer_6 = 0;
        int answer_7 = 0;
        int answer_8 = 0;
        int answer_9 = 0;
        int answer_10 = 0;
        int answer_11 = 0;
        int answer_12 = 0;

        if (option1_Id == binding.rbOption1NoQuizAkzhimerFragment.getId()) {
            answer_1 = 0;
        } else if (option1_Id == binding.rbOption1YesQuizAkzhimerFragment.getId()) {
            answer_1 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option2_Id == binding.rbOption2NoQuizAkzhimerFragment.getId()) {
            answer_2 = 0;
        } else if (option2_Id == binding.rbOption2YesQuizAkzhimerFragment.getId()) {
            answer_2 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option3_Id == binding.rbOption3NoQuizAkzhimerFragment.getId()) {
            answer_3 = 0;
        } else if (option3_Id == binding.rbOption3YesQuizAkzhimerFragment.getId()) {
            answer_3 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option4_Id == binding.rbOption4NoQuizAkzhimerFragment.getId()) {
            answer_4 = 0;
        } else if (option4_Id == binding.rbOption4YesQuizAkzhimerFragment.getId()) {
            answer_4 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption5NoQuizAkzhimerFragment.getId()) {
            answer_5 = 0;
        } else if (option5_Id == binding.rbOption5YesQuizAkzhimerFragment.getId()) {
            answer_5 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption6NoQuizAkzhimerFragment.getId()) {
            answer_6 = 0;
        } else if (option5_Id == binding.rbOption6YesQuizAkzhimerFragment.getId()) {
            answer_6 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption7NoQuizAkzhimerFragment.getId()) {
            answer_7 = 0;
        } else if (option5_Id == binding.rbOption7YesQuizAkzhimerFragment.getId()) {
            answer_7 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption8NoQuizAkzhimerFragment.getId()) {
            answer_8 = 0;
        } else if (option5_Id == binding.rbOption8YesQuizAkzhimerFragment.getId()) {
            answer_8 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption9NoQuizAkzhimerFragment.getId()) {
            answer_9 = 0;
        } else if (option5_Id == binding.rbOption9YesQuizAkzhimerFragment.getId()) {
            answer_9 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption10NoQuizAkzhimerFragment.getId()) {
            answer_10 = 0;
        } else if (option5_Id == binding.rbOption10YesQuizAkzhimerFragment.getId()) {
            answer_10 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption5NoQuizAkzhimerFragment.getId()) {
            answer_11 = 0;
        } else if (option5_Id == binding.rbOption11YesQuizAkzhimerFragment.getId()) {
            answer_11 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption12NoQuizAkzhimerFragment.getId()) {
            answer_12 = 0;
        } else if (option5_Id == binding.rbOption12YesQuizAkzhimerFragment.getId()) {
            answer_12 = 1;
        } else {
            isCanSubmit = false;
        }
        answers.add(answer_1);
        answers.add(answer_2);
        answers.add(answer_3);
        answers.add(answer_4);
        answers.add(answer_5);
        answers.add(answer_6);
        answers.add(answer_7);
        answers.add(answer_8);
        answers.add(answer_9);
        answers.add(answer_10);
        answers.add(answer_11);
        answers.add(answer_12);
        if (isCanSubmit) {
            presenter.submit(answers);
        } else {
            showToast("尚有題目還沒填寫");
        }
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void submitSuccess(boolean isSuccess) {
        if (isSuccess) {
            ((MainActivity) context).onBackPressed();
        }
    }
}
