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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectionService.startService(this);
        finish();
    }


}



