package com.soul.app.models.res;

/**
 * Created by techahead on 23/7/16.
 */
public class CommonUserRes {

    /**
     * result : true
     * msg : settings Update.
     * data : false
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
