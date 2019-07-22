package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingDanZhiFuActivity extends BaseActivity {

    private static final int REQUEST_DIZHI = 1013;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.dizhi)
    TextView mDizhi;
    @BindView(R.id.edit_text)
    TextView mEditText;
    @BindView(R.id.shouhuodizhi)
    RelativeLayout mShouhuodizhi;
    @BindView(R.id.dizhi_pic)
    ImageView mDizhiPic;
    @BindView(R.id.no_shouhuodizhi)
    RelativeLayout mNoShouhuodizhi;
    @BindView(R.id.goods_pic)
    RoundedImageView mGoodsPic;
    @BindView(R.id.good_name)
    TextView mGoodName;
    @BindView(R.id.jifen_danjia)
    TextView mJifenDanjia;
    @BindView(R.id.fuhao)
    TextView mFuhao;
    @BindView(R.id.yuanjia)
    TextView mYuanjia;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.goods)
    RelativeLayout mGoods;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.zonge)
    TextView mZonge;
    @BindView(R.id.zongjifen)
    TextView mZongjifen;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.jian)
    Button mJian;
    @BindView(R.id.jia)
    Button mJia;
    @BindView(R.id.count)
    EditText mCount;

    private int count = 1;
    private int price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        price=Integer.parseInt(mJifenDanjia.getText().toString());
        mCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if(mCount.isFocusable()){
                  if(!s.toString().equals("")&&Integer.parseInt(s.toString())>0){
                      mZongjifen.setText(Integer.parseInt(s.toString())*price+"");
                  }
              }
            }
        });

        mYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2975436939,2465391407&fm=26&gp=0.jpg").into(mGoodsPic);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ding_dan_zhi_fu;
    }


    @OnClick(R.id.jia)
    void jia() {
       count++;
        mCount.setText(count+"");
        mZongjifen.setText(price*count+"");
    }

    @OnClick(R.id.jian)
    void jian() {
        if (count > 1) {
            count--;
            mCount.setText(count+"");
            mZongjifen.setText(price*count+"");
        }
    }


    @OnClick(R.id.no_shouhuodizhi)
    void dizhi(){
        Intent intent=new Intent(DingDanZhiFuActivity.this,ShouHuoDiZhiActivity.class);
        startActivityForResult(intent,REQUEST_DIZHI);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_DIZHI){
            mNoShouhuodizhi.setVisibility(View.GONE);
            mShouhuodizhi.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.login)
    void zhifu() {
        Intent intent=new Intent(DingDanZhiFuActivity.this,ZhiFuChengGongActivity.class);
        startActivity(intent);
        finish();
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
