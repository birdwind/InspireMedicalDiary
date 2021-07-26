package com.diary.init.base.utils.fragmentNavUtils;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import com.diary.init.view.viewCallback.BottomNavigationCallback;

public interface FragmentNavigationListener {

    void pushFragment(Fragment fragment);

    void popIndexTabFragment(int tab);

    void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode, boolean isShowBack,
        boolean isShowHeader, boolean isShowRightButton, String rightButtonText, @DrawableRes int rightImageButton,
        BottomNavigationCallback bottomNavigationCallback);
}
