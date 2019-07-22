package com.watiao.yishuproject.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aliyun.common.utils.DeviceUtils;

public class MarginDecoration4 extends RecyclerView.ItemDecoration{
    private int margin,margin2;

    public MarginDecoration4(Context context) {

        margin = DeviceUtils.dip2px(context,4);
        margin2 = DeviceUtils.dip2px(context,2);

    }

    @Override

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

         //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0

        if (parent.getChildLayoutPosition(view)%3 ==0) {

            outRect.set(0,0,margin2,0);

        }else  if ((parent.getChildLayoutPosition(view)+1)%3 ==0){

            outRect.set(margin2,0,0,0);
        }
        else {
            outRect.set(margin2,0,margin2,0);
        }

    }
}
