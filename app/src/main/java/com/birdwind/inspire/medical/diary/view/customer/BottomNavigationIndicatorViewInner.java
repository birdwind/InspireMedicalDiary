package com.birdwind.inspire.medical.diary.view.customer;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.base.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewInner;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.core.content.ContextCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import kotlin.ranges.RangesKt;

public class BottomNavigationIndicatorViewInner extends BottomNavigationViewInner {

    private final float DEFAULT_SCALE = 1f;

    private final float MAX_SCALE = 15f;

    private final float BASE_DURATION = 300L;

    private final float VARIABLE_DURATION = 300L;

    private ValueAnimator animator = new ValueAnimator();

    private RectF indicator;

    private Paint paint;

    private float bottomOffset;

    private float defaultWidth;

    private float defaultHeight;

    public BottomNavigationIndicatorViewInner(Context context) {
        super(context);
        initIndicator();
    }

    public BottomNavigationIndicatorViewInner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initIndicator();
    }

    public BottomNavigationIndicatorViewInner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initIndicator();
    }

    private void initIndicator() {
        indicator = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bottomOffset = Utils.dp2px(getContext(), 0);
        defaultHeight = Utils.dp2px(getContext(), 5);
    }

    public void moveTabIndicator(int position, boolean isAnimate) {
        View itemView = getBottomNavigationItemView(position);
        defaultWidth = itemView.getMeasuredWidth();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlue_67BCFF));

        float fromScale = indicator.width() / defaultWidth;
        float indicatorStart = indicator.left;
        float indicatorEnd = defaultWidth * position;

        animator = ValueAnimator.ofFloat(fromScale, MAX_SCALE, DEFAULT_SCALE);
        animator.setDuration(3000);
        animator.addUpdateListener(animation -> {
            float progress = animation.getAnimatedFraction();
            float distanceTravelled = linearInterpolation(progress, indicatorStart, indicatorEnd);

            float scale = (float) animation.getAnimatedValue();
            float indicatorWidth = defaultWidth;
            float left = distanceTravelled;
            float top = getHeight() - defaultHeight;
            float right = distanceTravelled + indicatorWidth;
            float bottom = getHeight();
            indicator.set(left, top, right, bottom);
            invalidate();
        });
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        float distanceToMove = Math.abs(indicatorStart - indicatorEnd);
        long temp = calculateDuration(distanceToMove);
        animator.setDuration(isAnimate ? temp : 0L);
        animator.start();

    }

    private void cancelAnimator(boolean setEndValues) {
        if (setEndValues) {
            animator.end();
        } else {
            animator.cancel();
        }
        animator.removeAllUpdateListeners();
        animator = null;
    }

    /**
     * Linear interpolation between 'a' and 'b' based on the progress 't'
     */
    private float linearInterpolation(float t, float a, float b) {
        return (1 - t) * a + t * b;
    }

    private long calculateDuration(float distance) {
        return (long) (BASE_DURATION + VARIABLE_DURATION * RangesKt.coerceIn((distance / getWidth()), 0f, 1f));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isLaidOut()) {
            float cornerRadius = indicator.height() / 2;
            canvas.drawRect(indicator, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimator(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                moveTabIndicator(0, false);
//                getViewTreeObserver().removeOnPreDrawListener(this);
//                return true;
//            }
//        });
    }
}
