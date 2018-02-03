package com.example.xmfy.yzubookshop.net;

import com.example.xmfy.yzubookshop.model.FormedData;

/**
 * Created by xmfy on 2018/1/8.
 */
public interface AsyncResponse<T> {
    void onDataReceivedSuccess(FormedData<T> formedData);

    void onDataReceivedFailed();
}
