package com.watiao.yishuproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.aliyun.apsara.alivclittlevideo.sts.OnStsResultListener;
import com.aliyun.apsara.alivclittlevideo.sts.StsInfoManager;
import com.aliyun.apsara.alivclittlevideo.sts.StsTokenInfo;
import com.aliyun.apsara.alivclittlevideo.view.mine.AlivcLittleUserManager;
import com.aliyun.video.common.utils.ToastUtils;
import com.aliyun.video.common.widget.AlivcCustomAlertDialog;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {


    /**
     * 用户信息是否初始化成功
     */
    private boolean isUserInitSuccess;
    /**
     * sts信息是否初始化成功
     */
    private boolean isStsInitSuccess;
    private String message;

    private Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUserInfo();
        setContentView(R.layout.activity_splash);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SplashActivity.this,RegisterActivity.class);
                    if(getIntent().getBundleExtra("消息")!=null){
                        intent.putExtra("消息", getIntent().getBundleExtra("消息"));
                    }
                    startActivity(intent);
                    finish();

                }
            },2000);


    }


    /**
     * 初始化sts信息
     */
    private void initStsInfo(){
        StsInfoManager.getInstance().refreshStsToken(new OnStsResultListener() {
            @Override
            public void onSuccess(StsTokenInfo tokenInfo) {
                isStsInitSuccess = true;
            }

            @Override
            public void onFail() {
                isStsInitSuccess = true;
                ToastUtils.show(SplashActivity.this, "获取sts信息失败");
            }
        });
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        AlivcLittleUserManager.getInstance().setmRequestInitUserCallback(
                new AlivcLittleUserManager.OnRequestInitUserCallback() {
                    @Override
                    public void onSuccess() {
                        isUserInitSuccess = true;
                        initStsInfo();
                    }

                    @Override
                    public void onFailure(final String errorMsg) {
                        isUserInitSuccess = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showUserInfoErrorTip(errorMsg);
                            }
                        });

                    }
                });
        AlivcLittleUserManager.getInstance().init(this);

    }
    WeakReference<SplashActivity> weakReference = new WeakReference<>(this);
    private void showUserInfoErrorTip(String errorMsg) {

        SplashActivity splashActivity = weakReference.get();
        if (splashActivity == null) {
            return;
        }
        AlivcCustomAlertDialog errorTips = new AlivcCustomAlertDialog.Builder(SplashActivity.this)
                .setMessage("获取用户信息失败 \n 错误信息: " + errorMsg)
                .setDialogClickListener("重试", "关闭", new AlivcCustomAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onConfirm() {
                        initUserInfo();
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }
                })
                .create();
        errorTips.setCanceledOnTouchOutside(false);
        errorTips.setCancelable(false);
        errorTips.show();
    }

}
