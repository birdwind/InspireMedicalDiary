package com.birdwind.inspire.medical.diary.view.customer.smartPopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@IntDef({HorizontalPosition.CENTER, HorizontalPosition.LEFT, HorizontalPosition.RIGHT, HorizontalPosition.ALIGN_LEFT,
    HorizontalPosition.ALIGN_RIGHT,})
@Retention(RetentionPolicy.SOURCE)
public @interface HorizontalPosition {
    int CENTER = 0;

    int LEFT = 1;

    int RIGHT = 2;

    int ALIGN_LEFT = 3;

    int ALIGN_RIGHT = 4;
}
