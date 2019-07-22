package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.PaiHangAdapter;
import com.watiao.yishuproject.base.BaseActivity4;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class PaiHangBangActivity extends BaseActivity4 {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tips)
    TextView mTips;
    @BindView(R.id.rv_paihang)
    RecyclerView mRvPaihang;


    CircleImageView mUserPic2;
    TextView mName2;
    TextView mDefen2;
    CircleImageView mDier;
    CircleImageView mUserPic;
    TextView mName;
    TextView mDefen;
    CircleImageView mDiyi;
    CircleImageView mUserPic3;
    TextView mName3;
    TextView mDefen3;
    CircleImageView mDisan;
    LinearLayout mQiansan;


    private PaiHangAdapter mPaiHangAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private View header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getHeader();
        getData();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_pai_hang_bang;
    }

    @Override
    public void init() {

    }



    private void getHeader() {
        header = View.inflate(PaiHangBangActivity.this, R.layout.paihang_header, null);
        mUserPic=header.findViewById(R.id.user_pic);
        mUserPic2=header.findViewById(R.id.user_pic2);
        mUserPic3=header.findViewById(R.id.user_pic3);
        mDiyi=header.findViewById(R.id.diyi);
        mDier=header.findViewById(R.id.dier);
        mDisan=header.findViewById(R.id.disan);
        mName=header.findViewById(R.id.name);
        mName2=header.findViewById(R.id.name2);
        mName3=header.findViewById(R.id.name3);
        mDefen=header.findViewById(R.id.defen);
        mDefen2=header.findViewById(R.id.defen2);
        mDefen3=header.findViewById(R.id.defen3);
    }


    private void getData() {
        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id="+BaseUrl.Token+"&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
        Log.d("创建活动", url);
        HashMap<String, String> paramsMap = new HashMap<>();
        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                ToastUtils.showLong(e.toString());
            }

            @Override
            public void onResponse(String response) {
                Log.d("创建活动", response);
                if (response != null) {
                    try {
                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                        if (yiXiangJin.getRet().equals("200")) {
                            dataList = yiXiangJin.getDataList();
                            if (dataList.size() != 0) {
                                shouData(dataList);
                                mTips.setVisibility(View.VISIBLE);
                            } else {
                                shouData(dataList);
                                mTips.setVisibility(View.GONE);
                            }
                        }
                    } catch (Exception e) {
                        Log.d("出错了", e.toString());
                    }
                }

            }
        });
    }


    private void shouData(final List<Test.DataListBean> data) {
        try {
            mPaiHangAdapter = new PaiHangAdapter(R.layout.item_paihang, data);
            final LinearLayoutManager manager = new LinearLayoutManager(PaiHangBangActivity.this);
            mRvPaihang.setLayoutManager(manager);
            mRvPaihang.setAdapter(mPaiHangAdapter);
            mPaiHangAdapter.addHeaderView(header);
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
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
