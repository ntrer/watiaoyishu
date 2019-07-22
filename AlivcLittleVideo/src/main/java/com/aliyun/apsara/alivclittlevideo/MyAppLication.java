package com.aliyun.apsara.alivclittlevideo;

import android.app.Application;

public class MyAppLication extends Application {
    private static MyAppLication myApplication=null;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        AppContext.getInstance().init(myApplication);
    }

    public static MyAppLication getInstance(){
        return myApplication;
    }
}
