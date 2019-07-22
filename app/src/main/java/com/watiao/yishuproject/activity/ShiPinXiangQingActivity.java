package com.watiao.yishuproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.aliyun.apsara.alivclittlevideo.constants.AlivcLittleHttpConfig;
import com.aliyun.apsara.alivclittlevideo.net.HttpEngine;
import com.aliyun.apsara.alivclittlevideo.net.LittleHttpManager;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleHttpResponse;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleUserInfo;
import com.aliyun.apsara.alivclittlevideo.sts.OnStsResultListener;
import com.aliyun.apsara.alivclittlevideo.sts.StsInfoManager;
import com.aliyun.apsara.alivclittlevideo.sts.StsTokenInfo;
import com.aliyun.apsara.alivclittlevideo.utils.Common;
import com.aliyun.apsara.alivclittlevideo.view.mine.AlivcLittleUserManager;
import com.aliyun.apsara.alivclittlevideo.view.publish.AlivcVideoPublishView;
import com.aliyun.apsara.alivclittlevideo.view.video.AlivcVideoPlayView;
import com.aliyun.apsara.alivclittlevideo.view.video.OnStsInfoExpiredListener;
import com.aliyun.apsara.alivclittlevideo.view.video.videolist.AlivcVideoListView;
import com.aliyun.video.common.utils.ToastUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.utils.PreferencesUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShiPinXiangQingActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView mBack;
    private LinearLayoutManager layoutManager;
    private AlivcVideoPlayView videoPlayView;
    private AlivcVideoPublishView mAlivcVideoPublishView;
    /**
     * 系统授权设置的弹框
     */
    AlertDialog openAppDetDialog;

    /**
     * 视频合成输出文件名称
     */
    private String mComposeFileName = "output_compose_video.mp4";

    /**
     * assets目录文件拷贝工具类
     */
    private Common commonUtils;
    /**
     * 当前请求的视频列表最后数据的主键id，用于查询下一页数据
     */
    private int mLastVideoId = -1;
    /**
     * 数据请求是否为加载更多数据
     */
    private boolean isLoadMoreData = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        setContentView(R.layout.activity_shi_pin_xiang_qing);
        ButterKnife.bind(this);
        //初始化页面
        initView();

        // 复制加密证书到sdcard，在播放加密视频的时候需要
        copyAssets();


        loadPlayList(mLastVideoId);
        PreferencesUtils.putString(ShiPinXiangQingActivity.this, "name", null);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (PreferencesUtils.getString(ShiPinXiangQingActivity.this, "name") != null) {
            String name = PreferencesUtils.getString(ShiPinXiangQingActivity.this, "name");
            videoPlayView.setName(name);
        }
        videoPlayView.rePlay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 复制文件
     */
    private void copyAssets() {
        commonUtils = Common.getInstance(getApplicationContext()).copyAssetsToSD("encrypt", "aliyun");
        commonUtils.setFileOperateCallback(

                new Common.FileOperateCallback() {
                    @Override
                    public void onSuccess() {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save/");
                        if (!file.exists()) {
                            file.mkdir();
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        Log.e("Test", "unZip fail..");
                    }
                });
    }

    /**
     * 初始化View
     */
    protected void initView() {
        videoPlayView = findViewById(com.aliyun.apsara.alivclittlevideo.R.id.video_play);
        videoPlayView.setOnRefreshDataListener(new MyRefreshDataListener(this));
        videoPlayView.setOnStsInfoExpiredListener(new OnStsInfoExpiredListener() {
            @Override
            public void onTimeExpired() {
                //刷新获取STS
                StsInfoManager.getInstance().refreshStsToken(new MyStsResultListener(ShiPinXiangQingActivity.this));
            }

            @Override
            public StsTokenInfo refreshSts() {
                return StsInfoManager.getInstance().refreshStsToken();
            }
        });


    }



    /**
     * 获取播放列表数据
     *
     * @param id 请求第pageNo页数据
     */
    private void loadPlayList(final int id) {
        LittleUserInfo userInfo = AlivcLittleUserManager.getInstance().getUserInfo(this);
        if (userInfo == null) {
            Toast.makeText(this, "R.string.alivc_little_no_user:" + com.aliyun.apsara.alivclittlevideo.R.string.alivc_little_no_user, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        LittleHttpManager.getInstance().requestRecommonVideoList(userInfo.getToken(), 1, AlivcLittleHttpConfig.DEFAULT_PAGE_SIZE, id,
                new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittleMyVideoListResponse>() {
                    @Override
                    public void onResponse(final boolean result, String message,
                                           final LittleHttpResponse.LittleMyVideoListResponse data) {

                        if (result) {
                            List<LittleMineVideoInfo.VideoListBean> videoList = data.data.getVideoList();
                            if (videoList != null && videoList.size() > 0) {
                                int listSize = videoList.size();
                                for (int i = 0; i < listSize; i++) {
                                    videoList.get(i).setCensorStatus(LittleMineVideoInfo
                                            .VideoListBean.STATUS_CENSOR_SUCCESS);
                                    if (i == listSize - 1) {
                                        mLastVideoId = videoList.get(i).getId();
                                    }
                                }
                            }
                            if (!isLoadMoreData) {
                                videoPlayView.refreshVideoList(data.data.getVideoList());
                            } else {
                                videoPlayView.addMoreData(data.data.getVideoList());
                            }
                        } else {
                            if (videoPlayView != null) {
                                videoPlayView.loadFailure();
                            }
                            ToastUtils.show(ShiPinXiangQingActivity.this, "网络错误");
                        }

                    }
                });
    }


    /**
     * 视频播放列表刷新、加载更多事件监听
     */
    private static class MyRefreshDataListener implements AlivcVideoListView.OnRefreshDataListener {
        WeakReference<ShiPinXiangQingActivity> weakReference;

        MyRefreshDataListener(ShiPinXiangQingActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onRefresh() {

            ShiPinXiangQingActivity activity = weakReference.get();
            if (activity != null) {
                activity.isLoadMoreData = false;
                activity.mLastVideoId = -1;
                activity.loadPlayList(activity.mLastVideoId);
            }
        }

        @Override
        public void onLoadMore() {
            ShiPinXiangQingActivity activity = weakReference.get();
            if (activity != null) {
                activity.isLoadMoreData = true;
                activity.loadPlayList(activity.mLastVideoId);
            }
        }
    }

    /**
     * sts刷新监听
     */
    private static class MyStsResultListener implements OnStsResultListener {
        WeakReference<ShiPinXiangQingActivity> weakReference;

        MyStsResultListener(ShiPinXiangQingActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(StsTokenInfo tokenInfo) {

            ShiPinXiangQingActivity videoListActivity = weakReference.get();
            // videoListActivity.videoPlayView刷新sts信息
            videoListActivity.videoPlayView.refreshStsInfo(tokenInfo);

        }

        @Override
        public void onFail() {

        }
    }

    /**
     * 退出activity的动画效果不起作用，要在java代码里写
     * 复写activity的finish方法，在overridePendingTransition中写入自己的动画效果
     */
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.activity_stay, R.anim.activity_out);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
