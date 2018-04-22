package com.soul.app.models.res;

import java.io.Serializable;
import java.util.List;

/**
 * Created by techahead on 19/7/16.
 */
public class UserListRes implements Serializable {


    /**
     * result : true
     * msg : User List.
     * data : [{"latitude":"29.956386","longitude":"31.265134","is_profilePic_changed":"1","profile_pic":"http://52.25.82.251/salaam-swipe/assets/profileimages/2016-04-28WqHprofile_pic.png","facebook_id":"10154112702482052","user_id":"13008","user_name":"Angie","age":"37","work":"Kalimat Magazine","education":"University of Toronto","user_image1":"","user_image2":"","user_image3":"","user_image4":"","distance":"191.59931555758013","userImage_count":1,"mutual_count":0,"primary_category":"http://52.25.82.251/salaam-swipe/assets/category_image/1434979691_03_Artsy.png"}]
     */

    private boolean result;
    private String msg;
    /**
     * latitude : 29.956386
     * longitude : 31.265134
     * is_profilePic_changed : 1
     * profile_pic : http://52.25.82.251/salaam-swipe/assets/profileimages/2016-04-28WqHprofile_pic.png
     * facebook_id : 10154112702482052
     * user_id : 13008
     * user_name : Angie
     * age : 37
     * work : Kalimat Magazine
     * education : University of Toronto
     * user_image1 :
     * user_image2 :
     * user_image3 :
     * user_image4 :
     * distance : 191.59931555758013
     * userImage_count : 1
     * mutual_count : 0
     * primary_category : http://52.25.82.251/salaam-swipe/assets/category_image/1434979691_03_Artsy.png
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
        private String latitude;
        private String longitude;
        private String is_profilePic_changed;
        private String profile_pic;
        private String facebook_id;
        private String user_id;
        private String user_name;
        private String age;
        private String work;
        private String education;
        private String user_image1;
        private String user_image2;
        private String user_image3;
        private String user_image4;
        private String distance;
        private int userImage_count;
        private int mutual_count;
        private String primary_category;
        private int pos=0;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }



        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getIs_profilePic_changed() {
            return is_profilePic_changed;
        }

        public void setIs_profilePic_changed(String is_profilePic_changed) {
            this.is_profilePic_changed = is_profilePic_changed;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(String facebook_id) {
            this.facebook_id = facebook_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getUser_image1() {
            return user_image1;
        }

        public void setUser_image1(String user_image1) {
            this.user_image1 = user_image1;
        }

        public String getUser_image2() {
            return user_image2;
        }

        public void setUser_image2(String user_image2) {
            this.user_image2 = user_image2;
        }

        public String getUser_image3() {
            return user_image3;
        }

        public void setUser_image3(String user_image3) {
            this.user_image3 = user_image3;
        }

        public String getUser_image4() {
            return user_image4;
        }

        public void setUser_image4(String user_image4) {
            this.user_image4 = user_image4;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getUserImage_count() {
            return userImage_count;
        }

        public void setUserImage_count(int userImage_count) {
            this.userImage_count = userImage_count;
        }

        public int getMutual_count() {
            return mutual_count;
        }

        public void setMutual_count(int mutual_count) {
            this.mutual_count = mutual_count;
        }

        public String getPrimary_category() {
            return primary_category;
        }

        public void setPrimary_category(String primary_category) {
            this.primary_category = primary_category;
        }
    }
}
