package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.base.network.response.Response;
import com.birdwind.inspire.medical.diary.enums.IdentityEnums;
import com.birdwind.inspire.medical.diary.model.request.SurveyAnswerRequest;
import com.birdwind.inspire.medical.diary.model.response.QuestionnaireResponse;
import com.birdwind.inspire.medical.diary.model.response.SurveyResponse;
import com.birdwind.inspire.medical.diary.server.SurveyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.SurveyView;

public class SurveyPresenter extends AbstractPresenter<SurveyView> {
    public SurveyPresenter(SurveyView baseView) {
        super(baseView);
    }

    public void getSurvey(IdentityEnums identityEnums) {
        initMap();
        paramsMap.put("RespondentType", identityEnums.getType() + 1);
        addDisposable(apiServer.executeGet(SurveyApiServer.SURVEY_RESPONSE.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<SurveyResponse>(this, baseView, "GetSurvey", null, SurveyResponse.class, true) {
                @Override
                public void onSuccess(SurveyResponse response) {
                    baseView.onGetSurvey(true, response.getJsonData());
                }

                @Override
                public void onError(String title, String code, String msg, boolean isDialog) {
                    super.onError(title, code, msg, isDialog);
                    if(msg.equals("目前沒有問卷")){
                        baseView.onGetSurvey(false, null);
                    }
                }
            });
    }

    public void submit(SurveyAnswerRequest surveyAnswerRequest) {
        initMap();
        addDisposable(
            apiServer.executePost(SurveyApiServer.SURVEY_REQUEST.valueOfName(), paramsMap,
                packageToRequestBody(surveyAnswerRequest), headerMap),
            new AbstractObserver<Response>(this, baseView, "submitSurvey", null, Response.class, true) {
                @Override
                public void onSuccess(Response response) {
                    baseView.submitSuccess(true);
                }
            });
    }

    public void getQuestionnaire(int questionnaireID) {
        initMap();
        paramsMap.put("QuestionnaireID", questionnaireID);
        addDisposable(apiServer.executeGet(SurveyApiServer.QUESTIONNAIRE.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<QuestionnaireResponse>(this, baseView, "GetQuestionnaire", null,
                QuestionnaireResponse.class, true) {
                @Override
                public void onSuccess(QuestionnaireResponse response) {
                    baseView.onGetQuestionnaire(response.getJsonData());
                }
            });
    }
}
