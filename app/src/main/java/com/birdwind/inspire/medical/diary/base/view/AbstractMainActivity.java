package com.birdwind.inspire.medical.diary.base.view;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.animation.SlideHeightAnimation;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils.FragNavTransactionOptions;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.leaf.library.StatusBarUtil;
import com.tbruyelle.rxpermissions3.Permission;

import java.util.List;

public abstract class AbstractMainActivity<P extends AbstractPresenter, VB extends ViewBinding>
    extends AbstractActivity<P, VB> implements AbstractActivity.PermissionRequestListener {

    protected final int PAGE_FRIEND = 0;

    protected final int PAGE_SCAN = 1;

    protected final int PAGE_QRCODE = 2;

    protected final int PAGE_REPORT = 3;

    protected final int PAGE_SETTING = 4;

    protected SlideHeightAnimation expandSlideMenuAnimation;

    protected SlideHeightAnimation shrinkSlideMenuAnimation;

    protected SlideHeightAnimation expandSlideTopBarAnimation;

    protected SlideHeightAnimation shrinkSlideTopBarAnimation;

    protected FragNavTransactionOptions popFragNavTransactionOptions;

    protected FragNavTransactionOptions tabToRightFragNavTransactionOptions;

    protected FragNavTransactionOptions tabToLeftFragNavTransactionOptions;

    protected List<Fragment> fragments;

    protected int currentFragmentIndex;

    protected View topBarView;

    protected View topMenuView;

    protected View fragmentView;

    protected abstract View setTopBar();

    protected abstract View setTopMenu();

    protected abstract View setFragmentView();

    protected abstract List<Fragment> initFragmentList();

    @Override
    public void initView() {
        topBarView.setBackgroundColor(App.userModel.getIdentityMainColor());
        StatusBarUtil.setColor(this, App.userModel.getIdentityMainColor());
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        topBarView = setTopBar();
        topMenuView = setTopMenu();
        fragmentView = setFragmentView();
        expandSlideMenuAnimation =
            new SlideHeightAnimation(topMenuView, Utils.dp2px(this, 91), Utils.dp2px(this, 126), 300);
        shrinkSlideMenuAnimation =
            new SlideHeightAnimation(topMenuView, Utils.dp2px(this, 126), Utils.dp2px(this, 91), 300);
        expandSlideMenuAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideMenuAnimation.setInterpolator(new AccelerateInterpolator());

        expandSlideTopBarAnimation =
            new SlideHeightAnimation(topBarView, Utils.dp2px(this, 0), Utils.dp2px(this, 44), 300);
        shrinkSlideTopBarAnimation =
            new SlideHeightAnimation(topBarView, Utils.dp2px(this, 44), Utils.dp2px(this, 0), 300);
        expandSlideTopBarAnimation.setInterpolator(new AccelerateInterpolator());
        shrinkSlideTopBarAnimation.setInterpolator(new AccelerateInterpolator());

        popFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();

        tabToRightFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left).build();

        tabToLeftFragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
            .customAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right).build();
        fragments = initFragmentList();
        currentFragmentIndex = -1;
    }

    @Override
    public void onBackPressed() {
        if (currentFragmentIndex != PAGE_FRIEND) {
            swipeFragment(PAGE_FRIEND);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void permissionRequest(Context context, Permission permission) {
        if (permission.granted) {
            if (permission.name.equals(Manifest.permission.CAMERA)) {
                hideTopBar(false);
                pushFragment(PAGE_SCAN, currentFragmentIndex > PAGE_SCAN);
                currentFragmentIndex = PAGE_SCAN;
            }
        }
    }

    public void hideTopBar(boolean isHide) {
        if (isHide) {
            slideHeightAnimation(topBarView, shrinkSlideTopBarAnimation);
            slideHeightAnimation(topMenuView, expandSlideMenuAnimation);
        } else {
            slideHeightAnimation(topBarView, expandSlideTopBarAnimation);
            slideHeightAnimation(topMenuView, shrinkSlideMenuAnimation);
        }
    }

    private void slideHeightAnimation(View view, Animation animation) {
        view.setAnimation(animation);
        view.startAnimation(animation);
    }

    public void swipeFragment(int pageIndex) {
        boolean isCanPush = true;
        if (currentFragmentIndex == pageIndex)
            return;

        if (pageIndex == PAGE_SCAN) {
            if (!hasPermission(Manifest.permission.CAMERA)) {
                isCanPush = false;
                getPermission(new String[] {Manifest.permission.CAMERA}, this);
            }
        }

        if (isCanPush) {
            if (pageIndex == PAGE_FRIEND) {
                hideTopBar(true);
            } else {
                hideTopBar(false);
            }
            pushFragment(pageIndex, currentFragmentIndex > pageIndex);
            currentFragmentIndex = pageIndex;
        }
    }

    private void pushFragment(int pageIndex, boolean isRightToLeft){
        pushFragment(fragments.get(pageIndex), isRightToLeft);
    }

    public void pushFragment(Fragment fragment, boolean isRightToLeft) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isRightToLeft) {
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        } else {
            transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(fragmentView.getId(), fragment);
        transaction.commit();
    }
}
