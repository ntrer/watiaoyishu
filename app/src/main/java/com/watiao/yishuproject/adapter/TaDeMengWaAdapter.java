package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.util.List;

public class TaDeMengWaAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public TaDeMengWaAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
             helper.setText(R.id.name,"郑勇");
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

