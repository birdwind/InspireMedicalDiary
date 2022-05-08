package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogPatientManagerBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.birdwind.inspire.medical.diary.presenter.PatientManagerDialogPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.SurveyAdapter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientManagerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PatientManagerDialog
    extends AbstractDialog<CommonDialogListener, PatientManagerDialogPresenter, DialogPatientManagerBinding>
    implements PatientManagerView {

    private Long uid;

    private DiseaseEnums diseaseEnums;

    private String name;

    private SurveyAdapter patientSurveyAdapter;

    private SurveyAdapter familySurveyAdapter;

    private SurveyListResponse.Response selectPatientSurvey;

    private SurveyListResponse.Response selectFamilySurvey;

    public PatientManagerDialog(@NonNull @NotNull Context context, Long uid, DiseaseEnums diseaseEnums) {
        super(context, new CommonDialogListener() {});
        this.uid = uid;
        this.diseaseEnums = diseaseEnums;

        patientSurveyAdapter = new SurveyAdapter(R.layout.item_survey);
        patientSurveyAdapter.setAnimationEnable(true);
        familySurveyAdapter = new SurveyAdapter(R.layout.item_survey);
        familySurveyAdapter.setAnimationEnable(true);
    }

    @Override
    public DialogPatientManagerBinding getViewBinding() {
        return DialogPatientManagerBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        patientSurveyAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectPatientSurvey = (SurveyListResponse.Response) adapter.getData().get(position);
            for (int i = 0; i < adapter.getData().size(); i++) {
                ImageView imageView = (ImageView) adapter.getViewByPosition(i, R.id.iv_check_survey_item);
                TextView textView = (TextView) adapter.getViewByPosition(i, R.id.tv_name_survey_item);
                if (imageView != null && textView != null) {
                    if (i == position) {
                        imageView.setImageTintList(ColorStateList
                            .valueOf(getContext().getResources().getColor(App.userModel.getIdentityMainColorId())));
                        textView.setTextColor(App.userModel.getIdentityMainColorId());
                    } else {
                        imageView.setImageTintList(
                            ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
                        textView.setTextColor(getContext().getResources().getColor(R.color.colorBlack_000000));
                    }
                }
            }
        });

        familySurveyAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectFamilySurvey = (SurveyListResponse.Response) adapter.getData().get(position);
            for (int i = 0; i < adapter.getData().size(); i++) {
                ImageView imageView = (ImageView) adapter.getViewByPosition(i, R.id.iv_check_survey_item);
                TextView textView = (TextView) adapter.getViewByPosition(i, R.id.tv_name_survey_item);
                if (imageView != null && textView != null) {
                    if (i == position) {
                        imageView.setImageTintList(ColorStateList
                            .valueOf(getContext().getResources().getColor(App.userModel.getIdentityMainColorId())));
                        textView.setTextColor(App.userModel.getIdentityMainColorId());
                    } else {
                        imageView.setImageTintList(
                            ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorBlack_000000)));
                        textView.setTextColor(getContext().getResources().getColor(R.color.colorBlack_000000));
                    }
                }
            }
        });

        binding.btConfirmDialogPatientManager.setOnClickListener(v -> {
            if (selectPatientSurvey != null) {
                presenter.setSurvey(uid, selectPatientSurvey.getSurveyID(), IdentityEnums.PAINTER.getType());
            }
            if (selectFamilySurvey != null) {
                presenter.setSurvey(uid, selectFamilySurvey.getSurveyID(), IdentityEnums.FAMILY.getType());
            }
            dismiss();
        });
    }

    @Override
    public void doSomething() {
        presenter.getSurvey(diseaseEnums.getType(), IdentityEnums.PAINTER.getType());

        patientSurveyAdapter.setRecyclerView(binding.rvPatientSurveyUserDialog);
        familySurveyAdapter.setRecyclerView(binding.rvFamilySurveyUserDialog);

        binding.rvPatientSurveyUserDialog
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvPatientSurveyUserDialog.setHasFixedSize(true);
        binding.rvPatientSurveyUserDialog.setAdapter(patientSurveyAdapter);

        binding.rvFamilySurveyUserDialog
            .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvFamilySurveyUserDialog.setHasFixedSize(true);
        binding.rvFamilySurveyUserDialog.setAdapter(familySurveyAdapter);
    }

    @Override
    public PatientManagerDialogPresenter createPresenter() {
        return new PatientManagerDialogPresenter(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onGetSurvey(boolean isSuccess, List<SurveyListResponse.Response> response, int identity) {
        if (isSuccess) {
            switch (identity) {
                case 0:
                    // Patient
                    if (response.size() > 0) {
                        binding.rvPatientSurveyUserDialog.setVisibility(View.VISIBLE);
                        binding.tvEmptyPatientSurveyUserDialog.setVisibility(View.GONE);
                    } else {
                        binding.rvPatientSurveyUserDialog.setVisibility(View.GONE);
                        binding.tvEmptyPatientSurveyUserDialog.setVisibility(View.VISIBLE);
                    }
                    patientSurveyAdapter.setList(response);
                    presenter.getSurvey(diseaseEnums.getType(), IdentityEnums.FAMILY.getType());
                    break;
                case 1:
                    // Family
                    if (response.size() > 0) {
                        binding.rvFamilySurveyUserDialog.setVisibility(View.VISIBLE);
                        binding.tvEmptyFamilySurveyUserDialog.setVisibility(View.GONE);
                    } else {
                        binding.rvFamilySurveyUserDialog.setVisibility(View.GONE);
                        binding.tvEmptyFamilySurveyUserDialog.setVisibility(View.VISIBLE);
                    }
                    familySurveyAdapter.setList(response);
                    presenter.getInformation(uid);
                    break;
            }
        }
    }

    @Override
    public void onSetSurvey(boolean isSuccess, String response, int identity) {
        if (isSuccess) {
            // if (identity == IdentityEnums.PAINTER.getType()) {
            // } else {
            dismiss();
            // }
        } else {
            ToastUtils.show(getContext(), "問卷更新失敗，請稍後再試");
        }
    }

    @Override
    public void onGetInformation(boolean isSuccess, InformationResponse.Response informationResponse) {
        if(informationResponse.getName() == null){
            binding.tvNamePatientManagerDialog
                    .setText("尚未設定姓名" + "(" + informationResponse.getSexString() + ")");
        }else{
            binding.tvNamePatientManagerDialog
                    .setText(informationResponse.getName() + "(" + informationResponse.getSexString() + ")");
        }
        binding.tvIdcardPatientManagerDialog.setText(informationResponse.getIDNumber());
        binding.tvPhonePatientManagerDialog.setText(informationResponse.getPhone());
    }
}
