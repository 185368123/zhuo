package li.com.base.basesinglebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class SingleChooseBean {

        /**
         * choice_id : 3
         * choice_name : 啄一啄
         * exp : 0
         * type : single
         * value : 初识,相处,交往,热恋,冷战1天,哄女孩,在一起
         * desc : 线上：小视频介绍自己
         线下：饮野（二选一）, 线上：打游戏
         线下：行街食饭,线上：视频聊天&游戏
         线下：营造浪漫的约会,线上：互换情史
         线下：散步,线上：录一首歌或视频给对方
         线下：唱K,线上：互相寄一个小礼物
         线下：旅游,Yes  or  No
         * choice_type : 0
         * choice_title : 挑个人带走
         * match_status : 0
         * status : 66
         * start_time : 0
         */

        private String choice_id;
        private String choice_name;
        private String exp;
        private String type;
        private String value;
        private String desc;
        private String choice_type;
        private String choice_title;
        private String match_status;
        private String status;
        private int start_time;

        public String getChoice_id() {
            return choice_id;
        }

        public void setChoice_id(String choice_id) {
            this.choice_id = choice_id;
        }

        public String getChoice_name() {
            return choice_name;
        }

        public void setChoice_name(String choice_name) {
            this.choice_name = choice_name;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getChoice_type() {
            return choice_type;
        }

        public void setChoice_type(String choice_type) {
            this.choice_type = choice_type;
        }

        public String getChoice_title() {
            return choice_title;
        }

        public void setChoice_title(String choice_title) {
            this.choice_title = choice_title;
        }

        public String getMatch_status() {
            return match_status;
        }

        public void setMatch_status(String match_status) {
            this.match_status = match_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getStart_time() {
            return start_time;
        }

        public void setStart_time(int start_time) {
            this.start_time = start_time;
        }
}
