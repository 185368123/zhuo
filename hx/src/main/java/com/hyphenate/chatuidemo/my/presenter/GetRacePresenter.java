package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.ConfrontationBean;
import com.hyphenate.chatuidemo.my.bean.RaceBean;
import com.hyphenate.chatuidemo.my.bean.TeamScoreBean;
import com.hyphenate.chatuidemo.my.constract.GetRaceConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.SingleBeans;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GetRacePresenter extends GetRaceConstract.Presenter {
    @Override
    public void getRace(String hunderd_id) {
        mModel.getRace(UserInfoProvider.getToken(),hunderd_id).subscribe(new RxSubscriber<List<RaceBean>>(mContext, false) {
            @Override
            protected void _onNext(List<RaceBean> raceBeans) {
                String type="99";
                String group="99";
                String group_ = "9";
                List<ConfrontationBean>  list=new ArrayList<>();
                for (int i = 0; i < raceBeans.size(); i++) {


                    if (!type.equals(raceBeans.get(i).getRace_type())) {
                        type = raceBeans.get(i).getRace_type();
                        ConfrontationBean bean_=new ConfrontationBean();
                        if (type.equals("2")) {
                            bean_.setType("1");
                            bean_.setDate("小组赛");
                            list.add(bean_);
                        }
                    }


                    if (type.equals("2")) {
                        if (!group_.equals(raceBeans.get(i).getRace_group())) {
                            group_ = raceBeans.get(i).getRace_group();
                            ConfrontationBean bean_=new ConfrontationBean();
                            bean_.setType("3");
                            if (group_.equals("1")) {
                                bean_.setDate("A组");
                            } else if (group_.equals("2")) {
                                bean_.setDate("B组");
                            } else if (group_.equals("3")) {
                                bean_.setDate("C组");
                            } else if (group_.equals("4")) {
                                bean_.setDate("D组");
                            } else if (group_.equals("5")) {
                                bean_.setDate("E组");
                            } else if (group_.equals("6")) {
                                bean_.setDate("F组");
                            } else if (group_.equals("7")) {
                                bean_.setDate("G组");
                            }
                            list.add(bean_);
                        }
                    } else {
                        if (!group.equals(raceBeans.get(i).getRace_stage())) {
                            ConfrontationBean bean_=new ConfrontationBean();
                            if (type.equals("1")) {
                                bean_.setType("1");
                                bean_.setDate("淘汰赛");
                            } else if (type.equals("3")) {
                                bean_.setType("1");
                                bean_.setDate("循环赛");
                            } else {
                                bean_.setType("1");
                                bean_.setDate("自定义");
                            }
                            list.add(bean_);
                        }
                    }



                    ConfrontationBean bean=new ConfrontationBean();
                    if (SingleBeans.getInstance().isTeam()){
                        if (raceBeans.get(i).getLine_id()!=null&&raceBeans.get(i).getGroup_right_line_id()!=null){
                            if (raceBeans.get(i).getLine_id().equals(SingleBeans.getInstance().getLine_id())||raceBeans.get(i).getGroup_right_line_id().equals(SingleBeans.getInstance().getLine_id())){
                                bean.setMyTeam(true);
                            }
                        }
                    }
                    String[] times=raceBeans.get(i).getRace_time().split(" ");
                    bean.setType("2");
                    String[] time_1=times[0].split("-");
                    String[] time_2=times[1].split(":");
                    bean.setTime(time_1[1]+"-"+time_1[2]);
                    bean.setDescribe(time_2[0]+":"+time_2[1]);
                    bean.setTeam1Name(raceBeans.get(i).getGroup_name());
                    bean.setTeam2Name(raceBeans.get(i).getGroup_right_name());
                    bean.setTeam1Victory(raceBeans.get(i).getGroup_left_win());
                    bean.setTeam2Victory(raceBeans.get(i).getGroup_right_win());
                    list.add(bean);
                }
                mView.returnRace(list);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
