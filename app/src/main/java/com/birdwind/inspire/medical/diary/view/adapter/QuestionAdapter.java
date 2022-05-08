package com.birdwind.inspire.medical.diary.view.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.MediaUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractActivity;
import com.birdwind.inspire.medical.diary.enums.AnswerTypeEnum;
import com.birdwind.inspire.medical.diary.model.MultipleChooseModel;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.model.request.SurveyAnswerRequest;
import com.birdwind.inspire.medical.diary.view.dialog.RecordVoiceDialog;
import com.birdwind.inspire.medical.diary.view.dialog.callback.RecordVoiceDialogListener;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;
import java.util.Objects;

import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

public class QuestionAdapter extends BaseMultiItemQuickAdapter<QuestionModel, BaseViewHolder>
    implements RecordVoiceDialogListener, MediaPlayer.OnCompletionListener, OnProgressListener {

    private SurveyAnswerRequest surveyAnswerRequest;

    private final AnswerCompleteListener answerCompleteListener;

    private final SpecialAnswerListener specialAnswerListener;

    private RecordVoiceDialog recordVoiceDialog;

    private MediaUtils mediaUtils;

    private Handler handler;

    private final Animation animation;

    private AudioWaveView audioWaveView;

    private int currentPosition;

    public QuestionAdapter(AnswerCompleteListener answerCompleteListener, SpecialAnswerListener specialAnswerListener) {
        addItemType(AnswerTypeEnum.BOOLEAN, R.layout.item_question_boolean);
        addItemType(AnswerTypeEnum.MULTIPLE, R.layout.item_question_multiple);
        addItemType(AnswerTypeEnum.NUMBER, R.layout.item_question_number);
        addItemType(AnswerTypeEnum.TEXT, R.layout.item_question_multiple);
        addItemType(AnswerTypeEnum.IMAGE, R.layout.item_question_image);
        addItemType(AnswerTypeEnum.AUDIO, R.layout.item_question_audio);
        addItemType(AnswerTypeEnum.VIDEO, R.layout.item_question_video);

        addChildClickViewIds(R.id.rb_true_question_boolean_item, R.id.rb_false_question_boolean_item);
        addChildClickViewIds(R.id.ibtn_record_question_item, R.id.ibtn_play_question_item,
            R.id.awv_record_question_item);
        addChildClickViewIds(R.id.ibtn_drawing_question_item);
        addChildClickViewIds(R.id.ibtn_picture_question_item);
        setOnItemChildClickListener((adapter, view, position) -> {
        });
        this.answerCompleteListener = answerCompleteListener;
        this.specialAnswerListener = specialAnswerListener;
        handler = new Handler();

        animation = new AlphaAnimation(1, 0);
        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);
        mediaUtils = new MediaUtils();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, QuestionModel questionModel) {
        RelativeLayout rlMain = baseViewHolder.getView(R.id.rl_main_question_item);
        ImageView ivPicture = baseViewHolder.getView(R.id.iv_picture_question_item);
        rlMain.setTag(questionModel.getQuestionID());
        if (recordVoiceDialog == null) {
            recordVoiceDialog = new RecordVoiceDialog(getContext(), this);
        }

        Glide.with(getContext()).load(questionModel.getMediaLink()).into(ivPicture);

        baseViewHolder.setText(R.id.tv_title_question_item, questionModel.getQuestionText());
        switch (baseViewHolder.getItemViewType()) {
            case AnswerTypeEnum.BOOLEAN:
                break;
            case AnswerTypeEnum.NUMBER:
                EditText editText = baseViewHolder.getView(R.id.et_value_question_number_item);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        try {
                            updateAnswer(questionModel.getQuestionID(), charSequence.toString());
                        } catch (Exception e) {
                            updateAnswer(questionModel.getQuestionID(), null);
                            LogUtils.exception(e);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {}
                });
                break;
            case AnswerTypeEnum.MULTIPLE:
                initMultipleAnswer(baseViewHolder.getView(R.id.rv_answer_question_item), questionModel);
                break;
            case AnswerTypeEnum.IMAGE:
                break;
            case AnswerTypeEnum.AUDIO:
                AudioWaveView audioWaveView = baseViewHolder.getView(R.id.awv_record_question_item);
                audioWaveView.setOnProgressListener(this);
                baseViewHolder.getView(R.id.ibtn_record_question_item).setVisibility(View.VISIBLE);
                baseViewHolder.getView(R.id.ibtn_play_question_item).setVisibility(View.GONE);
                break;
            case AnswerTypeEnum.VIDEO:
                break;
        }
    }

    @Override
    protected void setOnItemChildClick(@NonNull View v, int position) {
        super.setOnItemChildClick(v, position);
        QuestionModel questionModel = getData().get(position);
        switch (questionModel.getAnswerType()) {
            case AnswerTypeEnum.BOOLEAN:
                if (v.getId() == R.id.rb_true_question_boolean_item) {
                    ((RadioButton) Objects
                        .requireNonNull(getViewByPosition(position, R.id.rb_false_question_boolean_item)))
                            .setChecked(false);
                    updateAnswer(questionModel.getQuestionID(), "1");
                } else if (v.getId() == R.id.rb_false_question_boolean_item) {
                    ((RadioButton) Objects
                        .requireNonNull(getViewByPosition(position, R.id.rb_true_question_boolean_item)))
                            .setChecked(false);
                    updateAnswer(questionModel.getQuestionID(), "0");
                }
                break;
            case AnswerTypeEnum.AUDIO:
                if (v.getId() == R.id.ibtn_record_question_item) {
                    if (((AbstractActivity) getContext()).hasPermission(Manifest.permission.RECORD_AUDIO)) {
                        recordVoiceDialog.show();
                        recordVoiceDialog.initRecord(getData().get(position).getQuestionID());
                    }
                } else if (v.getId() == R.id.ibtn_play_question_item || v.getId() == R.id.awv_record_question_item) {
                    playAudio(position);
                }
                break;
            case AnswerTypeEnum.IMAGE:
                if (v.getId() == R.id.ibtn_drawing_question_item) {
                    specialAnswerListener.drawing(getItem(position));
                }
                break;
            case AnswerTypeEnum.VIDEO:
                if (v.getId() == R.id.ibtn_picture_question_item) {
                    specialAnswerListener.recordVideo(getItem(position));
                }
        }
    }

    private void initMultipleAnswer(RecyclerView rvAnswer, QuestionModel questionModel) {
        AnswerAdapter answerAdapter = new AnswerAdapter(R.layout.item_answer, questionModel.getMultipleChoose());
        answerAdapter.setAnimationEnable(true);

        answerAdapter.addChildClickViewIds(R.id.rb_answer_item);
        answerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (i != position) {
                    RadioButton radioButton = (RadioButton) adapter.getViewByPosition(i, R.id.rb_answer_item);
                    Objects.requireNonNull(radioButton).setChecked(false);
                } else {
                    updateAnswer(questionModel.getQuestionID(),
                        String.valueOf(((MultipleChooseModel) adapter.getItem(position)).getChoiceID()));
                }
            }
        });

        answerAdapter.setRecyclerView(rvAnswer);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        rvAnswer.setLayoutManager(flexboxLayoutManager);
        rvAnswer.setHasFixedSize(true);
        rvAnswer.setAdapter(answerAdapter);
    }

    public void initSurveyAnswerRequest(int surveyId) {
        surveyAnswerRequest = new SurveyAnswerRequest(surveyId);
    }

    public SurveyAnswerRequest getSurveyAnswerRequest() {
        return surveyAnswerRequest;
    }

    /**
     * 檢查答案是否選填完
     */
    private void checkAnswerComplete() {
        if (surveyAnswerRequest != null && surveyAnswerRequest.getQuestions().size() == getData().size()) {
            answerCompleteListener.complete();
        } else {
            answerCompleteListener.unComplete();
        }
    }

    /**
     * 更新問題答案
     */
    public void updateAnswer(int questionId, String answer) {
        if (surveyAnswerRequest != null) {
            boolean isExist = false;
            for (SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest : surveyAnswerRequest.getQuestions()) {
                if (questionAnswerRequest.getQuestionID() == questionId) {
                    if (!TextUtils.isEmpty(answer)) {
                        questionAnswerRequest.setValue(answer);
                    } else {
                        surveyAnswerRequest.getQuestions().remove(questionAnswerRequest);
                    }
                    isExist = true;
                }
            }

            if (!isExist && answer != null) {
                surveyAnswerRequest.getQuestions()
                    .add(new SurveyAnswerRequest.QuestionAnswerRequest(questionId, answer));
            }
        }
        checkAnswerComplete();
    }

    /**
     * 更新問卷所有答案，並設為不可改
     */
    public void updateAnswer(SurveyAnswerRequest surveyAnswerRequest) {
        this.surveyAnswerRequest = surveyAnswerRequest;
        List<QuestionModel> questionModels = getData();
        for (SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest : surveyAnswerRequest.getQuestions()) {
            for (int i = 0; i < questionModels.size(); i++) {
                QuestionModel questionModel = questionModels.get(i);
                if (questionAnswerRequest.getQuestionID() == questionModel.getQuestionID()) {
                    try {
                        switch (questionModel.getAnswerType()) {
                            case AnswerTypeEnum.BOOLEAN:
                                RadioButton rbBooleanTrue =
                                    (RadioButton) getViewByPosition(i, R.id.rb_true_question_boolean_item);
                                RadioButton rbBooleanFalse =
                                    (RadioButton) getViewByPosition(i, R.id.rb_false_question_boolean_item);
                                rbBooleanTrue.setEnabled(false);
                                rbBooleanFalse.setEnabled(false);
                                if (questionAnswerRequest.getValue().equals("1")) {
                                    rbBooleanTrue.setChecked(true);
                                } else {
                                    rbBooleanFalse.setChecked(false);
                                }
                                break;
                            case AnswerTypeEnum.NUMBER:
                                EditText editText = (EditText) getViewByPosition(i, R.id.et_value_question_number_item);
                                editText.setText(questionAnswerRequest.getValue());
                                editText.setEnabled(false);
                                break;
                            case AnswerTypeEnum.MULTIPLE:
                                RecyclerView recyclerView =
                                    (RecyclerView) getViewByPosition(i, R.id.rv_answer_question_item);
                                AnswerAdapter answerAdapter = (AnswerAdapter) recyclerView.getAdapter();
                                answerAdapter.updateData(Integer.parseInt(questionAnswerRequest.getValue()));
                                break;
                            case AnswerTypeEnum.IMAGE:
                                // getViewByPosition(i, R.id.iv_picture_question_item).setVisibility(View.GONE);
                                getViewByPosition(i, R.id.ibtn_drawing_question_item).setVisibility(View.GONE);
                                getViewByPosition(i, R.id.view_image_underline_drawing_question_item)
                                    .setVisibility(View.VISIBLE);
                                getViewByPosition(i, R.id.iv_answer_picture_question_item).setVisibility(View.VISIBLE);
                                CustomPicasso.getImageLoader(getContext()).load(questionAnswerRequest.getValue())
                                    .into((ImageView) getViewByPosition(i, R.id.iv_answer_picture_question_item));
                                break;
                            case AnswerTypeEnum.AUDIO:
                                View view = getViewByPosition(i, R.id.ibtn_record_question_item);
                                if (view != null) {
                                    view.setVisibility(View.GONE);
                                }
                                view = getViewByPosition(i, R.id.ibtn_play_question_item);
                                if (view != null) {
                                    view.setVisibility(View.VISIBLE);
                                }
                                break;
                            case AnswerTypeEnum.VIDEO:
                                break;
                        }
                    } catch (Exception e) {
                        LogUtils.exception(e);
                        ToastUtils.show(getContext(), "回答格式與問題格式不同，請聯絡管理人員");
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void recordVoiceDone(int questionId, String recordUrl) {
        updateAnswer(questionId, recordUrl);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopAudio();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mediaUtils != null) {
            stopAudio();
            mediaUtils.release();
        }
    }

    @Override
    public void onProgressChanged(float percent, boolean fromUser) {
        if (fromUser) {
            mediaUtils.seekTo((int) percent);
        }
    }

    @Override
    public void onStartTracking(float v) {

    }

    @Override
    public void onStopTracking(float v) {}

    private void playAudio(int position) {
        if (mediaUtils != null && !mediaUtils.isPlaying()) {
            currentPosition = position;
            ImageButton ibtnPlay = (ImageButton) getViewByPosition(position, R.id.ibtn_play_question_item);
            audioWaveView = (AudioWaveView) getViewByPosition(position, R.id.awv_record_question_item);
            audioWaveView.setWaveColor(App.userModel.getIdentityMainColor());
            ibtnPlay.setImageResource(R.drawable.lb_ic_pause);
            ibtnPlay.setImageTintList(ColorStateList.valueOf(App.userModel.getIdentityMainColor()));
            mediaUtils.playAudioByUrl(surveyAnswerRequest.getQuestions().get(position).getValue(), this);
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaUtils != null && mediaUtils.isPlaying()) {

                        audioWaveView.setProgress(mediaUtils.getCurrentPercent());
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        } else {
            if (position != currentPosition) {
                ToastUtils.show(getContext(), "請先停止播放上一個錄音檔");
            } else {
                stopAudio();
            }
        }
    }

    private void stopAudio() {
        if (mediaUtils.isPlaying()) {
            mediaUtils.stop();
            ImageButton ibtnPlay = (ImageButton) getViewByPosition(currentPosition, R.id.ibtn_play_question_item);
            ibtnPlay.setImageResource(R.drawable.ic_play);
            ibtnPlay.setImageTintList(
                ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
            audioWaveView.setWaveColor(R.color.colorBlack_000000);
            audioWaveView.setProgress(0);
            handler = new Handler();
        }
    }

    public interface AnswerCompleteListener {

        void complete();

        void unComplete();
    }

    public interface SpecialAnswerListener {
        void drawing(QuestionModel questionModel);

        void recordVideo(QuestionModel questionModel);
    }
}
