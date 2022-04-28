package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogUserBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.enums.ScanUserMessageEnums;
import com.birdwind.inspire.medical.diary.model.response.AddUserResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.presenter.UserDialogPresenter;
import com.birdwind.inspire.medical.diary.view.adapter.SurveyAdapter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.dialog.callback.UserDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.UserDialogView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserDialog extends AbstractDialog<CommonDialogListener, UserDialogPresenter, DialogUserBinding>
    implements UserDialogView {

    private final int STATE_ADD = 0;

    private final int STATE_SURVEY = 1;

    private UserResponse userResponse;

    private UserResponse.Response user;

    private UserDialogListener userDialogListener;

    private DiseaseEnums diseaseEnums;

    private int progressState;

    private SurveyAdapter patientSurveyAdapter;

    private SurveyAdapter familySurveyAdapter;

    private SurveyListResponse.Response selectPatientSurvey;

    private SurveyListResponse.Response selectFamilySurvey;

    private AddUserResponse.Response addUserResponse;

    public UserDialog(@NonNull @NotNull Context context, UserResponse userResponse,
        UserDialogListener userDialogListener) {
        super(context, new CommonDialogListener() {});
        this.userResponse = userResponse;
        this.userDialogListener = userDialogListener;
        user = userResponse.getJsonData();
        diseaseEnums = DiseaseEnums.NOT_SET;

        patientSurveyAdapter = new SurveyAdapter(R.layout.item_survey);
        patientSurveyAdapter.setAnimationEnable(true);
        familySurveyAdapter = new SurveyAdapter(R.layout.item_survey);
        familySurveyAdapter.setAnimationEnable(true);
    }

    @Override
    public DialogUserBinding getViewBinding() {
        return DialogUserBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.ibtnHeaderCloseDialogUser.setOnClickListener(v -> {
            dismiss();
        });

        binding.rbHeadacheDialogUser.setOnClickListener(v -> {
            diseaseEnums = DiseaseEnums.HEADACHE;
        });
        binding.rbAzDialogUser.setOnClickListener(v -> {
            diseaseEnums = DiseaseEnums.ALZHEIMER;
        });
        binding.rbBgDialogUser.setOnClickListener(v -> {
            diseaseEnums = DiseaseEnums.PERKINS;
        });

        binding.btConfirmDialogUser.setOnClickListener(v -> {
            if (progressState == STATE_ADD) {
                progressState = STATE_SURVEY;
                if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
                    updateView();
                    getSurvey(diseaseEnums.getType(), IdentityEnums.PAINTER.getType());
                } else {
                    addPatient();
                }
            } else {
                boolean isContinue = selectPatientSurvey != null;
                if (diseaseEnums != DiseaseEnums.HEADACHE) {
                    isContinue = selectFamilySurvey != null;
                }
                if (isContinue) {
                    addPatient();
                }

            }
        });

        binding.btPrevButtonDialogUser.setOnClickListener(v -> {
            progressState = STATE_ADD;
            updateView();
        });

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
    }

    @Override
    public void doSomething() {
        progressState = STATE_ADD;
        String identity = "";
        IdentityEnums identityEnums = IdentityEnums.parseEnumsByType(user.getIdentity());

        switch (identityEnums) {
            case DOCTOR:
                identity = context.getString(R.string.identity_doctor);
                break;
            case FAMILY:
                identity = context.getString(R.string.identity_family);
                break;
            case PAINTER:
                identity = context.getString(R.string.identity_patient);
                break;
        }
        binding.tvIdentityDialogUser.setText(identity);
        binding.tvNameDialogUser.setText(user.getName());

        parseWithIdentity();
        Glide.with(context).load(userResponse.getJsonData().getPhotoUrl())
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar)).into(binding.civImageDialogUser);

        if (App.userModel != null) {
            binding.btConfirmDialogUser.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
                PorterDuff.Mode.SRC_IN);

            if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
                binding.rgOptionDialogUser.setVisibility(View.VISIBLE);
            } else {
                binding.rgOptionDialogUser.setVisibility(View.GONE);
            }
        }

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

        updateView();

        binding.rgOptionDialogUser.clearCheck();
    }

    @Override
    public UserDialogPresenter createPresenter() {
        return new UserDialogPresenter(this);
    }

    private void parseWithIdentity() {
        ScanUserMessageEnums scanUserMessageEnums = ScanUserMessageEnums.parseEnumsByKey(userResponse.getMessage());
        binding.rbHeadacheDialogUser.setVisibility(View.VISIBLE);

        if (scanUserMessageEnums != null) {
            switch (scanUserMessageEnums) {
                case IS_PAINTER:
                case IS_FAMILY:
                case IS_DOCTOR:
                case DOCTOR_ALREADY_ADD_PAINTER:
                case HAVE_ANOTHER_DOCTOR:
                case HAVE_DOCTOR:
                    binding.rgOptionDialogUser.setVisibility(View.GONE);
                    showError(true, scanUserMessageEnums, null);
                    break;
                case ADD_TO_PROXY_PAINTER:
                    binding.rbHeadacheDialogUser.setVisibility(View.GONE);
                case ADD_TO_PAINTER:
                    binding.rgOptionDialogUser.setVisibility(View.VISIBLE);
                case ADD_TO_FAMILY:
                case ADD_TO_DOCTOR:
                    showError(false, null, null);
                    break;
                default:
                    binding.rgOptionDialogUser.setVisibility(View.GONE);
            }
        } else {
            showError(false, null, userResponse.getMessage());
        }
    }

    private void showError(boolean isShow, @Nullable ScanUserMessageEnums scanUserMessageEnums, @Nullable String msg) {
        if (isShow) {
            assert scanUserMessageEnums != null;
            binding.tvReasonDialogUser.setText(scanUserMessageEnums.getKey());
            binding.tvReasonDialogUser.setVisibility(View.VISIBLE);
            binding.btConfirmDialogUser.setVisibility(View.GONE);
        } else {
            changeAddButtonColorWithIdentity();
            if (TextUtils.isEmpty(msg)) {
                binding.tvReasonDialogUser.setVisibility(View.GONE);
                binding.btConfirmDialogUser.setVisibility(View.VISIBLE);
            } else {
                binding.tvReasonDialogUser.setText(msg);
                binding.tvReasonDialogUser.setVisibility(View.VISIBLE);
                binding.btConfirmDialogUser.setVisibility(View.GONE);
            }
        }
    }

    private void changeAddButtonColorWithIdentity() {
        binding.btConfirmDialogUser.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
            PorterDuff.Mode.SRC_ATOP);
    }

    private void addPatient() {
        if (diseaseEnums != DiseaseEnums.NOT_SET || App.userModel.getIdentityEnums() != IdentityEnums.DOCTOR) {
            presenter.addUser(user.getUID(), diseaseEnums);
        } else {
            ToastUtils.show(context, R.string.painter_dialog_not_select);
        }
    }

    private void getSurvey(int disease, int identity) {
        presenter.getSurvey(disease, identity);
    }

    private void updatePatientSurvey() {
        presenter.setSurvey(user.getUID(), selectPatientSurvey.getSurveyID(), IdentityEnums.PAINTER.getType());
    }

    @Override
    public void dismiss() {
        userDialogListener.userDialogClose();
        super.dismiss();
    }

    @Override
    public void onAddUser(boolean isSuccess, AddUserResponse.Response response) {
        if (isSuccess) {
            addUserResponse = response;
            if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
                updatePatientSurvey();
            } else {
                dismiss();
                userDialogListener.userDialogAdded(addUserResponse);
            }
        }
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
                    getSurvey(diseaseEnums.getType(), IdentityEnums.FAMILY.getType());
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
                    break;
            }
        }
    }

    @Override
    public void onSetSurvey(boolean isSuccess, String response, int identity) {
        if (isSuccess) {
            switch (identity) {
                case 0:
                    // Patient
                    if (diseaseEnums == DiseaseEnums.HEADACHE) {
                        dismiss();
                        userDialogListener.userDialogAdded(addUserResponse);
                    } else {
                        presenter.setSurvey(user.getUID(), selectFamilySurvey.getSurveyID(),
                            IdentityEnums.FAMILY.getType());
                    }
                    break;
                case 1:
                    // Family
                    dismiss();
                    userDialogListener.userDialogAdded(addUserResponse);
                    break;
            }
        } else {
            ToastUtils.show(context, R.string.painter_dialog_select_survey_failed);
        }
    }

    private void updateView() {
        switch (progressState) {
            case STATE_ADD:
                binding.btConfirmDialogUser.setText(R.string.common_next);
                binding.btPrevButtonDialogUser.setVisibility(View.GONE);
                binding.llSurveyUserDialog.setVisibility(View.GONE);
                binding.llMainUserDialog.setVisibility(View.VISIBLE);
                break;
            case STATE_SURVEY:
                binding.btConfirmDialogUser.setText(R.string.common_confirm);
                binding.btPrevButtonDialogUser.setVisibility(View.VISIBLE);
                binding.llSurveyUserDialog.setVisibility(View.VISIBLE);
                binding.llMainUserDialog.setVisibility(View.GONE);
                break;
        }
    }
}
