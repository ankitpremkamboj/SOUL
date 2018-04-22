package com.soul.app.models.res;

import java.util.List;

/**
 * Created by techahead on 14/7/16.
 */
public class FriendsListIdResp {


    /**
     * total_count : 201
     */

    private SummaryEntity summary;
    /**
     * cursors : {"after":"QVFIUjVBWktRRFVVcE45TVhGVjRYUkIxR2U0Ym1uSEpFTGJoVjVnemdienJINkNYVS1Va2lDWUpKZAEFkODVfNG5Wem9HeC1IdWZApRTBNTTFWaUs3RzRxVWh3","before":"QVFIUjJpNGZA1MzZApYVB5cURTMmRJeWNtZADVFVTNnOXo0TTlBckZABQ2JLeWZA1WEU0U3pwYTlvdlhQZA1VraHVDc3RmZAXVHbWw5MnBIM2toOC12TmVkTGZAlQzVB"}
     */

    private PagingEntity paging;
    /**
     * id : 540689292808581
     * name : Xtrem AI
     */

    private List<DataEntity> data;

    public SummaryEntity getSummary() {
        return summary;
    }

    public void setSummary(SummaryEntity summary) {
        this.summary = summary;
    }

    public PagingEntity getPaging() {
        return paging;
    }

    public void setPaging(PagingEntity paging) {
        this.paging = paging;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public static class SummaryEntity {
        private int total_count;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
    }

    public static class PagingEntity {
        /**
         * after : QVFIUjVBWktRRFVVcE45TVhGVjRYUkIxR2U0Ym1uSEpFTGJoVjVnemdienJINkNYVS1Va2lDWUpKZAEFkODVfNG5Wem9HeC1IdWZApRTBNTTFWaUs3RzRxVWh3
         * before : QVFIUjJpNGZA1MzZApYVB5cURTMmRJeWNtZADVFVTNnOXo0TTlBckZABQ2JLeWZA1WEU0U3pwYTlvdlhQZA1VraHVDc3RmZAXVHbWw5MnBIM2toOC12TmVkTGZAlQzVB
         */

        private CursorsEntity cursors;

        public CursorsEntity getCursors() {
            return cursors;
        }

        public void setCursors(CursorsEntity cursors) {
            this.cursors = cursors;
        }

        public static class CursorsEntity {
            private String after;
            private String before;

            public String getAfter() {
                return after;
            }

            public void setAfter(String after) {
                this.after = after;
            }

            public String getBefore() {
                return before;
            }

            public void setBefore(String before) {
                this.before = before;
            }
        }
    }

    public static class DataEntity {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
