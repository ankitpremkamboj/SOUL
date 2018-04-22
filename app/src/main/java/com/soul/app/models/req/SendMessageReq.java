package com.soul.app.models.req;

/**
 * Created by ashishkumar on 26/7/16.
 */
public class SendMessageReq {
    /**
     * message : Testjchjv
     * to : 14335
     * msg_type : 0
     * attachment :
     * video_thumb :
     * from : 14414
     */

    private String message;
    private String to;
    private String msg_type;
    private String attachment;
    private String video_thumb;
    private String from;

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

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
