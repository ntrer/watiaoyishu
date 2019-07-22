package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.util.List;

public class HotSearchHuaTiAdapter extends BaseQuickAdapter<String,BaseViewHolder> {



    public HotSearchHuaTiAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            helper.setText(R.id.tab_name,item);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

