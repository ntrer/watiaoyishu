package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.WoDeGuanZhuAdapter;
import com.watiao.yishuproject.base.BaseActivity;
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
import okhttp3.Call;

public class WoDeGuanZhuActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener,BaseSectionQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private WoDeGuanZhuAdapter mWoDeGuanZhuAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        getData();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_wo_de_guan_zhu;
    }

    @Override
    public void init() {

    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id="+BaseUrl.Token+"&loginOS=2&page=1&rows=10";
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
                mSrlRefresh.setRefreshing(false);
                if (response != null) {
                    try {
                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                        if (yiXiangJin.getRet().equals("200")) {
                            dataList = yiXiangJin.getDataList();
                            if (dataList.size() != 0) {
                                shouData(dataList);

                            } else {
                                shouData(dataList);

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
            mWoDeGuanZhuAdapter = new WoDeGuanZhuAdapter(R.layout.item_wofensi, data);
            final LinearLayoutManager manager = new LinearLayoutManager(WoDeGuanZhuActivity.this);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mWoDeGuanZhuAdapter);
            mWoDeGuanZhuAdapter.setOnLoadMoreListener(this,mRvRecycleview);
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }



    @Override
    public void onRefresh() {
    page=1;
    getData();
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


    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        page = page + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id="+BaseUrl.Token+"&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mWoDeGuanZhuAdapter.loadMoreComplete();
                    mWoDeGuanZhuAdapter.loadMoreEnd();
                }

                @Override
                public void onResponse(String response) {
                    Log.d("创建活动", response);
                    if (response != null) {
                        Log.d("nnnnnnn", response);
                        Test test = JSONUtil.fromJson(response, Test.class);
                        if (test.getRet().equals("200")) {
                            if (page > test.getIntmaxPage()) {
                                page = 1;
                                mWoDeGuanZhuAdapter.loadMoreComplete();
                                mWoDeGuanZhuAdapter.loadMoreEnd();
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mWoDeGuanZhuAdapter.loadMoreComplete();
                                mWoDeGuanZhuAdapter.loadMoreEnd();
                            }
                        } else {
                            mWoDeGuanZhuAdapter.loadMoreComplete();
                            mWoDeGuanZhuAdapter.loadMoreEnd();
                        }
                    }

                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void LoadMoreData(List<Test.DataListBean> data) {
        if (data.size() != 0) {
            mWoDeGuanZhuAdapter.addData(data);
            mWoDeGuanZhuAdapter.loadMoreComplete();
        }

    }
}
