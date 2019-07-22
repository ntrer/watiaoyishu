package com.watiao.yishuproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity3;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BangDingShouJIHaoActivity extends BaseActivity3 {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.bangding)
    TextView mBangding;
    @BindView(R.id.txt_phone)
    AppCompatEditText mTxtPhone;
    @BindView(R.id.txt_yzm)
    AppCompatEditText mTxtYzm;
    @BindView(R.id.yzm)
    RelativeLayout mYzm;
    @BindView(R.id.login)
    Button mLogin;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTxtYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!mTxtPhone.getText().toString().equals("")&&!mTxtYzm.getText().toString().equals("")&&mTxtYzm.getText().toString().length()==6){
                    mLogin.setBackground(getResources().getDrawable(R.drawable.btn_border2));
                    mLogin.setClickable(true);
                }            }
        });

        mActivity=this;

    }





    @Override
    public int setLayout() {
        return R.layout.activity_bang_ding_shou_jihao;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.login)
    void login(){
        startActivity(new Intent(BangDingShouJIHaoActivity.this,TianXieZiliaoActivity.class));
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
