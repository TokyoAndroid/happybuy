package com.zw.happybuy.http;

import com.orhanobut.logger.Logger;

import rx.functions.Func1;

/**
 * Created by Tok on 2017/8/24.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if(httpResult.getRet() != 10000){
            try {
                throw new ApiException(httpResult.getRet());
            } catch (ApiException e) {
                e.printStackTrace();
                Logger.d(e);
            }
        }
        return httpResult.getResult();
    }

}
