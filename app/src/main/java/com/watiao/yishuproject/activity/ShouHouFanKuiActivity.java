package com.watiao.yishuproject.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouHouFanKuiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.zhuangtai)
    TextView mZhuangtai;
    @BindView(R.id.zhuangtai_pic)
    ImageView mZhuangtaiPic;
    @BindView(R.id.header)
    RelativeLayout mHeader;
    @BindView(R.id.dingdan_pic)
    RoundedImageView mDingdanPic;
    @BindView(R.id.dingdan_name)
    TextView mDingdanName;
    @BindView(R.id.biaozhi)
    TextView mBiaozhi;
    @BindView(R.id.danjia)
    TextView mDanjia;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.jifen_biaozhi)
    TextView mJifenBiaozhi;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.dingdan_content)
    RelativeLayout mDingdanContent;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.totalcount)
    TextView mTotalcount;
    @BindView(R.id.heji)
    TextView mHeji;
    @BindView(R.id.zongjia)
    TextView mZongjia;
    @BindView(R.id.jiesuan)
    LinearLayout mJiesuan;
    @BindView(R.id.totalcount2)
    TextView mTotalcount2;
    @BindView(R.id.heji2)
    TextView mHeji2;
    @BindView(R.id.zongjifen)
    TextView mZongjifen;
    @BindView(R.id.jifenbiao)
    TextView mJifenbiao;
    @BindView(R.id.jiesuan2)
    LinearLayout mJiesuan2;
    @BindView(R.id.shouhou)
    Button mShouhou;
    @BindView(R.id.pingjia)
    Button mPingjia;
    @BindView(R.id.shangpin)
    RelativeLayout mShangpin;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.zhifufangshi)
    TextView mZhifufangshi;
    @BindView(R.id.zhifufangshi_text)
    TextView mZhifufangshiText;
    @BindView(R.id.dingdanbianhao)
    TextView mDingdanbianhao;
    @BindView(R.id.dingdanbianhao_text)
    TextView mDingdanbianhaoText;
    @BindView(R.id.fukuanshijian)
    TextView mFukuanshijian;
    @BindView(R.id.fukuanshijian_text)
    TextView mFukuanshijianText;
    @BindView(R.id.fahuoshijian)
    TextView mFahuoshijian;
    @BindView(R.id.fahuoshijian_text)
    TextView mFahuoshijianText;
    @BindView(R.id.fuzhi)
    Button mFuzhi;
    @BindView(R.id.dingdanxinxi)
    RelativeLayout mDingdanxinxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2975436939,2465391407&fm=26&gp=0.jpg").placeholder(R.mipmap.remensaishi_bg).into(mDingdanPic);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shou_hou_fan_kui;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.pingjia)
    void pingjia(){
        Intent intent=new Intent(ShouHouFanKuiActivity.this,PingJiaActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.fuzhi)
    void fuzhi(){
        ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(mZhifufangshi.getText().toString().trim()+mZhifufangshiText.getText().toString().trim()+"\n"
                +mDingdanbianhao.getText().toString().trim()+mDingdanbianhaoText.getText().toString().trim()+"\n"
                 +mFukuanshijian.getText().toString().trim()+mFukuanshijianText.getText().toString().trim()+"\n"
                 +mFahuoshijian.getText().toString().trim()+mFukuanshijianText.getText().toString().trim());
        com.blankj.utilcode.util.ToastUtils.showLong("已复制到粘贴板");
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
