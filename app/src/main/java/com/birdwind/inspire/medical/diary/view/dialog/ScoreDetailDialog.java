package com.birdwind.inspire.medical.diary.view.dialog;

import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogScoreDetailBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.model.response.ScoreDetailResponse;
import com.birdwind.inspire.medical.diary.presenter.ScoreDetailPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.ScoreDetailDialogView;
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

    public ScoreDetailDialog(@NonNull Context context, int quizId, DiseaseEnums diseaseEnums) {
        this(context, new CommonDialogListener() {}, quizId, diseaseEnums);
    }

    public ScoreDetailDialog(@NonNull Context context, CommonDialogListener dialogListener, int quizId,
        DiseaseEnums diseaseEnums) {
        super(context, dialogListener);
        this.quizId = quizId;
        this.diseaseEnums = diseaseEnums;
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
        switch (diseaseEnums) {
            case HEADACHE:
                binding.compHeadacheScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                binding.compAkzhimerScoreDetailDialog.getRoot().setVisibility(View.GONE);
                break;
            case ALZHEIMER:
            case PERKINS:
                binding.compHeadacheScoreDetailDialog.getRoot().setVisibility(View.GONE);
                binding.compAkzhimerScoreDetailDialog.getRoot().setVisibility(View.VISIBLE);
                break;
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
        switch (diseaseEnums) {
            case HEADACHE:
                parseToHeadacheQuiz(response);
                break;
            case ALZHEIMER:
            case PERKINS:
                parseToAlzheimerQuiz(response);
                break;
        }
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
        LinearLayout llAlzheimer = binding.compAkzhimerScoreDetailDialog.getRoot();
        initRadioButton(llAlzheimer);
        for (ScoreDetailResponse.Score scoreItem : response.getItems()) {
            switch (scoreItem.getNo()) {
                case 1:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption1YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption1NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 2:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption2YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption2NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 3:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption3YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption3NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 4:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption4YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption4NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 5:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption5YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption5NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 6:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption6YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption6NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 7:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption7YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption7NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 8:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption8YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption8NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 9:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption9YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption9NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 10:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption10YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption10NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 11:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption11YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption11NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
                case 12:
                    setRadioButtonByInteger(binding.compAkzhimerScoreDetailDialog.rbOption12YesQuizAkzhimerComponent,
                        binding.compAkzhimerScoreDetailDialog.rbOption12NoQuizAkzhimerComponent, scoreItem.getScore());
                    break;
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
