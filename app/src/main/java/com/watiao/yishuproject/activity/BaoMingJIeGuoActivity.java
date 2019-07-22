package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.constants.LittleVideoParamConfig;
import com.aliyun.demo.recorder.activity.AlivcSvideoRecordActivity;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.base.MessageEvent;
import com.watiao.yishuproject.base.MessageEvent4;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaoMingJIeGuoActivity extends BaseActivity {

    private static final int REQUEST_SHOUHUO_DIZHI = 1003;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.baomingchenggong)
    LinearLayout mBaomingchenggong;
    @BindView(R.id.saishixiangqing)
    CardView mSaishixiangqing;
    @BindView(R.id.shangchuanshipin)
    CardView mShangchuanshipin;
    @BindView(R.id.baomingshibai)
    LinearLayout mBaomingshibai;
    @BindView(R.id.kefu)
    CardView mKefu;
    @BindView(R.id.tips)
    TextView mTips;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.phone)
    TextView mPhone;
    @BindView(R.id.firstline)
    LinearLayout mFirstline;
    @BindView(R.id.dizhi)
    TextView mDizhi;
    @BindView(R.id.edit)
    LinearLayout mEdit;
    @BindView(R.id.baomingdizhi)
    CardView mBaomingdizhi;
    @BindView(R.id.tips2)
    TextView mTips2;
    @BindView(R.id.saishixiangqing2)
    CardView mSaishixiangqing2;
    @BindView(R.id.shangchuanshipin2)
    CardView mShangchuanshipin2;
    @BindView(R.id.xuanzedizhihou)
    RelativeLayout mXuanzedizhihou;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_bao_ming_jie_guo;
    }

    @Override
    public void init() {
        EventBus.getDefault().post(new MessageEvent("报名成功"));
    }


    @OnClick(R.id.login)
    void DiZhi() {
        Intent intent = new Intent(BaoMingJIeGuoActivity.this, ShouHuoDiZhiActivity.class);
        startActivityForResult(intent, REQUEST_SHOUHUO_DIZHI);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SHOUHUO_DIZHI) {
           mXuanzedizhihou.setVisibility(View.VISIBLE);
           mTips.setVisibility(View.GONE);
           mSaishixiangqing.setVisibility(View.GONE);
           mShangchuanshipin.setVisibility(View.GONE);
           mLogin.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.saishixiangqing2)
    void saishixiangqing2(){
        Intent intent=new Intent(BaoMingJIeGuoActivity.this,WodeSaiShiActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.saishixiangqing)
    void saishixiangqing(){
        Intent intent=new Intent(BaoMingJIeGuoActivity.this,WodeSaiShiActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.shangchuanshipin)
    void shangchuanshipin(){
        jumpToRecorder();
        finish();
    }

    @OnClick(R.id.shangchuanshipin2)
    void shangchuanshipin2(){
        jumpToRecorder();
        finish();
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

    @OnClick(R.id.edit)
    void edit(){
        Intent intent = new Intent(BaoMingJIeGuoActivity.this, ShouHuoDiZhiActivity.class);
        startActivityForResult(intent, REQUEST_SHOUHUO_DIZHI);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent4 messageEvent) {

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
