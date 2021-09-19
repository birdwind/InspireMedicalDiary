package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractListResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

import java.util.Date;

public class ChartResponse extends AbstractListResponse<ChartResponse.Response> {

    public static class Response implements BaseResponse {
        private long TID;

        private long PID;

        private int Disease;

        private Date TimeC;

        private float Score;

        public long getTID() {
            return TID;
        }

        public void setTID(long TID) {
            this.TID = TID;
        }

        public long getPID() {
            return PID;
        }

        public void setPID(long PID) {
            this.PID = PID;
        }

        public int getDisease() {
            return Disease;
        }

        public void setDisease(int disease) {
            Disease = disease;
        }

        public Date getTimeC() {
            return TimeC;
        }

        public void setTimeC(Date timeC) {
            TimeC = timeC;
        }

        public float getScore() {
            return Score;
        }

        public void setScore(float score) {
            Score = score;
        }
    }
}
