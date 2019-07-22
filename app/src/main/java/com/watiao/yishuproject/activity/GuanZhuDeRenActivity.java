package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.GuanZhuAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;
import com.watiao.yishuproject.utils.PreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class GuanZhuDeRenActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @BindView(R.id.txt_souuo)
    AppCompatEditText mTxtSouuo;
    @BindView(R.id.search_title)
    RelativeLayout mSearchTitle;
    @BindView(R.id.title)
    TextView mTitle;
    private List<Test.DataListBean> customerList = new ArrayList<>();
    private GuanZhuAdapter mGuanZhuAdapter;
    private int page = 1;
    private boolean isSearch=false;
    private String searchKey="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        isSearch=false;
        getData();
        mTxtSouuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard(GuanZhuDeRenActivity.this);
                    isSearch=true;
                    searchKey=mTxtSouuo.getText().toString();
                    getData();
                    mTitle.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        mTxtSouuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if(s.toString().equals("")){
                 isSearch=false;
                 mTitle.setVisibility(View.VISIBLE);
                 getData();
             }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_guan_zhu;
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
                            customerList =  yiXiangJin.getDataList();
                            if (customerList.size() != 0) {
                                shouData(customerList);
                            } else {
                                if(isSearch){
                                    mTitle.setVisibility(View.VISIBLE);
                                    mTitle.setText("没有搜索到此人");
                                }
                                else {
                                    mTitle.setVisibility(View.VISIBLE);
                                    mTitle.setText("我关注的人");
                                }
                                shouData(customerList);
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
            mGuanZhuAdapter = new GuanZhuAdapter(R.layout.item_guanzhu, data,searchKey);
            final LinearLayoutManager manager = new LinearLayoutManager(GuanZhuDeRenActivity.this);
            mGuanZhuAdapter.setOnLoadMoreListener(this, mRvRecycleview);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mGuanZhuAdapter);
            mGuanZhuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    PreferencesUtils.putString(GuanZhuDeRenActivity.this,"name",data.get(position).getChuangjianrenName());
                    finish();
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }


    private void loadMore() {
        try {
            page = page + 1;
            String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id=ad74ad9c729f4463a7fb347934a75b68&loginOS=2&page=2&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mGuanZhuAdapter.loadMoreComplete();
                    mGuanZhuAdapter.loadMoreEnd();
                }

                @Override
                public void onResponse(String response) {
                    Log.d("创建活动", response);
                    if (response != null) {
                        Log.d("nnnnnnn", response);
                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                        if (yiXiangJin.getRet().equals("200")) {
                            if (page > yiXiangJin.getIntmaxPage()) {
                                page = 1;
                                mGuanZhuAdapter.loadMoreComplete();
                                mGuanZhuAdapter.loadMoreEnd();
                            } else if (yiXiangJin.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = yiXiangJin.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                                mGuanZhuAdapter.loadMoreComplete();
                            } else if (yiXiangJin.getDataList().size() == 0) {
                                mGuanZhuAdapter.loadMoreComplete();
                                mGuanZhuAdapter.loadMoreEnd();
                            }
                        } else {
                            mGuanZhuAdapter.loadMoreComplete();
                            mGuanZhuAdapter.loadMoreEnd();
                        }
                    }

                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void LoadMoreData(List<Test.DataListBean> dataList) {
        if (dataList.size() != 0) {
            mGuanZhuAdapter.addData(dataList);
            customerList.addAll(dataList);
            mGuanZhuAdapter.loadMoreComplete();
        }

    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideSoftKeyboard(GuanZhuDeRenActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
