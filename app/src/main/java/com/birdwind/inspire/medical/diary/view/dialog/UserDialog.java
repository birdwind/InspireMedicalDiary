package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogUserBinding;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.enums.ScanUserMessageEnums;
import com.birdwind.inspire.medical.diary.model.response.UserResponse;
import com.birdwind.inspire.medical.diary.presenter.UserDialogPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import com.birdwind.inspire.medical.diary.view.dialog.callback.UserDialogListener;
import com.birdwind.inspire.medical.diary.view.viewCallback.UserDialogView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

public class UserDialog extends AbstractDialog<CommonDialogListener, UserDialogPresenter, DialogUserBinding>
    implements UserDialogView {

    private UserResponse userResponse;

    private UserResponse.Response user;

    private UserDialogListener userDialogListener;

    private DiseaseEnums diseaseEnums;

    public UserDialog(@NonNull @NotNull Context context, UserResponse userResponse,
        UserDialogListener userDialogListener) {
        super(context, new CommonDialogListener() {});
        this.userResponse = userResponse;
        this.userDialogListener = userDialogListener;
        user = userResponse.getJsonData();
        diseaseEnums = DiseaseEnums.NOT_SET;
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

        binding.btButtonDialogUser.setOnClickListener(v -> {
            if (diseaseEnums != DiseaseEnums.NOT_SET || App.userModel.getIdentityEnums() != IdentityEnums.DOCTOR) {
                presenter.addUser(user.getUID(), diseaseEnums);
            } else {
                ToastUtils.show(context, R.string.painter_dialog_not_select);
            }
        });
    }

    @Override
    public void doSomething() {
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
            binding.btButtonDialogUser.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
                PorterDuff.Mode.SRC_IN);

            if (App.userModel.getIdentityEnums() == IdentityEnums.DOCTOR) {
                binding.rgOptionDialogUser.setVisibility(View.VISIBLE);
            } else {
                binding.rgOptionDialogUser.setVisibility(View.GONE);
            }
        }

        binding.rgOptionDialogUser.clearCheck();

    }

    @Override
    public UserDialogPresenter createPresenter() {
        return new UserDialogPresenter(this);
    }

    private void parseWithIdentity() {
        ScanUserMessageEnums scanUserMessageEnums = ScanUserMessageEnums.parseEnumsByKey(userResponse.getMessage());
        if (scanUserMessageEnums != null) {
            switch (scanUserMessageEnums) {
                case IS_PAINTER:
                case IS_FAMILY:
                case IS_DOCTOR:
                case DOCTOR_ALREADY_ADD_PAINTER:
                case HAVE_ANOTHER_DOCTOR:
                case HAVE_DOCTOR:
                    binding.rgOptionDialogUser.setVisibility(View.GONE);
                    showError(true, scanUserMessageEnums);
                    break;
                case ADD_TO_PAINTER:
                case ADD_TO_PROXY_PAINTER:
                    binding.rgOptionDialogUser.setVisibility(View.VISIBLE);
                case ADD_TO_FAMILY:
                case ADD_TO_DOCTOR:
                    showError(false, null);
                    break;
                default:
                    binding.rgOptionDialogUser.setVisibility(View.GONE);
            }
        }
    }

    private void showError(boolean isShow, @Nullable ScanUserMessageEnums scanUserMessageEnums) {
        if (isShow) {
            assert scanUserMessageEnums != null;
            binding.tvReasonDialogUser.setText(scanUserMessageEnums.getKey());
            binding.tvReasonDialogUser.setVisibility(View.VISIBLE);
            binding.btButtonDialogUser.setVisibility(View.GONE);
        } else {
            changeAddButtonColorWithIdentity();
            binding.tvReasonDialogUser.setVisibility(View.GONE);
            binding.btButtonDialogUser.setVisibility(View.VISIBLE);
        }
    }

    private void changeAddButtonColorWithIdentity() {
        binding.btButtonDialogUser.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
            PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void dismiss() {
        userDialogListener.userDialogClose();
        super.dismiss();
    }

    @Override
    public void onAddUser(boolean isSuccess) {
        if (isSuccess) {
            dismiss();
            userDialogListener.userDialogAdded();
        }
    }
}
