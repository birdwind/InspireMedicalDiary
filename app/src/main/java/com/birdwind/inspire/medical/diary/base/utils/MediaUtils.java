package com.birdwind.inspire.medical.diary.base.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaUtils {
    private MediaPlayer mediaPlayer;

    public MediaUtils() {
        mediaPlayer = getInstance();
    }

    private MediaPlayer getInstance() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public void playAudioByUrl(String url) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    public void play(){
        mediaPlayer.start();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }
    public void reset(){
        mediaPlayer.reset();
    }
    public void release(){
        mediaPlayer.release();
    }
}
