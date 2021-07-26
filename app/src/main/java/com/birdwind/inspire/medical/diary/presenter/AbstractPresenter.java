package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractBasePresenter;
import com.birdwind.inspire.medical.diary.view.viewCallback.BaseCustomView;

public abstract class AbstractPresenter<V extends BaseCustomView> extends AbstractBasePresenter<V> {

    public AbstractPresenter(V baseView) {
        super(baseView);
    }

    /**
     * Example
     *
     * @param errorTitle 彈跳視窗標題
     * @param msgCode 伺服器訊息 Code
     */
    public void example() {
//        initMap();
//
//        addDisposable(
//            apiServer.executePostFormUrlEncode(MessageApiServer.SHOW.valueOfName(), paramsMap, fieldMap, headerMap),
//            new AbstractObserver<MsgShowResponse>(this, baseView, "MsgShow-" + msgCode, errorTitle,
//                MsgShowResponse.class, false) {
//                @Override
//                public void onSuccess(MsgShowResponse response) {
//                    if (baseView.onGetMsgConstant(errorTitle, response.getMessage(), response.getCode())) {
//                        baseView.showMessage(errorTitle, response.getJsonData().getMessage(), true, null);
//                    }
//                }
//            });
    }
}
