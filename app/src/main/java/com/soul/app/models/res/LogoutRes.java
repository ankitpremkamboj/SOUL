package com.soul.app.models.res;

/**
 * Created by techahead on 23/7/16.
 */
public class LogoutRes {

    /**
     * result : true
     * msg : logout.
     * data : {"user_id":"14401"}
     */

    private boolean result;
    private String msg;
    /**
     * user_id : 14401
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String user_id;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
