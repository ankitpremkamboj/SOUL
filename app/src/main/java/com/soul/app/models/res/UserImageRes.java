package com.soul.app.models.res;

/**
 * Created by ashishkumar on 6/9/16.
 */
public class UserImageRes {
    /**
     * result : true
     * msg : Image Uploaded
     * data : {"profilePicUrl":"http://52.25.82.251/salaam-swipe-phase2/assets/profileimages/2016-09-06oeb13502947_10157067057015704_8466629845832043482_o.jpg"}
     */

    private boolean result;
    private String msg;
    /**
     * profilePicUrl : http://52.25.82.251/salaam-swipe-phase2/assets/profileimages/2016-09-06oeb13502947_10157067057015704_8466629845832043482_o.jpg
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
        private String profilePicUrl;

        public String getProfilePicUrl() {
            return profilePicUrl;
        }

        public void setProfilePicUrl(String profilePicUrl) {
            this.profilePicUrl = profilePicUrl;
        }
    }
}
