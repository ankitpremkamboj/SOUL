package com.soul.app.models.res;

/**
 * Created by ashishkumar on 25/7/16.
 */
public class EditProfileRes {
    /**
     * result : true
     * msg : Update User Profile.
     * data : {"instagram":"dfdf","is_profilePic_changed":"1","about_text":"test"}
     */

    private boolean result;
    private String msg;
    /**
     * instagram : dfdf
     * is_profilePic_changed : 1
     * about_text : test
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
        private String instagram;
        private String is_profilePic_changed;
        private String about_text;

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getIs_profilePic_changed() {
            return is_profilePic_changed;
        }

        public void setIs_profilePic_changed(String is_profilePic_changed) {
            this.is_profilePic_changed = is_profilePic_changed;
        }

        public String getAbout_text() {
            return about_text;
        }

        public void setAbout_text(String about_text) {
            this.about_text = about_text;
        }
    }
}
