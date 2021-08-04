package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.utils.SharedPreferencesUtils;
import com.birdwind.inspire.medical.diary.model.UserModel;
import com.birdwind.inspire.medical.diary.model.request.LoginRequest;
import com.birdwind.inspire.medical.diary.model.response.LoginResponse;
import com.birdwind.inspire.medical.diary.server.LoginApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.LoginView;

public class LoginPresenter extends AbstractPresenter<LoginView> {
    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }

    public void login(LoginRequest loginRequest) {
        initMap();

        paramsMap = parseObjectToHashMap(loginRequest);
        addDisposable(
            apiServer.executePostFormUrlEncode(LoginApiServer.LOGIN.valueOfName(), paramsMap, fieldMap, headerMap),
            new AbstractObserver<LoginResponse>(this, baseView, "Login", null, LoginResponse.class, true) {
                @Override
                public void onSuccess(LoginResponse response) {
                    if (response.getMessage() == null) {
                        UserModel userModel = new UserModel();
                        userModel.setToken(response.getJsonData().getLoginKey());
                        App.userModel = userModel;
                        App.updateUserModel();
                        baseView.onLoginSuccess();
                    } else {
                        switch (response.getMessage()) {
                            case "帳號為空":
                            case "密碼錯誤":
                                baseView.onLoginNeedVerify();
                                break;
                        }
                    }
                }
            });
    }
}
