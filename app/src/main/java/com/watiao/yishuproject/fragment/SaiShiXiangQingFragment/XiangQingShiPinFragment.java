package com.watiao.yishuproject.fragment.SaiShiXiangQingFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.AppContext;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.BaoBeiXiangQingActivity;
import com.watiao.yishuproject.activity.BaoMingXuanZeActivity;
import com.watiao.yishuproject.activity.GengDuoShiPinActivity;
import com.watiao.yishuproject.adapter.GengDuoShiPinAdapter;
import com.watiao.yishuproject.base.BaseFragment;
import com.watiao.yishuproject.base.BaseUrl;
import com.watiao.yishuproject.bean.Test;
import com.watiao.yishuproject.ui.MarginDecoration;
import com.watiao.yishuproject.utils.Json.JSONUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.CallBackUtil;
import com.watiao.yishuproject.utils.OkhttpUtils.OkhttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

public class XiangQingShiPinFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout mSrlRefresh;
    Unbinder unbinder;
    @BindView(R.id.more)
    Button mMore;
    Unbinder unbinder1;
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.day)
    TextView mDays;
    @BindView(R.id.tian)
    TextView mTian;
    @BindView(R.id.hour)
    TextView mHours;
    @BindView(R.id.maohao1)
    TextView mMaohao1;
    @BindView(R.id.min)
    TextView mMins;
    @BindView(R.id.maohao2)
    TextView mMaohao2;
    @BindView(R.id.sec)
    TextView mSec;
    @BindView(R.id.daojishi)
    RelativeLayout mDaojishi;
    @BindView(R.id.login)
    Button mLogin;
    Unbinder unbinder2;
    private Dialog mRequestDialog,dialog;

    private GengDuoShiPinAdapter mGengDuoShiPinAdapter;
    private List<Test.DataListBean> dataList = new ArrayList<>();

    private long mDay = 23;// 天
    private long mHour = 11;//小时,
    private long mMin = 56;//分钟,
    private long mSecond = 32;//秒

    private Timer mTimer;

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                mDays.setText(mDay + "");//天数不用补位
                mHours.setText(getTv(mHour));
                mMins.setText(getTv(mMin));
                mSec.setText(getTv(mSecond));
                if (mSecond == 0 && mDay == 0 && mHour == 0 && mMin == 0) {
                    mTimer.cancel();
                }
            }
        }
    };

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }


    public XiangQingShiPinFragment() {
        // Required empty public constructor
    }

    public static XiangQingShiPinFragment newInstance() {
        return new XiangQingShiPinFragment();
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        mSrlRefresh.setOnRefreshListener(this);
        mTimer = new Timer();
        mRequestDialog=creatRequestDialog2(getContext(),"投票中...");
        mRequestDialog.setCancelable(false);
        getData();
        startRun();
        mProgress.setMax(100);
        mProgress.setProgress(50);
    }


    private void getData() {
        mSrlRefresh.setRefreshing(true);
        String url = "http://app.ejjzcloud.com/clueController.do?method=getMyClues&token_id=" + BaseUrl.Token + "&orderType=1&sort=desc&loginOS=2&page=1&rows=10";
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
            final GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.addItemDecoration(new MarginDecoration(getContext()));
            mRvRecycleview.setNestedScrollingEnabled(false);
            mRvRecycleview.setAdapter(mGengDuoShiPinAdapter);
            mGengDuoShiPinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent=new Intent(getContext(),BaoBeiXiangQingActivity.class);
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


    @OnClick(R.id.more)
    void more() {
        startActivity(new Intent(getContext(), GengDuoShiPinActivity.class));
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shipin, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


    /**
     * 开启倒计时
     * //time为Date类型：在指定时间执行一次。
     * timer.schedule(task, time);
     * //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
     * timer.schedule(task, firstTime,period);
     * //delay 为long类型：从现在起过delay毫秒执行一次。
     * timer.schedule(task, delay);
     * //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
     * timer.schedule(task, delay,period);
     */
    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                    if (mDay < 0) {
                        // 倒计时结束
                        mDay = 0;
                        mHour = 0;
                        mMin = 0;
                        mSecond = 0;
                    }
                }
            }
        }
    }

    @OnClick(R.id.login)
    void baoming() {
        startActivity(new Intent(getContext(), BaoMingXuanZeActivity.class));
    }


    public  Dialog creatRequestDialog2(final Context context, String tip) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        timeHandler.removeCallbacksAndMessages(null);
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    @Override
    public void onRefresh() {
    getData();
    }
}
