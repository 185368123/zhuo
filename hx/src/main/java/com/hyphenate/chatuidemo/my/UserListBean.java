package com.hyphenate.chatuidemo.my;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4.
 */

public class UserListBean {

    /**
     * data : [{"user_id":"1","nick_name":"Roger","photo_link":"https://wx.qlogo.cn/mmopen/vi_32/5icsJibet91SiadmNp95pSQpxr0MAhV38023eRuALNruqRkvqtQ6B6yyvlsmtem3vcLVyibHrg6yibylzFYTtrQgExg/0","account":"501","sex":"1","location":"深圳","exp":"25050"},{"user_id":"3","nick_name":"Vice.R","photo_link":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKSibLyBjfn0JVz4mfP7cVoJicZm5Fc65NGHmg5IFoA6vRJPu2UIbnmNiciaiaID2nhL0cWlknT8T6R7jA/0","account":"873","sex":"1","location":"深圳","exp":"2000"},{"user_id":"4","nick_name":"大灰机","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170925172946.png","account":"324","sex":"1","location":"","exp":"9350"},{"user_id":"5","nick_name":"测试","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/BBS/20171013110807.png","account":"405","sex":"1","location":"","exp":"0"},{"user_id":"6","nick_name":"阿西吧","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20171018170607.png","account":"476","sex":"1","location":"","exp":"7000"},{"user_id":"7","nick_name":"啊啊","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926100108.png","account":"476","sex":"2","location":"","exp":"4550"},{"user_id":"8","nick_name":"啄啄阿卡丽","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","account":"648","sex":"1","location":"","exp":"21850"},{"user_id":"9","nick_name":"123","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/BBS/20171013110807.png","account":"829","sex":"2","location":"(null)","exp":"0"},{"user_id":"11","nick_name":"阿拉蕾","photo_link":"","account":"1111","sex":"1","location":"","exp":"150"},{"user_id":"67","nick_name":"111111","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926100108.png","account":"476","sex":"1","location":"","exp":"1050"}]
     * msg : 返回用户搜索列表
     * error_code : 0
     */

    private String msg;
    private int error_code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", photo_link='" + photo_link + '\'' +
                    ", account='" + account + '\'' +
                    ", sex='" + sex + '\'' +
                    ", location='" + location + '\'' +
                    ", exp='" + exp + '\'' +
                    '}';
        }

        /**
         * user_id : 1
         * nick_name : Roger
         * photo_link : https://wx.qlogo.cn/mmopen/vi_32/5icsJibet91SiadmNp95pSQpxr0MAhV38023eRuALNruqRkvqtQ6B6yyvlsmtem3vcLVyibHrg6yibylzFYTtrQgExg/0
         * account : 501
         * sex : 1
         * location : 深圳
         * exp : 25050
         */

        private String user_id;
        private String nick_name;
        private String photo_link;
        private String account;
        private String sex;
        private String location;
        private String exp;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPhoto_link() {
            return photo_link;
        }

        public void setPhoto_link(String photo_link) {
            this.photo_link = photo_link;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }
    }
}
