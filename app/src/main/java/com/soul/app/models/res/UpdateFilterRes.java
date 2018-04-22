package com.soul.app.models.res;

/**
 * Created by ashishkumar on 25/8/16.
 */
public class UpdateFilterRes {
    /**
     * result : true
     * msg : Filter Update.
     * data : true
     */

    private boolean result;
    private String msg;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
