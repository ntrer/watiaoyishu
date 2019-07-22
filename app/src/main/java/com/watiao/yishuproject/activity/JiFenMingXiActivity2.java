package com.watiao.yishuproject.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.JiFenAdapter;
import com.watiao.yishuproject.adapter.JiFenMingXiAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.fragment.JiFenMingXiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JiFenMingXiActivity2 extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.keyongjifen)
    TextView mKeyongjifen;
    @BindView(R.id.huoqumingxi)
    TextView mHuoqumingxi;
    @BindView(R.id.duihuanjilu)
    TextView mDuihuanjilu;
    @BindView(R.id.header)
    RelativeLayout mHeader;

    @BindView(R.id.nsesv)
    NestedScrollView mNsesv;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;


    private View statusBarView;
    private JiFenAdapter mJiFenAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Test.DataListBean> dataList2 = new ArrayList<>();
    private boolean isFirst = true;
    private int dataType = 1;
    private int page = 1;
    private int page2 = 1;
    private boolean isLoadMore1 = true;
    private boolean isLoadMore2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isStatusBar()) {
            initStatusBar();
            getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    initStatusBar();
                }
            });
        }
        ButterKnife.bind(this);
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
                    mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border8));
                    mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                    mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border9));
                    mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                } else {
                    mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border11));
                    mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                    mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border10));
                    mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected boolean isStatusBar() {
        return true;
    }


    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.bg_color3);
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ji_fen_ming_xi2;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.huoqumingxi)
    void huoqumingxi() {
        dataType = 1;
        mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border8));
        mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border9));
        mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        mViewpager.setCurrentItem(0);
    }


    @OnClick(R.id.duihuanjilu)
    void duihuanjilu() {
        dataType = 2;
        mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border11));
        mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border10));
        mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        mViewpager.setCurrentItem(1);
    }

    private void SetUpViewPager(ViewPager bookViewpager) {
        JiFenMingXiAdapter adapter = new JiFenMingXiAdapter(getSupportFragmentManager());
        adapter.addFragment(JiFenMingXiFragment.newInstance("获取明细"), "获取明细");
        adapter.addFragment(JiFenMingXiFragment.newInstance("兑换记录"), "兑换记录");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
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
