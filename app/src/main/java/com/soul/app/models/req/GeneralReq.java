package com.soul.app.models.req;

import java.util.List;

/**
 * Created by techahead on 18/7/16.
 */
public class GeneralReq {

    /**
     * user_id : 11133
     */

    private String user_id;
    private String other_id;
    private String cognito_status;

    private String about_text;
    private String instagram;
    private String education;


    // 0-dislike 1-like
    private String is_match;
    /**
     * category_id : 117
     * is_primary : 1
     */

    private List<IntrestsEntity> intrests;
    /**
     * distance : 192.951523
     * distance_unit : mi
     * max_age : 65
     * min_age : 32
     */

    private String is_male;
    private String is_female;
    private String incogonito_mode;
    private String chat_notification;
    private String distance;
    private String distance_unit;
    private String max_age;
    private String min_age;
    private String min_height;
    private String max_height;
    private String academy;
    private String interest;
    private String height;
    private String education_status;
    private String location_status;
    private String work_status;


    /**
     * address :
     * latitude : 24
     * longitude : 28
     * last_seen : 123
     */

    private String address;
    private String latitude;
    private String longitude;
    private String last_seen;
    /**
     * flaged_comment : Hello
     * flaged_type : InappropriateMesg
     * reported_by :  11172
     */

    private String flaged_comment;
    private String flaged_type;
    private String reported_by;

    private String profile_pic;
    private String user_image1;
    private String user_image2;
    private String user_image3;
    private String user_image4;
    private String user_image_column;
    /**
     * match_notification : 0
     */

    private String match_notification;
    private String unmatch_reason;

    private String is_liberal;
    private String is_moderate;
    private String is_conservative;
    private String is_nonpracticing;
    private String is_notimportant;
    private String is_sunni;
    private String is_shiaismaili;
    private String is_justmuslim;
    private String is_convert;


    public String getUser_image_column() {
        return user_image_column;
    }

    public void setUser_image_column(String user_image_column) {
        this.user_image_column = user_image_column;
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

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getIs_male() {
        return is_male;
    }

    public void setIs_male(String is_male) {
        this.is_male = is_male;
    }

    public String getIs_female() {
        return is_female;
    }

    public void setIs_female(String is_female) {
        this.is_female = is_female;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<IntrestsEntity> getIntrests() {
        return intrests;
    }

    public void setIntrests(List<IntrestsEntity> intrests) {
        this.intrests = intrests;
    }

    public String getOther_id() {
        return other_id;
    }

    public void setOther_id(String other_id) {
        this.other_id = other_id;
    }

    public String getIs_match() {
        return is_match;
    }

    public void setIs_match(String is_match) {
        this.is_match = is_match;
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

    public String getMax_age() {
        return max_age;
    }

    public void setMax_age(String max_age) {
        this.max_age = max_age;
    }

    public String getMin_age() {
        return min_age;
    }

    public void setMin_age(String min_age) {
        this.min_age = min_age;
    }

    public String getCognito_status() {
        return cognito_status;
    }

    public void setCognito_status(String cognito_status) {
        this.cognito_status = cognito_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getFlaged_comment() {
        return flaged_comment;
    }

    public void setFlaged_comment(String flaged_comment) {
        this.flaged_comment = flaged_comment;
    }

    public String getFlaged_type() {
        return flaged_type;
    }

    public void setFlaged_type(String flaged_type) {
        this.flaged_type = flaged_type;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public String getMatch_notification() {
        return match_notification;
    }

    public void setMatch_notification(String match_notification) {
        this.match_notification = match_notification;
    }

    public String getUnmatch_reason() {
        return unmatch_reason;
    }

    public void setUnmatch_reason(String unmatch_reason) {
        this.unmatch_reason = unmatch_reason;
    }

    public String getIs_liberal() {
        return is_liberal;
    }

    public void setIs_liberal(String is_liberal) {
        this.is_liberal = is_liberal;
    }

    public String getIs_moderate() {
        return is_moderate;
    }

    public void setIs_moderate(String is_moderate) {
        this.is_moderate = is_moderate;
    }

    public String getIs_conservative() {
        return is_conservative;
    }

    public void setIs_conservative(String is_conservative) {
        this.is_conservative = is_conservative;
    }

    public String getIs_nonpracticing() {
        return is_nonpracticing;
    }

    public void setIs_nonpracticing(String is_nonpracticing) {
        this.is_nonpracticing = is_nonpracticing;
    }

    public String getIs_notimportant() {
        return is_notimportant;
    }

    public void setIs_notimportant(String is_notimportant) {
        this.is_notimportant = is_notimportant;
    }

    public String getIs_sunni() {
        return is_sunni;
    }

    public void setIs_sunni(String is_sunni) {
        this.is_sunni = is_sunni;
    }

    public String getIs_shiaismaili() {
        return is_shiaismaili;
    }

    public void setIs_shiaismaili(String is_shiaismaili) {
        this.is_shiaismaili = is_shiaismaili;
    }

    public String getIs_justmuslim() {
        return is_justmuslim;
    }

    public void setIs_justmuslim(String is_justmuslim) {
        this.is_justmuslim = is_justmuslim;
    }

    public String getIs_convert() {
        return is_convert;
    }

    public void setIs_convert(String is_convert) {
        this.is_convert = is_convert;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getLocation_status() {
        return location_status;
    }

    public void setLocation_status(String location_status) {
        this.location_status = location_status;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public static class IntrestsEntity {
        private String category_id;
        private String is_primary;

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getIs_primary() {
            return is_primary;
        }

        public void setIs_primary(String is_primary) {
            this.is_primary = is_primary;
        }
    }


}
