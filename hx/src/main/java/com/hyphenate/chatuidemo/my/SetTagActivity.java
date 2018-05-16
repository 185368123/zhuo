package com.hyphenate.chatuidemo.my;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.bean.SuggestTagBean;
import com.hyphenate.chatuidemo.my.constract.SetTagConstract;
import com.hyphenate.chatuidemo.my.easytagdragview.EasyTipDragView;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.SimpleTitleTip;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.Tip;
import com.hyphenate.chatuidemo.my.model.SetTagModel;
import com.hyphenate.chatuidemo.my.presenter.SetTagPresenter;
import com.hyphenate.chatuidemo.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baserx.RxManager;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;

public class SetTagActivity extends BaseActivity implements SetTagConstract.View, View.OnClickListener {

    private SetTagPresenter mPresenter;
    private SetTagModel mModel;
    private TextView tv_save;
    private EasyTipDragView easyTipDragView;
    private List<Tip> addTipList = new ArrayList<>();
    private List<Tip> dragTipList = new ArrayList<>();
    private Button bt_add;
    private EditText et_add;
    private String choice_id;
    private String you_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tag);
        choice_id = getIntent().getStringExtra("choice_id");
        you_user_id = getIntent().getStringExtra("you_user_id");

        mPresenter = new SetTagPresenter();
        mModel = new SetTagModel();
        mPresenter.setVM(mModel, this);

        mPresenter.getSuggestTag();

        tv_save = (TextView) findViewById(R.id.tv_save_tag);
        tv_save.setOnClickListener(this);
        bt_add = (Button) findViewById(R.id.bt_add_tag);
        bt_add.setOnClickListener(this);
        et_add = (EditText) findViewById(R.id.et_add_tag);

        easyTipDragView = (EasyTipDragView) findViewById(R.id.easy_tip_drag_view);
        easyTipDragView.setAddData(addTipList);
        easyTipDragView.setDragData(dragTipList);
        //设置每次数据改变后的回调（例如每次拖拽排序了标签或者增删了标签都会回调）
        easyTipDragView.setDataResultCallback(new EasyTipDragView.OnDataChangeResultCallback() {
            @Override
            public void onDataChangeResult(ArrayList<Tip> tips) {
                LogUtils.logd(tips.toString());
                dragTipList.clear();
                dragTipList.addAll(tips);
            }
        });
        easyTipDragView.open();
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
    public void returnSuggestTag(List<SuggestTagBean> suggestTagBeans) {
        addTipList.clear();
        for (int i = 0; i < suggestTagBeans.size(); i++) {
            SimpleTitleTip titleTip = new SimpleTitleTip();
            titleTip.setTip(suggestTagBeans.get(i).getLabel_name());
            titleTip.setId(i);
            titleTip.setId_member(suggestTagBeans.get(i).getIdd());
            addTipList.add(titleTip);
        }
        easyTipDragView.setAddData(addTipList);
    }

    @Override
    public void setTagSucess() {
        ToastUitl.showLong("设置活动成功");
        new RxManager().post("GetTag","");
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_save_tag) {
            if (dragTipList.size() != 6) {
                ToastUitl.showLong("请选择或输入六个活动名称");
            } else {
                mPresenter.setTag(choice_id, you_user_id, dragTipList);
            }

        } else if (i == R.id.bt_add_tag) {
            String tag = et_add.getText().toString();
            if (dragTipList.size()>5){
                ToastUitl.showLong("已设置满6个活动");
            }else {
                if (tag != null && tag.length() != 0) {
                    SimpleTitleTip tip = new SimpleTitleTip();
                    tip.setTip(tag);
                    tip.setId(dragTipList.size());
                    tip.setId_member("0");
                    dragTipList.add(tip);
                    easyTipDragView.setDragData(dragTipList);
                    et_add.setText("");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
