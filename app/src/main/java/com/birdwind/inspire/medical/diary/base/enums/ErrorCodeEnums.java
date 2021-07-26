package com.birdwind.inspire.medical.diary.base.enums;

import com.birdwind.inspire.medical.diary.App;
import com.birdwind.inspire.medical.diary.R;
import java.io.Serializable;

public enum ErrorCodeEnums implements BaseEnums {


    /**
     * 錯誤代碼枚舉
     * <p>
     * UNKNOWN                  未知異常
     * TIMEOUT_ERROR            連接超時
     * NULL_POINT_EXCEPTION     空指針異常
     * SSL_ERROR                SSL異常
     * CAST_ERROR               Class轉換異常
     * PARSE_ERROR              解析異常
     * ILLEGAL_STATE_ERROR      非法數據異常
     */

    UNKNOWN(R.string.error_common_unknow, 1000),
    SOCKET_TIMEOUT_ERROR(R.string.error_common_timeout, 1001),
    NULL_POINT_EXCEPTION(R.string.error_common_null_point, 1002),
    SSL_ERROR(R.string.error_common_ssl, 1003),
    CAST_ERROR(R.string.error_common_cast, 1004),
    PARSE_ERROR(R.string.error_common_parse, 1005),
    ILLEGAL_STATE_ERROR(R.string.error_common_null_point, 1006),
    CONNECT_ERROR(R.string.error_common_connect, 1007),
    UNKNOWN_HOST(R.string.error_common_connect, 1008),
    TIMEOUT_ERROR(R.string.error_common_timeout, 1009),
    SERVER_ERROR(R.string.error_common_server, 1010),
    ;

    private int messageCode;

    private int value;

    ErrorCodeEnums(int messageCode, int value) {
        this.messageCode = messageCode;
        this.value = value;
    }

    @Override
    public Serializable valueOf() {
        return this.value;
    }

    @Override
    public String valueOfName() {
        return App.getAppResources().getString(this.messageCode);
    }

    public int getCode() {
        return (int) this.valueOf();
    }

    public String getMessage() {
        return this.valueOfName();
    }
}
