package zhuozhuo.com.zhuo.view.activity;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.ClassChoseAdapter;
import zhuozhuo.com.zhuo.adapter.LocationRecyclerViewAdapter;
import zhuozhuo.com.zhuo.adapter.SpinnerAdapter;
import com.hyphenate.chatuidemo.my.bean.ClassBean;
import zhuozhuo.com.zhuo.constants.LocationRecycleViewConstant;
import zhuozhuo.com.zhuo.contract.ChangeMsgConstract;
import zhuozhuo.com.zhuo.model.ChangeMsgModel;
import zhuozhuo.com.zhuo.presenter.ChangeMsgPresenter;
import zhuozhuo.com.zhuo.util.ToastUtils;
import com.hyphenate.chatuidemo.widget.SlidingLetter;

public class LocationChangeActivity extends BaseActivity<ChangeMsgModel,ChangeMsgPresenter> implements LocationRecyclerViewAdapter.OnItemOnClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener, ChangeMsgConstract.View {
    static final String[] letters = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private RelativeLayout rl_left;
    private FrameLayout fl_right;
    String location1 = "";//学校
    String location2 = "";//学院
    String location3 = "大一";//年级
    private RecyclerView rv;
    private boolean isFirst=false;

    //声明控件
    StickyListHeadersListView stickyList;
    EditText et_search;
    SlidingLetter slidingLetter;
    TextView tv_letter;

    //学院数据源
    List<ClassBean> data = new ArrayList<>();
    List<Boolean> flags = new ArrayList<>();
    //年级数据源
    List<String> grade = new ArrayList<>();
    Handler handler = new Handler();
    /**
     * 用于搜索的字符串
     */
    String searchStr;

    ClassChoseAdapter classChoseAdapter;
    private Spinner location_spinner;
    private LocationRecyclerViewAdapter adapter;
    private TextView tv_msg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_location_change;
    }

    @Override
    public void initPresenter() {
mPresenter.setVM(mModel,this);
    }


    @Override
    public void initView() {
        isFirst=getIntent().getBooleanExtra("isFirst",false);
        grade.add("大一");
        grade.add("大二");
        grade.add("大三");
        grade.add("大四");
        flags.addAll(LocationRecycleViewConstant.flags);
        rl_left = (RelativeLayout) findViewById(R.id.rl_left_location);
        if (isFirst){
            rl_left.setVisibility(View.GONE);
        }
        fl_right = (FrameLayout) findViewById(R.id.fl_right_location);
        stickyList = (StickyListHeadersListView) findViewById(R.id.stickyList);
        et_search = (EditText) findViewById(R.id.et_search);
        slidingLetter = (SlidingLetter) findViewById(R.id.slidingLetter);
        tv_letter = (TextView) findViewById(R.id.tv_letter);
        tv_msg = (TextView)findViewById(R.id.tv_msg);
        tv_msg.setText("未选择");

        location_spinner = (Spinner) findViewById(R.id.location_spinner);
        location_spinner.setAdapter(new SpinnerAdapter(this, grade));
        //初始化adapter
        classChoseAdapter = new ClassChoseAdapter(this, data);
        //设置adapter
        stickyList.setAdapter(classChoseAdapter);

        initRecycleView();

        //设置点击监听
        setItemClickListener();
        //设置搜索监听
        setSearchListener();


    }

    private void initRecycleView() {
        adapter = new LocationRecyclerViewAdapter(this, LocationRecycleViewConstant.schoolPhoto, LocationRecycleViewConstant.schoolName, flags, this);
        rv = (RecyclerView) findViewById(R.id.rv_location);
        //创建布局管理器
        StaggeredGridLayoutManager glm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rv.setLayoutManager(glm);
        rv.setAdapter(adapter);
    }


    private void setSearchListener() {
        //输入框 文本变化监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                searchStr = s.toString();
                //文本发生改变后，进行搜索
                //取消前面的搜索条件
                handler.removeCallbacks(searchRun);
                //发一个新的搜索条件
                handler.postDelayed(searchRun, 200);
            }
        });
    }

    Runnable searchRun = new Runnable() {
        @Override
        public void run() {
            //进行搜索
            classChoseAdapter.search(searchStr);
        }
    };

    private void setItemClickListener() {
        location_spinner.setOnItemSelectedListener(this);
        rl_left.setOnClickListener(this);
        fl_right.setOnClickListener(this);
        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到数据源
                ClassBean bean = data.get(position);
                location2 = bean.getClassName();
                tv_msg.setText(location1+","+location2+","+location3);
            }
        });

        //侧滑字母监听
        slidingLetter.setLetterClickListener(new SlidingLetter.LetterClick() {
            @Override
            public void letterClick(int index, String letter) {
                //显示中间的大字母
                tv_letter.setText(letter);
                tv_letter.setVisibility(View.VISIBLE);

//                Toast.makeText(CityChoseActivity.this, letter, Toast.LENGTH_LONG).show();
                //根据字母，计算ListView中的数据的首字母为当前字母的第一个数据的位置
                int poistion = getPostion(letter);
                //ListView跳转到指定位置
                stickyList.setSelection(poistion);
            }

            @Override
            public void up() {
                //手指抬起时
                //中间大字母消失
                tv_letter.setVisibility(View.GONE);
            }
        });

    }

    /**
     * 根据字母，得到首字母为指定字母的首个数据的位置
     *
     * @param letter
     * @return
     */
    private int getPostion(String letter) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getFirstLetter().equals(letter)) {
                return i;
            }
        }
        return 0;
    }


    public List<ClassBean> getClassList(String s) {
        List<ClassBean> data = new ArrayList<>();
        try {
            //解析JSONObject
            JSONObject object = new JSONObject(s);
            LogUtils.logd("citiesObject:" + object.toString());
            // A-Z解析
            //从字母数组中，取出每个字母，
            for (int i = 0; i < letters.length; i++) {
                //从JSON中，按字母取出每个JSONArray，循环解析SJONArray
                JSONArray arr = object.optJSONArray(letters[i]);
                if (arr != null) {
                    LogUtils.logd("JSONArray:" + arr.toString());
                    int len = arr.length();
                    for (int j = 0; j < len; j++) {
                        JSONObject obj = arr.getJSONObject(j);
                        //创建CityBean
                        ClassBean bean = new ClassBean(letters[i], i, obj.getString("name"), obj.getString("namePY"));
                        //添加到集合中
                        data.add(bean);
                    }
                }

            }
        } catch (Exception e) {
            LogUtils.logd(e.toString());
        }
        return data;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_left_location://返回
                finish();
                break;
            case R.id.fl_right_location://保存信息
                if (location1 == "") {
                    ToastUtils.showToast("学校为空");
                    return;
                } else {
                    if (location2 == "") {
                        ToastUtils.showToast("学院为空");
                        return;
                    } else if (location3 == "") {
                        ToastUtils.showToast("年级为空");
                        return;
                    }
                }
                mPresenter.changeMsg("location", location1 + "," + location2 + "," + location3);
                break;
        }
    }

    @Override
    public void onItemOnClick(View view, int pos) {
        for (int i = 0; i < flags.size(); i++) {
            if (i == pos) {
                flags.set(i, true);
            } else {
                flags.set(i, false);
                ;
            }
        }
        adapter.notifyDataSetChanged();
        if (pos == 0) {
            location1 = "深大";
            location2="";
            data = getClassList(LocationRecycleViewConstant.class1.get(0));
            classChoseAdapter.setAllData(data);
        } else {
            location1 = "深职";
            location2="";
            data = getClassList(LocationRecycleViewConstant.class1.get(1));
            classChoseAdapter.setAllData(data);
        }
        tv_msg.setText(location1+","+location2+","+location3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        location3 = grade.get(position);
        tv_msg.setText(location1+","+location2+","+location3);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void changeMsgSucess() {
        finish();
        ToastUitl.showLong("保存成功");
        if (isFirst){
            this.startActivity(MainActivity.class);
        }
    }

    @Override
    public void onBackPressed() {
        if (!isFirst){
            super.onBackPressed();
        }

    }
}
