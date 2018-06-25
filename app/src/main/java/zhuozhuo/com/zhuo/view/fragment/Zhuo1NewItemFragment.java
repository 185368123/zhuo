package zhuozhuo.com.zhuo.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.SetCitykActivity;
import com.hyphenate.chatuidemo.ui.UserProfileActivity;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.basesinglebean.SuggestFriendBean;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1NewItemListAdapter;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentNewModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentNewPresenter;
import zhuozhuo.com.zhuo.view.activity.MyCollege;
import zhuozhuo.com.zhuo.view.activity.RecordVideoActivity;
import zhuozhuo.com.zhuo.view.activity.VideoSelectActivity;
import zhuozhuo.com.zhuo.view.activity.Welcome2Activity;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by Administrator on 2018/6/6.
 */
public class Zhuo1NewItemFragment extends BaseFragment<Zhuo1FragmentNewPresenter, Zhuo1FragmentNewModel> implements View.OnClickListener, Zhuo1FragmentNewConstract.View {
    // 要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
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
    private AlertDialog dialog;


    @Override
    public void onResume() {
        super.onResume();
        if (image != null && image.equals("2")) {
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
                            // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                // 检查该权限是否已经获取
                                int j = ContextCompat.checkSelfPermission(getContext(), permissions[0]);
                                int i = ContextCompat.checkSelfPermission(getContext(), permissions[1]);
                                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                                if (j != PackageManager.PERMISSION_GRANTED || i != PackageManager.PERMISSION_GRANTED) {
                                    // 如果没有授予该权限，就去提示用户请求
                                    startRequestPermission();
                                } else {
                                    startActivity(RecordVideoActivity.class);
                                }
                            } else {
                                startActivity(RecordVideoActivity.class);
                            }

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
                if (SingleBeans.getInstance().getFriends() != null && SingleBeans.getInstance().getFriends().size() != 0) {
                    startActivity(MyCollege.class);
                } else {
                    ToastUitl.showLong("好友列表为空，请稍后再试！");
                    rx.Observable.create(new rx.Observable.OnSubscribe<List<String>>() {
                        private List<String> usernames;

                        @Override
                        public void call(Subscriber<? super List<String>> subscriber) {
                            try {
                                usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                            subscriber.onNext(usernames);
                        }
                    }).subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<List<String>>() {
                                @Override
                                public void onCompleted() {
                                    LogUtils.logd("获取聊天好友成功！");
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(List<String> strings) {
                                    SingleBeans.getInstance().setFriends(strings);
                                    for (int i = 0; i < strings.size(); i++) {
                                        DemoHelper.getInstance().saveContact(new EaseUser(strings.get(i)));
                                    }
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                                }
                            });
                }
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
        if (SingleBeans.getInstance().getFriends() != null) {
            tv_friend_num.setText("认识人数" + SingleBeans.getInstance().getFriends().size() + "/" + suggestFriend.getUser_num());
        } else {
            tv_friend_num.setText("认识人数" + 0 + "/" + suggestFriend.getUser_num());
        }

        if (suggestFriend.getData().size() >= 2) {
            tv_suggest1.setText(suggestFriend.getData().get(0).getNick_name());
            tv_suggest2.setText(suggestFriend.getData().get(1).getNick_name());
            UserMsgDBHelp.getUserMsgDBHelp().updateMsg(suggestFriend.getData().get(0).getUser_id());
            UserMsgDBHelp.getUserMsgDBHelp().updateMsg(suggestFriend.getData().get(1).getUser_id());
        }
    }


    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(getActivity(), permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (requestCode == 321) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                        boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!b) {
                            // 用户还是想用我的 APP 的
                            // 提示用户去应用设置界面手动开启权限
                            showDialogTipUserGoToAppSettting();
                        } else
                            getActivity().finish();
                    } else {
                        if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                            boolean a = shouldShowRequestPermissionRationale(permissions[1]);
                            if (!a) {
                                // 用户还是想用我的 APP 的
                                // 提示用户去应用设置界面手动开启权限
                                showDialogTipUserGoToAppSettting();
                            } else
                                getActivity().finish();
                        } else {
                            startActivity(RecordVideoActivity.class);
                        }
                    }
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(getContext())
                .setTitle("相机权限或录音权限不可用")
                .setMessage("请在-应用设置-权限-中，打开权限")
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
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

}
