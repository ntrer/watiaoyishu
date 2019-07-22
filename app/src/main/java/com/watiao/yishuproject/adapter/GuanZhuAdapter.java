package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;

public class GuanZhuAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {


    private String searchKey;
    private TextView mTextView;
    public GuanZhuAdapter(int layoutResId, @Nullable List<Test.DataListBean> data, String searchKey) {
        super(layoutResId, data);
        this.searchKey=searchKey;
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            mTextView= helper.getView(R.id.name);
            if(searchKey!=null&&!searchKey.equals("")&&searchKey.equals(item.getChuangjianrenName())){
                mTextView.setTextColor(mContext.getResources().getColor(R.color.weixin));
            }
            helper.setText(R.id.name,item.getChuangjianrenName());
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

