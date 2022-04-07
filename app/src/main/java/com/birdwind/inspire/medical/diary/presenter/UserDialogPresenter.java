package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.enums.DiseaseEnums;
import com.birdwind.inspire.medical.diary.model.response.AddUserResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.birdwind.inspire.medical.diary.server.DoctorApiServer;
import com.birdwind.inspire.medical.diary.server.FamilyApiServer;
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
                api = DoctorApiServer.ADD_PATIENT.valueOfName();
                paramsMap.put("Disease", diseaseEnums.getType());
                break;
            case FAMILY:
                api = FamilyApiServer.BE_FAMILY.valueOfName();
                break;
            // case PAINTER:
            // api = PatientApiServer.JOIN_MY_GROUP.valueOfName();
            // break;
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

    public void getSurvey(int uid){
        initMap();
        paramsMap.put("PID", uid);

        addDisposable(apiServer.executeGet(DoctorApiServer.GET_SURVEY.valueOfName(), paramsMap, headerMap),
                new AbstractObserver<SurveyListResponse>(this, baseView, "GetSurvey", null, SurveyListResponse.class, true) {
                    @Override
                    public void onSuccess(SurveyListResponse response) {
                        baseView.onGetSurvey(true, response.getJsonData());
                    }
                });
    }

    public void setSurvey(int uid, int surveyID){
        initMap();
        paramsMap.put("PID", uid);
        paramsMap.put("SurveyID", surveyID);

        addDisposable(apiServer.executeGet(DoctorApiServer.GET_SURVEY.valueOfName(), paramsMap, headerMap),
                new AbstractObserver<AbstractResponse>(this, baseView, "GetSurvey", null, AbstractResponse.class, true) {
                    @Override
                    public void onSuccess(AbstractResponse response) {
                        baseView.onSetSurvey(true, response.getMessage());
                    }
                });
    }
}
