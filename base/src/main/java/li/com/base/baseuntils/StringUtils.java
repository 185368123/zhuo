package li.com.base.baseuntils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class StringUtils {
    //将字符串数组转化成逗号分隔的字符串
    public static String arryToString(List<String> list) {
        if (list != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size()) {
                    sb.append(list.get(i));
                } else {
                    sb.append(list.get(i) + ",");
                }
            }
            return sb.toString();
        }
        return null;
    }
}
