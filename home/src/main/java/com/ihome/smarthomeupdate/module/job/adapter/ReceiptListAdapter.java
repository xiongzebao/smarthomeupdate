package com.ihome.smarthomeupdate.module.job.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ihome.smarthomeupdate.module.job.model.ReceiptListBean;

import java.util.List;

public class ReceiptListAdapter extends BaseQuickAdapter<ReceiptListBean, BaseViewHolder> {
    public ReceiptListAdapter(int layoutResId, List<ReceiptListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ReceiptListBean receiptListBean) {

    }
}
