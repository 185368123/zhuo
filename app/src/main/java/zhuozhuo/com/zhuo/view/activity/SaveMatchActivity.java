package zhuozhuo.com.zhuo.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DBOpenHelp;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.my.TeamMenuActivity;
import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;
import com.hyphenate.chatuidemo.ui.UserProfileActivity;
import com.hyphenate.chatuidemo.widget.BottomDialog;
import com.hyphenate.easeui.provider.PreferenceManager;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

import zhuozhuo.com.zhuo.MainApplication;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.SaveMatchListAdapter;
import zhuozhuo.com.zhuo.contract.SaveMatchConstract;
import zhuozhuo.com.zhuo.model.SaveMatchModel;
import zhuozhuo.com.zhuo.presenter.SaveMatchPresent;

public class SaveMatchActivity extends BaseActivity<SaveMatchModel, SaveMatchPresent> implements SaveMatchConstract.View, SaveMatchListAdapter.BTOnClickLiseten {

    private List<SaveMatchBean> list = new ArrayList<>();
    private ILoadingLayout endLabels;
    private PullToRefreshListView listView;
    private boolean isBottom = false;
    private int page = 1;
    private int page_size = 10;
    private ProgressDialog progressDialog;
    private SaveMatchListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_save_match;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        mPresenter.getSaveMatch(page + "", page_size + "");
        listView = (PullToRefreshListView) findViewById(R.id.lv_savematch);
        adapter = new SaveMatchListAdapter(this, list, this);
        listView.setAdapter(adapter);
        endLabels = listView.getLoadingLayoutProxy(
                false, true);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("正在刷新...");// 刷新时
                endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
                String label = DateUtils.formatDateTime(
                        getApplicationContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);

                refresh();//刷新列表
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (isBottom) {
                    endLabels.setPullLabel("全部加载完毕...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("全部加载完毕...");// 刷新时
                    endLabels.setReleaseLabel("全部加载完毕...");// 下来达到一定距离时，显示的提示
                    addmore();
                } else {
                    endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
                    endLabels.setRefreshingLabel("正在加载...");// 刷新时
                    endLabels.setReleaseLabel("松开立刻加载更多...");// 下来达到一定距离时，显示的提示
                    addmore();//加载更多
                }
            }
        });
    }


    private void addmore() {
        page += 1;
        mPresenter.getSaveMatch(page + "", page_size + "");
    }

    private void refresh() {
        isBottom = false;
        page = 1;
        list.clear();
        mPresenter.getSaveMatch(page + "", page_size + "");

    }

    public void clearSaveClick(View view){
        BottomDialog dialog = BottomDialog.newInstance("是否清空我的保留？", new String[]{"确定"});
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position) {
                    case 0:
                        mPresenter.relieveLine("", "2");
                        break;
                }
            }
        });
    }
    public void back(View v){
        finish();
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
    public void returnSaveMatch(List<SaveMatchBean> saveMatchBeans) {
        if (saveMatchBeans == null || saveMatchBeans.size() == 0) {
            isBottom = true;
        } else {
            list.addAll(saveMatchBeans);
            adapter.notifyDataSetChanged();
        }
        listView.onRefreshComplete();
    }

    @Override
    public void relieveLineSucess() {
       refresh();
    }

    @Override
    public void onClick(final String id) {
        progressDialog = new ProgressDialog(SaveMatchActivity.this);
        String stri = getResources().getString(com.hyphenate.chatuidemo.R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(com.hyphenate.chatuidemo.R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(id, s);
                    DBOpenHelp.getDBOpenHelp().selectByUserId(id);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(com.hyphenate.chatuidemo.R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(com.hyphenate.chatuidemo.R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onLongClick(final String id) {
        BottomDialog dialog = BottomDialog.newInstance("", new String[]{"删除"});
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position) {
                    case 0:
                        mPresenter.relieveLine(id, "1");
                        break;
                }
            }
        });

    }
}
