package com.birdwind.inspire.medical.diary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentFamilyMainBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;

import java.util.Stack;

public class FamilyMainFragment extends AbstractFragment<AbstractPresenter, FragmentFamilyMainBinding>
    implements FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private PatientDashboardFragment patientDashboardFragment;

    private static FragNavController mNavController;

    private static FragNavTransactionOptions popFragNavTransactionOptions;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentFamilyMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentFamilyMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        // binding.llScanFamilyMainFragment.setOnClickListener(v -> {
        // ((MainActivity) context).openScanFragment();
        // });

        binding.llQuizFamilyMainFragment.setOnClickListener(v -> {
            openQuizFragment();
        });

        binding.llSettingFamilyMainFragment.setOnClickListener(v -> {
            pushFragment(new SettingFragment());
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FragNavTransactionOptions defaultFragNavTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        mNavController = FragNavController
            .newBuilder(savedInstanceState, getChildFragmentManager(), binding.flFamilyMainFragment.getId())
            .transactionListener(this).rootFragmentListener(this, 1)
            .defaultTransactionOptions(defaultFragNavTransactionOptions).build();
    }

    @Override
    public void initView() {

    }

    @Override
    public void doSomething() {}

    @Override
    public Fragment getRootFragment(int index) {
        patientDashboardFragment = new PatientDashboardFragment();
        return patientDashboardFragment;
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

    }

    @Override
    public void pushFragment(Fragment fragment) {
        mNavController.pushFragment(fragment);
        binding.llMenuFamilyMainFragment.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
            Stack<Fragment> fragmentStack = mNavController.getStack(FragNavController.TAB1);
            if (fragmentStack.size() <= 1) {
                binding.llMenuFamilyMainFragment.setVisibility(View.VISIBLE);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (App.userModel.isNeedPatineName()) {
            ((MainActivity) context).showPatientNameDialog();
        }
    }

    public static void replaceFragment(Fragment fragment, boolean isBack) {
        if (isBack) {
            mNavController.replaceFragment(fragment, popFragNavTransactionOptions);
        } else {
            mNavController.replaceFragment(fragment);
        }
    }

    public void popFragmentToRoot(int patientDashboardTabIndex) {
        mNavController.clearStack();
        patientDashboardFragment.switchTab(patientDashboardTabIndex);
        binding.llMenuFamilyMainFragment.setVisibility(View.VISIBLE);
    }

    private void openQuizFragment() {
        if (App.userModel.getDiseaseEnums() == DiseaseEnums.NOT_SET) {
            showDialog(getString(R.string.common_dialog_title), "您的照護對象尚無醫生照料", null);
        } else if (App.userModel.getDiseaseEnums() != DiseaseEnums.HEADACHE) {
            if (App.userModel.isProxy()) {
                pushFragment(new QuizFamilyIdentityFragment());
            } else {
                switch (App.userModel.getDiseaseEnums()) {
                    case ALZHEIMER:
                        pushFragment(new QuizAkzhimerFamilyFragment());
                        break;
                    case PERKINS:
                        pushFragment(new QuizPerkinsFamilyFragment());
                        break;
                }
            }
        } else {
            showDialog(getString(R.string.common_dialog_title), "目前沒有問卷", null);
        }
    }
}
