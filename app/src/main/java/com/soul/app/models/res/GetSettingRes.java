package com.soul.app.models.res;

import java.io.Serializable;

/**
 * Created by techahead on 22/7/16.
 */
public class GetSettingRes implements Serializable {


    private String user_id;
    private String distance;
    private String distance_unit;
    private String min_age;
    private String max_age;
    private String male;
    private String female;
    private String incogonito_mode;
    private String chat_notification;
    private String match_notification;
    private String cogonito_status;

    private String min_height;
    private String max_height;
    private String academy;
    private String intrestid;

    private String intrestname;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public void setDistance_unit(String distance_unit) {
        this.distance_unit = distance_unit;
    }

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getIncogonito_mode() {
        return incogonito_mode;
    }

    public void setIncogonito_mode(String incogonito_mode) {
        this.incogonito_mode = incogonito_mode;
    }

    public String getChat_notification() {
        return chat_notification;
    }

    public void setChat_notification(String chat_notification) {
        this.chat_notification = chat_notification;
    }

    public String getMatch_notification() {
        return match_notification;
    }

    public void setMatch_notification(String match_notification) {
        this.match_notification = match_notification;
    }

    public String getCogonito_status() {
        return cogonito_status;
    }

    public void setCogonito_status(String cogonito_status) {
        this.cogonito_status = cogonito_status;
    }

    public String getMin_height() {
        return min_height;
    }

    public void setMin_height(String min_height) {
        this.min_height = min_height;
    }

    public String getMax_height() {
        return max_height;
    }

    public void setMax_height(String max_height) {
        this.max_height = max_height;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }


    public String getIntrestid() {
        return intrestid;
    }

    public void setIntrestid(String intrestid) {
        this.intrestid = intrestid;
    }

    public String getIntrestname() {
        return intrestname;
    }

    public void setIntrestname(String intrestname) {
        this.intrestname = intrestname;
    }

}
