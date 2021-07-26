package com.diary.init.view.customer.calendar;

import com.diary.init.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class CustomWeekBarView extends WeekBar {

    private int mPreSelectedIndex;

    public CustomWeekBarView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.english_week_bar, this, true);
        setBackgroundColor(Color.WHITE);
        // int padding = Utils.dp2px(context, 10);
        // setPadding(padding, 0, padding, 0);
    }

    @Override
    protected void onDateSelected(Calendar calendar, int weekStart, boolean isClick) {
        getChildAt(mPreSelectedIndex).setSelected(false);
        int viewIndex = getViewIndexByCalendar(calendar, weekStart);
        getChildAt(viewIndex).setSelected(true);
        mPreSelectedIndex = viewIndex;
    }

    /**
     * 当周起始发生变化，使用自定义布局需要重写这个方法，避免出问题
     *
     * @param weekStart 周起始
     */
    @Override
    protected void onWeekStartChange(int weekStart) {
        for (int i = 0; i < getChildCount(); i++) {
            ((TextView) getChildAt(i)).setText(getWeekString(i, weekStart));
        }
    }

    /**
     * 或者周文本，这个方法仅供父类使用
     *
     * @param index index
     * @param weekStart weekStart
     * @return 或者周文本
     */
    private String getWeekString(int index, int weekStart) {
        String[] weeks = getContext().getResources().getStringArray(R.array.week_string_array);

        if (weekStart == 1) {
            return weeks[index];
        }
        if (weekStart == 2) {
            return weeks[index == 6 ? 0 : index + 1];
        }
        return weeks[index == 0 ? 6 : index - 1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGray_C4C4C4));
        paint.setStrokeWidth(1.5f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, getBottom() - 1, getWidth(), getBottom(), paint);
    }
}
