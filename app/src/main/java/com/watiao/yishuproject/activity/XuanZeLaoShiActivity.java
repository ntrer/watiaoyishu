package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.watiao.yishuproject.adapter.LaoShiSouSuoAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;
import com.watiao.yishuproject.utils.RecyclerViewAnimUtil.RecyclerViewAnimUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class XuanZeLaoShiActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.txt_souuo)
    AppCompatEditText mTxtSouuo;
    @BindView(R.id.search_title)
    RelativeLayout mSearchTitle;
    @BindView(R.id.cancle)
    TextView mCancle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sousuojieguo)
    TextView mSousuojieguo;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Integer> position2 = new ArrayList<>();
    private List<Test.DataListBean> selectDatas = new ArrayList<>();
    private LaoShiSouSuoAdapter mLaoShiSouSuoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        mTxtSouuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    hideSoftKeyboard(XuanZeLaoShiActivity.this);
                    getData();
                    return true;
                }
                return false;
            }
        });
    }


    @OnClick(R.id.cancle)
    void cancle(){
       finish();
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id="+BaseUrl.Token+"&loginOS=2&page=1&rows=10";
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
            mLaoShiSouSuoAdapter = new LaoShiSouSuoAdapter(R.layout.item_laoshi, data);
            final LinearLayoutManager manager = new LinearLayoutManager(XuanZeLaoShiActivity.this);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mLaoShiSouSuoAdapter);
            RecyclerViewAnimUtil.getInstance().closeDefaultAnimator(mRvRecycleview);
            mLaoShiSouSuoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    position2.add(position);
                    if (!mLaoShiSouSuoAdapter.isSelected.get(position)) {
                        mLaoShiSouSuoAdapter.isSelected.put(position, true); // 修改map的值保存状态
                        mLaoShiSouSuoAdapter.notifyItemChanged(position);
                        selectDatas.add(data.get(position));
                    } else {
                        mLaoShiSouSuoAdapter.isSelected.put(position, false); // 修改map的值保存状态
                        mLaoShiSouSuoAdapter.notifyItemChanged(position);
                        selectDatas.remove(data.get(position));
                    }
                    Intent intent=new Intent();
                    intent.putExtra("laoshi",data.get(position).getChuangjianrenName());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

            mLaoShiSouSuoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.checkbox) {
                        if (!mLaoShiSouSuoAdapter.isSelected.get(position)) {
                            mLaoShiSouSuoAdapter.isSelected.put(position, true); // 修改map的值保存状态
                            mLaoShiSouSuoAdapter.notifyItemChanged(position);
                            selectDatas.add(data.get(position));

                        } else {
                            mLaoShiSouSuoAdapter.isSelected.put(position, false); // 修改map的值保存状态
                            mLaoShiSouSuoAdapter.notifyItemChanged(position);
                            selectDatas.remove(data.get(position));
                        }
                        Intent intent=new Intent();
                        intent.putExtra("laoshi",data.get(position).getChuangjianrenName());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @Override
    public int setLayout() {
        return R.layout.activity_xuan_ze_lao_shi;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRefresh() {
        getData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideSoftKeyboard(XuanZeLaoShiActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
