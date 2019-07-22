package com.watiao.yishuproject.fragment.TaDeGeRenZhuYeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.MengWaZhuYeActivity;
import com.watiao.yishuproject.adapter.TaDeMengWaAdapter;
import com.watiao.yishuproject.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MengWaFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_xiaofeijilu)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_xiaofeijilu)
    SwipeRefreshLayout mSrlXiaofeijilu;
    Unbinder unbinder;
    private List<String> list = new ArrayList<>();
    private TaDeMengWaAdapter mTaDeMengWaAdapter;
    private boolean isFirst = true;
    public MengWaFragment() {
        // Required empty public constructor
    }

    public static MengWaFragment newInstance() {
        return new MengWaFragment();
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        list.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b6c6be44c9fdfc03fa78e5b8e43e87a9/8b82b9014a90f6030db7d4fd3312b31bb051ed01.jpg");
        list.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg");
        list.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4b5cf38a692762d09f3ea2bf90ed0849/5243fbf2b211931352ceb3196f380cd790238d8e.jpg");

        shouData(list);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tade_mengwa, null);
        return view;
    }

    @Override
    public void onRefresh() {

    }



    private void shouData(final List<String> data) {
        try {
            mTaDeMengWaAdapter = new TaDeMengWaAdapter(R.layout.item_tade_mengwa2, data);
            final GridLayoutManager manager = new GridLayoutManager(getContext(),2);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mTaDeMengWaAdapter);
            mRvRecycleview.scrollToPosition(0);
            mTaDeMengWaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(getContext(),MengWaZhuYeActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
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
