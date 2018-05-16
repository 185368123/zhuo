package com.hyphenate.chatuidemo.my;

import android.os.Bundle;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.my.constract.TeamMenuConstract;
import com.hyphenate.chatuidemo.my.model.TeamMenuModel;
import com.hyphenate.chatuidemo.my.presenter.TeamMenuPresenter;
import com.hyphenate.chatuidemo.ui.BaseActivity;
import com.hyphenate.chatuidemo.R;

import li.com.base.baserx.RxManager;

/**
 * Created by Administrator on 2018/4/23.
 */

public class TeamMenuActivity extends BaseActivity implements TeamMenuConstract.View {

    private String type;
    private String line_id;
    private String you_user_id;
    private String group_id;
    private TeamMenuPresenter mPresenter;
    private TeamMenuModel mModel;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        type = getIntent().getStringExtra("type");
        line_id = getIntent().getStringExtra("line_id");
        you_user_id = getIntent().getStringExtra("you_user_id");
        group_id = getIntent().getStringExtra("group_id");
        mPresenter = new TeamMenuPresenter();
        mModel = new TeamMenuModel();
        mPresenter.setVM(mModel,this);
        if (type.equals("1")){
            setContentView(R.layout.team_menu_for_1);
        }else if (type.equals("2")){
            setContentView(R.layout.team_menu_for_2);
        }else {
            setContentView(R.layout.team_menu_for_3);
        }
    }

    public void deleteteam(View view){
        mPresenter.deleteTeam(line_id,group_id);
    }

    public void cancle(View view){
        finish();
    }

    public void deletemember(View view){
       mPresenter.deleteMember(line_id,group_id,"2",you_user_id);

    }

    public void quitteam(View view){
        mPresenter.deleteMember(line_id,group_id,"1",you_user_id);
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
    public void deleteMemberSucess() {
      new RxManager().post("upadteTeam","");
        finish();
    }

    @Override
    public void deleteTeamSucess() {
        EMClient.getInstance().chatManager().deleteConversation(group_id, true);
        new RxManager().post("upadteTeam","");
        finish();
    }
}
