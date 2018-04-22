package com.soul.app.models.res;

/**
 * Created by techahead on 23/7/16.
 */
public class UserMatchesResponse {

    /**
     * user_id : 11103
     * message : \ud83d\ude21\ud83d\ude21\ud83d\ude21\ud83d\ude21\ud83d\ude21
     * user_name : رومینا
     * age : 22
     * profile_pic : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xla1/v/t1.0-1/s320x320/1379841_10150004552801901_469209496895221757_n.jpg?oh=fd398cf1306f6efad21c97935b181012&oe=57431997&__gda__=1464423184_3b55ca15da0b5301d3d9807cb9d54e75
     * unread_msg_count : 0
     */

    private String user_id;
    private String message;
    private String user_name;
    private String age;
    private String profile_pic;
    private int unread_msg_count;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public int getUnread_msg_count() {
        return unread_msg_count;
    }

    public void setUnread_msg_count(int unread_msg_count) {
        this.unread_msg_count = unread_msg_count;
    }
}
