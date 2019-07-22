package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.DiZhiAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.KeHu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouHuoDiZhiActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener{

    private static final int RESULT_CODE_BIANJIJI_DIZHI = 1001;
    private static final int RESULT_CODE_TIANJIA_DIZHI = 1002;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private List<KeHu.DataBean.CustomerListBean> customerList = new ArrayList<>();
    private List<String> data = new ArrayList<>();
    private DiZhiAdapter mDiZhiAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        data.add("diyi");
        data.add("dier");
//        getData();
        shouData(data);
    }


//    private void getData() {
//        mSrlRefresh.setRefreshing(true);
//        String url = "http://192.168.0.55:8879/user/customer/list?token_id=7929fd386d7a4aa6a86321ec639d257b&loginOS=1&page=1&rows=10";
//        Log.d("创建活动", url);
//        HashMap<String, String> paramsMap = new HashMap<>();
//        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//                ToastUtils.showLong(e.toString());
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("创建活动", response);
//                mSrlRefresh.setRefreshing(false);
//                if (response != null) {
//                    try {
//                        KeHu yiXiangJin = JSONUtil.fromJson(response, KeHu.class);
//                        if (yiXiangJin.getRet().equals("200")) {
//                            customerList = yiXiangJin.getData().getCustomerList();
//                            if (customerList.size() != 0) {
//                                shouData(customerList);
//
//                            } else {
//                                shouData(customerList);
//
//                            }
//                        }
//                    } catch (Exception e) {
//                        Log.d("出错了", e.toString());
//                    }
//                }
//
//            }
//        });
//    }


    private void shouData(final List<String> data) {
        try {
            mDiZhiAdapter = new DiZhiAdapter(R.layout.item_dizhi, data);
            final LinearLayoutManager manager = new LinearLayoutManager(ShouHuoDiZhiActivity.this);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mDiZhiAdapter);
            mDiZhiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(ShouHuoDiZhiActivity.this,TianJiaShouHuoDiZhiActivity.class);
                    intent.putExtra("bianji","bianji");
                    startActivityForResult(intent,RESULT_CODE_BIANJIJI_DIZHI);
                }
            });

            mDiZhiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    finish();
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.login)
    void NewDiZhi(){
        Intent intent=new Intent(ShouHuoDiZhiActivity.this,TianJiaShouHuoDiZhiActivity.class);
        intent.putExtra("tianjia","tianjia");
        startActivityForResult(intent,RESULT_CODE_TIANJIA_DIZHI);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public int setLayout() {
        return R.layout.activity_shou_huo_di_zhi;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRefresh() {
//      getData();
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
