package com.watiao.yishuproject.fragment.ShangChengFenLeiFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.GoodsDetailActivity;
import com.watiao.yishuproject.adapter.ReMenShangPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class ShangChengFenLeiFragment extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener,BaseSectionQuickAdapter.RequestLoadMoreListener{


    Unbinder unbinder;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder1;
    private String leixing;
    private ReMenShangPinAdapter mReMenShangPinAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private boolean isFirst = true;
    private int page = 1;
    public ShangChengFenLeiFragment() {
        // Required empty public constructor
    }

    public static ShangChengFenLeiFragment newInstance(String fenlei) {
        ShangChengFenLeiFragment fragment = new ShangChengFenLeiFragment();
        Bundle args = new Bundle();
        args.putString("fenlei", fenlei);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        leixing = getArguments().getString("fenlei");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shangcheng_fenlei, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void getData() {
            mSrlRefresh.setRefreshing(true);
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
                    try {
                    Log.d("创建活动", response);
                    mSrlRefresh.setRefreshing(false);
                    if (response != null) {
                            Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                            if (yiXiangJin.getRet().equals("200")) {
                                dataList = yiXiangJin.getDataList();
                                if (dataList.size() != 0) {
                                    shouData(dataList);

                                } else {
                                    shouData(dataList);

                                }
                            }

                    }
                    } catch (Exception e) {
                        Log.d("出错了", e.toString());
                    }
                }

            });

    }


    private void shouData(final List<Test.DataListBean> data) {
        try {
            mReMenShangPinAdapter = new ReMenShangPinAdapter(R.layout.item_remenshangpin, data);
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setNestedScrollingEnabled(false);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(getContext()));
                isFirst = false;
            }
//            mRvRecycleview.addItemDecoration(new CommonItemDecoration(25,30));
            mReMenShangPinAdapter.setOnLoadMoreListener(this,mRvRecycleview);
            mRvRecycleview.setAdapter(mReMenShangPinAdapter);
            mReMenShangPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(),GoodsDetailActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        page=1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        page = page + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=58526df4175a42429685bdf320c892e4&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mReMenShangPinAdapter.loadMoreComplete();
                    mReMenShangPinAdapter.loadMoreEnd();
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
                                mReMenShangPinAdapter.loadMoreComplete();
                                mReMenShangPinAdapter.loadMoreEnd();
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mReMenShangPinAdapter.loadMoreComplete();
                                mReMenShangPinAdapter.loadMoreEnd();
                            }
                        } else {
                            mReMenShangPinAdapter.loadMoreComplete();
                            mReMenShangPinAdapter.loadMoreEnd();
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
            mReMenShangPinAdapter.addData(data);
            mReMenShangPinAdapter.loadMoreComplete();
        }

    }
}
