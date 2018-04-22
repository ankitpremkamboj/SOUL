package com.soul.app.models.req;

import java.util.List;

/**
 * Created by techahead on 13/7/16.
 */
public class LoginReq {

    /**
     * age : 25
     * device_token :
     * education : University of Delhi
     * email : iosdev1@techaheadcorp.com
     * facebook_friend : [{"id":"10206025584713087","name":"Khalil Jessa"},{"id":"1503248256602568","name":"Jon Martin"}]
     * facebook_id : 1457518861210302
     * gender : female
     * hometown : Noida, India
     * latitude : 0.000000
     * location : Noida, India
     * longitude : 0.000000
     * profile_pic : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p320x320/10271638_1527969737498547_716786099359522166_n.jpg?oh:bec27f10eb2fd4c8c97ee713975db972&oe:58326257&__gda__:1476380081_871cdba24dee2c2b8800c752b0a53ce4
     * user_name :  Ios
     * work  : TechAhead Software
     */

    private String age;
    private String device_token;
    private String education;
    private String email;
    private String facebook_id;
    private String gender;
    private String hometown;
    private String latitude;
    private String location;
    private String longitude;
    private String profile_pic;
    private String user_name;
    private String work;
    /**
     * id : 10206025584713087
     * name : Khalil Jessa
     */

    private List<FacebookFriendEntity> facebook_friend;

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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public List<FacebookFriendEntity> getFacebook_friend() {
        return facebook_friend;
    }

    public void setFacebook_friend(List<FacebookFriendEntity> facebook_friend) {
        this.facebook_friend = facebook_friend;
    }

    public static class FacebookFriendEntity {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
