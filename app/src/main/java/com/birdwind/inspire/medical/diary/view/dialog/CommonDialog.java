package com.birdwind.inspire.medical.diary.view.dialog;

import com.birdwind.inspire.medical.diary.base.view.AbstractDialog;
import com.birdwind.inspire.medical.diary.databinding.DialogCommonBinding;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.dialog.callback.CommonDialogListener;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import androidx.annotation.NonNull;

public class CommonDialog extends AbstractDialog<CommonDialogListener, AbstractPresenter, DialogCommonBinding> {

    private String title;

    private Spanned content;

    public CommonDialog(@NonNull Context context, String title, String content) {
        super(context, new CommonDialogListener() {});
        this.title = title;
        this.content = Html.fromHtml(content);
        setCancelable(false);
    }

    public CommonDialog(@NonNull Context context, CommonDialogListener dialogListener, String title, String content) {
        super(context, dialogListener);
        this.title = title;
        this.content = Html.fromHtml(content);
        setCancelable(false);
    }

    @Override
    public DialogCommonBinding getViewBinding() {
        return DialogCommonBinding.inflate(getLayoutInflater());
    }

    @Override
    public void addListener() {
        binding.btConfirmDialogCommon.setOnClickListener(v -> {
            dialogListener.clickConfirm();
            dismiss();
        });

        binding.ibtnHeaderCloseDialogCommon.setOnClickListener(v->{
            dialogListener.clickClose();
            dismiss();
        });
    }

    @Override
    public void doSomething() {
        binding.tvContentDialogCommon.setText(content);
        binding.tvHeaderTitleDialogCommon.setText(title);
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
