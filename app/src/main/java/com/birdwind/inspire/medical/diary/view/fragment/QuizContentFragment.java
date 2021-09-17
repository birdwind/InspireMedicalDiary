package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentQuizBinding;
import com.birdwind.inspire.medical.diary.presenter.QuizContentPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.QuizContentView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class QuizContentFragment extends AbstractFragment<QuizContentPresenter, FragmentQuizBinding>
    implements QuizContentView {

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

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void doSomething() {}
}
