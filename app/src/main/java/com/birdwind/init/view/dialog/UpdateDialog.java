package com.birdwind.init.view.dialog;

import com.birdwind.init.R;
import com.birdwind.init.base.view.AbstractDialog;
import com.birdwind.init.databinding.DialogCommonBinding;
import com.birdwind.init.presenter.AbstractPresenter;
import com.birdwind.init.view.dialog.callback.CommonDialogListener;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
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

    }

    @Override
    public AbstractPresenter createPresenter() {
        return null;
    }
}
