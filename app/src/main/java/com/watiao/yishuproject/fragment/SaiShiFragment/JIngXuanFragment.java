package com.watiao.yishuproject.fragment.SaiShiFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.AdActivity;
import com.watiao.yishuproject.activity.SaiShiXiangQingActivity;
import com.watiao.yishuproject.activity.ZhuBanFangActivity;
import com.watiao.yishuproject.adapter.ReMenSaiShiAdapter;
import com.watiao.yishuproject.adapter.TaZaiBanAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
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

public class JIngXuanFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.zaibandehuodong)
    RelativeLayout mZaibandehuodong;
    @BindView(R.id.nesv)
    NestedScrollView mNesv;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    @BindView(R.id.tazaiban)
    TextView mTazaiban;
    @BindView(R.id.rv_recycleview2)
    RecyclerView mRvRecycleview2;
    Unbinder unbinder1;
    @BindView(R.id.logo)
    ImageView mLogo;
    Unbinder unbinder2;

    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Test.DataListBean> dataList2 = new ArrayList<>();
    private ReMenSaiShiAdapter mReMenSaiShiAdapter;
    private TaZaiBanAdapter mTaZaiBanAdapter;
    private List<String> list = new ArrayList<>();
    private boolean isFirst = true;
    private int page = 1;
    private boolean isLoadMore = true;

    public JIngXuanFragment() {
        // Required empty public constructor
    }

    public static JIngXuanFragment newInstance() {
        return new JIngXuanFragment();
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/37639/36/4317/94390/5cc83680E6fdd8260/28e8c83ee507d9c6.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t28954/169/1113073846/65121/d7108622/5cd637dfNe7ede62b.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/75243/27/1937/93321/5d036705E51e72ec1/a47e25f60c6dc8cd.jpg!q90!cc_590x470.webp");
        mSrlRefresh.setOnRefreshListener(this);
        getBanner(list);
        getData();
        getZaiBan();
        mNesv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (isLoadMore) {
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

    private void getZaiBan() {
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
                            dataList2 = yiXiangJin.getDataList();
                            if (dataList2.size() != 0) {
                                showZaiBan(dataList2);

                            } else {
                                showZaiBan(dataList2);

                            }
                        }
                    } catch (Exception e) {
                        Log.d("出错了", e.toString());
                    }
                }

            }
        });
    }

    private void showZaiBan(List<Test.DataListBean> dataList2) {
        mTaZaiBanAdapter = new TaZaiBanAdapter(R.layout.layout_tazaiban, dataList2);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRvRecycleview2.setLayoutManager(manager);
        mRvRecycleview2.setAdapter(mTaZaiBanAdapter);
        mTaZaiBanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ZhuBanFangActivity.class));
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_jingxuan, null);
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
            mReMenSaiShiAdapter = new ReMenSaiShiAdapter(R.layout.item_saishi_sousuo, data);
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(getContext()));
                isFirst = false;
            }
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setNestedScrollingEnabled(false);
            mRvRecycleview.setAdapter(mReMenSaiShiAdapter);
            mReMenSaiShiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), SaiShiXiangQingActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void loadMore() {
        page = page + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id="+ BaseUrl.Token + "&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mReMenSaiShiAdapter.loadMoreComplete();
                    mReMenSaiShiAdapter.loadMoreEnd();
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
                                mReMenSaiShiAdapter.loadMoreComplete();
                                mReMenSaiShiAdapter.loadMoreEnd();
                                isLoadMore = false;
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                                mReMenSaiShiAdapter.loadMoreComplete();
                            } else if (test.getDataList().size() == 0) {
                                mReMenSaiShiAdapter.loadMoreComplete();
                                mReMenSaiShiAdapter.loadMoreEnd();
                                isLoadMore = false;
                            }
                        } else {
                            mReMenSaiShiAdapter.loadMoreComplete();
                            mReMenSaiShiAdapter.loadMoreEnd();
                            isLoadMore = false;
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
            mReMenSaiShiAdapter.addData(mReMenSaiShiAdapter.getData().size(),data);
            mRvRecycleview.scrollToPosition(mReMenSaiShiAdapter.getData().size());
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
        isLoadMore=true;
        getData();
    }

}
