package com.ihome.smarthomeupdate.applockscreen.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.ihome.smarthomeupdate.applockscreen.activity.LockScreenActivity;
import com.ihome.smarthomeupdate.applockscreen.util.Parser;


public class LockScreenReceiver extends BroadcastReceiver {
    public static String TAG = "LockScreenReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
          //  EventBusUtils.sendLog(TAG,"LockScreenReceiver ACTION_SCREEN_OFF", LogEvent.LOG_IMPORTANT,true);
            if (Parser.sPhoneCallState == TelephonyManager.CALL_STATE_IDLE) { // 手机状态为未来电的空闲状态
                // 判断锁屏界面是否已存在，如果已存在就先finish，防止多个锁屏出现
                if (!Parser.KEY_GUARD_INSTANCES.isEmpty()) {
                    for (Activity activity : Parser.KEY_GUARD_INSTANCES) {
                        activity.finish();
                    }
                }
                Intent lockScreen = new Intent(context, LockScreenActivity.class);
                lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(lockScreen);
            }
        } else {

            Parser.killBackgroundProcess(context);
        }
    }
}



