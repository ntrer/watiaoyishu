package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.WodeDingDanAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.WoDeDingDanFragment.QuanBuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WoDeDingDanActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.quanbu_text)
    TextView mQuanbuText;
    @BindView(R.id.quanbu_line)
    View mQuanbuLine;
    @BindView(R.id.quanbu)
    RelativeLayout mQuanbu;
    @BindView(R.id.daifahuo_text)
    TextView mDaifahuoText;
    @BindView(R.id.daifahuo_line)
    View mDaifahuoLine;
    @BindView(R.id.daifahuo)
    RelativeLayout mDaifahuo;
    @BindView(R.id.daishouhuo_text)
    TextView mDaishouhuoText;
    @BindView(R.id.daishouhuo_line)
    View mDaishouhuoLine;
    @BindView(R.id.daishouhuo)
    RelativeLayout mDaishouhuo;
    @BindView(R.id.daipingjia_text)
    TextView mDaipingjiaText;
    @BindView(R.id.daipingjia_line)
    View mDaipingjiaLine;
    @BindView(R.id.daipingjia)
    RelativeLayout mDaipingjia;
    @BindView(R.id.fenlei)
    RelativeLayout mFenlei;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.shouhou)
    TextView mShouhou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    mQuanbuText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
                    mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                } else if (position == 1) {
                    mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaifahuoText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
                    mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                } else if (position == 2) {
                    mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaishouhuoText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
                    mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                } else if (position == 3) {
                    mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
                    mDaipingjiaText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @OnClick(R.id.quanbu)
    void quanbu() {
        mViewpager.setCurrentItem(0);
        mQuanbuText.setTextColor(getResources().getColor(R.color.app_text_color));
        mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
        mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
    }

    @OnClick(R.id.daifahuo)
    void daifahuo() {
        mViewpager.setCurrentItem(1);
        mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaifahuoText.setTextColor(getResources().getColor(R.color.app_text_color));
        mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
        mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
    }

    @OnClick(R.id.daishouhuo)
    void daishouhuo() {
        mViewpager.setCurrentItem(2);
        mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaishouhuoText.setTextColor(getResources().getColor(R.color.app_text_color));
        mDaipingjiaText.setTextColor(getResources().getColor(R.color.textColor6));
        mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
    }

    @OnClick(R.id.daipingjia)
    void daipingjia() {
        mViewpager.setCurrentItem(3);
        mQuanbuText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaifahuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaishouhuoText.setTextColor(getResources().getColor(R.color.textColor6));
        mDaipingjiaText.setTextColor(getResources().getColor(R.color.app_text_color));
        mQuanbuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaifahuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaishouhuoLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mDaipingjiaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
    }


    @Override
    public int setLayout() {
        return R.layout.activity_wo_de_ding_dan;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.shouhou)
    void shouhou(){
     startActivity(new Intent(WoDeDingDanActivity.this,ShouHouActivity.class));
    }



    private void SetUpViewPager(ViewPager bookViewpager) {
        WodeDingDanAdapter adapter = new WodeDingDanAdapter(getSupportFragmentManager());
        adapter.addFragment(QuanBuFragment.newInstance("全部"), "");
        adapter.addFragment(QuanBuFragment.newInstance("待发货"), "");
        adapter.addFragment(QuanBuFragment.newInstance("待收货"), "");
        adapter.addFragment(QuanBuFragment.newInstance("待评价"), "");
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
