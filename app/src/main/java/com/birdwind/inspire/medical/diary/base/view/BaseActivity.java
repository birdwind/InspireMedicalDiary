package com.birdwind.inspire.medical.diary.base.view;

import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import android.os.Bundle;
import androidx.viewbinding.ViewBinding;

public interface BaseActivity<P extends AbstractPresenter, VB extends ViewBinding> extends BaseMVPView<P, VB> {
    void logout(Bundle bundle);
}
