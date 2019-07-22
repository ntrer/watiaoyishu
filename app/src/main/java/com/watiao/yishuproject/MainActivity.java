package com.watiao.yishuproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.apsara.alivclittlevideo.activity.PublishActivity;
import com.aliyun.apsara.alivclittlevideo.constants.LittleVideoParamConfig;
import com.aliyun.apsara.alivclittlevideo.net.HttpEngine;
import com.aliyun.apsara.alivclittlevideo.net.LittleHttpManager;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleHttpResponse;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleImageUploadAuthInfo;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleVideoUploadAuthInfo;
import com.aliyun.apsara.alivclittlevideo.view.mine.AlivcLittleUserManager;
import com.aliyun.apsara.alivclittlevideo.view.publish.AlivcVideoPublishView;
import com.aliyun.apsara.alivclittlevideo.view.publish.OnAuthInfoExpiredListener;
import com.aliyun.apsara.alivclittlevideo.view.video.OnUploadCompleteListener;
import com.aliyun.demo.recorder.activity.AlivcSvideoRecordActivity;
import com.aliyun.svideo.base.AliyunSvideoActionConfig;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.activity.DingDanDetailActivity;
import com.watiao.yishuproject.activity.WoDeSaiShiDetailActivity;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.base.MessageEvent4;
import com.watiao.yishuproject.base.PermissionListener;
import com.watiao.yishuproject.fragment.CaiYiFragment.CaiYiFragment;
import com.watiao.yishuproject.fragment.SaiShiFragment.SaiShiFragment;
import com.watiao.yishuproject.fragment.WoDeFragment.WoDeFragment;
import com.watiao.yishuproject.fragment.ZongYiFragment.ZongYiFragment;
import com.watiao.yishuproject.ui.BottomNavigationViewHelper;
import com.watiao.yishuproject.ui.ExtAlertDialog;
import com.watiao.yishuproject.utils.ActivityManager.ActivityStackManager;
import com.watiao.yishuproject.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.aliyun.apsara.alivclittlevideo.activity.PublishActivity.KEY_PARAM_CONFIG;
import static com.aliyun.apsara.alivclittlevideo.activity.PublishActivity.KEY_PARAM_DESCRIBE;
import static com.aliyun.apsara.alivclittlevideo.activity.PublishActivity.KEY_PARAM_THUMBNAIL;

public class MainActivity extends BaseActivity {


    @BindView(R.id.record)
    CircleImageView mRecord;
    private CaiYiFragment mCaiYiFragment;
    private SaiShiFragment mSaiShiFragment;
    private ZongYiFragment mZongYiFragment;
    private WoDeFragment mWoDeFragment;
    private BottomNavigationView mNavigationView;
    private Fragment[] mFragments;
    private int lastfragment;//用于记录上个选择的Fragment
    //退出时的时间
    private long mExitTime;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private boolean oPen = false;
    private AlertDialog dlg,dlg2;
    private Boolean isGranted=true;

    private static final int PERMISSION_REQUEST_CODE = 1001;

    private Dialog mRequestDialog;

    private String message;
    /**
     * 视频合成输出文件名称
     */
    private String mComposeFileName = "output_compose_video.mp4";
    /**
     * 视频合成输出文件路径
     */
    private String mComposeOutputPath = LittleVideoParamConfig.DIR_COMPOSE + "/" + mComposeFileName;
    /**
     * 视频上传凭证信息
     */
    private LittleVideoUploadAuthInfo mVideoUploadAuthInfo;
    /**
     * 图片上传凭证信息
     */
    private LittleImageUploadAuthInfo mImageUploadAuthInfo;
    /**
     * 视频上传凭证信息获取成功
     */
    private boolean isVideoUploadAuthRequestSuccess = false;
    /**
     * 图片上传凭证信息获取成功
     */
    private boolean isImageUploadAuthRequestSuccess = false;

    /**
     * 编辑后的特效配置文件地址，用户合成视频
     */
    private String mConfigPath;
    /**
     * 封面地址
     */
    private String mThumbnailPath;
    /**
     * 视频描述
     */
    private String mVideoDesc;

    private AlivcVideoPublishView mAlivcVideoPublishView;

    private FrameLayout mContentView;

    /**
     * 系统授权设置的弹框
     */
    AlertDialog openAppDetDialog;

    /**
     * 权限列表
     */
    String[] permission = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        oPen = isOPen(this);
        mRequestDialog = ExtAlertDialog.creatRequestDialog2(MainActivity.this, "定位中...");
        mRequestDialog.setCancelable(false);
        mContentView=findViewById(R.id.fragment_container);
        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToRecorder();
            }
        });
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.LOLLIPOP) {
            if (!oPen) {
                showSureDlg2(MainActivity.this, "您没有开启定位服务，功能无法正常使用", "允许", false);
            }
            else {
                permission();
            }
        }

        if(getIntent().getBundleExtra("消息") != null) {
            intentToActivity(DingDanDetailActivity.class);
        }

        // 注册编辑完成之后跳转的页面
        AliyunSvideoActionConfig.getInstance().registerPublishActivity(PublishActivity.class.getName());
        EventBus.getDefault().register(this);
    }



    /**
     * 界面跳转
     * @param tarActivity
     */
    protected void intentToActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(MainActivity.this, tarActivity);
        startActivity(intent);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mThumbnailPath = intent.getStringExtra(KEY_PARAM_THUMBNAIL);
        mVideoDesc = intent.getStringExtra(KEY_PARAM_DESCRIBE);
        mConfigPath = intent.getStringExtra(KEY_PARAM_CONFIG);
        Log.d("asdas",PreferencesUtils.getString(MainActivity.this,"music"));
        if (!TextUtils.isEmpty(mThumbnailPath)) {
            if (mAlivcVideoPublishView == null) {
                initUploadView();
            }
            AlivcLittleUserManager.getInstance().setAllowChangeUser(false);
        } else {
            return;
        }
//        LittleHttpManager.getInstance().requestImageUploadAuth(
//                new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittleImageUploadAuthResponse>() {
//                    @Override
//                    public void onResponse(boolean result, String message,
//                                           LittleHttpResponse.LittleImageUploadAuthResponse data) {
//                        if (result) {
//                            isImageUploadAuthRequestSuccess = true;
//                            mImageUploadAuthInfo = data.data;
//                            if (isVideoUploadAuthRequestSuccess) {
//                                mAlivcVideoPublishView.startUpload(
//                                        //编辑配置信息相关的json文件路径
//                                        mConfigPath,
//                                        //视频合成路径
//                                        mComposeOutputPath,
//                                        //视频id
//                                        "9816a346398949f19a9a5925d55b3763",
//                                        //视频上传地址
//                                        "eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLWJlaWppbmcuYWxpeXVuY3MuY29tIiwiQnVja2V0Ijoib3V0aW4tMDNlNGNkZWQ5ZmM5MTFlOTg2OGMwMDE2M2UxYzk0YTQiLCJGaWxlTmFtZSI6InN2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2Lm1wNCJ9",
//                                        //视频上传凭证
//                                        "eyJTZWN1cml0eVRva2VuIjoiQ0FJUzBBUjFxNkZ0NUIyeWZTaklyNG5XTWQzd25ZZ1Z4UGVwTngv" +
//                                                "Q3FVVVVYLzlHZ0kvOHV6ejJJSDlJZEhWb0FPOGZ2dlUwbTJ0WTdQc1p" +
//                                                "sclVxRU1BYUZCR1ZONUlzdDhnTW9GTC9KcGZadjh1ODRZQURpNUNqUWFO" +
//                                                "MGlzcFNtWjI4V2Y3d2FmK0FVQlhHQ1RtZDVNTVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5" +
//                                                "TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmF" +
//                                                "IeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlx" +
//                                                "cDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTaj" +
//                                                "QzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRH" +
//                                                "REWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0Iy" +
//                                                "RHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoyc" +
//                                                "ExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwcEZncGZUSzFSemJQbU5" +
//                                                "MS205YmFCMjUvelcrUGREZTBkc1Znb0lGS09waUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VsUGZCcjVoc" +
//                                                "0hRZC82TnNPVkZpSUlJWXovMWMrdS9Mc3RCbksrNzYvV3l6dDVYUi91UHVncHRnUnVSbzhJNjM3MmJUSj" +
//                                                "QyV0c1VWI5Ty9kcHhKM2xQMFIwV2dteWRuQkR4L1NmdTJrS3ZSaHBrUnZ2WkVwUHR3eklpai9nTFpaRWlh" +
//                                                "elJteWhlZm81WG1QWEZUUW1uOGw1cEFNbXkvNjB4WHVkdmJFMlIwRVFEWTlZQ0dvQUJFcXFoc2sweW5OQzV" +
//                                                "VSXorbVR0d0FVMjdSYis3ZGNsc0RlbGJWNnRaT1czZ29FeU9HNENFaksyMXd6b2JFb0hIOG1acE9IaGFMRl" +
//                                                "lkaERwNnk3VjM3VjRZdXlVMkx5bmhpNmNxNWNWYkhtdnhlYUpFR3dGUFFLaDhTMzB5NHRIUk03THNaRjRo" +
//                                                "dkdtdmd2L2ZZeW95WXlqVENRL3EvNlpoQldNTU5FcXg1b2s9IiwiQWNjZXNzS2V5SWQiOiJTVFMuTkpje" +
//                                                "mdEcFc0czVCNTlzTUVBU3Bqb0pXWSIsIkV4cGlyZVVUQ1RpbWUiOiIyMDE5LTA3LTIyVDAyOjU0OjQyW" +
//                                                "iIsIkFjY2Vzc0tleVNlY3JldCI6IkRQaUdTS0V3QVd2SkRqejU3Q2VFOHU3QXBMWXhQTkxIRmY1eFBBN" +
//                                                "GVMZHl1IiwiRXhwaXJhdGlvbiI6IjM0ODkiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSJ9",
//                                        //视频描述
//                                        mVideoDesc,
//                                        //封面图片地址
//                                        mThumbnailPath,
//                                        //图片id
//                                        mImageUploadAuthInfo.getImageId(),
//                                        //图片访问地址
//                                        mImageUploadAuthInfo.getImageURL(),
//                                        //图片上传地址
//                                        mImageUploadAuthInfo.getUploadAddress(),
//                                        //图片上传凭证
//                                        mImageUploadAuthInfo.getUploadAuth());
//
//                            }
//                        } else {
//                            //  对失败处理
//                            ToastUtil.showToast(MainActivity.this, "获取上传凭证失败" );
//                            AlivcLittleUserManager.getInstance().setAllowChangeUser(true);
//                        }
//
//
//                    }
//                });
//        LittleHttpManager.getInstance().requestVideoUploadAuth("video", mComposeFileName,
//                new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittleVideoUploadAuthResponse>() {
//                    @Override
//                    public void onResponse(boolean result, String message,
//                                           LittleHttpResponse.LittleVideoUploadAuthResponse data) {
//                        if (result) {
//                            isVideoUploadAuthRequestSuccess = true;
//                            mVideoUploadAuthInfo = data.data;
//                            if (isImageUploadAuthRequestSuccess) {
//                                mAlivcVideoPublishView.startUpload(mConfigPath,
//                                        mComposeOutputPath,
//                                        //视频id
//                                        "9816a346398949f19a9a5925d55b3763",
//                                        //视频上传地址
//                                        "eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLWJlaWppbmcuYWxpeXVuY3MuY29tIiwiQnVja2V0Ijoib3V0aW4tMDNlNGNkZWQ5ZmM5MTFlOTg2OGMwMDE2M2UxYzk0YTQiLCJGaWxlTmFtZSI6InN2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2Lm1wNCJ9",
//                                        //视频上传凭证
//                                        "eyJTZWN1cml0eVRva2VuIjoiQ0FJUzBBUjFxNkZ0NUIyeWZTaklyNG5XTWQzd25ZZ1Z4UGVwTngv" +
//                                                "Q3FVVVVYLzlHZ0kvOHV6ejJJSDlJZEhWb0FPOGZ2dlUwbTJ0WTdQc1p" +
//                                                "sclVxRU1BYUZCR1ZONUlzdDhnTW9GTC9KcGZadjh1ODRZQURpNUNqUWFO" +
//                                                "MGlzcFNtWjI4V2Y3d2FmK0FVQlhHQ1RtZDVNTVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5" +
//                                                "TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmF" +
//                                                "IeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlx" +
//                                                "cDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTaj" +
//                                                "QzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRH" +
//                                                "REWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0Iy" +
//                                                "RHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoyc" +
//                                                "ExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwcEZncGZUSzFSemJQbU5" +
//                                                "MS205YmFCMjUvelcrUGREZTBkc1Znb0lGS09waUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VsUGZCcjVoc" +
//                                                "0hRZC82TnNPVkZpSUlJWXovMWMrdS9Mc3RCbksrNzYvV3l6dDVYUi91UHVncHRnUnVSbzhJNjM3MmJUSj" +
//                                                "QyV0c1VWI5Ty9kcHhKM2xQMFIwV2dteWRuQkR4L1NmdTJrS3ZSaHBrUnZ2WkVwUHR3eklpai9nTFpaRWlh" +
//                                                "elJteWhlZm81WG1QWEZUUW1uOGw1cEFNbXkvNjB4WHVkdmJFMlIwRVFEWTlZQ0dvQUJFcXFoc2sweW5OQzV" +
//                                                "VSXorbVR0d0FVMjdSYis3ZGNsc0RlbGJWNnRaT1czZ29FeU9HNENFaksyMXd6b2JFb0hIOG1acE9IaGFMRl" +
//                                                "lkaERwNnk3VjM3VjRZdXlVMkx5bmhpNmNxNWNWYkhtdnhlYUpFR3dGUFFLaDhTMzB5NHRIUk03THNaRjRo" +
//                                                "dkdtdmd2L2ZZeW95WXlqVENRL3EvNlpoQldNTU5FcXg1b2s9IiwiQWNjZXNzS2V5SWQiOiJTVFMuTkpje" +
//                                                "mdEcFc0czVCNTlzTUVBU3Bqb0pXWSIsIkV4cGlyZVVUQ1RpbWUiOiIyMDE5LTA3LTIyVDAyOjU0OjQyW" +
//                                                "iIsIkFjY2Vzc0tleVNlY3JldCI6IkRQaUdTS0V3QVd2SkRqejU3Q2VFOHU3QXBMWXhQTkxIRmY1eFBBN" +
//                                                "GVMZHl1IiwiRXhwaXJhdGlvbiI6IjM0ODkiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSJ9",
//                                        mVideoDesc,
//                                        mThumbnailPath,
//                                        mImageUploadAuthInfo.getImageId(),
//                                        mImageUploadAuthInfo.getImageURL(),
//                                        mImageUploadAuthInfo.getUploadAddress(),
//                                        mImageUploadAuthInfo.getUploadAuth());
//                            }
//                        } else {
//                            //  对失败处理
//                            ToastUtil.showToast(MainActivity.this, "获取上传凭证失败" );
//                            AlivcLittleUserManager.getInstance().setAllowChangeUser(true);
//                        }
//
//                    }
//                });

    }


    /**
     * 出事化上传view
     */
    private void initUploadView() {
        mAlivcVideoPublishView = new AlivcVideoPublishView(this);
        //设置上传凭证过期监听
        mAlivcVideoPublishView.setOnAuthInfoExpiredListener(new OnAuthInfoExpiredListener() {
            @Override
            public void onImageAuthInfoExpired() {
                LittleHttpManager.getInstance().requestImageUploadAuth(
                        new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittleImageUploadAuthResponse>() {
                            @Override
                            public void onResponse(boolean result, String message,
                                                   LittleHttpResponse.LittleImageUploadAuthResponse data) {
                                if (result) {
                                    if (mAlivcVideoPublishView != null) {
                                        mAlivcVideoPublishView.refreshImageUploadAuthInfo(data.data.getImageId(),
                                                data.data.getImageURL(),
                                                //视频上传地址
                                                "eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLWJlaWppbmcuYWxpeXVuY3MuY29tIiwiQnVja2V0Ijoib3V0aW4tMDNlNGNkZWQ5ZmM5MTFlOTg2OGMwMDE2M2UxYzk0YTQiLCJGaWxlTmFtZSI6InN2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2LzNjNGNjOGQ2LTE2YzE3NjUwOGU2Lm1wNCJ9",
                                                //视频上传凭证
                                                "eyJTZWN1cml0eVRva2VuIjoiQ0FJUzBBUjFxNkZ0NUIyeWZTaklyNG5XTWQzd25ZZ1Z4UGVwTngv" +
                                                        "Q3FVVVVYLzlHZ0kvOHV6ejJJSDlJZEhWb0FPOGZ2dlUwbTJ0WTdQc1p" +
                                                        "sclVxRU1BYUZCR1ZONUlzdDhnTW9GTC9KcGZadjh1ODRZQURpNUNqUWFO" +
                                                        "MGlzcFNtWjI4V2Y3d2FmK0FVQlhHQ1RtZDVNTVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5" +
                                                        "TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmF" +
                                                        "IeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlx" +
                                                        "cDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTaj" +
                                                        "QzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRH" +
                                                        "REWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0Iy" +
                                                        "RHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoyc" +
                                                        "ExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwcEZncGZUSzFSemJQbU5" +
                                                        "MS205YmFCMjUvelcrUGREZTBkc1Znb0lGS09waUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VsUGZCcjVoc" +
                                                        "0hRZC82TnNPVkZpSUlJWXovMWMrdS9Mc3RCbksrNzYvV3l6dDVYUi91UHVncHRnUnVSbzhJNjM3MmJUSj" +
                                                        "QyV0c1VWI5Ty9kcHhKM2xQMFIwV2dteWRuQkR4L1NmdTJrS3ZSaHBrUnZ2WkVwUHR3eklpai9nTFpaRWlh" +
                                                        "elJteWhlZm81WG1QWEZUUW1uOGw1cEFNbXkvNjB4WHVkdmJFMlIwRVFEWTlZQ0dvQUJFcXFoc2sweW5OQzV" +
                                                        "VSXorbVR0d0FVMjdSYis3ZGNsc0RlbGJWNnRaT1czZ29FeU9HNENFaksyMXd6b2JFb0hIOG1acE9IaGFMRl" +
                                                        "lkaERwNnk3VjM3VjRZdXlVMkx5bmhpNmNxNWNWYkhtdnhlYUpFR3dGUFFLaDhTMzB5NHRIUk03THNaRjRo" +
                                                        "dkdtdmd2L2ZZeW95WXlqVENRL3EvNlpoQldNTU5FcXg1b2s9IiwiQWNjZXNzS2V5SWQiOiJTVFMuTkpje" +
                                                        "mdEcFc0czVCNTlzTUVBU3Bqb0pXWSIsIkV4cGlyZVVUQ1RpbWUiOiIyMDE5LTA3LTIyVDAyOjU0OjQyW" +
                                                        "iIsIkFjY2Vzc0tleVNlY3JldCI6IkRQaUdTS0V3QVd2SkRqejU3Q2VFOHU3QXBMWXhQTkxIRmY1eFBBN" +
                                                        "GVMZHl1IiwiRXhwaXJhdGlvbiI6IjM0ODkiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSJ9");
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onVideoAuthInfoExpired(String videoId) {

                LittleHttpManager.getInstance().refreshVideoUploadAuth(videoId,
                        new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittleVideoUploadAuthResponse>() {
                            @Override
                            public void onResponse(boolean result, String message,
                                                   LittleHttpResponse.LittleVideoUploadAuthResponse data) {
                                if (result) {
                                    if (mAlivcVideoPublishView != null) {
                                        mAlivcVideoPublishView.refreshVideoAuth(data.data.getUploadAddress(),
                                                data.data.getUploadAuth());
                                    }
                                }
                            }
                        });
            }
        });
        //设置上传结果回调监听
        mAlivcVideoPublishView.setOnUploadCompleteListener(new MyUploadCompleteListener(this));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mContentView.addView(mAlivcVideoPublishView, params);
    }



    /**
     * 上传结果回调
     */
    private class MyUploadCompleteListener implements OnUploadCompleteListener {
        WeakReference<MainActivity> weakReference;

        MyUploadCompleteListener(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(final String videoId, final String imageUrl, final String describe) {
            if (weakReference == null) {
                return;
            }
            MainActivity activity = weakReference.get();
            LittleHttpManager.getInstance().videoPublish(
                    AlivcLittleUserManager.getInstance().getUserInfo(activity).getToken(),
                    videoId,
                    imageUrl,
                    "", describe, "", 0, 0, 0, "",
                    new HttpEngine.HttpResponseResultCallback<LittleHttpResponse.LittlePublishResponse>() {
                        @Override
                        public void onResponse(boolean result, String message,
                                               LittleHttpResponse.LittlePublishResponse data) {
                            if (weakReference == null) {
                                return;
                            }
                            MainActivity activity = weakReference.get();
                            //标识位，恢复初始状态
                            activity.isImageUploadAuthRequestSuccess = false;
                            activity.isVideoUploadAuthRequestSuccess = false;
                            AlivcLittleUserManager.getInstance().setAllowChangeUser(true);

                            if (result) {

                                if (activity == null) {
                                    return;
                                }
                                activity.mAlivcVideoPublishView.showSuccess();
                                activity.startActivity(new Intent(MainActivity.this,WoDeSaiShiDetailActivity.class));
                            } else {
                                activity.mAlivcVideoPublishView.showFailed("发布失败," + message);
                            }
                        }
                    });
        }
        @Override
        public void onFailure(String code, String msg) {
            if (weakReference == null) {
                return;
            }
            MainActivity activity = weakReference.get();
            //标识位，恢复初始状态
            activity.isImageUploadAuthRequestSuccess = false;
            activity.isVideoUploadAuthRequestSuccess = false;
            AlivcLittleUserManager.getInstance().setAllowChangeUser(true);
            if (weakReference == null) {
                return;
            }
            activity.mAlivcVideoPublishView.showFailed("发布失败," + msg);
        }
    }






    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new MessageEvent("主页"));
    }

    private void jumpToRecorder() {
        AliyunSnapVideoParam recordParam = new AliyunSnapVideoParam.Builder()
                .setResolutionMode(LittleVideoParamConfig.Recorder.RESOLUTION_MODE)
                .setRatioMode(LittleVideoParamConfig.Recorder.RATIO_MODE)
                .setRecordMode(LittleVideoParamConfig.Recorder.RECORD_MODE)
                .setBeautyLevel(LittleVideoParamConfig.Recorder.BEAUTY_LEVEL)
                .setBeautyStatus(LittleVideoParamConfig.Recorder.BEAUTY_STATUS)
                .setCameraType(LittleVideoParamConfig.Recorder.CAMERA_TYPE)
                .setFlashType(LittleVideoParamConfig.Recorder.FLASH_TYPE)
                .setNeedClip(LittleVideoParamConfig.Recorder.NEED_CLIP)
                .setMaxDuration(LittleVideoParamConfig.Recorder.MAX_DURATION)
                .setMinDuration(LittleVideoParamConfig.Recorder.MIN_DURATION)
                .setVideoQuality(LittleVideoParamConfig.Recorder.VIDEO_QUALITY)
                .setGop(LittleVideoParamConfig.Recorder.GOP)
                .setVideoCodec(LittleVideoParamConfig.Recorder.VIDEO_CODEC)

                //裁剪参数
                .setMinVideoDuration(LittleVideoParamConfig.Crop.MIN_VIDEO_DURATION)
                .setMaxVideoDuration(LittleVideoParamConfig.Crop.MAX_VIDEO_DURATION)
                .setMinCropDuration(LittleVideoParamConfig.Crop.MIN_CROP_DURATION)
                .setFrameRate(LittleVideoParamConfig.Crop.FRAME_RATE)
                .setCropMode(LittleVideoParamConfig.Crop.CROP_MODE)
                .build();
        AlivcSvideoRecordActivity.startRecord(this, recordParam, "community");
    }


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mNavigationView = findViewById(R.id.navigation_fragment);
        mNavigationView.setItemIconTintList(null);
        initFragment();
    }



    //初始化fragment和fragment数组
    private void initFragment() {
        mCaiYiFragment = new CaiYiFragment();
        mSaiShiFragment = new SaiShiFragment();
        mZongYiFragment = new ZongYiFragment();
        mWoDeFragment = new WoDeFragment();
        mFragments = new Fragment[]{mCaiYiFragment, mSaiShiFragment, mZongYiFragment, mWoDeFragment};
        lastfragment = 0;
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mCaiYiFragment, "tag1").show(mCaiYiFragment).commit();
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_fragment_zero: {
                        if (lastfragment != 0) {
                            switchFragment(lastfragment, 0);
                            lastfragment = 0;

                        }
                        return true;

                    }
                    case R.id.navigation_fragment_one: {
                        if (lastfragment != 1) {
                            switchFragment(lastfragment, 1);
                            lastfragment = 1;

                        }

                        return true;
                    }
                    case R.id.navigation_fragment_three: {
                        if (lastfragment != 2) {
                            switchFragment(lastfragment, 2);
                            lastfragment = 2;

                        }

                        return true;
                    }
                    case R.id.navigation_fragment_four: {
                        if (lastfragment != 3) {
                            switchFragment(lastfragment, 3);
                            lastfragment = 3;

                        }

                        return true;
                    }


                }
                return false;
            }
        });


    }


    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastfragment]);//隐藏上个Fragment
        if (mFragments[index].isAdded() == false) {
            transaction.add(R.id.fragment_container, mFragments[index], "tag" + index);


        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();

    }

    private void reGetPermission() {
        showSureDlg(MainActivity.this, "警告", "权限被拒绝，部分功能将无法使用，请重新授予权限", "确定", false);
    }

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(MainActivity.this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        mRequestDialog.show();
    }


    private void permission() {
        isGranted=true;
        requestRunPermisssion(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS
        }, new PermissionListener() {
            @Override
            public void onGranted() {
                initMap();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                  isGranted=false;
                }
                if(!isGranted){
                    reGetPermission();
                }
            }
        });
    }


    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }


    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (aMapLocation.getCity() != null) {
//                        mDingwei.setText(aMapLocation.getCity());
                        EventBus.getDefault().postSticky(new MessageEvent4("定位", aMapLocation.getCity()));
                        mRequestDialog.dismiss();
                    } else {
                        ToastUtils.showLong("定位失败，请重新定位");
                    }
                } else {
                    ExtAlertDialog.showSureDlg(MainActivity.this, "提醒", "您没有开启定位服务，功能无法正常使用", "允许", false, new ExtAlertDialog.IExtDlgClick() {
                        @Override
                        public void Oclick(int result) {
                            if (result == 1) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, 887);
                            } else {
                                showSureDlg2(MainActivity.this, "您没有开启定位服务，功能无法正常使用", "允许", false);
                            }

                        }
                    });
                    ToastUtils.showLong("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 887) {
            if (isOPen(this)) {
                permission();
            } else {
                showSureDlg2(MainActivity.this, "您没有开启定位服务，功能无法正常使用", "允许", false);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
         if(messageEvent.getMessage().equals("上传")){
         }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }



    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //退出应用
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
//            MyApplication.getInstance().exitApp();
            ActivityStackManager.getActivityStackManager().popAllActivity();//remove all activity.
//            System.exit(0);
            finish();
        }
    }


    public void showSureDlg2(Context context, String msg, String btnTxt, boolean isCancelable) {
        dlg = new AlertDialog.Builder(context).create();
        dlg.show();
        dlg.setContentView(R.layout.ext_cancel_sure_dialog2);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.content);
        if (msg != null && !msg.equals("")) {
            contentTxt.setText(msg);
        } else {
            contentTxt.setText("无备注");
        }
        Button btn = (Button) dlg.findViewById(R.id.ext_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showSureDlg2(MainActivity.this, "您没有开启定位服务，功能无法正常使用", "允许", false);
                    }
                },1000);
            }
        });

        Button sureBnt = (Button) dlg.findViewById(R.id.sure);
        if (!TextUtils.isEmpty(btnTxt))
            sureBnt.setText(btnTxt);

        sureBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 887);
                dlg.dismiss();
            }
        });
        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
    }


    public void showSureDlg(Context context, String title, String msg, String btnTxt, boolean isCancelable) {
        dlg2 = new AlertDialog.Builder(context).create();
        dlg2.show();
        dlg2.setContentView(R.layout.ext_cancel_sure_dialog);
        dlg2.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        View titleLayout = dlg2.findViewById(R.id.title_layout);
        TextView titleTxt = (TextView) dlg2.findViewById(R.id.ext_dialog_title);
        if (TextUtils.isEmpty(title))
            titleLayout.setVisibility(View.GONE);
        else
            titleTxt.setText(title);
        TextView contentTxt = (TextView) dlg2
                .findViewById(R.id.content);
        if (msg != null && !msg.equals("")) {
            contentTxt.setText(msg);
        } else {
            contentTxt.setText("无备注");
        }
        Button btn = (Button) dlg2.findViewById(R.id.ext_dialog_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(intent, 887);
                dlg2.dismiss();
                permission();
            }
        });

        dlg2.setCancelable(false);
    }
}
