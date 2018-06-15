package com.hyphenate.chatuidemo.my.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class FriendsCollegeBean {

        /**
         * shenda : [{"school":"深大","college":"师范学院","grade":"","num":0},{"school":"深大","college":"人文学院","grade":"","num":0},{"school":"深大","college":"外国语学院","grade":"","num":0},{"school":"深大","college":"传播学院","grade":"","num":0},{"school":"深大","college":"经济学院","grade":"","num":0},{"school":"深大","college":"法学院","grade":"","num":0},{"school":"深大","college":"艺术设计学院","grade":"","num":0},{"school":"深大","college":"马克思主义学院(社会科学学院)","grade":"","num":0},{"school":"深大","college":"数学与统计学院","grade":"","num":0},{"school":"深大","college":"物理与能源学院","grade":"","num":0},{"school":"深大","college":"化学与环境工程学院","grade":"","num":0},{"school":"深大","college":"材料学院","grade":"","num":0},{"school":"深大","college":"信息工程学院","grade":"","num":0},{"school":"深大","college":"计算机与软件学院","grade":"","num":0},{"school":"深大","college":"建筑与城市规划学院","grade":"","num":0},{"school":"深大","college":"土木工程学院","grade":"","num":0},{"school":"深大","college":"电子科学与技术学院","grade":"","num":0},{"school":"深大","college":"机电与控制工程学院","grade":"","num":0},{"school":"深大","college":"生命与海洋科学学院","grade":"","num":0},{"school":"深大","college":"光电工程学院","grade":"","num":0},{"school":"深大","college":"医学部","grade":"","num":0},{"school":"深大","college":"国际交流学院","grade":"","num":0},{"school":"深大","college":"金钟音乐学院","grade":"","num":0},{"school":"深大","college":"心理与社会学院","grade":"","num":0},{"school":"深大","college":"南特商学院","grade":"","num":0},{"school":"深大","college":"高等研究院","grade":"","num":0},{"school":"深大","college":"创业学院","grade":"","num":0},{"school":"深大","college":"高尔夫学院","grade":"","num":0},{"school":"深大","college":"继续教育学院","grade":"","num":0},{"school":"深大","college":"研究生院","grade":"","num":0},{"school":"深大","college":"管理学院","grade":"","num":0}]
         * shenzhi : [{"school":"深职","college":"创新创业学院","grade":"大一","num":1},{"school":"深大","college":"电子与通信工程学院","grade":"","num":0},{"school":"深大","college":"计算机工程学院","grade":"","num":0},{"school":"深大","college":"机电工程学院","grade":"","num":0},{"school":"深大","college":"经济学院","grade":"","num":0},{"school":"深大","college":"管理学院","grade":"","num":0},{"school":"深大","college":"传播工程学院","grade":"","num":0},{"school":"深大","college":"艺术设计学院","grade":"","num":0},{"school":"深大","college":"应用外国语学院","grade":"","num":0},{"school":"深大","college":"应用化学与生物技术学院","grade":"","num":0},{"school":"深大","college":"建筑与环境工程学院","grade":"","num":0},{"school":"深大","college":"数字创意与动画学院","grade":"","num":0},{"school":"深大","college":"汽车与交通学院","grade":"","num":0},{"school":"深大","college":"医学技术与护理学院","grade":"","num":0},{"school":"深大","college":"人文学院","grade":"","num":0},{"school":"深大","college":"继续教育与培训学院","grade":"","num":0}]
         * time : 1529033378
         */

        private int time;
        private List<ShendaBean> shenda;
        private List<ShenzhiBean> shenzhi;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public List<ShendaBean> getShenda() {
            return shenda;
        }

        public void setShenda(List<ShendaBean> shenda) {
            this.shenda = shenda;
        }

        public List<ShenzhiBean> getShenzhi() {
            return shenzhi;
        }

        public void setShenzhi(List<ShenzhiBean> shenzhi) {
            this.shenzhi = shenzhi;
        }

        public static class ShendaBean {
            /**
             * school : 深大
             * college : 师范学院
             * grade :
             * num : 0
             */

            private String school;
            private String college;
            private String grade;
            private int num;

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getCollege() {
                return college;
            }

            public void setCollege(String college) {
                this.college = college;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }

        public static class ShenzhiBean {
            /**
             * school : 深职
             * college : 创新创业学院
             * grade : 大一
             * num : 1
             */

            private String school;
            private String college;
            private String grade;
            private int num;

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getCollege() {
                return college;
            }

            public void setCollege(String college) {
                this.college = college;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
}
