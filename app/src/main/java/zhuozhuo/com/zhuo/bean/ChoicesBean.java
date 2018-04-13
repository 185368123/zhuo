package zhuozhuo.com.zhuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ChoicesBean {

    /**
     * data : [{"choice_id":"2","choice_name":"暖男","exp":"0","type":"single","value":"送花,送早餐,送下午茶,温暖她的心,回暖他的心,吃饭,礼物","desc":"让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,第二天早上送早餐给女生，记得约好时间地点哦,送下午茶给女生，记得约好时间地点哦,陪女生做一件她经常做的事,陪男生做一件他经常做的事,最后的晚餐,互相送一个小礼物","choice_title":"做/找个暖男"},{"choice_id":"3","choice_name":"幽默","exp":"0","type":"single","value":"送花,讲笑话,互相发自画像,拍一张鬼脸,坑队友,吃饭,内心的认真","desc":"让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,相互每10分钟说一个笑话说10个（3个带费玉清风格）,发挥你们的神笔的时候了,拍一张鬼脸发给对方,跟舍友说对面是自己亲弟弟妹妹，让舍友请你们吃饭,最后的晚餐,在录歌app深情录一首歌给对方","choice_title":"拍拖时怎么样的"},{"choice_id":"4","choice_name":"游戏","exp":"0","type":"single","value":"送花,打游戏,教男生玩一款新游戏,教女生玩一款新游戏,网吧双排,吃饭,互相送游戏皮肤或装备","desc":"让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,打3把王者或者LOL或者其他,教男生玩一款新游戏,教女生玩一款新游戏,也让别人羡慕下你也有异性去网吧,最后的晚餐,互相送游戏皮肤或装备","choice_title":"找个人陪自己打游戏"},{"choice_id":"5","choice_name":"疯狂","exp":"0","type":"single","value":"送花,游乐园,吹海风,酒吧,旅游,kiss,对方一张明信片","desc":"让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,欢乐谷浪起来，不要浪费学生证作用，（AA制）,凌晨深圳湾溜,喝酒，释放真我,说走就走（AA制）,法式，日式，你们喜欢的方式就好,对方一张明信片","choice_title":"谈一次疯狂的恋爱"},{"choice_id":"16","choice_name":"学习","exp":"0","type":"single","value":"送花,一起看一部纪录片,一起读一本书,一起跑步,图书馆,吃饭,送对方一本书","desc":"送花,一起看一部纪录片,一起读一本书,一起跑步,图书馆,吃饭,送对方一本书","choice_title":"学习"},{"choice_id":"17","choice_name":"共享舍友","exp":"0","type":"single","value":"送花,互相介绍舍友,一起唱k,帮舍友营造浪漫的约会,第二次安排他们吃饭,有没有考虑你么在一起呢？","desc":"送花,互相介绍舍友（兴趣，爱好，性格，喜欢类型，雷区）,一起唱k,帮舍友营造浪漫的约会,第二次安排他们吃饭,有没有考虑你么在一起呢？","choice_title":"共享舍友"},{"choice_id":"18","choice_name":"吃货","exp":"0","type":"single","value":"送花,美食1,美食2,美食3,美食4,美食5,为对方做一个吃的","desc":"送花,美食1,美食2,美食3,美食4,美食5,为对方做一个吃的","choice_title":"吃货"}]
     * msg : 返回个人的选项
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
         * choice_id : 2
         * choice_name : 暖男
         * exp : 0
         * type : single
         * value : 送花,送早餐,送下午茶,温暖她的心,回暖他的心,吃饭,礼物
         * desc : 让所有女生都收到花，每晚9点下楼到女生宿舍楼下送花,第二天早上送早餐给女生，记得约好时间地点哦,送下午茶给女生，记得约好时间地点哦,陪女生做一件她经常做的事,陪男生做一件他经常做的事,最后的晚餐,互相送一个小礼物
         * choice_title : 做/找个暖男
         */

        private String choice_id;
        private String choice_name;
        private String exp;
        private String type;
        private String value;
        private String desc;
        private String choice_title;

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

        public String getChoice_title() {
            return choice_title;
        }

        public void setChoice_title(String choice_title) {
            this.choice_title = choice_title;
        }
    }
}
