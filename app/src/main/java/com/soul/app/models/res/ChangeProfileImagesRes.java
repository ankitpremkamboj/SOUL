package com.soul.app.models.res;

/**
 * Created by ashishkumar on 28/7/16.
 */
public class ChangeProfileImagesRes {
    /**
     * result : true
     * msg : Update User Profile Image.
     * data : {"profile_pic":"http://103.25.130.197/salaam-swipe/assets/profileimages/2016-07-28cIScreenshot_2016-07-26-18-39-35.png","user_image1":"http://103.25.130.197/salaam-swipe/assets/profileimages/2016-07-28DmSIMG_20160728_115131646.jpg","user_image2":null,"user_image3":null,"user_image4":null}
     */

    private boolean result;
    private String msg;
    /**
     * profile_pic : http://103.25.130.197/salaam-swipe/assets/profileimages/2016-07-28cIScreenshot_2016-07-26-18-39-35.png
     * user_image1 : http://103.25.130.197/salaam-swipe/assets/profileimages/2016-07-28DmSIMG_20160728_115131646.jpg
     * user_image2 : null
     * user_image3 : null
     * user_image4 : null
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
        private String profile_pic;
        private String user_image1;
        private Object user_image2;
        private Object user_image3;
        private Object user_image4;

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getUser_image1() {
            return user_image1;
        }

        public void setUser_image1(String user_image1) {
            this.user_image1 = user_image1;
        }

        public Object getUser_image2() {
            return user_image2;
        }

        public void setUser_image2(Object user_image2) {
            this.user_image2 = user_image2;
        }

        public Object getUser_image3() {
            return user_image3;
        }

        public void setUser_image3(Object user_image3) {
            this.user_image3 = user_image3;
        }

        public Object getUser_image4() {
            return user_image4;
        }

        public void setUser_image4(Object user_image4) {
            this.user_image4 = user_image4;
        }
    }
}
