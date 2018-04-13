package com.hyphenate.chatuidemo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */

public class RateBean {
        /**
         * uid : 001
         * user_id : 1
         * nick_name : abc
         * rate : 1
         * task : [{"oriscore":"10","taskrate":1},{"oriscore":"2018-01-05 10:21:10","taskrate":1}]
         * score : 90
         */

        private String uid;
        private int user_id;
        private String nick_name;
        private int rate;
        private String score;
        private List<TaskBean> task;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public List<TaskBean> getTask() {
            return task;
        }

        public void setTask(List<TaskBean> task) {
            this.task = task;
        }

        public static class TaskBean {
            /**
             * oriscore : 10
             * taskrate : 1
             */

            private String oriscore;
            private int taskrate;

            public String getOriscore() {
                return oriscore;
            }

            public void setOriscore(String oriscore) {
                this.oriscore = oriscore;
            }

            public int getTaskrate() {
                return taskrate;
            }

            public void setTaskrate(int taskrate) {
                this.taskrate = taskrate;
            }
        }
}
