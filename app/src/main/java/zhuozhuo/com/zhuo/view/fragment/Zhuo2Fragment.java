package zhuozhuo.com.zhuo.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.my.bean.GroupChoiceTitle;
import com.hyphenate.chatuidemo.my.okhttp.ToastUtils;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import li.com.base.base.BaseFragment;
import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;
import li.com.base.basewidget.LoadingTip;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo2ListAdapter;
import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import li.com.base.basesinglebean.GroupStatusBean;
import zhuozhuo.com.zhuo.contract.Zhuo2FragmentConstract;
import zhuozhuo.com.zhuo.model.Zhuo2FragmentModel;
import zhuozhuo.com.zhuo.presenter.Zhuo2FragmentPresenter;
import zhuozhuo.com.zhuo.util.Zhuo2ViewHoldUtil;
import zhuozhuo.com.zhuo.view.MatchInterface;
import zhuozhuo.com.zhuo.view.activity.InviteActivity;
import zhuozhuo.com.zhuo.view.activity.MainActivity;
import zhuozhuo.com.zhuo.widget.CircleImageView;

/**
 * Created by Administrator on 2017/1/16.
 */
public class Zhuo2Fragment extends BaseFragment<Zhuo2FragmentPresenter,Zhuo2FragmentModel> implements View.OnClickListener, MatchInterface, Zhuo2FragmentConstract.View {

    public static final int GoChat=8990;
    public static final int Zhuo2FragmentCode = 10086;

    private TextView tv4;
    private CircleImageView iv1;
    private CircleImageView iv2;
    private CircleImageView iv3;
    private CircleImageView iv4;
    private CircleImageView iv5;
    private CircleImageView[] ivs;
    private FrameLayout frameLayout;
    private RelativeLayout rl;
    private AlertDialog dialog;
    private List<GroupChoicesBean> list;
    private Zhuo2ListAdapter adapter;
    private PullToRefreshListView lv;
    private ILoadingLayout endLabels;
    private LinearLayout wait_ll;
    private Chronometer chronometer;
    private Button button_cancle;
    private ImageView iv_group;
    private AnimationDrawable drawable;
    private android.app.AlertDialog alertDialog;
    private EditText editText;
    private LoadingTip loadingTip;

    @Override
    protected int getLayoutResource() {
        return R.layout.zhuo2fragment_layout;
    }

    @Override
    public void initPresenter() {
      mPresenter.setVM(mModel,this);
    }

    protected void initView(View view) {
        initRxBus();
        list = new ArrayList<>();
        mPresenter.getGroupChoices();
        mPresenter.getGroupStatus();

        loadingTip = view.findViewById(R.id.loadedTip_zhuo2);
        iv1 = (CircleImageView) view.findViewById(R.id.iv1);
        iv2 = (CircleImageView) view.findViewById(R.id.iv2);
        iv3 = (CircleImageView) view.findViewById(R.id.iv3);
        iv4 = (CircleImageView) view.findViewById(R.id.iv4);
        iv5 = (CircleImageView) view.findViewById(R.id.iv5);
        ivs = new CircleImageView[]{iv1, iv2, iv3, iv4, iv5};
        rl = (RelativeLayout) view.findViewById(R.id.rl_send);
        frameLayout = (FrameLayout) view.findViewById(R.id.chat_frame2);
        lv = (PullToRefreshListView) view.findViewById(R.id.lv_zhuo2);
        wait_ll = (LinearLayout) view.findViewById(R.id.wait_group);
        chronometer = (Chronometer) view.findViewById(R.id.time_group);
        iv_group = (ImageView) view.findViewById(R.id.iv_wait_group);
        drawable = (AnimationDrawable) iv_group.getDrawable();
        button_cancle = (Button) view.findViewById(R.id.cancle_button_group);
        adapter = new Zhuo2ListAdapter(list, getContext(), this);
        lv.setAdapter(adapter);
        endLabels = lv.getLoadingLayoutProxy(false, true);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
                endLabels.setRefreshingLabel("正在刷新...");// 刷新时
                endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                refresh();//刷新列表
            }
        });
        button_cancle.setOnClickListener(this);
        rl.setOnClickListener(this);
        frameLayout.setOnClickListener(this);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);

    }

    private void initRxBus() {
        mRxManager=new RxManager();
        mRxManager.on("teamlist", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getGroupStatus();
            }
        });
        mRxManager.on("groupmatch", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object=new JSONObject(s);
                    for (int i = 0; i < list.size(); i++) {
                        if (object.getString("choice_id").equals(list.get(i).getChoice_id())){
                            GroupChoiceTitle.getGroupChoiceTitle().setTitle(list.get(i).getChoice_title());
                            GroupChoiceTitle.getGroupChoiceTitle().setValue(list.get(i).getValue().split(",")[new Integer(object.getString("choice_number"))-1]);
                        }
                    }
                    setCancleMatchView();
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId",object.getString("group_id"));
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                    startActivityForResult(intent,GoChat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mRxManager.on("group_remark", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.groupRemark(s,true);
            }
        });
        mRxManager.on("group_remark_", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.groupRemark(s,false);
            }
        });
        mRxManager.on("leave_team", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.leaveTeam();
            }
        });
        mRxManager.on("GroupChange", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getGroupStatus();
            }
        });
    }

    private void refresh() {
        list.clear();
        Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().clearViewHold();
       mPresenter.getGroupChoices();
       mPresenter.getGroupStatus();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_send:
                Intent intent = new Intent(getContext(), InviteActivity.class);
                startActivityForResult(intent, Zhuo2FragmentCode);
                break;
            case R.id.chat_frame2:
                MainActivity activity = (MainActivity) getActivity();
                activity.showMenu();
                break;
            case R.id.tv4:
                dialog = new AlertDialog.Builder(getContext())
                        .setTitle("温馨提示")
                        .setCancelable(false)
                        .setMessage("是否确定退出组队")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                                if (SingleBeans.getInstance().getGroupStatusBeans().getGroup_match_status().equals("2")){
                                    editText = new EditText(getContext());
                                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                    alertDialog = new android.app.AlertDialog.Builder(getContext())
                                            .setTitle("给对方些评语吧")
                                            .setView(editText)
                                            .setNegativeButton("滚,不评了", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    mPresenter.groupRemark("",false);
                                                    mPresenter.leaveTeam();
                                                }
                                            })
                                            .setPositiveButton("提交评论", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    String comment=editText.getText().toString();
                                                    if (TextUtils.isEmpty(comment)){
                                                        ToastUitl.showLong("评论不能为空！");
                                                        return;
                                                    }else {
                                                        mPresenter.groupRemark(comment,true);
                                                        mPresenter.leaveTeam();
                                                    }
                                                }
                                            })
                                            .create();
                                    alertDialog.show();
                                }else {
                                  mPresenter.leaveTeam();  //退出组队
                                }

                            }
                        }).create();
                dialog.show();
                break;
            case R.id.cancle_button_group:
                mPresenter.cancleGroupMatch();
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       mPresenter.getGroupStatus();
    }

    @Override
    public void doClick(String id) {
        mPresenter.getGroupStatus();
        String choiceId = list.get(new Integer(id)).getChoice_id();
        int choice_number = Zhuo2ViewHoldUtil.getZhuo2ViewHoldUtil().getViewHold(new Integer(id)).getRadioButtonIndex();
        String group_name = list.get(new Integer(id)).getChoice_name();
        GroupChoiceTitle.getGroupChoiceTitle().setTitle(list.get(new Integer(id)).getChoice_title());
        GroupChoiceTitle.getGroupChoiceTitle().setValue(list.get(new Integer(id)).getValue().split(",")[choice_number-1]);
        LogUtils.logd("组队匹配选择条件choiceId:" + choiceId + ",choice_number:" + choice_number + ",group_name:" + group_name);
        if (SingleBeans.getInstance().getGroupStatusBeans().getGroup_match_status().equals("3")){
            ToastUtils.showToast("只有队长才能开始匹配");
        }else if(SingleBeans.getInstance().getGroupStatusBeans().getGroup_match_status().equals("2")){
            ToastUtils.showToast("上一个群未完成");
        }else {
            mPresenter.beginGroupMatch(choiceId, group_name, choice_number);
        }
    }

    @Override
    public void doHundreadClick(String string) {

    }

    public void setMatchWaitView() {
        wait_ll.setVisibility(View.VISIBLE);
        //设置开始时间
        chronometer.setBase(SystemClock.elapsedRealtime());
        //启动计时器
        chronometer.start();
        drawable.start();
    }

    public void setCancleMatchView() {
        wait_ll.setVisibility(View.GONE);
        chronometer.stop();
        drawable.stop();
    }




    @Override
    public void showLoading(String title) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        loadingTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadingTip.setTips(msg);
        lv.onRefreshComplete();
    }

    @Override
    public void returnGroupStatus(GroupStatusBean statusBean) {
        for (int i = 0; i < ivs.length; i++) {//头像全部初始化
            ivs[i].setImageResource(R.drawable.teamlog);
        }
        if (statusBean.getPhoto_link() == null||statusBean.getPhoto_link().equals("") ) {
            tv4.setVisibility(View.GONE);
            setCancleMatchView();
        } else {
            String[] strings = statusBean.getPhoto_link().split(",");//队伍头像
            String group_match_status = statusBean.getGroup_match_status();//组队状态
            tv4.setVisibility(View.VISIBLE);
            for (int i = 0; i < strings.length; i++) {//设置队伍头像
                Glide.with(this).load(strings[i]).into(ivs[i]);
            }
            if (group_match_status.equals("4") || group_match_status.equals("3")) {//43都是无匹配状态，4是队长状态，3是队员状态
                setCancleMatchView();
            } else if (group_match_status.equals("1")) {//1为等待匹配状态
                setMatchWaitView();
            }else if (group_match_status.equals("2")) {//2为匹配成功
                //匹配成功的UI展示
            }
        }
    }

    @Override
    public void returnGroupChoices(List<GroupChoicesBean> list) {
        if (list == null || list.size() == 0) {
            lv.onRefreshComplete();
        } else {
            this.list.addAll(list);
            adapter.notifyDataSetChanged();
            lv.onRefreshComplete();
        }
    }

    @Override
    public void beginGroupMatchSucess() {
        setMatchWaitView();
    }

    @Override
    public void groupMatchSucess(String group_id) {
        setCancleMatchView();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userId",group_id);
        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
        startActivityForResult(intent,GoChat);
    }

    @Override
    public void cancleGroupMatchSucess() {
        setCancleMatchView();
    }

    @Override
    public void remarkSucess() {
        mPresenter.getGroupStatus();
    }

    @Override
    public void leaveTeamSucess() {
        mPresenter.getGroupStatus();
    }
}
