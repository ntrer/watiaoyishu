package com.watiao.yishuproject.fragment.WoDeSaiShiXiangQingFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.ZhuBanFangActivity;
import com.watiao.yishuproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class WoDeXiangQingFragment extends BaseFragment {


    Unbinder unbinder;
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
    Unbinder unbinder1;

    public WoDeXiangQingFragment() {
        // Required empty public constructor
    }

    public static WoDeXiangQingFragment newInstance() {
        return new WoDeXiangQingFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        inidWebView();
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
        View view = View.inflate(mContext, R.layout.fragment_xiangqing2, null);
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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
