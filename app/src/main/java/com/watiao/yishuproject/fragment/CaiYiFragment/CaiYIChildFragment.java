package com.watiao.yishuproject.fragment.CaiYiFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.ShiPinXiangQing2Activity;
import com.watiao.yishuproject.adapter.CaiYiShiPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class CaiYIChildFragment extends BaseFragment {


    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.loading_progress)
    ProgressBar mLoadingProgress;
    @BindView(R.id.loading_text)
    TextView mLoadingText;
    @BindView(R.id.load_more_loading_view)
    LinearLayout mLoadMoreLoadingView;
    @BindView(R.id.tv_prompt)
    TextView mTvPrompt;
    @BindView(R.id.load_more_load_fail_view)
    FrameLayout mLoadMoreLoadFailView;
    @BindView(R.id.pic)
    ImageView mPic;
    @BindView(R.id.load_more_load_end_view)
    FrameLayout mLoadMoreLoadEndView;
    @BindView(R.id.loading)
    FrameLayout mLoading;
    Unbinder unbinder;
    private String leixing;
    private boolean isFirst = true;
    private CaiYiShiPinAdapter mCaiYiShiPinAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private int page = 1;
    private boolean isLoadMore = true;

    public CaiYIChildFragment() {
        // Required empty public constructor
    }

    public static CaiYIChildFragment newInstance(String fenlei) {
        CaiYIChildFragment fragment = new CaiYIChildFragment();
        Bundle args = new Bundle();
        args.putString("fenlei", fenlei);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        leixing = getArguments().getString("fenlei");
        EventBus.getDefault().register(this);
        getData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("加载综合更多")) {
            loadMore();
        }
        else if(messageEvent.getMessage().equals("加载发布更多")){
            loadMore();
        }
        else if(messageEvent.getMessage().equals("下拉刷新")){
            page=1;
            getData();
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_caiyichild, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    @Override
    public void initData() {
        super.initData();
    }

    private void getData() {
        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id=" + BaseUrl.Token + " &loginOS=2&page=1&rows=10";
        Log.d("创建活动", url);
        HashMap<String, String> paramsMap = new HashMap<>();
        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                ToastUtils.showLong(e.toString());
            }

            @Override
            public void onResponse(String response) {
                EventBus.getDefault().post(new MessageEvent("刷新完成"));
                Log.d("创建活动", response);
                if (response != null) {
//                    getBanner();
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
//        getBanner();
        try {
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            mRvRecycleview.setLayoutManager(manager);
            mCaiYiShiPinAdapter = new CaiYiShiPinAdapter(R.layout.item_caiyishipin, data);
            mRvRecycleview.setAdapter(mCaiYiShiPinAdapter);
            mRvRecycleview.setNestedScrollingEnabled(false);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(getContext()));
                isFirst = false;
            }

            mCaiYiShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), ShiPinXiangQing2Activity.class));
                    getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out2);
                }
            });

            mCaiYiShiPinAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int itemViewId = view.getId();
                    try {
                        if (itemViewId == R.id.like) {
                            ImageView viewByPosition = (ImageView) adapter.getViewByPosition(mRvRecycleview, position, R.id.like_pic);
                            if (viewByPosition.isSelected()) {
                                viewByPosition.setSelected(false);
                                viewByPosition.setColorFilter(Color.rgb(255, 255, 255));
                            } else {
                                viewByPosition.setSelected(true);
                                viewByPosition.setColorFilter(Color.rgb(251, 92, 111));
                            }
                        }
                    } catch (Exception e) {
                        ToastUtils.showLong(e.toString());
                    }
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


        private void loadMore() {
        page = page + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=58526df4175a42429685bdf320c892e4&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mCaiYiShiPinAdapter.loadMoreComplete();
                    mCaiYiShiPinAdapter.loadMoreEnd();
                }

                @Override
                public void onResponse(String response) {
                    Log.d("创建活动", response);
                    if (response != null) {
                        Log.d("nnnnnnn", response);
                        mLoadMoreLoadingView.setVisibility(View.GONE);
                        Test test = JSONUtil.fromJson(response, Test.class);
                        if (test.getRet().equals("200")) {
                            if (page > test.getIntmaxPage()) {
                                page = 1;
                                mCaiYiShiPinAdapter.loadMoreComplete();
                                mCaiYiShiPinAdapter.loadMoreEnd();
                                isLoadMore=false;
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mCaiYiShiPinAdapter.loadMoreComplete();
                                mCaiYiShiPinAdapter.loadMoreEnd();
                                isLoadMore=false;
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            }
                        } else {
                            mCaiYiShiPinAdapter.loadMoreComplete();
                            mCaiYiShiPinAdapter.loadMoreEnd();
                            isLoadMore=false;
//                            mZaibandehuodong.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void LoadMoreData(List<Test.DataListBean> data) {
        if (dataList.size() != 0) {
            mCaiYiShiPinAdapter.addData(data);
            dataList.addAll(dataList);
            mCaiYiShiPinAdapter.loadMoreComplete();
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
