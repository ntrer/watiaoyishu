package com.watiao.yishuproject.fragment.SaiShiXiangQingFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.BaoMingXuanZeActivity;
import com.watiao.yishuproject.activity.ZhuBanFangActivity;
import com.watiao.yishuproject.base.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class XiangQingFragment extends BaseFragment {


    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.jianjie)
    TextView mJianjie;
    @BindView(R.id.jichuxinxi)
    RelativeLayout mJichuxinxi;
    @BindView(R.id.jichu)
    RelativeLayout mJichu;
    @BindView(R.id.saishijieshao)
    RelativeLayout mSaishijieshao;
    @BindView(R.id.wb_img)
    WebView mWbImg;
    @BindView(R.id.jieshaoneirong)
    RelativeLayout mJieshaoneirong;
    @BindView(R.id.jifenjiangli)
    RelativeLayout mJifenjiangli;
    @BindView(R.id.diyitidui)
    TextView mDiyitidui;
    @BindView(R.id.fenshu1)
    TextView mFenshu1;
    @BindView(R.id.diertidui)
    TextView mDiertidui;
    @BindView(R.id.fenshu2)
    TextView mFenshu2;
    @BindView(R.id.disantidui)
    TextView mDisantidui;
    @BindView(R.id.fenshu3)
    TextView mFenshu3;
    @BindView(R.id.jianglieneirong)
    RelativeLayout mJianglieneirong;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.day)
    TextView mDays;
    @BindView(R.id.tian)
    TextView mTian;
    @BindView(R.id.hour)
    TextView mHours;
    @BindView(R.id.maohao1)
    TextView mMaohao1;
    @BindView(R.id.min)
    TextView mMins;
    @BindView(R.id.maohao2)
    TextView mMaohao2;
    @BindView(R.id.sec)
    TextView mSec;
    @BindView(R.id.daojishi)
    RelativeLayout mDaojishi;
    @BindView(R.id.login)
    Button mLogin;
    Unbinder unbinder;
    private long mDay = 23;// 天
    private long mHour = 11;//小时,
    private long mMin = 56;//分钟,
    private long mSecond = 32;//秒

    private Timer mTimer;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                mDays.setText(mDay + "");//天数不用补位
                mHours.setText(getTv(mHour));
                mMins.setText(getTv(mMin));
                mSec.setText(getTv(mSecond));
                if (mSecond == 0 && mDay == 0 && mHour == 0 && mMin == 0) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }


    public XiangQingFragment() {
        // Required empty public constructor
    }

    public static XiangQingFragment newInstance() {
        return new XiangQingFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mTimer = new Timer();
        inidWebView();
        startRun();
        mProgress.setMax(100);
        mProgress.setProgress(50);
    }

    private void inidWebView() {
        WebSettings settings = mWbImg.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(false); //选择内置缩放机制与单独缩放控件；
        settings.setDisplayZoomControls(false); //是否显示缩放控件
        settings.setSupportZoom(false);  //是否支持缩放
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWbImg.setInitialScale(145); //第一次显示的缩放比例，例子是120%;
        mWbImg.getSettings().setJavaScriptEnabled(true);//启用js
        mWbImg.getSettings().setBlockNetworkImage(false);//解决图片不显示
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWbImg.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWbImg.loadUrl("https://baidu.com");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_xiangqing, null);
        return view;
    }


    @OnClick(R.id.jichuxinxi)
    void zhubanfang() {
        startActivity(new Intent(getContext(), ZhuBanFangActivity.class));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    /**
     * 开启倒计时
     * //time为Date类型：在指定时间执行一次。
     * timer.schedule(task, time);
     * //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     * timer.schedule(task, firstTime,period);
     * //delay 为long类型：从现在起过delay毫秒执行一次。
     * timer.schedule(task, delay);
     * //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     * timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if (mDay < 0) {
                        // 倒计时结束
                        mDay = 0;
                        mHour = 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }

    @OnClick(R.id.login)
    void baoming() {
        startActivity(new Intent(getContext(), BaoMingXuanZeActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        timeHandler.removeCallbacksAndMessages(null);
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
