package com.soul.app.models.res;

import java.io.Serializable;
import java.util.List;

/**
 * Created by techahead on 21/7/16.
 */
public class UserProfileRes implements Serializable {

    /**
     * user_id : 14335
     * facebook_id : 1228656753835967
     * user_name : Mayank Singh
     * age : 1926
     * device_token : abc
     * profile_pic : http://graph.facebook.com/1228656753835967/picture?width=800&height=800
     * user_image1 :
     * user_image2 :
     * user_image3 :
     * user_image4 :
     * about_text :
     * email : mayanksingh26@ymail.com
     * latitude : 28.5354983
     * longitude : 28.5354983
     * status : Liberal
     * denomination : Sunni
     * location :
     * location_status : 1
     * hometown :
     * hometown_status : 1
     * work :
     * work_status : 1
     * education :
     * education_status : 1
     * gender : male
     * cogonito_status : 0
     * isCatSelected : 1
     * active_status : 0
     * lastActivity_time : 1468931566
     * created_on : 2016-07-15 11:21:42
     * is_profilePic_changed : 0
     */

    private ProfileDetailsBean profileDetails;
    /**
     * category_id : 117
     * category_name : Ghazal
     * category_image : http://52.25.82.251/salaam-swipe/assets/category_image/1435034574_64_Ghazal.png
     * is_primary : 0
     */

    private List<InterestsBean> interests;

    public ProfileDetailsBean getProfileDetails() {
        return profileDetails;
    }

    public void setProfileDetails(ProfileDetailsBean profileDetails) {
        this.profileDetails = profileDetails;
    }

    public List<InterestsBean> getInterests() {
        return interests;
    }

    public void setInterests(List<InterestsBean> interests) {
        this.interests = interests;
    }

    public static class ProfileDetailsBean implements Serializable {
        private String user_id;
        private String facebook_id;
        private String user_name;
        private String age;
        private String device_token;
        private String profile_pic;
        private String user_image1;
        private String user_image2;
        private String user_image3;
        private String user_image4;
        private String about_text;
        private String email;
        private String latitude;
        private String longitude;
        private String status;
        private String denomination;
        private String location;
        private String location_status;
        private String hometown;
        private String hometown_status;
        private String work;
        private String work_status;
        private String education;
        private String education_status;
        private String gender;
        private String cogonito_status;
        private String isCatSelected;
        private String active_status;
        private String lastActivity_time;
        private String created_on;
        private String is_profilePic_changed;
        private String instagram;
        private String height;
        private String academy;



        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(String facebook_id) {
            this.facebook_id = facebook_id;
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

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

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

        public String getAbout_text() {
            return about_text;
        }

        public void setAbout_text(String about_text) {
            this.about_text = about_text;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDenomination() {
            return denomination;
        }

        public void setDenomination(String denomination) {
            this.denomination = denomination;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLocation_status() {
            return location_status;
        }

        public void setLocation_status(String location_status) {
            this.location_status = location_status;
        }

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getHometown_status() {
            return hometown_status;
        }

        public void setHometown_status(String hometown_status) {
            this.hometown_status = hometown_status;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public String getWork_status() {
            return work_status;
        }

        public void setWork_status(String work_status) {
            this.work_status = work_status;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getEducation_status() {
            return education_status;
        }

        public void setEducation_status(String education_status) {
            this.education_status = education_status;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCogonito_status() {
            return cogonito_status;
        }

        public void setCogonito_status(String cogonito_status) {
            this.cogonito_status = cogonito_status;
        }

        public String getIsCatSelected() {
            return isCatSelected;
        }

        public void setIsCatSelected(String isCatSelected) {
            this.isCatSelected = isCatSelected;
        }

        public String getActive_status() {
            return active_status;
        }

        public void setActive_status(String active_status) {
            this.active_status = active_status;
        }

        public String getLastActivity_time() {
            return lastActivity_time;
        }

        public void setLastActivity_time(String lastActivity_time) {
            this.lastActivity_time = lastActivity_time;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getIs_profilePic_changed() {
            return is_profilePic_changed;
        }

        public void setIs_profilePic_changed(String is_profilePic_changed) {
            this.is_profilePic_changed = is_profilePic_changed;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getAcademy() {
            return academy;
        }

        public void setAcademy(String academy) {
            this.academy = academy;
        }
    }

    public static class InterestsBean implements Serializable {
        private String category_id;
        private String category_name;
        private String category_image;
        private String is_primary;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_image() {
            return category_image;
        }

        public void setCategory_image(String category_image) {
            this.category_image = category_image;
        }

        public String getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(String is_primary) {
            this.is_primary = is_primary;
        }


    }
}
