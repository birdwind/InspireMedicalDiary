package com.birdwind.inspire.medical.diary.base.network.request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File file;

    private String path;

    private UploadCallbacks uploadCallbacks;

    private String contentType;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public ProgressRequestBody(final File file, String contentType, final UploadCallbacks listener) {
        this.contentType = contentType;
        this.file = file;
        this.uploadCallbacks = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
        long fileLength = file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {

                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));

                uploaded += read;
                bufferedSink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;

        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            uploadCallbacks.onProgressUpdate((int) (100 * mUploaded / mTotal));
        }
    }

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError();

        void onFinish();
    }
}
