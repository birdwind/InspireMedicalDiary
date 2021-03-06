package com.birdwind.inspire.medical.diary.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavController;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentDoctorMainBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.activity.MainActivity;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

import java.util.Stack;

public class DoctorMainFragment extends AbstractFragment<AbstractPresenter, FragmentDoctorMainBinding>
    implements FragNavController.RootFragmentListener, FragNavController.TransactionListener, ToolbarCallback {

    private FragNavController mNavController;

    private FragNavTransactionOptions popFragNavTransactionOptions;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public FragmentDoctorMainBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
        boolean attachToParent) {
        return FragmentDoctorMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

        binding.llScanDoctorMain.setOnClickListener(v -> {
            ((MainActivity) context).openScanFragment();
        });

        binding.llSettingDoctorMain.setOnClickListener(v -> {
            // ((MainActivity)context).pushFragment(new SettingFragment());
            pushFragment(new SettingFragment());

            // startActivity(CameraActivity.class);
        });
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        Intent intent = result.getData();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra("bundle");
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        FragNavTransactionOptions defaultFragNavTransactionOptions =
            FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right,
                R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        mNavController = FragNavController
            .newBuilder(savedInstanceState, getChildFragmentManager(), binding.flDoctorMainActivity.getId())
            .transactionListener(this).rootFragmentListener(this, 1)
            .defaultTransactionOptions(defaultFragNavTransactionOptions).build();
    }

    @Override
    public void doSomething() {

    }

    @Override
    public Fragment getRootFragment(int index) {
        return new DoctorPatientFragment();
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {}

    @Override
    public void pushFragment(Fragment fragment) {
        super.pushFragment(fragment);
        // mNavController.pushFragment(fragment);
        binding.llMenuDoctorMain.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment(popFragNavTransactionOptions);
            Stack<Fragment> fragmentStack = mNavController.getStack(FragNavController.TAB1);
            if (fragmentStack.size() <= 1) {
                binding.llMenuDoctorMain.setVisibility(View.VISIBLE);
            }
        } else {
            return false;
        }
        return true;
    }
}
