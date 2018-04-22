package com.soul.app.models.req;

/**
 * Created by ashishkumar on 26/7/16.
 */
public class GetUnreadChatDataReq {
    /**
     * id : 1
     * to : 14335
     * from : 14414
     */

    private String id;
    private String to;
    private String from;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
