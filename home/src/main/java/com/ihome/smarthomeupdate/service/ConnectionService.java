package com.ihome.smarthomeupdate.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.erongdu.wireless.tools.log.MyLog;
import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.module.base.Constants;
import com.ihome.smarthomeupdate.module.base.NoticeManager;
import com.ihome.smarthomeupdate.module.base.communicate.ICommunicate;
import com.ihome.smarthomeupdate.module.base.communicate.MySocketManager;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.BTMessageEvent;
import com.ihome.smarthomeupdate.module.base.communicate.MyBluetoothManager;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.BaseMessageEvent;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.LogEvent;
import com.ihome.smarthomeupdate.utils.EventBusUtils;
import com.ihome.smarthomeupdate.utils.NoticeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/1/19 16:31
 */

public class ConnectionService extends Service {

    public final static int START_BLUETOOTH_SERVER  = 1000;
    public final static int START_WIRELESS_SERVER  = 1001;
    private PowerManager.WakeLock wl;






    public class MyBinder extends Binder {
        public ConnectionService getService(){
            return ConnectionService.this;
        }
    }

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = NoticeUtils.createForegroundNotification(this,
                "appupdate_notice_channal",
                "appupdate service",
                "升级服务",
                "升级服务正在运行中"
                );
        //将服务置于启动状态 ,NOTIFICATION_ID指的是创建的通知的ID
        startForeground(2, notification);

        registerEvent();
        MySocketManager.getInstance().connect();
        wakeLock();
    }


    private void registerEvent(){
        MySocketManager.getInstance().on(Constants.WARN, new ICommunicate.Listener() {
            @Override
            public void onMessage(BaseMessageEvent event) {
                MyLog.e("ConnectService1 SERVER_MSG");
                NoticeManager.startNotification("提醒",event.message);
                EventBusUtils.saveToDatabase("notice",event.message,LogEvent.LOG_NOTICE,event.event);
               // EventBusUtils.sendMessageEvent(new BTMessageEvent(BTMessageEvent.REFRESH_NOTICE));
              // VibrateUtils.vibrate(5000);
            }
        });

        MySocketManager.getInstance().on(Constants.ACTION, new ICommunicate.Listener() {
            @Override
            public void onMessage(BaseMessageEvent event) {
                MyLog.e("ConnectService2 "+event.event);
               NoticeManager.startNotification("动作",event.message);
               // EventBusUtils.saveToDatabase("notice",event.message,LogEvent.LOG_NOTICE,event.event);
            }
        });
    }







    private void wakeLock() {
        PowerManager pm = (PowerManager) getSystemService(
                Context.POWER_SERVICE);
        wl= pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE,
                getString(R.string.pm_tag1));
        wl.acquire();
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent==null){
            return START_STICKY;
        }
        int  command = intent.getIntExtra(Constants.SP_KEY_COMMAND,0);
        switch (command){
            case START_BLUETOOTH_SERVER:

                break;
            case START_WIRELESS_SERVER:
                startStartWirelessServer();
                break;
        }
        return START_STICKY;
    }



    public void startStartWirelessServer(){
        MySocketManager.getInstance();
    }


    @Override
    public boolean onUnbind(Intent intent) {

        Log.i("test_out","----->onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("test_out","----->onRebind");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        MySocketManager.getInstance().destroy();
        EventBusUtils.sendLog("ConnectService","ConnectService onDestroyed!", LogEvent.LOG_IMPORTANT,true);
        wl.release();
    }

    public static void startService(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(new Intent( activity, ConnectionService.class));
        }else{
            activity.startService(new Intent( activity, ConnectionService.class));
        }
    }
}
