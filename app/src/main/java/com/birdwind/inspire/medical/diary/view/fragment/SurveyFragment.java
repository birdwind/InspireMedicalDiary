package com.birdwind.inspire.medical.diary.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.network.request.ProgressRequestBody;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.base.view.AbstractFragment;
import com.birdwind.inspire.medical.diary.databinding.FragmentSurveyBinding;
import com.birdwind.inspire.medical.diary.enums.AnswerTypeEnum;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.model.response.QuestionnaireResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyResponse;
import com.birdwind.inspire.medical.diary.presenter.SurveyPresenter;
import com.birdwind.inspire.medical.diary.view.activity.CameraActivity;
import com.birdwind.inspire.medical.diary.view.activity.DrawingActivity;
import com.birdwind.inspire.medical.diary.view.adapter.QuestionAdapter;
import com.birdwind.inspire.medical.diary.view.viewCallback.SurveyView;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

public class SurveyFragment extends AbstractFragment<SurveyPresenter, FragmentSurveyBinding>
    implements SurveyView, ToolbarCallback, QuestionAdapter.AnswerCompleteListener,
    QuestionAdapter.SpecialAnswerListener, ProgressRequestBody.UploadCallbacks {

    public static final int ACTIVITY_RESULT_DRAWING_OK = 0x101;

    public static final int ACTIVITY_RESULT_VIDEO_OK = 0x201;

    private QuestionAdapter questionAdapter;

    private int questionnaireID;

    private IdentityEnums identityEnums;

    private QuestionnaireResponse.Response questionnaireResponse;

    @Override
    public SurveyPresenter createPresenter() {
        return new SurveyPresenter(this);
    }

    @Override
    public FragmentSurveyBinding getViewBinding(LayoutInflater inflater, ViewGroup container, boolean attachToParent) {
        return FragmentSurveyBinding.inflate(inflater);
    }

    @Override
    public void addListener() {
        binding.btSubmitSurveyFragment.setOnClickListener(v -> {
            presenter.submit(questionAdapter.getSurveyAnswerRequest());
        });

        binding.rvQuizFragment.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (questionnaireResponse != null) {
                questionAdapter.updateAnswer(questionnaireResponse.getSurveyRequest());
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            questionnaireID = bundle.getInt("questionnaireID", 0);
            identityEnums =
                IdentityEnums.parseEnumsByType(bundle.getInt("identity", App.userModel.getIdentityEnums().getType()));
        } else {
            questionnaireID = 0;
            identityEnums = App.userModel.getIdentityEnums();
        }

        questionAdapter = new QuestionAdapter(this, this);
        questionAdapter.setAnimationEnable(true);
        questionAdapter.setRecyclerView(binding.rvQuizFragment);
    }

    @Override
    public void initView() {
        binding.rvQuizFragment.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvQuizFragment.setHasFixedSize(true);
        binding.rvQuizFragment.setAdapter(questionAdapter);
        if (questionnaireID != 0) {
            binding.btSubmitSurveyFragment.setVisibility(View.GONE);
        } else {
            binding.btSubmitSurveyFragment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void doSomething() {
        if (hasPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO)) {
            init();
        } else {
            ((AbstractActivity) context).getCurrentAppPermission(this::init, this::onBackPressedByActivity,
                this::onBackPressedByActivity);
        }

    }

    private void init() {
        if (questionnaireID == 0) {
            presenter.getSurvey(identityEnums);
        } else {
            presenter.getQuestionnaire(questionnaireID);
        }
    }

    @Override
    public void onGetSurvey(boolean isSuccess, SurveyResponse.Response response) {
        if (isSuccess) {
            questionAdapter.setList(response.getQuestions());
            questionAdapter.initSurveyAnswerRequest(response.getSurveyID());
            fragmentNavigationListener.updateTitle(response.getSurveyName());
            binding.rvQuizFragment.setVisibility(View.VISIBLE);
            binding.tvEmptyQuizFragment.setVisibility(View.GONE);
        } else {
            binding.rvQuizFragment.setVisibility(View.GONE);
            binding.tvEmptyQuizFragment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void submitSuccess(boolean isSuccess) {
        onBackPressedByActivity();
    }

    @Override
    public void onGetQuestionnaire(QuestionnaireResponse.Response questionnaireResponse) {
        binding.rvQuizFragment.setVisibility(View.VISIBLE);
        binding.tvEmptyQuizFragment.setVisibility(View.GONE);
        this.questionnaireResponse = questionnaireResponse;
        questionAdapter.setList(questionnaireResponse.getSurveyResponse().getQuestions());
        fragmentNavigationListener.updateTitle(questionnaireResponse.getSurveyResponse().getSurveyName());
    }

    @Override
    public void onUploadRecord(boolean isSuccess, String url) {}

    @Override
    public void complete() {
        binding.btSubmitSurveyFragment.setEnabled(true);
        binding.btSubmitSurveyFragment.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(App.userModel.getIdentityMainColorId())));
    }

    @Override
    public void unComplete() {
        binding.btSubmitSurveyFragment.setEnabled(false);
        binding.btSubmitSurveyFragment
            .setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGray_A6A6A6)));
    }

    @Override
    public void onProgressUpdate(int percentage) {
        LogUtils.d("上傳", String.valueOf(percentage));
        showLoading();
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

    @Override
    public void drawing(QuestionModel questionModel) {
        Intent intent = new Intent(context, DrawingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", questionModel);
        intent.putExtras(bundle);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void recordVideo(QuestionModel questionModel) {
        Intent intent = new Intent(context, CameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", questionModel);
        intent.putExtras(bundle);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void previewVideo(String videoUrl) {
        Intent playVideo = new Intent(Intent.ACTION_VIEW);
        playVideo.setDataAndType(Uri.parse(videoUrl), "video/mp4");
        startActivity(playVideo);
    }

    @Override
    public void previewImage(String imageUrl) {
        Intent playVideo = new Intent(Intent.ACTION_VIEW);
        playVideo.setDataAndType(Uri.parse(imageUrl), "image/jpg");
        startActivity(playVideo);
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        Intent intent = result.getData();
        if (intent != null) {
            String url = intent.getStringExtra("url");
            int questionId = intent.getIntExtra("questionId", -1);
            if (!TextUtils.isEmpty(url) && questionId != -1) {
                if (result.getResultCode() == ACTIVITY_RESULT_DRAWING_OK) {
                    questionAdapter.updateAnswer(questionId, url, AnswerTypeEnum.IMAGE);
                } else if (result.getResultCode() == ACTIVITY_RESULT_VIDEO_OK) {
                    questionAdapter.updateAnswer(questionId, url, AnswerTypeEnum.VIDEO);
                }
            }
        }
    }
}
