package com.soul.app.models.res;

import java.io.Serializable;

/**
 * Created by ashishkumar on 26/7/16.
 */
public class SendMessageRes {

    /**
     * result : true
     * msg : Message sent.
     * data : {"to":"14335","from":"14414","message":"Testjchjv","video_thumb":"","attachment":"","msg_type":"0","created_on":"1469517420","chat_screenid":10495,"profile_pic":""}
     */

    private boolean result;
    private String msg;
    /**
     * to : 14335
     * from : 14414
     * message : Testjchjv
     * video_thumb :
     * attachment :
     * msg_type : 0
     * created_on : 1469517420
     * chat_screenid : 10495
     * profile_pic :
     */

    private DataBean data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String to;
        private String from;
        private String message;
        private String video_thumb;
        private String attachment;
        private String msg_type;
        private String created_on;
        private int chat_screenid;
        private String profile_pic;

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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getVideo_thumb() {
            return video_thumb;
        }

        public void setVideo_thumb(String video_thumb) {
            this.video_thumb = video_thumb;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getMsg_type() {
            return msg_type;
        }

        public void setMsg_type(String msg_type) {
            this.msg_type = msg_type;
        }

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public int getChat_screenid() {
            return chat_screenid;
        }

        public void setChat_screenid(int chat_screenid) {
            this.chat_screenid = chat_screenid;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }
    }
}
