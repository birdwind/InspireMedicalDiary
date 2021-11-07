package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.utils.LogUtils;
import com.birdwind.inspire.medical.diary.base.utils.ToastUtils;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogPatientNameBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.PatineNameDialogListener;

public class PatineNameDialog
    extends AbstractDialog<PatineNameDialogListener, AbstractPresenter, DialogPatientNameBinding> {

    public PatineNameDialog(@NonNull Context context) {
        super(context, new PatineNameDialogListener() {
            @Override
            public void clickConfirm(String name) {
                LogUtils.d("尚未實作");
            }
        });
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public PatineNameDialog(@NonNull Context context, PatineNameDialogListener dialogListener) {
        super(context, dialogListener);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public DialogPatientNameBinding getViewBinding() {
        return DialogPatientNameBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btConfirmPatientNameDialog.setOnClickListener(v -> {
            String name = String.valueOf(binding.etNamePatientNameDialog.getText()).trim();
            if (!TextUtils.isEmpty(name)) {
                dialogListener.clickConfirm(name);
                dismiss();
            } else {
                ToastUtils.show(context, "請輸入病患名稱");
            }
        });
    }

    @Override
    public void doSomething() {

        if (App.userModel != null) {
            binding.btConfirmPatientNameDialog.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
                PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }

    @Override
    public void show() {
        super.show();
    }
}
