package zhuozhuo.com.zhuo.view.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.chatuidemo.my.RemarkActivity;
import com.hyphenate.chatuidemo.my.SimpleVideo;
import com.hyphenate.chatuidemo.my.model.GetRandStrModel;
import com.hyphenate.chatuidemo.my.presenter.GetRandStrPresenter;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.hyphenate.easeui.events.RxBusConstants;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import li.com.base.base.BaseFragment;
import li.com.base.base.BaseFragmentAdapter;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1RecycleAdapter;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentNewModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentNewPresenter;
import zhuozhuo.com.zhuo.util.CountDownUtils;
import zhuozhuo.com.zhuo.view.activity.MainActivity;

import static zhuozhuo.com.zhuo.view.fragment.Zhuo1Fragment.CHAT;

/**
 * Created by Administrator on 2018/6/6.
 */

public class Zhuo1NewFragment extends BaseFragment<Zhuo1FragmentNewPresenter, Zhuo1FragmentNewModel> implements View.OnClickListener, Zhuo1FragmentNewConstract.View {

    private ViewPager vp;
    private TextView tv_service;
    private TextView tv_unread;
    private FrameLayout frameLayout_chat;
    private MainActivity activity;
    private BaseFragmentAdapter fragmentAdapter;
    private LinearLayout ll;
    private ImageView iv_cancle;
    private ImageView iv_animation;
    private Chronometer chronometer;
    private AnimationDrawable drawable_wait;
    private RecyclerView rv;
    private Zhuo1RecycleAdapter recycleAdapter;
    private List<MatchPersonBean> personBeans = new ArrayList<>();
    private StaggeredGridLayoutManager glm;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private ImageView iv_cancle1;
    private TextView button_save;
    private Button button_skip;
    private TextView button_receive;
    private LinearLayout layout_user;
    private SimpleVideo simpleVideo;
    private GSYVideoOptionBuilder gsyVideoOption;
    private String useId;
    private String other_party_id = "";
    private CountDownUtils countDownUtils1;
    private String is_status;
    private LinearLayout ll_prize;
    private TextView iv_see_prize;
    private TextView iv_see_prize_msg;
    private TextView tv_prize_msg;
    private LinearLayout ll_prize_show;
    private GetRandStrPresenter randStrPresenter;
    private GetRandStrModel randStrModel;
    private ImageView ll_prize_hide;


    @Override
    protected int getLayoutResource() {
        return R.layout.zhuo1newfragment_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView(View view) {
        mPresenter.getSingleChoose();
        mPresenter.getSingleStatus();
        mPresenter.getAllMatch();
        mPresenter.getAllCities();

        randStrPresenter = new GetRandStrPresenter();
        randStrModel = new GetRandStrModel();
        randStrPresenter.setVM(randStrModel,null);
        randStrPresenter.getRandStr();

        vp = view.findViewById(R.id.vp_zhuo1_new);
        tv_unread = (TextView) view.findViewById(R.id.tv_unread_num);
        tv_service = view.findViewById(R.id.tv_service_new);
        frameLayout_chat = (FrameLayout) view.findViewById(R.id.chat_frame_new);
        rv = view.findViewById(R.id.rv_zhuo1_);
        ll = view.findViewById(R.id.wait_zhuo1_);
        ll_prize_hide = view.findViewById(R.id.ll_prize_hide);
        iv_cancle = view.findViewById(R.id.iv_cancle_zhuo1_);
        iv_cancle1 = (ImageView) view.findViewById(R.id.iv_cancle_zhuo1_2);
        iv_animation = view.findViewById(R.id.iv_wait_zhuo1_);
        drawable_wait = (AnimationDrawable) iv_animation.getDrawable();
        chronometer = view.findViewById(R.id.time_zhuo1_);
        simpleVideo = view.findViewById(R.id.sv_zhuo1_);
        simpleVideo.getBackButton().setVisibility(View.GONE);
        tv1 = view.findViewById(R.id.tv1_zhuo1_);
        tv2 = view.findViewById(R.id.tv2_zhuo1_);
        tv3 = view.findViewById(R.id.tv3_zhuo1_);
        tv4 = view.findViewById(R.id.tv4_zhuo1_);
        tv5 = view.findViewById(R.id.tv5_zhuo1_);
        ll_prize = view.findViewById(R.id.ll_prize);
        ll_prize_show = view.findViewById(R.id.ll_prize_show);
        iv_see_prize = view.findViewById(R.id.iv_see_prize);
        iv_see_prize_msg = view.findViewById(R.id.iv_see_prize_msg);
        tv_prize_msg = view.findViewById(R.id.tv_prize_msg);
        layout_user = view.findViewById(R.id.ll_zhuo1_);
        button_receive = view.findViewById(R.id.button_receive_zhuo1_);
        button_skip = view.findViewById(R.id.button_skip_zhuo1_);
        button_save = view.findViewById(R.id.button_save_zhuo1_);
        button_receive.setOnClickListener(this);
        button_skip.setOnClickListener(this);
        button_save.setOnClickListener(this);
        iv_cancle1.setOnClickListener(this);
        iv_see_prize.setOnClickListener(this);
        iv_see_prize_msg.setOnClickListener(this);
        ll_prize_hide.setOnClickListener(this);

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
        activity = (MainActivity) getActivity();
        activity.menu.addIgnoredView(vp);

        tv_service.setOnClickListener(this);
        frameLayout_chat.setOnClickListener(this);
        iv_cancle.setOnClickListener(this);
        int num_msg = SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
        if (num_msg > 0) {
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(num_msg + "");
        } else {
            tv_unread.setVisibility(View.INVISIBLE);
        }

        vp.setOffscreenPageLimit(4);

        if (SingleBeans.getInstance().getVisonBean().getStr_type().equals("1")){
            ll_prize.setVisibility(View.VISIBLE);
        }else {
            ll.setVisibility(View.GONE);
        }
        initRxManger();
        initViewPage();
    }

    private void initViewPage() {

    }

    private void initRxManger() {
        mRxManager.on(RxBusConstants.UNREAD_MSG_COUNT, new Action1<Integer>() {//未读消息数量
            @Override
            public void call(Integer unm) {
                int num_msg = SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
                if (num_msg > 0) {
                    tv_unread.setVisibility(View.VISIBLE);
                    tv_unread.setText(num_msg + "");
                } else {
                    tv_unread.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRxManager.on(Constant.MATCH_BEGIN_, new Action1<Object>() {
            @Override
            public void call(Object o) {
                ll.setVisibility(View.VISIBLE);
                ll.setVisibility(View.VISIBLE);
                drawable_wait.start();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });
        mRxManager.on(RxBusConstants.UPDATE_ALL_MATCH, new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getAllMatch();
            }
        });
        mRxManager.on("match_accept", new Action1<String>() {//双方接受匹配
            @Override
            public void call(String s) {
                randStrPresenter.getRandStr();
                mPresenter.getAllMatch();
                ll.setVisibility(View.GONE);
                layout_user.setVisibility(View.GONE);
                UserInfoProvider.setHelloFlag(true);//设置第一次进入聊天
                UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(useId);
                if (countDownUtils1 != null) {
                    countDownUtils1.deletCallBack();
                }
                ToastUitl.showLong("匹配成功");
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        gochat();
                    }
                }, 1000);
            }
        });
        mRxManager.on("match_line", new Action1<String>() {
            @Override
            public void call(String s) {
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    String type = object.getString("type");
                    if (type.equals("4000")) {
                        if (SingleBeans.getInstance().getStatu().equals("0")){
                            useId = object.getString("user_id");
                            String user_video = object.getString("user_video");
                            String name = object.getString("nick_name");
                            String location = object.getString("location");
                            String account = object.getString("account");
                            String sex = object.getString("sex");
                            is_status = object.getString("is_status");
                            other_party_id = object.getString("other_party_id");
                            SingleBeans.getInstance().setChoose_id(object.getString("choice_id"));
                            judge(useId, user_video, name, location, account, sex);
                        }else if (SingleBeans.getInstance().getStatu().equals("66")){
                            mPresenter.getAllMatch();
                        }
                    }else if (type.equals("3000")){
                        mPresenter.getAllMatch();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public void gochat() {
        chronometer.stop();
        drawable_wait.stop();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("userId", useId);
        startActivityForResult(intent, CHAT);
    }
    private void judge(String userId, String user_video, String userName, String location, String hobby, String sex) {//第一次匹配成功时候更改UI界面
        chronometer.stop();
        if (userId != null && !userId.equals("")) {
            LogUtils.logd("userId" + userId + ";   name" + userName);
            useId = userId;
        }
        if (user_video != null) {
            gsyVideoOption = new GSYVideoOptionBuilder();
            gsyVideoOption.setUrl(user_video);
            gsyVideoOption.build(simpleVideo);
            simpleVideo.getStartButton().performClick();
        }
        if (userName != null) {
            tv1.setText(userName);
        }
        if (!location.equals("")) {
            tv2.setText("信息：" + location);
        }
        if (hobby != null) {
            tv3.setText("日常：" + hobby);
        }
        if (sex != null) {
            if (sex.equals("1")) {
                tv4.setText("男");
            } else {
                tv4.setText("女");
            }
        }
        layout_user.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);
        button_receive.setClickable(true);
        button_skip.setClickable(true);
        button_receive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.receive_button_maincolor_shape));

        button_save.setClickable(true);
        button_save.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.receive_button_maincolor_shape));

        tv5.setVisibility(View.VISIBLE);
        countDownUtils1 = new CountDownUtils(tv5, 30 * 1000, new EmptyView() {
            @Override
            public void emptyBack() {//倒计时跳过这次匹配
                skipMatch();
            }
        });
        countDownUtils1.start();
    }

    public void skipMatch() {
        mPresenter.matchBegin(SingleBeans.getInstance().getChoose_id(), SingleBeans.getInstance().getMatch_type(), "0", SingleBeans.getInstance().getCityID(), SingleBeans.getInstance().getLocation_id());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_service_new:
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userId", "1");
                startActivityForResult(intent, CHAT);
                break;
            case R.id.chat_frame_new:
                activity.showMenu();
                break;
            case R.id.iv_cancle_zhuo1_:
                mPresenter.matchCancle(SingleBeans.getInstance().getChoose_id(), "0");
                break;
            case R.id.button_receive_zhuo1_:
                button_receive.setClickable(false);
                button_receive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.skip_button_maincolor_shape));
                mPresenter.matchAccept(SingleBeans.getInstance().getChoose_id(),useId,other_party_id,is_status);

                break;
            case R.id.button_skip_zhuo1_:
                skipMatch();
                break;
            case R.id.button_save_zhuo1_:
                startActivityForResult(RemarkActivity.class, 1634);
                break;
            case R.id.iv_cancle_zhuo1_2:
                mPresenter.matchCancle(SingleBeans.getInstance().getChoose_id(), "0");
                break;
            case R.id.iv_see_prize:
                StringBuffer stringBuffer=new StringBuffer();
                ll_prize_show.setVisibility(View.VISIBLE);
                for (int i = 0; i < SingleBeans.getInstance().getRandStrBeans().size(); i++) {
                    if ((i+1)==SingleBeans.getInstance().getRandStrBeans().size()){
                        stringBuffer.append(SingleBeans.getInstance().getRandStrBeans().get(i).getStr());
                    }else {
                        stringBuffer.append(SingleBeans.getInstance().getRandStrBeans().get(i).getStr()+",");
                    }
                }
                tv_prize_msg.setText(stringBuffer.toString().replace(",","\n"));
                break;
            case R.id.iv_see_prize_msg:
                ll_prize_show.setVisibility(View.VISIBLE);
                tv_prize_msg.setText(SingleBeans.getInstance().getVisonBean().getVersion_type().replace("/n","\n"));
                break;
            case R.id.ll_prize_hide:
                ll_prize_show.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        activity.refreshConversationListFragment();
        if (requestCode == 1634) {
            mPresenter.matchSet(useId, SingleBeans.getInstance().getChoose_id(), data.getStringExtra("com"));

            mPresenter.matchBegin(SingleBeans.getInstance().getChoose_id(), SingleBeans.getInstance().getMatch_type(), "0", SingleBeans.getInstance().getCityID(), SingleBeans.getInstance().getLocation_id());
        }
    }

    private Zhuo1NewItemFragment createListFragments(SingleChooseBean singleChooseBean, String posion) {
        Zhuo1NewItemFragment fragment = new Zhuo1NewItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.FRAGMENT_NAME, singleChooseBean.getChoice_name());
        bundle.putString(Constant.FRAGMENT_TITLE, singleChooseBean.getChoice_title());
        bundle.putString(Constant.FRAGMENT_IMAGE, posion);
        fragment.setArguments(bundle);
        return fragment;
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
            List<String> names = new ArrayList<>();
            List<Fragment> mNewsFragmentList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                names.add(data.get(i).getChoice_name());
                mNewsFragmentList.add(createListFragments(data.get(i), i + 1 + ""));
                if (data.get(i).getMatch_status().equals("1") && data.get(i).getStatus().equals("0")) {
                    SingleBeans.getInstance().setChoose_id(data.get(i).getChoice_id());
                    ll.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                    drawable_wait.start();
                    chronometer.setBase(SystemClock.elapsedRealtime()-(System.currentTimeMillis()/1000-data.get(i).getStart_time())*1000);
                    chronometer.start();
                }
            }
            mNewsFragmentList.add(new Zhuo1NewItemFragment_());
            if (fragmentAdapter == null) {
                fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), mNewsFragmentList);
            } else {
                //刷新fragment
                fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList);
            }
            vp.setAdapter(fragmentAdapter);
        }
    }

    @Override
    public void matchBeginSucess() {
        if (countDownUtils1 != null) {
            countDownUtils1.deletCallBack();
        }
        if (simpleVideo.getCurrentState() == SimpleVideo.CURRENT_STATE_PLAYING) {
            simpleVideo.getStartButton().performClick();
        }
        layout_user.setVisibility(View.GONE);
        ll.setVisibility(View.VISIBLE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        drawable_wait.start();
        chronometer.start();
    }


    @Override
    public void matchAcceptSucess() {

    }

    @Override
    public void matchCancle() {
        if (countDownUtils1 != null) {
            countDownUtils1.deletCallBack();
        }
        ll.setVisibility(View.GONE);
        layout_user.setVisibility(View.GONE);
        chronometer.stop();
        drawable_wait.stop();
        if (simpleVideo.getCurrentState() == SimpleVideo.CURRENT_STATE_PLAYING) {
            simpleVideo.getStartButton().performClick();
        }
    }


    @Override
    public void returnAllMatch(List<MatchPersonBean> data) {
        if (data.size() > 0) {
            rv.setVisibility(View.VISIBLE);
            recycleAdapter.update(data);
        } else {
            rv.setVisibility(View.GONE);
        }
        mRxManager.post(Zhuo1NewItemFragment_.ToItemFragment1,"");
    }

    @Override
    public void returnSingleStatus(SingleStatusBean singleStatusBean) {

    }

    @Override
    public void returnSuggestFriend(List<SuggestFriendBean> suggestFriends) {

    }
}
