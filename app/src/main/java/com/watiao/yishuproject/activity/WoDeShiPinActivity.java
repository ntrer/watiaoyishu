package com.watiao.yishuproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.codingending.popuplayout.PopupLayout;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.WoDeshipinAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Blur.BlurDialog;
import com.watiao.yishuproject.utils.Blur.BlurView;
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

public class WoDeShiPinActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener,BaseSectionQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.menu)
    ImageView mMenu;
    @BindView(R.id.shanchu)
    TextView mShanchu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    private WoDeshipinAdapter mWoDeshipinAdapter;
    private List<Test.DataListBean> customerList = new ArrayList<>();
    private List<Test.DataListBean> selectDatas = new ArrayList<>();
    private List<Integer> position2 = new ArrayList<>();
    private View bottomMenu;
    private PopupLayout mPopupLayout;
    private RelativeLayout mShanchuMenu,mQuxiao;
    private boolean isShanchu=false;
    private AlertDialog dlg;
    private BlurView mBlurView;
    private BlurDialog mBlurDialog;
    private Dialog mDialog;
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
        getData();
        dlg = new AlertDialog.Builder(this).create();
        mDialog=new Dialog(this){
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                TextView tv = new TextView(WoDeShiPinActivity.this);
                tv.setGravity(Gravity.TOP);
                tv.setBackgroundColor(0x7E7D7D);
                setContentView(tv, new ViewGroup.LayoutParams(1, 1));
            }
        };
        BlurDialog(WoDeShiPinActivity.this);
        bottomMenu=View.inflate(WoDeShiPinActivity.this,R.layout.bottom_layout2,null);
        mPopupLayout=PopupLayout.init(WoDeShiPinActivity.this,bottomMenu);
        mShanchuMenu=bottomMenu.findViewById(R.id.shanchu);
        mQuxiao=bottomMenu.findViewById(R.id.quxiao);
        mPopupLayout.setUseRadius(true);
        mPopupLayout.setMenu(true);
        mShanchuMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShanchu=!isShanchu;
                mMenu.setVisibility(View.GONE);
                mShanchu.setVisibility(View.VISIBLE);
                mPopupLayout.dismiss();
                if(mBlurView!=null){
                    mBlurView.hide();
                }
                mWoDeshipinAdapter.changetShowDelImage(isShanchu);
            }
        });

        mQuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.setVisibility(View.VISIBLE);
                mShanchu.setVisibility(View.GONE);
                mPopupLayout.dismiss();
                if(mBlurView!=null){
                    mBlurView.hide();
                }
            }
        });

        mPopupLayout.setDismissListener(new PopupLayout.DismissListener() {
            @Override
            public void onDismiss() {
                if(mBlurView!=null){
                    mBlurView.hide();
                }
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_wo_de_shi_pin;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.menu)
    void menu(){
        if(mBlurView != null) {
            mBlurView.blur();
            mBlurView.show();
        }
        mPopupLayout.show(PopupLayout.POSITION_BOTTOM);
    }


    @OnClick(R.id.shanchu)
    void shanchu(){
        if(mBlurView != null) {
            mBlurView.blur();
            mBlurView.show();
        }
        ShowDialog2();
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
                            customerList = yiXiangJin.getDataList();
                            if (customerList.size() != 0) {
                                shouData(customerList);

                            } else {
                                shouData(customerList);

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
            mWoDeshipinAdapter = new WoDeshipinAdapter(R.layout.item_wodeshipin, data);
            final GridLayoutManager manager = new GridLayoutManager(WoDeShiPinActivity.this,2);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mWoDeshipinAdapter);
            mWoDeshipinAdapter.setOnLoadMoreListener(this,mRvRecycleview);
            mWoDeshipinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!mWoDeshipinAdapter.isSelected.get(position)) {
                        mWoDeshipinAdapter.isSelected.put(position, true); // 修改map的值保存状态
                        mWoDeshipinAdapter.notifyItemChanged(position);
                        selectDatas.add(data.get(position));
                    } else {
                        mWoDeshipinAdapter.isSelected.put(position, false); // 修改map的值保存状态
                        mWoDeshipinAdapter.notifyItemChanged(position);
                        selectDatas.remove(data.get(position));
                    }
                }
            });


            mWoDeshipinAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.checkbox) {
                        if (!mWoDeshipinAdapter.isSelected.get(position)) {
                            mWoDeshipinAdapter.isSelected.put(position, true); // 修改map的值保存状态
                            mWoDeshipinAdapter.notifyItemChanged(position);
                            selectDatas.add(data.get(position));

                        } else {
                            mWoDeshipinAdapter.isSelected.put(position, false); // 修改map的值保存状态
                            mWoDeshipinAdapter.notifyItemChanged(position);
                            selectDatas.remove(data.get(position));
                        }

                        Log.d("asdasdad",selectDatas.toString());
                    }
                }
            });

        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    private void ShowDialog2() {
        dlg.show();
        dlg.setContentView(R.layout.dialog_layout2);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        TextView title=dlg.findViewById(R.id.title);
        title.setText("删除萌娃视频后相关数据会清除  确定删除吗？");
        Button btn1 = (Button) dlg.findViewById(R.id.sure);
        Button btn2 = (Button) dlg.findViewById(R.id.cancle);
        btn1.setText("确定");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if(mBlurView!=null){
                    mBlurView.hide();
                }
                mDialog.dismiss();
                mShanchu.setVisibility(View.GONE);
                mMenu.setVisibility(View.VISIBLE);
                isShanchu=false;
                mWoDeshipinAdapter.changetShowDelImage(isShanchu);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if(mBlurView!=null){
                    mBlurView.hide();
                }
                mDialog.dismiss();
                mShanchu.setVisibility(View.GONE);
                mMenu.setVisibility(View.VISIBLE);
                isShanchu=false;
                mWoDeshipinAdapter.changetShowDelImage(isShanchu);
            }
        });

        dlg.setCancelable(false);
    }


    private void BlurDialog(Activity context){
        ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
        mBlurView = decorView.findViewById(R.id.blur_dialog_bg);
        if(mBlurView == null){
            mBlurView = new BlurView(context);
            mBlurView.setId(R.id.blur_dialog_bg);
            mBlurView.setAlpha(0f);
            decorView.addView(mBlurView, new ViewGroup.LayoutParams(-1,-1));
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
                    mWoDeshipinAdapter.loadMoreComplete();
                    mWoDeshipinAdapter.loadMoreEnd();
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
                                mWoDeshipinAdapter.loadMoreComplete();
                                mWoDeshipinAdapter.loadMoreEnd();
                            } else if (test.getDataList().size() != 0) {
                                List<Test.DataListBean> userSaleInfo = test.getDataList();
                                LoadMoreData(userSaleInfo);
                                Log.d("33333333333", response);
                            } else if (test.getDataList().size() == 0) {
                                mWoDeshipinAdapter.loadMoreComplete();
                                mWoDeshipinAdapter.loadMoreEnd();
                            }
                        } else {
                            mWoDeshipinAdapter.loadMoreComplete();
                            mWoDeshipinAdapter.loadMoreEnd();
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
            mWoDeshipinAdapter.addData(data);
            mWoDeshipinAdapter.loadMoreComplete();
        }

    }
}
