package com.soul.app.models.res;


import com.soul.app.models.res.GeneralResp;

public class ObjResp<T> extends GeneralResp {
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
