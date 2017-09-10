package com.zw.happybuy.utils.rxbus.event;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;

/**
 * Created by Tok on 2017/9/2.
 */

public class RxBusLocationEvent {

    private BDLocation location;

    public BDLocation getLocation(){
        return location;
    }

    public void setLocation(BDLocation location) {
        this.location = location;
    }

    public RxBusLocationEvent(BDLocation location) {

        this.location = location;
    }
}
