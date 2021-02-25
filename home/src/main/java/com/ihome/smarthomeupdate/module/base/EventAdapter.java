package com.ihome.smarthomeupdate.module.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ihome.smarthomeupdate.R;

import java.util.List;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/1/15 10:45
 */

public class EventAdapter extends BaseQuickAdapter<Event, BaseViewHolder> {

    public EventAdapter(int layoutResId, @Nullable List<Event> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Event item) {
        helper.addOnClickListener(R.id.iv_delete);
        helper.setText(R.id.tv_message,item.getEvent());
    }
}
