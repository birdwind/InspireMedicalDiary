package com.birdwind.inspire.medical.diary.base.utils.glide;

public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}