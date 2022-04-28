package com.birdwind.inspire.medical.diary.presenter;

import com.birdwind.inspire.medical.diary.base.basic.AbstractObserver;
import com.birdwind.inspire.medical.diary.model.response.SurveyQuestionnaireListResponse;
import com.birdwind.inspire.medical.diary.server.SurveyApiServer;
import com.birdwind.inspire.medical.diary.view.viewCallback.SurveyListView;

public class SurveyListPresenter extends AbstractPresenter<SurveyListView> {
    public SurveyListPresenter(SurveyListView baseView) {
        super(baseView);
    }

    public void getSurveyQuestionnaire(int uid, int identity) {
        initMap();

        paramsMap.put("UID", uid);
        paramsMap.put("RespondentType", identity + 1);
        addDisposable(apiServer.executeGet(SurveyApiServer.QUESTIONNAIRE_LIST.valueOfName(), paramsMap, headerMap),
            new AbstractObserver<SurveyQuestionnaireListResponse>(this, baseView, "GetSurveyQuestionnaire", null,
                SurveyQuestionnaireListResponse.class, true) {
                @Override
                public void onSuccess(SurveyQuestionnaireListResponse response) {
                    baseView.getSurveyQuestionnaire(response.getJsonData());
                }
            });
    }
}
