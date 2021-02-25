package com.ihome.smarthomeupdate;


import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ihome.smarthomeupdate.module.base.LogFragment;
import com.ihome.smarthomeupdate.module.base.SmartHomeFragment;
import com.ihome.smarthomeupdate.module.base.eventbusmodel.LogEvent;
import com.ihome.base.base.BaseActivity;
import com.ihome.base.base.BaseFragment;
import com.ihome.smarthomeupdate.base.MyApplication;
import com.ihome.smarthomeupdate.databinding.ActivityMainBinding;

import com.ihome.smarthomeupdate.module.main.view.MineFragment;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    SmartHomeFragment homeFrag;
    LogFragment jobFragment;
    MineFragment mineFragment;

    final String TAG_HOME = "tag_home";
    final String TAG_JOB = "tag_job";
    final String TAG_MINE = "tag_mine";

    public BottomNavigationBar.OnTabSelectedListener listener = new BottomNavigationBar.OnTabSelectedListener() {


        @Override
        public void onTabSelected(int position) {
            switch (position) {
                case 0:
                    showFragment(homeFrag, TAG_HOME);
                    break;
                case 1:
                    showFragment(jobFragment, TAG_JOB);
                    jobFragment.refresh();
                    break;
                case 2:
                    showFragment(mineFragment, TAG_MINE);
                    break;
            }
        }

        @Override
        public void onTabUnselected(int position) {
            switch (position) {
                case 0:
                    hideFragment(homeFrag, TAG_HOME);
                    break;
                case 1:
                    hideFragment(jobFragment, TAG_JOB);
                    break;
                case 2:
                    hideFragment(mineFragment, TAG_MINE);
                    break;
            }
        }

        @Override
        public void onTabReselected(int position) {

        }
    };


    private void hideFragment(BaseFragment fragment, String tag) {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (null == fragment) {
            fragment = (BaseFragment) manager.findFragmentByTag(tag);
        }
        if (null != fragment) {
            transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();
    }


    private void showFragment(BaseFragment fragment, String tag) {
        FragmentManager manager = MainActivity.this.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (null == fragment) {
            fragment = (BaseFragment) manager.findFragmentByTag(tag);
        }
        if (null == fragment) {
            switch (tag) {
                case TAG_HOME:
                    fragment = SmartHomeFragment.newInstance();
                    homeFrag = (SmartHomeFragment) fragment;
                    break;
                case TAG_JOB:
                    fragment =LogFragment.newInstance(LogEvent.LOG_NOTICE);
                    jobFragment = (LogFragment) fragment;
                    break;
                case TAG_MINE:
                    fragment = MineFragment.newInstance();
                    mineFragment = (MineFragment) fragment;
                    break;
            }

            transaction.add(R.id.content, fragment, tag);
        } else {
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();

    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void bindView() {
        MyApplication.mainActivity = this;

    }






    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.mainActivity = null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.ihome.smarthomeupdate.module.base.SmartHomeFragment.REQUEST_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                //bindFloatingService();
               // SmartHomeFragment.startFloatingService();
            }
        }
    }


}
