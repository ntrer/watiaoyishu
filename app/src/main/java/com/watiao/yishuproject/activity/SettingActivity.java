package com.watiao.yishuproject.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.ui.ExtAlertDialog;
import com.watiao.yishuproject.ui.dialog.ActionSheetDialog;
import com.watiao.yishuproject.utils.CacheUtil.CacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.heimingdan)
    RelativeLayout mHeimingdan;
    @BindView(R.id.huancun)
    TextView mHuancun;
    @BindView(R.id.qingchuhuancun)
    RelativeLayout mQingchuhuancun;
    @BindView(R.id.guanyuwomen)
    RelativeLayout mGuanyuwomen;
    @BindView(R.id.banbenhaoma)
    TextView mBanbenhaoma;
    @BindView(R.id.banbenhao)
    RelativeLayout mBanbenhao;
    @BindView(R.id.quit)
    RelativeLayout mQuit;
    QMUITipDialog tipDialog;
    private Dialog mRequestDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRequestDialog = ExtAlertDialog.creatRequestDialog2(SettingActivity.this, "清除中...");
        mRequestDialog.setCancelable(false);
        String verName = getVerName(SettingActivity.this);
        mBanbenhaoma.setText(verName);
        try {
            String totalCacheSize = CacheUtil.getTotalCacheSize(SettingActivity.this);
            mHuancun.setText(totalCacheSize+"B");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }


    @OnClick(R.id.guanyuwomen)
    void  guanyuwomen(){
        startActivity(new Intent(SettingActivity.this,AboutActivity.class));
    }

    @OnClick(R.id.heimingdan)
    void  heimingdan(){
        startActivity(new Intent(SettingActivity.this,HeiMingDanActivity.class));
    }

    @OnClick(R.id.qingchuhuancun)
    void  qingchuhuancun(){
        new ActionSheetDialog(SettingActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("确定", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                mRequestDialog.show();
                                CacheUtil.clearAllCache(SettingActivity.this);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String totalCacheSize = CacheUtil.getTotalCacheSize(SettingActivity.this);
                                            mHuancun.setText(totalCacheSize+"B");
                                            if(totalCacheSize.equals("0K")){
                                                mRequestDialog.dismiss();
                                                ToastUtils.showLong("清理完成");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },1000);
                            }
                        })
                .show();
    }


    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {

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
