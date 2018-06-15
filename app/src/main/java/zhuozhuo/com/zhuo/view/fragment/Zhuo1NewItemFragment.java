package zhuozhuo.com.zhuo.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.SetCitykActivity;
import com.hyphenate.chatuidemo.ui.UserProfileActivity;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1NewItemListAdapter;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentNewModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentNewPresenter;
import zhuozhuo.com.zhuo.view.activity.MyCollege;
import zhuozhuo.com.zhuo.view.activity.RecordVideoActivity;
import zhuozhuo.com.zhuo.view.activity.VideoSelectActivity;

/**
 * Created by Administrator on 2018/6/6.
 */

public class Zhuo1NewItemFragment extends BaseFragment<Zhuo1FragmentNewPresenter, Zhuo1FragmentNewModel> implements View.OnClickListener, Zhuo1FragmentNewConstract.View {

    private TextView tv1;
    private TextView tv2;
    private String name;
    private String title;
    private String image;
    private ImageView iv1;
    private ImageView iv2;
    private AnimationDrawable drawable;
    private LinearLayout ll;
    private ImageView iv_cancle;
    private ImageView iv_animation;
    private Chronometer chronometer;
    private AnimationDrawable drawable_wait;
    Zhuo1NewItemListAdapter.ViewSave viewSave;
    private LinearLayout ll_suggest;
    private TextView tv_suggest1;
    private TextView tv_suggest2;
    private SuggestFriendBean suggestFriend;
    private TextView tv_friend_num;


    @Override
    public void onResume() {
        super.onResume();
        if (image!=null&&image.equals("2")) {
            mPresenter.getSuggestFriend(SingleBeans.getInstance().getFriensToString());
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.zhuo1newfragmentitem_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(mModel, this);
    }

    @Override
    protected void initView(View view) {

        if (getArguments() != null) {
            name = getArguments().getString(Constant.FRAGMENT_NAME);
            title = getArguments().getString(Constant.FRAGMENT_TITLE);
            image = getArguments().getString(Constant.FRAGMENT_IMAGE);
        }

        tv1 = view.findViewById(R.id.tv_zhuo1item_name);
        tv2 = view.findViewById(R.id.tv_zhuo1item_title);
        iv1 = view.findViewById(R.id.iv_zhuo1item_1);
        iv2 = view.findViewById(R.id.iv_zhuo1item_2);

        tv_friend_num = view.findViewById(R.id.tv_friend_num);
        ll = view.findViewById(R.id.wait_zhuo1_item_);
        ll_suggest = view.findViewById(R.id.ll_suggest);
        iv_cancle = view.findViewById(R.id.iv_cancle_zhuo1_item_);
        iv_animation = view.findViewById(R.id.iv_wait_zhuo1_item_);
        drawable_wait = (AnimationDrawable) iv_animation.getDrawable();
        chronometer = view.findViewById(R.id.time_zhuo1_item_);
        tv_suggest1 = view.findViewById(R.id.people_suggest1);
        tv_suggest2 = view.findViewById(R.id.people_suggest2);

        drawable = (AnimationDrawable) iv2.getDrawable();
        drawable.start();
        iv1.setOnClickListener(this);
        iv_cancle.setOnClickListener(this);
        tv_suggest1.setOnClickListener(this);
        tv_suggest2.setOnClickListener(this);
        tv_friend_num.setOnClickListener(this);
        tv1.setText(name.replace("/n", "\n"));
        tv2.setText(title.replace("/n", "\n"));
        switch (image) {
            case "1":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_1).into(iv1);
                break;
            case "2":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_2).into(iv1);
                ll_suggest.setVisibility(View.VISIBLE);
                tv_friend_num.setVisibility(View.VISIBLE);
                mPresenter.getSuggestFriend(SingleBeans.getInstance().getFriensToString());
                break;
            case "3":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_3).into(iv1);
                break;
            case "4":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_4).into(iv1);
                break;
        }

        mRxManager.on(Constant.MATCH_BEGIN, new Action1<Zhuo1NewItemListAdapter.ViewSave>() {
            @Override
            public void call(Zhuo1NewItemListAdapter.ViewSave save) {
                String position = save.position + 1 + "";
                if (image.equals(position)) {
                    viewSave = save;
                    ll.setVisibility(View.VISIBLE);
                    drawable_wait.start();
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }
            }
        });

        mRxManager.on(Constant.MATCH_STOP_, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (image.equals("1")) {
                    ll.setVisibility(View.GONE);
                    chronometer.stop();
                }
            }
        });

        mRxManager.on(Constant.MATCH_STOP, new Action1<Zhuo1NewItemListAdapter.ViewSave>() {
            @Override
            public void call(Zhuo1NewItemListAdapter.ViewSave save) {
                String position = save.position + 1 + "";
                if (image.equals(position)) {
                    viewSave = save;
                    ll.setVisibility(View.GONE);
                    drawable_wait.stop();
                    chronometer.stop();
                }
            }
        });
        mRxManager.on(Constant.MATCH_BEGIN_1, new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (image.equals("1")) {
                    mPresenter.matchBegin(SingleBeans.getInstance().getSingleChooseBeans().get(Integer.parseInt(image) - 1).getChoice_id(), "1", "0", SingleBeans.getInstance().getCityID(), SingleBeans.getInstance().getLocation_id());
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancle_zhuo1_item_:
                mPresenter.matchCancle(SingleBeans.getInstance().getSingleChooseBeans().get(viewSave.position).getChoice_id(), "66");
                break;
            case R.id.iv_zhuo1item_1:
                if (UserInfoProvider.getUserVideo() != null && !UserInfoProvider.getUserVideo().equals("")) {
                    if (image.equals("1")) {
                        startActivity(SetCitykActivity.class);
                    } else {
                        mPresenter.matchBegin(SingleBeans.getInstance().getSingleChooseBeans().get(Integer.parseInt(image) - 1).getChoice_id(), "2", "0", "", "");
                    }
                    // 保存匹配信息
                    SingleBeans.getInstance().setStatu("0");
                    SingleBeans.getInstance().setMatch_type(image.equals("1") ? "1" : "2");
                    SingleBeans.getInstance().setChoose_id(SingleBeans.getInstance().getSingleChooseBeans().get(Integer.parseInt(image) - 1).getChoice_id());
                } else {
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
                break;
            case R.id.people_suggest1:
                if (suggestFriend != null && suggestFriend.getData().size() > 1) {
                    Intent intent = new Intent(getContext(), UserProfileActivity.class);
                    intent.putExtra("username", suggestFriend.getData().get(0).getUser_id());
                    startActivity(intent);
                } else {
                    mPresenter.getSuggestFriend(SingleBeans.getInstance().getFriensToString());
                }
                break;
            case R.id.people_suggest2:
                if (suggestFriend != null && suggestFriend.getData().size() > 1) {
                    Intent intent = new Intent(getContext(), UserProfileActivity.class);
                    intent.putExtra("username", suggestFriend.getData().get(1).getUser_id());
                    startActivity(intent);
                } else {
                    mPresenter.getSuggestFriend(SingleBeans.getInstance().getFriensToString());
                }
                break;
            case R.id.tv_friend_num:
                startActivity(MyCollege.class);
                break;
        }
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

    }

    @Override
    public void matchBeginSucess() {
        mRxManager.post(Constant.MATCH_BEGIN_, "");
    }


    @Override
    public void matchAcceptSucess() {

    }

    @Override
    public void matchCancle() {
        if (viewSave != null) {
            viewSave.chronometer.stop();
            viewSave.ll.setVisibility(View.GONE);
        }
        ll.setVisibility(View.GONE);
        drawable_wait.stop();
        chronometer.stop();
    }

    @Override
    public void returnAllMatch(List<MatchPersonBean> data) {

    }

    @Override
    public void returnSingleStatus(SingleStatusBean singleStatusBean) {

    }

    @Override
    public void returnSuggestFriend(SuggestFriendBean suggestFriend) {
        this.suggestFriend = suggestFriend;
        tv_friend_num.setText("认识人数" + SingleBeans.getInstance().getFriends().size() + "/" + suggestFriend.getUser_num());
        if (suggestFriend.getData().size() >= 2) {
            tv_suggest1.setText(suggestFriend.getData().get(0).getNick_name());
            tv_suggest2.setText(suggestFriend.getData().get(1).getNick_name());
            UserMsgDBHelp.getUserMsgDBHelp().updateMsg(suggestFriend.getData().get(0).getUser_id());
            UserMsgDBHelp.getUserMsgDBHelp().updateMsg(suggestFriend.getData().get(1).getUser_id());
        }
    }
}
