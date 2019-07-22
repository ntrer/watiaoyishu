package com.watiao.yishuproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.watiao.yishuproject.MainActivity;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity3;
import com.watiao.yishuproject.ui.SLEditTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TianXieZiliaoActivity extends BaseActivity3 {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bangding)
    TextView mBangding;
    @BindView(R.id.txt_phone)
    SLEditTextView mTxtPhone;
    @BindView(R.id.tips)
    TextView mTips;
    @BindView(R.id.login)
    Button mLogin;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity=this;
    }




    @Override
    public int setLayout() {
        return R.layout.activity_tian_xie_ziliao;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.login)
    void login(){
        Intent intent=new Intent(TianXieZiliaoActivity.this,MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
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
