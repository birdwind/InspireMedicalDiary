package com.birdwind.init.base.view;

import com.birdwind.init.presenter.AbstractPresenter;
import android.os.Bundle;
import androidx.viewbinding.ViewBinding;

public interface BaseActivity<P extends AbstractPresenter, VB extends ViewBinding> extends BaseMVPView<P, VB> {
    void logout(Bundle bundle);
}
