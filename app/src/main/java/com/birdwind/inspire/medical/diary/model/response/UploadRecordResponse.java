package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class UploadRecordResponse extends AbstractResponse<UploadRecordResponse.Response> {
    public class Response implements BaseResponse {
        private String FileName;

        private long FileSize;

        public String getFileName() {
            return FileName;
        }

        public void setFileName(String fileName) {
            FileName = fileName;
        }

        public long getFileSize() {
            return FileSize;
        }

        public void setFileSize(long fileSize) {
            FileSize = fileSize;
        }
    }
}
