package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.BiaoQianAdapter;
import com.watiao.yishuproject.adapter.ShangChengFenLeiAdapter;
import com.watiao.yishuproject.adapter.TabRecyclerViewAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.ShangChengFenLeiFragment.ShangChengFenLeiFragment;
import com.watiao.yishuproject.ui.MarginDecoration5;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShangChengFenLeiActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_fenlei)
    RecyclerView mRvFenlei;
    @BindView(R.id.fenlei)
    LinearLayout mFenlei;
    @BindView(R.id.caidanlan)
    LinearLayout mCaidanlan;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private String title;
    private List<String> list = new ArrayList<>();
    private TabRecyclerViewAdapter mTabRecyclerViewAdapter;
    private EasyPopup mCirclePop;
    private RecyclerView mRecyclerView;
    private BiaoQianAdapter mBiaoQianAdapter;
    private ImageView closeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title=getIntent().getStringExtra("title");
        mTitle.setText(title);
        list.add("精选");
        list.add("厨艺");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        list.add("分类");
        getFenLei(list);
        SetUpViewPager(mViewpager);
        int screenWidth = QMUIDisplayHelper.getScreenWidth(ShangChengFenLeiActivity.this);
        int screenHeight = QMUIDisplayHelper.getScreenHeight(ShangChengFenLeiActivity.this);
        mCirclePop = EasyPopup.create()
                .setContentView(ShangChengFenLeiActivity.this, R.layout.custom_popup)
                .setWidth(screenWidth)
                .setHeight(screenHeight/3-50)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.5f)
                .setDimColor(Color.BLACK)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();

        mRecyclerView=mCirclePop.findViewById(R.id.rv_data);
        closeView=mCirclePop.findViewById(R.id.close);
        mBiaoQianAdapter = new BiaoQianAdapter(R.layout.lable_item2, list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(ShangChengFenLeiActivity.this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new MarginDecoration5(this));
        mRecyclerView.setAdapter(mBiaoQianAdapter);
        mBiaoQianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                TextView viewById = view.findViewById(R.id.tab_name);
//                view.setBackground(getResources().getDrawable(R.drawable.layout_border5));
//                viewById.setTextColor(getResources().getColor(R.color.color_w));
                mTabRecyclerViewAdapter.setThisPosition(position);
                mTabRecyclerViewAdapter.notifyDataSetChanged();
                mRvFenlei.scrollToPosition(position);
                mViewpager.setCurrentItem(position);
                mCirclePop.dismiss();
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
            }
        });

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabRecyclerViewAdapter.setThisPosition(position);
                mTabRecyclerViewAdapter.notifyDataSetChanged();
                mRvFenlei.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void getFenLei(List<String> list) {
        try {
            mTabRecyclerViewAdapter = new TabRecyclerViewAdapter(R.layout.tab_items, list);
            mTabRecyclerViewAdapter.setThisPosition(0);
            final LinearLayoutManager manager = new LinearLayoutManager(ShangChengFenLeiActivity.this, LinearLayoutManager.HORIZONTAL, false);
            mRvFenlei.setLayoutManager(manager);
            mRvFenlei.setAdapter(mTabRecyclerViewAdapter);
            mTabRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mTabRecyclerViewAdapter.setThisPosition(position);
                    mTabRecyclerViewAdapter.notifyDataSetChanged();
                    mRvFenlei.scrollToPosition(position);
                    mViewpager.setCurrentItem(position);
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void SetUpViewPager(ViewPager bookViewpager) {
        ShangChengFenLeiAdapter adapter = new ShangChengFenLeiAdapter(getSupportFragmentManager());
        for (int i = 0; i < list.size(); i++) {
            adapter.addFragment(ShangChengFenLeiFragment.newInstance(list.get(i)), list.get(i));
        }
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }

    @OnClick(R.id.fenlei)
    void fenlei() {
        mCirclePop.showAtAnchorView(mToolbar, YGravity.BELOW, XGravity.CENTER, 0, 0);
    }



    @OnClick(R.id.search)
    void search() {
       startActivity(new Intent(ShangChengFenLeiActivity.this,ShangChengSouSuoActivity.class));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shang_cheng_fen_lei;
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


}
