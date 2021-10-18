package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;
import java.util.Date;
import java.util.List;

public class ScoreDetailResponse extends AbstractResponse<ScoreDetailResponse.Response> {
    public class Response implements BaseResponse {
        private int TID;

        private int PID;

        private int Disease;

        private Date TimeC;

        private int Score;

        private List<Score> Items;

        public int getTID() {
            return TID;
        }

        public void setTID(int TID) {
            this.TID = TID;
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

        public Date getTimeC() {
            return TimeC;
        }

        public void setTimeC(Date timeC) {
            TimeC = timeC;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int score) {
            Score = score;
        }

        public List<ScoreDetailResponse.Score> getItems() {
            return Items;
        }

        public void setItems(List<ScoreDetailResponse.Score> items) {
            Items = items;
        }
    }

    public class Score {
        private int No;

        private int Score;

        public int getNo() {
            return No;
        }

        public void setNo(int no) {
            No = no;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int score) {
            Score = score;
        }
    }
}
