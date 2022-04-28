package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentFamilyQuizIdentitySelectorBinding;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

public class QuizFamilyIdentityFragment
    extends AbstractFragment<AbstractPresenter, FragmentFamilyQuizIdentitySelectorBinding> {

    private SurveyFragment surveyFragment;

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
            Bundle bundle = new Bundle();
            bundle.putInt("identity", IdentityEnums.PAINTER.getType());
            surveyFragment.setArguments(bundle);
            pushFragment(surveyFragment);
        });

        binding.btFamilyFamilyQuizIdentitySelectorFragment.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("identity", IdentityEnums.FAMILY.getType());
            surveyFragment.setArguments(bundle);
            pushFragment(surveyFragment);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        surveyFragment = new SurveyFragment();
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
