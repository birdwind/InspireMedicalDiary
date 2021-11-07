package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.UserModel;
import com.birdwind.inspire.medical.diary.model.response.LoginResponse;
import com.birdwind.inspire.medical.diary.server.LoginApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.LoginVerificationView;

public class LoginVerificationPresenter extends AbstractPresenter<LoginVerificationView> {
    public LoginVerificationPresenter(LoginVerificationView baseView) {
        super(baseView);
    }

    public void sendVerificationCode(String phone, String FCM) {
        initMap();

        paramsMap.put("phone", phone);
        paramsMap.put("FCM", FCM);

        addDisposable(
            apiServer.executePostFormUrlEncode(LoginApiServer.SEND_PHONE_VERIFICATION.valueOfName(), paramsMap,
                fieldMap, headerMap),
            new AbstractObserver<AbstractResponse>(this, baseView, "SendVerificationCode", null, AbstractResponse.class,
                true) {
                @Override
                public void onSuccess(AbstractResponse response) {
                    showMsg(response.getMessage(), true);
                    baseView.onSendVerify(true);
                }

                @Override
                public boolean onErrorHandler(String title, String code, String msg, boolean isDialog,
                    AbstractResponse response) {
                    baseView.onSendVerify(false);
                    return false;
                }
            });
    }

    public void verify(String phone, String pass, String name, String FCM, IdentityEnums identityEnums) {
        initMap();

        paramsMap.put("phone", phone);
        paramsMap.put("pass", pass);
        paramsMap.put("name", name);
        paramsMap.put("FCM", FCM);
        paramsMap.put("identity", identityEnums.getType());

        addDisposable(
            apiServer.executePostFormUrlEncode(LoginApiServer.CHECK_PHONE_VERIFY.valueOfName(), paramsMap, fieldMap,
                headerMap),
            new AbstractObserver<LoginResponse>(this, baseView, "Verify", null, LoginResponse.class, true) {
                @Override
                public void onSuccess(LoginResponse response) {
                    UserModel userModel = new UserModel();
                    userModel.setToken(response.getJsonData().getLoginKey());
                    userModel.setUid(response.getJsonData().getUID());
                    userModel.setName(response.getJsonData().getName());
                    userModel.setPhotoUrl(response.getJsonData().getPhotoUrl());
                    userModel.setHasFamily(response.getJsonData().isHasFamily());
                    userModel.setProxy(response.getJsonData().isProxy());
                    userModel.setDiseaseEnums(DiseaseEnums.parseEnumsByType(response.getJsonData().getDisease()));
                    userModel.setNeedPatineName(response.getJsonData().isNeedSetPatientName());
                    App.userModel = userModel;
                    App.updateUserModel();
                    baseView.onVerify(true);
                }

                @Override
                public boolean onErrorHandler(String title, String code, String msg, boolean isDialog,
                    LoginResponse response) {
                    if (msg.equals("已驗證")) {
                        UserModel userModel = new UserModel();
                        userModel.setToken(response.getJsonData().getLoginKey());
                        userModel.setUid(response.getJsonData().getUID());
                        userModel.setName(response.getJsonData().getName());
                        userModel.setPhotoUrl(response.getJsonData().getPhotoUrl());
                        userModel.setHasFamily(response.getJsonData().isHasFamily());
                        userModel.setProxy(response.getJsonData().isProxy());
                        userModel.setDiseaseEnums(DiseaseEnums.parseEnumsByType(response.getJsonData().getDisease()));
                        userModel.setNeedPatineName(response.getJsonData().isNeedSetPatientName());
                        App.userModel = userModel;
                        App.updateUserModel();
                        baseView.onVerify(false);
                    }
                    return false;
                }
            });
    }
}
