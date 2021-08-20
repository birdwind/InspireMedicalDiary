package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

public class ChatResponse extends AbstractListResponse<ChatResponse.Response> {

    public class Response implements BaseResponse, MultiItemEntity {
        private int id;

        private int pid;

        private int fromUID;

        private String content;

        private int identity;

        private String fromName;

        private String photoUrl;

        private Date timeC;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getFromUID() {
            return fromUID;
        }

        public void setFromUID(int fromUID) {
            this.fromUID = fromUID;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public Date getTimeC() {
            return timeC;
        }

        public void setTimeC(Date timeC) {
            this.timeC = timeC;
        }

        @Override
        public int getItemType() {
            return 0;
        }
    }
}
