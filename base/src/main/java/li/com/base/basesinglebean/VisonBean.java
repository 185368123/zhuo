package li.com.base.basesinglebean;

/**
 * Created by Administrator on 2018/4/4.
 */

public class VisonBean {


    /**
     * error_code : 0
     * msg : 版本更新
     * android_version : 1.0.8
     * android_strong :
     * android_url : http://a.app.qq.com/o/simple.jsp?pkgname=zhuozhuo.com.zhuo
     * data : {"phone":{"image_type":"1","image_name":"美男","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head1523256467199012.png","http_type":"1","http_url":""},"phone_x":{"image_type":"2","image_name":"美男X","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232564979394853.png","http_type":"1","http_url":""}}
     */

    private int error_code;
    private String msg;
    private String android_version;
    private String android_strong;
    private String android_url;
    private DataBean data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * phone : {"image_type":"1","image_name":"美男","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head1523256467199012.png","http_type":"1","http_url":""}
         * phone_x : {"image_type":"2","image_name":"美男X","image_url":"http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15232564979394853.png","http_type":"1","http_url":""}
         */

        private PhoneBean phone;
        private PhoneXBean phone_x;

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
             * image_url : http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head1523256467199012.png
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
}
