package com.hyphenate.chatuidemo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.hyphenate.chatuidemo.adapter.TagRecycleViewAdapter;
import com.hyphenate.chatuidemo.my.CreatTeamActivity;
import com.hyphenate.chatuidemo.my.CupActivity;
import com.hyphenate.chatuidemo.my.InviteMemberActivity;
import com.hyphenate.chatuidemo.my.SetTagActivity;
import com.hyphenate.chatuidemo.my.TeamActivity;
import com.hyphenate.chatuidemo.my.TeamMenuActivity;
import com.hyphenate.chatuidemo.my.bean.IsRemarkBean;
import com.hyphenate.chatuidemo.my.bean.TagBean;
import com.hyphenate.easeui.HundredCupBean;
import com.hyphenate.chatuidemo.my.constract.ChatFragmentConstract;
import com.hyphenate.chatuidemo.domain.EmojiconExampleGroupData;
import com.hyphenate.chatuidemo.domain.RobotUser;
import com.hyphenate.chatuidemo.my.bean.GroupChoiceTitle;

import li.com.base.basesinglebean.SingleBeans;

import com.hyphenate.chatuidemo.my.bean.ReceiveGroupStepBean;
import com.hyphenate.chatuidemo.my.bean.ReceiveGroupStepBusBean;
import com.hyphenate.chatuidemo.my.CameActivity;
import com.hyphenate.chatuidemo.my.TableActivity;
import com.hyphenate.chatuidemo.my.Untils.CountDownUtil;
import com.hyphenate.chatuidemo.my.Untils.DialogUtils;
import com.hyphenate.chatuidemo.my.Untils.ImageUtil;
import com.hyphenate.chatuidemo.my.VideoPlayActivity;
import com.hyphenate.chatuidemo.my.https.UICallback;
import com.hyphenate.chatuidemo.my.https.UIDispatcher;
import com.hyphenate.chatuidemo.my.https.UpLoad;
import com.hyphenate.chatuidemo.my.model.ChatFragmentModel;
import com.hyphenate.chatuidemo.my.okhttp.ToastUtils;
import com.hyphenate.chatuidemo.my.presenter.ChatFragmentPresenter;
import com.hyphenate.easeui.provider.UserInfoProvider;
import com.hyphenate.chatuidemo.widget.BottomDialog;
import com.hyphenate.chatuidemo.widget.ChatRowVoiceCall;
import com.hyphenate.chatuidemo.widget.EaseChatRowRecall;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.events.RxBusConstants;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.TeamRecycleView;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.PathUtil;
import com.umeng.analytics.MobclickAgent;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import li.com.base.baserx.RxManager;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.baseuntils.ToastUitl;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;


public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper, View.OnClickListener, ChatFragmentConstract.View, RadioGroup.OnCheckedChangeListener {

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "ZhuoCropImage";
    // 要申请的权限
    private String[] permissions3 = {Manifest.permission.CAMERA};

    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    private static final int TAKE_VIDEO = 12321;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;
    private static final int REQUEST_CODE_TAKE_VIDEO = 12322;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;
    private static final int MESSAGE_TYPE_RECALL = 9;

    protected static final int CODE_CAMERA = 1000;
    protected static final int CODE_LOCAL = 2000;
    protected static final int CODE_CREATTEAM = 1100;
    private String i;
    private UIDispatcher UIDispatcher;
    private boolean flag = true;
    private boolean isTimeStart = false;

    /**
     * if it is chatBot
     */
    private boolean isRobot;
    private int step = 0;
    private String objectName;
    private String video_id;
    private DialogUtils dialogUtils;
    private AlertDialog alertDialog;
    private EditText editText;
    private String is_upload = "0";
    private BottomDialog dialog1;
    private RxManager rxManager;
    private StringBuffer buffer = new StringBuffer();
    private ChatFragmentPresenter mPresenter;
    private ChatFragmentModel mModel;
    private boolean isMatch = false;//
    private BottomDialog bottomDialog;
    private String mystatus;
    private HundredCupBean cupBean;
    private String choice_id;
    private List<TagBean.DataBean> list = new ArrayList<>();
    public TagRecycleViewAdapter tagRecycleViewAdapter;
    int startTime = 0;
    int stopTime = 0;
    public int num = 0;
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            try {
                Change(num);  //改变背景色
            }catch (Exception e){
                e.printStackTrace();
            }
                num++;                 //依次下一个
                  //如果到了最后一个item，则循环
            if (num >= 12) {
                num = 0;
            }
        }
    };
    private TagBean tagBean_;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UIDispatcher = new UIDispatcher(Looper.getMainLooper());
        initRxMange();
        dialogUtils = new DialogUtils(getActivity());
        mPresenter = new ChatFragmentPresenter();
        mModel = new ChatFragmentModel();
        mPresenter.setVM(mModel, this);

        return super.onCreateView(inflater, container, savedInstanceState,
                DemoHelper.getInstance().getModel().isMsgRoaming() && (chatType != EaseConstant.CHATTYPE_CHATROOM));
    }

    @Override
    protected void setUpView() {
        mPresenter.isEvaluate(toChatUsername);
        setVisibility(View.GONE);
        hundred_bt.setOnClickListener(this);
        match_input.init();
        match_input.setNumColumns(2);
        match_input.registerMenuItem("小视频", itemdrawables[0], TAKE_VIDEO, extendMenuItemClickListener);
        match_input.registerMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);

        button.setOnClickListener(this);
        button_upload.setOnClickListener(this);
        group_button.setOnClickListener(this);
        button_yuan.setOnClickListener(this);
        group1.setOnCheckedChangeListener(this);
        group2.setOnCheckedChangeListener(this);
        group3.setOnCheckedChangeListener(this);
        group4.setOnCheckedChangeListener(this);
        button_7.setOnClickListener(this);
        hundred_bt1.setOnClickListener(this);
        hundred_bt2.setOnClickListener(this);
        hundred_bt2.setText("积分表&对战表");
        hundred_bt3.setOnClickListener(this);
        bt_register_cup.setOnClickListener(this);
        bt_start.setOnClickListener(this);

        tagRecycleViewAdapter = new TagRecycleViewAdapter(list, getContext());
        rv_tag.setAdapter(tagRecycleViewAdapter);

        rv_team.setOnItemClickListener_(new TeamRecycleView.MyAdapter.OnItemClick() {
            @Override
            public void goTeamChat(HundredCupBean.DataBean dataBean) {
                if (dataBean != null) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId", dataBean.getGroup_id());
                    intent.putExtra("userName", dataBean.getGroup_name());
                    intent.putExtra(com.hyphenate.chatuidemo.Constant.EXTRA_CHAT_TYPE, com.hyphenate.chatuidemo.Constant.CHATTYPE_GROUP);
                    startActivity(intent);
                }
            }


            @Override
            public void inviteMember() {
                Intent intent = new Intent(getContext(), InviteMemberActivity.class);
                intent.putExtra("hundred_id", cupBean.getHundred_id());
                intent.putExtra("line_id", cupBean.getData().get(0).getLine_id());
                getActivity().startActivity(intent);
            }

            @Override
            public void onLongClick(String type, int index) {
                Intent intent = new Intent(getContext(), TeamMenuActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("line_id", cupBean.getData().get(index).getLine_id());
                intent.putExtra("you_user_id", cupBean.getData().get(index).getUser_id());
                intent.putExtra("group_id", cupBean.getData().get(index).getGroup_id());
                startActivity(intent);
            }
        });
        setChatFragmentHelper(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {

            Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
            setVisibility(View.GONE);
            setNormal_input();
           /* for (int j = 0; j < SingleBeans.getInstance().getMatchPersonBeans().size(); j++) {
                if (SingleBeans.getInstance().getMatchPersonBeans().get(j).getUser_id().equals(toChatUsername)){
                    buffer.append("{action: step,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                    sendSocketMsg();
                }
            }*/

            if (SingleBeans.getInstance().getMatchPersonBeans().size() > 0) {
                for (int j = 0; j < SingleBeans.getInstance().getMatchPersonBeans().size(); j++) {
                    if (SingleBeans.getInstance().getMatchPersonBeans().get(j).isMe(toChatUsername)) {
                        isMatch = true;
                        //setVisibility(View.VISIBLE);
                        //setMatch_input();
                        break;
                    }
                }
            }

        }
        super.setUpView();
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    if (isMatch) {
                        bottomDialog = BottomDialog.newInstance("", new String[]{"查看资料", "放弃匹配", "举报", "清空记录"});
                        bottomDialog.show(getFragmentManager(), "dialog");
                        bottomDialog.setListener(new BottomDialog.OnClickListener() {
                            @Override
                            public void click(int position) {
                                switch (position) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
                                        intent.putExtra("username", toChatUsername);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        layout_chatted.setVisibility(View.VISIBLE);
                                        bottomDialog.dismiss();
                                        break;
                                    case 2:
                                        //TODO 发送举报信息
                                        bottomDialog.dismiss();
                                        break;
                                    case 3:
                                        emptyHistory();
                                        break;
                                }
                            }
                        });
                    } else {
                        bottomDialog = BottomDialog.newInstance("", new String[]{"查看资料", "举报", "清空记录"});
                        bottomDialog.show(getFragmentManager(), "dialog");
                        bottomDialog.setListener(new BottomDialog.OnClickListener() {
                            @Override
                            public void click(int position) {
                                switch (position) {
                                    case 0:
                                        Intent intent = new Intent(getContext(), UserProfileActivity.class);
                                        intent.putExtra("username", toChatUsername);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        //TODO 发送举报信息
                                        bottomDialog.dismiss();
                                        break;
                                    case 2:
                                        emptyHistory();
                                        break;
                                }
                            }
                        });
                    }

                } else {
                    toGroupDetails();
                }
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            mPresenter.getDetail(toChatUsername);
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void sendSocketMsg() {
        rxManager.post(RxBusConstants.SOCKET_SEND, buffer.toString());
        buffer.delete(0, buffer.length());
    }

    public void setGroupUnComplete() {
        frameLayout.setVisibility(View.VISIBLE);
        group_ll.setVisibility(View.VISIBLE);
        group_tv.setText(GroupChoiceTitle.getGroupChoiceTitle().getTitle());
        group_button.setText("完成");
        group_button.setClickable(true);
    }

    public void setGroupComplete() {
        frameLayout.setVisibility(View.VISIBLE);
        group_ll.setVisibility(View.VISIBLE);
        group_tv.setText(GroupChoiceTitle.getGroupChoiceTitle().getTitle());
        group_button.setText("已完成");
        group_button.setClickable(false);
        group_button.setBackgroundColor(Color.GRAY);
        new RxManager().post("teamlist", "");
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        //inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        //inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            // inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_CREATTEAM) {
            mPresenter.getDetail(toChatUsername);
        } else if (requestCode == CODE_CAMERA) {
            if (cameraFile != null && cameraFile.exists()) {
                doUCrop(Uri.fromFile(cameraFile));
            }
        } else if (requestCode == CODE_LOCAL) {
            if (data != null) {
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    doUCrop(selectedImage);
                }
            }
        } else if (requestCode == REQUEST_CODE_TAKE_VIDEO && resultCode == RESULT_OK) {
            Uri uri = data.getParcelableExtra("uri");
            String[] projects = new String[]{MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DURATION};
            Cursor cursor = getActivity().getContentResolver().query(
                    uri, projects, null,
                    null, null);
            int duration = 0;
            String filePath = null;
            if (cursor.moveToFirst()) {
                // path：MediaStore.Audio.Media.DATA
                filePath = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                // duration：MediaStore.Audio.Media.DURATION
                duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(filePath, 1);
                    ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    sendVideoMessage(filePath, file.getAbsolutePath(), duration);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        } else if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;
                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);
                    break;
                case ContextMenuActivity.RESULT_CODE_RECALL://recall
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMMessage msgNotification = EMMessage.createTxtSendMessage(" ", contextMenuMessage.getTo());
                                EMTextMessageBody txtBody = new EMTextMessageBody(getResources().getString(R.string.msg_recall_by_self));
                                msgNotification.addBody(txtBody);
                                msgNotification.setMsgTime(contextMenuMessage.getMsgTime());
                                msgNotification.setLocalTime(contextMenuMessage.getMsgTime());
                                msgNotification.setAttribute(Constant.MESSAGE_TYPE_RECALL, true);
                                EMClient.getInstance().chatManager().recallMessage(contextMenuMessage);
                                EMClient.getInstance().chatManager().saveMessage(msgNotification);
                                messageList.refresh();
                            } catch (final HyphenateException e) {
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                    break;

                default:
                    break;
            }
        }
        switch (requestCode) {
            case UCrop.REQUEST_CROP:
                try {
                    if (UCrop.getOutput(data) != null) {
                        final Uri resultUri = UCrop.getOutput(data);
                        if (resultUri != null) {
                            try {
                                dialog1.dismiss();
                                String savePath = PathUtil.getInstance().getImagePath() + EMClient.getInstance().getCurrentUser() + System.currentTimeMillis() + ".jpg";
                                ImageUtil.photoAddText(getContext()
                                        , getPicByUri(resultUri)
                                        , UserInfoProvider.getNickName() + "&" + UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(toChatUsername).getNick_name()
                                        , savePath);
                                updata(savePath);
                            } catch (ClientException e) {
                                e.printStackTrace();
                            } catch (ServiceException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception NullPointerException) {

                }
                break;
            case REQUEST_CODE_SELECT_AT_USER:
                if (data != null) {
                    String username = data.getStringExtra("username");
                    inputAtUsername(username, false);
                }
                break;
            case REQUEST_CODE_GROUP_DETAIL:
                if (resultCode == GroupDetailsActivity.RESULT_QUIT_GROUP) {
                    getActivity().finish();
                    EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                    rxManager.post(RxBusConstants.UPDATE_HUNDRED, "");
                }
                break;
            case 6688:
                layout_chatted.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    public void doUCrop(Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getActivity().getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(9, 16);
        // uCrop = uCrop.withMaxResultSize(890, 1582);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        uCrop.withOptions(options);
        uCrop.start(getContext(), this, UCrop.REQUEST_CROP);
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    @Override
    public void takePhoto() {
        doPicFromCamera();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult(
                    (new Intent(getActivity(), GroupDetailsActivity.class).putExtra("groupId", toChatUsername)).putExtra("quitGroup", quitGroup),
                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
        if (username.equals("admin")) {

        } else {
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
        //end of red packet code
        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    str = "拍照";
                    // 检查该权限是否已经获取
                    int j = ContextCompat.checkSelfPermission(getContext(), permissions3[0]);
                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                    if (j != PackageManager.PERMISSION_GRANTED) {
                        // 如果没有授予该权限，就去提示用户请求
                        // showDialogTipUserRequestPermission();
                        startRequestPermission(permissions3);
                    } else {
                        // 检查该权限是否已经获取
                        str = "录音";
                        int i = ContextCompat.checkSelfPermission(getContext(), permissions2[0]);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (i != PackageManager.PERMISSION_GRANTED) {
                            // 如果没有授予该权限，就去提示用户请求
                            // showDialogTipUserRequestPermission();
                            startRequestPermission(permissions2);
                        } else {
                            startVideoCall();
                        }
                    }
                } else {
                    startVideoCall();
                }
                break;
            case TAKE_VIDEO:
                // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    str = "拍照";
                    // 检查该权限是否已经获取
                    int j = ContextCompat.checkSelfPermission(getContext(), permissions3[0]);
                    // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                    if (j != PackageManager.PERMISSION_GRANTED) {
                        // 如果没有授予该权限，就去提示用户请求
                        // showDialogTipUserRequestPermission();
                        startRequestPermission(permissions3);
                    } else {
                        // 检查该权限是否已经获取
                        str = "录音";
                        int i = ContextCompat.checkSelfPermission(getContext(), permissions2[0]);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (i != PackageManager.PERMISSION_GRANTED) {
                            // 如果没有授予该权限，就去提示用户请求
                            // showDialogTipUserRequestPermission();
                            startRequestPermission(permissions2);
                        } else {
                            takeVideo("false");
                        }
                    }
                } else {
                    takeVideo("false");
                }

                break;
        }
        //keep exist extend menu
        return false;
    }


    public void takeVideo(String flag) {
        Intent intentVideo = new Intent();
        //intentVideo.setClass(getActivity(), CameActivity.class);
        intentVideo.setClass(getActivity(), CameActivity.class);
        intentVideo.putExtra("flag", flag);
        startActivityForResult(intentVideo, REQUEST_CODE_TAKE_VIDEO);
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }


    @Override
    public void onClick(View view) {
        int i1 = view.getId();
        if (i1 == R.id.chat_next) {
            if (title.length == 1) {
                layout_chatted.setVisibility(View.VISIBLE);
            } else {
                if ((step + 1) == title.length) {
                    button.setText("视频制作中...");
                    dialogUtils.showWaitDialog("视频制作中...", false);
                    buffer.append("{action: next,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + " ,total_step: " + title.length + ",video_id:" + video_id + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                    sendSocketMsg();
                    new getVideoThread().start();
                    mPresenter.changeFinish(toChatUsername);
                } else {
                    button.setText("请等待对方回应");
                    buffer.append("{action: next,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + " ,total_step: " + title.length + ",video_id:" + video_id + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                    sendSocketMsg();
                }
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
                button.setClickable(false);
            }

        } else if (i1 == R.id.button_upload) {
            chooseImage();
            buffer.append("{action: next,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + " ,total_step: " + (step + 1) + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
            sendSocketMsg();
        } else if (i1 == R.id.group_button) {
            editText = new EditText(getContext());
            editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("给对方些评语吧")
                    .setView(editText)
                    .setNegativeButton("滚,不评了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rxManager.post("group_remark_", "");
                            alertDialog.dismiss();
                            buffer.append("{" + "action: groupstep,token:" + UserInfoProvider.getToken() + ",group_id:" + toChatUsername + "}");
                            sendSocketMsg();
                            setGroupComplete();
                            rxManager.post("leave_team", "");
                        }
                    })
                    .setPositiveButton("提交评论", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String comment = String.valueOf(editText.getText());
                            if (comment == null) {
                                ToastUitl.showLong("评论不能为空");
                            } else {
                                rxManager.post("group_remark", comment);
                                alertDialog.dismiss();
                                buffer.append("{" + "action: groupstep,token:" + UserInfoProvider.getToken() + ",group_id:" + toChatUsername + "}");
                                sendSocketMsg();
                                setGroupComplete();
                            }
                            rxManager.post("leave_team", "");
                        }
                    })
                    .create();
            alertDialog.show();
        } else if (i1 == R.id.button_yuan) {
            String s = String.valueOf(et_yuan.getText());
            ToastUtils.showToast("修改成功");
            mPresenter.setMoney(s, toChatUsername);
        } else if (i1 == R.id.hundred_bt) {
            Intent intent = new Intent(getContext(), TableActivity.class);
            intent.putExtra("group_id", toChatUsername);
            startActivity(intent);
        } else if (i1 == R.id.tijiao_button) {
            if (socor == null) {
                ToastUtils.showToast("请选择分数");
            } else if (card == null) {
                ToastUtils.showToast("请选择卡片");
            } else {
                mPresenter.remark(toChatUsername, socor, card);
            }
        } else if (i1 == R.id.hundred_bt_1) {
            Intent intent = new Intent(getContext(), CreatTeamActivity.class);
            startActivityForResult(intent, CODE_CREATTEAM);
        } else if (i1 == R.id.hundred_bt_2) {
            Intent intent = new Intent(getContext(), CupActivity.class);
            intent.putExtra("hunderd_id", cupBean.getHundred_id());
            startActivity(intent);
        } else if (i1 == R.id.bt_register_cup) {
            mPresenter.teamRegister(cupBean.getLine_id(), cupBean.getHundred_id());
        } else if (i1 == R.id.hundred_bt_3) {
            startActivity(new Intent(getContext(), TeamActivity.class));
        } else if (i1 == R.id.bt_start) {
            mPresenter.getRandom(choice_id,toChatUsername);
        }
    }

    private void doPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                CODE_CAMERA);
    }

    private void doPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, CODE_LOCAL);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
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

    //单人匹配评价成功
    @Override
    public void remarkSucess() {
        if (!DemoHelper.getInstance().getContactList().containsKey(toChatUsername)) {
            EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
        }
        getActivity().finish();
        //评价完成，清空所有ButtonGroup状态
        group1.setOnCheckedChangeListener(null);
        group2.setOnCheckedChangeListener(null);
        group3.setOnCheckedChangeListener(null);
        group1.clearCheck();
        group2.clearCheck();
        group3.clearCheck();
        group1.setOnCheckedChangeListener(this);
        group2.setOnCheckedChangeListener(this);
        group3.setOnCheckedChangeListener(this);
        rxManager.post(RxBusConstants.UPDATE_ALL_MATCH, "6666");
    }

    //设置单人匹配步骤图片成功
    @Override
    public void setStepPhtotSucess() {
        is_upload = "1";
        setButtonUnClick(button_upload);
        button.setVisibility(View.VISIBLE);
        if ((step + 1) == title.length) {
            mPresenter.startVideo(video_id);
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.m_color4));
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape2));
            button.setText("完成");
            if (!isTimeStart) {
                button.setClickable(false);
                CountDownUtil countDownUtil = new CountDownUtil(button, 60 * 1000, "", "完成");
                countDownUtil.setSetClick(true);
                countDownUtil.start();
                isTimeStart = true;
            }
        } else {
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
            button.setText("请等待对方完成");
            buffer.append("{action: next,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + " ,total_step: " + title.length + ",video_id:" + video_id + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
            sendSocketMsg();
        }
        button.setClickable(false);
    }

    //设置组队匹配金额成功
    @Override
    public void setMoneySucess() {

    }

    @Override
    public void returnVieo(String video_link) {
        if (!video_link.equals("")) {
            Intent intent = new Intent(getContext(), VideoPlayActivity.class);
            intent.putExtra("url", video_link);
            startActivityForResult(intent, 6688);
        }
        dialogUtils.closeDialog();
        isTimeStart = false;
        flag = false;
    }

    @Override
    public void returnHundredDetail(HundredCupBean hundredCupBean) {
        cupBean = hundredCupBean;
        if (hundredCupBean.getFinish() == null) {
            quitGroup = false;
        } else {
            if (hundredCupBean.getFinish().equals("1")) {
                hundred_tv3.setText(hundredCupBean.getConditions());
                rv_team.upadteList(hundredCupBean.getData());
                frameLayout.setVisibility(View.VISIBLE);
                chatlinearLayout.setVisibility(View.GONE);
                setNormal_input();
                group_ll.setVisibility(View.GONE);
                hundred_rl.setVisibility(View.VISIBLE);
                hundred_tv2.setVisibility(View.GONE);
                hundred_bt.setVisibility(View.GONE);
                hundred_tv3.setVisibility(View.VISIBLE);
                hundred_bt1.setVisibility(View.VISIBLE);
                hundred_bt2.setVisibility(View.VISIBLE);
                if (hundredCupBean.getData().size() > 0) {
                    rv_team.setVisibility(View.VISIBLE);
                    hundred_ll.setVisibility(View.GONE);
                    SingleBeans.getInstance().setTeam(true);
                    SingleBeans.getInstance().setLine_id(hundredCupBean.getLine_id());
                } else {
                    rv_team.setVisibility(View.GONE);
                    hundred_ll.setVisibility(View.VISIBLE);
                    SingleBeans.getInstance().setTeam(false);
                }
                if (hundredCupBean.getLine_match().equals("1")) {
                    bt_register_cup.setVisibility(View.VISIBLE);
                    bt_register_cup.setText("已报名");
                    bt_register_cup.setClickable(false);
                } else {
                    if (hundredCupBean.getData().size() == 5) {
                        if (hundredCupBean.getData().get(0).getUser_id().equals(UserInfoProvider.getUserID())) {
                            bt_register_cup.setVisibility(View.VISIBLE);
                            bt_register_cup.setText("报名");
                            bt_register_cup.setClickable(true);
                        } else {
                            bt_register_cup.setVisibility(View.VISIBLE);
                            bt_register_cup.setText("等待队长报名");
                            bt_register_cup.setClickable(false);
                        }
                    } else {
                        bt_register_cup.setVisibility(View.GONE);
                    }
                }

            } else {
                buffer.append("{" + "action: groupstep,token:" + UserInfoProvider.getToken() + ",group_id:" + toChatUsername + "}");
                sendSocketMsg();
            }
        }
    }

    @Override
    public void teamRegisterSucess() {
        ToastUitl.showLong("报名成功");
        mPresenter.getDetail(toChatUsername);
    }

    @Override
    public void returnTag(TagBean tagBean) {
        tagBean_ = tagBean;
        if (tagBean.getLabel_error() == 1) {
            Intent intent = new Intent(getContext(), SetTagActivity.class);
            intent.putExtra("choice_id", choice_id);
            intent.putExtra("you_user_id", toChatUsername);
            startActivity(intent);
        } else if (tagBean.getData().size() > 0) {
            list.clear();
            list.addAll(tagBean.getData());
            tagRecycleViewAdapter.notifyDataSetChanged();
            if (tagBean.getData().size() != 12) {
                bt_start.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
                bt_start.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
                bt_start.setText("等待对方设置标签");
                bt_start.setClickable(false);
            } else {
                bt_start.setTextColor(ContextCompat.getColor(getContext(), R.color.main_color));
                bt_start.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape2));
                bt_start.setText("开始");
                bt_start.setClickable(true);
            }
        }
    }

    @Override
    public void getIsEvaluateSucess(IsRemarkBean isRemarkBean) {
        choice_id=isRemarkBean.getChoice_id();
       if (isRemarkBean.getIs_status().equals("1")){
           layout_chatted.setVisibility(View.VISIBLE);
       }
        if (isRemarkBean.getType().equals("1")){
           frameLayout.setVisibility(View.GONE);
            setNormal_input();
        }else if (isRemarkBean.getType().equals("2")){
            frameLayout.setVisibility(View.VISIBLE);
            setNormal_input();
            chatlinearLayout.setVisibility(View.GONE);
            ll_tag.setVisibility(View.VISIBLE);
            mPresenter.getTag(choice_id, toChatUsername);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.button_0) {
            socor = "0";
        } else if (i == R.id.button_50) {
            socor = "5";
        } else if (i == R.id.button_100) {
            socor = "10";
        } else if (i == R.id.button_hrk) {
            card = "0";
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_gmk) {
            card = "1";
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_ptk) {
            card = "2";
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_hyk) {
            card = "3";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_jrk) {
            card = "4";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_lak) {
            card = "5";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group4.setOnCheckedChangeListener(null);
            group4.clearCheck();
            group4.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_card7) {
            card = "6";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_card8) {
            card = "7";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);

        } else if (i == R.id.button_card9) {
            card = "8";
            group2.setOnCheckedChangeListener(null);
            group2.clearCheck();
            group2.setOnCheckedChangeListener(this);
            group3.setOnCheckedChangeListener(null);
            group3.clearCheck();
            group3.setOnCheckedChangeListener(this);

        }
    }


    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            return 11;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //messagee recall
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    return MESSAGE_TYPE_RECALL;
                }
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
                //recall message
                else if (message.getBooleanAttribute(Constant.MESSAGE_TYPE_RECALL, false)) {
                    return new EaseChatRowRecall(getActivity(), message, position, adapter);
                }
            }
            return null;
        }

    }

    private void chooseImage() {
        dialog1 = BottomDialog.newInstance("", new String[]{"拍照", "从相册选择"});
        dialog1.show(getChildFragmentManager(), "dialog");
        dialog1.setListener(new BottomDialog.OnClickListener() {
            @Override
            public void click(int position) {
                switch (position) {
                    case 0:
                        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            str = "拍照";
                            // 检查该权限是否已经获取
                            int j = ContextCompat.checkSelfPermission(getContext(), permissions3[0]);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (j != PackageManager.PERMISSION_GRANTED) {
                                // 如果没有授予该权限，就去提示用户请求
                                // showDialogTipUserRequestPermission();
                                startRequestPermission(permissions3);
                            } else {
                                doPicFromCamera();
                            }
                        } else {
                            doPicFromCamera();
                        }
                        break;
                    case 1:
                        doPicFromLocal();
                        break;
                }
            }
        });
    }

    private void updata(String url) throws ClientException, ServiceException, IOException {
        /**
         * 上传服务器代码
         */
        setButtonUnClick(button_upload);
        objectName = "Single/" + UserInfoProvider.getUserID() + System.currentTimeMillis() + ".jpg";
        UpLoad.getInstance().beginUpLoad(objectName, url, getPutCallback());
    }

    //图片上传阿里服务器成功时回调
    public UICallback<PutObjectRequest, PutObjectResult> getPutCallback() {
        return new UICallback<PutObjectRequest, PutObjectResult>(UIDispatcher) {
            @Override
            public void onSuccess(final PutObjectRequest request, final PutObjectResult result) {
                addCallback(new Runnable() {
                    @Override
                    public void run() {
                        String url = "http://zhuozhuo.oss-cn-shenzhen.aliyuncs.com/" + objectName;
                        if (step == 0) {
                            mPresenter.setStepPhtot(url, toChatUsername, url);
                        } else {
                            mPresenter.setStepPhtot(url, toChatUsername, "");
                        }
                    }
                }, null);
                super.onSuccess(request, result);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

                setButtonClick(button_upload);
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    info = serviceException.toString();
                }
                addCallback(null, new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                onFailure(request, clientExcepion, serviceException);
            }
        };
    }

    protected String getPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;
            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(getContext(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            return picturePath;
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(getContext(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }
            return file.getAbsolutePath();
        }
    }

    public void setStep(int step, String mystatu) {
        if (title.length == 1) {
            setNormal_input();
            chatlinearLayout.setVisibility(View.GONE);
            ll_tag.setVisibility(View.VISIBLE);
            mPresenter.getTag(choice_id, toChatUsername);

        } else {
            if (step >= title.length) {
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
                button.setText("视频制作中...");
                dialogUtils.showWaitDialog("视频制作中...", false);
                mPresenter.changeFinish(toChatUsername);
                new getVideoThread().start();
            }
            if ((step + 1) == title.length) {
                button.setText("完成");
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.m_color4));
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape2));
                if (!isTimeStart) {
                    button.setClickable(false);
                    CountDownUtil countDownUtil = new CountDownUtil(button, 60 * 1000, "", "完成");
                    countDownUtil.setSetClick(true);
                    countDownUtil.start();
                    isTimeStart = true;
                }
            } else if (mystatu.equals("1")) {
                button.setText("请等待对方回应");
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
                button.setClickable(false);
            } else {
                button.setTextColor(ContextCompat.getColor(getContext(), R.color.m_color4));
                button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape2));
                button.setText("下一步");
            }
            if (is_upload.equals("0")) {
                setButtonClick(button_upload);
                button.setVisibility(View.GONE);
            } else {
                setButtonUnClick(button_upload);
                button.setVisibility(View.VISIBLE);
            }
            if (step <= title.length - 1) {
                tv_step.setText("step." + (step + 1));
                tv_title.setText(title[step]);
                tv_deteil.setText(detail[step]);
            } else {
                tv_title.setText("已完成");
                tv_deteil.setText("");
            }
            button.setClickable(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rxManager.clear();
    }

    public class getVideoThread extends Thread {
        @Override
        public void run() {
            while (flag) {
                try {
                    mPresenter.getVideo(toChatUsername);
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(getActivity(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Unexpected error", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRxMange() {
        rxManager = new RxManager();
        rxManager.on("step", new Action1<String>() {
            @Override
            public void call(String s) {
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.has("step_count")) {
                            if (jsonObject.get("l_user_id").equals(UserInfoProvider.getUserID())) {
                                i = "l_user_status";
                            } else if (jsonObject.get("r_user_id").equals(UserInfoProvider.getUserID())){
                                i = "r_user_status";
                            }else {
                                return;
                            }
                            step = jsonObject.getInt("step_count");
                            is_upload = jsonObject.getString("is_upload");
                            video_id = jsonObject.getString("video_id");
                            mystatus = jsonObject.getString(i);
                            int finish = jsonObject.getInt("finish");
                            int status = jsonObject.getInt("status");
                            int is_status = jsonObject.getInt("is_status");
                            choice_id = jsonObject.getString("choice_id");
                            List<SingleChooseBean> singleChooseBeans = SingleBeans.getInstance().getSingleChooseBeans();
                            for (int j = 0; j < singleChooseBeans.size(); j++) {
                                if (singleChooseBeans.get(j).getChoice_id().equals(choice_id)) {
                                    title = singleChooseBeans.get(j).getValue().split(",");
                                    detail = singleChooseBeans.get(j).getDesc().split(",");
                                }
                            }
                            if (finish == 1) {
                                dialogUtils.showWaitDialog("视频制作中...", false);
                                tv_title.setText("已完成");
                                tv_deteil.setText("");
                                tv_step.setText("");
                                new getVideoThread().start();
                            } else {
                                if (is_status == 1|| status == 1) {
                                    layout_chatted.setVisibility(View.VISIBLE);
                                    ToastUitl.showLong("对方已放弃，请评价完成此次匹配");
                                } else {
                                    setStep(step, mystatus);
                                }
                            }
                        } else {
                            layout_chatted.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rxManager.on("nextstep", new Action1<String>() {
            @Override
            public void call(String s) {
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.getString("l_user_id").equals(toChatUsername)||jsonObject.getString("r_user_id").equals(toChatUsername)){
                            step = jsonObject.getInt("step_count");
                            is_upload = "0";
                            addstep();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rxManager.on("stepupload", new Action1<String>() {
            @Override
            public void call(String s) {
                buffer.append("{action: step,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                sendSocketMsg();
            }
        });
        rxManager.on("stepvideo", new Action1<String>() {
            @Override
            public void call(String s) {
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        videoFinish(jsonObject.getString("video_link"), jsonObject.getString("video_name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rxManager.on("stateless", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("user_id").equals(toChatUsername)){
                        ToastUitl.showLong("对方已放弃，请评价完成此次匹配");
                        layout_chatted.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rxManager.on("groupstep", new Action1<String>() {
            @Override
            public void call(String s) {
                if (s != null) {
                    try {
                        JSONObject object = new JSONObject(s);
                        if (object.has("conditions")) {//百人群返回
                            ReceiveGroupStepBusBean busBean = new Gson().fromJson(s, ReceiveGroupStepBusBean.class);
                            frameLayout.setVisibility(View.VISIBLE);
                            chatlinearLayout.setVisibility(View.GONE);
                            setNormal_input();
                            group_ll.setVisibility(View.GONE);
                            hundred_rl.setVisibility(View.VISIBLE);
                            hundred_tv2.setVisibility(View.VISIBLE);
                            hundred_bt.setVisibility(View.VISIBLE);
                            hundred_tv3.setVisibility(View.GONE);
                            hundred_ll.setVisibility(View.GONE);
                            hundred_bt2.setVisibility(View.GONE);
                            int index = new Integer(busBean.getConditions_num());
                            String[] msg = busBean.getConditions().split(",");
                            hundred_tv2.setText(msg[index]);
                        } else {//组队匹配返回
                            if (object.has("group_match_status")) {
                                ReceiveGroupStepBean stepBean = new Gson().fromJson(s, ReceiveGroupStepBean.class);
                                if (stepBean.getMoney() == 1) {
                                    button_yuan.setVisibility(View.VISIBLE);
                                    tv1_yuan.setVisibility(View.VISIBLE);
                                    tv2_yuan.setVisibility(View.VISIBLE);
                                    et_yuan.setVisibility(View.VISIBLE);
                                    et_yuan.setText(stepBean.getChoice_money());
                                    group_tv.setText(stepBean.getChoice_desc());
                                    group_name.setText(stepBean.getChoice_number_name());
                                    if (stepBean.getGroup_match_status().equals("0")) {
                                        setGroupComplete();
                                    } else {
                                        setGroupUnComplete();
                                    }
                                } else if (stepBean.getMoney() == 0) {
                                    button_yuan.setVisibility(View.GONE);
                                    tv1_yuan.setVisibility(View.GONE);
                                    tv2_yuan.setVisibility(View.GONE);
                                    et_yuan.setVisibility(View.GONE);
                                    group_tv.setText(stepBean.getChoice_desc());
                                    group_name.setText(stepBean.getChoice_number_name());
                                    if (stepBean.getGroup_match_status().equals("0")) {
                                        setGroupComplete();
                                    } else {
                                        setGroupUnComplete();
                                    }
                                } else if (stepBean.getMoney() == 2) {
                                    frameLayout.setVisibility(View.VISIBLE);
                                    chatlinearLayout.setVisibility(View.GONE);
                                    setNormal_input();
                                    group_ll.setVisibility(View.GONE);
                                    hundred_rl.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ReceiveGroupStepBean bean = new ReceiveGroupStepBean();
                                bean.setMoney(-1);
                                frameLayout.setVisibility(View.VISIBLE);
                                chatlinearLayout.setVisibility(View.GONE);
                                setNormal_input();
                                group_ll.setVisibility(View.GONE);
                                hundred_rl.setVisibility(View.GONE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rxManager.on("wait", new Action1<String>() {
            @Override
            public void call(String s) {
                if (s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.getString("l_user_id").equals(toChatUsername)||jsonObject.getString("r_user_id").equals(toChatUsername)){
                            if (jsonObject.getInt("step_count") >= title.length) {
                                button.setText("视频制作中...");
                                dialogUtils.showWaitDialog("视频制作中...", false);
                                new getVideoThread().start();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        rxManager.on("next", new Action1<String>() {
            @Override
            public void call(String s) {
                buffer.append("{action: step,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                sendSocketMsg();
            }
        });

        rxManager.on("upadteTeam", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getDetail(toChatUsername);
            }
        });
        rxManager.on("RefrshMatch", new Action1<String>() {
            @Override
            public void call(String s) {
                if (chatType == Constant.CHATTYPE_SINGLE) {
                    Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
                    if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                        isRobot = true;
                    }
                    setVisibility(View.GONE);
                    setNormal_input();
                    if (SingleBeans.getInstance().getMatchPersonBeans().size() > 0) {
                        for (int j = 0; j < SingleBeans.getInstance().getMatchPersonBeans().size(); j++) {
                            if (SingleBeans.getInstance().getMatchPersonBeans().get(j).isMe(toChatUsername)) {
                                isMatch = true;
                                setVisibility(View.VISIBLE);
                                setMatch_input();
                                buffer.append("{action: step,token: " + UserInfoProvider.getToken() + ", user_id:" + UserInfoProvider.getUserID() + ",you_user_id  :" + toChatUsername + ",new_step:123" + "}");
                                sendSocketMsg();
                                break;
                            }
                        }
                    }
                }
            }
        });
        rxManager.on("match_line", new Action1<String>() {
            @Override
            public void call(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    final String type = object.getString("type");
                    if (type.equals("9") || type.equals("2")) {
                        mPresenter.getDetail(toChatUsername);
                    } else if (type.equals("1001")) {
                        if (tagBean_!=null&&!(tagBean_.getLabel_error() == 1)) {
                            mPresenter.getTag(choice_id, toChatUsername);
                        }
                    }else if (type.equals("1002")){
                        int random_num=object.getInt("random_num");
                        String id=object.getString("random_user_id");
                        if (id.equals(toChatUsername)||id.equals(UserInfoProvider.getUserID())){
                            CountDownUtil countDownUtil=new CountDownUtil(bt_start,6*1000,"s","开始");
                            countDownUtil.setSetClick(true);
                            countDownUtil.start();
                            tagRecycleViewAdapter.notifyDataSetChanged();
                            startTime = 0;
                            stopTime = 2400+(random_num-num-1)*100;
                            runnable.run();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rxManager.on("GetTag", new Action1<String>() {
            @Override
            public void call(String s) {
                mPresenter.getTag(choice_id, toChatUsername);
            }
        });

    }

    private void videoFinish(String video_link, String video_name) {
        dialogUtils.closeDialog();
        flag = false;

    }

    private void donext() {
        is_upload = "1";
        setButtonUnClick(button_upload);
        button.setVisibility(View.VISIBLE);
    }

    private void setButtonUnClick(Button button) {
        button.setClickable(false);
        button.setBackgroundResource(R.drawable.takephoto_check);
    }

    private void setButtonClick(Button button) {
        button.setClickable(true);
        button.setBackgroundResource(R.drawable.takephoto_normal);
    }

    private void addstep() {
        if (step < title.length) {
            setStep(step, "0");
        } else {
            //图片步骤完成
            button.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_pressed));
            button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.button_shape1));
            button.setText("视频制作中...");
            dialogUtils.showWaitDialog("视频制作中...", false);
            button.setClickable(false);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面，"MainScreen"为页面名称，可自定义
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }



    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);  //发送消息
            //如果到达指定的时间,则停止
            if (startTime >= stopTime) {
                if (list.size()>=num){
                    ((TextView) (rv_tag.getChildAt(num).findViewById(R.id.tv_tag_item))).setText(list.get(num).getLabel_name());
                }
                startTime = 0;
                stopTime = 0;
                return;
            }
            if ((stopTime-startTime)<700){
                //每隔100毫秒运行一次
                handler.postDelayed(runnable, startTime+700-stopTime);
            }else {
                //每隔100毫秒运行一次
                handler.postDelayed(runnable, 100);
            }
            startTime += 100;
        }
    };

    public void Change(int id) {
        for (int i = 0; i < rv_tag.getChildCount(); i++) {
            if (i == id) {
                //如果是选中的，则改变图片为数组2中的图片
                ((TextView) (rv_tag.getChildAt(i).findViewById(R.id.tv_tag_item))).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.maincolor_shape_));
            } else {
                //未选中的就设置为数组1中的图片
                ((TextView) (rv_tag.getChildAt(i).findViewById(R.id.tv_tag_item))).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.maincolor_shape));
            }
        }
    }
}