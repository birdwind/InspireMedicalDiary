package com.birdwind.inspire.medical.diary.view.fragment;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentPatientMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

import androidx.fragment.app.Fragment;

public class PatientMainFragment extends AbstractFragment<AbstractPresenter, FragmentPatientMainBinding>
    implements FragNavController.RootFragmentListener, FragNavController.TransactionListener {

    private String activityAction;

    private PatientDashboardFragment patientDashboardFragment;

    private FragNavController mNavController;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentPatientMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentPatientMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.llQrcodePatientMainFragment.setOnClickListener(v -> {
            pushFragment(new QRCodeFragment());
        });
        binding.llQuizPatientMainFragment.setOnClickListener(v -> {
            openQuizFragment();
        });
        binding.llSettingPatientMainFragment.setOnClickListener(v -> {
            pushFragment(new SettingFragment());
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            activityAction = bundle.getString("action");
        }
        FragNavTransactionOptions defaultFragNavTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        mNavController = FragNavController
            .newBuilder(savedInstanceState, getChildFragmentManager(), binding.flPatientMainFragment.getId())
            .transactionListener(this).rootFragmentListener(this, 1)
            .defaultTransactionOptions(defaultFragNavTransactionOptions).build();
    }

    @Override
    public void initView() {}

    @Override
    public void doSomething() {
        if (activityAction != null && activityAction.equals("quiz")) {
            openQuizFragment();
        }
    }

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
        super.pushFragment(fragment);
        // mNavController.pushFragment(fragment);
        binding.llMenuPatientMainFragment.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
            Stack<Fragment> fragmentStack = mNavController.getStack(FragNavController.TAB1);
            if (fragmentStack.size() <= 1) {
                binding.llMenuPatientMainFragment.setVisibility(View.VISIBLE);
            }
        } else {
            return false;
        }
        return true;
    }

    public void popFragmentToRoot(int patientDashboardTabIndex) {
        mNavController.clearStack();
        patientDashboardFragment.switchTab(patientDashboardTabIndex);
        binding.llMenuPatientMainFragment.setVisibility(View.VISIBLE);
    }

    private void openQuizFragment() {
        if (App.isStepByStep) {
            pushFragment(new QuestionFragment());
        } else {
            if (hasPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)) {
                pushFragment(new SurveyFragment());
            } else {
                showToast(getString(R.string.error_common_permission_denied_some));
            }
        }
    }
}
