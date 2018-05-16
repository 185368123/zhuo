package com.hyphenate.chatuidemo.my;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.constract.CreatTeamConstract;
import com.hyphenate.chatuidemo.my.model.CreatTeamModel;
import com.hyphenate.chatuidemo.my.presenter.CreatTeamPresenter;
import com.hyphenate.chatuidemo.ui.BaseActivity;

import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.ToastUitl;

public class CreatTeamActivity extends BaseActivity implements View.OnClickListener, CreatTeamConstract.View {

    private EditText editText_name;
    private EditText editText_id;
    private Button bt_creat;
    private Button bt_cancle;
    private InputFilter inputFilter;
    private final static int MAX_LENGTH=20;//设置最大长度为20个英文字符或者10个中文汉字
    private CreatTeamPresenter mPresenter;
    private CreatTeamModel mModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_team);
        mPresenter = new CreatTeamPresenter();
        mModel = new CreatTeamModel();
        mPresenter.setVM(mModel,this);
        initView();
    }

    private void initView() {
        inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 输入内容是否超过设定值，最多输入10个汉字或20个字符
                if (getTextLength(dest.toString()) + getTextLength(source.toString()) > MAX_LENGTH) {
                    // 输入框内已经有20个字符则返回空字符
                    if (getTextLength(dest.toString()) >= 20) {
                        return "";
                        // 如果输入框内没有字符，且输入的超过了20个字符，则截取前10个汉字
                    } else if (getTextLength(dest.toString()) == 0) {
                        return source.toString().substring(0, 10);
                    } else {
                        // 输入框已有的字符数为双数还是单数
                        if (getTextLength(dest.toString()) % 2 == 0) {
                            return source.toString().substring(0,10 - (getTextLength(dest.toString()) / 2));
                        } else {
                            return source.toString().substring(0,10 - (getTextLength(dest.toString()) / 2 + 1));
                        }
                    }
                }
                return null;
            }
        };
        editText_name = (EditText) findViewById(R.id.et_team_mame);
        editText_name.setFilters(new InputFilter[]{inputFilter});
        editText_id = (EditText) findViewById(R.id.et_gameID);
        editText_id.setFilters(new InputFilter[]{inputFilter});
        bt_creat = (Button) findViewById(R.id.bt_creatteam);
        bt_creat.setOnClickListener(this);
        bt_cancle = (Button) findViewById(R.id.bt_canclecreat);
        bt_cancle.setOnClickListener(this);
    }


    /**
     * 获取字符数量 汉字占2个，英文占一个
     *
     * @param text
     * @return
     */
    public static int getTextLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) > 255) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_creatteam) {
            mPresenter.getUserMsg(editText_name,editText_id);
        }else if (i == R.id.bt_canclecreat) {
            finish();
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
    public void returnUserMsg() {
        ToastUitl.showLong("队伍创建成功");
        finish();
    }
}
