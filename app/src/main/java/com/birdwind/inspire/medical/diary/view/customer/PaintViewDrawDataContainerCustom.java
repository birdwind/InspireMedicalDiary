package com.birdwind.inspire.medical.diary.view.customer;

import zhanglei.com.paintview.PaintView;
import zhanglei.com.paintview.PaintViewDrawDataContainer;

public class PaintViewDrawDataContainerCustom extends PaintViewDrawDataContainer {
    public PaintViewDrawDataContainerCustom(PaintView paintView) {
        super(paintView);
    }

    @Override
    public void clearAndSetNull() {
        clear();
        mTempPath = null;
        mDrawPhotoList = null;
        mDrawShapeList = null;
        mMementoList = null;
        mDrawPathList = null;
        if (mPaintViewBg != null) {
            mPaintViewBg.bitmap.recycle();
            mPaintViewBg = null;
        }
    }
}
