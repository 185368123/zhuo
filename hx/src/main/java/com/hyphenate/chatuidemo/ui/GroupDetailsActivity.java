/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.chatuidemo.ui;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMPushConfigs;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.EmptyView;
import com.hyphenate.chatuidemo.my.PresentModel.QuitGroupPresentModel;
import com.hyphenate.easeui.ui.EaseGroupListener;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseExpandGridView;
import com.hyphenate.easeui.widget.EaseSwitchButton;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupDetailsActivity extends BaseActivity implements OnClickListener {

	public static final int  RESULT_QUIT_GROUP=0011;
	private static final String TAG = "GroupDetailsActivity";

	private String groupId;
	private ProgressBar loadingPB;
	private EMGroup group;
	private GridAdapter membersAdapter;
	private OwnerAdminAdapter ownerAdminAdapter;
	private ProgressDialog progressDialog;
	private TextView announcementText;


	public static GroupDetailsActivity instance;

	
	String st = "";

	private EaseSwitchButton switchButton;
	private EaseSwitchButton offlinePushSwitch;
	private EMPushConfigs pushConfigs;

	private String operationUserId = "";

	private List<String> adminList = Collections.synchronizedList(new ArrayList<String>());
	private List<String> memberList = Collections.synchronizedList(new ArrayList<String>());
	private List<String> muteList = Collections.synchronizedList(new ArrayList<String>());
	private List<String> blackList = Collections.synchronizedList(new ArrayList<String>());

	GroupChangeListener groupChangeListener;
	private Button bt_quit_group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        groupId = getIntent().getStringExtra("groupId");
        group = EMClient.getInstance().groupManager().getGroup(groupId);

        // we are not supposed to show the group if we don't find the group
        if(group == null){
            finish();
            return;
        }
        
		setContentView(R.layout.em_activity_group_details);

		bt_quit_group = (Button) findViewById(R.id.bt_quit_group);
		bt_quit_group.setOnClickListener(this);

		instance = this;
		st = getResources().getString(R.string.people);
		loadingPB = (ProgressBar) findViewById(R.id.progressBar);
		RelativeLayout idLayout = (RelativeLayout) findViewById(R.id.rl_group_id);
		idLayout.setVisibility(View.VISIBLE);
		TextView idText = (TextView) findViewById(R.id.tv_group_id_value);

		RelativeLayout rl_switch_block_groupmsg = (RelativeLayout) findViewById(R.id.rl_switch_block_groupmsg);
		switchButton = (EaseSwitchButton) findViewById(R.id.switch_btn);

		RelativeLayout blockOfflineLayout = (RelativeLayout) findViewById(R.id.rl_switch_block_offline_message);
		offlinePushSwitch = (EaseSwitchButton) findViewById(R.id.switch_block_offline_message);


		idText.setText(groupId);

		//get push configs
		pushConfigs = EMClient.getInstance().pushManager().getPushConfigs();

		groupChangeListener = new GroupChangeListener();
		EMClient.getInstance().groupManager().addGroupChangeListener(groupChangeListener);
		
		((TextView) findViewById(R.id.group_name)).setText(group.getGroupName() + "(" + group.getMemberCount() + st);

		membersAdapter = new GridAdapter(this, R.layout.em_grid_owner, new ArrayList<String>());
		EaseExpandGridView userGridview = (EaseExpandGridView) findViewById(R.id.gridview);
		userGridview.setAdapter(membersAdapter);

		ownerAdminAdapter = new OwnerAdminAdapter(this, R.layout.em_grid_owner, new ArrayList<String>());
		//EaseExpandGridView ownerAdminGridview = (EaseExpandGridView) findViewById(R.id.owner_and_administrators_grid_view);
		//ownerAdminGridview.setAdapter(ownerAdminAdapter);

		// 保证每次进详情看到的都是最新的group
		updateGroup();


		rl_switch_block_groupmsg.setOnClickListener(this);

		blockOfflineLayout.setOnClickListener(this);
	}



	boolean isCurrentOwner(EMGroup group) {
		String owner = group.getOwner();
		if (owner == null || owner.isEmpty()) {
			return false;
		}
		return owner.equals(EMClient.getInstance().getCurrentUser());
	}

	boolean isCurrentAdmin(EMGroup group) {
		synchronized (adminList) {
			String currentUser = EMClient.getInstance().getCurrentUser();
			for (String admin : adminList) {
				if (currentUser.equals(admin)) {
					return true;
				}
			}
		}
		return false;
	}

	boolean isAdmin(String id) {
		synchronized (adminList) {
			for (String admin : adminList) {
				if (id.equals(admin)) {
					return true;
				}
			}
		}
		return false;
	}

	boolean isInBlackList(String id) {
		synchronized (blackList) {
			if (id != null && !id.isEmpty()) {
				for (String item : blackList) {
					if (id.equals(item)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	boolean isInMuteList(String id) {
		synchronized (muteList) {
			if (id != null && !id.isEmpty()) {
				for (String item : muteList) {
					if (id.equals(item)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	boolean isCanAddMember(EMGroup group) {
		if (group.isMemberAllowToInvite() ||
				isAdmin(EMClient.getInstance().getCurrentUser()) ||
				isCurrentOwner(group)) {
			return true;
		}
		return false;
	}


	private void refreshOwnerAdminAdapter() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ownerAdminAdapter.clear();
				ownerAdminAdapter.add(group.getOwner());
				membersAdapter.add(group.getOwner());
				synchronized (adminList) {
					Log.e("group.getOwner()",group.getOwner());

					ownerAdminAdapter.addAll(adminList);
				}
				ownerAdminAdapter.notifyDataSetChanged();
			}
		});
	}

	private void debugList(String str, List<String> list) {
		EMLog.d(TAG, str);
		for (String member : list) {
			EMLog.d(TAG, "    " + member);
		}
	}

	private void refreshMembersAdapter() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				debugList("memberList", memberList);
				debugList("muteList", muteList);
				debugList("blackList", blackList);
				membersAdapter = new GridAdapter(GroupDetailsActivity.this, R.layout.em_grid_owner, new ArrayList<String>());
				membersAdapter.clear();
				synchronized (memberList) {
					Log.e(TAG, "fetchGroupMembers memberList.size:" + memberList.size());
					membersAdapter.addAll(memberList);
				}
				synchronized (adminList) {
					Log.e(TAG, "fetchGroupMembers adminList.size:" + adminList.size());
					membersAdapter.addAll(adminList);
				}
				synchronized (blackList) {
					membersAdapter.addAll(blackList);
				}
				membersAdapter.notifyDataSetChanged();
				EaseExpandGridView userGridview = (EaseExpandGridView) findViewById(R.id.gridview);
				userGridview.setAdapter(membersAdapter);
			}
		});
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.rl_switch_block_groupmsg) {
			toggleBlockGroup();
		} else if (i == R.id.rl_switch_block_offline_message) {
			toggleBlockOfflineMsg();
		}else if (i==R.id.bt_quit_group){
			// 创建构建器
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// 设置参数
			builder.setTitle("提示：")
					.setMessage("是否退出群组？")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new QuitGroupPresentModel(new EmptyView() {
								@Override
								public void emptyBack() {
									setResult(RESULT_QUIT_GROUP);
									finish();
								}
							}).quitGroup(groupId);
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}




	private void toggleBlockOfflineMsg() {
		if(EMClient.getInstance().pushManager().getPushConfigs() == null){
			return;
		}
		progressDialog = createProgressDialog();
		progressDialog.setMessage("processing...");
		progressDialog.show();
//		final ArrayList list = (ArrayList) Arrays.asList(groupId);
		final List<String> list = new ArrayList<String>();
		list.add(groupId);
		new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(offlinePushSwitch.isSwitchOpen()) {
                        EMClient.getInstance().pushManager().updatePushServiceForGroup(list, false);
                    }else{
                        EMClient.getInstance().pushManager().updatePushServiceForGroup(list, true);
                    }
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progressDialog.dismiss();
							if(offlinePushSwitch.isSwitchOpen()){
								offlinePushSwitch.closeSwitch();
							}else{
								offlinePushSwitch.openSwitch();
							}
						}
					});
                } catch (HyphenateException e) {
                    e.printStackTrace();
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progressDialog.dismiss();
							Toast.makeText(GroupDetailsActivity.this, "progress failed", Toast.LENGTH_SHORT).show();
						}
					});
                }
            }
        }).start();
	}

	private ProgressDialog createProgressDialog(){
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(GroupDetailsActivity.this);
			progressDialog.setCanceledOnTouchOutside(false);
		}
		return progressDialog;
	}

	private void toggleBlockGroup() {
		if(switchButton.isSwitchOpen()){
			EMLog.d(TAG, "change to unblock group msg");
			createProgressDialog();
			progressDialog.setMessage(getString(R.string.Is_unblock));
			progressDialog.show();
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                EMClient.getInstance().groupManager().unblockGroupMessage(groupId);
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                    	switchButton.closeSwitch();
		                        progressDialog.dismiss();
		                    }
		                });
		            } catch (Exception e) {
		                e.printStackTrace();
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                        progressDialog.dismiss();
		                        Toast.makeText(getApplicationContext(), R.string.remove_group_of, Toast.LENGTH_LONG).show();
		                    }
		                });
		                
		            }
		        }
		    }).start();
			
		} else {
			String st8 = getResources().getString(R.string.group_is_blocked);
			final String st9 = getResources().getString(R.string.group_of_shielding);
			EMLog.d(TAG, "change to block group msg");
			createProgressDialog();
			progressDialog.setMessage(st8);
			progressDialog.show();
			new Thread(new Runnable() {
		        public void run() {
		            try {
		                EMClient.getInstance().groupManager().blockGroupMessage(groupId);
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                    	switchButton.openSwitch();
		                        progressDialog.dismiss();
		                    }
		                });
		            } catch (Exception e) {
		                e.printStackTrace();
		                runOnUiThread(new Runnable() {
		                    public void run() {
		                        progressDialog.dismiss();
		                        Toast.makeText(getApplicationContext(), st9, Toast.LENGTH_LONG).show();
		                    }
		                });
		            }
		            
		        }
		    }).start();
		}
	}


	/**
	 * 群组Owner和管理员gridadapter
	 *
	 * @author admin_new
	 *
	 */
	private class OwnerAdminAdapter extends ArrayAdapter<String> {

		private int res;

		public OwnerAdminAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			res = textViewResourceId;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getContext()).inflate(res, null);
				holder.imageView = (ImageView) convertView.findViewById(R.id.iv_avatar);
				holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
				holder.badgeDeleteView = (ImageView) convertView.findViewById(R.id.badge_delete);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}

			final LinearLayout button = (LinearLayout) convertView.findViewById(R.id.button_avatar);

			final String username = getItem(position);
			convertView.setVisibility(View.VISIBLE);
			button.setVisibility(View.VISIBLE);
			EaseUserUtils.setUserNick(username, holder.textView);
			EaseUserUtils.setUserAvatar(getContext(), username, holder.imageView);

			LinearLayout id_background = (LinearLayout) convertView.findViewById(R.id.l_bg_id);
			id_background.setBackgroundColor(convertView.getResources().getColor(
					position == 0 ? R.color.holo_red_light :
							(isInMuteList(username) ? R.color.gray_normal : R.color.holo_orange_light)));

			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isCurrentOwner(group)) {
						return;
					}
					if (username.equals(group.getOwner())) {
						return;
					}
					operationUserId = username;
					//TODO 查看个人资料
					Intent intent = new Intent(getContext(), UserProfileActivity.class);
					intent.putExtra("username", username);
					startActivity(intent);

				}
			});
			return convertView;
		}

		@Override
		public int getCount() {
			return super.getCount();
		}
	}


	/**
	 * 群组成员gridadapter
	 * 
	 * @author admin_new
	 * 
	 */
	private class GridAdapter extends ArrayAdapter<String> {

		private int res;

		public GridAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
			Log.e(" List<String> objects",objects.size()+"");
			res = textViewResourceId;
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
		    ViewHolder holder = null;
			if (convertView == null) {
			    holder = new ViewHolder();
				convertView = LayoutInflater.from(getContext()).inflate(res, null);
				holder.imageView = (ImageView) convertView.findViewById(R.id.iv_avatar);
				holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			}else{
			    holder = (ViewHolder) convertView.getTag();
			}
			LinearLayout button = (LinearLayout) convertView.findViewById(R.id.button_avatar);
			if (getCount()!=position){
				// members
				final String username = getItem(position);
				EaseUserUtils.setUserNick(username, holder.textView);
				EaseUserUtils.setUserAvatar(getContext(), username, holder.imageView);

				LinearLayout id_background = (LinearLayout) convertView.findViewById(R.id.l_bg_id);
			    id_background.setBackgroundColor(convertView.getResources().getColor(R.color.holo_blue_bright));

				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						/*if (!isCurrentOwner(group) && !isCurrentAdmin(group)) {
							return;
						}*/
						operationUserId = username;
						Log.e("operationUserId",operationUserId);
						Intent intent = new Intent(getContext(), UserProfileActivity.class);
						intent.putExtra("username", username);
						startActivity(intent);

					}
				});
			}
			return convertView;
		}

		@Override
		public int getCount() {
			return super.getCount();
		}
	}

	protected void updateGroup() {
		new Thread(new Runnable() {
			public void run() {
				try {
					if(pushConfigs == null){
						EMClient.getInstance().pushManager().getPushConfigsFromServer();
					}

					try {
						group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
						adminList.clear();
						adminList.addAll(group.getAdminList());
						memberList.clear();
						memberList.addAll(group.getAdminList());
						EMCursorResult<String> result = null;
						do {
							// page size set to 20 is convenient for testing, should be applied to big value
							result = EMClient.getInstance().groupManager().fetchGroupMembers(groupId,
									result != null ? result.getCursor() : "",
									20);
							Log.e(TAG, "fetchGroupMembers result.size:" + result.getData().size());
							memberList.addAll(result.getData());
							Log.e("memberList",memberList.size()+"");
						} while (result.getCursor() != null && !result.getCursor().isEmpty());
						muteList.clear();
						muteList.addAll(EMClient.getInstance().groupManager().fetchGroupMuteList(groupId, 0, 200).keySet());
						blackList.clear();
						blackList.addAll(EMClient.getInstance().groupManager().fetchGroupBlackList(groupId, 0, 200));
					} catch (Exception e) {

					}

					try {
						EMClient.getInstance().groupManager().fetchGroupAnnouncement(groupId);
					} catch (HyphenateException e) {
						e.printStackTrace();
					}

					runOnUiThread(new Runnable() {
						public void run() {
							refreshMembersAdapter();
							refreshOwnerAdminAdapter();
//							refreshUIVisibility();
							((TextView) findViewById(R.id.group_name)).setText(group.getGroupName() + "(" + group.getMemberCount()
									+ ")");
							loadingPB.setVisibility(View.INVISIBLE);

							// update block
							EMLog.d(TAG, "group msg is blocked:" + group.isMsgBlocked());
							if (group.isMsgBlocked()) {
								switchButton.openSwitch();
							} else {
							    switchButton.closeSwitch();
							}
							List<String> disabledIds = EMClient.getInstance().pushManager().getNoPushGroups();
							if(disabledIds != null && disabledIds.contains(groupId)){
								offlinePushSwitch.openSwitch();
							}else{
								offlinePushSwitch.closeSwitch();
							}
						}
					});
				} catch (Exception e) {
					runOnUiThread(new Runnable() {
						public void run() {
							loadingPB.setVisibility(View.INVISIBLE);
						}
					});
				}
			}
		}).start();

	}

	public void back(View view) {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	protected void onDestroy() {
		EMClient.getInstance().groupManager().removeGroupChangeListener(groupChangeListener);
		super.onDestroy();
		instance = null;

	}
	
	private static class ViewHolder{
	    ImageView imageView;
	    TextView textView;
	    ImageView badgeDeleteView;
	}
    
    private class GroupChangeListener extends EaseGroupListener {

		@Override
		public void onInvitationAccepted(String groupId, String inviter, String reason) {
			runOnUiThread(new Runnable(){

				@Override
				public void run() {
					memberList = group.getMembers();

					Log.e("onInvitationAccepted",memberList.size()+"");
					//memberList.remove(group.getOwner());
					//memberList.removeAll(adminList);
					//memberList.removeAll(muteList);
					refreshMembersAdapter();
				}
            });
		}

		@Override
		public void onUserRemoved(String groupId, String groupName) {
			finish();
		}

		@Override
		public void onGroupDestroyed(String groupId, String groupName) {
			finish();
		}

	    @Override
	    public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
		    updateGroup();
	    }

	    @Override
	    public void onMuteListRemoved(String groupId, final List<String> mutes) {
		    updateGroup();
	    }

	    @Override
	    public void onAdminAdded(String groupId, String administrator) {
		    updateGroup();
	    }

	    @Override
	    public void onAdminRemoved(String groupId, String administrator) {
		    updateGroup();
	    }

	    @Override
	    public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
		    updateGroup();
	    }
	    
	    @Override
	    public void onMemberJoined(String groupId, String member) {
	        updateGroup();
	    }
	    
	    @Override
	    public void onMemberExited(String groupId, String member) {
            updateGroup();
	    }

		@Override
		public void onAnnouncementChanged(String groupId, final String announcement) {
			if(groupId.equals(GroupDetailsActivity.this.groupId)) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						announcementText.setText(announcement);
					}
				});
			}
		}

		@Override
		public void onSharedFileAdded(String groupId, final EMMucSharedFile sharedFile) {

		}

		@Override
		public void onSharedFileDeleted(String groupId, String fileId) {

		}
	}

}
