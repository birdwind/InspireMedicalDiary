package com.birdwind.inspire.medical.diary.model.response;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.birdwind.inspire.medical.diary.base.model.BaseModel;
import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ChatMemberResponse extends AbstractListResponse<ChatMemberResponse.Response> {

    @Entity(tableName = "chat_member")
    public static class Response implements BaseResponse, MultiItemEntity, BaseModel {
        private int PID;

        @PrimaryKey
        @NonNull
        private long UID;

        private int Role;

        private String RoleName;

        private String UserName;

        private String PhotoUrl;

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public long getUID() {
            return UID;
        }

        public void setUID(long UID) {
            this.UID = UID;
        }

        public int getRole() {
            return Role;
        }

        public void setRole(int role) {
            Role = role;
        }

        public String getRoleName() {
            return RoleName;
        }

        public void setRoleName(String roleName) {
            RoleName = roleName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }

        @Override
        public int getItemType() {
            return 0;
        }
    }
}
