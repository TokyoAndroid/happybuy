package com.zw.happybuy.http;

/**
 * Created by Tok on 2017/8/24.
 *
 * 模拟的数据为
 * {
 *  "ret":10000
 *  "msg":"msg"
 *  "token":""
 *  results:{
 *
 *  }
 * }
 * 的样子，因此T是results，根据实际需要更改数据结构
 *
 */

public class HttpResult<T> {

    private int ret;
    private String msg;
    private String token;

    private T result;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
