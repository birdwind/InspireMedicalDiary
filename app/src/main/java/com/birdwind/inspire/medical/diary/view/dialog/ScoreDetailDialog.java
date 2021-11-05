package com.birdwind.inspire.medical.diary.view.dialog;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogScoreDetailBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.ScoreDetailResponse;
import com.birdwind.inspire.medical.diary.presenter.ScoreDetailPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScoreDetailDialogView;
import com.warkiz.widget.IndicatorSeekBar;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;

public class ScoreDetailDialog
    extends AbstractDialog<CommonDialogListener, ScoreDetailPresenter, DialogScoreDetailBinding>
    implements ScoreDetailDialogView {

    private int quizId;

    private DiseaseEnums diseaseEnums;

    private boolean isFamily;

    public ScoreDetailDialog(@NonNull Context context, int quizId, DiseaseEnums diseaseEnums, boolean isFamily) {
        this(context, new CommonDialogListener() {}, quizId, diseaseEnums, isFamily);
    }

    public ScoreDetailDialog(@NonNull Context context, CommonDialogListener dialogListener, int quizId,
        DiseaseEnums diseaseEnums, boolean isFamily) {
        super(context, dialogListener);
        this.quizId = quizId;
        this.diseaseEnums = diseaseEnums;
        this.isFamily = isFamily;
    }

    @Override
    public DialogScoreDetailBinding getViewBinding() {
        return DialogScoreDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void doSomething() {
        presenter.GetScoreDetail(quizId);
        initQuestionView();
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
            if (isFamily) {
                switch (diseaseEnums) {
                    case ALZHEIMER:
                        binding.compAlzheimerScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case PERKINS:
                        binding.compPerkinsFamilyScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                binding.compPerkinsPatientScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
            }
        } else {
            switch (diseaseEnums) {
                case HEADACHE:
                    binding.compHeadacheScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                    break;
                case ALZHEIMER:
                    binding.compAlzheimerScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                    break;
                case PERKINS:
                    binding.compPerkinsPatientScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    @Override
    public ScoreDetailPresenter createPresenter() {
        return new ScoreDetailPresenter(this);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onGetDetailSuccess(ScoreDetailResponse.Response response) {
        if (App.userModel.getIdentityEnums() == IdentityEnums.FAMILY) {
            initQuestionView();
            if (isFamily) {
                switch (diseaseEnums) {
                    case ALZHEIMER:
                        parseToAlzheimerQuiz(response);
                        break;
                    case PERKINS:
                        parseToPerkinsFamilyQuiz(response);
                        break;
                }
            } else {
                parseToPerkinsPatientQuiz(response);
            }
        } else {
            switch (diseaseEnums) {
                case HEADACHE:
                    parseToHeadacheQuiz(response);
                    break;
                case ALZHEIMER:
                    parseToAlzheimerQuiz(response);
                    break;
                case PERKINS:
                    parseToPerkinsPatientQuiz(response);
                    break;
            }
        }
    }

    private void initQuestionView() {
        binding.compHeadacheScoreDetailDialog.getRoot().setVisibility(View.GONE);
        binding.compAlzheimerScoreDetailDialog.getRoot().setVisibility(View.GONE);
        binding.compPerkinsFamilyScoreDetailDialog.getRoot().setVisibility(View.GONE);
        binding.compPerkinsPatientScoreDetailDialog.getRoot().setVisibility(View.GONE);
    }

    private void parseToHeadacheQuiz(ScoreDetailResponse.Response response) {
        LinearLayout llHeadache = binding.compHeadacheScoreDetailDialog.getRoot();
        initRadioButton(llHeadache);
        binding.compHeadacheScoreDetailDialog.ivHeadache1QuizHeadacheComp.setEnabled(false);
        binding.compHeadacheScoreDetailDialog.ivHeadache2QuizHeadacheComp.setEnabled(false);
        binding.compHeadacheScoreDetailDialog.ivHeadache3QuizHeadacheComp.setEnabled(false);
        binding.compHeadacheScoreDetailDialog.ivHeadache4QuizHeadacheComp.setEnabled(false);
        binding.compHeadacheScoreDetailDialog.ivHeadache5QuizHeadacheComp.setEnabled(false);
        binding.compHeadacheScoreDetailDialog.ivHeadache6QuizHeadacheComp.setEnabled(false);
        for (ScoreDetailResponse.Score scoreItem : response.getItems()) {
            switch (scoreItem.getNo()) {
                case 1:
                    setRadioButtonByInteger(binding.compHeadacheScoreDetailDialog.rbOption1YesQuizHeadacheComp,
                        binding.compHeadacheScoreDetailDialog.rbOption1NoQuizHeadacheComp, scoreItem.getScore());
                    break;
                case 2:
                    setRadioButtonByInteger(binding.compHeadacheScoreDetailDialog.rbOption2YesQuizHeadacheComp,
                        binding.compHeadacheScoreDetailDialog.rbOption2NoQuizHeadacheComp, scoreItem.getScore());
                    break;
                case 3:
                    setRadioButtonByInteger(binding.compHeadacheScoreDetailDialog.rbOption3YesQuizHeadacheComp,
                        binding.compHeadacheScoreDetailDialog.rbOption3NoQuizHeadacheComp, scoreItem.getScore());
                    break;
                case 4:
                    setRadioButtonByInteger(binding.compHeadacheScoreDetailDialog.rbOption4YesQuizHeadacheComp,
                        binding.compHeadacheScoreDetailDialog.rbOption4NoQuizHeadacheComp, scoreItem.getScore());
                    break;
                case 5:
                    setRadioButtonByInteger(binding.compHeadacheScoreDetailDialog.rbOption5YesQuizHeadacheComp,
                        binding.compHeadacheScoreDetailDialog.rbOption5NoQuizHeadacheComp, scoreItem.getScore());
                    break;
                case 6:
                    switch (scoreItem.getScore()) {
                        case 1:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache1QuizHeadacheComp);
                            break;
                        case 2:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache2QuizHeadacheComp);
                            break;
                        case 3:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache3QuizHeadacheComp);
                            break;
                        case 4:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache4QuizHeadacheComp);
                            break;
                        case 5:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache5QuizHeadacheComp);
                            break;
                        case 6:
                            setImageViewSelected(binding.compHeadacheScoreDetailDialog.ivHeadache6QuizHeadacheComp);
                            break;
                    }
            }
        }

    }

    private void parseToAlzheimerQuiz(ScoreDetailResponse.Response response) {
        LinearLayout llAlzheimer = binding.compAlzheimerScoreDetailDialog.getRoot();
        initRadioButton(llAlzheimer);
        for (ScoreDetailResponse.Score scoreItem : response.getItems()) {
            switch (scoreItem.getNo()) {
                case 1:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption1YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption1NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 2:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption2YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption2NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 3:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption3YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption3NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 4:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption4YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption4NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 5:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption5YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption5NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 6:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption6YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption6NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 7:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption7YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption7NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 8:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption8YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption8NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 9:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption9YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption9NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 10:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption10YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption10NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 11:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption11YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption11NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 12:
                    setRadioButtonByInteger(binding.compAlzheimerScoreDetailDialog.rbOption12YesQuizAkzhimerComponent,
                        binding.compAlzheimerScoreDetailDialog.rbOption12NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
            }
        }
    }

    private void parseToPerkinsPatientQuiz(ScoreDetailResponse.Response response) {
        LinearLayout quizLinearLayout = binding.compPerkinsFamilyScoreDetailDialog.getRoot();
        for (int i = 0; i < quizLinearLayout.getChildCount(); i++) {
            View quizView = quizLinearLayout.getChildAt(i);
            if (quizView instanceof LinearLayout) {
                LinearLayout questionLinearLayout = (LinearLayout) quizView;
                for (int j = 0; j < questionLinearLayout.getChildCount(); j++) {
                    View answerView = questionLinearLayout.getChildAt(j);
                    if (answerView instanceof IndicatorSeekBar) {
                        IndicatorSeekBar answerIndicatorSeekBar = (IndicatorSeekBar) answerView;
                        answerIndicatorSeekBar.setProgress(response.getScore());
                        answerIndicatorSeekBar.setEnabled(false);
                    }
                }
            }
        }
    }

    private void parseToPerkinsFamilyQuiz(ScoreDetailResponse.Response response) {
        LinearLayout quizLinearLayout = binding.compPerkinsFamilyScoreDetailDialog.getRoot();
        for (int i = 0; i < quizLinearLayout.getChildCount(); i++) {
            View quizView = quizLinearLayout.getChildAt(i);
            if (quizView instanceof LinearLayout) {
                LinearLayout questionLinearLayout = (LinearLayout) quizView;
                for (int j = 0; j < questionLinearLayout.getChildCount(); j++) {
                    View answerView = questionLinearLayout.getChildAt(j);
                    if (answerView instanceof IndicatorSeekBar) {
                        IndicatorSeekBar answerIndicatorSeekBar = (IndicatorSeekBar) answerView;
                        answerIndicatorSeekBar.setProgress(response.getScore());
                        answerIndicatorSeekBar.setEnabled(false);
                    }
                }
            }
        }
    }

    private void initRadioButton(LinearLayout view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            View innerView = view.getChildAt(i);
            if (innerView instanceof LinearLayout) {
                for (int j = 0; j < ((LinearLayout) innerView).getChildCount(); j++) {
                    View linearLayoutInnerView = ((LinearLayout) innerView).getChildAt(j);
                    if (linearLayoutInnerView instanceof RadioGroup) {
                        for (int k = 0; k < ((RadioGroup) linearLayoutInnerView).getChildCount(); k++) {
                            View radioGroupInnerView = ((RadioGroup) linearLayoutInnerView).getChildAt(k);
                            if (radioGroupInnerView instanceof RadioButton) {
                                radioGroupInnerView.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setRadioButtonByInteger(RadioButton yesRadioButton, RadioButton noRadioButton, int score) {
        yesRadioButton.setChecked(score == 1);
        noRadioButton.setChecked(score != 1);
    }

    private void setImageViewSelected(ImageView imageView) {
        imageView.setAlpha(1f);
    }
}
