package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.NianLingAdapter;
import com.watiao.yishuproject.adapter.XuanzeHaiZiAdapter;
import com.watiao.yishuproject.adapter.XuanzeHaiZiAdapter2;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.library.SmoothCheckBox;
import okhttp3.Call;

public class BaoMingXuanZeActivity extends BaseActivity {

    private static final int REQUSET_LAOSHI = 1015;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.zonge)
    TextView mZonge;
    @BindView(R.id.zonge_biaozhi)
    TextView mZongeBiaozhi;
    @BindView(R.id.zonge_money)
    TextView mZongeMoney;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.rl_mengwa)
    RelativeLayout mRlMengwa;
    @BindView(R.id.tianjia)
    ImageView mTianjia;
    @BindView(R.id.xuanzemengwa)
    RelativeLayout mXuanzemengwa;
    @BindView(R.id.rv_mengwa)
    RecyclerView mRvMengwa;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.zhankai)
    RelativeLayout mZhankai;
    @BindView(R.id.rv_mengwa2)
    RecyclerView mRvMengwa2;
    @BindView(R.id.nianling)
    RelativeLayout mNianling;
    @BindView(R.id.nianlingduan)
    TextView mNianlingduan;
    @BindView(R.id.rv_nianling)
    RecyclerView mRvNianling;
    @BindView(R.id.line2)
    View mLine2;
    @BindView(R.id.arrow)
    ImageView mArrow;
    @BindView(R.id.zhidaolaoshi)
    TextView mZhidaolaoshi;
    @BindView(R.id.laoshi)
    RelativeLayout mLaoshi;
    @BindView(R.id.weixin_pic)
    ImageView mWeixinPic;
    @BindView(R.id.checkbox1)
    SmoothCheckBox mCheckbox1;
    @BindView(R.id.weixin)
    RelativeLayout mWeixin;
    @BindView(R.id.zhifubao_pic)
    ImageView mZhifubaoPic;
    @BindView(R.id.checkbox2)
    SmoothCheckBox mCheckbox2;
    @BindView(R.id.zhifubao)
    RelativeLayout mZhifubao;
    @BindView(R.id.zhifu)
    LinearLayout mZhifu;
    @BindView(R.id.checkbox)
    SmoothCheckBox mCheckbox;
    @BindView(R.id.queren)
    RelativeLayout mQueren;
    @BindView(R.id.zhankaijiantou)
    ImageView mZhankaijiantou;
    private XuanzeHaiZiAdapter mXuanzeHaiZiAdapter;
    private XuanzeHaiZiAdapter2 mXuanzeHaiZiAdapter2;
    private List<Test.DataListBean> dataList = new ArrayList<>();
    private List<Test.DataListBean> dataList2 = new ArrayList<>();
    private List<Test.DataListBean> dataList3 = new ArrayList<>();
    private List<String> nianling = new ArrayList<>();
    private int rows = 0;
    private ScaleAnimation animation, animation2;
    private boolean isExpand = false;
    private List<Test.DataListBean> selectDatas = new ArrayList<>();
    private NianLingAdapter mNianLingAdapter;
    private boolean isChecked = false;
    private boolean isWeixin, isZhifubao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
        nianling.add("10-11");
        nianling.add("12-13");
        nianling.add("14-15");
        nianling.add("16-17");
        nianling.add("18-19");
//        getData(rows);

        getNianLing(nianling);
    }

    private void getNianLing(List<String> nianling) {
        mNianLingAdapter = new NianLingAdapter(R.layout.nianling_item, nianling);
        mNianLingAdapter.setThisPosition(0);
        final LinearLayoutManager manager = new LinearLayoutManager(BaoMingXuanZeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRvNianling.setLayoutManager(manager);
        mRvNianling.setAdapter(mNianLingAdapter);
        mNianLingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mNianLingAdapter.setThisPosition(position);
                mNianLingAdapter.notifyDataSetChanged();
                mRvNianling.scrollToPosition(position);
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_bao_ming_xuan_ze;
    }


    @OnClick(R.id.login)
    void baoming() {
        startActivity(new Intent(BaoMingXuanZeActivity.this, BaoMingJIeGuoActivity.class));
    }

    @OnClick(R.id.laoshi)
    void laoshi() {
        Intent intent = new Intent(BaoMingXuanZeActivity.this, XuanZeLaoShiActivity.class);
        startActivityForResult(intent, REQUSET_LAOSHI);
    }


    @OnClick(R.id.queren)
    void queren() {
        isChecked = !isChecked;
        mCheckbox.setChecked(isChecked);
    }

    @OnClick(R.id.weixin)
    void weixin() {
        mCheckbox1.setChecked(true);
        mCheckbox2.setChecked(false);
    }

    @OnClick(R.id.zhifubao)
    void zhifubao() {
        mCheckbox1.setChecked(false);
        mCheckbox2.setChecked(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET_LAOSHI && resultCode == RESULT_OK) {
            mZhidaolaoshi.setText("张老师(id:34566)");
        }
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.tianjia)
    void tianjia() {
        startActivity(new Intent(BaoMingXuanZeActivity.this, MengWaZiLiaoActivity.class));
        rows++;
        if (rows > 8) {
            mLine.setVisibility(View.GONE);
            mZhankai.setVisibility(View.VISIBLE);
            mZhankaijiantou.setImageResource(R.mipmap.zhankaisanjiao);
            getData2(rows);
        } else {
            getData(rows);
        }
    }


    private void getData2(int rows) {
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=2&page=1&rows=" + rows;
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
                if (response != null) {
                    try {
                        Test yiXiangJin = JSONUtil.fromJson(response, Test.class);
                        if (yiXiangJin.getRet().equals("200")) {
                            dataList2 = yiXiangJin.getDataList();
                            if (dataList2.size() != 0) {
                                shouData2(dataList2);

                            } else {
                                shouData2(dataList2);

                            }
                        }
                    } catch (Exception e) {
                        Log.d("出错了", e.toString());
                    }
                }

            }
        });
    }

    private void shouData2(List<Test.DataListBean> dataList2) {
        dataList3.clear();
        for (int i = 0; i < dataList2.size(); i++) {
            if (i > 7) {
                dataList3.add(dataList2.get(i));
            }
        }
        try {
            mXuanzeHaiZiAdapter2 = new XuanzeHaiZiAdapter2(R.layout.item_xuanzehaizi, dataList3);
            final GridLayoutManager manager = new GridLayoutManager(this, 4);
            mRvMengwa2.setLayoutManager(manager);
            mRvMengwa2.setNestedScrollingEnabled(false);
            mRvMengwa2.setAdapter(mXuanzeHaiZiAdapter2);
            mXuanzeHaiZiAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!mXuanzeHaiZiAdapter2.isSelected.get(position)) {
                        mXuanzeHaiZiAdapter2.isSelected.put(position, true); // 修改map的值保存状态
                        mXuanzeHaiZiAdapter2.notifyItemChanged(position);
                        selectDatas.add(dataList3.get(position));
                    } else {
                        mXuanzeHaiZiAdapter2.isSelected.put(position, false); // 修改map的值保存状态
                        mXuanzeHaiZiAdapter2.notifyItemChanged(position);
                        selectDatas.remove(dataList3.get(position));
                    }
                    Log.d("asdasdad", selectDatas.toString());
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.zhankai)
    void zhankai() {
        if (!isExpand) {
//            mRvMengwa2.startAnimation(animation);
            mRvMengwa2.setVisibility(View.VISIBLE);
            mZhankaijiantou.setImageResource(R.mipmap.shouhui);
            isExpand = true;
        } else {
//            mRvMengwa2.startAnimation(animation2);
            mRvMengwa2.setVisibility(View.GONE);
            mZhankaijiantou.setImageResource(R.mipmap.zhankaisanjiao);
            isExpand = false;
        }
    }

    private void getData(int rows) {
        String url = "http://app.ejjzcloud.com/clueController.do?method=getAllClues&token_id=" + BaseUrl.Token + "&loginOS=2&page=1&rows=" + rows;
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
            selectDatas.clear();
            mXuanzeHaiZiAdapter = new XuanzeHaiZiAdapter(R.layout.item_xuanzehaizi, data);
            final GridLayoutManager manager = new GridLayoutManager(this, 4);
            mRvMengwa.setLayoutManager(manager);
            mRvMengwa.setNestedScrollingEnabled(false);
            mRvMengwa.setAdapter(mXuanzeHaiZiAdapter);
            selectDatas.add(data.get(0));
            mXuanzeHaiZiAdapter.isSelected.put(0, true);
            mXuanzeHaiZiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (!mXuanzeHaiZiAdapter.isSelected.get(position)) {
                        mXuanzeHaiZiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                        mXuanzeHaiZiAdapter.notifyItemChanged(position);
                        selectDatas.add(data.get(position));
                    } else {
                        mXuanzeHaiZiAdapter.isSelected.put(position, false); // 修改map的值保存状态
                        mXuanzeHaiZiAdapter.notifyItemChanged(position);
                        selectDatas.remove(data.get(position));
                    }
                    Log.d("asdasdad", selectDatas.toString());
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("报名成功")) {
            finish();
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


}
