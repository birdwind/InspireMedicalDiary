package com.birdwind.inspire.medical.diary.view.dialog;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogCommonBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;

import org.jetbrains.annotations.NotNull;

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
        binding.btConfirmDialogCommon.setOnClickListener(v -> {
            String urlString = "https://toxto.top/File/App";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void doSomething() {
        if (App.userModel != null) {
            binding.btConfirmDialogCommon.getBackground().setColorFilter(App.userModel.getIdentityMainColor(),
                PorterDuff.Mode.SRC_IN);
        }
        binding.tvContentDialogCommon.setText(this.content);
        binding.tvHeaderTitleDialogCommon.setText(context.getString(R.string.common_dialog_title));
    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }
}
