package com.watiao.yishuproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.GoodsCommentAdapter;
import com.watiao.yishuproject.base.BaseFragment;
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
import butterknife.Unbinder;
import okhttp3.Call;

public class CommentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;

    Unbinder unbinder;
    @BindView(R.id.nodata)
    ImageView mNodata;
    @BindView(R.id.ll_nodata)
    LinearLayout mLlNodata;
    Unbinder unbinder1;
    private GoodsCommentAdapter mGoodsCommentAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private int page = 1;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mSrlRefresh.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        getData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_comment, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
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
            mGoodsCommentAdapter = new GoodsCommentAdapter(R.layout.item_goods_comment, data);
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvRecycleview.setLayoutManager(manager);
            mGoodsCommentAdapter.setOnLoadMoreListener(this, mRvRecycleview);
            mRvRecycleview.setAdapter(mGoodsCommentAdapter);
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
        page = 1;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    private void loadMore() {
        try {
            page = page + 1;
            String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id=c4ddac70eb5a47e68bce76137d22c08a&orderType=1&sort=desc&loginOS=2&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();

            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    Toast.makeText(getActivity(), "错误了", Toast.LENGTH_SHORT).show();
                    mGoodsCommentAdapter.loadMoreComplete();
                    mGoodsCommentAdapter.loadMoreEnd();
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
                                mGoodsCommentAdapter.loadMoreComplete();
                                mGoodsCommentAdapter.loadMoreEnd();
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> radarRecordList = test.getDataList();
                                LoadMoreData(radarRecordList);
                                Log.d("33333333333", response);
                                mGoodsCommentAdapter.loadMoreComplete();
                            } else if (test.getDataList().size() == 0) {
                                mGoodsCommentAdapter.loadMoreComplete();
                                mGoodsCommentAdapter.loadMoreEnd();
                            }
                        } else {
                            mGoodsCommentAdapter.loadMoreComplete();
                            mGoodsCommentAdapter.loadMoreEnd();
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
            mGoodsCommentAdapter.addData(dataList);
            dataList.addAll(dataList);
            mGoodsCommentAdapter.loadMoreComplete();
        }

    }
}
