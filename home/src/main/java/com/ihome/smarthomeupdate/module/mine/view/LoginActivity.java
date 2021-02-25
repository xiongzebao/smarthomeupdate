package com.ihome.smarthomeupdate.module.mine.view;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.erongdu.wireless.tools.log.MyLog;
import com.erongdu.wireless.tools.utils.TextUtil;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.ihome.base.base.BaseActivity;
import com.ihome.base.network.NetworkUtil;

import com.ihome.base.utils.SharedInfo;
import com.ihome.base.utils.SystemBarUtils;
import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.api.ApiService;
import com.ihome.smarthomeupdate.api.STClient;
import com.ihome.smarthomeupdate.base.KTRequestCallBack;
import com.ihome.smarthomeupdate.base.ResBase;
import com.ihome.smarthomeupdate.common.KTConstant;
import com.ihome.smarthomeupdate.module.mine.model.LoginBean;
import com.ihome.smarthomeupdate.module.mine.model.UserInfoBean;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    EditText userNameView;
    EditText passwordView;

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_login);
        SystemBarUtils.setAndroidNativeLightStatusBar(this,true);
        userNameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        userNameView.setText( (String)SharedInfo.getInstance().getValue(KTConstant.LOGIN_NAME,"") );

    }

    public void onClick(View view){
       String username =  userNameView.getText().toString();
       String password = passwordView.getText().toString();
        if(TextUtil.isEmpty_new(username)||TextUtil.isEmpty_new(password)){
            ToastUtil.toast("请输入用户名或密码");
            return;
        }
       login(username,password);
    }




    private void getUserInfo(){
        Call<ResBase<UserInfoBean>> login = STClient.getService(ApiService.class).getUserInfo();
        NetworkUtil.showCutscenes(login);
        login.enqueue(new KTRequestCallBack<ResBase<UserInfoBean>>() {
            @Override
            public void onSuccess(Call<ResBase<UserInfoBean>> call, Response<ResBase<UserInfoBean>> response) {
                MyLog.e(response.body().toString());

            }

            @Override
            public void onFailed(Call<ResBase<UserInfoBean>> call, Response<ResBase<UserInfoBean>> response) {
                super.onFailed(call, response);

            }

            @Override
            public void onFailure(Call<ResBase<UserInfoBean>> call, Throwable t) {
                super.onFailure(call, t);
                ToastUtil.toast(t.getMessage());
            }
        });
    }



    public void login(final String username, String password) {
        Call<ResBase<LoginBean>> login = STClient.getService(ApiService.class).login(username.trim(),password.trim());
        NetworkUtil.showCutscenes(login);
        login.enqueue(new KTRequestCallBack<ResBase<LoginBean>>() {
            @Override
            public void onSuccess(Call<ResBase<LoginBean>> call, Response<ResBase<LoginBean>> response) {
                if(!response.body().resultdata.isSuccess()){
                    ToastUtil.toast(response.body().resultdata.getMessage());
                    return;
                }
                SharedInfo.getInstance().saveValue(KTConstant.TOKEN,response.body().resultdata.getToken());
                SharedInfo.getInstance().saveEntity(response.body().resultdata);
                SharedInfo.getInstance().saveValue(KTConstant.LOGIN_NAME,username);

                ToastUtil.toast("登录成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                      //  ActivityManager.startActivity(MainActivity.class);
                        finish();


                    }
                },500);

            }

            @Override
            public void onFailed(Call<ResBase<LoginBean>> call, Response<ResBase<LoginBean>> response) {
                super.onFailed(call, response);
                ToastUtil.toast(response.body().resultdata.getMessage());
                MyLog.e(response.body().toString());
            }

            @Override
            public void onFailure(Call<ResBase<LoginBean>> call, Throwable t) {
                super.onFailure(call, t);
                ToastUtil.toast(t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}