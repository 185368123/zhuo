package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class TotalRateBean {

    /**
     * data : [{"user_id":"3","nick_name":"啄啄","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/320171228114000.png","hundred_rate":"1","hundred_level":"a"},{"user_id":"8","nick_name":"深大1","photo_link":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/20170926195437.png","hundred_rate":"1","hundred_level":"a"},{"user_id":"98","nick_name":"深职2","photo_link":"https://wx.qlogo.cn/mmopen/vi_32/LMicarFC5m2dP5pDMM7qYaE1riago2gwELnwsHRsaoooaPUziafNElG5OAkDEFibUAXv9hc6a38gibT07zsCiax7e9aA/0","hundred_rate":"1","hundred_level":"a"},{"user_id":"99","nick_name":"花落、莫相離","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/tsw3h9icPSV6heLgiaYTd6WTpwHicf743D5vbDqia67fye0mQAbpbeC3KxmCRYaN4ibNzIQk23ye7uqeutKSB5icSFGA/0","hundred_rate":"1","hundred_level":"a"},{"user_id":"100","nick_name":"深大2","photo_link":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKNG3tCrmUSNTBmZzIe2LE7L0d1Pyiaibpoeo70BUPA980Ufp1Y97ictf6NGhJRtv5WTeftROlLgA45w/0","hundred_rate":"1","hundred_level":"a"},{"user_id":"102","nick_name":"Roger-华","photo_link":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqt5khFuFMr7c0ehHqaiawIYodZhWO7o4mBFeQZ1BKxRhrahYKqqsQGX8pDS3L6NNoBeQBM7enAuhw/132","hundred_rate":"1","hundred_level":"a"}]
     * msg : 百人团排名记录
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
        /**
         * user_id : 3
         * nick_name : 啄啄
         * photo_link : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/UserIcon/320171228114000.png
         * hundred_rate : 1
         * hundred_level : a
         */

        private String user_id;
        private String nick_name;
        private String photo_link;
        private String hundred_rate;
        private String hundred_level;

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

        public String getHundred_rate() {
            return hundred_rate;
        }

        public void setHundred_rate(String hundred_rate) {
            this.hundred_rate = hundred_rate;
        }

        public String getHundred_level() {
            return hundred_level;
        }

        public void setHundred_level(String hundred_level) {
            this.hundred_level = hundred_level;
        }
    }
}
