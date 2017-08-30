package com.zw.happybuy.http;

import android.util.Log;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ApiException extends IllegalAccessException {

    public static final String NETWORK_ERROR = "没有连接到网络!";
    public static final String SERVICE_ERROR = "暂时无法连接！";
    public static final String UNKNOWN_ERROR = "连接失败，请重试！";

    public static final int NETWORK_ERROR_CODE = 0x00001;
    public static final int SERVICE_ERROR_CODE = 0x00002;
    public static final int UNKNOWN_ERROR_CODE = 0x00003;

    public ApiException(String s) {
        super(s);
    }

    public ApiException(int resultCode){
        this(getApiExceptionMessage(resultCode));
    }

    private static String getApiExceptionMessage(int code){
        switch (code){
            case NETWORK_ERROR_CODE:
                return NETWORK_ERROR;
            case SERVICE_ERROR_CODE:
                return SERVICE_ERROR;
            case UNKNOWN_ERROR_CODE:
                return UNKNOWN_ERROR;
            default:
        }
        return UNKNOWN_ERROR;
    }


}
