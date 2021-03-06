package zhuozhuo.com.zhuo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.easeui.events.RxBusConstants;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;
import rx.functions.Action1;
import zhuozhuo.com.zhuo.view.activity.TeamWindowActivity;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SocketService extends Service {
    protected String TAG = "SocketService" + "  -->  ";
    private MyThread mThread;
    private WebSocketClient webSocketClient;
    private StringBuilder buff;
    private MyBinder binder;
    private Boolean flag = true;
    private JSONObject object;
    private RxManager manager;


    private void initBuff() {
        buff = new StringBuilder();
        buff.append("{token:'" + UserInfoProvider.getToken());
        buff.append("',action: 'open',user_id:");
        String user_id = UserInfoProvider.getUserID();
        buff.append(user_id + "}");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            binder = new MyBinder();
        }
        manager = new RxManager();

        initSocket();//连接Socket

        initBuff();//初始化心跳包数据

        initMsg();//注册发送消息的监听
        return binder;
    }

    private void initMsg() {
        manager.on(RxBusConstants.SOCKET_SEND, new Action1<String>() {
            @Override
            public void call(String s) {
               binder.sendMsg(s);
            }
        });
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                try {
                    binder.sendMsg(buff.toString());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initSocket() {
        try {
            webSocketClient = new WebSocketClient(new URI("http://119.23.251.79:8080/")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    LogUtils.logd(TAG + "Socket连接成功");
                    flag = true;
                    mThread = new MyThread();
                    mThread.start();
                }

                @Override
                public void onMessage(String message) {
                    LogUtils.logd(TAG + "Socket收到信息" + message);
                    try {
                        object = new JSONObject(message);
                        if (object.has("method")) {
                            String method = object.getString("method");
                            if (method.equals("unread")) {
                                SingleBeans.getInstance().getUnReadBean().setComNum(object.getInt("unread_share_count"));
                                manager.post(method,message);
                            }else if (method.equals("stateless")){
                                SingleBeans.getInstance().getUnReadBean().setReamrkNum(1);
                                manager.post(method,message);
                            } else if (method.equals("match_line")){
                                Intent intent=new Intent(SocketService.this, TeamWindowActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                String type = object.getString("type");
                                if (type.equals("9")) {
                                    manager.post(method,message);
                                }else if (type.equals("1001")||type.equals("1002")){
                                    manager.post(method,message);
                                }else if (type.equals("1003")){
                                    SingleBeans.getInstance().getUnReadBean().setReamrkNum(1);
                                    manager.post(method,message);
                                }else if (type.equals("2")) {
                                    String group_name = object.getString("group_name");
                                    intent.putExtra("type",type);
                                    intent.putExtra("group_name",group_name);
                                    startActivity(intent);
                                }else if(type.equals("3000")||type.equals("4000")){
                                    manager.post(method,message);
                                }else {
                                    String user_id = object.getString("user_id");
                                    String group_id = object.getString("group_id");
                                    String line_id = object.getString("line_id");
                                    String nick_name = object.getString("nick_name");
                                    String group_name = object.getString("group_name");

                                    intent.putExtra("type",type);
                                    intent.putExtra("group_name",group_name);
                                    intent.putExtra("user_id",user_id);
                                    intent.putExtra("group_id",group_id);
                                    intent.putExtra("line_id",line_id);
                                    intent.putExtra("nick_name",nick_name);
                                    startActivity(intent);
                                }
                            }else {
                                manager.post(method,message);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    LogUtils.logd(TAG + "webSocketClient关闭");
                    flag = false;
                    initSocket();
                }

                @Override
                public void onError(Exception ex) {
                    LogUtils.logd(TAG + "webSocketClient连接失败");
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public class MyBinder extends Binder {
        public void sendMsg(String jsonstring) {
            if (!(jsonstring == null)) {
                LogUtils.logd(TAG + "WebSocket发送消息" + jsonstring);
                JSONObject object;
                try {
                    object = new JSONObject(jsonstring);
                    webSocketClient.send(object.toString());
                } catch (Exception e) {
                    initSocket();
                    try {
                        object = new JSONObject(jsonstring);
                        webSocketClient.send(object.toString());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        flag = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       manager.clear();
    }
}
