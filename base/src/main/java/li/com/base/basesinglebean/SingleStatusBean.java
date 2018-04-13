package li.com.base.basesinglebean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/11.
 */

public class SingleStatusBean {

        /**
         * match_status : 0
         * choice : {"choice_id":"2","choice_name":"暖男","choice_title":"做/找个暖男","exp":"0","type":"single","value":"送花,送早餐,送下午茶,温暖她的心,回暖他的心,吃饭,礼物","desc":"让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,第二天早上送早餐给女生，记得约好时间地点哦,送下午茶给女生，记得约好时间地点哦,陪女生做一件她经常做的事,陪男生做一件他经常做的事,最后的晚餐,互相送一个小礼物","status":"1","total_step":"7"}
         * finish : 0
         * unread_share_count : 1
         * unread_group_count : 0
         */

        private String match_status;
        private int  time;
        private String unread_share_count;
        private String unread_group_count;


        public void setTime(int time) {
            this.time = time;
        }

        public int getTime() {
            return time;
        }


        public String getMatch_status() {
            return match_status;
        }

        public void setMatch_status(String match_status) {
            this.match_status = match_status;
        }



        public String getUnread_share_count() {
            return unread_share_count;
        }

        public void setUnread_share_count(String unread_share_count) {
            this.unread_share_count = unread_share_count;
        }

        public String getUnread_group_count() {
            return unread_group_count;
        }

        public void setUnread_group_count(String unread_group_count) {
            this.unread_group_count = unread_group_count;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "match_status='" + match_status + '\'' +
                    ", unread_share_count='" + unread_share_count + '\'' +
                    ", unread_group_count='" + unread_group_count + '\'' +
                    '}';
        }

}
