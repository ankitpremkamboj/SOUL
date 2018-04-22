package com.soul.app.models.res;

import java.io.Serializable;
import java.util.List;

/**
 * Created by techahead on 20/7/16.
 */
public class OtherProfileRes implements Serializable {

    /**
     * isCatSelected : 1
     * is_profilePic_changed : 1
     * facebook_id : 10208472160592704
     * user_name : Devi
     * age : 36
     * profile_pic : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/v/t1.0-1/p320x320/264634_2051853627115_6340332_n.jpg?oh=89371625ba769be40968675c06eece26&oe=57380BA6&__gda__=1460141983_b93be3d6f8232b1a50c0ba2409de7eb0
     * user_image1 : http://52.25.82.251/salaam-swipe/assets/profileimages/2016-01-18cWZuser_image1.png
     * user_image2 :
     * user_image3 :
     * user_image4 :
     * about_text : Simple person . Like to eat new foods
     * status : Liberal
     * denomination : Just Muslim
     * location : Jakarta, Indonesia
     * location_status : 1
     * hometown : Bandung, Indonesia
     * hometown_status : 1
     * work :
     * work_status : 1
     * education : Institut Teknologi Bandung
     * education_status : 0
     * gender : female
     * lastActivity_time : 1453126553
     * distance : 2727.96
     * latitude :
     * longitude :
     */

    private ProfileDetailsBean profileDetails;
    /**
     * category_id : 129
     * category_name : Professional
     * category_image : http://52.25.82.251/salaam-swipe/assets/category_image/1435034388_44_Professional.png
     */

    private List<InterestsBean> interests;
    /**
     * category_name : Professional
     * category_image : http://52.25.82.251/salaam-swipe/assets/category_image/1435034388_44_Professional.png
     */

    private List<CommonInterestsBean> Common_interests;

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

    public List<CommonInterestsBean> getCommon_interests() {
        return Common_interests;
    }

    public void setCommon_interests(List<CommonInterestsBean> Common_interests) {
        this.Common_interests = Common_interests;
    }

    public static class ProfileDetailsBean implements Serializable {
        private String isCatSelected;
        private String is_profilePic_changed;
        private String facebook_id;
        private String user_name;
        private String age;
        private String profile_pic;
        private String user_image1;
        private String user_image2;
        private String user_image3;
        private String user_image4;
        private String about_text;
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
        private String lastActivity_time;
        private double distance;
        private String latitude;
        private String longitude;
        private String height;

        public String getInstagram() {
            return instagram;
        }

        public void setInstagram(String instagram) {
            this.instagram = instagram;
        }

        private String instagram;

        public String getIsCatSelected() {
            return isCatSelected;
        }

        public void setIsCatSelected(String isCatSelected) {
            this.isCatSelected = isCatSelected;
        }

        public String getIs_profilePic_changed() {
            return is_profilePic_changed;
        }

        public void setIs_profilePic_changed(String is_profilePic_changed) {
            this.is_profilePic_changed = is_profilePic_changed;
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

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
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

        public String getLastActivity_time() {
            return lastActivity_time;
        }

        public void setLastActivity_time(String lastActivity_time) {
            this.lastActivity_time = lastActivity_time;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
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
    }

    public static class InterestsBean implements Serializable {
        private String category_id;
        private String category_name;
        private String category_image;

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


    }

    public static class CommonInterestsBean {
        private String category_name;
        private String category_image;

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
    }
}
