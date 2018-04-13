package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2017/12/6.
 */

public class GroupStatusBean {
        /**
         * group_match_status : 0
         * group_choice_id : 0
         * team_id : 0
         * group_id : 0
         * member_ids :
         * match_member_ids :
         * nick_names :
         * photo_link :
         * match_nick_names :
         * match_photo_link :
         */

        private String group_match_status;
        private String group_choice_id;
        private String team_id;
        private String group_id;
        private String member_ids;
        private String match_member_ids;
        private String nick_names;
        private String photo_link;
        private String match_nick_names;
        private String match_photo_link;

        public String getGroup_match_status() {
            return group_match_status;
        }

        public void setGroup_match_status(String group_match_status) {
            this.group_match_status = group_match_status;
        }

        public String getGroup_choice_id() {
            return group_choice_id;
        }

        public void setGroup_choice_id(String group_choice_id) {
            this.group_choice_id = group_choice_id;
        }

        public String getTeam_id() {
            return team_id;
        }

        public void setTeam_id(String team_id) {
            this.team_id = team_id;
        }

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getMember_ids() {
            return member_ids;
        }

        public void setMember_ids(String member_ids) {
            this.member_ids = member_ids;
        }

        public String getMatch_member_ids() {
            return match_member_ids;
        }

        public void setMatch_member_ids(String match_member_ids) {
            this.match_member_ids = match_member_ids;
        }

        public String getNick_names() {
            return nick_names;
        }

        public void setNick_names(String nick_names) {
            this.nick_names = nick_names;
        }

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }

        public String getMatch_nick_names() {
            return match_nick_names;
        }

        public void setMatch_nick_names(String match_nick_names) {
            this.match_nick_names = match_nick_names;
        }

        public String getMatch_photo_link() {
            return match_photo_link;
        }

        public void setMatch_photo_link(String match_photo_link) {
            this.match_photo_link = match_photo_link;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "group_match_status='" + group_match_status + '\'' +
                    ", group_choice_id='" + group_choice_id + '\'' +
                    ", team_id='" + team_id + '\'' +
                    ", group_id='" + group_id + '\'' +
                    ", member_ids='" + member_ids + '\'' +
                    ", match_member_ids='" + match_member_ids + '\'' +
                    ", nick_names='" + nick_names + '\'' +
                    ", photo_link='" + photo_link + '\'' +
                    ", match_nick_names='" + match_nick_names + '\'' +
                    ", match_photo_link='" + match_photo_link + '\'' +
                    '}';
        }
}
