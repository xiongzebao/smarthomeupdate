package com.ihome.smarthomeupdate.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.erongdu.wireless.tools.log.MyLog;
import com.ihome.smarthomeupdate.module.base.Utils;

import java.io.File;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/2/25 18:35
 */

public class PackageBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_PACKAGE_ADDED)) {
            // 应用安装
            // 获取应用包名，和要监听的应用包名做对比
            String packName = intent.getData().getSchemeSpecificPart();
            MyLog.e(packName+"应用安装");
            if(packName.equals("com.ihome.smarthome")){
                new File(Utils.PARENT_PATH+"/test.apk").delete();
            }
        }else if (TextUtils.equals(intent.getAction(),Intent.ACTION_PACKAGE_REMOVED)){
            // 应用卸载
            // 获取应用包名
            String packName = intent.getData().getSchemeSpecificPart();

            MyLog.e(packName+"已经卸载");
        }else if (TextUtils.equals(intent.getAction(),Intent.ACTION_PACKAGE_REPLACED)){
            // 应用覆盖
            // 获取应用包名
            String packName = intent.getData().getSchemeSpecificPart();
            MyLog.e(packName+"应用覆盖安装");
        }
    }
}
