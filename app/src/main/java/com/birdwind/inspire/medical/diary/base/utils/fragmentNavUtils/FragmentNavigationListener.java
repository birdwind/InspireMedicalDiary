package com.birdwind.inspire.medical.diary.base.utils.fragmentNavUtils;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;

import com.birdwind.inspire.medical.diary.view.viewCallback.BottomNavigationCallback;

public interface FragmentNavigationListener {

    void pushFragment(Fragment fragment);

    void popIndexTabFragment(int tab);

    void updateToolbar(String title, int titleColor, int backgroundColor, boolean isStatusLightMode, boolean isShowBack,
        boolean isShowHeader, boolean isShowRightButton, String rightButtonText, @DrawableRes int rightImageButton,
        BottomNavigationCallback bottomNavigationCallback);
}
