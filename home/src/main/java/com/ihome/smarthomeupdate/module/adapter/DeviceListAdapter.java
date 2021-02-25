package com.ihome.smarthomeupdate.module.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.ihome.smarthomeupdate.module.adapter.provider.DeviceItemProvider;
import com.ihome.smarthomeupdate.module.base.DeviceConstant;
import com.ihome.smarthomeupdate.module.base.DeviceItem;

import java.util.List;

/**
 * @author ChayChan
 * @description: 消息列表的adapter
 * @date 2018/3/21  14:40
 */

public class DeviceListAdapter extends MultipleItemRvAdapter<DeviceItem, BaseViewHolder> {

    public static final int TYPE_DEVICE_BASE = 0;



     public void refreshItem(String macAddress,int connectState){
         for (int i=0;  i<getData().size();i++) {
             DeviceItem item = getData().get(i);
             item.setConnectStatus(connectState);
             if(item.getDeviceId().equals(macAddress)){
                 notifyItemChanged(i);
                return;
             }
         }
     }

    public DeviceListAdapter(@Nullable List<DeviceItem> data) {
        super(data);

        //构造函数若有传其他参数可以在调用finishInitialize()之前进行赋值，赋值给全局变量
        //这样getViewType()和registerItemProvider()方法中可以获取到传过来的值
        //getViewType()中可能因为某些业务逻辑，需要将某个值传递过来进行判断，返回对应的viewType
        //registerItemProvider()中可以将值传递给ItemProvider

        //If the constructor has other parameters, it needs to be assigned before calling finishInitialize() and assigned to the global variable
        // This getViewType () and registerItemProvider () method can get the value passed over
        // getViewType () may be due to some business logic, you need to pass a value to judge, return the corresponding viewType
        //RegisterItemProvider() can pass value to ItemProvider

        finishInitialize();
    }

    @Override
    protected int getViewType(DeviceItem message) {
        //返回对应的viewType
        if (message.getItemType()== DeviceConstant.BT_COOKER) {
            return TYPE_DEVICE_BASE;
        }
        return 0;
    }


    @Override
    public void registerItemProvider() {
        //注册相关的条目provider

        mProviderDelegate.registerProvider(new DeviceItemProvider());
    }

}
