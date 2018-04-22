package com.soul.app.models.res;

import java.util.List;

/**
 * Created by ashishkumar on 28/7/16.
 */
public class DeleteImagesRes {

    /**
     * result : true
     * msg : Image Deleted.
     * data : []
     */

    private boolean result;
    private String msg;
    private List<?> data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
