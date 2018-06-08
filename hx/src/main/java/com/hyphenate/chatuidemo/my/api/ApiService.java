package com.hyphenate.chatuidemo.my.api;

import li.com.base.basesinglebean.GroupStatusBean;

import com.hyphenate.chatuidemo.my.UserListBean;
import com.hyphenate.chatuidemo.my.bean.AllArticleBean;
import li.com.base.basesinglebean.CitiesSingBean;
import com.hyphenate.chatuidemo.my.bean.CupMemberBean;
import com.hyphenate.chatuidemo.my.bean.CupTeamBean;
import com.hyphenate.chatuidemo.my.bean.GroupChoicesBean;
import com.hyphenate.chatuidemo.my.bean.IntegralBean;
import com.hyphenate.chatuidemo.my.bean.IsRemarkBean;
import com.hyphenate.chatuidemo.my.bean.RaceBean;
import com.hyphenate.chatuidemo.my.bean.SaveMatchBean;
import com.hyphenate.chatuidemo.my.bean.SuggestTagBean;
import com.hyphenate.chatuidemo.my.bean.TagBean;
import com.hyphenate.easeui.HundredCupBean;
import com.hyphenate.chatuidemo.my.bean.MineArticleBean;
import com.hyphenate.chatuidemo.my.bean.RateBean;
import com.hyphenate.chatuidemo.my.bean.HundredBean;
import com.hyphenate.chatuidemo.my.bean.LoginBean;

import li.com.base.basesinglebean.MatchPersonBean;

import com.hyphenate.chatuidemo.my.bean.UserMsgBean;
import com.hyphenate.chatuidemo.my.bean.UserOnlineBean;
import com.hyphenate.chatuidemo.my.bean.VideoBean;
import com.hyphenate.chatuidemo.my.bean.VideoLinkBean;
import com.hyphenate.easeui.TeamUnreadBean;

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
    Observable<BaseRespose<VisonBean>> getVison(@Field("type") String type);


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

    //获取网吧杯详情
    @FormUrlEncoded
    @POST(UrlConstant.GetHundredCup_URL)
    Observable<HundredCupBean> getHundredCupDetail(@Field("token") String token,
                                                   @Field("hundred_id") String hundred_id,
                                                   @Field("group_id") String group_id);

    //创建网吧杯队伍
    @FormUrlEncoded
    @POST(UrlConstant.CreatTeam_URL)
    Observable<BaseRespose<Object>> creatTeam(@Field("token") String token,
                                              @Field("group_name") String group_name,
                                              @Field("game_name") String game_name);

    //获取网吧杯成员
    @FormUrlEncoded
    @POST(UrlConstant.GetHundredCupMember_URL)
    Observable<BaseRespose<List<CupMemberBean>>> getAllMember(@Field("token") String token,
                                                              @Field("page") String page,
                                                              @Field("page_size") String page_size,
                                                              @Field("nick_name") String nick_name);

    //邀请网吧杯队员
    @FormUrlEncoded
    @POST(UrlConstant.InviteMember_URL)
    Observable<BaseRespose<Object>> inviteMember(@Field("token") String token,
                                                 @Field("line_id") String line_id,
                                                 @Field("you_user_id") String you_user_id);

    //网吧杯队员踢出队伍
    @FormUrlEncoded
    @POST(UrlConstant.DeleteMember_URL)
    Observable<BaseRespose<Object>> deleteMember(@Field("token") String token,
                                                 @Field("line_id") String line_id,
                                                 @Field("group_id") String group_id,
                                                 @Field("type") String type,
                                                 @Field("you_user_id") String you_user_id);

    //网吧杯队长解散队伍
    @FormUrlEncoded
    @POST(UrlConstant.DeleteTeam_URL)
    Observable<BaseRespose<Object>> deleteTeam(@Field("token") String token,
                                               @Field("line_id") String line_id,
                                               @Field("group_id") String group_id);

    //网吧杯申请加入队伍
    @FormUrlEncoded
    @POST(UrlConstant.ApplyTeam_URL)
    Observable<BaseRespose<Object>> applyTeam(@Field("token") String token,
                                              @Field("line_id") String line_id);

    //网吧杯报名
    @FormUrlEncoded
    @POST(UrlConstant.TeamRegister_URL)
    Observable<BaseRespose<Object>> teamRegister(@Field("token") String token,
                                                 @Field("line_id") String line_id,
                                                 @Field("hundred_id") String hundred_id);

    //获取好友列表
    @FormUrlEncoded
    @POST(UrlConstant.GetFriend_URL)
    Observable<BaseRespose<List<CupMemberBean>>> getFriends(@Field("token") String token,
                                                            @Field("user_ids") String user_ids,
                                                            @Field("page") String page,
                                                            @Field("page_size") String page_size,
                                                            @Field("nick_name") String nick_name);

    //接受网吧杯队伍邀请
    @FormUrlEncoded
    @POST(UrlConstant.ReceiveInvite_URL)
    Observable<BaseRespose<Object>> receiveInvite(@Field("token") String token,
                                                  @Field("line_id") String line_id,
                                                  @Field("group_id") String group_id,
                                                  @Field("nick_user_id") String nick_user_id);


    //拒绝网吧杯队伍邀请
    @FormUrlEncoded
    @POST(UrlConstant.RefuseInvite_URL)
    Observable<BaseRespose<Object>> refuseInvite(@Field("token") String token,
                                                 @Field("line_id") String line_id,
                                                 @Field("group_id") String group_id,
                                                 @Field("nick_user_id") String nick_user_id);

    //获得所有队伍
    @FormUrlEncoded
    @POST(UrlConstant.GetTeam_URL)
    Observable<BaseRespose<List<CupTeamBean>>> getTeam(@Field("token") String token);

    //拒绝申请入队
    @FormUrlEncoded
    @POST(UrlConstant.RefuseApply_URL)
    Observable<BaseRespose<Object>> refuseApply(@Field("token") String token,
                                                @Field("line_id") String line_id,
                                                @Field("you_user_id") String you_user_id);

    //同意申请入队
    @FormUrlEncoded
    @POST(UrlConstant.ReceiveApply_URL)
    Observable<BaseRespose<Object>> receiveApply(@Field("token") String token,
                                                 @Field("line_id") String line_id,
                                                 @Field("you_user_id") String you_user_id);

    //获取队伍未读消息列表
    @FormUrlEncoded
    @POST(UrlConstant.GetTeamUnread_URL)
    Observable<TeamUnreadBean> getTeamUnread(@Field("token") String token);


    //接受组队匹配邀请
    @FormUrlEncoded
    @POST(UrlConstant.Accept_URL)
    Observable<BaseRespose<Object>> accept(@Field("token") String token,
                                           @Field("from_user_id") String from_user_id,
                                           @Field("team_id") String team_id);

    //拒绝组队匹配邀请
    @FormUrlEncoded
    @POST(UrlConstant.GetTeamUnread_URL)
    Observable<BaseRespose<Object>> deny(@Field("token") String token,
                                         @Field("from_user_id") String from_user_id,
                                         @Field("team_id") String team_id);

    //获取比赛赛程
    @FormUrlEncoded
    @POST(UrlConstant.GetRace_URL)
    Observable<BaseRespose<List<RaceBean>>> getRace(@Field("token") String token,
                                                    @Field("hunderd_id") String hunderd_id);


    //获取比赛积分
    @FormUrlEncoded
    @POST(UrlConstant.GetIntegral_URL)
    Observable<BaseRespose<List<IntegralBean>>> getIntegral(@Field("token") String token,
                                                            @Field("hunderd_id") String hunderd_id);


    //获取匹配成功后填写的的12个标签
    @FormUrlEncoded
    @POST(UrlConstant.GetTag_URL)
    Observable<TagBean> getTag(@Field("token") String token,
                               @Field("choice_id") String choice_id,
                               @Field("you_user_id") String you_user_id);

    //获取系统推荐标签
    @FormUrlEncoded
    @POST(UrlConstant.GetSuggestTag_URL)
    Observable<BaseRespose<List<SuggestTagBean>>> getSuggestTag(@Field("token") String token);


    //用户提交6个标签
    @FormUrlEncoded
    @POST(UrlConstant.SetTag_URL)
    Observable<BaseRespose<Object>> setTag(@Field("token") String token,
                                           @Field("choice_id") String choice_id,
                                           @Field("you_user_id") String you_user_id,
                                           @Field("label") String label);

    //用户提交6个标签
    @FormUrlEncoded
    @POST(UrlConstant.GetRandom_URL)
    Observable<BaseRespose<List<Object>>> getRandom(@Field("token") String token,
                                                    @Field("choice_id") String choice_id,
                                                    @Field("you_user_id") String you_user_id);


    //用户提交6个标签
    @FormUrlEncoded
    @POST(UrlConstant.Order_URL)
    Observable<BaseRespose<Object>> order(@Field("token") String token,
                                          @Field("goods_id") String goods_id,
                                          @Field("goods_price") String goods_price,
                                          @Field("pay_type") String pay_type);


    //退出百人群聊天
    @FormUrlEncoded
    @POST(UrlConstant.QuitHundredGroup_URL)
    Observable<BaseRespose<Object>> qiutGroup(@Field("token") String token,
                                              @Field("goods_id") String goods_id);

    //搜索用户
    @FormUrlEncoded
    @POST(UrlConstant.SearchUser)
    Observable<BaseRespose<List<UserListBean>>> searchUser(@Field("index") String index,
                                                           @Field("keyword") String keyword);

    //搜索用户
    @FormUrlEncoded
    @POST(UrlConstant.ShareVideo)
    Observable<BaseRespose<Object>> shareVideo(@Field("token") String token,
                                               @Field("video_link") String video_link);

    //保存匹配到的用户
    @FormUrlEncoded
    @POST(UrlConstant.MatchSet_URL)
    Observable<BaseRespose<Object>> matchSet(@Field("token") String token,
                                             @Field("you_user_id") String you_user_id,
                                             @Field("choice_id") String choice_id,
                                             @Field("content") String content);

    //获取保存的用户
    @FormUrlEncoded
    @POST(UrlConstant.GetUserLine_URL)
    Observable<BaseRespose<List<SaveMatchBean>>> getSaveUser(@Field("token") String token,
                                                             @Field("page") String page,
                                                             @Field("page_size") String page_size);

    //删除保存的用户
    @FormUrlEncoded
    @POST(UrlConstant.RelieveLine_URL)
    Observable<BaseRespose<Object>> relieveLine(@Field("token") String token,
                                                @Field("you_user_id") String you_user_id,
                                                @Field("type") String type);

    //开始主题匹配
    @FormUrlEncoded
    @POST(UrlConstant.NewMatchBegin_URL)
    Observable<BaseRespose<List<Object>>> matchBegin_new(@Field("token") String token,
                                                   @Field("choice_id") String choice_id,
                                                   @Field("user_sex") String user_sex,
                                                   @Field("dest_sex") String dest_sex,
                                                   @Field("type") String type,
                                                   @Field("status") String status,
                                                   @Field("destination") String destination,
                                                   @Field("location") String location);

    //接受主题匹配
    @FormUrlEncoded
    @POST(UrlConstant.NewMatchAccept_URL)
    Observable<BaseRespose<List<Object>>> matchAccept_new(@Field("token") String token,
                                                   @Field("choice_id") String choice_id,
                                                   @Field("you_user_id") String you_user_id,
                                                   @Field("other_party_id") String other_party_id,
                                                   @Field("is_status") String is_status);

    //取消主题匹配
    @FormUrlEncoded
    @POST(UrlConstant.NewMatchCancle_URL)
    Observable<BaseRespose<List<Object>>> matchCancle_new(@Field("token") String token,
                                                    @Field("choice_id") String choice_id,
                                                    @Field("status") String status);

    //获取所有城市列表
    @FormUrlEncoded
    @POST(UrlConstant.GetProvices_URL)
    Observable<BaseRespose<List<CitiesSingBean>>> getProvices(@Field("token") String token);

    //获取推荐好友列表
    @FormUrlEncoded
    @POST(UrlConstant.GetSuggest_URL)
    Observable<BaseRespose<List<Object>>> getSuggest(@Field("token") String token,
                                                    @Field("you_user_id") String you_user_id);

    //获取推荐好友列表
    @FormUrlEncoded
    @POST(UrlConstant.IsEvaluate_URL)
    Observable<BaseRespose<IsRemarkBean>> isEvaluate(@Field("token") String token,
                                                     @Field("you_user_id") String you_user_id);

}
