package com.birdwind.inspire.medical.diary.view.viewCallback;

import com.birdwind.inspire.medical.diary.model.response.PatientResponse;

import java.util.List;

public interface DoctorPatientPresent extends BaseCustomView {
    void onGetFriends(List<PatientResponse.Response> friendResponse);
}
