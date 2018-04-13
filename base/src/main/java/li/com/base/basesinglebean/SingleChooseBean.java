package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/3/26.
 */

public class SingleChooseBean {

        /**
         * choice_id : 3
         * choice_name : 啄一啄
         * exp : 0
         * type : single
         * value : 约出来（上传7幅相生成视频）,再约出来,还是约出来,继续约出来,不要停约出来,无聊约出来,（我想不出）约出来
         * desc : do something you want,do everything you want
         ,do anything you want,do onething you want,do one more thing you want,do  two more thing you want,do (i don't  know)thing you want
         * choice_title : 挑个人带走
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

    @Override
    public String toString() {
        return "SingleChooseBean{" +
                "choice_id='" + choice_id + '\'' +
                ", choice_name='" + choice_name + '\'' +
                ", exp='" + exp + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                ", choice_title='" + choice_title + '\'' +
                '}';
    }
}
