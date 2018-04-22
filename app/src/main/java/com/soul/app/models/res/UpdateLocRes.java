package com.soul.app.models.res;

/**
 * Created by techahead on 1/8/16.
 */
public class UpdateLocRes  {

    /**
     * last_seen : 1470050499763
     * latitude : 77.391025
     * longitude : 28.535515
     * user_id : 14434
     */

    private String last_seen;
    private String latitude;
    private String longitude;
    private String user_id;

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
