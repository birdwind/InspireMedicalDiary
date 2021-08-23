package com.birdwind.inspire.medical.diary.model.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

public class ChatResponse extends AbstractListResponse<ChatResponse.Response> {

    public final static int SELF_MESSAGE = 0;

    public final static int OTHER_MESSAGE = 1;

    @Entity(tableName = "chat")
    public static class Response implements BaseResponse, MultiItemEntity, BaseModel {
        @PrimaryKey
        @NonNull
        private long ID;

        private int PID;

        private int FromUID;

        private String Content;

        private int Identity;

        private String FromName;

        private String PhotoUrl;

        private boolean Self;

        private Date TimeC;

        public long getID() {
            return ID;
        }

        public void setID(long ID) {
            this.ID = ID;
        }

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public int getFromUID() {
            return FromUID;
        }

        public void setFromUID(int fromUID) {
            FromUID = fromUID;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public int getIdentity() {
            return Identity;
        }

        public void setIdentity(int identity) {
            Identity = identity;
        }

        public String getFromName() {
            return FromName;
        }

        public void setFromName(String fromName) {
            FromName = fromName;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }

        public boolean isSelf() {
            return Self;
        }

        public void setSelf(boolean self) {
            Self = self;
        }

        public Date getTimeC() {
            return TimeC;
        }

        public void setTimeC(Date timeC) {
            TimeC = timeC;
        }

        @Override
        public int getItemType() {
            if (Self) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
