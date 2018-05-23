package com.hyphenate.chatuidemo.my.api;

/**
 * Created by Administrator on 2017/10/17.
 */

public class UrlConstant {
    //服务器地址
    public static final String Base_URL="http://119.23.251.79";

    //服务器地址
    public static final String Base1_URL="http://119.23.251.79";

    //获取当前最新版本号
    public static final String GetVison_URL="/cron/get_version";

    //隐私政策
    public static final String Privacy_URL="http://www.zhuozhuotech.xin/privacy.html";

    //服务条款
    public static final String Eula_URL="http://www.zhuozhuotech.xin/eula.html";

    //注册协议
    public static final String Terms_URL="http://www.zhuozhuotech.xin/terms.html";

    //登录地址
    public static final String Login_URL="/user/phone_login";

    public static final String WeiXinLogin_URL="/user/wechat_login";

    //获取手机验证码
    public static  final String GetPhone_URL="/user/sendSMS";

    //忘记密码
    public static  final String FrogetPassword_URL="/user/reset_password";

    //注册地址
    public static final String Register_URL="/user/signup";

    //修改信息地址
    public static final String Change_URL="/handler/set_properties";

    //登出账户
    public static  final String LogOut_URL="/handler/logout";

    //获取博客视频列表地址
    public static final String GetVideo_URL="/share/get_video_list";

    //单人匹配
    public static final String SingleMatch_URL="/handler/start_single_match_v4";

    //跳过此次匹配
    public static final String SkipMatch_URL="/handler/skip_single_match_v3";

    //接受此次匹配
    public static final String AcceptMatch_URL="/handler/accept_single_match_v3";

    //取消匹配
    public static final String CancelMatch_URL="/Evaluate/cancel_wait";

    //对他人评价
    public static final String Remark_URL="/handler/set_remark";

    //获取在线用户列表
    public static final String GetUser_URL="/handler/get_online";

    //获取七个选项
    public static final String GetChoices_URL="/handler/get_choices";

    //个人条件和问题获取
    //public static final String GetChoicesList_URL="/handler/get_choices_list";
    public static final String GetChoicesList_URL="/Evaluate/get_choices_list";

    //获取个人故事视频列表
    public static final String GetStory_URL="/handler/get_story_list";

    //获取详细评论列表
    public static final String GetComment_URL="/share/get_comment_list/";

    //获取对我的评论列表
    public static final String GetRemark_URL="/handler/get_remark_list";

    //获取所有帖子列表
    public static final String GetAllArticle_URL="/share/get_blog_list";

    //获取我的帖子列表
    public static final String GetMyArticle_URL="/handler/get_blog_list";

    //获取一个用户的详细信息
    public static final String GetUserMsg_URL="/user/get_profile";

    //评论
    public static final String Comment_URL="/handler/set_comment";

    //点赞和取消点赞
    public static final String Zan_URL="/handler/set_share_like";

    //删除故事视频
    public static final String DelVideo_URL="/handler/delete_video";

    //获取BBS的详细信息
    public static final String GetBBS_URL="/share/get_blog";


    //写一篇BBS
    public static final String WriteArticle_URL="/handler/set_blog";

    //获取我的未读列表
    public static final String GetUnRead_URL="/handler/get_unread_share_list";

    //将未读标记已读
    public static final String SetRead_URL="/handler/set_read";

    //清空未读列表
    public static final String ClearUnread_URL="/handler/set_read";


    //设置成为队长
    public static final String SetCaptain_URL="/handler/set_captain";

    //邀请在线玩家
    public static final String Invite_URL="/handler/group_invite";

    //接受邀请
    public static final String Accept_URL="/handler/group_accept";

    //拒绝邀请
    public static final String Deny_URL="/handler/group_deny";

    //获取组队条件
    public static final String GroupMatch_URL="/handler/start_group_match";

    //取消组队匹配
    public static final String CancelGroupMatch_URL="/handler/cancel_group_wait";

    //获取百人团最新条件群
    public static final String GetHundred_URL="/Line/get_hundred";

    //获取百人团排名接口
    public static final String GetTotalRate_URL="/handler/get_total_rate";

    //获取百人团历史记录排名接口
    public static final String GetHistoryRate_URL="/handler/get_history_rate";

    //获取百人团历史记录排名接口
    public static final String Test_URL="/handler/android";

    /**
     * 设置步骤图片
     */
    public static final String SetStepPhoto = "/handler/set_step_photo";
    /**
     * 开始生成视频
     */
    public static final String StartVideo = "/handler/start_video";
    /**
     * 获取生成视频
     */
    public static final String GetVideo = " /Evaluate/get_video";
    /**
     * 获取搜索列表
     */
    public static final String SearchUser = "/user/search";
    /**
     * 分享视频
     */
    public static final String ShareVideo = "/handler/share_video";

    /**
     * 获取用户信息
     */
    public static final String GetUserMsg = "/user/get_profile";

    //获取组队条件
    public static final String GetGroupChoices_URL="/handler/get_choices_list_group";

    //获取组队匹配状态
    public static final String GetGroupStatus_URL="/handler/get_group_match_status";

    //设置组队评价
    public static final String SetGroupRemark_URL="/handler/set_group_remark";

    //设置筹码金额
    public static final String SetMoney_URL="/handler/set_choice_money";

    //退出队伍
    public static final String LeaveGroup_URL="/handler/group_quit";

    //加入百人团
    public static final String JoinHundredGroup_URL="/handler/join_hundred";

    //退出百人团
    public static final String QuitHundredGroup_URL="/handler/quit_hundred";

    //获取得分列表
    public static final String GetHundredRate_URL="/handler/get_hundred_rate";

    //获取用户状态
    public static final String GetStaus_URL="/Evaluate/get_match_status";


    //匹配接口（新）
    public static final String Play_Match_URL="/handler/player_match";

    //接受匹配（新）
    public static final String Play_Accept_URL="/handler/player_accept";

    //已匹配列表（新）
    public static final String Get_Accept_All_URL="/Evaluate/get_accept_all";

    //最后一步完成修改两个用户finish值（新）
    public static final String Edit_Finish_URL=" /Evaluate/editfinish";

    //评价接口（新）
    public static final String Evaluate_URL="/Evaluate/evaluate";

    //获取网吧杯详情
    public static final String GetHundredCup_URL="/Line/getHundred";

    //创建网吧杯队伍
    public static final String CreatTeam_URL="/Line/addGroup";

    //获取全部成员
    public static final String GetHundredCupMember_URL="/Line/searchUser";

    //邀请队员
    public static final String InviteMember_URL="/Line/joinGroup";

    //踢出队员,退出组队
    public static final String DeleteMember_URL="/Line/GroupSignOut";

    // 解散队伍
    public static final String DeleteTeam_URL="/Line/dissolutionGroup";

    // 申请加入队伍
    public static final String ApplyTeam_URL="Line/applyJoinGroup";

    // 队伍报名
    public static final String TeamRegister_URL="/Line/joinMatch";

    // 获取好友
    public static final String GetFriend_URL="/Line/getUserData";

    // 获取全部队伍
    public static final String GetTeam_URL="/Line/getGroupUser";

    // 接受邀请
    public static final String ReceiveInvite_URL="/Line/receiveJoinGroup";

    // 拒绝邀请
    public static final String RefuseInvite_URL="/Line/refuseJoinGroup";

    // 同意申请
    public static final String ReceiveApply_URL="/Line/approvalJoinGroup";

    // 拒绝申请
    public static final String RefuseApply_URL="/Line/refuseCaptainJoinGroup";

    // 获取未读消息列表
    public static final String GetTeamUnread_URL="/Line/getUserMessage";

    // 获取比赛赛程
    public static final String GetRace_URL="/Line/getRace";

    // 获取比赛积分
    public static final String GetIntegral_URL="/Line/getIntegral";

    // 获取匹配成功后填写的的12个标签
    public static final String GetTag_URL="/Line/labelData";

    // 用户提交6个标签
    public static final String SetTag_URL="/Line/labelMatch";

    // 获取系统推荐标签
    public static final String GetSuggestTag_URL="/Line/label";


    // 生成随机数
    public static final String GetRandom_URL="/Line/randomMatch";

    //
    public static final String Order_URL="/OrderPay/order_goods";
}
