package com.ihome.smarthomeupdate.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.receiver.AlarmReceiver;
import com.ihome.smarthomeupdate.utils.NoticeUtils;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/1/20 13:34
 */

public class AlarmService extends Service {



    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = NoticeUtils.createForegroundNotification(this,
                "alarm_notice_channal",
                "alarm service",
                "提醒服务",
                "提醒服务已开启"
        );
        //将服务置于启动状态 ,NOTIFICATION_ID指的是创建的通知的ID
        startForeground(5, notification);
        wakeLock();
    }
   PowerManager.WakeLock wl;

    private void wakeLock() {
        PowerManager pm = (PowerManager) getSystemService(
                Context.POWER_SERVICE);
        wl= pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK
                        | PowerManager.ON_AFTER_RELEASE,
                getString(R.string.pm_tag2));
        wl.acquire();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);


        long triggerTime = SystemClock.elapsedRealtime() + 60000;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
      //  manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        manager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerTime, pi);
        return START_STICKY;
    }



    public static void startService(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(new Intent( activity, AlarmService.class));
        }else{
            activity.startService(new Intent( activity, AlarmService.class));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wl.release();
    }
}
