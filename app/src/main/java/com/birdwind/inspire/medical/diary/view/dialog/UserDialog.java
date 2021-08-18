package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogUserBinding;
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

    public UserDialog(@NonNull @NotNull Context context, UserResponse userResponse,
        UserDialogListener userDialogListener) {
        super(context, new CommonDialogListener() {});
        this.userResponse = userResponse;
        this.userDialogListener = userDialogListener;
        user = userResponse.getJsonData();
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

        binding.btButtonDialogUser.setOnClickListener(v -> {
            presenter.addUser(user.getUid());
            dismiss();
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
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avator)).into(binding.civImageDialogUser);

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
                    showError(true, scanUserMessageEnums);
                    break;
                case ADD_TO_PAINTER:
                case ADD_TO_FAMILY:
                case ADD_TO_DOCTOR:
                    showError(false, null);
                    break;
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
        userDialogListener.close();
        super.dismiss();
    }
}