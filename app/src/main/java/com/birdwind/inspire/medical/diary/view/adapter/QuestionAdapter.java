package com.birdwind.inspire.medical.diary.view.adapter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.CustomPicasso;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.MediaUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.enums.AnswerTypeEnum;
import com.birdwind.inspire.medical.diary.model.MultipleChooseModel;
import com.birdwind.inspire.medical.diary.model.QuestionModel;
import com.birdwind.inspire.medical.diary.model.request.SurveyAnswerRequest;
import com.birdwind.inspire.medical.diary.utils.question.AudioQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.BooleanQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.ImageQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.MultipleQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.NumberQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.QuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.SpecialAnswerListener;
import com.birdwind.inspire.medical.diary.utils.question.VideoQuestionBuilder;
import com.birdwind.inspire.medical.diary.utils.question.WeightedQuestionBuilder;
import com.birdwind.inspire.medical.diary.view.customer.indicatorSeekBar.CustomIndicatorSeekBar;
import com.birdwind.inspire.medical.diary.view.dialog.callback.RecordVoiceDialogListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

public class QuestionAdapter extends BaseMultiItemQuickAdapter<QuestionModel, BaseViewHolder>
    implements RecordVoiceDialogListener, MediaPlayer.OnCompletionListener, OnProgressListener {

    private SurveyAnswerRequest surveyAnswerRequest;

    private final AnswerCompleteListener answerCompleteListener;

    private final Map<String, QuestionBuilder> questionBuilderMap;

    private final AnswerListener answerListener;

    private final SpecialAnswerListener specialAnswerListener;

    private boolean isResult;

    // Audio Start
    private MediaPlayer.OnCompletionListener onCompletionListener;

    private MediaUtils mediaUtils;

    private AudioWaveView audioWaveView;

    private int audioPlayPosition;

    private Handler handler;
    // Audio End

    // Weighted Start
    private Map<String, Boolean> isWeightedTotalCorrect;
    // Weighted End

    public QuestionAdapter(SpecialAnswerListener specialAnswerListener, AnswerCompleteListener answerCompleteListener) {
        addItemType(AnswerTypeEnum.BOOLEAN, R.layout.item_question_boolean);
        addItemType(AnswerTypeEnum.MULTIPLE, R.layout.item_question_multiple);
        addItemType(AnswerTypeEnum.NUMBER, R.layout.item_question_number);
        addItemType(AnswerTypeEnum.TEXT, R.layout.item_question_multiple);
        addItemType(AnswerTypeEnum.IMAGE, R.layout.item_question_image);
        addItemType(AnswerTypeEnum.AUDIO, R.layout.item_question_audio);
        addItemType(AnswerTypeEnum.VIDEO, R.layout.item_question_video);
        addItemType(AnswerTypeEnum.WEIGHTED, R.layout.item_question_weight);
        addChildClickView();

        this.questionBuilderMap = new ArrayMap<>();
        this.specialAnswerListener = specialAnswerListener;
        this.answerCompleteListener = answerCompleteListener;
        this.mediaUtils = new MediaUtils();
        this.audioPlayPosition = -1;
        this.handler = new Handler();
        this.onCompletionListener = this;
        this.isWeightedTotalCorrect = new HashMap<>();
        this.isResult = false;

        this.answerListener = new AnswerListener() {
            @Override
            public void updateAnswer(int questionId, String value) {
                surveyAnswerRequest.updateAnswer(questionId, value);
                if (surveyAnswerRequest.isComplete()) {
                    for (String idAndPosition : isWeightedTotalCorrect.keySet()) {
                        if (Boolean.FALSE.equals(isWeightedTotalCorrect.get(idAndPosition))) {
                            answerCompleteListener.unComplete();
                            return;
                        }
                    }
                    answerCompleteListener.complete();
                }
            }

            @Override
            public void playAudio(int position, String audioUrl) {
                if (mediaUtils != null && !mediaUtils.isPlaying()) {
                    audioPlayPosition = position;
                    ImageButton ibtnPlay =
                        (ImageButton) getViewByPosition(position, R.id.ibtn_play_audio_question_item);
                    audioWaveView = (AudioWaveView) getViewByPosition(position, R.id.awv_record_question_item);
                    if (audioWaveView != null && ibtnPlay != null) {
                        audioWaveView.setWaveColor(App.userModel.getIdentityMainColor());
                        ibtnPlay.setImageResource(R.drawable.lb_ic_pause);
                        ibtnPlay.setImageTintList(ColorStateList.valueOf(App.userModel.getIdentityMainColor()));
                    }
                    mediaUtils.playAudioByUrl(audioUrl, onCompletionListener);
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
                    if (position != audioPlayPosition) {
                        ToastUtils.show(getContext(), "請先停止播放上一個錄音檔");
                    } else {
                        stopAudio();
                    }
                }
            }

            @Override
            public void stopAudio() {
                if (mediaUtils.isPlaying()) {
                    mediaUtils.stop();
                    resetAudioPlayView();
                    handler = new Handler();
                }
            }

            @Override
            public void weightedCorrect(String idAndPosition, boolean isCorrect) {
                isWeightedTotalCorrect.put(idAndPosition, isCorrect);
            }
        };
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, QuestionModel questionModel) {
        RelativeLayout rlMain = baseViewHolder.getView(R.id.rl_main_question_item);
        ImageView ivPicture = baseViewHolder.getView(R.id.iv_picture_question_item);

        rlMain.setTag(questionModel.getQuestionID());

        Glide.with(getContext()).load(questionModel.getMediaLink()).into(ivPicture);

        baseViewHolder.setText(R.id.tv_title_question_item, questionModel.getQuestionText());

        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest = null;

        for (SurveyAnswerRequest.QuestionAnswerRequest questionAnswer : surveyAnswerRequest.getQuestions()) {
            if (questionAnswer.getQuestionID() == questionModel.getQuestionID()) {
                questionAnswerRequest = questionAnswer;
                break;
            }
        }
        if (questionAnswerRequest == null) {
            questionAnswerRequest = new SurveyAnswerRequest.QuestionAnswerRequest(questionModel.getQuestionID(), null);
        }

        QuestionBuilder questionBuilder =
            questionBuilderMap.get(String.valueOf(questionModel.getQuestionID()) + getItemPosition(questionModel));
        switch (questionModel.getItemType()) {
            case AnswerTypeEnum.BOOLEAN:
                if (questionBuilder == null) {
                    questionBuilder =
                        new BooleanQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }

                initBoolean(baseViewHolder, questionBuilder, questionAnswerRequest);
                break;
            case AnswerTypeEnum.NUMBER:
                if (questionBuilder == null) {
                    questionBuilder =
                        new NumberQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }

                initNumber(baseViewHolder, questionBuilder, questionAnswerRequest);
                break;
            case AnswerTypeEnum.MULTIPLE:
                if (questionBuilder == null) {
                    questionBuilder =
                        new MultipleQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }
                MultipleQuestionBuilder multipleQuestionBuilder = (MultipleQuestionBuilder) questionBuilder;

                initMultiple(baseViewHolder.getView(R.id.rv_answer_question_item), questionModel,
                    multipleQuestionBuilder);
                break;
            case AnswerTypeEnum.IMAGE:
                if (questionBuilder == null) {
                    questionBuilder =
                        new ImageQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }
                initImage(baseViewHolder, questionBuilder, questionAnswerRequest);
                break;
            case AnswerTypeEnum.AUDIO:
                if (questionBuilder == null) {
                    questionBuilder =
                        new AudioQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }
                AudioWaveView audioWaveView = baseViewHolder.getView(R.id.awv_record_question_item);
                audioWaveView.setOnProgressListener(this);

                initAudio(baseViewHolder, questionBuilder, questionAnswerRequest);
                break;
            case AnswerTypeEnum.VIDEO:
                if (questionBuilder == null) {
                    questionBuilder =
                        new VideoQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }
                initVideo(baseViewHolder, questionBuilder, questionAnswerRequest);
                break;
            case AnswerTypeEnum.WEIGHTED:
                if (questionBuilder == null) {
                    questionBuilder =
                        new WeightedQuestionBuilder(getContext(), getItemPosition(questionModel), answerListener, this);
                }
                WeightedQuestionBuilder weightedQuestionBuilder = (WeightedQuestionBuilder) questionBuilder;

                initWeight(baseViewHolder, questionModel, weightedQuestionBuilder, questionAnswerRequest);
                break;
        }
        if (questionBuilderMap
            .get(String.valueOf(questionModel.getQuestionID()) + getItemPosition(questionModel)) == null) {
            questionBuilderMap.put(String.valueOf(questionModel.getQuestionID()) + getItemPosition(questionModel),
                questionBuilder);
        }

        // if (questionBuilder != null) {
        // questionBuilder
        // .convertDataToView(surveyAnswerRequest.getQuestions().get(getItemPosition(questionModel)).getValue());
        // }
    }

    @Override
    protected void setOnItemChildClick(@NonNull View v, int position) {
        super.setOnItemChildClick(v, position);
        QuestionModel questionModel = getData().get(position);
        QuestionBuilder questionBuilder =
            questionBuilderMap.get(String.valueOf(questionModel.getQuestionID()) + position);
        if (questionBuilder != null) {
            questionBuilder.onClick(v);
        } else {
            LogUtils.d("尚未實作QuestionBuilder");
        }
    }

    private void addChildClickView() {
        // boolean
        addChildClickViewIds(R.id.rb_true_question_boolean_item, R.id.rb_false_question_boolean_item);
        // audio
        addChildClickViewIds(R.id.ibtn_record_audio_question_item, R.id.ibtn_play_audio_question_item,
            R.id.awv_record_question_item);
        // drawing
        addChildClickViewIds(R.id.ibtn_drawing_question_item, R.id.ibtn_play_image_question_item,
            R.id.iv_answer_picture_question_item);
        // video
        addChildClickViewIds(R.id.ibtn_record_video_question_item, R.id.iv_preview_question_item,
            R.id.ibtn_play_video_question_item);

        setOnItemChildClickListener((adapter, view, position) -> {
        });
    }

    public void initSurvey(int surveyId, List<QuestionModel> questionModels, SurveyAnswerRequest surveyAnswerRequest) {
        this.surveyAnswerRequest =
            surveyAnswerRequest == null ? new SurveyAnswerRequest(surveyId, questionModels) : surveyAnswerRequest;
        setList(questionModels);
    }

    private void initBoolean(BaseViewHolder baseViewHolder, QuestionBuilder questionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        questionBuilder.setValue(questionAnswerRequest.getValue());
        baseViewHolder.setEnabled(R.id.rb_true_question_boolean_item, !isResult);
        baseViewHolder.setEnabled(R.id.rb_false_question_boolean_item, !isResult);

        if (questionAnswerRequest.getValue() != null) {
            RadioButton rbBooleanTrue = baseViewHolder.getView(R.id.rb_true_question_boolean_item);
            RadioButton rbBooleanFalse = baseViewHolder.getView(R.id.rb_false_question_boolean_item);
            rbBooleanTrue.setChecked(questionAnswerRequest.getValue().toLowerCase(Locale.ROOT).equals("true")
                || questionAnswerRequest.getValue().toLowerCase(Locale.ROOT).equals("1"));
            rbBooleanFalse.setChecked(questionAnswerRequest.getValue().toLowerCase(Locale.ROOT).equals("false")
                || questionAnswerRequest.getValue().toLowerCase(Locale.ROOT).equals("0"));
        }
    }

    private void initNumber(BaseViewHolder baseViewHolder, QuestionBuilder questionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        questionBuilder.setValue(questionAnswerRequest.getValue());

        EditText editText = baseViewHolder.getView(R.id.et_value_question_number_item);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                questionBuilder.updateAnswer(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        editText.setEnabled(!isResult);

        if (questionAnswerRequest.getValue() != null) {
            editText.setText(questionAnswerRequest.getValue());
        }
    }

    private void initMultiple(RecyclerView rvAnswer, QuestionModel questionModel,
        MultipleQuestionBuilder multipleQuestionBuilder) {
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswer = null;
        if (surveyAnswerRequest != null) {
            for (SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest : surveyAnswerRequest.getQuestions()) {
                if (questionAnswerRequest.getQuestionID() == questionModel.getQuestionID()) {
                    questionAnswer = questionAnswerRequest;
                    break;
                }
            }
        }

        for (int i = 0; i < questionModel.getMultipleChoose().size(); i++) {
            MultipleChooseModel multipleChooseModel = questionModel.getMultipleChoose().get(i);
            multipleChooseModel.setAnswerType(questionModel.getAnswerType());
            if (questionAnswer != null && questionAnswer.getValue() != null
                && multipleChooseModel.getChoiceID() == Integer.parseInt(questionAnswer.getValue())) {
                multipleChooseModel.setChoose(true);
            }
        }
        AnswerAdapter answerAdapter = new AnswerAdapter(questionModel.getMultipleChoose());
        answerAdapter.setAnimationEnable(true);
        answerAdapter.setResult(isResult);

        answerAdapter.addChildClickViewIds(R.id.rb_answer_item);
        answerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            for (int i = 0; i < adapter.getData().size(); i++) {
                if (i != position) {
                    RadioButton radioButton = (RadioButton) adapter.getViewByPosition(i, R.id.rb_answer_item);
                    Objects.requireNonNull(radioButton).setChecked(false);
                    answerAdapter.getItem(i).setChoose(false);
                } else {
                    multipleQuestionBuilder
                        .updateAnswer(((MultipleChooseModel) adapter.getItem(position)).getChoiceID());
                    answerAdapter.getItem(i).setChoose(true);
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

    private void initImage(BaseViewHolder baseViewHolder, QuestionBuilder questionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        questionBuilder.setValue(questionAnswerRequest.getValue());

        if (questionAnswerRequest.getValue() != null) {
            baseViewHolder.getView(R.id.ll_question_item).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.view_image_underline_drawing_question_item).setVisibility(View.VISIBLE);
            View view = baseViewHolder.getView(R.id.iv_answer_picture_question_item);
            view.setVisibility(View.VISIBLE);
            CustomPicasso.getImageLoader(getContext()).load(questionAnswerRequest.getValue()).into((ImageView) view);
        }
    }

    private void initAudio(BaseViewHolder baseViewHolder, QuestionBuilder questionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        questionBuilder.setValue(questionAnswerRequest.getValue());

        if (questionAnswerRequest.getValue() != null) {
            baseViewHolder.getView(R.id.ibtn_record_audio_question_item).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.ibtn_play_audio_question_item).setAlpha(1);
        }

    }

    private void initVideo(BaseViewHolder baseViewHolder, QuestionBuilder questionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        questionBuilder.setValue(questionAnswerRequest.getValue());

        if (questionAnswerRequest.getValue() != null) {
            baseViewHolder.getView(R.id.ll_question_item).setVisibility(View.GONE);

            baseViewHolder.getView(R.id.rl_preview_question_item).setVisibility(View.VISIBLE);

            ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_preview_question_item);
            Glide.with(getContext()).load(questionAnswerRequest.getValue()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        }
    }

    private void initWeight(BaseViewHolder baseViewHolder, QuestionModel questionModel,
        WeightedQuestionBuilder weightedQuestionBuilder,
        SurveyAnswerRequest.QuestionAnswerRequest questionAnswerRequest) {
        List<Integer> weightedValue = null;
        int score = 100;

        if (questionAnswerRequest.getValue() != null) {
            weightedValue = weightedQuestionBuilder.parseWeightedValue(questionAnswerRequest.getValue());
        }
        for (int i = 0; i < questionModel.getMultipleChoose().size(); i++) {
            MultipleChooseModel multipleChooseModel = questionModel.getMultipleChoose().get(i);
            multipleChooseModel.setAnswerType(questionModel.getAnswerType());
            if (weightedValue != null) {
                multipleChooseModel.setValue(weightedValue.get(i));
                score -= weightedValue.get(i);
            }
        }

        TextView tvScore = baseViewHolder.getView(R.id.tv_total_question_item);
        tvScore.setText(
            getContext().getString(R.string.survey_weighted_total_score).replace("${score}", String.valueOf(score)));

        AnswerAdapter answerAdapter = new AnswerAdapter(questionModel.getMultipleChoose(), (value, position) -> {
            updateWeightedValue(baseViewHolder, weightedQuestionBuilder, questionModel, position, value);
        });
        answerAdapter.setResult(isResult);
        answerAdapter.setAnimationEnable(true);
        answerAdapter.setTotalScore(score);

        answerAdapter.addChildClickViewIds(R.id.ib_reduce_answer_item, R.id.ib_add_answer_item);
        answerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CustomIndicatorSeekBar seekBar =
                (CustomIndicatorSeekBar) adapter.getViewByPosition(position, R.id.isb_answer_item);
            if (seekBar != null) {
                if (view.getId() == R.id.ib_reduce_answer_item) {
                    seekBar.setProgress(seekBar.getProgress() - 1);
                } else {
                    seekBar.setProgress(seekBar.getProgress() + 1);
                }
                updateWeightedValue(baseViewHolder, weightedQuestionBuilder, questionModel, position,
                    seekBar.getProgress());
            }
        });

        RecyclerView rvAnswer = baseViewHolder.getView(R.id.rv_answer_question_item);
        answerAdapter.setRecyclerView(rvAnswer);
        rvAnswer.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rvAnswer.setHasFixedSize(true);
        rvAnswer.setAdapter(answerAdapter);

        isWeightedTotalCorrect
            .put(String.valueOf(questionModel.getQuestionID()) + weightedQuestionBuilder.getPosition(), true);
    }

    private void updateWeightedValue(BaseViewHolder baseViewHolder, WeightedQuestionBuilder weightedQuestionBuilder,
        QuestionModel questionModel, int position, int value) {
        List<Integer> weightedValue =
            weightedQuestionBuilder.getWeightedValue(surveyAnswerRequest, questionModel.getMultipleChoose().size());
        weightedValue.set(position, value);
        weightedQuestionBuilder.updateAnswer(weightedValue);
        int score = 100;
        for (int scoreValue : weightedValue) {
            score -= scoreValue;
        }

        TextView tvScore = baseViewHolder.getView(R.id.tv_total_question_item);
        TextView tvQuestionName = baseViewHolder.getView(R.id.tv_title_question_item);
        RecyclerView rvAnswer = baseViewHolder.getView(R.id.rv_answer_question_item);
        tvScore.setText(
            getContext().getString(R.string.survey_weighted_total_score).replace("${score}", String.valueOf(score)));
        AnswerAdapter answerAdapter = (AnswerAdapter) rvAnswer.getAdapter();
        if (answerAdapter != null) {
            answerAdapter.updateWeightedTotalScore(score);
        }
        if (score < 0) {
            tvScore.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed_B70908));
            tvQuestionName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed_B70908));
            this.isWeightedTotalCorrect
                .put(String.valueOf(questionModel.getQuestionID()) + weightedQuestionBuilder.getPosition(), false);
        } else {
            tvScore.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack_000000));
            tvQuestionName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack_000000));
            this.isWeightedTotalCorrect
                .put(String.valueOf(questionModel.getQuestionID()) + weightedQuestionBuilder.getPosition(), true);
        }
    }

    private void resetAudioPlayView() {
        ImageButton ibtnPlay = (ImageButton) getViewByPosition(audioPlayPosition, R.id.ibtn_play_audio_question_item);
        if (ibtnPlay != null) {
            ibtnPlay.setImageResource(R.drawable.ic_play);
            ibtnPlay.setImageTintList(
                ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
            audioWaveView.setWaveColor(getContext().getResources().getColor(R.color.colorBlack_000000));
            audioWaveView.setProgress(0);
        }
    }

    public SpecialAnswerListener getSpecialAnswerListener() {
        return this.specialAnswerListener;
    }

    public QuestionBuilder getQuestionBuilder(String questionId) {
        for (String idAndPosition : questionBuilderMap.keySet()) {
            if (Objects.equals(idAndPosition, questionId)) {
                return questionBuilderMap.get(idAndPosition);
            }
        }
        return null;
    }

    public SurveyAnswerRequest getSurveyAnswerRequest() {
        return surveyAnswerRequest;
    }

    public void setResult(boolean isResult) {
        this.isResult = isResult;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mediaUtils != null) {
            this.answerListener.stopAudio();
            mediaUtils.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        resetAudioPlayView();
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
    public void onStopTracking(float v) {

    }

    @Override
    public void recordVoiceDone(int questionId, String recordUrl) {

    }

    public interface AnswerListener {
        void updateAnswer(int questionId, String value);

        void playAudio(int position, String audioUrl);

        void stopAudio();

        void weightedCorrect(String idAndPosition, boolean isCorrect);
    }

    public interface AnswerCompleteListener {

        void complete();

        void unComplete();
    }
}
