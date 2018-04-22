package com.soul.app.models.res;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by techahead on 25/7/16.
 */
public class UserMatchesRes implements Parcelable{

    /**
     * user_id : 5187
     * message : Socal..
     * user_name : Amir
     * age : 28
     * profile_pic : http://graph.facebook.com/10103636673169671/picture?width=800&height=800
     * unread_msg_count : 0
     */

    private String user_id;
    private String message;
    private String user_name;
    private String age;
    private String profile_pic;
    private int unread_msg_count;
    private String created_on;

    protected UserMatchesRes(Parcel in) {
        user_id = in.readString();
        message = in.readString();
        user_name = in.readString();
        age = in.readString();
        profile_pic = in.readString();
        unread_msg_count = in.readInt();
        created_on=in.readString();
    }

    public static final Creator<UserMatchesRes> CREATOR = new Creator<UserMatchesRes>() {
        @Override
        public UserMatchesRes createFromParcel(Parcel in) {
            return new UserMatchesRes(in);
        }

        @Override
        public UserMatchesRes[] newArray(int size) {
            return new UserMatchesRes[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(message);
        dest.writeString(user_name);
        dest.writeString(age);
        dest.writeString(profile_pic);
        dest.writeInt(unread_msg_count);
        dest.writeString(created_on);
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
