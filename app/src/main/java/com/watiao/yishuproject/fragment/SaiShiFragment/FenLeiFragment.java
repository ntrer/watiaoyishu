package com.watiao.yishuproject.fragment.SaiShiFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.AdActivity;
import com.watiao.yishuproject.activity.SaiShiXiangQingActivity;
import com.watiao.yishuproject.adapter.FenLeiSaiShi1Adapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.GlideImageLoader;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class FenLeiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    @BindView(R.id.nesv)
    NestedScrollView mNesv;
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
    Unbinder unbinder1;
    private boolean isFirst = true;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private FenLeiSaiShi1Adapter mFenLeiSaiShi1Adapter;
    private List<String> list = new ArrayList<>();
    private int page = 1;
    private String leixing;
    private boolean isLoadMore = true;
    public FenLeiFragment() {
        // Required empty public constructor
    }

    public static FenLeiFragment newInstance(String fenlei) {
        FenLeiFragment fragment = new FenLeiFragment();
        Bundle args = new Bundle();
        args.putString("fenlei", fenlei);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/37639/36/4317/94390/5cc83680E6fdd8260/28e8c83ee507d9c6.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t28954/169/1113073846/65121/d7108622/5cd637dfNe7ede62b.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/75243/27/1937/93321/5d036705E51e72ec1/a47e25f60c6dc8cd.jpg!q90!cc_590x470.webp");
        leixing = getArguments().getString("fenlei");
        mSrlRefresh.setOnRefreshListener(this);
        mNesv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if(isLoadMore){
                        loadMore();
                    }
                }
            }
        });

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent(getContext(),AdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        getBanner(list);
        getData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_fenlei1, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void getBanner(List<String> list) {
        Object object = getFieldValue(mBanner, "indicator");
        if (object != null && object instanceof LinearLayout) {
            LinearLayout indicator = (LinearLayout) object;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicator.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(10));
            indicator.setLayoutParams(layoutParams);
        }
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(list);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.start();

    }

    public static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=2&page=1&rows=10";
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
            mFenLeiSaiShi1Adapter = new FenLeiSaiShi1Adapter(R.layout.item_saishifenlei, data);
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mFenLeiSaiShi1Adapter);
            mRvRecycleview.setNestedScrollingEnabled(false);
            mFenLeiSaiShi1Adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), SaiShiXiangQingActivity.class));
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
        page = 1;
        isLoadMore=true;
        getData();
    }

    private void loadMore() {
        page = page + 1;
        mLoadMoreLoadingView.setVisibility(View.VISIBLE);
        mLoadMoreLoadEndView.setVisibility(View.GONE);
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mFenLeiSaiShi1Adapter.loadMoreComplete();
                    mFenLeiSaiShi1Adapter.loadMoreEnd();
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
                                mFenLeiSaiShi1Adapter.loadMoreComplete();
                                mFenLeiSaiShi1Adapter.loadMoreEnd();
                                isLoadMore=false;
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mFenLeiSaiShi1Adapter.loadMoreComplete();
                                mFenLeiSaiShi1Adapter.loadMoreEnd();
                                isLoadMore=false;
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            }
                        } else {
                            mFenLeiSaiShi1Adapter.loadMoreComplete();
                            mFenLeiSaiShi1Adapter.loadMoreEnd();
                            mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
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
        if (data.size() != 0) {
            mFenLeiSaiShi1Adapter.addData(mFenLeiSaiShi1Adapter.getData().size(),data);
            mRvRecycleview.scrollToPosition(mFenLeiSaiShi1Adapter.getData().size());
        }

    }
}
