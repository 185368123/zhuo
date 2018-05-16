package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2017/11/11.
 */

public class SingleStatusBean {

        /**
         * match_status : 0
         * time : 0
         * unread_share_count : 0
         * unread_group_count : 0
         * unread_line_count : -1
         * unread_msg_count : 0
         */

        private String match_status;
        private int time;
        private int unread_share_count;
        private int unread_group_count;
        private String unread_line_count;
        private int unread_msg_count;

        public String getMatch_status() {
            return match_status;
        }

        public void setMatch_status(String match_status) {
            this.match_status = match_status;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getUnread_share_count() {
            return unread_share_count;
        }

        public void setUnread_share_count(int unread_share_count) {
            this.unread_share_count = unread_share_count;
        }

        public int getUnread_group_count() {
            return unread_group_count;
        }

        public void setUnread_group_count(int unread_group_count) {
            this.unread_group_count = unread_group_count;
        }

        public String getUnread_line_count() {
            return unread_line_count;
        }

        public void setUnread_line_count(String unread_line_count) {
            this.unread_line_count = unread_line_count;
        }

        public int getUnread_msg_count() {
            return unread_msg_count;
        }

        public void setUnread_msg_count(int unread_msg_count) {
            this.unread_msg_count = unread_msg_count;
        }
}
