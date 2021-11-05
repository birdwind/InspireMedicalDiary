package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class CheckDiseaseResponse extends AbstractResponse<CheckDiseaseResponse.Response> {
    public class Response implements BaseResponse {
        private int PID;

        private int Doctor;

        private int Disease;

        private String Name;

        private String Phone;

        private String IDCard;

        private boolean HasUnreadMessage;

        private boolean HasUnreadReport;

        private String PhotoUrl;

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

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

        public String getIDCard() {
            return IDCard;
        }

        public void setIDCard(String IDCard) {
            this.IDCard = IDCard;
        }

        public boolean isHasUnreadMessage() {
            return HasUnreadMessage;
        }

        public void setHasUnreadMessage(boolean hasUnreadMessage) {
            HasUnreadMessage = hasUnreadMessage;
        }

        public boolean isHasUnreadReport() {
            return HasUnreadReport;
        }

        public void setHasUnreadReport(boolean hasUnreadReport) {
            HasUnreadReport = hasUnreadReport;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }
    }
}
