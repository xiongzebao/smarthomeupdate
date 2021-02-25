package com.ihome.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ihome.base.R;
import com.ihome.base.views.SelectRecyclerView;

import java.util.List;

/**
 * @author xiongbin
 * @description:
 * @date : 2020/12/25 17:17
 */

public class BottomSheetDialogUtils {


    public static interface  onClickListener{

        void onConfirm(List data);
    }


    public  static  void showSelectSheetDialog(Context context, String title, List data, final onClickListener listener){

        if(data==null||data.size()==0){
            return;
        }
        View v = LayoutInflater.from(context).inflate(R.layout.layout_select_dialog,null);

        TextView cancelView = v.findViewById(R.id.tv_cancel);
        TextView confirmView = v.findViewById(R.id.tv_confirm);
        TextView titleView = v.findViewById(R.id.tv_title);

        titleView.setText(title);
        final SelectRecyclerView selectRecyclerView  = v.findViewById(R.id.recycler_view);
        RecyclerViewUtils.setVerticalLayout(context,selectRecyclerView);
        RecyclerViewUtils.addItemDecoration(context,selectRecyclerView);

        selectRecyclerView.setData(data);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context, R.style.CustomSelectDialog);
        bottomSheetDialog.setContentView(v);
        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) v.getParent());

        mDialogBehavior.setPeekHeight(getWindowHeight(context));//dialog的高度
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();

            }
        });

        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null)
                listener.onConfirm(selectRecyclerView.getSelectData());
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }
    private static int getWindowHeight(Context context) {
        Resources res =  context.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels/2;
    }

}
