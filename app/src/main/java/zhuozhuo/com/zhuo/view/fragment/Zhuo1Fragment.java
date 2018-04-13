package zhuozhuo.com.zhuo.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import li.com.base.basesinglebean.MatchPersonBean;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.constract.InitializationConstract;
import com.hyphenate.chatuidemo.my.bean.HundredBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.model.InitializationModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;
import com.hyphenate.chatuidemo.my.presenter.InitializationPresenter;
import com.hyphenate.chatuidemo.provider.UserInfoProvider;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.hyphenate.easeui.events.RxBusConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import li.com.base.base.BaseFragment;
import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.baseuntils.LogUtils;
import li.com.base.basewidget.LoadingTip;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1ListViewAdapter;
import zhuozhuo.com.zhuo.adapter.Zhuo1RecycleAdapter;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentPresenter;
import zhuozhuo.com.zhuo.util.CountDownUtils;
import zhuozhuo.com.zhuo.view.MatchInterface;
import zhuozhuo.com.zhuo.view.activity.MainActivity;
import zhuozhuo.com.zhuo.widget.CircleImageView;


/**
 * Created by Administrator on 2017/1/16.
 */
public class Zhuo1Fragment extends BaseFragment<Zhuo1FragmentPresenter,Zhuo1FragmentModel> implements View.OnClickListener, MatchInterface, InitializationConstract.View, Zhuo1FragmentConstract.View {

    // 要申请的权限
    private String[] permissions3 = {Manifest.permission.CAMERA};
    protected String[] permissions2 = { Manifest.permission.RECORD_AUDIO};
    protected String str;

    public final int CHAT = 10010;
    PullToRefreshListView pulllist_zhuo1;
    private ImageView iv_cancle1;
    private ImageView iv_cancle;
    private LinearLayout layout;
    private ImageView iv;
    private AnimationDrawable drawable;
    private Chronometer chronometer;


    int reclen1 = 0;//深大倒计时时间
    int reclen2 = 0;//深职倒计时时间
    boolean flag1 = true;//深大是否刷新倒计时
    boolean flag2 = true;//深职是否刷新倒计时
    int[] picId = {R.drawable.go_blue, R.drawable.go_green, R.drawable.go_red, R.drawable.go_yellow};
    private List<SingleChooseBean> list;
    private Zhuo1ListViewAdapter adapter;
    private MainActivity activity;
    private TextView tv_unread;
    private FrameLayout frameLayout;
    private Button button_skip;
    private TextView button_receive;
    private CircleImageView circleImageView;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private LinearLayout layout_user;
    private ImageView iv_animation;
    private AnimationDrawable animationDrawable;
    private TextView group_tv1;
    private TextView group_tv2;
    private TextView group_tv3;
    private TextView group_tv4;
    private TextView group_tv5;
    private TextView group_tv6;
    private TextView group_tv7;
    private TextView group_tv8;
    private Button group_bt1;
    private String groupID;
    private String hundred_id;
    private String userName;
    private String groupID1;
    private String hundred_id1;
    private String userName1;
    private String is_member1;
    private Button group_bt2;
    private String groupID2;
    private String hundred_id2;
    private String is_member2;
    private String userName2;
    private CountDownUtils countDownUtils1;
    private InitializationPresenter initializationPresenter;
    private InitializationModel initializationModel;
    private String other_party_id="";
    private RecyclerView rv;
    private List<MatchPersonBean> personBeans=new ArrayList<>();
    private Zhuo1RecycleAdapter recycleAdapter;
    private StaggeredGridLayoutManager glm;
    private TextView tv_service;
    private LoadingTip loadingTip;
    private String choice_id;
    private String user_id;
    private AlertDialog dialog;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_zhuo1_layout;
    }

    @Override
    public void initPresenter() {
    mPresenter.setVM(mModel,this);
    }

    @Override
    protected void initView(View view) {

        initRxManger();

        initializationPresenter = new InitializationPresenter();
        initializationModel = new InitializationModel();
        initializationPresenter.setVM(initializationModel,this);

        initializationPresenter.getSingleStatus();
        mPresenter.getAllMatch();
        activity = (MainActivity) getActivity();


        list = new ArrayList<>();
        adapter = new Zhuo1ListViewAdapter(list, getContext(), picId, this);
        pulllist_zhuo1 = (PullToRefreshListView) view.findViewById(R.id.pulllistview_zhuo1);
        pulllist_zhuo1.setAdapter(adapter);
        mPresenter.getSingleChoose();
        mPresenter.getHundredMsg();//获取白人群的信息
        tv_unread = (TextView) view.findViewById(R.id.tv_unreadnum);
        int num_msg=SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
        if (num_msg > 0) {
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(num_msg + "");
        } else {
            tv_unread.setVisibility(View.INVISIBLE);
        }

        loadingTip = view.findViewById(R.id.loadedTip_zhuo3);
        iv_cancle1 = (ImageView) view.findViewById(R.id.cancle_iv1);
        iv_cancle = (ImageView) view.findViewById(R.id.cancle_iv);
        button_skip = (Button) view.findViewById(R.id.button_skip);
        button_receive = (TextView) view.findViewById(R.id.button_receive);
        group_bt1 = (Button) view.findViewById(R.id.group_bt1);
        group_bt2 = (Button) view.findViewById(R.id.group_bt2);

        circleImageView = (CircleImageView) view.findViewById(R.id.zhuo1_iv);
        tv_service = view.findViewById(R.id.tv_service);
        tv3 = (TextView) view.findViewById(R.id.zhuo1_tv3);
        tv4 = (TextView) view.findViewById(R.id.zhuo1_tv4);
        tv5 = (TextView) view.findViewById(R.id.zhuo1_tv5);
        tv6 = (TextView) view.findViewById(R.id.zhuo1_tv6);
        tv7 = (TextView) view.findViewById(R.id.zhuo1_tv7);
        group_tv1 = (TextView) view.findViewById(R.id.group_tv1);
        group_tv2 = (TextView) view.findViewById(R.id.group_tv2);
        group_tv3 = (TextView) view.findViewById(R.id.group_tv3);
        group_tv4 = (TextView) view.findViewById(R.id.group_tv4);
        group_tv5 = (TextView) view.findViewById(R.id.group_tv5);
        group_tv6 = (TextView) view.findViewById(R.id.group_tv6);
        group_tv7 = (TextView) view.findViewById(R.id.group_tv7);
        group_tv8 = (TextView) view.findViewById(R.id.group_tv8);

        rv = view.findViewById(R.id.zhuo1_recycle);
        layout = (LinearLayout) view.findViewById(R.id.wait);
        layout_user = (LinearLayout) view.findViewById(R.id.zhuo1_user);
        iv = (ImageView) view.findViewById(R.id.iv_wait);
        iv_animation = (ImageView) view.findViewById(R.id.iv_animation);
        animationDrawable = (AnimationDrawable) iv_animation.getDrawable();
        animationDrawable.start();
        drawable = (AnimationDrawable) iv.getDrawable();
        chronometer = (Chronometer) view.findViewById(R.id.time);
        frameLayout = (FrameLayout) view.findViewById(R.id.chat_frame);

        tv_service.setOnClickListener(this);
        frameLayout.setOnClickListener(this);
        iv_cancle1.setOnClickListener(this);
        iv_cancle.setOnClickListener(this);
        button_skip.setOnClickListener(this);
        button_receive.setOnClickListener(this);
        group_bt1.setOnClickListener(this);
        group_bt2.setOnClickListener(this);


        pulllist_zhuo1.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                mPresenter.getSingleChoose();//获取单人匹配条件列表
                mPresenter.getHundredMsg();//获取白人群的信息
                mPresenter.getAllMatch();
            }
        });


        //创建布局管理器
        glm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        rv.setLayoutManager(glm);
        recycleAdapter = new Zhuo1RecycleAdapter(getContext(), personBeans, new Zhuo1RecycleAdapter.OnItemClick() {
            @Override
            public void onItemOnClick(int pos) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", SingleBeans.getInstance().getMatchPersonBeans().get(pos).getR_user_id());
                startActivityForResult(intent, CHAT);
            }
        });
        rv.setAdapter(recycleAdapter);
    }


    public void doClick(String choice_id) {
        this.choice_id=choice_id;
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            str = "拍照";
            // 检查该权限是否已经获取
            int j = ContextCompat.checkSelfPermission(getContext(), permissions3[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (j != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                // showDialogTipUserRequestPermission();
                startRequestPermission(permissions3);
            } else {
                // 检查该权限是否已经获取
                str = "录音";
                int i = ContextCompat.checkSelfPermission(getContext(), permissions2[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    // showDialogTipUserRequestPermission();
                    startRequestPermission(permissions2);
                } else {
                    mPresenter.match_(choice_id);
                }
            }
        } else {
            mPresenter.match_(choice_id);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancle_iv://取消匹配
                mPresenter.cancelMatch();
                countDownUtils1.deletCallBack();
                layout.setVisibility(View.GONE);
                layout_user.setVisibility(View.GONE);
                chronometer.stop();
                initializationPresenter.getSingleStatus();
                drawable.stop();
                break;
            case R.id.cancle_iv1://取消匹配
                mPresenter.cancelMatch();
                layout.setVisibility(View.GONE);
                layout_user.setVisibility(View.GONE);
                chronometer.stop();
                initializationPresenter.getSingleStatus();
                drawable.stop();
                break;
            case R.id.chat_frame:
                activity.showMenu();
                break;
            case R.id.button_skip:
                countDownUtils1.deletCallBack();
                mPresenter.match_(choice_id);
                break;
            case R.id.button_receive://接受这次匹配
                button_receive.setClickable(false);
                button_receive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.skip_button_maincolor_shape));
                mPresenter.accept_(other_party_id,user_id,choice_id);
                break;
            case R.id.group_bt1:
                if (groupID1 != null) {
                    final Intent intent = new Intent(getActivity(), ChatActivity.class);
                    groupID=groupID1;
                    hundred_id=hundred_id1;
                    userName=userName1;
                    intent.putExtra("userId", groupID);
                    intent.putExtra("hundred_id", hundred_id);
                    intent.putExtra("userName", userName);
                    intent.putExtra(com.hyphenate.chatuidemo.Constant.EXTRA_CHAT_TYPE, com.hyphenate.chatuidemo.Constant.CHATTYPE_GROUP);
                    if (is_member1.equals("0")) {
                        mPresenter.joinHundredGroup(hundred_id1);
                    } else {
                        mPresenter.getHundredMsg();//获取白人群的信息
                        startActivity(intent);
                    }
                }
                break;
            case R.id.group_bt2:
                if (groupID1 != null) {
                    final Intent intent2 = new Intent(getActivity(), ChatActivity.class);
                    groupID=groupID2;
                    hundred_id=hundred_id2;
                    userName=userName2;
                    intent2.putExtra("userId", groupID);
                    intent2.putExtra("hundred_id", hundred_id);
                    intent2.putExtra("userName", userName);
                    intent2.putExtra(com.hyphenate.chatuidemo.Constant.EXTRA_CHAT_TYPE, com.hyphenate.chatuidemo.Constant.CHATTYPE_GROUP);
                    if (is_member2.equals("0")) {
                      mPresenter.joinHundredGroup(hundred_id2);
                    } else {
                        mPresenter.getHundredMsg();//刷新百人群的信息
                        startActivity(intent2);
                    }
                }
                break;
            case R.id.tv_service:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId","1");
                startActivityForResult(intent, CHAT);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializationPresenter.getSingleStatus();
    }

    public void timebegin(SingleStatusBean singleStatusBean) {
        LogUtils.logd("singleStatusBean.getTime:"+singleStatusBean.getTime());
        if (singleStatusBean != null) {
                chronometer.setBase(SystemClock.elapsedRealtime() - singleStatusBean.getTime() * 1000);
        }else {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        //启动计时器
        chronometer.start();
    }

    public void gochat() {
        layout.setVisibility(View.GONE);
        chronometer.stop();
        drawable.stop();
        List<UserDB> userDBList = DataSupport.select().where("user_id = ?", user_id).find(UserDB.class);
        if (!(userDBList.size() > 0)) {
            GetUserMsgPresenter getUserMsgPresenter=new GetUserMsgPresenter();
            getUserMsgPresenter.setVM(new GetUserMsgModel(),null);
            getUserMsgPresenter.getUserMsg(UserInfoProvider.getUserID());
        }
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userId", user_id);
        startActivityForResult(intent, CHAT);
        initializationPresenter.getSingleStatus();
    }


    Handler handler1 = new Handler();
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            if (reclen1 > 0) {
                reclen1 = reclen1 - 1;
                int seconds;
                int min = 00;
                int hour = 00;
                seconds = reclen1;
                if (seconds > 60) {
                    min = seconds / 60;
                    seconds = seconds % 60;
                }
                if (min > 60) {
                    hour = min / 60;
                    min = min % 60;
                }
                group_tv5.setText(hour + ":" + min + ":" + seconds);
                handler1.postDelayed(this, 1000);
            } else if (reclen1 == 0) {
                flag1 = true;
                Message message = new Message();
                message.what = 1;
                handlerStop.sendMessage(message);
                mPresenter.getHundredMsg();
                reclen1 = -1;
            } else {
            }
        }
    };


    Handler handler2 = new Handler();
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            if (reclen2 > 0) {
                reclen2 = reclen2 - 1;
                int seconds;
                int min = 00;
                int hour = 00;
                //将毫秒转换成秒
                seconds = reclen2;
                if (seconds > 60) {
                    min = seconds / 60;
                    seconds = seconds % 60;
                }
                if (min > 60) {
                    hour = min / 60;
                    min = min % 60;
                }
                group_tv6.setText(hour + ":" + min + ":" + seconds);
                handler2.postDelayed(this, 1000);
            } else if (reclen2 == 0) {
                flag2 = true;
                Message message = new Message();
                message.what = 2;
                handlerStop.sendMessage(message);
                mPresenter.getHundredMsg();
                reclen2 = -1;
            } else {

            }
        }
    };


    final Handler handlerStop = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    handler1.removeCallbacks(runnable1);
                    break;
                case 2:
                    handler2.removeCallbacks(runnable2);
                    break;
            }
            super.handleMessage(msg);
        }
    };




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activity.refreshConversationListFragment();
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014-06-14 16:09:00"）返回倒计时差
     *
     * @param time
     * @return
     */
    public long data(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        long time_ = 0;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
            long timeend = Integer.valueOf(times);
            long timenow = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
            time_ = timeend - timenow;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time_;
    }

    private void initRxManger() {
        mRxManager = new RxManager();
        mRxManager.on("match_accept", new Action1<String>() {//双方接受匹配
            @Override
            public void call(String s) {
                mPresenter.getAllMatch();
                layout.setVisibility(View.GONE);
                layout_user.setVisibility(View.GONE);
                UserInfoProvider.setHelloFlag(true);//设置第一次进入聊天
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        gochat();
                    }
                },1000);
                if (countDownUtils1 != null) {
                    countDownUtils1.deletCallBack();
                }
            }
        });
        mRxManager.on(RxBusConstants.UPDATE_HUNDRED, new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getHundredMsg();//获取白人群的信息
            }
        });
        mRxManager.on(RxBusConstants.UPDATE_ALL_MATCH, new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getAllMatch();
            }
        });
        mRxManager.on(RxBusConstants.UNREAD_MSG_COUNT, new Action1<Integer>() {//未读消息数量
            @Override
            public void call(Integer unm) {
                int num_msg=SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
                if (num_msg > 0) {
                    tv_unread.setVisibility(View.VISIBLE);
                    tv_unread.setText(num_msg + "");
                } else {
                    tv_unread.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRxManager.on("stateless", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object=new JSONObject(s);
                    if (!DemoHelper.getInstance().getContactList().containsKey(object.getString("user_id"))) {
                        EMClient.getInstance().chatManager().deleteConversation(object.getString("user_id"), true);
                        activity.refreshConversationListFragment();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mRxManager.on("match", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String userId = object.getString("user_id");
                    String photo = object.getString("photo_link");
                    String name = object.getString("nick_name");
                    String location = object.getString("location");
                    String account = object.getString("account");
                    String sex = object.getString("sex");
                    other_party_id=object.getString("other_party_id");
                    choice_id = object.getString("choice_id");
                    user_id = object.getString("user_id");
                    judge(userId, photo, name, location, account, sex);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void judge(String userId, String photo, String userName, String location, String hobby, String sex) {//第一次匹配成功时候更改UI界面
        chronometer.stop();
        drawable.stop();
        if (userId != null && !userId.equals("")) {
            LogUtils.logd("userId" + userId + ";   name" + userName);

        }
        if (photo != null) {

        }
        if (userName != null) {
         tv7.setText(userName);
        }
        if (!location.equals("")) {
            tv4.setText("信息：" + location);
        }
        if (hobby != null) {
            tv5.setText("日常：" + hobby);
        }
        if (sex != null) {
            if (sex.equals("1")) {
                tv3.setText("男");
            } else {
                tv3.setText("女");
            }
        }
        layout_user.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        button_receive.setClickable(true);
        button_skip.setClickable(true);
        button_receive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.receive_button_maincolor_shape));
        Glide.with(this).load(photo).into(circleImageView);
        tv6.setVisibility(View.VISIBLE);
        countDownUtils1 = new CountDownUtils(tv6, 10 * 1000, new EmptyView() {
            @Override
            public void emptyBack() {//倒计时跳过这次匹配
                mPresenter.match_(choice_id);
            }
        });
        countDownUtils1.start();
    }

    // 开始提交请求权限
    public void startRequestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(getActivity(), permissions, 321);
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
        pulllist_zhuo1.onRefreshComplete();
    }

    @Override
    public void returnSingleStatus(SingleStatusBean singleStatusBean) {
        if (singleStatusBean != null) {
                if (singleStatusBean.getMatch_status().equals("0")) {
                    layout.setVisibility(View.GONE);
                    layout_user.setVisibility(View.GONE);
                    drawable.stop();
                } else if (singleStatusBean.getMatch_status().equals("1")) {
                    layout.setVisibility(View.VISIBLE);
                    layout_user.setVisibility(View.GONE);
                    timebegin(singleStatusBean);
                    drawable.start();
                }
        }
    }

    @Override
    public void returnSingleChooseDetaile(List<SingleChooseDetailBean> singleChooseBeans) {

    }

    @Override
    public void returnSingleChoose(List<SingleChooseBean>  data) {
        list.clear();
        if (data.size()>0){
            list.addAll(data);
        }
        adapter.notifyDataSetChanged();
        pulllist_zhuo1.onRefreshComplete();
    }

    @Override
    public void cancleMatchSucess() {
        initializationPresenter.getSingleStatus();
    }


    @Override
    public void returnHundredMsg(List<HundredBean> list) {
        for (int i = 0; i < list.size(); i++) {
            HundredBean bean=list.get(i);
            String[] member_ids=bean.getMember_ids().split(",");
            for (int j = 0; j < member_ids.length; j++) {
                UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(member_ids[j]);
            }
        }
        HundredBean dataBean1 = list.get(0);
        HundredBean dataBean2 = list.get(1);
        //第一个百人群的UI显示
        group_tv1.setText(dataBean1.getGroup_name());
        group_tv2.setText(dataBean1.getMember_count() - 2 + "/" + (dataBean1.getMaxuser() - 2));
        reclen1 = (int) data(dataBean1.getEnd_time());
        if (flag1) {
            handler1.postDelayed(runnable1, 1000);
            flag1 = false;
        }
        groupID1 = dataBean1.getGroup_id();
        this.hundred_id1 = dataBean1.getHundred_id();
        this.is_member1 = dataBean1.getIs_member();
        userName1 = dataBean1.getGroup_name();
        if (dataBean1.getIs_close().equals("1")) {
            group_tv5.setVisibility(View.GONE);
            group_tv7.setVisibility(View.VISIBLE);
            group_bt1.setVisibility(View.GONE);
        } else {
            group_tv5.setVisibility(View.VISIBLE);
            group_tv7.setVisibility(View.GONE);
            group_bt1.setVisibility(View.VISIBLE);
        }
        if (is_member1.equals("0")) {
            group_bt1.setText("加入");
        } else {
            group_bt1.setText("已加入");
        }

        //第二个百人群的UI显示
        group_tv3.setText(dataBean2.getGroup_name());
        group_tv4.setText(dataBean2.getMember_count() - 2 + "/" + (dataBean2.getMaxuser() - 2));
        reclen2 = (int) data(dataBean2.getEnd_time());
        if (flag2) {
            handler2.postDelayed(runnable2, 1000);
            flag2 = false;
        }
        groupID2 = dataBean2.getGroup_id();
        this.hundred_id2 = dataBean2.getHundred_id();
        this.is_member2 = dataBean2.getIs_member();
        userName2 = dataBean2.getGroup_name();
        if (dataBean2.getIs_close().equals("1")) {
            group_tv6.setVisibility(View.GONE);
            group_tv8.setVisibility(View.VISIBLE);
            group_bt2.setVisibility(View.GONE);
        } else {
            group_tv6.setVisibility(View.VISIBLE);
            group_tv8.setVisibility(View.GONE);
            group_bt2.setVisibility(View.VISIBLE);
        }
        if (is_member2.equals("0")) {
            group_bt2.setText("加入");
        } else {
            group_bt2.setText("已加入");
        }
    }

    @Override
    public void match_SucessReturn() {
        initializationPresenter.getSingleStatus();
    }

    @Override
    public void joinSucess() {
        final Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userId", groupID);
        intent.putExtra("hundred_id", hundred_id);
        intent.putExtra("userName", userName);
        intent.putExtra(com.hyphenate.chatuidemo.Constant.EXTRA_CHAT_TYPE, com.hyphenate.chatuidemo.Constant.CHATTYPE_GROUP);
        startActivity(intent);
        mPresenter.getHundredMsg();//获取白人群的信息
    }

    @Override
    public void returnAllMatch(List<MatchPersonBean> data) {
        if (data.size()>0){
            rv.setVisibility(View.VISIBLE);
            recycleAdapter.update(data);
        }else {
            rv.setVisibility(View.GONE);
        }

    }


    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(getContext())
                .setTitle("读写权限不可用")
                .setMessage("请在-应用设置-权限-中，打开拍照和录音权限")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

}
