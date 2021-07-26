package com.birdwind.inspire.medical.diary.view.customer.smartPopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

@IntDef({VerticalPosition.CENTER, VerticalPosition.ABOVE, VerticalPosition.BELOW, VerticalPosition.ALIGN_TOP,
    VerticalPosition.ALIGN_BOTTOM,})
@Retention(RetentionPolicy.SOURCE)
public @interface VerticalPosition {
    int CENTER = 0;

    int ABOVE = 1;

    int BELOW = 2;

    int ALIGN_TOP = 3;

    int ALIGN_BOTTOM = 4;
}
