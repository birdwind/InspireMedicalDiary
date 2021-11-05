package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentFamilyQuizIdentitySelectorBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class QuizFamilyIdentityFragment
    extends AbstractFragment<AbstractPresenter, FragmentFamilyQuizIdentitySelectorBinding> {
    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentFamilyQuizIdentitySelectorBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentFamilyQuizIdentitySelectorBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btPatientFamilyQuizIdentitySelectorFragment.setOnClickListener(v -> {
            switch (App.userModel.getDiseaseEnums()) {
                case ALZHEIMER:
                    pushFragment(new RecordFragment());
                    break;
                case PERKINS:
                    pushFragment(new QuizPerkinsPatientFragment());
                    break;
            }
        });

        binding.btFamilyFamilyQuizIdentitySelectorFragment.setOnClickListener(v -> {
            switch (App.userModel.getDiseaseEnums()) {
                case ALZHEIMER:
                    pushFragment(new QuizAkzhimerFamilyFragment());
                    break;
                case PERKINS:
                    pushFragment(new QuizPerkinsFamilyFragment());
                    break;
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {

    }

    @Override
    public void pushFragment(Fragment fragment) {
        ((FamilyMainFragment) getParentFragment()).pushFragment(fragment);
    }
}
