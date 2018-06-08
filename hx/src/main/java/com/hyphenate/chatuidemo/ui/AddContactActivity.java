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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.my.FindUserListAdapter;
import com.hyphenate.chatuidemo.my.UserListBean;
import com.hyphenate.chatuidemo.my.constract.SearchUserConstract;
import com.hyphenate.chatuidemo.my.model.SearchUserModel;
import com.hyphenate.chatuidemo.my.presenter.SearchUserPresenter;
import com.hyphenate.easeui.widget.EaseAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends BaseActivity implements SearchUserConstract.View {
	private EditText editText;
	private Button searchBtn;
	private String toAddUsername;
	private ProgressDialog progressDialog;
	private int index=0;
	private ILoadingLayout endLabels;
	private PullToRefreshListView lv;
	private boolean isBottom = false;
	private List<UserListBean> list;
	private FindUserListAdapter adapter;
	private SearchUserPresenter searchUserPresenter;
	private SearchUserModel searchUserModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.em_activity_add_contact);
		searchUserPresenter = new SearchUserPresenter();
		searchUserModel = new SearchUserModel();
		searchUserPresenter.setVM(searchUserModel,this);
		lv = (PullToRefreshListView) findViewById(R.id.lv_find);
		list = new ArrayList<>();
		adapter = new FindUserListAdapter(this, list,this);
		lv.setAdapter(adapter);
		TextView mTextView = (TextView) findViewById(R.id.add_list_friends);
		
		editText = (EditText) findViewById(R.id.edit_note);
		String strAdd = getResources().getString(R.string.add_friend);
		mTextView.setText(strAdd);
		String strUserName = getResources().getString(R.string.user_name);
		editText.setHint(strUserName);
		endLabels = lv.getLoadingLayoutProxy(
				false, true);
		searchBtn = (Button) findViewById(R.id.search);


		lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

				endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
				endLabels.setRefreshingLabel("正在刷新...");// 刷新时
				endLabels.setReleaseLabel("松开立即刷新...");// 下来达到一定距离时，显示的提示
				String label = DateUtils.formatDateTime(
						getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// 显示最后更新的时间
				refreshView.getLoadingLayoutProxy()
						.setLastUpdatedLabel(label);

				refresh();//刷新列表
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if (isBottom) {
					endLabels.setPullLabel("全部加载完毕...");// 刚下拉时，显示的提示
					endLabels.setRefreshingLabel("全部加载完毕...");// 刷新时
					endLabels.setReleaseLabel("全部加载完毕...");// 下来达到一定距离时，显示的提示
					addmore();

				} else {
					endLabels.setPullLabel("上拉加载更多...");// 刚下拉时，显示的提示
					endLabels.setRefreshingLabel("正在加载...");// 刷新时
					endLabels.setReleaseLabel("松开立刻加载更多...");// 下来达到一定距离时，显示的提示
					addmore();//加载更多
				}

			}
		});
	}

	private void addmore() {
		index += 10;
		searchUserPresenter.searchUser(editText.getText().toString(),index+"");
	}

	private void refresh() {
		isBottom=false;
		index = 0;
		list.clear();
		searchUserPresenter.searchUser(editText.getText().toString(),index+"");
	}


	/**
	 * search contact
	 * @param v
	 */
	public void searchContact(View v) {
		String name = editText.getText().toString();
		String saveText = searchBtn.getText().toString();
		
		if (getString(R.string.button_search).equals(saveText)) {
			toAddUsername = name;
			if(TextUtils.isEmpty(name)) {
				new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
				return;
			}
			searchUserPresenter.searchUser(index+"",name);
		} 
	}	
	

	public void back(View v) {
		finish();
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
	public void returnUser(List<UserListBean> data) {
		if (data==null||data.size()==0){
			lv.onRefreshComplete();
			isBottom=true;
		}else {
			this.list.addAll(data);
			adapter.notifyDataSetChanged();
			lv.onRefreshComplete();
		}
	}
}
