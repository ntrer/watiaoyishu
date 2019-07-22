package com.watiao.yishuproject.fragment.HotSearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.ShiPinXiangQingActivity;
import com.watiao.yishuproject.activity.TaDeGeRenZhuYeActivity;
import com.watiao.yishuproject.adapter.ZongHeSouSuoAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class ZongHeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private ZongHeSouSuoAdapter mZongHeSouSuoAdapter;
   private View header;
   private RelativeLayout mtitle;
   private CircleImageView mavatar;
   private TextView mname,mId;
   private Button mGuanzhu;
   private boolean isFirst=true;
   private RelativeLayout mRelativeLayout;
    public ZongHeFragment() {
        // Required empty public constructor
    }

    public static ZongHeFragment newInstance() {
        return new ZongHeFragment();
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mSrlRefresh.setOnRefreshListener(this);
        getData();
        getHeader();
    }

    private void getHeader() {
        header = View.inflate(getContext(), R.layout.zonghe_header, null);
        mtitle=header.findViewById(R.id.title);
        mRelativeLayout=header.findViewById(R.id.content);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),TaDeGeRenZhuYeActivity.class);
                startActivity(intent);
            }
        });
        mname=header.findViewById(R.id.name);
        mId=header.findViewById(R.id.id);
        mGuanzhu=header.findViewById(R.id.jiagaunzhu);
        mtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("全部用户"));
            }
        });

    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_zonghe, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id="+BaseUrl.Token+"&loginOS=2&page=1&rows=10";
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
            if(!isFirst){
                mZongHeSouSuoAdapter.removeAllHeaderView();
                isFirst=true;
            }
            mZongHeSouSuoAdapter = new ZongHeSouSuoAdapter(R.layout.item_zongheshipin, data);
            final LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mZongHeSouSuoAdapter);
            if(isFirst){
                mZongHeSouSuoAdapter.addHeaderView(header);
                isFirst=false;
            }
            mZongHeSouSuoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if(view.getId()==R.id.pic){
                        Intent intent=new Intent(getContext(),ShiPinXiangQingActivity.class);
                        startActivity(intent);
                    }
                    else if(view.getId()==R.id.user_pic){
                        Intent intent=new Intent(getContext(),TaDeGeRenZhuYeActivity.class);
                        startActivity(intent);
                    }
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
        getData();
    }
}
