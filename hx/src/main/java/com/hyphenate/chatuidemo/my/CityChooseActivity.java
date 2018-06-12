package com.hyphenate.chatuidemo.my;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.adatper.CityChooseAdapter;
import com.hyphenate.chatuidemo.my.bean.ClassBean;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.chatuidemo.widget.SlidingLetter;

import java.util.ArrayList;
import java.util.List;

import li.com.base.basesinglebean.CitiesSingBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class CityChooseActivity extends BaseActivity implements View.OnClickListener {

     private String where;
    static final String[] letters = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private RelativeLayout rl_left;

    private boolean isFirst=false;

    //学院数据源
    List<ClassBean> data = new ArrayList<>();
    Handler handler = new Handler();

    Handler handler_finish=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setResult(RESULT_OK);
            finish();
            return false;
        }
    });

private CityChooseAdapter cityChooseAdapter;
    //声明控件
    StickyListHeadersListView stickyList;
    EditText et_search;
    SlidingLetter slidingLetter;
    TextView tv_letter;
    /**
     * 用于搜索的字符串
     */
    String searchStr;
    TextView tv_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        where=getIntent().getStringExtra("where");
        rl_left = (RelativeLayout) findViewById(R.id.rl_left_setcity);
        stickyList = (StickyListHeadersListView) findViewById(R.id.stickyList_setcity);
        et_search = (EditText) findViewById(R.id.et_search_setcity);
        slidingLetter = (SlidingLetter) findViewById(R.id.slidingLetter_setcity);
        tv_letter = (TextView) findViewById(R.id.tv_letter_setcity);
        tv_msg = (TextView)findViewById(R.id.tv_setcity_title);
        tv_msg.setText("选择城市");

        data=getClassList();

        cityChooseAdapter=new CityChooseAdapter(this,data);

        //设置adapter
        stickyList.setAdapter(cityChooseAdapter);

        //设置点击监听
        setItemClickListener();
        //设置搜索监听
        setSearchListener();
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
            cityChooseAdapter.search(searchStr);
        }
    };

    private void setItemClickListener() {

        rl_left.setOnClickListener(this);
        stickyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //得到数据源
                ClassBean bean = data.get(position);
                if (where.equals("start")){
                    SingleBeans.getInstance().setLocation(bean.getClassName());
                    SingleBeans.getInstance().setLocation_id(bean.getClassID());
                }else if (where.equals("end_1")){
                    SingleBeans.getInstance().setCity1(bean.getClassID());
                    SingleBeans.getInstance().setCity1Name(bean.getClassName());
                }else if (where.equals("end_2")){
                    SingleBeans.getInstance().setCity2(bean.getClassID());
                    SingleBeans.getInstance().setCity2Name(bean.getClassName());
                }else if (where.equals("end_3")){
                    SingleBeans.getInstance().setCity3(bean.getClassID());
                    SingleBeans.getInstance().setCity3Name(bean.getClassName());
                }
             handler_finish.sendMessage(new Message());
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


    public List<ClassBean> getClassList() {
        List<ClassBean> data = new ArrayList<>();
        try {
            // A-Z解析
            //从字母数组中，取出每个字母，
            for (int i = 0; i < letters.length; i++) {
                List<CitiesSingBean>  arr= SingleBeans.getInstance().getCitiesSingBean();
                if (arr != null) {
                    int len = arr.size();
                    for (int j = 0; j < len; j++) {
                        if (arr.get(j).getCities_str().equals(letters[i])){
                            //创建CityBean
                            ClassBean bean = new ClassBean(letters[i], i, arr.get(j).getCities_name(), "AAAAA",arr.get(j).getProvinces_id());
                            //添加到集合中
                            data.add(bean);
                        }
                    }
                }

            }
        } catch (Exception e) {
            LogUtils.logd(e.toString());
        }
        return data;

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_left_setcity) {
            finish();
        }
    }
}
