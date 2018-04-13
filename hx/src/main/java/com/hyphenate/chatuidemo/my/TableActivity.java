package com.hyphenate.chatuidemo.my;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.bean.RateBean;
import com.hyphenate.chatuidemo.my.constract.TableActivityConstract;
import com.hyphenate.chatuidemo.my.model.TableActivityModel;
import com.hyphenate.chatuidemo.my.presenter.TableActivityPresenter;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.rmondjone.locktableview.DisplayUtil;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;
import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity implements TableActivityConstract.View {
    private RelativeLayout rl;
    private LockTableView mLockTableView;
    private ArrayList<ArrayList<String>> mTableDatas;
    private ArrayList<String> mfristData;
    private ArrayList<String> mineData;
    private EaseTitleBar titlebar;
    private TableActivityPresenter mPresenter;
    private TableActivityModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        rl = (RelativeLayout) findViewById(R.id.rl_table);
        initDisplayOpinion();
        String group_id = getIntent().getStringExtra("group_id");

        mPresenter = new TableActivityPresenter();
        mModel = new TableActivityModel();
        mPresenter.setVM(mModel,this);
        mPresenter.getGroupRate(group_id);

        titlebar = (EaseTitleBar) findViewById(R.id.table_titlebar);
        titlebar.setTitle("得分表");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
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
    public void returnGroupRate(List<RateBean> rateBeans) {
        //构造数据
        mTableDatas = new ArrayList<ArrayList<String>>();
        mfristData = new ArrayList<String>();
        mineData = new ArrayList<String>();
        mfristData.add("总排名");
        mfristData.add("昵称");
        mfristData.add("序号");
        if (rateBeans.size() > 0) {
            for (int i = 0; i < rateBeans.get(0).getTask().size(); i++) {
                mfristData.add("活动" + (i + 1));
                mfristData.add("名次");
            }
            mTableDatas.add(mfristData);
            for (int i = 0; i < rateBeans.size(); i++) {
                RateBean dataBean = rateBeans.get(i);
                ArrayList<String> mRowDatas = new ArrayList<String>();
                if (!dataBean.getUid().equals("000")) {
                    mRowDatas.add(dataBean.getRate() + "");
                    mRowDatas.add(dataBean.getNick_name());
                    mRowDatas.add(dataBean.getUid());
                    for (int j = 0; j < dataBean.getTask().size(); j++) {
                        mRowDatas.add(dataBean.getTask().get(j).getOriscore());
                        mRowDatas.add(dataBean.getTask().get(j).getTaskrate() + "");
                    }
                    mTableDatas.add(mRowDatas);
                }
                if (dataBean.getUser_id() == Integer.valueOf(UserInfoProvider.getUserID())) {
                    mineData.clear();
                    mineData.add(dataBean.getRate() + "");
                    mineData.add(dataBean.getNick_name());
                    mineData.add(dataBean.getUid());
                    for (int j = 0; j < dataBean.getTask().size(); j++) {
                        if (dataBean.getTask().get(j).getOriscore() == null || dataBean.getTask().get(j).getOriscore().equals("") || dataBean.getTask().get(j).getOriscore().equals("1000000")) {
                            mineData.add("N/A");
                        } else {
                            mineData.add(dataBean.getTask().get(j).getOriscore());
                        }
                        mineData.add(dataBean.getTask().get(j).getTaskrate() + "");

                    }
                }
            }
        }
        mLockTableView = new LockTableView(this, rl, mTableDatas, mineData);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(100) //列最大宽度
                .setMinColumnWidth(60) //列最小宽度
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(60)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setFristRowBackGroudColor(R.color.m_color4)//表头背景色
                .setTableHeadTextColor(R.color.white)//表头字体颜色
                .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                .setNullableString("N/A") //空值替换值
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }
}
