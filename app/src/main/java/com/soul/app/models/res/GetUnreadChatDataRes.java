package com.soul.app.models.res;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ashishkumar on 26/7/16.
 */
public class GetUnreadChatDataRes {
    /**
     * result : true
     * msg : chat data
     * data : [{"chat_screenid":"10495","from":"14414","to":"14335","attachment":"http://103.25.130.197/salaam-swipe/","video_thumb":"http://103.25.130.197/salaam-swipe/","msg_type":"0","created_on":"1469517420","profile_pic":"https://graph.facebook.com/v2.4/1125645360830578/picture?type=large","message":"Testjchjv"},{"chat_screenid":"10496","from":"14414","to":"14335","attachment":"http://103.25.130.197/salaam-swipe/","video_thumb":"http://103.25.130.197/salaam-swipe/","msg_type":"0","created_on":"1469517710","profile_pic":"https://graph.facebook.com/v2.4/1125645360830578/picture?type=large","message":"sfdsdf"}]
     */

    private boolean result;
    private String msg;
    /**
     * chat_screenid : 10495
     * from : 14414
     * to : 14335
     * attachment : http://103.25.130.197/salaam-swipe/
     * video_thumb : http://103.25.130.197/salaam-swipe/
     * msg_type : 0
     * created_on : 1469517420
     * profile_pic : https://graph.facebook.com/v2.4/1125645360830578/picture?type=large
     * message : Testjchjv
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        private String chat_screenid;
        private String from;
        private String to;
        private String attachment;
        private String video_thumb;
        private String msg_type;
        private String created_on;
        private String profile_pic;
        private String message;



        public String getChat_screenid() {
            return chat_screenid;
        }

        public void setChat_screenid(String chat_screenid) {
            this.chat_screenid = chat_screenid;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
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

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
