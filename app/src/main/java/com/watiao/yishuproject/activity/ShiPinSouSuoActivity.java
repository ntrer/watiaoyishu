package com.watiao.yishuproject.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.watiao.yishuproject.AppContext;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.GengDuoShiPinAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
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

public class ShiPinSouSuoActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener ,BaseSectionQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.txt_souuo)
    AppCompatEditText mTxtSouuo;
    @BindView(R.id.search_title)
    RelativeLayout mSearchTitle;
    @BindView(R.id.cancle)
    TextView mCancle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sousuojieguo)
    TextView mSousuojieguo;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private Dialog mRequestDialog,dialog;
    private int page = 1;
    private boolean isFirst=true;
    private GengDuoShiPinAdapter mGengDuoShiPinAdapter;
    private List<Test.DataListBean> dataList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mTxtSouuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    hideSoftKeyboard(ShiPinSouSuoActivity.this);
                    getData();
                    return true;
                }
                return false;
            }
        });
        mSrlRefresh.setOnRefreshListener(this);
        mRequestDialog=creatRequestDialog2(ShiPinSouSuoActivity.this,"投票中...");
        mRequestDialog.setCancelable(false);
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
            mGengDuoShiPinAdapter = new GengDuoShiPinAdapter(R.layout.item_gengduoshipin, data);
            final GridLayoutManager manager = new GridLayoutManager(ShiPinSouSuoActivity.this,2);
            if(isFirst){
                mRvRecycleview.addItemDecoration(new MarginDecoration(this));
                isFirst=false;
            }
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mGengDuoShiPinAdapter);
            mGengDuoShiPinAdapter.setOnLoadMoreListener(this,mRvRecycleview);
            mGengDuoShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(ShiPinSouSuoActivity.this,BaoBeiXiangQingActivity.class);
                    startActivity(intent);
                }
            });
            mGengDuoShiPinAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int viewId=view.getId();
                    if(viewId==R.id.toupiao){
                        mRequestDialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mRequestDialog != null && mRequestDialog.isShowing()) {
                                    mRequestDialog.dismiss();
                                    ToastUtils.showLong("投票成功");
                                }
                            }
                        }, 2000);
                    }
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.cancle)
    void cancle(){
        finish();
    }


    @Override
    public int setLayout() {
        return R.layout.activity_shi_pin_sou_suo;
    }

    @Override
    public void init() {

    }

    @Override
    public void onRefresh() {
        page=1;
        getData();
    }

    public Dialog creatRequestDialog2(final Context context, String tip) {
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.alert_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int width = AppContext.getInstance().getDisplayWidth();
        lp.width = (int) (0.3 * width);
        lp.height = (int) (0.3 * width);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvLoad);
        tvTitle.setText(tip);


        return dialog;
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
                    mGengDuoShiPinAdapter.loadMoreComplete();
                    mGengDuoShiPinAdapter.loadMoreEnd();
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
                                mGengDuoShiPinAdapter.loadMoreComplete();
                                mGengDuoShiPinAdapter.loadMoreEnd();
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mGengDuoShiPinAdapter.loadMoreComplete();
                                mGengDuoShiPinAdapter.loadMoreEnd();
                            }
                        } else {
                            mGengDuoShiPinAdapter.loadMoreComplete();
                            mGengDuoShiPinAdapter.loadMoreEnd();
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
            mGengDuoShiPinAdapter.addData(data);
            dataList.addAll(data);
            mGengDuoShiPinAdapter.loadMoreComplete();
        }

    }
}
