# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class zhuozhuo.com.zhuo.bean.** { *; }
-keep class com.hyphenate.chatuidemo.my.Bean.** { *; }
-keep class li.com.base.** { *; }

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------
#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.baidu.** {*;}
-keep class vi.com.gdi.bgl.**{*;}
-keep class com.superrtc.** {*;}
-keep class com.hyphenate.chatuidemo.utils.SmileUtils {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}
-keep class android.support.v4.** {*;}

-keep class org.xmlpull.** {*;}
-keep class com.baidu.** {*;}
-keep public class * extends com.umeng.**
-keep class com.umeng.** { *; }
-keep class com.squareup.picasso.* {*;}

-keep class com.hyphenate.** {*;}
-keep class com.hyphenate.chat.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}


#友盟登录分享
-dontshrink
-dontoptimize
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-dontwarn com.facebook.**
-keep public class javax.**
-keep public class android.webkit.**
-dontwarn android.support.v4.**
-keep enum com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public interface com.facebook.**
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public interface com.umeng.socialize.sensor.**
-keep public interface com.umeng.scrshot.**
-keep public class com.umeng.socialize.* {*;}
-keep class com.facebook.**
-keep class com.facebook.** { *; }
-keep class com.umeng.scrshot.**
-keep public class com.tencent.** {*;}
-keep class com.umeng.socialize.sensor.**
-keep class com.umeng.socialize.handler.**
-keep class com.umeng.socialize.handler.*
-keep class com.umeng.weixin.handler.**
-keep class com.umeng.weixin.handler.*
-keep class com.umeng.qq.handler.**
-keep class com.umeng.qq.handler.*
-keep class UMMoreHandler{*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
-keep class im.yixin.sdk.api.YXMessage {*;}
-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
-keep class com.tencent.mm.sdk.** {
   *;
}
-keep class com.tencent.mm.opensdk.** {
   *;
}
-keep class com.tencent.wxop.** {
   *;
}
-keep class com.tencent.mm.sdk.** {
   *;
}
-dontwarn twitter4j.**
-keep class twitter4j.** { *; }

-keep class com.tencent.** {*;}
-dontwarn com.tencent.**
-keep class com.kakao.** {*;}
-dontwarn com.kakao.**
-keep public class com.umeng.com.umeng.soexample.R$*{
    public static final int *;
}
-keep public class com.linkedin.android.mobilesdk.R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}
-keep class com.umeng.socialize.impl.ImageImpl {*;}
-keep class com.sina.** {*;}
-dontwarn com.sina.**
-keep class  com.alipay.share.sdk.** {
   *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

-keep class com.linkedin.** { *; }
-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
-keepattributes Signature
#友盟统计
-keep class com.umeng.commonsdk.** {*;}
#JSYV播放器
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**
-keep class com.shuyu.gsyvideoplayer.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.**
#Glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }
#rxjava & rxandroid
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
        long producerIndex;
        long consumerIndex;
    }
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
        rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
#okhttp3
    -dontwarn okio.**
    -dontwarn okhttp3.**
    -dontwarn com.squareup.okhttp3.**
#retrofit2
    -dontwarn retrofit2.**
    -keep class retrofit2.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions
#eventbus 3.0.0
    -keepattributes *Annotation*
    -keepclassmembers class ** {
        @org.greenrobot.eventbus.Subscribe <methods>;
    }
    -keep enum org.greenrobot.eventbus.ThreadMode { *; }
    -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
        <init>(java.lang.Throwable);
    }
#UCROP
    -dontwarn com.yalantis.ucrop**
     -keep class com.yalantis.ucrop** { *; }
     -keep interface com.yalantis.ucrop** { *; }
#litepal
     -dontwarn org.litepal.*
     -keep class org.litepal.** { *; }
     -keep enum org.litepal.**
     -keep interface org.litepal.** { *; }
     -keep public class * extends org.litepal.**
     -keepattributes *Annotation*
     -keepclassmembers enum * {
         public static **[] values();
         public static ** valueOf(java.lang.String);
     }
     -keepclassmembers class * extends org.litepal.crud.DataSupport{*;}
#阿里OOS
     -keep class com.alibaba.sdk.android.oss.** { *; }
     -dontwarn okio.**
     -dontwarn org.apache.commons.codec.binary.**
     -keep class org.apache.commons.**{ *;}
     -keep class com.alibaba.sdk.*{ *;}
#阿里hotfix
    #基线包使用，生成mapping.txt
   -printmapping mapping.txt
   #生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下
   #修复后的项目使用，保证混淆结果一致
  # -applymapping mapping.txt
   #hotfix
    -keep class com.taobao.sophix.**{*;}
   -keep class com.ta.utdid2.device.**{*;}

   -dontwarn cn.jpush.**
   -keep class cn.jpush.** { *; }
   -keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

   -dontwarn cn.jiguang.**
   -keep class cn.jiguang.** { *; }

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------



#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------



#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

#----------------------------------------------------------------------------
-ignorewarnings
#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}


-dontwarn com.tencent.**
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.beaconselfupdate.** {*;}
-keep class com.tencent.hlyyb.** {*;}
-keep class com.tencent.tmapkupdatesdk.** {*;}
-keep class com.tencent.tmassistantbase.** {*;}
-keep class com.tencent.tmdownloader.** {*;}
-keep class com.tencent.tmassistantsdk.** {*;}
-keep class com.tencent.tmselfupdatesdk.** {*;}
-keep class com.tencent.yybsdk.apkpatch.** {*;}
-keep class com.tencent.assistant.sdk.remote.**{public protected private *;}
-keep public interface com.tencent.tmassistantbase.common.download.ITMAssistantDownloadClientListener{*;}
-keep class com.qq.** {*;}
#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------


