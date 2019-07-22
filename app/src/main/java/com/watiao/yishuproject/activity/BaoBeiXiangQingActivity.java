package com.watiao.yishuproject.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.demo.recorder.view.publish.LittleVideoParamConfig;
import com.aliyun.svideo.editor.MediaActivity;
import com.aliyun.svideo.sdk.external.struct.common.CropKey;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.codingending.popuplayout.PopupLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.ui.ExtAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BaoBeiXiangQingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.zuobiao_img)
    ImageView mZuobiaoImg;
    @BindView(R.id.zuibiao_dizhi)
    TextView mZuibiaoDizhi;
    @BindView(R.id.year)
    TextView mYear;
    @BindView(R.id.shengao)
    TextView mShengao;
    @BindView(R.id.tizhong)
    TextView mTizhong;
    @BindView(R.id.user_headr)
    RelativeLayout mUserHeadr;
    @BindView(R.id.piaoshu)
    TextView mPiaoshu;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.cansaizuopin)
    TextView mCansaizuopin;
    @BindView(R.id.tianjiashipin)
    ImageView mTianjiashipin;
    @BindView(R.id.bianjishipin)
    ImageView mBianjishipin;
    @BindView(R.id.logo)
    ImageView mLogo;
    @BindView(R.id.shuzhi)
    LinearLayout mShuzhi;
    @BindView(R.id.shipin_pic)
    RoundedImageView mShipinPic;
    @BindView(R.id.jianjie)
    TextView mJianjie;
    @BindView(R.id.line)
    View mLine;
    private Dialog mRequestDialog;
    private View bottomMenu;
    private PopupLayout mPopupLayout;
    private boolean useRadius = true;//是否使用圆角特性
    private TextView mcancle;
    private LinearLayout weixin, qq;
    /**
     * 判断是编辑模块进入还是通过社区模块的编辑功能进入
     */
    private static final String INTENT_PARAM_KEY_ENTRANCE = "entrance";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRequestDialog = ExtAlertDialog.creatRequestDialog2(this, "投票中...");

        bottomMenu = View.inflate(BaoBeiXiangQingActivity.this, R.layout.bottom_layout, null);
        mPopupLayout = PopupLayout.init(BaoBeiXiangQingActivity.this, bottomMenu);
        mPopupLayout.setUseRadius(useRadius);
        mcancle = bottomMenu.findViewById(R.id.cancle);
        weixin = bottomMenu.findViewById(R.id.weixin);
        qq = bottomMenu.findViewById(R.id.qq);
        mcancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupLayout.dismiss();
            }
        });

        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(BaoBeiXiangQingActivity.this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                        .withText("hello")//分享内容
                        .setCallback(shareListener)//回调监听器
                        .share();
            }
        });

        Glide.with(this).load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2163100570,3803850188&fm=26&gp=0.jpg").placeholder(R.mipmap.remensaishi_bg).into(mShipinPic);
    }


    @OnClick(R.id.tianjiashipin)
    void tianjiashipin() {
        jumpToEditor();
    }

    /**
     * 判断是编辑模块进入还是通过社区模块的编辑功能进入 svideo: 短视频 community: 社区
     */
    private static final String INTENT_PARAM_ENTRANCE_VALUE = "community";

    private void jumpToEditor() {
        Intent intent = new Intent(this, MediaActivity.class);
        intent.putExtra(CropKey.VIDEO_RATIO, LittleVideoParamConfig.Editor.VIDEO_RATIO);
        intent.putExtra(CropKey.VIDEO_SCALE, LittleVideoParamConfig.Editor.VIDEO_SCALE);
        intent.putExtra(CropKey.VIDEO_QUALITY, LittleVideoParamConfig.Editor.VIDEO_QUALITY);
        intent.putExtra(CropKey.VIDEO_FRAMERATE, LittleVideoParamConfig.Editor.VIDEO_FRAMERATE);
        intent.putExtra(CropKey.VIDEO_GOP, LittleVideoParamConfig.Editor.VIDEO_GOP);
        intent.putExtra(CropKey.VIDEO_BITRATE, LittleVideoParamConfig.Editor.VIDEO_BITRATE);
        intent.putExtra(INTENT_PARAM_KEY_ENTRANCE, INTENT_PARAM_ENTRANCE_VALUE);
        startActivity(intent);
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showLong("分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showLong("" + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };


    @Override
    public int setLayout() {
        return R.layout.activity_bao_bei_xiang_qing;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.shipin_pic)
    void shipin(){
        Intent intent=new Intent(BaoBeiXiangQingActivity.this,ShiPinXiangQingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_headr)
    void header() {
        startActivity(new Intent(BaoBeiXiangQingActivity.this, MengWaZhuYeActivity.class));
    }

    @OnClick(R.id.share)
    void share() {
        mPopupLayout.show(PopupLayout.POSITION_BOTTOM);
    }


    @OnClick(R.id.login)
    void toupiao() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
