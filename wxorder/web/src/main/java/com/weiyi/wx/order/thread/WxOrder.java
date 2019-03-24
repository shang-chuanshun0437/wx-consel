package com.weiyi.wx.order.thread;

import com.weiyi.wx.order.request.H5AddStoreOrderRequest;

public class WxOrder implements Runnable{

    private H5AddStoreOrderRequest request;

    public WxOrder(H5AddStoreOrderRequest request){
        this.request = request;
    }

    public void run(){

    }
}
