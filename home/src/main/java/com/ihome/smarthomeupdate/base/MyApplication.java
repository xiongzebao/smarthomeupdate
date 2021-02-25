package com.ihome.smarthomeupdate.base;

import android.app.Application;
import android.content.Context;


import com.erongdu.wireless.tools.log.MyLog;
import com.ihome.smarthomeupdate.utils.CrashHandlerUtils;
import com.ihome.smarthomeupdate.MainActivity;


public class MyApplication extends Application {
    public static MyApplication application = null;
    public static MyApplication getInstance(){
        return application;
    }
   public static MainActivity mainActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        application = this;
        MyLog.init(KTAppConfig.isDebug,"xiong",false,2,3);
        CrashHandlerUtils.getInstance().init(this);

    }


    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      //  MultiDex.install(this);

    }

}
