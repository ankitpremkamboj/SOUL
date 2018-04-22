package com.soul.app.models.res;

/**
 * Created by techahead on 27/7/16.
 */
public class ChatConversationRes {

    /**
     * user_id : 5187
     * message : Socal..
     * msg_type : 0
     * user_name : Amir
     * profile_pic : http://graph.facebook.com/10103636673169671/picture?width=800&height=800
     * created_on : 1439929670
     * unread_msg_count : 0
     */

    private String user_id;
    private String message;
    private String msg_type;
    private String user_name;
    private String age;
    private String profile_pic;
    private String created_on;
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

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public int getUnread_msg_count() {
        return unread_msg_count;
    }

    public void setUnread_msg_count(int unread_msg_count) {
        this.unread_msg_count = unread_msg_count;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
