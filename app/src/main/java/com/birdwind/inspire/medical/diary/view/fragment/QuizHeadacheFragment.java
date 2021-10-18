package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizHeadacheBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        binding.comQuizQuizHeadacheFragment.ivHeadache1QuizHeadacheComp.setOnClickListener(this);
        binding.comQuizQuizHeadacheFragment.ivHeadache2QuizHeadacheComp.setOnClickListener(this);
        binding.comQuizQuizHeadacheFragment.ivHeadache3QuizHeadacheComp.setOnClickListener(this);
        binding.comQuizQuizHeadacheFragment.ivHeadache4QuizHeadacheComp.setOnClickListener(this);
        binding.comQuizQuizHeadacheFragment.ivHeadache5QuizHeadacheComp.setOnClickListener(this);
        binding.comQuizQuizHeadacheFragment.ivHeadache6QuizHeadacheComp.setOnClickListener(this);
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
        int option1_Id = binding.comQuizQuizHeadacheFragment.rgOption1QuizHeadacheComp.getCheckedRadioButtonId();
        int option2_Id = binding.comQuizQuizHeadacheFragment.rgOption2QuizHeadacheComp.getCheckedRadioButtonId();
        int option3_Id = binding.comQuizQuizHeadacheFragment.rgOption3QuizHeadacheComp.getCheckedRadioButtonId();
        int option4_Id = binding.comQuizQuizHeadacheFragment.rgOption4QuizHeadacheComp.getCheckedRadioButtonId();
        int option5_Id = binding.comQuizQuizHeadacheFragment.rgOption5QuizHeadacheComp.getCheckedRadioButtonId();

        int answer_1 = 0;
        int answer_2 = 0;
        int answer_3 = 0;
        int answer_4 = 0;
        int answer_5 = 0;
        if (option1_Id == binding.comQuizQuizHeadacheFragment.rbOption1NoQuizHeadacheComp.getId()) {
            answer_1 = 0;
        } else if (option1_Id == binding.comQuizQuizHeadacheFragment.rbOption1YesQuizHeadacheComp.getId()) {
            answer_1 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option2_Id == binding.comQuizQuizHeadacheFragment.rbOption2NoQuizHeadacheComp.getId()) {
            answer_2 = 0;
        } else if (option2_Id == binding.comQuizQuizHeadacheFragment.rbOption2YesQuizHeadacheComp.getId()) {
            answer_2 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option3_Id == binding.comQuizQuizHeadacheFragment.rbOption3NoQuizHeadacheComp.getId()) {
            answer_3 = 0;
        } else if (option3_Id == binding.comQuizQuizHeadacheFragment.rbOption3YesQuizHeadacheComp.getId()) {
            answer_3 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option4_Id == binding.comQuizQuizHeadacheFragment.rbOption4NoQuizHeadacheComp.getId()) {
            answer_4 = 0;
        } else if (option4_Id == binding.comQuizQuizHeadacheFragment.rbOption4YesQuizHeadacheComp.getId()) {
            answer_4 = 1;
        } else {
            isCanSubmit = false;
        }
        if (option5_Id == binding.comQuizQuizHeadacheFragment.rbOption5NoQuizHeadacheComp.getId()) {
            answer_5 = 0;
        } else if (option5_Id == binding.comQuizQuizHeadacheFragment.rbOption5YesQuizHeadacheComp.getId()) {
            answer_5 = 1;
        } else {
            isCanSubmit = false;
        }
        answers.add(answer_1);
        answers.add(answer_2);
        answers.add(answer_3);
        answers.add(answer_4);
        answers.add(answer_5);
        for (int i = 0; i < binding.comQuizQuizHeadacheFragment.llHeadachePositionQuizHeadacheComp
            .getChildCount(); i++) {
            View headachePositionView =
                binding.comQuizQuizHeadacheFragment.llHeadachePositionQuizHeadacheComp.getChildAt(i);
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
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache1QuizHeadacheComp, false);
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache2QuizHeadacheComp, false);
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache3QuizHeadacheComp, false);
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache4QuizHeadacheComp, false);
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache5QuizHeadacheComp, false);
        updateHeadachePictureState(binding.comQuizQuizHeadacheFragment.ivHeadache6QuizHeadacheComp, false);
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
