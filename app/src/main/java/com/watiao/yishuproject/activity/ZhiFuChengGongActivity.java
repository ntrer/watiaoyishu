package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhiFuChengGongActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.zhifuchenggong)
    LinearLayout mZhifuchenggong;
    @BindView(R.id.dingdanhao)
    TextView mDingdanhao;
    @BindView(R.id.tips)
    TextView mTips;
    @BindView(R.id.login)
    Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_zhi_fu_cheng_gong;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.login)
    void dingdan() {
        Intent intent=new Intent(ZhiFuChengGongActivity.this,DingDanDetailActivity.class);
        intent.putExtra("leixing","待发货");
        startActivity(intent);
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
