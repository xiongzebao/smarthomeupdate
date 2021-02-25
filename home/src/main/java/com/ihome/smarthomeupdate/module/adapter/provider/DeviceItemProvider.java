package com.ihome.smarthomeupdate.module.adapter.provider;

import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.erongdu.wireless.tools.utils.ToastUtil;
import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.module.adapter.DeviceListAdapter;
import com.ihome.smarthomeupdate.module.base.DeviceItem;
import com.ihome.smarthomeupdate.utils.SpanUtils;

/**
 * @author ChayChan
 * @description 图片消息条目的provider
 * @date 2018/3/21  14:43
 */
public class DeviceItemProvider extends BaseItemProvider<DeviceItem, BaseViewHolder> {

    @Override
    public int viewType() {
        return DeviceListAdapter.TYPE_DEVICE_BASE;
    }

    @Override
    public int layout() {
        return R.layout.layout_device_list_item;
    }

    @Override
    public void convert(BaseViewHolder helper, DeviceItem data, int position) {
        //处理相关业务逻辑
     /*   ImageView iv = helper.getView(R.id.iv_img);
        Glide.with(mContext).load(data.imgUrl).into(iv);*/
        helper.setText(R.id.tv_name,data.getName());
        setCookerConnectState(helper.getView(R.id.tv_status),data.getConnectStatus());
    }

    private void setCookerConnectState(TextView tv_status,int  isConnect) {

        if (tv_status == null) {
            ToastUtil.toast("tv_status==null");
        }

        String state = isConnect==1 ? "已连接" : "未连接";

        int color = isConnect==1 ? Color.BLUE : Color.RED;

        SpanUtils.newInstance().append("连接状态:", Color.BLACK)
                .append(state, color)
                .setText(tv_status);
    }
    @Override
    public void onClick(BaseViewHolder helper, DeviceItem data, int position) {
        //单击事件
        Toast.makeText(mContext, "Click: " + data.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, DeviceItem data, int position) {
        //长按事件
        Toast.makeText(mContext, "longClick: " + data.getName(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
