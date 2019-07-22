package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.content)
    TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContent.setText("\u3000\u3000"+"天使动漫论坛成立于2010年10月1日。 是一个以天使类动漫为题材的综合型动漫论坛。提供经典动画下载，新番下载和在线观看、讨论，动漫、同人音乐欣赏，系统萌化资源，原创作品展示平台，翻唱交流，同人主题交流（V家、东方等）等内容。");
    }

    @Override
    public int setLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void init() {

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
