package com.soul.app.models.res;

import java.util.List;

/**
 * Created by ashishkumar on 25/8/16.
 */
public class GetFilterRes {

    /**
     * result : true
     * msg : Profile data.
     * data : [{"user_id":"14561","is_liberal":"1","is_moderate":"1","is_conservative":"1","is_nonpracticing":"1","is_notimportant":"1","is_sunni":"1","is_shiaismaili":"1","is_justmuslim":"1","is_convert":"1"}]
     */

    private boolean result;
    private String msg;
    /**
     * user_id : 14561
     * is_liberal : 1
     * is_moderate : 1
     * is_conservative : 1
     * is_nonpracticing : 1
     * is_notimportant : 1
     * is_sunni : 1
     * is_shiaismaili : 1
     * is_justmuslim : 1
     * is_convert : 1
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String user_id;
        private String is_liberal;
        private String is_moderate;
        private String is_conservative;
        private String is_nonpracticing;
        private String is_notimportant;
        private String is_sunni;
        private String is_shiaismaili;
        private String is_justmuslim;
        private String is_convert;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getIs_liberal() {
            return is_liberal;
        }

        public void setIs_liberal(String is_liberal) {
            this.is_liberal = is_liberal;
        }

        public String getIs_moderate() {
            return is_moderate;
        }

        public void setIs_moderate(String is_moderate) {
            this.is_moderate = is_moderate;
        }

        public String getIs_conservative() {
            return is_conservative;
        }

        public void setIs_conservative(String is_conservative) {
            this.is_conservative = is_conservative;
        }

        public String getIs_nonpracticing() {
            return is_nonpracticing;
        }

        public void setIs_nonpracticing(String is_nonpracticing) {
            this.is_nonpracticing = is_nonpracticing;
        }

        public String getIs_notimportant() {
            return is_notimportant;
        }

        public void setIs_notimportant(String is_notimportant) {
            this.is_notimportant = is_notimportant;
        }

        public String getIs_sunni() {
            return is_sunni;
        }

        public void setIs_sunni(String is_sunni) {
            this.is_sunni = is_sunni;
        }

        public String getIs_shiaismaili() {
            return is_shiaismaili;
        }

        public void setIs_shiaismaili(String is_shiaismaili) {
            this.is_shiaismaili = is_shiaismaili;
        }

        public String getIs_justmuslim() {
            return is_justmuslim;
        }

        public void setIs_justmuslim(String is_justmuslim) {
            this.is_justmuslim = is_justmuslim;
        }

        public String getIs_convert() {
            return is_convert;
        }

        public void setIs_convert(String is_convert) {
            this.is_convert = is_convert;
        }
    }
}
