package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaoBeiXiangQingDianZanActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.zuobiao_img)
    ImageView mZuobiaoImg;
    @BindView(R.id.zuibiao_dizhi)
    TextView mZuibiaoDizhi;
    @BindView(R.id.year)
    TextView mYear;
    @BindView(R.id.shengao)
    TextView mShengao;
    @BindView(R.id.tizhong)
    TextView mTizhong;
    @BindView(R.id.user_headr)
    RelativeLayout mUserHeadr;
    @BindView(R.id.piaoshu)
    TextView mPiaoshu;
    @BindView(R.id.title)
    LinearLayout mTitle;
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
        return R.layout.activity_bao_bei_xiang_qing;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.user_headr)
    void header(){
        startActivity(new Intent(BaoBeiXiangQingDianZanActivity.this,MengWaZhuYeActivity.class));
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
