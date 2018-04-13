package zhuozhuo.com.zhuo.bean;

/**
 * Created by Administrator on 2018/1/4.
 */

public class ClassBean {
    //首字母
    private String firstLetter;
    //分类id
    private int letterId;

    private String className;

    private String classNamePY;
    //是否被选中
    private boolean flag;
    public ClassBean(String letter, int id, String className,String classNamePY) {
        this.firstLetter=letter;
        this.letterId=id;
        this.className=className;
        this.classNamePY=classNamePY;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public int getLetterId() {
        return letterId;
    }

    public String getClassName() {
        return className;
    }

    public String getClassNamePY() {
        return classNamePY;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return "ClassBean{" +
                "firstLetter='" + firstLetter + '\'' +
                ", letterId=" + letterId +
                ", className='" + className + '\'' +
                ", classNamePY='" + classNamePY + '\'' +
                '}';
    }
}
