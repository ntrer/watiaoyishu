package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.util.HashMap;
import java.util.List;

public class BiaoQianAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    private List<String> datas;
    public static HashMap<Integer, Boolean> isSelected;
    public BiaoQianAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.datas=data;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
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

