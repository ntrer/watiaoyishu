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

import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.JiFenAdapter;
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

public class JiFenMingXiFragment extends BaseFragment {


    @BindView(R.id.rv_jifen)
    RecyclerView mRvJifen;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    private String leixing;
    private JiFenAdapter mJiFenAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();


    public JiFenMingXiFragment() {
        // Required empty public constructor
    }

    public static JiFenMingXiFragment newInstance(String fenlei) {
        JiFenMingXiFragment fragment = new JiFenMingXiFragment();
        Bundle args = new Bundle();
        args.putString("fenlei", fenlei);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        leixing = getArguments().getString("fenlei");
        getData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_jifenmingxi, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id="+BaseUrl.Token+"&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
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
            if(leixing.equals("获取明细")){
                mJiFenAdapter = new JiFenAdapter(R.layout.item_jifenmingxi, data);
            }
            else {
                mJiFenAdapter = new JiFenAdapter(R.layout.item_jifenmingxi2, data);
            }
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvJifen.setLayoutManager(manager);
            mRvJifen.setAdapter(mJiFenAdapter);
            mRvJifen.setNestedScrollingEnabled(false);

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
