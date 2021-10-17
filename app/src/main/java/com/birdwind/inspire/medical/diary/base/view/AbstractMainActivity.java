package com.birdwind.inspire.medical.diary.base.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.SystemUtils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.service.InspireDiaryWebSocketService;
import com.leaf.library.StatusBarUtil;
import com.tbruyelle.rxpermissions3.Permission;

import java.util.List;

public abstract class AbstractMainActivity<P extends AbstractPresenter, VB extends ViewBinding>
    extends AbstractActivity<P, VB> implements AbstractActivity.PermissionRequestListener {

    protected FragNavTransactionOptions popFragNavTransactionOptions;

    protected FragNavTransactionOptions tabToRightFragNavTransactionOptions;

    protected FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    protected View topBarView;

    protected View topMenuView;

    protected View fragmentView;

    protected abstract List<Fragment> initFragmentList();

    @Override
    public void initView() {
        topBarView.setBackgroundColor(App.userModel.getIdentityMainColor());
        StatusBarUtil.setColor(this, App.userModel.getIdentityMainColor());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();

        tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();
    }

    @Override
    public void onBackPressed() {
        int childCount = getSupportFragmentManager().getBackStackEntryCount();
//        if (currentFragmentIndex != PAGE_DEFAULT) {
//            swipeFragment(PAGE_DEFAULT);
//        } else {
//            if (childCount <= 1) {
//                hideTopBar(true);
//            }
//            super.onBackPressed();
//        }
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {
            if (permission.name.equals(Manifest.permission.CAMERA)) {
//                pushFragment(PAGE_SCAN, currentFragmentIndex > PAGE_SCAN);
//                currentFragmentIndex = PAGE_SCAN;
            }
        }
    }

    private void slideHeightAnimation(View view, Animation animation) {
        if (view != null) {
            view.setAnimation(animation);
            view.startAnimation(animation);
        }
    }

    public void swipeFragment(int pageIndex) {
        swipeFragment(pageIndex, false);
    }

    public void swipeFragment(int pageIndex, boolean isIgnore) {
//        boolean isCanPush = true;
//        if (currentFragmentIndex == pageIndex && !isIgnore)
//            return;
//
//        if (pageIndex == PAGE_SCAN) {
//            if (!hasPermission(Manifest.permission.CAMERA)) {
//                isCanPush = false;
//                getPermission(new String[] {Manifest.permission.CAMERA}, this);
//            }
//        }
//
//        if (isCanPush) {
//            if (pageIndex == PAGE_DEFAULT) {
//                hideTopBar(true);
//            } else {
//                hideTopBar(false);
//            }
//            pushFragment(pageIndex, currentFragmentIndex > pageIndex);
//            currentFragmentIndex = pageIndex;
//        }

    }

    private void pushFragment(Fragment fragment, boolean isRightToLeft) {
        pushFragment(fragment, isRightToLeft, false);
    }

    public void pushFragment(Fragment fragment, boolean isRightToLeft, boolean isAdd) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int childFragment = getSupportFragmentManager().getBackStackEntryCount();
        if (isRightToLeft) {
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(fragmentView.getId(), fragment);
        if (isAdd) {
            transaction.addToBackStack(fragment.getClass().getName());
        } else {
            for (int i = 0; i < childFragment; i++) {
                getSupportFragmentManager().popBackStack();
            }
        }
        transaction.commit();
    }

    private void startSignalRService() {
        if (!SystemUtils.isServiceRunning(InspireDiaryWebSocketService.class, context)) {
            Intent intent = new Intent(this, InspireDiaryWebSocketService.class);
            this.startService(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startSignalRService();
    }

}
