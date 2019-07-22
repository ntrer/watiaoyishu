package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.ReMenShangPinAdapter;
import com.watiao.yishuproject.base.BaseActivity;
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
import butterknife.OnClick;
import okhttp3.Call;

public class ShangChengActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener,BaseSectionQuickAdapter.RequestLoadMoreListener{


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.jifen_title)
    TextView mJifenTitle;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.jifenlan)
    RelativeLayout mJifenlan;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.quanbushangpin)
    LinearLayout mQuanbushangpin;
    @BindView(R.id.shipin)
    LinearLayout mShipin;
    @BindView(R.id.tushu)
    LinearLayout mTushu;
    @BindView(R.id.wenju)
    LinearLayout mWenju;
    @BindView(R.id.wanju)
    LinearLayout mWanju;
    @BindView(R.id.fenlei)
    LinearLayout mFenlei;
    @BindView(R.id.remenshangpin)
    TextView mRemenshangpin;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.nslv)
    NestedScrollView mNslv;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private ReMenShangPinAdapter mReMenShangPinAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private View header;
    private List<String> list = new ArrayList<>();
    private boolean isFirst = true;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSrlRefresh.setOnRefreshListener(this);
        getBanner();
    }

    private void getBanner() {
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
        getData();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent=new Intent(ShangChengActivity.this,AdActivity.class);
                startActivity(intent);
            }
        });
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
            mReMenShangPinAdapter = new ReMenShangPinAdapter(R.layout.item_remenshangpin, data);
            final GridLayoutManager manager = new GridLayoutManager(ShangChengActivity.this, 2);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setNestedScrollingEnabled(false);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(this));
                isFirst = false;
            }
//            mRvRecycleview.addItemDecoration(new CommonItemDecoration(25,30));
            mReMenShangPinAdapter.setOnLoadMoreListener(this,mRvRecycleview);
            mRvRecycleview.setAdapter(mReMenShangPinAdapter);
            mReMenShangPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(ShangChengActivity.this,GoodsDetailActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shang_cheng;
    }

    @Override
    public void init() {

    }



    @OnClick(R.id.quanbushangpin)
    void quanbushangpin(){
        startActivity(new Intent(ShangChengActivity.this,QuanBuShangPinActivity.class));
    }


    @OnClick(R.id.jifenlan)
    void jifenlan(){
        startActivity(new Intent(ShangChengActivity.this,JiFenMingXiActivity3.class));
    }


    @OnClick(R.id.shipin)
    void shipin(){
        Intent intent=new Intent(ShangChengActivity.this,ShangChengFenLeiActivity.class);
        intent.putExtra("title","食品");
        startActivity(intent);
    }

    @OnClick(R.id.tushu)
    void tushu(){
        Intent intent=new Intent(ShangChengActivity.this,ShangChengFenLeiActivity.class);
        intent.putExtra("title","图书");
        startActivity(intent);
    }

    @OnClick(R.id.wenju)
    void wenju(){
        Intent intent=new Intent(ShangChengActivity.this,ShangChengFenLeiActivity.class);
        intent.putExtra("title","文具");
        startActivity(intent);
    }

    @OnClick(R.id.wanju)
    void wanju(){
        Intent intent=new Intent(ShangChengActivity.this,ShangChengFenLeiActivity.class);
        intent.putExtra("title","玩具");
        startActivity(intent);
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
        if (dataList.size() != 0) {
            mReMenShangPinAdapter.addData(data);
            dataList.addAll(data);
            mReMenShangPinAdapter.loadMoreComplete();
        }

    }
}
