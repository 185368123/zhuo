/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.chatuidemo.ui;


import android.Manifest;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMCallManager.EMCameraDataProcessor;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMVideoCallHelper;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;
import com.hyphenate.chatuidemo.UserMsgDBHelp;
import com.cjt2325.cameralibrary.MyInvocation;
import com.hyphenate.chatuidemo.my.bean.UserDB;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.media.EMCallSurfaceView;
import com.hyphenate.util.EMLog;
import com.superrtc.sdk.VideoView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import edu.wuwang.opengl.camera.FrameCallback;
import edu.wuwang.opengl.camera.Renderer;
import edu.wuwang.opengl.camera.TextureController;
import edu.wuwang.opengl.filter.Beauty;
import edu.wuwang.opengl.filter.LookupFilter;
import edu.wuwang.opengl.gl.YuvOutputFilter;
import edu.wuwang.opengl.utils.PermissionUtils;

import static android.hardware.camera2.CameraDevice.TEMPLATE_PREVIEW;


public class VideoCallActivity extends CallActivity implements OnClickListener, FrameCallback {

    private boolean isMuteState;
    private boolean isHandsfreeState;
    private boolean isAnswered;
    private boolean endCallTriggerByMe = false;
    private boolean monitor = true;

    // 视频通话画面显示控件，这里在新版中使用同一类型的控件，方便本地和远端视图切换
    protected EMCallSurfaceView localSurface;
    protected EMCallSurfaceView oppositeSurface;
    private int surfaceState = -1;

    private TextView callStateTextView;

    private LinearLayout comingBtnContainer;
    private Button refuseBtn;
    private Button answerBtn;
    private Button hangupBtn;
    private ImageView muteImage;
    private ImageView handsFreeImage;
    private TextView nickTextView;
    private Chronometer chronometer;
    private LinearLayout voiceContronlLayout;
    private RelativeLayout rootContainer;
    private LinearLayout topContainer;
    private LinearLayout bottomContainer;
    private TextView monitorTextView;

    private Handler uiHandler;

    private boolean isInCalling;
    boolean isRecording = false;
    //    private Button recordBtn;
    private EMVideoCallHelper callHelper;
    private Button toggleVideoBtn;

    private BrightnessDataProcess dataProcessor = new BrightnessDataProcess();
    private LinearLayout ll_user;
    private EaseImageView iv_user;
    private TextView tv_user;
    private ImageView switchCameraIv;


    // dynamic adjust brightness
    class BrightnessDataProcess implements EMCameraDataProcessor {
        byte yDelta = 0;

        synchronized void setYDelta(byte yDelta) {
            Log.d("VideoCallActivity", "brigntness uDelta:" + yDelta);
            this.yDelta = yDelta;
        }

        // data size is width*height*2
        // the first width*height is Y, second part is UV
        // the storage layout detailed please refer 2.x demo CameraHelper.onPreviewFrame
        @Override
        public synchronized void onProcessData(byte[] data, Camera camera, final int width, final int height, final int rotateAngel) {
            int wh = width * height;
            for (int i = 0; i < wh; i++) {
                int d = (data[i] & 0xFF) + yDelta;
                d = d < 16 ? 16 : d;
                d = d > 235 ? 235 : d;
                data[i] = (byte) d;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            finish();
            return;
        }
        setContentView(R.layout.em_activity_video_call);
        doopengl();
        EMClient.getInstance().callManager().getCallOptions().setEnableExternalVideoData(true);

        DemoHelper.getInstance().isVideoCalling = true;
        callType = 1;

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        uiHandler = new Handler();

        callStateTextView = (TextView) findViewById(R.id.tv_call_state);
        comingBtnContainer = (LinearLayout) findViewById(R.id.ll_coming_call);
        rootContainer = (RelativeLayout) findViewById(R.id.root_layout);
        refuseBtn = (Button) findViewById(R.id.btn_refuse_call);
        answerBtn = (Button) findViewById(R.id.btn_answer_call);
        hangupBtn = (Button) findViewById(R.id.btn_hangup_call);
        muteImage = (ImageView) findViewById(R.id.iv_mute);
        handsFreeImage = (ImageView) findViewById(R.id.iv_handsfree);
        callStateTextView = (TextView) findViewById(R.id.tv_call_state);
        nickTextView = (TextView) findViewById(R.id.tv_nick);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        voiceContronlLayout = (LinearLayout) findViewById(R.id.ll_voice_control);
        RelativeLayout btnsContainer = (RelativeLayout) findViewById(R.id.ll_btns);
        topContainer = (LinearLayout) findViewById(R.id.ll_top_container);
        bottomContainer = (LinearLayout) findViewById(R.id.ll_bottom_container);
        monitorTextView = (TextView) findViewById(R.id.tv_call_monitor);
//        recordBtn = (Button) findViewById(R.id.btn_record_video);
        Button switchCameraBtn = (Button) findViewById(R.id.btn_switch_camera);
        Button captureImageBtn = (Button) findViewById(R.id.btn_capture_image);
        SeekBar YDeltaSeekBar = (SeekBar) findViewById(R.id.seekbar_y_detal);
        switchCameraIv = (ImageView) findViewById(R.id.iv_switch_camera);
        ll_user = (LinearLayout) findViewById(R.id.ll_usermsg);
        iv_user = (EaseImageView) findViewById(R.id.iv_user);
        tv_user = (TextView) findViewById(R.id.tv_user);
        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        iv_user.setShapeType(avatarOptions.getAvatarShape());


        refuseBtn.setOnClickListener(this);
        answerBtn.setOnClickListener(this);
        hangupBtn.setOnClickListener(this);
        muteImage.setOnClickListener(this);
        handsFreeImage.setOnClickListener(this);
        rootContainer.setOnClickListener(this);
        switchCameraIv.setOnClickListener(this);
//        recordBtn.setOnClickListener(this);
        switchCameraBtn.setOnClickListener(this);
        captureImageBtn.setOnClickListener(this);

        YDeltaSeekBar.setOnSeekBarChangeListener(new YDeltaSeekBarListener());

        msgid = UUID.randomUUID().toString();
        isInComingCall = getIntent().getBooleanExtra("isComingCall", false);
        username = getIntent().getStringExtra("username");

        UserDB userDB = UserMsgDBHelp.getUserMsgDBHelp().searchByUserId(username);
        if (userDB != null) {
            nickTextView.setText(userDB.getNick_name());
            tv_user.setText(userDB.getNick_name());
            Glide.with(this).load(userDB.getPhoto_link()).into(iv_user);
        }


        // local surfaceview
        localSurface = (EMCallSurfaceView) findViewById(R.id.local_surface);
        localSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        localSurface.setOnClickListener(this);
        localSurface.setZOrderMediaOverlay(true);
        localSurface.setZOrderOnTop(true);
        //changeMirror(localSurface);

        // remote surfaceview
        oppositeSurface = (EMCallSurfaceView) findViewById(R.id.opposite_surface);
        oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);
        //changeMirror(oppositeSurface);


        // set call state listener
        addCallStateListener();
        if (!isInComingCall) {// outgoing call
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            outgoing = soundPool.load(this, R.raw.em_outgoing, 1);
            comingBtnContainer.setVisibility(View.INVISIBLE);
            ll_user.setVisibility(View.INVISIBLE);
            hangupBtn.setVisibility(View.VISIBLE);
            String st = getResources().getString(R.string.Are_connected_to_each_other);
            callStateTextView.setText(st);
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
            handler.sendEmptyMessage(MSG_CALL_MAKE_VIDEO);
            handler.postDelayed(new Runnable() {
                public void run() {
                    streamID = playMakeCallSounds();
                }
            }, 300);
        } else { // incoming call
            callStateTextView.setText("等待接听......");
            if (EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.IDLE
                    || EMClient.getInstance().callManager().getCallState() == EMCallStateChangeListener.CallState.DISCONNECTED) {
                // the call has ended
                finish();
                return;
            }
            voiceContronlLayout.setVisibility(View.INVISIBLE);
            localSurface.setVisibility(View.INVISIBLE);
            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            audioManager.setMode(AudioManager.MODE_RINGTONE);
            audioManager.setSpeakerphoneOn(true);
            ringtone = RingtoneManager.getRingtone(this, ringUri);
            ringtone.play();
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }

        final int MAKE_CALL_TIMEOUT = 50 * 1000;
        handler.removeCallbacks(timeoutHangup);
        handler.postDelayed(timeoutHangup, MAKE_CALL_TIMEOUT);

        // get instance of call helper, should be called after setSurfaceView was called
        callHelper = EMClient.getInstance().callManager().getVideoCallHelper();

        EMClient.getInstance().callManager().setCameraDataProcessor(dataProcessor);
    }

    private void changeMirror(EMCallSurfaceView surface) {
        // 获取 surfaceView中的 surfaceHolder
        SurfaceHolder mSurfaceHolder = surface.getHolder();
        // 创建代理接口的实现
        MyInvocation testInvocation = new MyInvocation(mSurfaceHolder);
        // 为 mSurfaceHolder 添加动态代理,并获取添加代理之后的 newSurfaceHolder
        SurfaceHolder newSurfaceHolder = (SurfaceHolder) Proxy.newProxyInstance(mSurfaceHolder.getClass().getClassLoader(), mSurfaceHolder.getClass().getInterfaces(), testInvocation);
        // 获取mSurfaceHolder的field
        Field fieldHolder = null;
        try {
            fieldHolder = SurfaceView.class.getDeclaredField("mSurfaceHolder");
            // 更改为可访问权限
            fieldHolder.setAccessible(true);
            // 用添加代理后的 newSurfaceHolder 替换 mSurfaceHolder
            fieldHolder.set(surface,newSurfaceHolder);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    class YDeltaSeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            dataProcessor.setYDelta((byte) (20.0f * (progress - 50) / 50.0f));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    /**
     * 切换通话界面，这里就是交换本地和远端画面控件设置，以达到通话大小画面的切换
     */
    private void changeCallView() {
        if (surfaceState == 0) {
            surfaceState = 1;
            EMClient.getInstance().callManager().setSurfaceView(oppositeSurface, localSurface);
        } else {
            surfaceState = 0;
            EMClient.getInstance().callManager().setSurfaceView(localSurface, oppositeSurface);
        }
    }

    /**
     * set call state listener
     */
    void addCallStateListener() {
        callStateListener = new EMCallStateChangeListener() {

            @Override
            public void onCallStateChanged(final CallState callState, final CallError error) {
                switch (callState) {

                    case CONNECTING: // is connecting
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                callStateTextView.setText(R.string.Are_connected_to_each_other);
                            }

                        });
                        break;
                    case CONNECTED: // connected
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
//                            callStateTextView.setText(R.string.have_connected_with);
                            }

                        });
                        break;

                    case ACCEPTED: // call is accepted
                        surfaceState = 0;
                        handler.removeCallbacks(timeoutHangup);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (soundPool != null)
                                        soundPool.stop(streamID);
                                    EMLog.d("EMCallManager", "soundPool stop ACCEPTED");
                                } catch (Exception e) {
                                }
                                openSpeakerOn();
                                handsFreeImage.setImageResource(R.drawable.em_icon_speaker_on);
                                isHandsfreeState = true;
                                isInCalling = true;
                                chronometer.setVisibility(View.VISIBLE);
                                chronometer.setBase(SystemClock.elapsedRealtime());
                                // call durations start
                                chronometer.start();
                                nickTextView.setVisibility(View.INVISIBLE);
                                callStateTextView.setText("");
//                            recordBtn.setVisibility(View.VISIBLE);
                                callingState = CallingState.NORMAL;
                                startMonitor();
                            }

                        });
                        break;
                    case NETWORK_DISCONNECTED:
                        runOnUiThread(new Runnable() {
                            public void run() {

                            }
                        });
                        break;
                    case NETWORK_UNSTABLE:
                        runOnUiThread(new Runnable() {
                            public void run() {

                                if (error == CallError.ERROR_NO_DATA) {
                                    //R.string.no_call_data
                                } else {
                                    //R.string.network_unstable
                                }
                            }
                        });
                        break;
                    case NETWORK_NORMAL:
                        runOnUiThread(new Runnable() {
                            public void run() {

                            }
                        });
                        break;
                    case VIDEO_PAUSE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VIDEO_PAUSE", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case VIDEO_RESUME:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VIDEO_RESUME", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case VOICE_PAUSE:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VOICE_PAUSE", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case VOICE_RESUME:
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "VOICE_RESUME", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case DISCONNECTED: // call is disconnected
                        handler.removeCallbacks(timeoutHangup);
                        @SuppressWarnings("UnnecessaryLocalVariable") final CallError fError = error;
                        runOnUiThread(new Runnable() {
                            private void postDelayedCloseMsg() {
                                uiHandler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        removeCallStateListener();
                                        saveCallRecord();
                                        Animation animation = new AlphaAnimation(1.0f, 0.0f);
                                        animation.setDuration(1200);
                                        rootContainer.startAnimation(animation);
                                        finish();
                                    }

                                }, 200);
                            }

                            @Override
                            public void run() {
                                chronometer.stop();
                                callDruationText = chronometer.getText().toString();
                                String s1 = getResources().getString(R.string.The_other_party_refused_to_accept);
                                String s2 = getResources().getString(R.string.Connection_failure);
                                String s3 = getResources().getString(R.string.The_other_party_is_not_online);
                                String s4 = getResources().getString(R.string.The_other_is_on_the_phone_please);
                                String s5 = getResources().getString(R.string.The_other_party_did_not_answer);

                                String s6 = getResources().getString(R.string.hang_up);
                                String s7 = getResources().getString(R.string.The_other_is_hang_up);
                                String s8 = getResources().getString(R.string.did_not_answer);
                                String s9 = getResources().getString(R.string.Has_been_cancelled);
                                String s10 = getResources().getString(R.string.Refused);

                                if (fError == CallError.REJECTED) {
                                    callingState = CallingState.BEREFUSED;
                                    callStateTextView.setText(s1);
                                } else if (fError == CallError.ERROR_TRANSPORT) {
                                    callStateTextView.setText(s2);
                                } else if (fError == CallError.ERROR_UNAVAILABLE) {
                                    callingState = CallingState.OFFLINE;
                                    callStateTextView.setText(s3);
                                } else if (fError == CallError.ERROR_BUSY) {
                                    callingState = CallingState.BUSY;
                                    callStateTextView.setText(s4);
                                } else if (fError == CallError.ERROR_NORESPONSE) {
                                    callingState = CallingState.NO_RESPONSE;
                                    callStateTextView.setText(s5);
                                } else if (fError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || fError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                                    callingState = CallingState.VERSION_NOT_SAME;
                                    callStateTextView.setText(R.string.call_version_inconsistent);
                                } else {
                                    if (isRefused) {
                                        callingState = CallingState.REFUSED;
                                        callStateTextView.setText(s10);
                                    } else if (isAnswered) {
                                        callingState = CallingState.NORMAL;
                                        if (endCallTriggerByMe) {
//                                        callStateTextView.setText(s6);
                                        } else {
                                            callStateTextView.setText(s7);
                                        }
                                    } else {
                                        if (isInComingCall) {
                                            callingState = CallingState.UNANSWERED;
                                            callStateTextView.setText(s8);
                                        } else {
                                            if (callingState != CallingState.NORMAL) {
                                                callingState = CallingState.CANCELLED;
                                                callStateTextView.setText(s9);
                                            } else {
                                                callStateTextView.setText(s6);
                                            }
                                        }
                                    }
                                }
                                Toast.makeText(VideoCallActivity.this, callStateTextView.getText(), Toast.LENGTH_SHORT).show();
                                postDelayedCloseMsg();
                            }

                        });

                        break;

                    default:
                        break;
                }

            }
        };
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener);
    }

    void removeCallStateListener() {
        EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.local_surface) {
            changeCallView();

        } else if (i == R.id.btn_refuse_call) {
            isRefused = true;
            refuseBtn.setEnabled(false);
            handler.sendEmptyMessage(MSG_CALL_REJECT);

        } else if (i == R.id.btn_answer_call) {
            EMLog.d(TAG, "btn_answer_call clicked");
            answerBtn.setEnabled(false);
            openSpeakerOn();
            if (ringtone != null)
                ringtone.stop();
            callStateTextView.setText(getResources().getString(R.string.Are_connected_to_each_other));
            handler.sendEmptyMessage(MSG_CALL_ANSWER);
            handsFreeImage.setImageResource(R.drawable.em_icon_speaker_on);
            isAnswered = true;
            isHandsfreeState = true;
            comingBtnContainer.setVisibility(View.INVISIBLE);
            ll_user.setVisibility(View.INVISIBLE);
            hangupBtn.setVisibility(View.VISIBLE);
            voiceContronlLayout.setVisibility(View.VISIBLE);
            localSurface.setVisibility(View.VISIBLE);

        } else if (i == R.id.btn_hangup_call) {
            hangupBtn.setEnabled(false);
            chronometer.stop();
            endCallTriggerByMe = true;
            callStateTextView.setText(getResources().getString(R.string.hanging_up));
            if (isRecording) {
                callHelper.stopVideoRecord();
            }
            EMLog.d(TAG, "btn_hangup_call");
            handler.sendEmptyMessage(MSG_CALL_END);

        } else if (i == R.id.iv_mute) {
            if (isMuteState) {
                // resume voice transfer
                muteImage.setImageResource(R.drawable.em_icon_mute_normal);
                try {
                    EMClient.getInstance().callManager().resumeVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = false;
            } else {
                // pause voice transfer
                muteImage.setImageResource(R.drawable.em_icon_mute_on);
                try {
                    EMClient.getInstance().callManager().pauseVoiceTransfer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                isMuteState = true;
            }

        } else if (i == R.id.iv_handsfree) {
            if (isHandsfreeState) {
                // turn off speaker
                handsFreeImage.setImageResource(R.drawable.em_icon_speaker_normal);
                closeSpeakerOn();
                isHandsfreeState = false;
            } else {
                handsFreeImage.setImageResource(R.drawable.em_icon_speaker_on);
                openSpeakerOn();
                isHandsfreeState = true;
            }

        } else if (i == R.id.root_layout) {
            if (callingState == CallingState.NORMAL) {
                if (bottomContainer.getVisibility() == View.VISIBLE) {
                    bottomContainer.setVisibility(View.GONE);
                    topContainer.setVisibility(View.GONE);
                    //oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill);

                } else {
                    bottomContainer.setVisibility(View.VISIBLE);
                    topContainer.setVisibility(View.VISIBLE);
                    //oppositeSurface.setScaleMode(VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFit);
                }
            }

        } else if (i == R.id.btn_switch_camera) {
            handler.sendEmptyMessage(MSG_CALL_SWITCH_CAMERA);

        } else if (i == R.id.iv_switch_camera) {
            cameraId = cameraId == 1 ? 0 : 1;
            mRenderer.switchCamera();

        } else if (i == R.id.btn_capture_image) {
            DateFormat df = new DateFormat();
            Date d = new Date();
            File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            final String filename = storage.getAbsolutePath() + "/" + df.format("MM-dd-yy--h-mm-ss", d) + ".jpg";
            EMClient.getInstance().callManager().getVideoCallHelper().takePicture(filename);
        } else {
        }
    }

    @Override
    protected void onDestroy() {
        DemoHelper.getInstance().isVideoCalling = false;
        stopMonitor();
        if (isRecording) {
            callHelper.stopVideoRecord();
            isRecording = false;
        }
        localSurface.getRenderer().dispose();
        localSurface = null;
        oppositeSurface.getRenderer().dispose();
        oppositeSurface = null;
        super.onDestroy();
        if (mController != null) {
            mController.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        callDruationText = chronometer.getText().toString();
        super.onBackPressed();
    }

    /**
     * for debug & testing, you can remove this when release
     */
    void startMonitor() {
        monitor = true;
        new Thread(new Runnable() {
            public void run() {
                while (monitor) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            monitorTextView.setText("WidthxHeight：" + callHelper.getVideoWidth() + "x" + callHelper.getVideoHeight()
                                    + "\nDelay：" + callHelper.getVideoLatency()
                                    + "\nFramerate：" + callHelper.getVideoFrameRate()
                                    + "\nLost：" + callHelper.getVideoLostRate()
                                    + "\nLocalBitrate：" + callHelper.getLocalBitrate()
                                    + "\nRemoteBitrate：" + callHelper.getRemoteBitrate());
                        }
                    });
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }, "CallMonitor").start();
    }

    void stopMonitor() {
        monitor = false;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().pauseVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isInCalling) {
            try {
                EMClient.getInstance().callManager().resumeVideoTransfer();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    private SurfaceView mSurfaceView;
    private TextureController mController;
    private VideoCallActivity.Camera1Renderer mRenderer;
    private int cameraId = 1;
    private LookupFilter mLookupFilter;
    private Beauty mBeautyFilter;
    private Button button_b;
    private boolean flag = true;


    private void doopengl() {
        PermissionUtils.askPermission(this, new String[]{Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE}, 10, initViewRunnable);

        mLookupFilter.setIntensity(0.80f);
        mBeautyFilter.setFlag(5);
        button_b = (Button) findViewById(R.id.button_bb);
        button_b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    mLookupFilter.setIntensity(0.80f);
                    mBeautyFilter.setFlag(5);
                    button_b.setBackground(ContextCompat.getDrawable(VideoCallActivity.this, R.drawable.video_button_shape_));
                    flag = false;
                } else {
                    mLookupFilter.setIntensity(0.10f);
                    mBeautyFilter.setFlag(1);
                    button_b.setBackground(ContextCompat.getDrawable(VideoCallActivity.this, R.drawable.video_button_shape));
                    flag = true;
                }

            }
        });
    }

    private Runnable initViewRunnable = new Runnable() {
        @Override
        public void run() {
            //TODO 设置数据源
           /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
              mRenderer = new VideoCallActivity.Camera2Renderer();
            } else {*/
            mRenderer = new VideoCallActivity.Camera1Renderer();
            // }
            mSurfaceView = (SurfaceView)findViewById(R.id.mSurface);
            mController = new TextureController(VideoCallActivity.this);
            onFilterSet(mController);
            mController.setFrameCallback(720, 1280, VideoCallActivity.this);
            mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mController.surfaceCreated(holder);
                    mController.setRenderer(mRenderer);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    mController.surfaceChanged(width, height);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    mController.surfaceDestroyed();
                }
            });
        }
    };

    protected void onFilterSet(TextureController controller) {
        mLookupFilter = new LookupFilter(getResources());
        mLookupFilter.setMaskImage("lookup/purity.png");
        mLookupFilter.setIntensity(0.0f);
        controller.addFilter(mLookupFilter);
        mBeautyFilter = new Beauty(getResources());
        controller.addFilter(mBeautyFilter);
    }

    private YuvOutputFilter mOutputFilter;
    private int picX = 368;
    private int picY = 640;
    private byte[] tempBuffer;


    @Override
    public void onFrame(int var1, int var2, int var3) {
        if (mOutputFilter == null) {
            mOutputFilter = new YuvOutputFilter(YuvOutputFilter.EXPORT_TYPE_NV21);
            mOutputFilter.create();
            mOutputFilter.sizeChanged(picX, picY);
            mOutputFilter.setInputTextureSize(var2, var3);
            tempBuffer = new byte[picX * picY * 3 / 2];
        }
        mOutputFilter.drawToTexture(var1);
        mOutputFilter.getOutput(tempBuffer, 0, picX * picY * 3 / 2);
        //EMClient.getInstance().callManager().inputExternalVideoData(rotateYUVDegree270AndMirror(tempBuffer,picX, picY), picY, picX, 270);
        EMClient.getInstance().callManager().inputExternalVideoData(tempBuffer, picX, picY, 0);
    }


    private byte[] rotateYUVDegree270AndMirror(byte[] data, int imageWidth, int imageHeight) {
        byte[] yuv = new byte[imageWidth * imageHeight * 3 / 2];
// Rotate and mirror the Y luma
        int i = 0;
        int maxY = 0;
        for (int x = imageWidth - 1; x >= 0; x--) {
            maxY = imageWidth * (imageHeight - 1) + x * 2;
            for (int y = 0; y < imageHeight; y++) {
                yuv[i] = data[maxY - (y * imageWidth + x)];
                i++;
            }
        }
// Rotate and mirror the U and V color components
        int uvSize = imageWidth * imageHeight;
        i = uvSize;
        int maxUV = 0;
        for (int x = imageWidth - 1; x > 0; x = x - 2) {
            maxUV = imageWidth * (imageHeight / 2 - 1) + x * 2 + uvSize;
            for (int y = 0; y < imageHeight / 2; y++) {
                yuv[i] = data[maxUV - 2 - (y * imageWidth + x - 1)];
                i++;
                yuv[i] = data[maxUV - (y * imageWidth + x)];
                i++;
            }
        }
        return yuv;
    }

    private class Camera1Renderer implements Renderer {

        private Camera mCamera;

        @Override
        public void onDestroy() {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }

        public void switchCamera() {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            mCamera = Camera.open(cameraId);
            mController.setImageDirection(cameraId);
            Camera.Size size = mCamera.getParameters().getPreviewSize();
            mController.setDataSize(size.height, size.width);
            try {
                mCamera.setPreviewTexture(mController.getTexture());
                mController.getTexture().setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                    @Override
                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        mController.requestRender();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            mCamera = Camera.open(cameraId);
            mController.setImageDirection(cameraId);
            Camera.Size size = mCamera.getParameters().getPreviewSize();
            mController.setDataSize(size.height, size.width);
            try {
                mCamera.setPreviewTexture(mController.getTexture());
                mController.getTexture().setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                    @Override
                    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                        mController.requestRender();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private class Camera2Renderer implements Renderer {

        CameraDevice mDevice;
        CameraManager mCameraManager;
        private HandlerThread mThread;
        private Handler mHandler;
        private Size mPreviewSize;
        private Runnable mRunnable;

        Camera2Renderer() {
            mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            mThread = new HandlerThread("camera2 ");
            mThread.start();
            mHandler = new Handler(mThread.getLooper());
        }

        @Override
        public void onDestroy() {
            if (mDevice != null) {
                mDevice.close();
                mDevice = null;
            }
        }

        public void switchCamera() {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    cameraId = cameraId == 1 ? 0 : 1;
                }
            };
            onPause();
            onResume();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            try {
                if (mDevice != null) {
                    mDevice.close();
                    mDevice = null;
                }
                if (mRunnable != null) {
                    mRunnable.run();
                    mRunnable = null;
                }
                mRunnable.run();
                CameraCharacteristics c = mCameraManager.getCameraCharacteristics(cameraId + "");
                StreamConfigurationMap map = c.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size[] sizes = map.getOutputSizes(SurfaceHolder.class);
                //自定义规则，选个大小
                mPreviewSize = sizes[0];
                mController.setDataSize(mPreviewSize.getHeight(), mPreviewSize.getWidth());
                mCameraManager.openCamera(cameraId + "", callback, mHandler);
            } catch (SecurityException | CameraAccessException e) {
                e.printStackTrace();
            }
        }

        CameraDevice.StateCallback callback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                mDevice = camera;
                try {
                    Surface surface = new Surface(mController
                            .getTexture());
                    final CaptureRequest.Builder builder = mDevice.createCaptureRequest
                            (TEMPLATE_PREVIEW);
                    builder.addTarget(surface);
                    mController.getTexture().setDefaultBufferSize(
                            mPreviewSize.getWidth(), mPreviewSize.getHeight());
                    mDevice.createCaptureSession(Arrays.asList(surface), new
                            CameraCaptureSession.StateCallback() {
                                @Override
                                public void onConfigured(CameraCaptureSession session) {
                                    try {
                                        session.setRepeatingRequest(builder.build(), new CameraCaptureSession.CaptureCallback() {
                                            @Override
                                            public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
                                                super.onCaptureProgressed(session, request, partialResult);
                                            }

                                            @Override
                                            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                                                super.onCaptureCompleted(session, request, result);
                                                mController.requestRender();
                                            }
                                        }, mHandler);
                                    } catch (CameraAccessException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onConfigureFailed(CameraCaptureSession session) {

                                }
                            }, mHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
                mDevice = null;
            }

            @Override
            public void onError(CameraDevice camera, int error) {

            }
        };


        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}
