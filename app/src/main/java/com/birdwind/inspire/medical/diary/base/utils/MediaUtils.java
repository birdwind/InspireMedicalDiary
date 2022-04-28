package com.birdwind.inspire.medical.diary.base.utils;

import android.media.AudioAttributes;
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

    public void playAudioByUrl(String url, MediaPlayer.OnCompletionListener completionListener) {
        reset();
        mediaPlayer.setAudioAttributes(
            new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(MediaPlayer::start);
            mediaPlayer.setOnCompletionListener(completionListener);

        } catch (Exception e) {
            LogUtils.exception(e);
        }
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void reset() {
        mediaPlayer.reset();
    }

    public void release() {
        mediaPlayer.release();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void seekTo(int percent) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(mediaPlayer.getDuration() / 100 * percent);
        }
    }

    public long getCurrentPercent() {
        if (mediaPlayer.getCurrentPosition() != 0) {
            return (mediaPlayer.getCurrentPosition() * 100L / mediaPlayer.getDuration());
        }
        return 0;
    }
}
