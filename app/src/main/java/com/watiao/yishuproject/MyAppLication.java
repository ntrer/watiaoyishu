package com.watiao.yishuproject;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.aliyun.common.httpfinal.QupaiHttpFinal;
import com.aliyun.downloader.DownloaderManager;
import com.aliyun.private_service.PrivateService;
import com.aliyun.sys.AlivcSdkCore;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.watiao.yishuproject.utils.MediaLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

public class MyAppLication extends Application {
    private static MyAppLication myApplication=null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Beta.installTinker();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        AppContext.getInstance().init(myApplication);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Beta.autoCheckUpgrade=true;
        //公网
        Bugly.init(getApplicationContext(), "05270173b8", true);
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
        initFenXiang();
        initHttp();
        initDownLoader();
//        //短视频sdk，暂时只支持api 18以上的版本
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2){
            AlivcSdkCore.register(getApplicationContext());
            AlivcSdkCore.setLogLevel(AlivcSdkCore.AlivcLogLevel.AlivcLogDebug);

        }

    }

    private void initFenXiang() {
        UMConfigure.init(this,"5d19d5774ca357b96900077c"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
    }

    /**
     * 短视频需要的http依赖
     */
    private void initHttp() {
        QupaiHttpFinal.getInstance().initOkHttpFinal();
    }
    private void initDownLoader() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyun/encryptedApp.dat";
        PrivateService.initService(this,filePath );
        DownloaderManager.getInstance().init(this);

    }



    public static MyAppLication getInstance(){
        return myApplication;
    }
}
