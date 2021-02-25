package com.ihome.smarthomeupdate.module.base;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.erongdu.wireless.tools.log.MyLog;
import com.erongdu.wireless.tools.utils.ActivityManager;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.github.rubensousa.floatingtoolbar.FloatingToolbarMenuBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ihome.base.base.BaseActivity;
import com.ihome.base.utils.DialogUtils;
import com.ihome.base.utils.ScenceUtils;
import com.ihome.smarthomeupdate.applockscreen.service.LockScreenService;
import com.ihome.smarthomeupdate.module.adapter.DeviceListAdapter;
import com.ihome.smarthomeupdate.module.base.communicate.MyBluetoothManager;
import com.ihome.smarthomeupdate.module.base.communicate.MySocketManager;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.BTMessageEvent;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.BaseMessageEvent;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.LogEvent;
import com.ihome.smarthomeupdate.receiver.AdminReceiver;
import com.ihome.smarthomeupdate.service.AlarmService;
import com.ihome.smarthomeupdate.utils.CrashHandlerUtils;
import com.ihome.smarthomeupdate.utils.EventBusUtils;
import com.ihome.smarthomeupdate.utils.SystemTTSUtils;
import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.module.base.communicate.ICommunicate;
import com.ihome.smarthomeupdate.receiver.BluetoothMonitorReceiver;
import com.ihome.smarthomeupdate.service.ConnectionService;
import com.ihome.smarthomeupdate.service.FloatingService;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeActivity extends Activity {
    static final String URL1 =  "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_appupdate);
        ConnectionService.startService(this);
          new File(Utils.PARENT_PATH).mkdirs();

        DownloadTask task = createDownloadTask(URL1,"test.apk");

      //  task.enqueue(listener);

        task.enqueue(new DownloadListener() {
            @Override
            public void taskStart(@NonNull DownloadTask task) {
                MyLog.e("taskStart-------------");
            }

            @Override
            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
                    MyLog.e("downloadFromBeginning");
            }

            @Override
            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

            }

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {

            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
                MyLog.e("fetchProgress"+increaseBytes);
            }

            @Override
            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                MyLog.e("fetchEnd"+contentLength);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
                MyLog.e("taskEnd");
            }
        });

    }

    private DownloadTask createDownloadTask( String url,String filename) {
        return new DownloadTask.Builder( url, new File(Utils.PARENT_PATH)) //设置下载地址和下载目录，这两个是必须的参数
                .setFilename( filename)//设置下载文件名，没提供的话先看 response header ，再看 url path(即启用下面那项配置)
                .setFilenameFromResponse(false)//是否使用 response header or url path 作为文件名，此时会忽略指定的文件名，默认false
                .setPassIfAlreadyCompleted(true)//如果文件已经下载完成，再次下载时，是否忽略下载，默认为true(忽略)，设为false会从头下载
                .setConnectionCount(1)  //需要用几个线程来下载文件，默认根据文件大小确定；如果文件已经 split block，则设置后无效
                .setPreAllocateLength(false) //在获取资源长度后，设置是否需要为文件预分配长度，默认false
                .setMinIntervalMillisCallbackProcess(100) //通知调用者的频率，避免anr，默认3000
                .setWifiRequired(false)//是否只允许wifi下载，默认为false
                .setAutoCallbackToUIThread(true) //是否在主线程通知调用者，默认为true
                //.setHeaderMapFields(new HashMap<String, List<String>>())//设置请求头
                //.addHeader(String key, String value)//追加请求头
                .setPriority(0)//设置优先级，默认值是0，值越大下载优先级越高
                .setReadBufferSize(4096)//设置读取缓存区大小，默认4096
                .setFlushBufferSize(16384)//设置写入缓存区大小，默认16384
                .setSyncBufferSize(65536)//写入到文件的缓冲区大小，默认65536
                .setSyncBufferIntervalMillis(2000) //写入文件的最小时间间隔，默认2000
                .build();
    }



}



