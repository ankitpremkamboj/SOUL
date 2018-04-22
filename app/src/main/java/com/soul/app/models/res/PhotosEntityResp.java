package com.soul.app.models.res;

import java.util.List;


public class PhotosEntityResp {


    /**
     * cursors : {"before":"ODE4NDQ2Mzg0OTM1MTM5","after":"MzE5OTA5NzI0Nzg4ODEw"}
     * next : https://graph.facebook.com/v2.5/102224039890714/photos?access_token=EAAK9WBVZAGTIBAEKDM9CYq3gmEuU37Ge0WbHllAZACqHZCJfjr48pGCnw5xzEnZBK6QuZBdoC3YEfWtEes2nnWzNNC1BKFoZCWLMAoVENN9Py7I5Y5dZBFtC70lrIA9ZB1ksTsdjVRGq2bqEgmMZC3eZAJvEq67uJNr4VYpFCcSNfejBFlZC68SwbmTNYjoOmL0Jo0J4H5phsObaI2FkJmFaQK4&fields=images.limit%286%29&limit=25&after=MzE5OTA5NzI0Nzg4ODEw
     */

    private PagingBean paging;
    /**
     * images : [{"height":960,"source":"https://scontent.xx.fbcdn.net/hphotos-xpa1/v/t1.0-9/11218704_818446384935139_5761499547406451518_n.jpg?oh=5d0eec2b4f67e58a7d65854124ae8585&oe=57BDF4D8","width":539},{"height":854,"source":"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-0/p480x480/11218704_818446384935139_5761499547406451518_n.jpg?oh=e0c4d7dc85f2571575eae65100a339b0&oe=57C08963&__gda__=1471892613_5d1e30311536241139cd1b6d544d3de3","width":480},{"height":569,"source":"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-0/p320x320/11218704_818446384935139_5761499547406451518_n.jpg?oh=daaf2eaf5f0eee7634ad938f23403f08&oe=578B4439&__gda__=1472026335_43438f40c9f7fdd81f29a692cdcf1aea","width":320},{"height":540,"source":"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-0/p180x540/11218704_818446384935139_5761499547406451518_n.jpg?oh=af10e9dc32d0adb8d00a6f6dcb563fbf&oe=57C09384&__gda__=1468236130_09a961eb1bc57043d12b15c0f9e5e707","width":303},{"height":231,"source":"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-0/p130x130/11218704_818446384935139_5761499547406451518_n.jpg?oh=2ec395ab84815379f1ef61fec2a8d96f&oe=577B5356&__gda__=1468404656_62a83eb179e646cb967aa637e520a7cc","width":130},{"height":225,"source":"https://fbcdn-photos-c-a.akamaihd.net/hphotos-ak-xpa1/v/t1.0-0/p75x225/11218704_818446384935139_5761499547406451518_n.jpg?oh=3cf8f833914028ec9b410ff8aeb018f6&oe=57B6DA22&__gda__=1467986421_f8e94545ef279079a34e8a41fda77cec","width":126}]
     * id : 818446384935139
     */

    private List<DataBean> data;

    public PagingBean getPaging() {
        return paging;
    }

    public void setPaging(PagingBean paging) {
        this.paging = paging;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PagingBean {
        /**
         * before : ODE4NDQ2Mzg0OTM1MTM5
         * after : MzE5OTA5NzI0Nzg4ODEw
         */

        private CursorsBean cursors;
        private String next;

        public CursorsBean getCursors() {
            return cursors;
        }

        public void setCursors(CursorsBean cursors) {
            this.cursors = cursors;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public static class CursorsBean {
            private String before;
            private String after;

            public String getBefore() {
                return before;
            }

            public void setBefore(String before) {
                this.before = before;
            }

            public String getAfter() {
                return after;
            }

            public void setAfter(String after) {
                this.after = after;
            }
        }
    }

    public static class DataBean {
        private String id;
        /**
         * height : 960
         * source : https://scontent.xx.fbcdn.net/hphotos-xpa1/v/t1.0-9/11218704_818446384935139_5761499547406451518_n.jpg?oh=5d0eec2b4f67e58a7d65854124ae8585&oe=57BDF4D8
         * width : 539
         */

        private List<ImagesBean> images;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            private int height;
            private String source;
            private int width;

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }
    }
}
