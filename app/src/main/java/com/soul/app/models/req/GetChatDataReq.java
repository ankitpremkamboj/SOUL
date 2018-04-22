package com.soul.app.models.req;

/**
 * Created by ashishkumar on 26/7/16.
 */
public class GetChatDataReq {
    /**
     * from : 14414
     * start_limit : 0
     * end_limit : 10
     * to : 14335
     * created_on :
     */

    private String from;
    private String start_limit;
    private String end_limit;
    private String to;
    private String created_on;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStart_limit() {
        return start_limit;
    }

    public void setStart_limit(String start_limit) {
        this.start_limit = start_limit;
    }

    public String getEnd_limit() {
        return end_limit;
    }

    public void setEnd_limit(String end_limit) {
        this.end_limit = end_limit;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
