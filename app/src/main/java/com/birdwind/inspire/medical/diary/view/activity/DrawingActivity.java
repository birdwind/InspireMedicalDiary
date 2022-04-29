package com.birdwind.inspire.medical.diary.view.activity;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.DrawingActivityBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.leaf.library.StatusBarUtil;

import zhanglei.com.paintview.DrawTypeEnum;

public class DrawingActivity extends AbstractActivity<AbstractPresenter, DrawingActivityBinding> {

    private boolean isShowDrawingToolbar;
    private int currentColor;

    private ColorPickerDialog colorPickerDialog;

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public DrawingActivityBinding getViewBinding(LayoutInflater inflater, ViewGroup container,
            boolean attachToParent) {
        return DrawingActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.llToolbarUnderlineDrawingActivity.setOnClickListener(v -> {
            showDrawingToolbar(!isShowDrawingToolbar);
        });

        binding.ibtnPenDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.setDrawType(DrawTypeEnum.PEN);
            showDrawingToolbar(isShowDrawingToolbar);
        });
        binding.ibtnEraseDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.setDrawType(DrawTypeEnum.ERASER);
            showDrawingToolbar(isShowDrawingToolbar);
        });

        binding.ibtnColorpickerDrawingActivity.setOnClickListener(v -> {
            showColorPicker();
        });

        binding.ibtnNextDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.redo();
        });

        binding.ibtnPreDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.undo();
        });

        binding.ibtnClearDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.clear();
            showDrawingToolbar(isShowDrawingToolbar);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        isShowDrawingToolbar = true;
        currentColor = getResources().getColor(R.color.colorBlack_000000);
        colorPickerDialog = new ColorPickerDialog.Builder(this).setTitle("Pick Theme")
                .setColorShape(ColorShape.SQAURE).setDefaultColor(currentColor)
                .setColorListener((color, colorHex) -> {
                    currentColor = color;
                    binding.pvDrawingFragment.setPaintColor(currentColor);
                    showDrawingToolbar(false);
                }).build();
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this,
                getResources().getColor(App.userModel.getIdentityMainColorId()), 8);
        StatusBarUtil.setDarkMode(this);
        binding.compTopBarMainActivity.tvTitleTopBarComp.setText("");
        binding.compTopBarMainActivity.rlBackgroundTopBarComp.setBackgroundColor(
                getResources().getColor(App.userModel.getIdentityMainColorId()));
        binding.compTopBarMainActivity.llBackTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.rlBackgroundTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.GONE);
        binding.compTopBarMainActivity.ivRightButtonTopBarComp
                .setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save));
        binding.compTopBarMainActivity.ivRightButtonTopBarComp.setImageTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.colorWhite_FFFFFF)));

        binding.viewToolbarUnderlineDrawingActivity.setBackgroundTintList(ColorStateList
                .valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
        binding.llToolbarDrawingActivity.setBackgroundTintList(ColorStateList
                .valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
    }

    @Override
    public void doSomething() {
        showDrawingToolbar(false);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int newOrientation = newConfig.orientation;

        if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtils.d("橫向");
        } else {
            LogUtils.d("直向");
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        binding.llToolbarDrawingActivity.getGlobalVisibleRect(viewRect);
        if (isShowDrawingToolbar && !viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
            showDrawingToolbar(false);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        binding.pvDrawingFragment.destroy();
        super.onDestroy();
    }

    private void showDrawingToolbar(boolean show) {
        if (show != isShowDrawingToolbar) {
            View targetView = binding.llToolbarDrawingActivity;
            ViewGroup parent = binding.rlDrawingParentDrawingActivity;

            Transition transition = new Slide(Gravity.BOTTOM);
            transition.setDuration(100);
            transition.addTarget(binding.llToolbarDrawingActivity);

            TransitionManager.beginDelayedTransition(parent, transition);
            targetView.setVisibility(show ? View.VISIBLE : View.GONE);
            binding.llToolbarUnderlineDrawingActivity
                    .setVisibility(show ? View.GONE : View.VISIBLE);
            isShowDrawingToolbar = show;
        }
    }

    private void showColorPicker() {
        if (colorPickerDialog != null) {
            colorPickerDialog.show();
        }
    }
}
