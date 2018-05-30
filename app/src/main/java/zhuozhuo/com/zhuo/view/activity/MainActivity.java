package zhuozhuo.com.zhuo.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.chatuidemo.my.model.GetUserMsgModel;
import com.hyphenate.chatuidemo.my.presenter.GetUserMsgPresenter;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.chatuidemo.ui.ContactListFragment;
import com.hyphenate.chatuidemo.ui.ConversationListFragment;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.events.RxBusConstants;
import com.hyphenate.exceptions.HyphenateException;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.tablemanager.Connector;

import java.util.List;

import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;
import li.com.base.baseuntils.StringUtils;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zhuozhuo.com.zhuo.R;
import zhuozhuo.com.zhuo.constants.TabHostConstant;
import zhuozhuo.com.zhuo.service.SocketService;
import zhuozhuo.com.zhuo.widget.FragmentTabHost;

public class MainActivity extends BaseActivity implements View.OnClickListener, ServiceConnection {
    //控件声明
    FragmentTabHost tabHost;
    LayoutInflater inflater;
    private Button button_chat;
    private Button button_friend;
    private FragmentManager supportFragmentManager;

    private ConversationListFragment conversationListFragment;
    private ContactListFragment contactListFragment;
    private TextView tv1;
    private TextView tv3;
    private TextView tv4;
    private SlidingMenu menu;
    private TextView tv2;
    private TextView tv_chat_unread;
    private TextView tv_friend_unread;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent intent = new Intent(this, SocketService.class);
        bindService(intent, this, SocketService.BIND_AUTO_CREATE);
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        //初始化
        //参数一:Context
        //参数二：FragmentManager,v4包
        //参数三：指定动态加载Fragment的布局id
        tabHost.setup(this, getSupportFragmentManager(), R.id.fragment_layout);

        //登录环信聊天系统
        initChat();

        //设置Sliding的布局
        initSliding();

        //TabHost添加标签
        initTabs();

        //初始化RxBus注册事件
        initRxManger();

        tabHost.setCurrentTab(0);

        //创建用户列表数据库
        SQLiteDatabase db = Connector.getDatabase();

        //更新我的个人信息
        UserMsgDBHelp.getUserMsgDBHelp().updateMsg(UserInfoProvider.getUserID());

        EaseUI easeUI = EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                if (username.equals("admin")){
                    EaseUser easeUser = new EaseUser(username);
                    easeUser.setNickname("系统提示");
                    easeUser.setAvatar("http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/head/head15242085638782585.png");
                    return easeUser;
                }else {
                    UserDB userDB = UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(username);
                    if (userDB != null) {
                        EaseUser easeUser = new EaseUser(userDB.getUser_id());
                        easeUser.setNickname(userDB.getNick_name());
                        easeUser.setAvatar(userDB.getPhoto_link());
                        return easeUser;
                    } else
                        return null;
                }
                }

        });


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

    private void initRxManger() {
        mRxManager.on(RxBusConstants.UNREAD_MSG_COUNT, new Action1<Integer>() {
            @Override
            public void call(Integer nm) {
                int num=SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
                int num_msg=SingleBeans.getInstance().getUnReadBean().getMesNum();
                int num_friend=SingleBeans.getInstance().getUnReadBean().getNewFriendNum();
                if (num > 0) {
                    tv1.setVisibility(View.VISIBLE);
                    tv1.setText(num + "");
                } else {
                    tv1.setVisibility(View.INVISIBLE);
                }
                if (num_msg > 0) {
                    tv_chat_unread.setVisibility(View.VISIBLE);
                    tv_chat_unread.setText(num_msg + "");
                } else {
                    tv_chat_unread.setVisibility(View.INVISIBLE);
                }
                if (num_friend > 0) {
                    tv_friend_unread.setVisibility(View.VISIBLE);
                    tv_friend_unread.setText(num_friend + "");
                } else {
                    tv_friend_unread.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRxManager.on("unread", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getComNum();
                    if (num > 0) {
                        tv3.setVisibility(View.VISIBLE);
                        tv3.setText(num + "");
                    } else {
                        tv3.setVisibility(View.INVISIBLE);
                    }
            }
        });

        mRxManager.on("stateless", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                if (num > 0) {
                    tv4.setVisibility(View.VISIBLE);
                } else {
                    tv4.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRxManager.on("remarkUnread", new Action1<String>() {
            @Override
            public void call(String s) {
                int num= SingleBeans.getInstance().getUnReadBean().getReamrkNum();
                if (num > 0) {
                    tv4.setVisibility(View.VISIBLE);
                } else {
                    tv4.setVisibility(View.INVISIBLE);
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
                            tv4.setVisibility(View.VISIBLE);
                        } else {
                            tv4.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initChat() {
        DemoHelper.getInstance().init(DemoApplication.getContext());
        DemoHelper.getInstance().setCurrentUserName(UserInfoProvider.getUserID());
        DemoHelper.getInstance().initHandler(this.getMainLooper());
        linkChat();
        EMClient.getInstance().updateCurrentUserNick(UserInfoProvider.getNickName());
         //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

    }

        //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  linkChat();
                   /* if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){
                            //连接不到聊天服务器
                        } else{
                            //当前网络不可用，请检查网络设置
                        }
                    }*/
                }
            });
        }
    }

    public void linkChat(){
        EMClient.getInstance().login(UserInfoProvider.getUserID(), "111111", new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                LogUtils.logd("登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                LogUtils.logd("登录聊天服务器失败！");
                linkChat();
            }

        });
    }
    private void initSliding() {
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);

        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.sliding_layout);

        //设置Sliding里的监听事件
        supportFragmentManager = getSupportFragmentManager();
        button_chat = (Button) menu.findViewById(R.id.button_chat);
        button_friend = (Button) menu.findViewById(R.id.button_friend);
        tv_chat_unread = menu.findViewById(R.id.tv_chat_unread);
        tv_friend_unread = menu.findViewById(R.id.tv_friend_unread);
        conversationListFragment = new ConversationListFragment();
        contactListFragment = new ContactListFragment();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(R.id.fragment_sliding, conversationListFragment).commit();

        button_chat.setOnClickListener(this);
        button_friend.setOnClickListener(this);
    }

    public void refreshConversationListFragment() {
        conversationListFragment.refresh();
    }

    private void initTabs() {

        //添加四个标签
        for (int i = 0; i < TabHostConstant.tabText.length; i++) {
            //创建一个新的标签，指定Tag,这个tab就是Fragment的tag
            TabHost.TabSpec tab = tabHost.newTabSpec("" + i);
            //设置标签的视图
            tab.setIndicator(getTabView(i));
            //添加到TabHost中
            //参数一：标签TabSpec
            //参数二：tab标签对应的Fragment的Class
            //参数三：Bundle    可传参数到指定的Fragment中
            tabHost.addTab(tab, TabHostConstant.fragments[i], null);
            //把tab左右的分隔线设置为透明图片
            tabHost.getTabWidget().setDividerDrawable(new ColorDrawable(0x00000000));
        }
        View mView1 = tabHost.getTabWidget().getChildAt(0);
        tv1 = (TextView) mView1.findViewById(R.id.tab_msg);
        int num_msg=SingleBeans.getInstance().getUnReadBean().getMesNum();
        if (num_msg > 0) {
            tv1.setVisibility(View.VISIBLE);
            tv1.setText(num_msg + "");
        } else {
            tv1.setVisibility(View.INVISIBLE);
        }

        View mView3 = tabHost.getTabWidget().getChildAt(1);
        tv3 = (TextView) mView3.findViewById(R.id.tab_msg);

        View mView4 = tabHost.getTabWidget().getChildAt(2);
        tv4 = (TextView) mView4.findViewById(R.id.tab_msg);
        tv4.setWidth(15);
        tv4.setHeight(15);

        int num=SingleBeans.getInstance().getUnReadBean().getAllMsgNum();
        int num_mg=SingleBeans.getInstance().getUnReadBean().getMesNum();
        int num_friend=SingleBeans.getInstance().getUnReadBean().getNewFriendNum();
        if (num > 0) {
            tv1.setVisibility(View.VISIBLE);
            tv1.setText(num + "");
        } else {
            tv1.setVisibility(View.INVISIBLE);
        }
        if (num_mg > 0) {
            tv_chat_unread.setVisibility(View.VISIBLE);
            tv_chat_unread.setText(num_mg + "");
        } else {
            tv_chat_unread.setVisibility(View.INVISIBLE);
        }
        if (num_friend > 0) {
            tv_friend_unread.setVisibility(View.VISIBLE);
            tv_friend_unread.setText(num_friend + "");
        } else {
            tv_friend_unread.setVisibility(View.INVISIBLE);
        }
    }

    private View getTabView(int i) {
        //动态得到View-->tab_item的布局
        if (inflater == null) {
            inflater = LayoutInflater.from(this);
        }
        View view = inflater.inflate(R.layout.tab_item_layout, null);
        //设置view中的控件（ImageView,Text)相关属性
        ImageView iv = (ImageView) view.findViewById(R.id.tab_iv);
        TextView tv = (TextView) view.findViewById(R.id.tab_tv);
        iv.setImageResource(TabHostConstant.tabImgIds[i]);
        tv.setText(TabHostConstant.tabText[i]);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_chat://聊天
                FragmentTransaction transaction1 = supportFragmentManager.beginTransaction();
                transaction1.replace(R.id.fragment_sliding, conversationListFragment).commit();
                button_friend.setBackgroundColor(getResources().getColor(R.color.black));
                button_chat.setBackground(ContextCompat.getDrawable(this, R.drawable.slidingtop));
                break;
            case R.id.button_friend://好友
                FragmentTransaction transaction2 = supportFragmentManager.beginTransaction();
                transaction2.replace(R.id.fragment_sliding, contactListFragment).commit();
                button_chat.setBackgroundColor(getResources().getColor(R.color.black));
                button_friend.setBackground(ContextCompat.getDrawable(this, R.drawable.slidingtop));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void showMenu() {
        menu.showMenu();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();

    }

    boolean isFirst = true;
    long lastTime;

    //在按下返回键的时候会回调
    public void onBackPressed() {
        if (isFirst) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            lastTime = System.currentTimeMillis();
            isFirst = false;
        } else {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime <= 2000) {
                finish();
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            }
        }
    }


}
