package com.birdwind.inspire.medical.diary.model.response;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class PatientResponse extends AbstractListResponse<PatientResponse.Response> {

    @Entity(tableName = "friend")
    public static class Response implements BaseResponse, BaseModel {
        @PrimaryKey
        private int PID;

        private int Doctor;

        private String Name;

        private String Phone;

        @Nullable
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

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
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
    }
}
