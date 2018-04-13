package zhuozhuo.com.zhuo.view.activity;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.util.ArrayList;
import java.util.List;

import li.com.base.baseuntils.ToastUitl;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.InviteListAdapter;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import zhuozhuo.com.zhuo.contract.InviteConstract;
import zhuozhuo.com.zhuo.model.InviteModel;
import zhuozhuo.com.zhuo.presenter.InvitePresenter;

public class InviteActivity extends BaseActivity<InviteModel, InvitePresenter> implements InviteConstract.View {

    private PullToRefreshListView pullToRefreshListView;
    private int index = 0;
    private InviteListAdapter addListAdapter;
    private List<UserOnlineBean> dataList = new ArrayList<>();
    private ILoadingLayout endLabels;
    private EaseTitleBar titlebar;
    private boolean isBottom = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    public void initView() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_invite);
        endLabels = pullToRefreshListView.getLoadingLayoutProxy(
                false, true);
        addListAdapter = new InviteListAdapter(dataList, this,mPresenter);
        pullToRefreshListView.setAdapter(addListAdapter);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

        mPresenter.getUserList(index);

        initTitleBar();
    }

    private void addmore() {
        index += 10;
        mPresenter.getUserList(index);
    }

    private void refresh() {
        isBottom = false;
        index = 0;
        dataList.clear();
        mPresenter.getUserList(index);
    }


    protected void initTitleBar() {
        titlebar = (EaseTitleBar) findViewById(R.id.invite_titlebar);
        titlebar.setTitle("添加队友");
        titlebar.setLeftImageResource(R.drawable.ease_mm_title_back);
        titlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
    public void returnUserList(List<UserOnlineBean> data) {
        if (data == null || data.size() == 0) {
            pullToRefreshListView.onRefreshComplete();
            isBottom = true;
        } else {
            dataList.addAll(data);
            addListAdapter.notifyDataSetChanged();
            pullToRefreshListView.onRefreshComplete();
        }
    }

    @Override
    public void inviteSucess() {
        ToastUitl.showLong("邀请已成功发送");
    }
}
