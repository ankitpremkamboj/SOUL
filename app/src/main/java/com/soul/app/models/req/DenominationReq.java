package com.soul.app.models.req;

/**
 * Created by techahead on 15/7/16.
 */
public class DenominationReq {

    /**
     * denomination : Sunni
     * status : Liberal
     * user_id : 11133
     */

    private String denomination;
    private String status;
    private String user_id;

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
