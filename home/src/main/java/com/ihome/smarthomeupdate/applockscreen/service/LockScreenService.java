package com.ihome.smarthomeupdate.applockscreen.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.erongdu.wireless.tools.log.MyLog;
import com.ihome.smarthomeupdate.applockscreen.receiver.LockScreenReceiver;
import com.ihome.smarthomeupdate.utils.NoticeUtils;


public class LockScreenService extends Service {
    private LockScreenReceiver mReceiver;
    private IntentFilter mIntentFilter = new IntentFilter();
    private boolean isNotiShow = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //动态注册
        mIntentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
        MyLog.e("LockScreenService onStartCommand");
        mIntentFilter.setPriority(Integer.MAX_VALUE);
        if (null == mReceiver) {
            mReceiver = new LockScreenReceiver();
            mIntentFilter.setPriority(Integer.MAX_VALUE);
            registerReceiver(mReceiver, mIntentFilter);
            Notification notification=  NoticeUtils.createForegroundNotification(this,"channel_lock","LockScreen","LockScreen","locked");
            startForeground(7, notification);
            Toast.makeText(getApplicationContext(), "锁屏服务开启成功", Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }


    public static void startService(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(new Intent( activity, LockScreenService.class));
        }else{
            activity.startService(new Intent( activity, LockScreenService.class));
        }
    }
}