package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.ZhuBanFangSaiShiAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
import com.watiao.yishuproject.utils.ImageFilter;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class ZhuBanFangActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.user_img)
    CircleImageView mUserImg;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user)
    RelativeLayout mUser;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.zhubanfangjieshao)
    RelativeLayout mZhubanfangjieshao;
    @BindView(R.id.jieshao)
    TextView mJieshao;
    @BindView(R.id.zhubanfangsaishi)
    RelativeLayout mZhubanfangsaishi;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
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
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private ZhuBanFangSaiShiAdapter mZhuBanFangSaiShiAdapter;
    private boolean isFirst = true;
    private int page = 1;
    private boolean isLoadMore = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setStatusbar();
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        //模糊
        Resources res = ZhuBanFangActivity.this.getResources();
        //拿到初始图
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.memgwa);
        //处理得到模糊效果的图
        Bitmap blurBitmap = ImageFilter.blurBitmap(this, bmp, 20f);
        mImg.setImageBitmap(blurBitmap);
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
            mZhuBanFangSaiShiAdapter = new ZhuBanFangSaiShiAdapter(R.layout.item_saishi_sousuo, data);
            final GridLayoutManager manager = new GridLayoutManager(ZhuBanFangActivity.this, 2);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(this));
                isFirst = false;
            }
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setNestedScrollingEnabled(false);
            mRvRecycleview.setAdapter(mZhuBanFangSaiShiAdapter);
            mZhuBanFangSaiShiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(ZhuBanFangActivity.this, SaiShiXiangQingActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void setStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //下面这一行呢是android4.0起推荐大家使用的将布局延伸到状态栏的代码，配合5.0的设置状态栏颜色可谓天作之合
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_zhu_ban_fang;
    }

    @Override
    public void init() {

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
                    mZhuBanFangSaiShiAdapter.loadMoreComplete();
                    mZhuBanFangSaiShiAdapter.loadMoreEnd();
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
                                mZhuBanFangSaiShiAdapter.loadMoreComplete();
                                mZhuBanFangSaiShiAdapter.loadMoreEnd();
                                isLoadMore = false;
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mZhuBanFangSaiShiAdapter.loadMoreComplete();
                                mZhuBanFangSaiShiAdapter.loadMoreEnd();
                                isLoadMore = false;
                                mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
//                                mZaibandehuodong.setVisibility(View.VISIBLE);
                                ToastUtils.showLong("无更多数据");
                            }
                        } else {
                            mZhuBanFangSaiShiAdapter.loadMoreComplete();
                            mZhuBanFangSaiShiAdapter.loadMoreEnd();
                            mLoadMoreLoadEndView.setVisibility(View.VISIBLE);
                            isLoadMore = false;
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
            mZhuBanFangSaiShiAdapter.addData(mZhuBanFangSaiShiAdapter.getData().size(),data);
            mRvRecycleview.scrollToPosition(mZhuBanFangSaiShiAdapter.getData().size());
        }

    }

}
