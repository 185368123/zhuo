package zhuozhuo.com.zhuo.view.fragment;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.base.BaseFragment;
import li.com.base.basesinglebean.MatchPersonBean;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import li.com.base.baseuntils.LogUtils;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.adapter.Zhuo1NewItemListAdapter;
import zhuozhuo.com.zhuo.constants.Constant;
import zhuozhuo.com.zhuo.contract.Zhuo1FragmentNewConstract;
import zhuozhuo.com.zhuo.model.Zhuo1FragmentNewModel;
import zhuozhuo.com.zhuo.presenter.Zhuo1FragmentNewPresenter;
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

        ll = view.findViewById(R.id.wait_zhuo1_item_);
        iv_cancle = view.findViewById(R.id.iv_cancle_zhuo1_item_);
        iv_animation = view.findViewById(R.id.iv_wait_zhuo1_item_);
        drawable_wait = (AnimationDrawable) iv_animation.getDrawable();
        chronometer = view.findViewById(R.id.time_zhuo1_item_);

        drawable = (AnimationDrawable) iv2.getDrawable();
        drawable.start();
        iv1.setOnClickListener(this);
        iv_cancle.setOnClickListener(this);
        tv1.setText(name);
        tv2.setText(title);
        switch (image) {
            case "1":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_1).into(iv1);
                break;
            case "2":
                Glide.with(getContext()).load(R.mipmap.zhuo1_item_2).into(iv1);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancle_zhuo1_item_:
                mPresenter.matchCancle(SingleBeans.getInstance().getSingleChooseBeans().get(viewSave.position).getChoice_id(),"66");
                break;
            case R.id.iv_zhuo1item_1:
                if(UserInfoProvider.getUserVideo()!=null&&!UserInfoProvider.getUserVideo().equals("")){
                    SingleBeans.getInstance().setMatch_type(image.equals("1")?"1":"2");
                    //ToDo 保存匹配信息
                    SingleBeans.getInstance().setDestination("");
                    SingleBeans.getInstance().setLocation("");
                    SingleBeans.getInstance().setChoose_id(SingleBeans.getInstance().getSingleChooseBeans().get(Integer.parseInt(image)-1).getChoice_id());
                    mPresenter.matchBegin(SingleBeans.getInstance().getSingleChooseBeans().get(Integer.parseInt(image)-1).getChoice_id(),image.equals("1")?"1":"2","0","","");
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
}
