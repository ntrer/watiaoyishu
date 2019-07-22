package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.KeHu;

import java.util.List;

public class KeHuAdapter extends BaseQuickAdapter<KeHu.DataBean.CustomerListBean,BaseViewHolder> {


    public KeHuAdapter(int layoutResId, @Nullable List<KeHu.DataBean.CustomerListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeHu.DataBean.CustomerListBean item) {
        try {
            helper.setText(R.id.name,item.getUserName());
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

