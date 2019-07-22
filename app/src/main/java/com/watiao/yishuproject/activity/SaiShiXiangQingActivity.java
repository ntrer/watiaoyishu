package com.watiao.yishuproject.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codingending.popuplayout.PopupLayout;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.SaiShiXiangQingAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.SaiShiXiangQingFragment.XiangQingFragment;
import com.watiao.yishuproject.fragment.SaiShiXiangQingFragment.XiangQingShiPinFragment;
import com.watiao.yishuproject.utils.AppBarStateChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaiShiXiangQingActivity extends BaseActivity {


    @BindView(R.id.movie_image)
    ImageView mMovieImage;
    @BindView(R.id.biaoti)
    TextView mBiaoti;
    @BindView(R.id.money)
    TextView mMoney;
    @BindView(R.id.biaozhi)
    TextView mBiaozhi;
    @BindView(R.id.chengshi)
    TextView mChengshi;
    @BindView(R.id.xinxi)
    LinearLayout mXinxi;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.saishixiangqing_text)
    TextView mSaishixiangqingText;
    @BindView(R.id.saishixiangqing_line)
    View mSaishixiangqingLine;
    @BindView(R.id.saishixiangqing)
    RelativeLayout mSaishixiangqing;
    @BindView(R.id.cansaishipin_text)
    TextView mCansaishipinText;
    @BindView(R.id.cansaishipin_line)
    View mCansaishipinLine;
    @BindView(R.id.cansaishipin)
    RelativeLayout mCansaishipin;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.daohang)
    RelativeLayout mDaohang;
    @BindView(R.id.share)
    ImageView mShare;
    private PopupLayout mPopupLayout;
    private View bottomMenu;
    private boolean useRadius = true;//是否使用圆角特性
    private TextView mcancle;

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
        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    mXinxi.setAlpha(1);
                    mMovieImage.setAlpha(1.0f);

                } else if (state == State.COLLAPSED) {
                    mXinxi.setAlpha(0);
                    mMovieImage.setAlpha(0f);
                } else {
                    //中间状态
                    mXinxi.setAlpha(1);
                    mMovieImage.setAlpha(1.0f);
                }
            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mSaishixiangqingText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mCansaishipinText.setTextColor(getResources().getColor(R.color.textColor6));
                    mSaishixiangqingLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mCansaishipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaohang.setBackground(getResources().getDrawable(R.drawable.layout_border18));
                } else if (position == 1) {
                    mSaishixiangqingText.setTextColor(getResources().getColor(R.color.textColor6));
                    mCansaishipinText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mSaishixiangqingLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mCansaishipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mDaohang.setBackground(getResources().getDrawable(R.drawable.layout_border17));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomMenu = View.inflate(SaiShiXiangQingActivity.this, R.layout.bottom_layout, null);
        mPopupLayout = PopupLayout.init(SaiShiXiangQingActivity.this, bottomMenu);
        mPopupLayout.setUseRadius(useRadius);
        mcancle = bottomMenu.findViewById(R.id.cancle);
        mcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupLayout.dismiss();
            }
        });

    }


    private void SetUpViewPager(ViewPager bookViewpager) {
        SaiShiXiangQingAdapter adapter = new SaiShiXiangQingAdapter(getSupportFragmentManager());
        adapter.addFragment(XiangQingFragment.newInstance(), "消费记录");
        adapter.addFragment(XiangQingShiPinFragment.newInstance(), "充值记录");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
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

    @Override
    public int setLayout() {
        return R.layout.activity_sai_shi_xiang_qing;
    }


    @Override
    public void init() {

    }

    @OnClick(R.id.share)
    void share() {
        mPopupLayout.show(PopupLayout.POSITION_BOTTOM);
    }

    @OnClick(R.id.saishixiangqing)
    void saishixiangqing() {
        mSaishixiangqingText.setTextColor(getResources().getColor(R.color.app_text_color));
        mCansaishipinText.setTextColor(getResources().getColor(R.color.textColor6));
        mSaishixiangqingLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mCansaishipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mViewpager.setCurrentItem(0);
        mDaohang.setBackground(getResources().getDrawable(R.drawable.layout_border18));
    }


    @OnClick(R.id.cansaishipin)
    void cansaishipin() {
        mSaishixiangqingText.setTextColor(getResources().getColor(R.color.textColor6));
        mCansaishipinText.setTextColor(getResources().getColor(R.color.app_text_color));
        mSaishixiangqingLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mCansaishipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mViewpager.setCurrentItem(1);
        mDaohang.setBackground(getResources().getDrawable(R.drawable.layout_border17));
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
