package com.soul.app.Bean;

/**
 * Created by ashishkumar on 9/8/16.
 */
public class SocketChatData {

    /**
     * response_type : TEXT_USER
     * messageid : 11019
     * from : 14442
     * message : Hxjfj
     * to : 14445
     * timestamp : 2016-08-09 18:35:32
     * side : RECEIVER
     * message_type : 0
     * msguniqueid : 0
     */

    private String response_type;
    private int messageid;
    private String from;
    private String message;
    private String to;
    private String timestamp;
    private String side;
    private String message_type;
    private String msguniqueid;


    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMsguniqueid() {
        return msguniqueid;
    }

    public void setMsguniqueid(String msguniqueid) {
        this.msguniqueid = msguniqueid;
    }
}
