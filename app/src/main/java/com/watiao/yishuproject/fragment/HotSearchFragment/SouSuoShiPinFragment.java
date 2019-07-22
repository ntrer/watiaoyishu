package com.watiao.yishuproject.fragment.HotSearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.ShiPinXiangQingActivity;
import com.watiao.yishuproject.adapter.SouSuoShiPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.ui.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SouSuoShiPinFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private List<String> list2 = new ArrayList<>();
    private SouSuoShiPinAdapter mSouSuoShiPinAdapter;
    private boolean isFirst = true;

    public SouSuoShiPinFragment() {
        // Required empty public constructor
    }

    public static SouSuoShiPinFragment newInstance() {
        return new SouSuoShiPinFragment();
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        list2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561087471639&di=fdd49c3cc3383a474d1f94d4c0cd89d7&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201610%2F04%2F20161004135831_fCXTs.thumb.700_0.jpeg");
        list2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561087471637&di=4442331bbf36e72b57f66ff2ce8f9365&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201708%2F07%2F20170807170251_yCL8F.jpeg");
        list2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561682296&di=5e6445371eb3c826c5d651685189e0b1&imgtype=jpg&er=1&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201505%2F23%2F20150523224757_8CnZF.jpeg");
        list2.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561087601096&di=70deed1caec235f5d09c5816651356d4&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201504%2F29%2F20150429131406_2fekr.thumb.700_0.jpeg");

        shouData(list2);
    }

    private void shouData(List<String> list2) {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        mRvRecycleview.setLayoutManager(manager);
        if (isFirst) {
            mRvRecycleview.addItemDecoration(new MarginDecoration(getContext()));
            isFirst = false;
        }
        mSouSuoShiPinAdapter = new SouSuoShiPinAdapter(R.layout.item_sousuo_shipin, list2);
        mRvRecycleview.setAdapter(mSouSuoShiPinAdapter);
        mSouSuoShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ShiPinXiangQingActivity.class));
            }
        });
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_sousuoshipin, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
