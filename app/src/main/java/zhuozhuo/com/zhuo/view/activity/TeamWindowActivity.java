package zhuozhuo.com.zhuo.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chatuidemo.my.constract.TheBaseConstract;
import com.hyphenate.chatuidemo.my.model.TheBaseModel;
import com.hyphenate.chatuidemo.my.presenter.TheBasePresenter;

import zhuozhuo.com.zhuo.R;

public class TeamWindowActivity extends com.hyphenate.chatuidemo.ui.BaseActivity implements TheBaseConstract.View, View.OnClickListener {
    private Button bt_refuse;
    private Button bt_receive;
    private TextView tv_msg;
    private TheBasePresenter basePresenter;
    private TheBaseModel baseModel;
    private String type;
    private String group_name;
    private String user_id;
    private String group_id;
    private String line_id;
    private String nick_name;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_team_window);
        initView();
    }


    public void initView() {
        bt_refuse = (Button) findViewById(R.id.bt_msg_refuse);
        bt_receive = (Button) findViewById(R.id.bt_msg_receive);
        tv_msg = (TextView) findViewById(R.id.tv_msg_detaile);

        bt_refuse.setOnClickListener(this);
        bt_receive.setOnClickListener(this);

        basePresenter = new TheBasePresenter();
        baseModel = new TheBaseModel();
        basePresenter.setVM(baseModel, this);


        type = getIntent().getStringExtra("type");
        group_name = getIntent().getStringExtra("group_name");
        if (type.equals("2")){
          tv_msg.setText("你所在的 " + group_name + " 队伍已解散");
          bt_refuse.setVisibility(View.GONE);
          bt_receive.setText("确定");
        }else {
            user_id = getIntent().getStringExtra("user_id");
            group_id = getIntent().getStringExtra("group_id");
            line_id = getIntent().getStringExtra("line_id");
            nick_name = getIntent().getStringExtra("nick_name");
            if (type.equals("1")){
                tv_msg.setText("我系 " + nick_name + "，我在贪玩啄啄 " + group_name + " 队伍，系兄弟就来砍我");
                bt_refuse.setVisibility(View.VISIBLE);
                bt_refuse.setText("丑拒");
                bt_receive.setText("接受");
            }else if(type.equals("3")){
                tv_msg.setText("玩家 " + nick_name + " 申请加入你的 " + group_name + " 队伍");
                bt_refuse.setVisibility(View.VISIBLE);
                bt_refuse.setText("拒绝");
                bt_receive.setText("允许");
            }
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
    public void receiveInviteSucess() {

    }

    @Override
    public void refuseInviteSucess() {

    }

    @Override
    public void refuseApplySucess() {

    }

    @Override
    public void receiveApplySucess() {

    }

    @Override
    public void acceptSucess() {

    }

    @Override
    public void denySucess() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_msg_refuse:
                if (type.equals("1")){
                    basePresenter.refuseInvite(line_id, group_id, user_id);
                }else if(type.equals("3")){
                    basePresenter.refuseApply(line_id, user_id);
                }
                finish();
                break;
            case R.id.bt_msg_receive:
                if (type!=null){
                    if (type.equals("2")){

                    }else if (type.equals("1")){
                        basePresenter.receiveInvite(line_id, group_id, user_id);
                    }else if(type.equals("3")){
                        basePresenter.receiveApply(line_id, user_id);
                    }
                    finish();
                }
                break;
        }
    }
}
