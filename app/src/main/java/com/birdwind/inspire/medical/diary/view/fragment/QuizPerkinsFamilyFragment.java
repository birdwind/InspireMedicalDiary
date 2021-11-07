package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizPerkinsFamilyBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.utils.QuizUtils;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuizPerkinsFamilyFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizPerkinsFamilyBinding>
    implements QuizContentView, ToolbarCallback, View.OnClickListener {

    @Override
    public QuizContentPresenter createPresenter() {
        return new QuizContentPresenter(this);
    }

    @Override
    public FragmentQuizPerkinsFamilyBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentQuizPerkinsFamilyBinding.inflate(getLayoutInflater());
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
        presenter.submit(QuizUtils.parseAnswerList(binding.comQuizQuizPerkinsFamilyFragment.getRoot()), true);

    }

    @Override
    public void onClick(View v) {}

    @Override
    public void submitSuccess(boolean isSuccess, String msg) {
        if (isSuccess) {
            if(App.userModel.isProxy()){
                ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_3);
            }else{
                ((FamilyMainFragment) getParentFragment()).popFragmentToRoot(PatientDashboardFragment.TAB_2);
            }
        } else {
            showDialog(getString(R.string.common_dialog_title), msg, null);
        }
    }
}
