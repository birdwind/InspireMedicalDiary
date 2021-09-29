package com.birdwind.inspire.medical.diary.view.viewCallback;

import android.view.View;

public interface ToolbarCallback {

    default void clickTopBarRightTextButton(View view) {}

    default void clickTopBarRightImageButton(View view) {}

    default void clickTopBarLeftTextButton(View view) {}
}
