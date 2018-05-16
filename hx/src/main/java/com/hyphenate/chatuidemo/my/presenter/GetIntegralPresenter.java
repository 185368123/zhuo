package com.hyphenate.chatuidemo.my.presenter;

import com.hyphenate.chatuidemo.my.bean.IntegralBean;
import com.hyphenate.chatuidemo.my.bean.TeamScoreBean;
import com.hyphenate.chatuidemo.my.constract.GetIntegralConstract;
import com.hyphenate.easeui.provider.UserInfoProvider;

import java.util.ArrayList;
import java.util.List;

import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.SingleBeans;
import li.com.base.baseuntils.LogUtils;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GetIntegralPresenter extends GetIntegralConstract.Presenter {
    @Override
    public void getIntegral(String hunderd_id) {
        mModel.getIntegral(UserInfoProvider.getToken(),hunderd_id).subscribe(new RxSubscriber<List<IntegralBean>>(mContext, false) {
            @Override
            protected void _onNext(List<IntegralBean> integralBeans) {
                List<TeamScoreBean> list = new ArrayList<>();
                String type = "9";
                String group = "9";
                String group_ = "9";

                int index = 1;
                for (int i = 0; i < integralBeans.size(); i++) {
                    if (!type.equals(integralBeans.get(i).getRace_type())) {
                        type = integralBeans.get(i).getRace_type();
                        TeamScoreBean scoreBean_1 = new TeamScoreBean();
                        if (type.equals("2")) {
                            scoreBean_1.setType("1");
                            scoreBean_1.setTypeName("小组赛");
                            list.add(scoreBean_1);
                        }
                    } else {
                        if (type.equals("2")) {
                            if (!group_.equals(integralBeans.get(i).getRace_group())){
                                TeamScoreBean teamScoreBean = new TeamScoreBean();
                                teamScoreBean.setType("4");
                                list.add(teamScoreBean);
                            }
                        }else {
                            if (!group.equals(integralBeans.get(i).getRace_stage())) {
                                TeamScoreBean teamScoreBean = new TeamScoreBean();
                                teamScoreBean.setType("4");
                                list.add(teamScoreBean);
                            }
                        }
                    }

                    if (type.equals("2")) {
                        if (!group_.equals(integralBeans.get(i).getRace_group())) {
                            index = 1;
                            group_ = integralBeans.get(i).getRace_group();
                            TeamScoreBean scoreBean__ = new TeamScoreBean();
                            scoreBean__.setType("2");
                            if (group_.equals("1")) {
                                scoreBean__.setGroupName("A组");
                            } else if (group_.equals("2")) {
                                scoreBean__.setGroupName("B组");
                            } else if (group_.equals("3")) {
                                scoreBean__.setGroupName("C组");
                            } else if (group_.equals("4")) {
                                scoreBean__.setGroupName("D组");
                            } else if (group_.equals("5")) {
                                scoreBean__.setGroupName("E组");
                            } else if (group_.equals("6")) {
                                scoreBean__.setGroupName("F组");
                            } else if (group_.equals("7")) {
                                scoreBean__.setGroupName("G组");
                            }
                            list.add(scoreBean__);
                        }
                    } else {
                        if (!group.equals(integralBeans.get(i).getRace_stage())) {
                            TeamScoreBean scoreBean_ = new TeamScoreBean();
                            if (type.equals("1")) {
                                scoreBean_.setType("1");
                                scoreBean_.setTypeName("淘汰赛");
                            } else if (type.equals("3")) {
                                scoreBean_.setType("1");
                                scoreBean_.setTypeName("循环赛");
                            } else {
                                scoreBean_.setType("1");
                                scoreBean_.setTypeName("自定义");
                            }
                            list.add(scoreBean_);

                            index = 1;
                            group = integralBeans.get(i).getRace_stage();
                            TeamScoreBean scoreBean__ = new TeamScoreBean();
                            scoreBean__.setType("2");
                            scoreBean__.setGroupName("---");
                            list.add(scoreBean__);
                        }
                    }

                    TeamScoreBean scoreBean = new TeamScoreBean();
                    if (SingleBeans.getInstance().isTeam()){
                        if (integralBeans.get(i).getLine_id().equals(SingleBeans.getInstance().getLine_id())){
                           scoreBean.setMyTeam(true);
                        }
                    }
                    scoreBean.setType("3");
                    scoreBean.setTeamName(index + "." + integralBeans.get(i).getGroup_name());
                    scoreBean.setTeamVictory(integralBeans.get(i).getWin());
                    scoreBean.setTeamFiled(integralBeans.get(i).getLost());
                    scoreBean.setTeamScore(integralBeans.get(i).getIntegral_num());
                    list.add(scoreBean);
                    index++;
                }
                mView.returnIntegral(list);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
