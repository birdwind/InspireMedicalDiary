package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.ChartResponse;

import java.util.List;

public interface ChartView extends BaseCustomView {
    void onGetChart(boolean isSuccess, List<ChartResponse.Response> responses);
}
