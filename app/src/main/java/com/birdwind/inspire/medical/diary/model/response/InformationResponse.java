package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class InformationResponse extends AbstractResponse<InformationResponse.Response> {
    public static class Response implements BaseResponse {

        private Long UID;

        private String IDNumber;

        private Integer Sex;

        private String Name;

        private String Phone;

        public Response() {
            UID = 0L;
            IDNumber = "";
            Sex = 0;
            Name = "";
            Phone = "";
        }

        public Long getUID() {
            return UID;
        }

        public void setUID(Long UID) {
            this.UID = UID;
        }

        public String getIDNumber() {
            return IDNumber;
        }

        public void setIDNumber(String IDNumber) {
            this.IDNumber = IDNumber;
        }

        public Integer getSex() {
            return Sex;
        }

        public void setSex(Integer sex) {
            Sex = sex;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getSexString(){
            switch (Sex){
                case 1:
                    return "男";
                case 2:
                    return "女";
            }
            return "尚未設定性別";
        }
    }
}
