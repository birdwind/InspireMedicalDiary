package com.birdwind.inspire.medical.diary.view.dialog;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogCommonBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
import android.graphics.PorterDuff;

import androidx.annotation.NonNull;

public class UpdateDialog extends AbstractDialog<CommonDialogListener, AbstractPresenter, DialogCommonBinding> {

    private String content;

    public UpdateDialog(@NonNull @NotNull Context context) {
        super(context, new CommonDialogListener() {});
        this.content = context.getString(R.string.error_update);
        setCancelable(false);
    }

    @Override
    public DialogCommonBinding getViewBinding() {
        return DialogCommonBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {

    }

    @Override
    public void doSomething() {
        if(App.userModel!= null){
            binding.btConfirmDialogCommon.getBackground().setColorFilter(App.userModel.getIdentityMainColor(), PorterDuff.Mode.SRC_IN);
        }

    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }
}
