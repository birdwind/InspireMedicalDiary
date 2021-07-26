package com.birdwind.inspire.medical.diary.view.customer.calendar;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

public class CustomMonthView extends MonthView {

    private Paint dotPaint;

    private Paint dotBorderPaint;

    private int mPaddingBottom;

    private float mRadius;

    private Context context;

    private int circleTextX;

    private int circleTextY;

    private int tagDotY;

    public CustomMonthView(Context context) {
        super(context);
        this.context = context;
        dotPaint = new Paint();
        dotBorderPaint = new Paint();
        dotPaint.setAntiAlias(true);
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setTextAlign(Paint.Align.CENTER);
        dotPaint.setFakeBoldText(true);
        dotBorderPaint.setAntiAlias(true);
        dotBorderPaint.setStyle(Paint.Style.STROKE);
        dotBorderPaint.setTextAlign(Paint.Align.CENTER);
        dotBorderPaint.setColor(ContextCompat.getColor(context, R.color.colorGray_A6A6A6));
        dotBorderPaint.setFakeBoldText(true);
        mPaddingBottom = Utils.dp2px(getContext(), 4);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = (float) (Math.min(mItemWidth, mItemHeight) / 5 * 1.5);
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {
        super.onLoopStart(x, y);
        circleTextX = x + mItemWidth / 2;
        circleTextY = y + mItemHeight / 2 - mPaddingBottom;
        tagDotY = y + mItemHeight - mPaddingBottom*2;
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        mSelectedPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(circleTextX, circleTextY, mRadius, mSelectedPaint);
        if (hasScheme) {
            onDrawScheme(canvas, calendar, x, y);
        }
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
        if (calendar.getScheme().equals("0")) {
            canvas.drawCircle(circleTextX, tagDotY, 8, dotBorderPaint);
        } else {
            dotPaint.setColor(calendar.getSchemeColor());
            canvas.drawCircle(circleTextX, tagDotY, 8, dotPaint);
        }
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y - mPaddingBottom;

        Paint paintBorder = new Paint();
        paintBorder.setColor(ContextCompat.getColor(getContext(), R.color.colorGray_C4C4C4));
        paintBorder.setStrokeWidth(1.5f);
        paintBorder.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, y + mItemHeight - 1, x + mItemWidth, y + mItemHeight, paintBorder);

        if (calendar.isCurrentMonth()) {
            if (calendar.isCurrentDay()) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(1);
                paint.setColor(ContextCompat.getColor(context, R.color.colorGray_4D4D4D));
                canvas.drawCircle(circleTextX, circleTextY, mRadius, paint);
                canvas.drawText(String.valueOf(calendar.getDay()), circleTextX, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint
                        : calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
            } else if (isSelected) {
                canvas.drawText(String.valueOf(calendar.getDay()), circleTextX, baselineY, mSelectTextPaint);
            } else if (hasScheme) {
                canvas.drawText(String.valueOf(calendar.getDay()), circleTextX, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint
                        : calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
            } else {
                canvas.drawText(String.valueOf(calendar.getDay()), circleTextX, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint
                        : calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
            }
        }
    }
}
