package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.HashMap;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;

public class XuanzeHaiZiAdapter2 extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private SmoothCheckBox mSmoothCheckBox;
    public static HashMap<Integer, Boolean> isSelected;
    private List<Test.DataListBean> datas;
    public XuanzeHaiZiAdapter2(int layoutResId, @Nullable List<Test.DataListBean> data) {
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
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.name,"陆军");
            mSmoothCheckBox=helper.getView(R.id.checkbox);
            mSmoothCheckBox.setChecked(isSelected.get(helper.getAdapterPosition()),false);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

