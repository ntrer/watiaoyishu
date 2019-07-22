package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.JiFenRenWuAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.Test;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JiFenRenWuActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.firstline)
    LinearLayout mFirstline;
    @BindView(R.id.jiangli1)
    TextView mJiangli1;
    @BindView(R.id.secondline)
    LinearLayout mSecondline;
    @BindView(R.id.qiandao)
    Button mQiandao;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.renwu1)
    LinearLayout mRenwu1;
    @BindView(R.id.avatar2)
    ImageView mAvatar2;
    @BindView(R.id.firstline2)
    LinearLayout mFirstline2;
    @BindView(R.id.jiangli2)
    TextView mJiangli2;
    @BindView(R.id.secondline2)
    LinearLayout mSecondline2;
    @BindView(R.id.fabushipin)
    Button mFabushipin;
    @BindView(R.id.renwu2)
    LinearLayout mRenwu2;
    @BindView(R.id.avatar3)
    ImageView mAvatar3;
    @BindView(R.id.firstline3)
    LinearLayout mFirstline3;
    @BindView(R.id.jiangli3)
    TextView mJiangli3;
    @BindView(R.id.secondline3)
    LinearLayout mSecondline3;
    @BindView(R.id.fenxiang)
    Button mFenxiang;
    @BindView(R.id.renwu3)
    LinearLayout mRenwu3;
    @BindView(R.id.avatar4)
    ImageView mAvatar4;
    @BindView(R.id.firstline4)
    LinearLayout mFirstline4;
    @BindView(R.id.jiangli4)
    TextView mJiangli4;
    @BindView(R.id.secondline4)
    LinearLayout mSecondline4;
    @BindView(R.id.fenxiangsaishi)
    Button mFenxiangsaishi;
    @BindView(R.id.renwu4)
    LinearLayout mRenwu4;
    @BindView(R.id.avatar5)
    ImageView mAvatar5;
    @BindView(R.id.firstline5)
    LinearLayout mFirstline5;
    @BindView(R.id.jiangli5)
    TextView mJiangli5;
    @BindView(R.id.secondline5)
    LinearLayout mSecondline5;
    @BindView(R.id.liulanshipin)
    Button mLiulanshipin;
    @BindView(R.id.renwu5)
    LinearLayout mRenwu5;
    @BindView(R.id.avatar6)
    ImageView mAvatar6;
    @BindView(R.id.firstline6)
    LinearLayout mFirstline6;
    @BindView(R.id.jiangli6)
    TextView mJiangli6;
    @BindView(R.id.secondline6)
    LinearLayout mSecondline6;
    @BindView(R.id.goumaishangpin)
    Button mGoumaishangpin;
    @BindView(R.id.renwu6)
    LinearLayout mRenwu6;
    private JiFenRenWuAdapter mJiFenRenWuAdapter;
    private List<Test.DataListBean> customerList = new ArrayList<>();

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
        return R.layout.activity_ji_fen_ren_wu;
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
