package com.birdwind.inspire.medical.diary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragmentNavigationListener;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.ActivitySurveyBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.fragment.SurveyFragment;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;
import com.leaf.library.StatusBarUtil;

public class SurveyActivity extends AbstractActivity<AbstractPresenter, ActivitySurveyBinding>
    implements FragmentNavigationListener {

    private Bundle bundle;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public ActivitySurveyBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return ActivitySurveyBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.componentTopBarSurveyActivity.llBackTopBarComp.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, App.userModel.getIdentityMainColor());
        binding.componentTopBarSurveyActivity.rlBackgroundTopBarComp
            .setBackground(AppCompatResources.getDrawable(this, App.userModel.getIdentityMainColorId()));
        binding.componentTopBarSurveyActivity.llBackTopBarComp.setVisibility(View.VISIBLE);
    }

    @Override
    public void doSomething() {
        SurveyFragment surveyFragment = new SurveyFragment();
        surveyFragment.setArguments(bundle);
        switchFragment(R.id.fl_survey, surveyFragment, getSupportFragmentManager().beginTransaction());
    }

    @Override
    public void pushFragment(Fragment fragment) {

    }

    @Override
    public void replaceFragment(Fragment fragment, boolean isBack) {
    }

    @Override
    public void popIndexTabFragment(int tab) {

    }

    @Override
    public void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode,
        boolean isShowBack, boolean isShowHeader, boolean isShowRightButton, String rightButtonText,
        int rightImageButton, ToolbarCallback toolbarCallback, String leftButtonText) {
    }

    @Override
    public void updateTitle(String title) {
        binding.componentTopBarSurveyActivity.tvTitleTopBarComp.setText(title);
    }
}
