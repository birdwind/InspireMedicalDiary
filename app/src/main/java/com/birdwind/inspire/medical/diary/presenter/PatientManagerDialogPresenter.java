package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.response.InformationResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyListResponse;
import com.birdwind.inspire.medical.diary.server.MyApiServer;
import com.birdwind.inspire.medical.diary.server.SurveyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.PatientManagerView;

public class PatientManagerDialogPresenter extends AbstractPresenter<PatientManagerView> {
    public PatientManagerDialogPresenter(PatientManagerView baseView) {
        super(baseView);
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

    public void setSurvey(long uid, int surveyID, int identity) {
        initMap();
        paramsMap.put("UID", uid);
        paramsMap.put("SurveyID", surveyID);
        paramsMap.put("RespondentType", identity + 1);

        addDisposable(apiServer.executePostFormUrlEncode(SurveyApiServer.SET_SURVEY_FOR_USER.valueOfName(), paramsMap,
            fieldMap, headerMap),
            new AbstractObserver<Response>(this, baseView, "GetSurvey", null, Response.class, true) {
                @Override
                public void onSuccess(Response response) {
                    baseView.onSetSurvey(true, response.getMessage(), identity);
                }
            });
    }

    public void getInformation(long uid, IdentityEnums identityEnums) {
        initMap();
        paramsMap.put("UID", uid);
        paramsMap.put("RespondentType", identityEnums.getType() + 1);
        addDisposable(apiServer.executeGet(MyApiServer.GET_INFORMATION.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<InformationResponse>(this, baseView, "GetInformation", null, InformationResponse.class,
                true) {
                @Override
                public void onSuccess(InformationResponse response) {
                    baseView.onGetInformation(true, response.getJsonData());
                }
            });
    }
}
