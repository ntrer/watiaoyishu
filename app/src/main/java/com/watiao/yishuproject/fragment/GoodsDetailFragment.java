package com.watiao.yishuproject.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.DingDanZhiFuActivity;
import com.watiao.yishuproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GoodsDetailFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.wb_content)
    WebView mWebView;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.bottom)
    RelativeLayout mBottom;
    @BindView(R.id.logo)
    ImageView mLogo;
    Unbinder unbinder1;


    public GoodsDetailFragment() {
        // Required empty public constructor
    }

    public static GoodsDetailFragment newInstance() {
        return new GoodsDetailFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        inidWebView();

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_good_detail, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void inidWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(false); //选择内置缩放机制与单独缩放控件；
        settings.setDisplayZoomControls(false); //是否显示缩放控件
        settings.setSupportZoom(false);  //是否支持缩放
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.setInitialScale(145); //第一次显示的缩放比例，例子是120%;
        mWebView.getSettings().setJavaScriptEnabled(true);//启用js
        mWebView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
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

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                   mLogo.setVisibility(View.VISIBLE);
                   mBottom.setVisibility(View.VISIBLE);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl("https://a.app.qq.com/o/simple.jsp?pkgname=tv.acfundanmaku.video&channel=0002160650432d595942&fromcase=60001");
    }

    @OnClick(R.id.login)
    void buy() {
        Intent intent = new Intent(getContext(), DingDanZhiFuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
