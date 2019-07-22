package com.watiao.yishuproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.watiao.yishuproject.activity.BangDingShouJIHaoActivity;
import com.watiao.yishuproject.base.BaseActivity2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity2 {

    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.txt_phone)
    AppCompatEditText mTxtPhone;
    @BindView(R.id.txt_yzm)
    AppCompatEditText mTxtYzm;
    @BindView(R.id.yzm)
    RelativeLayout mYzm;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.other)
    RelativeLayout mOther;
    @BindView(R.id.wechat)
    LinearLayout mWechat;
    public static Activity mActivity;
    @BindView(R.id.huoqu_yzm)
    TextView mHuoquYzm;
    private  CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        QMUIStatusBarHelper.setStatusBarLightMode(RegisterActivity.this);
        String s = sHA1(RegisterActivity.this);
        Log.d("sfasfa", s);
        mTxtYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mTxtPhone.getText().toString().equals("") && !mTxtYzm.getText().toString().equals("") && mTxtYzm.getText().toString().length() == 6) {
                    mLogin.setBackground(getResources().getDrawable(R.drawable.btn_border2));
                    mLogin.setClickable(true);
                }
            }
        });

        mActivity = this;

         timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
               mHuoquYzm.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                mHuoquYzm.setText("获取验证码");
            }
        };

    }


    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.login)
    void login() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }


    @OnClick(R.id.wechat)
    void wechat() {
        startActivity(new Intent(RegisterActivity.this, BangDingShouJIHaoActivity.class));
    }

    @OnClick(R.id.huoqu_yzm)
    void yzm(){
        timer.start();
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            Log.d("sfasfa", context.getPackageName());
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
