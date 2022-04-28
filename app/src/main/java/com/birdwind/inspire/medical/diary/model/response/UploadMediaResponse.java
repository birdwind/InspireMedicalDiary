package com.birdwind.inspire.medical.diary.model.response;

import com.birdwind.inspire.medical.diary.base.network.response.AbstractResponse;
import com.birdwind.inspire.medical.diary.base.network.response.BaseResponse;

public class UploadMediaResponse extends AbstractResponse<UploadMediaResponse.Response> {
    public class Response implements BaseResponse {
        private String FileName;

        private String MediaLink;

        private long FileSize;

        public String getMediaLink() {
            return MediaLink;
        }

        public void setMediaLink(String mediaLink) {
            MediaLink = mediaLink;
        }

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
