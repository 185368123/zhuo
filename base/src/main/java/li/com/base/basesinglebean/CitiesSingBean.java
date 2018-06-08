package li.com.base.basesinglebean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class CitiesSingBean {

        /**
         * provinces_id : 116
         * provinces_str : A
         * provinces_name : 安徽
         * cities_str : H
         * cities_name : 黄山市
         * airport_name : 屯溪机场
         */

        private String provinces_id;
        private String provinces_str;
        private String provinces_name;
        private String cities_str;
        private String cities_name;
        private String airport_name;

        public String getProvinces_id() {
            return provinces_id;
        }

        public void setProvinces_id(String provinces_id) {
            this.provinces_id = provinces_id;
        }

        public String getProvinces_str() {
            return provinces_str;
        }

        public void setProvinces_str(String provinces_str) {
            this.provinces_str = provinces_str;
        }

        public String getProvinces_name() {
            return provinces_name;
        }

        public void setProvinces_name(String provinces_name) {
            this.provinces_name = provinces_name;
        }

        public String getCities_str() {
            return cities_str;
        }

        public void setCities_str(String cities_str) {
            this.cities_str = cities_str;
        }

        public String getCities_name() {
            return cities_name;
        }

        public void setCities_name(String cities_name) {
            this.cities_name = cities_name;
        }

        public String getAirport_name() {
            return airport_name;
        }

        public void setAirport_name(String airport_name) {
            this.airport_name = airport_name;
        }
}
