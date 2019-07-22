package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.WodeSaiShiAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.WoDeSaiShiFragment.JinXingZhongFragment;
import com.watiao.yishuproject.fragment.WoDeSaiShiFragment.YiJieShuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WodeSaiShiActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.jinxingzhong_text)
    TextView mJinxingzhongText;
    @BindView(R.id.jinxingzhong_line)
    View mJinxingzhongLine;
    @BindView(R.id.jinxingzhong)
    RelativeLayout mJinxingzhong;
    @BindView(R.id.yijisuhu_text)
    TextView mYijisuhuText;
    @BindView(R.id.yijisuhu_line)
    View mYijisuhuLine;
    @BindView(R.id.yijisuhu)
    RelativeLayout mYijisuhu;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

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
                    mJinxingzhongText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mYijisuhuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mJinxingzhongLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mYijisuhuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                } else if (position == 1) {
                    mJinxingzhongText.setTextColor(getResources().getColor(R.color.textColor6));
                    mYijisuhuText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mJinxingzhongLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mYijisuhuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_wode_sai_shi;
    }

    @Override
    public void init() {

    }



    private void SetUpViewPager(ViewPager bookViewpager) {
        WodeSaiShiAdapter adapter = new WodeSaiShiAdapter(getSupportFragmentManager());
        adapter.addFragment(JinXingZhongFragment.newInstance(), "消费记录");
        adapter.addFragment(YiJieShuFragment.newInstance(), "充值记录");
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
