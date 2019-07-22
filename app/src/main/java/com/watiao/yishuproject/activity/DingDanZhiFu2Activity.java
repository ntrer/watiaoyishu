package com.watiao.yishuproject.activity;

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

import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.library.SmoothCheckBox;

public class DingDanZhiFu2Activity extends BaseActivity {

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
    @BindView(R.id.fuhao)
    TextView mFuhao;
    @BindView(R.id.jifen_danjia)
    TextView mJifenDanjia;
    @BindView(R.id.yuanjia)
    TextView mYuanjia;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.goods)
    RelativeLayout mGoods;
    @BindView(R.id.weixin_pic)
    ImageView mWeixinPic;
    @BindView(R.id.checkbox1)
    SmoothCheckBox mCheckbox1;
    @BindView(R.id.weixin)
    RelativeLayout mWeixin;
    @BindView(R.id.zhifubao_pic)
    ImageView mZhifubaoPic;
    @BindView(R.id.checkbox2)
    SmoothCheckBox mCheckbox2;
    @BindView(R.id.zhifubao)
    RelativeLayout mZhifubao;
    @BindView(R.id.zonge)
    TextView mZonge;
    @BindView(R.id.jine)
    TextView mJine;
    @BindView(R.id.zongjifen)
    TextView mZongjifen;
    @BindView(R.id.login)
    Button mLogin;
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
        price = Integer.parseInt(mJifenDanjia.getText().toString());
        mCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCount.isFocusable()) {
                    if (!s.toString().equals("") && Integer.parseInt(s.toString()) > 0) {
                        mZongjifen.setText(Integer.parseInt(s.toString()) * price + "");
                    }
                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ding_dan_zhi_fu2;
    }

    @Override
    public void init() {

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
