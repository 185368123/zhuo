package com.hyphenate.chatuidemo.ui;

import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.db.InviteMessgeDao;
import com.hyphenate.chatuidemo.my.adatper.UnreadListAdapter;
import com.hyphenate.chatuidemo.my.constract.GetTeamUnreadConstract;
import com.hyphenate.chatuidemo.my.constract.TheBaseConstract;
import com.hyphenate.chatuidemo.my.model.GetTeamUnreadModel;
import com.hyphenate.chatuidemo.my.model.TheBaseModel;
import com.hyphenate.chatuidemo.my.presenter.GetTeamUnreadPresenter;
import com.hyphenate.chatuidemo.my.presenter.TheBasePresenter;
import com.hyphenate.easeui.TeamUnreadBean;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.util.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class ConversationListFragment extends EaseConversationListFragment implements GetTeamUnreadConstract.View, TheBaseConstract.View, UnreadListAdapter.onButtonClick {

    private TextView errorText;
    private GetTeamUnreadPresenter unreadPresenter;
    private GetTeamUnreadModel unreadModel;
    private TheBasePresenter basePresenter;
    private TheBaseModel baseModel;
    List<TeamUnreadBean.DataBean> unreadBeans=new ArrayList<>();
    private UnreadListAdapter adapter;

    @Override
    protected void initView() {
        super.initView();
        titleBar.setVisibility(View.GONE);
        View errorView = (LinearLayout) View.inflate(getActivity(),R.layout.em_chat_neterror_item, null);
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
        unreadPresenter = new GetTeamUnreadPresenter();
        unreadModel = new GetTeamUnreadModel();
        unreadPresenter.setVM(unreadModel,this);
        unreadPresenter.getTeamUnread();

        basePresenter = new TheBasePresenter();
        baseModel = new TheBaseModel();
        basePresenter.setVM(baseModel,this);

        adapter = new UnreadListAdapter(getContext(),unreadBeans);
        adapter.setOnButtonClick(this);
        lv.setAdapter(adapter);
    }
    
    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if(conversation.isGroup()){
                        if(conversation.getType() == EMConversationType.ChatRoom){
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        }else{
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }
                        
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        super.setUpView();
        //end of red packet code
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())){
         errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
          errorText.setText(R.string.the_current_network);
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
    public void returnTeamUnread(TeamUnreadBean teamUnreadBean) {
        unreadBeans.clear();;
        unreadBeans.addAll(teamUnreadBean.getData());
        adapter.notifyDataSetChanged();

    }

    @Override
    public void receiveInvite(String line_id, String group_id, String nick_user_id) {
          basePresenter.receiveInvite(line_id, group_id, nick_user_id);
    }

    @Override
    public void refuseInvite(String line_id, String group_id, String nick_user_id) {
        basePresenter.refuseInvite(line_id, group_id, nick_user_id);
    }

    @Override
    public void receiveApply(String line_id, String you_user_id) {
         basePresenter.receiveApply(line_id, you_user_id);
    }

    @Override
    public void refuseApply(String line_id, String you_user_id) {
           basePresenter.refuseApply(line_id, you_user_id);
    }

    @Override
    public void receiveInviteSucess() {
        unreadPresenter.getTeamUnread();
    }

    @Override
    public void refuseInviteSucess() {
        unreadPresenter.getTeamUnread();
    }

    @Override
    public void refuseApplySucess() {
        unreadPresenter.getTeamUnread();
    }

    @Override
    public void receiveApplySucess() {
        unreadPresenter.getTeamUnread();
    }

    @Override
    public void acceptSucess() {

    }

    @Override
    public void denySucess() {

    }
    
    
   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu); 
    }*/

 /*   @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
    	EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
    	if (tobeDeleteCons == null) {
    	    return true;
    	}
        if(tobeDeleteCons.getType() == EMConversationType.GroupChat){
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.conversationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        return true;
    }*/

}
