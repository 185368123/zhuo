package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/4/4.
 */

public class VisonBean {

    /**
     * android_version : 1.0.10
     * android_strong :
     * android_url : http://a.app.qq.com/o/simple.jsp?pkgname=zhuozhuo.com.zhuo
     * version_type:"这就是抽奖码。想怎么用就怎么用了"
     * str_type:"1"
     * phone : {"image_type":"1","image_name":"美男","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232613698836489.png","http_type":"1","http_url":""}
     * phone_x : {"image_type":"2","image_name":"美男X","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232564979394853.png","http_type":"1","http_url":""}
     */

    private String android_version;
    private String android_strong;
    private String android_url;
    private String version_type;
    private String str_type;
    private PhoneBean phone;
    private PhoneXBean phone_x;

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getAndroid_strong() {
        return android_strong;
    }

    public void setAndroid_strong(String android_strong) {
        this.android_strong = android_strong;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }

    public String getVersion_type() {
        return version_type;
    }

    public String getStr_type() {
        if (str_type==null){
            return "0";
        }else
        return str_type;
    }

    public void setVersion_type(String version_type) {
        this.version_type = version_type;
    }

    public void setStr_type(String str_type) {
        this.str_type = str_type;
    }

    public PhoneBean getPhone() {
        return phone;
    }

    public void setPhone(PhoneBean phone) {
        this.phone = phone;
    }

    public PhoneXBean getPhone_x() {
        return phone_x;
    }

    public void setPhone_x(PhoneXBean phone_x) {
        this.phone_x = phone_x;
    }

    public static class PhoneBean {
        /**
         * image_type : 1
         * image_name : 美男
         * image_url : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232613698836489.png
         * http_type : 1
         * http_url :
         */

        private String image_type;
        private String image_name;
        private String image_url;
        private String http_type;
        private String http_url;

        public String getImage_type() {
            return image_type;
        }

        public void setImage_type(String image_type) {
            this.image_type = image_type;
        }

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getHttp_type() {
            return http_type;
        }

        public void setHttp_type(String http_type) {
            this.http_type = http_type;
        }

        public String getHttp_url() {
            return http_url;
        }

        public void setHttp_url(String http_url) {
            this.http_url = http_url;
        }
    }

    public static class PhoneXBean {
        /**
         * image_type : 2
         * image_name : 美男X
         * image_url : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232564979394853.png
         * http_type : 1
         * http_url :
         */

        private String image_type;
        private String image_name;
        private String image_url;
        private String http_type;
        private String http_url;

        public String getImage_type() {
            return image_type;
        }

        public void setImage_type(String image_type) {
            this.image_type = image_type;
        }

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getHttp_type() {
            return http_type;
        }

        public void setHttp_type(String http_type) {
            this.http_type = http_type;
        }

        public String getHttp_url() {
            return http_url;
        }

        public void setHttp_url(String http_url) {
            this.http_url = http_url;
        }
    }
}
