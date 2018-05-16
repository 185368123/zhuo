package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.SuggestTagBean;
import com.hyphenate.chatuidemo.my.constract.SetTagConstract;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.SimpleTitleTip;
import com.hyphenate.chatuidemo.my.easytagdragview.bean.Tip;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.List;

import li.com.base.baserx.RxSubscriber;

/**
 * Created by Administrator on 2018/5/10.
 */

public class SetTagPresenter extends SetTagConstract.Presenter {
    @Override
    public void getSuggestTag() {
        mModel.getSuggestTag(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<SuggestTagBean>>(mContext,false) {
            @Override
            protected void _onNext(List<SuggestTagBean> suggestTagBeans) {
                mView.returnSuggestTag(suggestTagBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void setTag(String choice_id, String you_user_id, List<Tip> tips) {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < tips.size(); i++) {
            SimpleTitleTip tip= (SimpleTitleTip) tips.get(i);
            if (i+1==tips.size()){
                if (tip.getId_member().equals("0")){
                    sb.append(tip.getTip());
                }else{
                    sb.append(tip.getId_member()+"|"+tip.getTip());
                }
            }else {
                if (tip.getId_member().equals("0")){
                    sb.append(tip.getTip()+",");
                }else{
                    sb.append(tip.getId_member()+"|"+tip.getTip()+",");
                }
            }
        }
        mModel.setTag(UserInfoProvider.getToken(),choice_id,you_user_id,sb.toString()).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object o) {
                mView.setTagSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
