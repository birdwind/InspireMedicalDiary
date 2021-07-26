package com.birdwind.init.view.viewCallback;

import android.view.View;

public interface BottomNavigationCallback {

    default void clickTopBarRightButton(View view){}

    default void clickTopBarRightImageButton(View view){}
}
