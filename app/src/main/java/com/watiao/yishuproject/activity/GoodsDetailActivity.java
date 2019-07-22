package com.watiao.yishuproject.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.ShangPinXIangQingAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.CommentFragment;
import com.watiao.yishuproject.fragment.GoodsDetailFragment;
import com.watiao.yishuproject.utils.AppBarStateChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.movie_image)
    ImageView mMovieImage;
    @BindView(R.id.biaoti)
    TextView mBiaoti;
    @BindView(R.id.biaozhi)
    TextView mBiaozhi;
    @BindView(R.id.jiage)
    TextView mJiage;
    @BindView(R.id.yuanjia)
    TextView mYuanjia;
    @BindView(R.id.kucun)
    TextView mKucun;
    @BindView(R.id.shangpin_text)
    TextView mShangpinText;
    @BindView(R.id.shangpin_line)
    View mShangpinLine;
    @BindView(R.id.shangpin)
    RelativeLayout mShangpin;
    @BindView(R.id.pingjia_text)
    TextView mPingjiaText;
    @BindView(R.id.pingjia_line)
    View mPingjiaLine;
    @BindView(R.id.pingjia)
    RelativeLayout mPingjia;
    @BindView(R.id.daohang)
    RelativeLayout mDaohang;
    @BindView(R.id.xinxi)
    LinearLayout mXinxi;
    @BindView(R.id.shangpin_text2)
    TextView mShangpinText2;
    @BindView(R.id.shangpin2)
    RelativeLayout mShangpin2;
    @BindView(R.id.pingjia_text2)
    TextView mPingjiaText2;
    @BindView(R.id.pingjia2)
    RelativeLayout mPingjia2;
    @BindView(R.id.daohang2)
    LinearLayout mDaohang2;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setStatusbar();
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetUpViewPager(mViewpager);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mShangpinText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mPingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
                    mShangpinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mShangpinText2.setTextColor(getResources().getColor(R.color.color_w));
                    mPingjiaText2.setTextColor(getResources().getColor(R.color.textColor10));
                } else {
                    mAppbar.setExpanded(false);
                    mShangpinText.setTextColor(getResources().getColor(R.color.textColor6));
                    mPingjiaText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mShangpinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mShangpinText2.setTextColor(getResources().getColor(R.color.textColor10));
                    mPingjiaText2.setTextColor(getResources().getColor(R.color.color_w));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       mYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    mDaohang2.setVisibility(View.VISIBLE);
                } else {
                    mDaohang2.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int setLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void init() {

    }


    private void setStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //下面这一行呢是android4.0起推荐大家使用的将布局延伸到状态栏的代码，配合5.0的设置状态栏颜色可谓天作之合
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @OnClick(R.id.shangpin)
    void goods() {
        mShangpinText.setTextColor(getResources().getColor(R.color.app_text_color));
        mPingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
        mShangpinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mShangpinText2.setTextColor(getResources().getColor(R.color.color_w));
        mPingjiaText2.setTextColor(getResources().getColor(R.color.divider_line_d4));
        mViewpager.setCurrentItem(0);
    }


    @OnClick(R.id.pingjia)
    void comment() {
        mAppbar.setExpanded(false);
        mShangpinText.setTextColor(getResources().getColor(R.color.textColor6));
        mPingjiaText.setTextColor(getResources().getColor(R.color.app_text_color));
        mShangpinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mShangpinText2.setTextColor(getResources().getColor(R.color.divider_line_d4));
        mPingjiaText2.setTextColor(getResources().getColor(R.color.color_w));
        mViewpager.setCurrentItem(1);
    }


    @OnClick(R.id.shangpin2)
    void goods2() {
        mShangpinText.setTextColor(getResources().getColor(R.color.app_text_color));
        mPingjiaText.setTextColor(getResources().getColor(R.color.textColor10));
        mShangpinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mShangpinText2.setTextColor(getResources().getColor(R.color.color_w));
        mPingjiaText2.setTextColor(getResources().getColor(R.color.textColor10));
        mViewpager.setCurrentItem(0);
    }


    @OnClick(R.id.pingjia2)
    void comment2() {
        mShangpinText.setTextColor(getResources().getColor(R.color.textColor10));
        mPingjiaText.setTextColor(getResources().getColor(R.color.app_text_color));
        mShangpinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mPingjiaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mShangpinText2.setTextColor(getResources().getColor(R.color.textColor10));
        mPingjiaText2.setTextColor(getResources().getColor(R.color.color_w));
        mViewpager.setCurrentItem(1);
    }

    private void SetUpViewPager(ViewPager viewpager) {
        ShangPinXIangQingAdapter adapter = new ShangPinXIangQingAdapter(getSupportFragmentManager());
        adapter.addFragment(GoodsDetailFragment.newInstance(), "");
        adapter.addFragment(CommentFragment.newInstance(), "");
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0, true);
        viewpager.setOffscreenPageLimit(2);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
