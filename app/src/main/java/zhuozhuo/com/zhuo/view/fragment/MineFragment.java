package zhuozhuo.com.zhuo.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.widget.EaseTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import li.com.base.base.BaseFragment;
import li.com.base.basesinglebean.SingleBeans;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.VideoSelectAdapter;
import zhuozhuo.com.zhuo.view.activity.GetRemarkActivity;
import zhuozhuo.com.zhuo.view.activity.GetStoryActivity;
import zhuozhuo.com.zhuo.view.activity.MineActivity;
import zhuozhuo.com.zhuo.view.activity.AllArticleActivity;
import zhuozhuo.com.zhuo.view.activity.MyLevelActivity;
import zhuozhuo.com.zhuo.view.activity.MyVideoPlayActivity;
import zhuozhuo.com.zhuo.view.activity.RecordVideoActivity;
import zhuozhuo.com.zhuo.view.activity.SetActivity;
import zhuozhuo.com.zhuo.view.activity.VideoSelectActivity;
import zhuozhuo.com.zhuo.widget.CircleImageView;

/**
 * Created by Administrator on 2017/1/16.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_use;
    private TextView tv_use1;
    private TextView tv_use2;
    private EaseTitleBar titleBar;
    private CircleImageView iv_use;
    private TextView tv_remark_unread;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(View view) {
        titleBar = (EaseTitleBar) view.findViewById(R.id.mine_titlebar);
        titleBar.setTitle("我");
        iv_use = (CircleImageView) view.findViewById(R.id.iv_user);
        Glide.with(getContext()).load(UserInfoProvider.getPhotoLink()).into(iv_use);
        tv_use = (TextView) view.findViewById(R.id.tv_user);
        tv_use1 = (TextView) view.findViewById(R.id.tv_user2);
        tv_use2 = (TextView) view.findViewById(R.id.tv_user3);
        tv_remark_unread = view.findViewById(R.id.tv_remark_unread);

        int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
        if (num > 0) {
            tv_remark_unread.setVisibility(View.VISIBLE);
        } else {
            tv_remark_unread.setVisibility(View.INVISIBLE);
        }

        //我的评分
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.linearlayout1);
        linearLayout1.setOnClickListener(this);
        //反馈论坛
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.linearlayout2);
        linearLayout2.setOnClickListener(this);
        //设置
        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.linearlayout3);
        linearLayout3.setOnClickListener(this);
        //用户详情
        LinearLayout linearLayout4 = (LinearLayout) view.findViewById(R.id.linearlayout4);
        linearLayout4.setOnClickListener(this);

        //用户详情
        LinearLayout linearLayout5 = (LinearLayout) view.findViewById(R.id.linearlayout5);
        linearLayout5.setOnClickListener(this);

        //我的等级
        LinearLayout linearLayout6 = (LinearLayout) view.findViewById(R.id.linearlayout6);
        linearLayout6.setOnClickListener(this);

        //我的等级
        LinearLayout linearLayout7 = (LinearLayout) view.findViewById(R.id.linearlayout7);
        linearLayout7.setOnClickListener(this);

        initData();//初始化数据

        initRxManger();

    }

    private void initRxManger() {
        mRxManager.on("stateless", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                if (num > 0) {
                    tv_remark_unread.setVisibility(View.VISIBLE);
                } else {
                    tv_remark_unread.setVisibility(View.INVISIBLE);
                }
            }
        });

        mRxManager.on("remarkUnread", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                if (num > 0) {
                    tv_remark_unread.setVisibility(View.VISIBLE);
                } else {
                    tv_remark_unread.setVisibility(View.INVISIBLE);
                }
            }
        });

        mRxManager.on("match_line", new Action1<String>() {
            @Override
            public void call(String s) {
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    final String type = object.getString("type");
                    if (type.equals("1003")){
                        int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                        if (num > 0) {
                            tv_remark_unread.setVisibility(View.VISIBLE);
                        } else {
                            tv_remark_unread.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        mRxManager.on("remarkUnread", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                if (num > 0) {
                    tv_remark_unread.setVisibility(View.VISIBLE);
                } else {
                    tv_remark_unread.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void initData() {
        UserDB userDB= UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(UserInfoProvider.getUserID());
        if (userDB!=null) {
            tv_use1.setText(userDB.getHundred_level().trim().toUpperCase()+"级");
            tv_use2.setText("第"+userDB.getHundred_rate()+"名");
        }
        Glide.with(getContext()).load(UserInfoProvider.getPhotoLink()).into(iv_use);
        tv_use.setText(UserInfoProvider.getNickName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearlayout1://我的评分
                startActivity(GetRemarkActivity.class);
                break;
            case R.id.linearlayout2://反馈论坛
                startActivity(AllArticleActivity.class);
                break;
            case R.id.linearlayout3://设置
                startActivity(SetActivity.class);
                break;
            case R.id.linearlayout4://用户详情
                startActivityForResult(MineActivity.class,100);
                break;
            case R.id.linearlayout5://个人故事列表
                startActivity(GetStoryActivity.class);
                break;
            case R.id.linearlayout6://我的等级
                startActivity(MyLevelActivity.class);
            case R.id.linearlayout7://我的介绍视频
               if(UserInfoProvider.getUserVideo()!=null&&!UserInfoProvider.getUserVideo().equals("")){
                   startActivity(MyVideoPlayActivity.class);
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
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initData();
    }
}
