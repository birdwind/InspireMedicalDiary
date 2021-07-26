package com.birdwind.inspire.medical.diary.utils;

import java.util.Date;
import android.os.CountDownTimer;

public class CountDownUtils {

    private static CountDownTimer countDownTimer;

    private CountDownUtilsCallback countDownUtilsCallback;

    private long startTimeMill;

    private long endTimeMill;

    private int countTimeMill;

    private boolean isCountDown;

    public CountDownUtils(int sec, CountDownUtilsCallback countDownUtilsCallback) {
        this.countDownUtilsCallback = countDownUtilsCallback;
        isCountDown = false;
        init(sec);
    }

    private void init(int sec) {
        countTimeMill = sec * 1000;
        countDownTimer = new CountDownTimer(sec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownUtilsCallback.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                isCountDown = false;
                countDownUtilsCallback.onFinish();
            }
        };
    }

    public void start() {
        if (countDownTimer != null) {
            countDownTimer.start();
            isCountDown = true;
            Date date = new Date();
            startTimeMill = date.getTime();
            endTimeMill = startTimeMill + countTimeMill;
        }
    }

    public void restart() {
        if (countDownTimer != null && isCountDown) {
            Date date = new Date();
            long now = date.getTime();
//            countDownTimer.onTick(endTimeMill - now);
            countDownTimer = new CountDownTimer(endTimeMill - now, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    countDownUtilsCallback.onTick(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    isCountDown = false;
                    countDownUtilsCallback.onFinish();
                }
            };
            countDownTimer.start();
        }
    }

    public void finish() {
        if (countDownTimer != null) {
            countDownTimer.onFinish();
        }
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void delete() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
            countDownTimer = null;
        }
    }

    public boolean isCountDown() {
        return isCountDown;
    }

    public interface CountDownUtilsCallback {
        void onTick(long millisUntilFinished);

        void onFinish();
    }
}
