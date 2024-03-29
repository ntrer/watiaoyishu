package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;

public class HuiTiSouSuoAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {


    public HuiTiSouSuoAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.name,"2018年第六强大赛");
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

