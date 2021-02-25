package com.ihome.smarthomeupdate.module.mine.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ihome.base.views.SelectRecyclerView;
import com.ihome.smarthomeupdate.R;
import com.ihome.smarthomeupdate.module.mine.adapter.ExceptionAdapter;

/**
 * @author xiongbin
 * @description:
 * @date : 2021/1/4 15:41
 */

public class ExceptionRecyclerView extends SelectRecyclerView {


    public ExceptionRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ExceptionRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExceptionRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected BaseQuickAdapter getMyAdapter() {
        return new ExceptionAdapter(R.layout.layout_exception_item,list);

    }
}
