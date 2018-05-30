package zhuozhuo.com.zhuo.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import zhuozhuo.com.zhuo.R;

/**
 * Created by Administrator on 2017/11/15.
 */

public class CommentPopupWindow extends PopupWindow {

    //点击“发送”回调
    private LiveCommentSendClick sendClick;

    private View contentView;
    private CheckBox popupCommentBullCb;
    private EditText popupCommentEdt;
    private TextView popupCommentSendTv;
    private FragmentActivity context;
    private RelativeLayout popupCommentParent;
    String text;

    public interface LiveCommentSendClick {
        //第二个参数标记是不是弹幕，第三个是评论内容
        void onSendClick(View view, boolean isBull, String comment);
    }


    //构造方法
    public CommentPopupWindow(FragmentActivity context, LiveCommentSendClick sendClick,String text) {
        super(context);
        this.context = context;
        this.sendClick = sendClick;
        this.text=text;
        foundPopup();
    }

    private void foundPopup() {
        contentView = View.inflate(context, R.layout.popupwindow_layout, null);

        popupCommentParent = (RelativeLayout) contentView.findViewById(R.id.popup_comment_parent);
        popupCommentBullCb = (CheckBox) contentView.findViewById(R.id.popup_comment_bull_cb);
        popupCommentEdt = (EditText) contentView.findViewById(R.id.popup_comment_edt);
        popupCommentSendTv = (TextView) contentView.findViewById(R.id.popup_comment_send_tv);

        setContentView(contentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        //设置允许在外点击消失
        setOutsideTouchable(true);
        //这个是为了点击“Back”也能使其消失，不会影响背景
        setBackgroundDrawable(new BitmapDrawable());
        //显示在键盘上方
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupCommentSendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = popupCommentEdt.getText().toString().trim();
                if (result.length() <= 0) {
                    Toast.makeText(context, "还没有填写任何内容哦！", Toast.LENGTH_SHORT).show();
                } else {
                    //第二个参数标记是否是弹幕
                    sendClick.onSendClick(v, popupCommentBullCb.isChecked(), result);
                    dismiss();
                    hideKeyboard(context,popupCommentEdt);
                }
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                //AppUtils.hideSoftInput(context, popupCommentEdt.getWindowToken());
            }
        });
    }


    public void showReveal() {
        if (contentView == null) {
            Toast.makeText(context, "创建失败", Toast.LENGTH_SHORT).show();
        } else {
            //延时显示软键盘
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    showKeyboard(context,popupCommentEdt);
                }
            }, 50);
            //显示并设置位置
            showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void showKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }
}
