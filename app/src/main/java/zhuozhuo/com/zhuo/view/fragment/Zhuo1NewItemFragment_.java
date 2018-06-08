package zhuozhuo.com.zhuo.view.fragment;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.my.bean.HundredBean;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.ArrayList;
import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1NewItemListAdapter;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentConstract;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentModel;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentNewModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentNewPresenter;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentPresenter;
import zhuozhuo.com.zhuo.view.activity.RecordVideoActivity;
import zhuozhuo.com.zhuo.view.activity.VideoSelectActivity;

/**
 * Created by Administrator on 2018/6/6.
 */

public class Zhuo1NewItemFragment_ extends BaseFragment<Zhuo1FragmentNewPresenter, Zhuo1FragmentNewModel> implements Zhuo1NewItemListAdapter.ImageOnClick, Zhuo1FragmentNewConstract.View {

    private PullToRefreshListView listView;
    private List<SingleChooseBean> data = new ArrayList<>();
    private Zhuo1NewItemListAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.zhuo1newfragmentitem2_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView(View view) {
        listView = view.findViewById(R.id.pulllistview_zhuo1_item);
        adapter = new Zhuo1NewItemListAdapter(getContext(), data, this);
        listView.setAdapter(adapter);

        mPresenter.getSingleChoose();

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mPresenter.getSingleChoose();
            }
        });

    }

    @Override
    public void onImageWaitClick(int posion) {
        if(UserInfoProvider.getUserVideo()!=null&&!UserInfoProvider.getUserVideo().equals("")){
            mPresenter.matchBegin(data.get(posion).getChoice_id(), posion == 0 ? "1" : "2", "66", "", "");
        }else {
            // 创建构建器
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // 设置参数
            builder.setMessage("你还没有录制视频，是否现在去录制？")
                    .setNeutralButton("暂不设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("录制视频", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(RecordVideoActivity.class);
                }
            }).setPositiveButton("选择视频", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(VideoSelectActivity.class);
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void onImageCancleClick(int posion) {
        mPresenter.matchCancle(data.get(posion).getChoice_id(), "66");
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
    public void returnSingleChooseDetaile(List<SingleChooseDetailBean> singleChooseBeans) {

    }

    @Override
    public void returnSingleChoose(List<SingleChooseBean> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            adapter.notifyDataSetChanged();
        }
        listView.onRefreshComplete();

    }

    @Override
    public void matchBeginSucess() {
        //adapter.getViewSaveList().get(2).iv_go.performClick();
    }

    @Override
    public void matchAcceptSucess() {

    }

    @Override
    public void matchCancle() {

    }

    @Override
    public void returnAllMatch(List<MatchPersonBean> data) {

    }

    @Override
    public void returnSingleStatus(SingleStatusBean singleStatusBean) {

    }
}
