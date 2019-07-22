package com.watiao.yishuproject.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.JiFenAdapter;
import com.watiao.yishuproject.base.BaseActivity;
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
import butterknife.OnClick;
import okhttp3.Call;


public class JiFenMingXiActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.keyongjifen)
    TextView mKeyongjifen;
    @BindView(R.id.huoqumingxi)
    TextView mHuoqumingxi;
    @BindView(R.id.duihuanjilu)
    TextView mDuihuanjilu;
    @BindView(R.id.header)
    RelativeLayout mHeader;
    @BindView(R.id.rv_huoqu)
    RecyclerView mRvHuoqu;
    @BindView(R.id.rv_duihuan)
    RecyclerView mRvDuihuan;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    @BindView(R.id.nsesv)
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
    @BindView(R.id.loading1)
    FrameLayout mLoading1;
    @BindView(R.id.loading_progress2)
    ProgressBar mLoadingProgress2;
    @BindView(R.id.loading_text2)
    TextView mLoadingText2;
    @BindView(R.id.load_more_loading_view2)
    LinearLayout mLoadMoreLoadingView2;
    @BindView(R.id.tv_prompt2)
    TextView mTvPrompt2;
    @BindView(R.id.load_more_load_fail_view2)
    FrameLayout mLoadMoreLoadFailView2;
    @BindView(R.id.pic2)
    ImageView mPic2;
    @BindView(R.id.load_more_load_end_view2)
    FrameLayout mLoadMoreLoadEndView2;
    @BindView(R.id.loading2)
    FrameLayout mLoading2;
    private View statusBarView;
    private JiFenAdapter mJiFenAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Test.DataListBean> dataList2 = new ArrayList<>();
    private boolean isFirst = true;
    private int dataType = 1;
    private int page = 1;
    private int page2 = 1;
    private boolean isLoadMore1 = true;
    private boolean isLoadMore2 = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isStatusBar()) {
            initStatusBar();
            getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    initStatusBar();
                }
            });
        }
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        getData();

        mNesv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if(dataType==1){
                        if (isLoadMore1) {
                            loadMore1();
                        }
                    }
                    else {
                        if (isLoadMore2) {
                            loadMore2();
                        }
                    }
                }
            }
        });
//        SetUpViewPager(mViewpager);
//        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position==0){
//                    mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border8));
//                    mHuoqumingxi.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
//                    mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border9));
//                    mDuihuanjilu.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
//                }
//                else {
//                    mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border11));
//                    mHuoqumingxi.setTypeface(Typeface.DEFAULT,Typeface.NORMAL);
//                    mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border10));
//                    mDuihuanjilu.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    protected boolean isStatusBar() {
        return true;
    }


    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.bg_color3);
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ji_fen_ming_xi;
    }

    @Override
    public void init() {

    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
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
                            mLoading1.setVisibility(View.VISIBLE);
                            mLoading2.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Log.d("出错了", e.toString());
                    }
                }

            }
        });
    }

    private void getData2() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
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
                    isFirst = false;
                    try {
                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                        if (yiXiangJin.getRet().equals("200")) {
                            dataList2 = yiXiangJin.getDataList();
                            if (dataList2.size() != 0) {
                                shouData2(dataList2);
                            } else {
                                shouData2(dataList2);

                            }
                            mLoading1.setVisibility(View.GONE);
                            mLoading2.setVisibility(View.VISIBLE);
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
            final LinearLayoutManager manager = new LinearLayoutManager(this);
            mJiFenAdapter = new JiFenAdapter(R.layout.item_jifenmingxi, data);
            mRvHuoqu.setLayoutManager(manager);
            mRvHuoqu.setAdapter(mJiFenAdapter);
            mRvHuoqu.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void shouData2(final List<Test.DataListBean> data) {
        try {
            final LinearLayoutManager manager = new LinearLayoutManager(this);
            mJiFenAdapter = new JiFenAdapter(R.layout.item_jifenmingxi2, data);
            mRvDuihuan.setLayoutManager(manager);
            mRvDuihuan.setAdapter(mJiFenAdapter);
            mRvDuihuan.setNestedScrollingEnabled(false);
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.huoqumingxi)
    void huoqumingxi() {
        dataType = 1;
        mRvHuoqu.setVisibility(View.VISIBLE);
        mRvDuihuan.setVisibility(View.GONE);
        mLoading1.setVisibility(View.VISIBLE);
        mLoading2.setVisibility(View.GONE);
        mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border8));
        mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border9));
        mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
    }


    @OnClick(R.id.duihuanjilu)
    void duihuanjilu() {
        dataType = 2;
        if (isFirst) {
            getData2();
        }
        mRvHuoqu.setVisibility(View.GONE);
        mRvDuihuan.setVisibility(View.VISIBLE);
        mLoading1.setVisibility(View.GONE);
        mLoading2.setVisibility(View.VISIBLE);
        mHuoqumingxi.setBackground(getResources().getDrawable(R.drawable.text_border11));
        mHuoqumingxi.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
        mDuihuanjilu.setBackground(getResources().getDrawable(R.drawable.text_border10));
        mDuihuanjilu.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    }

//    private void SetUpViewPager(ViewPager bookViewpager) {
//        JiFenMingXiAdapter adapter = new JiFenMingXiAdapter(getSupportFragmentManager());
//        adapter.addFragment(JiFenMingXiFragment.newInstance("获取明细"), "获取明细");
//        adapter.addFragment(JiFenMingXiFragment.newInstance("兑换记录"), "兑换记录");
//        bookViewpager.setAdapter(adapter);
//        bookViewpager.setCurrentItem(0, true);
//        bookViewpager.setOffscreenPageLimit(2);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {
        page = 1;
        page2 = 1;
        isLoadMore1=true;
        isLoadMore2=true;
        if (dataType == 1) {
            getData();
        } else {
            getData2();
        }
    }

    private void loadMore1() {
        page = page + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mJiFenAdapter.loadMoreComplete();
                    mJiFenAdapter.loadMoreEnd();
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
                                mJiFenAdapter.loadMoreComplete();
                                mJiFenAdapter.loadMoreEnd();
                                isLoadMore1=false;
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData1(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mJiFenAdapter.loadMoreComplete();
                                mJiFenAdapter.loadMoreEnd();
                                isLoadMore1=false;
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            }
                        } else {
                            mJiFenAdapter.loadMoreComplete();
                            mJiFenAdapter.loadMoreEnd();
                            isLoadMore1=false;
//                            mZaibandehuodong.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void LoadMoreData1(List<Test.DataListBean> data) {
        if (dataList.size() != 0) {
            mJiFenAdapter.addData(data);
            dataList.addAll(dataList);
            mJiFenAdapter.loadMoreComplete();
        }

    }

    private void loadMore2() {
        page2 = page2 + 1;
        try {
            String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=1&page=" + page + "&rows=10";
            HashMap<String, String> paramsMap = new HashMap<>();
            OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    mJiFenAdapter.loadMoreComplete();
                    mJiFenAdapter.loadMoreEnd();
                }

                @Override
                public void onResponse(String response) {
                    Log.d("创建活动", response);
                    if (response != null) {
                        Log.d("nnnnnnn", response);
                        mLoadMoreLoadingView2.setVisibility(View.GONE);
                        Test test = JSONUtil.fromJson(response, Test.class);
                        if (test.getRet().equals("200")) {
                            if (page2 > test.getIntmaxPage()) {
                                page2 = 1;
                                mJiFenAdapter.loadMoreComplete();
                                mJiFenAdapter.loadMoreEnd();
                                isLoadMore2=false;
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                mLoadMoreLoadEndView2.setVisibility(View.VISIBLE);
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData2(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mJiFenAdapter.loadMoreComplete();
                                mJiFenAdapter.loadMoreEnd();
                                isLoadMore2=false;
                                mLoadMoreLoadEndView2.setVisibility(View.VISIBLE);
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            }
                        } else {
                            mJiFenAdapter.loadMoreComplete();
                            mJiFenAdapter.loadMoreEnd();
                            isLoadMore2=false;
//                            mZaibandehuodong.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void LoadMoreData2(List<Test.DataListBean> data) {
        if (dataList.size() != 0) {
            mJiFenAdapter.addData(data);
            dataList2.addAll(dataList);
            mJiFenAdapter.loadMoreComplete();
        }

    }
    
}
