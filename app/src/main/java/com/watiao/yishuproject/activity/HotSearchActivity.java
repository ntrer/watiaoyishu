package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.HotSearchAdapter;
import com.watiao.yishuproject.adapter.HotSearchHuaTiAdapter;
import com.watiao.yishuproject.adapter.HotSearchShangChuanZheAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.fragment.HotSearchFragment.SouSuoShiPinFragment;
import com.watiao.yishuproject.fragment.HotSearchFragment.YongHuFragment;
import com.watiao.yishuproject.fragment.HotSearchFragment.ZongHeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HotSearchActivity extends BaseActivity {

    @BindView(R.id.txt_souuo)
    AppCompatEditText mTxtSouuo;
    @BindView(R.id.search_title)
    RelativeLayout mSearchTitle;
    @BindView(R.id.cancle)
    TextView mCancle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.huati_title)
    TextView mHuatiTitle;
    @BindView(R.id.rv_huati)
    RecyclerView mRvHuati;
    @BindView(R.id.shangchuangzhe_title)
    TextView mShangchuangzheTitle;
    @BindView(R.id.rv_shangchuangzhe)
    RecyclerView mRvShangchuangzhe;
    @BindView(R.id.shipin_text)
    TextView mShipinText;
    @BindView(R.id.shipin_line)
    View mShipinLine;
    @BindView(R.id.shipin)
    RelativeLayout mShipin;

    @BindView(R.id.sousuo_fenlei)
    RelativeLayout mSousuoFenlei;
    @BindView(R.id.zonghe_text)
    TextView mZongheText;
    @BindView(R.id.zonghe_line)
    View mZongheLine;
    @BindView(R.id.zonghe)
    RelativeLayout mZonghe;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.yonghu_text)
    TextView mYonghuText;
    @BindView(R.id.yonghu_line)
    View mYonghuLine;
    @BindView(R.id.yonghu)
    RelativeLayout mYonghu;
    @BindView(R.id.line)
    View mLine;
    private HotSearchHuaTiAdapter mHotSearchHuaTiAdapter;
    private HotSearchShangChuanZheAdapter mHotSearchShangChuanZheAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ArrayList<String> label = new ArrayList<>();
        ArrayList<String> label2 = new ArrayList<>();
        label.add("少儿舞蹈比赛");
        label.add("少儿舞蹈比赛");
        label.add("少儿舞蹈比赛");
        label.add("少儿舞蹈比赛");

        label2.add("少儿");
        label2.add("少儿");
        label2.add("少儿");
        label2.add("少儿");
        label2.add("少儿");
        label2.add("少儿");

        getData(label);
        getShangChuah(label2);

        SetUpViewPager(mViewpager);
        EventBus.getDefault().register(this);

        mTxtSouuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    hideSoftKeyboard(HotSearchActivity.this);
                    mHuatiTitle.setVisibility(View.VISIBLE);
                    mRvHuati.setVisibility(View.VISIBLE);
                    mShangchuangzheTitle.setVisibility(View.VISIBLE);
                    mRvShangchuangzhe.setVisibility(View.VISIBLE);
                    mSousuoFenlei.setVisibility(View.GONE);
                    mViewpager.setVisibility(View.GONE);
                    mLine.setVisibility(View.GONE);
                }
            }
        });

        mTxtSouuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard(HotSearchActivity.this);
                    mHuatiTitle.setVisibility(View.GONE);
                    mRvHuati.setVisibility(View.GONE);
                    mShangchuangzheTitle.setVisibility(View.GONE);
                    mRvShangchuangzhe.setVisibility(View.GONE);
                    mSousuoFenlei.setVisibility(View.VISIBLE);
                    mViewpager.setVisibility(View.VISIBLE);
                    mLine.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });


        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mZongheText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mShipinText.setTextColor(getResources().getColor(R.color.textColor6));
                    mYonghuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mZongheLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mYonghuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mLine.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    mZongheText.setTextColor(getResources().getColor(R.color.textColor6));
                    mShipinText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mYonghuText.setTextColor(getResources().getColor(R.color.textColor6));
                    mZongheLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mShipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mYonghuLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mLine.setVisibility(View.GONE);
                } else {
                    mZongheText.setTextColor(getResources().getColor(R.color.textColor6));
                    mShipinText.setTextColor(getResources().getColor(R.color.textColor6));
                    mYonghuText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mZongheLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mYonghuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mLine.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void getData(ArrayList<String> label) {
        try {
            mHotSearchHuaTiAdapter = new HotSearchHuaTiAdapter(R.layout.lable_huati, label);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(HotSearchActivity.this, 3);
            mRvHuati.setLayoutManager(gridLayoutManager);
            mRvHuati.setAdapter(mHotSearchHuaTiAdapter);
            mHotSearchHuaTiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(HotSearchActivity.this, HuoReHuaTiActivity.class));
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void getShangChuah(ArrayList<String> label) {
        try {
            mHotSearchShangChuanZheAdapter = new HotSearchShangChuanZheAdapter(R.layout.lable_shangchuan, label);
            final LinearLayoutManager manager = new LinearLayoutManager(HotSearchActivity.this, LinearLayoutManager.HORIZONTAL, false);
            mRvShangchuangzhe.setLayoutManager(manager);
            mRvShangchuangzhe.setAdapter(mHotSearchShangChuanZheAdapter);
            mHotSearchShangChuanZheAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(HotSearchActivity.this, TaDeGeRenZhuYeActivity.class));
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void SetUpViewPager(ViewPager bookViewpager) {
        HotSearchAdapter adapter = new HotSearchAdapter(getSupportFragmentManager());
        adapter.addFragment(ZongHeFragment.newInstance(), "综合");
        adapter.addFragment(SouSuoShiPinFragment.newInstance(), "视频");
        adapter.addFragment(YongHuFragment.newInstance(), "用户");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }


    @Override
    public int setLayout() {
        return R.layout.activity_hot_search;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.cancle)
    void cancle() {
        finish();
    }


    @OnClick(R.id.zonghe)
    void zonghe() {
        mViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.shipin)
    void shipin() {
        mViewpager.setCurrentItem(1);
    }

    @OnClick(R.id.yonghu)
    void yonghu() {
        mViewpager.setCurrentItem(2);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("全部用户")) {
            mViewpager.setCurrentItem(2, true);
            mZongheText.setTextColor(getResources().getColor(R.color.textColor6));
            mShipinText.setTextColor(getResources().getColor(R.color.textColor6));
            mYonghuText.setTextColor(getResources().getColor(R.color.app_text_color));
            mZongheLine.setBackgroundColor(getResources().getColor(R.color.color_w));
            mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
            mYonghuLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
//    }
}
