package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizHeadacheBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

import java.util.ArrayList;
import java.util.List;

public class QuizHeadacheFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizHeadacheBinding>
    implements QuizContentView, ToolbarCallback, View.OnClickListener {

    @Override
    public QuizContentPresenter createPresenter() {
        return new QuizContentPresenter(this);
    }

    @Override
    public FragmentQuizHeadacheBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuizHeadacheBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ivHeadache1QuizHeadacheFragment.setOnClickListener(this);
        binding.ivHeadache2QuizHeadacheFragment.setOnClickListener(this);
        binding.ivHeadache3QuizHeadacheFragment.setOnClickListener(this);
        binding.ivHeadache4QuizHeadacheFragment.setOnClickListener(this);
        binding.ivHeadache5QuizHeadacheFragment.setOnClickListener(this);
        binding.ivHeadache6QuizHeadacheFragment.setOnClickListener(this);
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
        int option1_Id = binding.rgOption1QuizHeadacheFragment.getCheckedRadioButtonId();
        int option2_Id = binding.rgOption2QuizHeadacheFragment.getCheckedRadioButtonId();
        int option3_Id = binding.rgOption3QuizHeadacheFragment.getCheckedRadioButtonId();
        int option4_Id = binding.rgOption4QuizHeadacheFragment.getCheckedRadioButtonId();
        int option5_Id = binding.rgOption5QuizHeadacheFragment.getCheckedRadioButtonId();

        int answer_1 = 0;
        int answer_2 = 0;
        int answer_3 = 0;
        int answer_4 = 0;
        int answer_5 = 0;
        if (option1_Id == binding.rbOption1NoQuizHeadacheFragment.getId()) {
            answer_1 = 0;
        } else if (option1_Id == binding.rbOption1YesQuizHeadacheFragment.getId()) {
            answer_1 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option2_Id == binding.rbOption2NoQuizHeadacheFragment.getId()) {
            answer_2 = 0;
        } else if (option2_Id == binding.rbOption2YesQuizHeadacheFragment.getId()) {
            answer_2 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option3_Id == binding.rbOption3NoQuizHeadacheFragment.getId()) {
            answer_3 = 0;
        } else if (option3_Id == binding.rbOption3YesQuizHeadacheFragment.getId()) {
            answer_3 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option4_Id == binding.rbOption4NoQuizHeadacheFragment.getId()) {
            answer_4 = 0;
        } else if (option4_Id == binding.rbOption4YesQuizHeadacheFragment.getId()) {
            answer_4 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.rbOption5NoQuizHeadacheFragment.getId()) {
            answer_5 = 0;
        } else if (option5_Id == binding.rbOption5YesQuizHeadacheFragment.getId()) {
            answer_5 = 1;
        } else {
            isCanSubmit = false;
        }
        answers.add(answer_1);
        answers.add(answer_2);
        answers.add(answer_3);
        answers.add(answer_4);
        answers.add(answer_5);
        for (int i = 0; i < binding.llHeadachePositionQuizHeadacheFragment.getChildCount(); i++) {
            View headachePositionView = binding.llHeadachePositionQuizHeadacheFragment.getChildAt(i);
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
        updateHeadachePictureState(binding.ivHeadache1QuizHeadacheFragment, false);
        updateHeadachePictureState(binding.ivHeadache2QuizHeadacheFragment, false);
        updateHeadachePictureState(binding.ivHeadache3QuizHeadacheFragment, false);
        updateHeadachePictureState(binding.ivHeadache4QuizHeadacheFragment, false);
        updateHeadachePictureState(binding.ivHeadache5QuizHeadacheFragment, false);
        updateHeadachePictureState(binding.ivHeadache6QuizHeadacheFragment, false);
        updateHeadachePictureState(view, true);
    }

    private void updateHeadachePictureState(View v, boolean isSelected) {
        v.setAlpha(isSelected ? 1f : 0.6f);
        v.setTag(isSelected);
    }

    @Override
    public void submitSuccess(boolean isSuccess) {
        if (isSuccess) {
            ((MainActivity) context).onBackPressed();
        }
    }
}
