package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.WuLiuAdapter;
import com.watiao.yishuproject.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WuLiuXiangQingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    private ArrayList<String> data = new ArrayList<>();
    private WuLiuAdapter mWuLiuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data.add("【收货地址】北京市朝阳区 小美姐叨 大屯路细节22号院长");
        data.add("[已签收] [[沈阳市] [沈阳和平五部]的东北大学代理点正在派件 电话:18040xxxxxx 请保持电话畅通、耐心等待");
        data.add("[运输中] 快件离开 [杭州乔司区]已发往[沈阳]");
        data.add("[开始处理] [杭州乔司区]的市场部已收件 电话:18358xxxxxx");
        showData(data);
    }


    private void showData(ArrayList<String> data) {
        mWuLiuAdapter = new WuLiuAdapter(R.layout.item_wuliu, data);
        final LinearLayoutManager manager = new LinearLayoutManager(WuLiuXiangQingActivity.this);
        mRvRecycleview.setLayoutManager(manager);
        mRvRecycleview.setAdapter(mWuLiuAdapter);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_wu_liu_xiang_qing;
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
