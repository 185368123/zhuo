package zhuozhuo.com.zhuo.presenter;

import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import com.hyphenate.easeui.provider.UserInfoProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import li.com.base.baserx.RxSubscriber;
import li.com.base.basesinglebean.GroupStatusBean;
import li.com.base.basesinglebean.SingleBeans;
import zhuozhuo.com.zhuo.contract.Zhuo2FragmentConstract;

/**
 * Created by Administrator on 2018/3/30.
 */

public class Zhuo2FragmentPresenter extends Zhuo2FragmentConstract.Presenter {
    @Override
    public void getGroupStatus() {
        mModel.getGroupStatus(UserInfoProvider.getToken()).subscribe(new RxSubscriber<GroupStatusBean>(mContext,false) {
            @Override
            protected void _onNext(GroupStatusBean groupStatusBean) {
                mView.returnGroupStatus(groupStatusBean);
                SingleBeans.getInstance().setGroupStatusBeans(groupStatusBean);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void getGroupChoices() {
        mModel.getGroupChoices(UserInfoProvider.getToken()).subscribe(new RxSubscriber<List<GroupChoicesBean>>(mContext,false) {
            @Override
            protected void _onNext(List<GroupChoicesBean> groupChoicesBeans) {
                mView.returnGroupChoices(groupChoicesBeans);
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void beginGroupMatch(String choice_id, String group_name, int choice_number) {
        String []choicesValues=SingleBeans.getInstance().getGroupStatusBeans().getMember_ids().split(",");
        mModel.beginGroupMatch(UserInfoProvider.getToken(),
                SingleBeans.getInstance().getGroupStatusBeans().getMember_ids(),
                String.valueOf(choicesValues.length),
                choice_id,
                SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),
                group_name,
                String.valueOf(choice_number)).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object s) {
                try {
                    String str= String.valueOf(s);
                    JSONArray array=new JSONArray(str);
                    JSONObject jsonObject=array.getJSONObject(0);
                    // String status=jsonObject.getString("status");
                    if (jsonObject.has("status")){
                        mView.beginGroupMatchSucess();
                    }else if (jsonObject.has("group_id")){
                        mView.groupMatchSucess(jsonObject.getString("group_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }

    @Override
    public void cancleGroupMatch() {
      mModel.cancleGroupMatch(UserInfoProvider.getToken(),SingleBeans.getInstance().getGroupStatusBeans().getTeam_id()).subscribe(new RxSubscriber<Object>(mContext,false) {
          @Override
          protected void _onNext(Object s) {
              mView.cancleGroupMatchSucess();
          }

          @Override
          protected void _onError(String message) {

          }
      });
    }

    @Override
    public void groupRemark(String comment, boolean isRemark) {
        if (isRemark){
                mModel.groupRemark(UserInfoProvider.getToken(),SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),comment,SingleBeans.getInstance().getGroupStatusBeans().getGroup_id()).subscribe(new RxSubscriber<Object>(mContext,false) {
                    @Override
                    protected void _onNext(Object s) {
                        if (mView!=null){
                            mView.remarkSucess();
                        }
                    }

                    @Override
                    protected void _onError(String message) {

                    }
                });
        }else {
            mModel.groupRemark(UserInfoProvider.getToken(),SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),"",SingleBeans.getInstance().getGroupStatusBeans().getGroup_id()).subscribe(new RxSubscriber<Object>(mContext,false) {
                @Override
                protected void _onNext(Object s) {
                    if (mView!=null){
                        mView.remarkSucess();
                    }
                }

                @Override
                protected void _onError(String message) {

                }
            });
        }
    }

    @Override
    public void leaveTeam() {
        mModel.leaveTeam(UserInfoProvider.getToken(),SingleBeans.getInstance().getGroupStatusBeans().getTeam_id(),SingleBeans.getInstance().getGroupStatusBeans().getGroup_id()).subscribe(new RxSubscriber<Object>(mContext,false) {
            @Override
            protected void _onNext(Object s) {
                mView.leaveTeamSucess();
            }

            @Override
            protected void _onError(String message) {

            }
        });
    }
}
