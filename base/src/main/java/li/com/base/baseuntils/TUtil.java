package li.com.base.baseuntils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Administrator on 2018/2/27.
 */

public class TUtil {
    public static <T> T getT(Object o,int i ){
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
