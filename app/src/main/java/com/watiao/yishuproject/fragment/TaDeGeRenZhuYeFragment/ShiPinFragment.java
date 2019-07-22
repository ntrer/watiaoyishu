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
import com.watiao.yishuproject.activity.ShiPinXiangQingActivity;
import com.watiao.yishuproject.adapter.TaDeShiPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.CommonItemDecoration2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShiPinFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_xiaofeijilu)
    RecyclerView mRvRecycleview;
//    @BindView(R.id.srl_xiaofeijilu)
//    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    private  List<Test.DataListBean> dataList =new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private TaDeShiPinAdapter mTaDeShiPinAdapter;
    public ShiPinFragment() {
        // Required empty public constructor
    }

    public static ShiPinFragment newInstance() {
        return new ShiPinFragment();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
//        mSrlRefresh.setOnRefreshListener(this);
//        getData();
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        list.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b6c6be44c9fdfc03fa78e5b8e43e87a9/8b82b9014a90f6030db7d4fd3312b31bb051ed01.jpg");
        list.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg");
        list.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4b5cf38a692762d09f3ea2bf90ed0849/5243fbf2b211931352ceb3196f380cd790238d8e.jpg");
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=8f4dee5d336d55fbdac670265d234f40/96dda144ad3459821b6d671102f431adcaef844a.jpg");
        list.add("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f3812a96b2315c605c956defbdb0cbe6/a5c27d1ed21b0ef408aa1eddd3c451da80cb3ef6.jpg");
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=a1969894da2a28345ca6300b6bb4c92e/e61190ef76c6a7ef0fd420aff3faaf51f2de664d.jpg");
        list.add("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=907f6e689ddda144c5096ab282b6d009/dc54564e9258d1092f7663c9db58ccbf6c814d30.jpg");

        shouData(list);

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tade_shipin, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


//    private void getData() {
////        mSrlRefresh.setRefreshing(true);
//        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id=c4ddac70eb5a47e68bce76137d22c08a&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
//        Log.d("创建活动", url);
//        HashMap<String, String> paramsMap = new HashMap<>();
//        OkhttpUtil.okHttpPost(url, paramsMap, new CallBackUtil.CallBackString() {
//            @Override
//            public void onFailure(Call call, Exception e) {
//                ToastUtils.showLong(e.toString());
//            }
//
//            @Override
//            public void onResponse(String response) {
//                Log.d("创建活动", response);
////                mSrlRefresh.setRefreshing(false);
//                if (response != null) {
//                    try {
//                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
//                        if (yiXiangJin.getRet().equals("200")) {
//                            dataList = yiXiangJin.getDataList();
//                            if (dataList.size() != 0) {
//                                shouData(dataList);
//
//                            } else {
//                                shouData(dataList);
//
//                            }
//                        }
//                    } catch (Exception e) {
//                        Log.d("出错了", e.toString());
//                    }
//                }
//
//            }
//        });
//    }


    private void shouData(final List<String> data) {
        try {
            mTaDeShiPinAdapter = new TaDeShiPinAdapter(R.layout.item_tade_shipin, data);
            final GridLayoutManager manager = new GridLayoutManager(getContext(),3);
            mRvRecycleview.addItemDecoration(new CommonItemDecoration2(6,6));
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mTaDeShiPinAdapter);
            mRvRecycleview.scrollToPosition(0);
            mTaDeShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(getContext(),ShiPinXiangQingActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {

    }
}
