package com.birdwind.inspire.medical.diary.base.view.loadingDialog;

import com.birdwind.inspire.medical.diary.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;

public class LoadingBaseDialog extends Dialog {

    protected LoadingBaseDialog(Context context) {
        super(context, R.style.LoadingDialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    protected LoadingBaseDialog(Context context, int theme) {
        super(context, theme);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    protected int getMinimumSideOfScreen(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = windowManager.getCurrentWindowMetrics();
            return Math.min(windowMetrics.getBounds().width(), windowMetrics.getBounds().height());
        } else {
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return Math.min(size.x, size.y);
        }
        // Display display = windowManager.getDefaultDisplay();
        // if (Build.VERSION.SDK_INT >= 13) {
        // Point size = new Point();
        // display.getSize(size);
        // return Math.min(size.x, size.y);
        // } else {
        // return Math.min(display.getWidth(), display.getHeight());
        // }
    }
}
