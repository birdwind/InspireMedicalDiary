package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class AddUserResponse extends AbstractResponse<AddUserResponse.Response> {
    public class Response implements BaseResponse {
        private int Doctor;

        private int Disease;

        public int getDoctor() {
            return Doctor;
        }

        public void setDoctor(int doctor) {
            Doctor = doctor;
        }

        public int getDisease() {
            return Disease;
        }

        public void setDisease(int disease) {
            Disease = disease;
        }
    }
}
