package com.watiao.yishuproject.fragment.SaiShiFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.CityPickerActivity;
import com.watiao.yishuproject.activity.SaiShiRiQiSaiXuanActivity;
import com.watiao.yishuproject.activity.SaiShiSouSuoActivity;
import com.watiao.yishuproject.adapter.BiaoQianAdapter;
import com.watiao.yishuproject.adapter.SaiShiAdapter;
import com.watiao.yishuproject.adapter.TabRecyclerViewAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.MessageEvent4;
import com.watiao.yishuproject.ui.MarginDecoration5;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class SaiShiFragment extends BaseFragment {

    private static final int REQUEST_CODE_LOCATION = 2002;
    @BindView(R.id.dingwei)
    TextView mDingwei;
    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.riqi)
    ImageView mRiqi;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.caidanlan)
    LinearLayout mCaidanlan;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    Unbinder unbinder1;
    @BindView(R.id.rv_fenlei)
    RecyclerView mRvFenlei;
    @BindView(R.id.fenlei)
    LinearLayout mFenlei;
    private TabRecyclerViewAdapter mTabRecyclerViewAdapter;
    private List<String> list = new ArrayList<>();
    private EasyPopup mCirclePop;
    private RecyclerView mRecyclerView;
    private BiaoQianAdapter mBiaoQianAdapter;
    private ImageView closeView;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
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
        int screenWidth = QMUIDisplayHelper.getScreenWidth(getContext());
        int screenHeight = QMUIDisplayHelper.getScreenHeight(getContext());
        mCirclePop = EasyPopup.create()
                .setContentView(getContext(), R.layout.custom_popup)
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
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new MarginDecoration5(getContext()));
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
                Log.d("asasdadaddasdasdadad",positionOffsetPixels+"");
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
        EventBus.getDefault().register(this);
    }

    private void getFenLei(List<String> list) {
        try {
            mTabRecyclerViewAdapter = new TabRecyclerViewAdapter(R.layout.tab_items, list);
            mTabRecyclerViewAdapter.setThisPosition(0);
            final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_saishi, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void SetUpViewPager(ViewPager bookViewpager) {
        SaiShiAdapter adapter = new SaiShiAdapter(getChildFragmentManager());
        adapter.addFragment(JIngXuanFragment.newInstance(), "精选");
        for (int i=0;i<list.size()-1;i++){
            adapter.addFragment(FenLeiFragment.newInstance("fenlei"), "厨艺");
        }
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }


    @OnClick(R.id.search)
    void search() {
        Intent intent = new Intent(getContext(), SaiShiSouSuoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.dingwei)
    void dingwei() {
        Intent intent = new Intent(getContext(), CityPickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOCATION);
    }


    @OnClick(R.id.riqi)
    void riqi() {
        Intent intent = new Intent(getContext(), SaiShiRiQiSaiXuanActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOCATION);
    }


    @OnClick(R.id.fenlei)
    void fenlei() {
        mCirclePop.showAtAnchorView(mToolbar, YGravity.BELOW, XGravity.CENTER, 0, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_LOCATION&&resultCode==RESULT_OK){
            mDingwei.setText(data.getStringExtra("city"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky=true)
    public void Event(MessageEvent4 messageEvent) {
        if (messageEvent.getMessage().equals("定位")) {
            mDingwei.setText(messageEvent.getPrice());
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
