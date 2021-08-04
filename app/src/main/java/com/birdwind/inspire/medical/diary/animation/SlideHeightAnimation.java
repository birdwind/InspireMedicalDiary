package com.birdwind.inspire.medical.diary.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SlideHeightAnimation extends Animation {

    private View view;

    private int fromHeight;

    private int toHeight;

    public SlideHeightAnimation(View view, int fromHeight, int toHeight) {
        this.view = view;
        this.fromHeight = fromHeight;
        this.toHeight = toHeight;
    }

    public SlideHeightAnimation(View view, int fromHeight, int toHeight, int duration) {
        this.view = view;
        this.fromHeight = fromHeight;
        this.toHeight = toHeight;
        setDuration(duration);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;

        if (view.getHeight() != toHeight) {
            newHeight = (int) (fromHeight + ((toHeight - fromHeight) * interpolatedTime));
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getFromHeight() {
        return fromHeight;
    }

    public void setFromHeight(int fromHeight) {
        this.fromHeight = fromHeight;
    }

    public int getToHeight() {
        return toHeight;
    }

    public void setToHeight(int toHeight) {
        this.toHeight = toHeight;
    }
}
