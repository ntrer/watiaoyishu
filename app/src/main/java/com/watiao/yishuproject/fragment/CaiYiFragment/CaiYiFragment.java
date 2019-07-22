package com.watiao.yishuproject.fragment.CaiYiFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.watiao.yishuproject.activity.CityPickerActivity;
import com.watiao.yishuproject.activity.HotSearchActivity;
import com.watiao.yishuproject.activity.MengWaZhuYeActivity;
import com.watiao.yishuproject.activity.ShiPinXiangQingActivity;
import com.watiao.yishuproject.adapter.CaiYiShiPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.base.MessageEvent4;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
import com.watiao.yishuproject.utils.GlideImageLoader;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;
import com.watiao.yishuproject.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

public class CaiYiFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int REQUEST_CODE_LOCATION = 2002;


    Unbinder unbinder;
    @BindView(R.id.dingwei)
    TextView mDingwei;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.zonghepaixu_text)
    TextView mZonghepaixuText;
    @BindView(R.id.zonghepaixu_line)
    View mZonghepaixuLine;
    @BindView(R.id.zonghepaixu)
    RelativeLayout mZonghepaixu;
    @BindView(R.id.fabushijian_text)
    TextView mFabushijianText;
    @BindView(R.id.fabushijian_line)
    View mFabushijianLine;
    @BindView(R.id.fabushijian)
    RelativeLayout mFabushijian;
    @BindView(R.id.daohang)
    RelativeLayout mDaohang;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @BindView(R.id.nesv)
    NestedScrollView mNesv;
    Unbinder unbinder1;
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
    private CaiYiShiPinAdapter mCaiYiShiPinAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Test.DataListBean> dataList2 = new ArrayList<>();
    private View header;
    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    //    private Banner mBanner;
//    private TextView mZongHePaiXu, mFaBuShiJian;
//    private View lineZongHe, lineFabu;
//    private RelativeLayout rlZongHePaiXu, rlFaBuShiJian;
    private boolean isZongHe = true;
    private boolean isFirst = true;
    private int page = 1;
    private boolean isLoadMore = true;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mSrlRefresh.setOnRefreshListener(this);
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/37639/36/4317/94390/5cc83680E6fdd8260/28e8c83ee507d9c6.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t28954/169/1113073846/65121/d7108622/5cd637dfNe7ede62b.jpg!q90!cc_590x470.webp");
        list.add("https://img1.360buyimg.com/pop/s590x470_jfs/t1/75243/27/1937/93321/5d036705E51e72ec1/a47e25f60c6dc8cd.jpg!q90!cc_590x470.webp");

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
//        shouData(list2);
        getData();

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


//    private void getBanner() {
//        header = View.inflate(getContext(), R.layout.caiyi_header, null);
//        mBanner = header.findViewById(R.id.banner);
//        mZongHePaiXu = header.findViewById(R.id.zonghepaixu_text);
//        mFaBuShiJian = header.findViewById(R.id.fabushijian_text);
//        lineFabu = header.findViewById(R.id.fabushijian_line);
//        lineZongHe = header.findViewById(R.id.zonghepaixu_line);
//        rlZongHePaiXu = header.findViewById(R.id.zonghepaixu);
//        rlFaBuShiJian = header.findViewById(R.id.fabushijian);
//        Object object = getFieldValue(mBanner, "indicator");
//        if (object != null && object instanceof LinearLayout) {
//            LinearLayout indicator = (LinearLayout) object;
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicator.getLayoutParams();
//            layoutParams.setMargins(0, 0, 0, ConvertUtils.dp2px(10));
//            indicator.setLayoutParams(layoutParams);
//        }
//        mBanner.setImageLoader(new GlideImageLoader());
//        mBanner.setImages(list);
//        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
//        mBanner.start();
//
//        rlZongHePaiXu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isZongHe = true;
//                mZongHePaiXu.setTextColor(getResources().getColor(R.color.app_text_color));
//                mFaBuShiJian.setTextColor(getResources().getColor(R.color.textColor6));
//                lineZongHe.setBackgroundColor(getResources().getColor(R.color.weixin));
//                lineFabu.setBackgroundColor(getResources().getColor(R.color.color_w));
////                getData();
//            }
//        });
//
//
//        rlFaBuShiJian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isZongHe = false;
//                mZongHePaiXu.setTextColor(getResources().getColor(R.color.textColor6));
//                mFaBuShiJian.setTextColor(getResources().getColor(R.color.app_text_color));
//                lineZongHe.setBackgroundColor(getResources().getColor(R.color.color_w));
//                lineFabu.setBackgroundColor(getResources().getColor(R.color.weixin));
////                getData();
//            }
//        });
//    }

    public static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }


    @OnClick(R.id.zonghepaixu)
    void zonghepaixu() {
        isZongHe = true;
        mZonghepaixuText.setTextColor(getResources().getColor(R.color.app_text_color));
        mFabushijianText.setTextColor(getResources().getColor(R.color.textColor6));
        mZonghepaixuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mFabushijianLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        page=1;
        isLoadMore=true;
        getData();
    }

    @OnClick(R.id.fabushijian)
    void fabushijian() {
        isZongHe = false;
        mZonghepaixuText.setTextColor(getResources().getColor(R.color.textColor6));
        mFabushijianText.setTextColor(getResources().getColor(R.color.app_text_color));
        mZonghepaixuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mFabushijianLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        page=1;
        isLoadMore=true;
        getData();
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
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
                Log.d("创建活动", response);
                mSrlRefresh.setRefreshing(false);
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
//            mCaiYiShiPinAdapter.addHeaderView(header);
            if (isZongHe) {
                mZonghepaixuText.setTextColor(getResources().getColor(R.color.app_text_color));
                mFabushijianText.setTextColor(getResources().getColor(R.color.textColor6));
                mZonghepaixuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                mFabushijianLine.setBackgroundColor(getResources().getColor(R.color.color_w));
            } else {
                mZonghepaixuText.setTextColor(getResources().getColor(R.color.textColor6));
                mFabushijianText.setTextColor(getResources().getColor(R.color.app_text_color));
                mZonghepaixuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                mFabushijianLine.setBackgroundColor(getResources().getColor(R.color.weixin));
            }

            mCaiYiShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (Utils.isFastClick()) {
                        // 进行点击事件后的逻辑操作
                        startActivity(new Intent(getContext(), ShiPinXiangQingActivity.class));
                        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_stay);
                    }

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
                            else if(itemViewId==R.id.user_pic){
                                Intent intent=new Intent(getContext(),MengWaZhuYeActivity.class);
                                startActivity(intent);
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



        @OnClick(R.id.dingwei)
    void dingwei() {
        Intent intent = new Intent(getContext(), CityPickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOCATION);
    }

    @OnClick(R.id.search)
    void search() {
        Intent intent = new Intent(getContext(), HotSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_LOCATION&&resultCode==RESULT_OK){
            mDingwei.setText(data.getStringExtra("city"));
        }
    }


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_caiyii, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void Event(MessageEvent4 messageEvent) {
        if (messageEvent.getMessage().equals("定位")) {
            mDingwei.setText(messageEvent.getPrice());
        }
        else if(messageEvent.getMessage().equals("刷新完成")){
            mSrlRefresh.setRefreshing(false);
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

    @Override
    public void onRefresh() {
       page=1;
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
            ToastUtils.showLong("加载更多");
            mCaiYiShiPinAdapter.addData(mCaiYiShiPinAdapter.getData().size(),data);
            mRvRecycleview.scrollToPosition(mCaiYiShiPinAdapter.getData().size());
        }

    }
}
