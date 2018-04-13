package com.hyphenate.chatuidemo.my.api;

import li.com.base.basesinglebean.GroupStatusBean;

import com.hyphenate.chatuidemo.my.bean.AllArticleBean;
import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import com.hyphenate.chatuidemo.my.bean.RateBean;
import com.hyphenate.chatuidemo.my.bean.HundredBean;
import com.hyphenate.chatuidemo.my.bean.LoginBean;

import li.com.base.basesinglebean.MatchPersonBean;

import com.hyphenate.chatuidemo.my.bean.UserMsgBean;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import li.com.base.basesinglebean.VisonBean;
import java.util.List;
import li.com.base.baserx.BaseRespose;
import li.com.base.basesinglebean.SingleChooseBean;
import li.com.base.basesinglebean.SingleChooseDetailBean;
import li.com.base.basesinglebean.SingleStatusBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;


public interface ApiService {
    @FormUrlEncoded
    @POST(UrlConstant.Login_URL)
    Observable<BaseRespose<List<LoginBean>>> login(@Field("phone") String phone,
                                                   @Field("password") String password);

    @FormUrlEncoded
    @POST(UrlConstant.WeiXinLogin_URL)
    Observable<BaseRespose<List<LoginBean>>> wlogin(@Field("wechat_id") String wechat_id,
                                                    @Field("nick_name") String nick_name,
                                                    @Field("photo_link") String photo_link,
                                                    @Field("sex") String sex,
                                                    @Field("location") String location);

    @FormUrlEncoded
    @POST(UrlConstant.GetPhone_URL)
    Observable<BaseRespose<List<Object>>> getAnthCoe(@Field("phone") String phone,
                                                      @Field("opt") String opt);

    @FormUrlEncoded
    @POST(UrlConstant.FrogetPassword_URL)
    Observable<BaseRespose<List<LoginBean>>> changePassword(@Field("phone") String phone,
                                                            @Field("code") String code,
                                                            @Field("password") String password);

    @FormUrlEncoded
    @POST(UrlConstant.Register_URL)
    Observable<BaseRespose<List<LoginBean>>> register(@Field("phone") String phone,
                                                      @Field("password") String password,
                                                      @Field("code") String code,
                                                      @Field("nick_name") String nick_name,
                                                      @Field("sex") String sex);

    @FormUrlEncoded
    @POST(UrlConstant.GetVideo_URL)
    Observable<BaseRespose<List<VideoBean>>> getShareVideo(@Field("index") String index,
                                                           @Field("choice_id") String choice_id);

    @FormUrlEncoded
    @POST(UrlConstant.Change_URL)
    Observable<BaseRespose<List<Object>>> changeMsg(@Field("token") String token,
                                                     @Field("key") String key,
                                                     @Field("value") String value);

    @FormUrlEncoded
    @POST(UrlConstant.GetUser_URL)
    Observable<BaseRespose<List<UserOnlineBean>>> getUserList(@Field("token") String token,
                                                              @Field("index") String index);

    @FormUrlEncoded
    @POST(UrlConstant.Invite_URL)
    Observable<BaseRespose<List<Object>>> invite(@Field("token") String token,
                                                 @Field("team_id") String team_id,
                                                 @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(UrlConstant.GetChoicesList_URL)
    Observable<BaseRespose<List<SingleChooseBean>>> getSingleChoose(@Field("token") String token,
                                                                    @Field("type") String type);

    @FormUrlEncoded
    @POST(UrlConstant.GetChoicesList_URL)
    Observable<BaseRespose<List<SingleChooseDetailBean>>> getChooseDetail(@Field("token") String token,
                                                                          @Field("type") String type,
                                                                          @Field("choice_name") String choice_name);

    @FormUrlEncoded
    @POST(UrlConstant.GetStaus_URL)
    Observable<BaseRespose<SingleStatusBean>> getSingleStatus(@Field("token") String token);

    @FormUrlEncoded
    @POST(UrlConstant.GetUserMsg)
    Observable<BaseRespose<List<UserMsgBean>>> getUserMsg(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST(UrlConstant.SingleMatch_URL)
    Observable<BaseRespose<Object>> match(@Field("token") String token,
                                          @Field("member_ids") String member_ids,
                                          @Field("members") String members,
                                          @Field("choice_id") String choice_id,
                                          @Field("type") String type,
                                          @Field("sex") String sex,
                                          @Field("exclude_user_ids") String exclude_user_ids,
                                          @Field("match_user_id") String match_user_id);

    @FormUrlEncoded
    @POST(UrlConstant.CancelMatch_URL)
    Observable<BaseRespose<Object>> cancelmatch(@Field("token") String token);

    @FormUrlEncoded
    @POST(UrlConstant.Evaluate_URL)
    Observable<BaseRespose<Object>> remark(@Field("token") String token,
                                           @Field("user_id") String userId,
                                           @Field("remark") String remark,
                                           @Field("score") String score,
                                           @Field("card") String card);


    @FormUrlEncoded
    @POST(UrlConstant.GetHundred_URL)
    Observable<BaseRespose<List<HundredBean>>> getHundredMsg(@Field("token") String token);


    @FormUrlEncoded
    @POST(UrlConstant.Play_Match_URL)
    Observable<BaseRespose<Object>> playMatch(@Field("token") String token,
                                              @Field("choice_id") String choice_id,
                                              @Field("user_sex") String user_sex,
                                              @Field("dest_sex") String dest_sex);

    @FormUrlEncoded
    @POST(UrlConstant.Play_Accept_URL)
    Observable<BaseRespose<Object>> acceptMatch(@Field("token") String token,
                                                @Field("choice_id") String choice_id,
                                                @Field("you_accept_id") String you_accept_id,
                                                @Field("other_party_id") String other_party_id);

    @FormUrlEncoded
    @POST(UrlConstant.Get_Accept_All_URL)
    Observable<BaseRespose<List<MatchPersonBean>>> getAcceptAll(@Field("token") String token);

    @FormUrlEncoded
    @POST(UrlConstant.Edit_Finish_URL)
    Observable<BaseRespose<Object>> changeFinish(@Field("token") String token,
                                                 @Field("user_id") String user_id);

    //获取组队匹配状态
    @FormUrlEncoded
    @POST(UrlConstant.GetGroupStatus_URL)
    Observable<BaseRespose<GroupStatusBean>> getGroupStatus(@Field("token") String token);

    //获取组队匹配选项
    @FormUrlEncoded
    @POST(UrlConstant.GetGroupChoices_URL)
    Observable<BaseRespose<List<GroupChoicesBean>>> getGroupChoices(@Field("token") String token);

    //组队匹配
    @FormUrlEncoded
    @POST(UrlConstant.GroupMatch_URL)
    Observable<BaseRespose<Object>> beginGroupMatch(@Field("token") String token,
                                                    @Field("member_ids") String member_ids,
                                                    @Field("members") String members,
                                                    @Field("choice_id") String choice_id,
                                                    @Field("team_id") String team_id,
                                                    @Field("group_name") String group_name,
                                                    @Field("choice_number") String choice_number);

    //取消组队匹配
    @FormUrlEncoded
    @POST(UrlConstant.CancelGroupMatch_URL)
    Observable<BaseRespose<Object>> cancleGroupMatch(@Field("token") String token,
                                                     @Field("team_id") String team_id);

    //设置组队评价
    @FormUrlEncoded
    @POST(UrlConstant.SetGroupRemark_URL)
    Observable<BaseRespose<Object>> groupRemark(@Field("token") String token,
                                                @Field("team_id") String team_id,
                                                @Field("content") String content,
                                                @Field("group_id") String group_id);

    //离开队伍
    @FormUrlEncoded
    @POST(UrlConstant.LeaveGroup_URL)
    Observable<BaseRespose<Object>> leaveTeam(@Field("token") String token,
                                              @Field("team_id") String team_id,
                                              @Field("group_id") String group_id);

    //单人匹配设置步骤图片
    @FormUrlEncoded
    @POST(UrlConstant.SetStepPhoto)
    Observable<BaseRespose<Object>> setStepPhtot(@Field("token") String token,
                                                 @Field("photo_link") String photo_link,
                                                 @Field("you_user_id") String you_user_id,
                                                 @Field("thumb_link") String thumb_link);

    //组队匹配设置金额
    @FormUrlEncoded
    @POST(UrlConstant.SetMoney_URL)
    Observable<BaseRespose<Object>> setMoney(@Field("token") String token,
                                             @Field("choice_money") String choice_money,
                                             @Field("group_id") String group_id);

    //白人团得分表获取
    @FormUrlEncoded
    @POST(UrlConstant.GetHundredRate_URL)
    Observable<BaseRespose<List<RateBean>>> getGroupRate(@Field("token") String token,
                                                         @Field("group_id") String group_id);

    //获取视频
    @FormUrlEncoded
    @POST(UrlConstant.GetVideo)
    Observable<BaseRespose<VideoLinkBean>> getVideo(@Field("token") String token,
                                                    @Field("you_user_id") String you_user_id);

    //开始制作视频
    @FormUrlEncoded
    @POST(UrlConstant.StartVideo)
    Observable<BaseRespose<Object>> startVideo(@Field("token") String token,
                                             @Field("video_id") String video_id);

    @FormUrlEncoded
    @POST(UrlConstant.GetVison_URL)
    Observable<VisonBean> getVison(@Field("type") String type);


    @FormUrlEncoded
    @POST(UrlConstant.JoinHundredGroup_URL)
    Observable<BaseRespose<Object>> joinHundredGroup(@Field("token") String token,
                                                     @Field("hundred_id") String hundred_id);


    //获取我的帖子列表
    @FormUrlEncoded
    @POST(UrlConstant.GetMyArticle_URL)
    Observable<BaseRespose<List<MineArticleBean>>> getMyArticle(@Field("token") String token,
                                                                @Field("index") String index);

    //获取所有帖子列表
    @FormUrlEncoded
    @POST(UrlConstant.GetAllArticle_URL)
    Observable<BaseRespose<List<AllArticleBean>>> getAllArticle(@Field("index") String index);

}
