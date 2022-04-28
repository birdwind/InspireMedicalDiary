package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.model.response.AddUserResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.birdwind.inspire.medical.diary.server.PatientApiServer;
import com.birdwind.inspire.medical.diary.server.SurveyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.UserDialogView;

public class UserDialogPresenter extends AbstractPresenter<UserDialogView> {
    public UserDialogPresenter(UserDialogView baseView) {
        super(baseView);
    }

    public void addUser(int uid, DiseaseEnums diseaseEnums) {
        String api = "";

        initMap();

        switch (App.userModel.getIdentityEnums()) {
            case DOCTOR:
                api = PatientApiServer.ADD_PATIENT.valueOfName();
                paramsMap.put("Disease", diseaseEnums.getType());
                break;
            case FAMILY:
                api = PatientApiServer.BE_FAMILY.valueOfName();
                break;
        }
        paramsMap.put("UID", uid);

        addDisposable(apiServer.executePostFormUrlEncode(api, paramsMap, fieldMap, headerMap),
            new AbstractObserver<AddUserResponse>(this, baseView, "AddUser", null, AddUserResponse.class, true) {
                @Override
                public void onSuccess(AddUserResponse response) {
                    baseView.onAddUser(true, response.getJsonData());
                }
            });
    }

    public void getSurvey(int disease, int identity) {
        initMap();
        paramsMap.put("ConditionID", disease);
        paramsMap.put("RespondentType", identity + 1);

        addDisposable(apiServer.executeGet(SurveyApiServer.GET_SURVEY.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<SurveyListResponse>(this, baseView, "GetSurvey", null, SurveyListResponse.class,
                true) {
                @Override
                public void onSuccess(SurveyListResponse response) {
                    baseView.onGetSurvey(true, response.getJsonData(), identity);
                }
            });
    }

    public void setSurvey(int uid, int surveyID, int identity) {
        initMap();
        paramsMap.put("UID", uid);
        paramsMap.put("SurveyID", surveyID);
        paramsMap.put("RespondentType", identity + 1);

        addDisposable(apiServer.executePostFormUrlEncode(SurveyApiServer.SET_SURVEY_FOR_USER.valueOfName(), paramsMap, fieldMap, headerMap),
            new AbstractObserver<Response>(this, baseView, "SetSurvey", null, Response.class, true) {
                @Override
                public void onSuccess(Response response) {
                     baseView.onSetSurvey(true, response.getMessage(), identity);
                }
            });
    }
}
