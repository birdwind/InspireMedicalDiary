package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

import java.util.ArrayList;
import java.util.List;

public class QuizContentFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizBinding>
    implements QuizContentView, ToolbarCallback, View.OnClickListener {

    @Override
    public QuizContentPresenter createPresenter() {
        return new QuizContentPresenter(this);
    }

    @Override
    public FragmentQuizBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentQuizBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ivHeadache1QuizFragment.setOnClickListener(this);
        binding.ivHeadache2QuizFragment.setOnClickListener(this);
        binding.ivHeadache3QuizFragment.setOnClickListener(this);
        binding.ivHeadache4QuizFragment.setOnClickListener(this);
        binding.ivHeadache5QuizFragment.setOnClickListener(this);
        binding.ivHeadache6QuizFragment.setOnClickListener(this);
    }

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
        int option1_Id = binding.rgOption1QuizFragment.getCheckedRadioButtonId();
        int option2_Id = binding.rgOption2QuizFragment.getCheckedRadioButtonId();
        int option3_Id = binding.rgOption3QuizFragment.getCheckedRadioButtonId();
        int option4_Id = binding.rgOption4QuizFragment.getCheckedRadioButtonId();
        int option5_Id = binding.rgOption5QuizFragment.getCheckedRadioButtonId();

        int answer_1 = 0;
        int answer_2 = 0;
        int answer_3 = 0;
        int answer_4 = 0;
        int answer_5 = 0;
        if (option1_Id == binding.rbOption1NoQuizFragment.getId()) {
            answer_1 = 0;
        } else if (option1_Id == binding.rbOption1YesQuizFragment.getId()) {
            answer_1 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option2_Id == binding.rbOption2NoQuizFragment.getId()) {
            answer_2 = 0;
        } else if (option2_Id == binding.rbOption2YesQuizFragment.getId()) {
            answer_2 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option3_Id == binding.rbOption3NoQuizFragment.getId()) {
            answer_3 = 0;
        } else if (option3_Id == binding.rbOption3YesQuizFragment.getId()) {
            answer_3 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option4_Id == binding.rbOption4NoQuizFragment.getId()) {
            answer_4 = 0;
        } else if (option4_Id == binding.rbOption4YesQuizFragment.getId()) {
            answer_4 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption5NoQuizFragment.getId()) {
            answer_5 = 0;
        } else if (option5_Id == binding.rbOption5YesQuizFragment.getId()) {
            answer_5 = 1;
        } else {
            isCanSubmit = false;
        }
        answers.add(answer_1);
        answers.add(answer_2);
        answers.add(answer_3);
        answers.add(answer_4);
        answers.add(answer_5);
        for (int i = 0; i < binding.llHeadachePositionQuizFragment.getChildCount(); i++) {
            View headachePositionView = binding.llHeadachePositionQuizFragment.getChildAt(i);
            if (headachePositionView instanceof ImageView) {
                if (headachePositionView.getTag() != null && (Boolean) headachePositionView.getTag()) {
                    answers.add(i);
                    break;
                }
            }
        }
        if (isCanSubmit) {
            presenter.submit(answers);
        } else {
            showToast("輸出");
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            selectHeadachePosition(v);
        }
    }

    private void selectHeadachePosition(View view) {
        updateHeadachePictureState(binding.ivHeadache1QuizFragment, false);
        updateHeadachePictureState(binding.ivHeadache2QuizFragment, false);
        updateHeadachePictureState(binding.ivHeadache3QuizFragment, false);
        updateHeadachePictureState(binding.ivHeadache4QuizFragment, false);
        updateHeadachePictureState(binding.ivHeadache5QuizFragment, false);
        updateHeadachePictureState(binding.ivHeadache6QuizFragment, false);
        updateHeadachePictureState(view, true);
    }

    private void updateHeadachePictureState(View v, boolean isSelected) {
        v.setAlpha(isSelected ? 1f : 0.6f);
        v.setTag(isSelected);
    }

    @Override
    public void submitSuccess(boolean isSuccess) {
        if (isSuccess) {
            ((MainActivity)context).onBackPressed();
        }
    }
}
