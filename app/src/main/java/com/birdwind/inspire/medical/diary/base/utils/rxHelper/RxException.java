package com.diary.init.base.utils.rxHelper;

import com.diary.init.base.enums.ErrorCodeEnums;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import javax.net.ssl.SSLHandshakeException;
import androidx.annotation.Nullable;
import retrofit2.HttpException;

/**
 * 異常
 */
public class RxException extends Exception {

    private final int code;

    private String message;

    public RxException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
        this.message = throwable.getMessage();
    }

    public int getCode() {
        return this.code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public static RxException handleException(Throwable e) {
        RxException rxException;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            rxException = new RxException(httpException, httpException.code());

            switch (httpException.code()) {
                case 405:
                case 500:
                    rxException.message = ErrorCodeEnums.SERVER_ERROR.getMessage();
                    break;
                default:
                    rxException.message = String.valueOf(httpException.code());
                    break;
            }
            return rxException;
        } else if (e instanceof SocketTimeoutException) {
            rxException = new RxException(e, ErrorCodeEnums.SOCKET_TIMEOUT_ERROR.getCode());
            rxException.message = ErrorCodeEnums.SOCKET_TIMEOUT_ERROR.getMessage();
            return rxException;
        } else if (e instanceof ConnectException) {
            rxException = new RxException(e, ErrorCodeEnums.CONNECT_ERROR.getCode());
            rxException.message = ErrorCodeEnums.CONNECT_ERROR.getMessage();
            return rxException;
        } else if (e instanceof ConnectTimeoutException) {
            rxException = new RxException(e, ErrorCodeEnums.TIMEOUT_ERROR.getCode());
            rxException.message = ErrorCodeEnums.TIMEOUT_ERROR.getMessage();
            return rxException;
        } else if (e instanceof UnknownHostException) {
            rxException = new RxException(e, ErrorCodeEnums.UNKNOWN_HOST.getCode());
            rxException.message = ErrorCodeEnums.UNKNOWN_HOST.getMessage();
            return rxException;
        } else if (e instanceof NullPointerException) {
            rxException = new RxException(e, ErrorCodeEnums.NULL_POINT_EXCEPTION.getCode());
            rxException.message = ErrorCodeEnums.NULL_POINT_EXCEPTION.getMessage();
            return rxException;
        } else if (e instanceof SSLHandshakeException) {
            rxException = new RxException(e, ErrorCodeEnums.SSL_ERROR.getCode());
            rxException.message = ErrorCodeEnums.SSL_ERROR.getMessage();
            return rxException;
        } else if (e instanceof ClassCastException) {
            rxException = new RxException(e, ErrorCodeEnums.CAST_ERROR.getCode());
            rxException.message = ErrorCodeEnums.CAST_ERROR.getMessage();
            return rxException;
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof JsonSerializer
            || e instanceof NotSerializableException || e instanceof ParseException) {
            rxException = new RxException(e, ErrorCodeEnums.PARSE_ERROR.getCode());
            rxException.message = ErrorCodeEnums.PARSE_ERROR.getMessage();
            return rxException;
        } else if (e instanceof IllegalStateException) {
            rxException = new RxException(e, ErrorCodeEnums.ILLEGAL_STATE_ERROR.getCode());
            rxException.message = ErrorCodeEnums.ILLEGAL_STATE_ERROR.getMessage();
            return rxException;
        } else {
            rxException = new RxException(e, ErrorCodeEnums.UNKNOWN.getCode());
            rxException.message = ErrorCodeEnums.UNKNOWN.getMessage();
            return rxException;
        }

    }
}
