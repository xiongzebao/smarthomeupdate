package com.ihome.smarthomeupdate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.erongdu.wireless.tools.log.MyLog;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.LogEvent;
import com.ihome.smarthomeupdate.service.AlarmService;
import com.ihome.smarthomeupdate.utils.EventBusUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/1/21 15:17
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmService.class);
        isSpeechTime();
        context.startService(i);
    }


    private String  getNowTime(){
        return DateFormat.getTimeInstance().format(new Date());
    }



    private void isSpeechTime(){
        int hour =  Calendar.getInstance().get(Calendar.HOUR);
        int min = Calendar.getInstance().get(Calendar.MINUTE);
        MyLog.e("hour:"+hour+"min:"+min);
        EventBusUtils.sendLog("AlarmReceiver","hour:"+hour+"min:"+min, LogEvent.LOG_DEBUG,false);
        String time = hour+"点"+min+"分";
       // MySocketManager.getInstance().sendPing();
    }

    void setVibratorTimer(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
               // vibrator.cancel();
            }
        };
        timer.schedule(task, 10000);//2秒后执行TimeTask的run方法
    }


}