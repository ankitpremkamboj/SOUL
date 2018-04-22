package com.soul.app.models.res;

import com.soul.app.models.res.GeneralResp;

import java.util.List;


public class ListResp<T> extends GeneralResp {
    List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
