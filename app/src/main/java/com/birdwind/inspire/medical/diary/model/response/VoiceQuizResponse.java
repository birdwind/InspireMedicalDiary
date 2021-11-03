package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import java.util.Date;

public class VoiceQuizResponse extends AbstractResponse<VoiceQuizResponse.Response> {
    public class Response implements BaseResponse {
        private int VTID;

        private int QustID;

        private int PID;

        private int Disease;

        private String VoiceFile;

        private Date TimeC;

        private String Content;

        private String PhotoUrl;

        public int getVTID() {
            return VTID;
        }

        public void setVTID(int VTID) {
            this.VTID = VTID;
        }

        public int getQustID() {
            return QustID;
        }

        public void setQustID(int qustID) {
            QustID = qustID;
        }

        public int getPID() {
            return PID;
        }

        public void setPID(int PID) {
            this.PID = PID;
        }

        public int getDisease() {
            return Disease;
        }

        public void setDisease(int disease) {
            Disease = disease;
        }

        public String getVoiceFile() {
            return VoiceFile;
        }

        public void setVoiceFile(String voiceFile) {
            VoiceFile = voiceFile;
        }

        public Date getTimeC() {
            return TimeC;
        }

        public void setTimeC(Date timeC) {
            TimeC = timeC;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            PhotoUrl = photoUrl;
        }
    }
}
