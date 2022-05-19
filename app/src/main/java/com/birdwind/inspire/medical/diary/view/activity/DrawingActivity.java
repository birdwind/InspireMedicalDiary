package com.birdwind.inspire.medical.diary.view.activity;

import static com.birdwind.inspire.medical.diary.view.fragment.SurveyFragment.ACTIVITY_RESULT_DRAWING_OK;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.utils.FileUtils;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.databinding.DrawingActivityBinding;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.presenter.DrawingPresenter;
import com.birdwind.inspire.medical.diary.utils.ImageUtils;
import com.birdwind.inspire.medical.diary.view.viewCallback.DrawingView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.github.dhaval2404.colorpicker.ColorPickerDialog;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.leaf.library.StatusBarUtil;

import java.io.File;

import zhanglei.com.paintview.DrawTypeEnum;

public class DrawingActivity extends AbstractActivity<DrawingPresenter, DrawingActivityBinding>
    implements DrawingView, ProgressRequestBody.UploadCallbacks {

    private boolean isShowDrawingToolbar;

    private int currentColor;

    private ColorPickerDialog colorPickerDialog;

    private QuestionModel questionModel;

    @Override
    public DrawingPresenter createPresenter() {
        return new DrawingPresenter(this);
    }

    @Override
    public DrawingActivityBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return DrawingActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.compTopBarMainActivity.llBackTopBarComp.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.compTopBarMainActivity.ivRightButtonTopBarComp.setOnClickListener(v -> {
            Uri uri = ImageUtils.getImageUri(this, ImageUtils.getBitmapFromView(binding.rlAreaDrawingActivity));
            File file = null;
            if (uri != null) {
                String filePath = FileUtils.getFileFromContentUri(this, uri);
                if (filePath != null) {
                    file = new File(filePath);
                }
            }
            if (file == null) {
                showToast("繪圖轉換失敗，請稍後再試");
            } else {
                presenter.uploadRecord(file, this);
            }
        });

        binding.llToolbarUnderlineDrawingActivity.setOnClickListener(v -> {
            showDrawingToolbar(!isShowDrawingToolbar);
        });

        binding.ibtnPenDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.setDrawType(DrawTypeEnum.PEN);
            // showDrawingToolbar(!isShowDrawingToolbar);
        });
        binding.ibtnEraseDrawingActivity.setOnClickListener(v -> {
            binding.pvDrawingFragment.setDrawType(DrawTypeEnum.ERASER);
            showDrawingToolbar(!isShowDrawingToolbar);
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
            showDrawingToolbar(!isShowDrawingToolbar);
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            questionModel = (QuestionModel) bundle.getSerializable("question");
        }
        isShowDrawingToolbar = true;
        currentColor = getResources().getColor(R.color.colorBlack_000000);
        colorPickerDialog = new ColorPickerDialog.Builder(this).setTitle("Pick Theme").setColorShape(ColorShape.SQAURE)
            .setDefaultColor(currentColor).setColorListener((color, colorHex) -> {
                currentColor = color;
                binding.pvDrawingFragment.setPaintColor(currentColor);
                showDrawingToolbar(false);
            }).build();
    }

    @Override
    public void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(App.userModel.getIdentityMainColorId()), 8);
        StatusBarUtil.setDarkMode(this);
        binding.compTopBarMainActivity.tvTitleTopBarComp.setText("");
        binding.compTopBarMainActivity.rlBackgroundTopBarComp
            .setBackgroundColor(getResources().getColor(App.userModel.getIdentityMainColorId()));
        binding.compTopBarMainActivity.llBackTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.rlBackgroundTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.llRightButtonTopBarComp.setVisibility(View.VISIBLE);
        binding.compTopBarMainActivity.btRightButtonTopBarComp.setVisibility(View.GONE);
        binding.compTopBarMainActivity.ivRightButtonTopBarComp
            .setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_save));
        binding.compTopBarMainActivity.ivRightButtonTopBarComp
            .setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite_FFFFFF)));

        binding.viewToolbarUnderlineDrawingActivity.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
        binding.llToolbarDrawingActivity.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));

        binding.tvTitleDrawingActivity.setText(questionModel.getQuestionText());

        if (questionModel != null && !TextUtils.isEmpty(questionModel.getMediaLink())) {
            binding.ivImageDrawingActivity.setVisibility(View.VISIBLE);
            binding.viewImageUnderlineDrawingActivity.setVisibility(View.VISIBLE);
            CustomPicasso.getImageLoader(this).load(questionModel.getMediaLink()).into(binding.ivImageDrawingActivity);
        } else {
            binding.ivImageDrawingActivity.setVisibility(View.GONE);
            binding.viewImageUnderlineDrawingActivity.setVisibility(View.GONE);
        }

        if (questionModel.isBackground()) {
            binding.ivImageDrawingActivity.setVisibility(View.GONE);
            binding.viewImageUnderlineDrawingActivity.setVisibility(View.GONE);
            Glide.with(this).load(questionModel.getMediaLink()).into(binding.ivBackgroundDrawingActivity);
        }
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
            binding.llToolbarUnderlineDrawingActivity.setVisibility(show ? View.GONE : View.VISIBLE);
            isShowDrawingToolbar = show;
        }
    }

    private void showColorPicker() {
        if (colorPickerDialog != null) {
            colorPickerDialog.show();
        }
    }

    @Override
    public void onUpload(boolean isSuccess, String url) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("url", url);
        returnIntent.putExtra("questionId", questionModel.getQuestionID());
        setResult(ACTIVITY_RESULT_DRAWING_OK, returnIntent);
        finish();
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("上傳", String.valueOf(percentage));
    }

    @Override
    public void onError() {
        LogUtils.d("上傳", "失敗");
        hideLoading();
    }

    @Override
    public void onFinish() {
        LogUtils.d("上傳", "完成");
        hideLoading();
    }
}
