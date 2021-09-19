package com.birdwind.inspire.medical.diary.base.view;

import com.birdwind.inspire.medical.diary.R;
import com.birdwind.inspire.medical.diary.presenter.AbstractPresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.ToolbarCallback;

import androidx.annotation.DrawableRes;
import androidx.viewbinding.ViewBinding;

public interface BaseFragment<P extends AbstractPresenter, VB extends ViewBinding> extends BaseMVPView<P, VB> {

    /**
     * 設定activity topbar title
     * 
     * @return 標題
     */
    default String setTopBarTitle() {
        return null;
    }

    /**
     * 設定activity topbar title color
     * 
     * @return (int)drawable id
     */
    default int setTopBarTitleColor() {
        return R.color.colorWhite_FFFFFF;
    }

    /**
     * 設定activity topbar background color
     * 
     * @return (int)drawable id
     */
    default int setTopBarBackgroundColor() {
        return -1;
    }

    /**
     * 設定activity 明亮狀態列
     * 
     * @return true 明亮, false 黑暗
     */
    default boolean isStatusLightMode() {
        return true;
    }

    /**
     * 顯示topbar 退回鍵
     * 
     * @return true Visible, false InVisible
     */
    default boolean isShowTopBarBack() {
        return true;
    }

    /**
     * 顯示topbar
     * 
     * @return true Visible, false GONE
     */
    default boolean isShowTopBar() {
        return true;
    }

    /**
     * 是否需要firebase追蹤
     * 
     * @return true 追蹤, false 不追羧
     */
    default boolean isNeedFirebaseAnalyticViewTracker() {
        return true;
    }

    /**
     * 顯示右邊按鍵
     *
     * @return true Visible, false GONE
     */
    default boolean isShowRightButton() {
        return false;
    }

    /**
     * 設定右邊按鍵文字
     *
     * @return 按鍵文字
     */
    default String setRightButtonText() {
        return "";
    }

    /**
     * 設定右邊圖片按鍵的圖片
     *
     * @return 按鍵圖片
     */
    default @DrawableRes int setRightImageButton() {
        return 0;
    }

    default ToolbarCallback setToolbarCallback() {
        return null;
    }
}
