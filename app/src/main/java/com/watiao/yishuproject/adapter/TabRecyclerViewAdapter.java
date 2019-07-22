package com.watiao.yishuproject.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.util.List;

public class TabRecyclerViewAdapter  extends BaseQuickAdapter<String,BaseViewHolder> {

    //先声明一个int成员变量
    private int thisPosition;
    //再定义一个int类型的返回值方法
    public int getthisPosition() {
        return thisPosition;
    }

    //其次定义一个方法用来绑定当前参数值的方法
    //此方法是在调用此适配器的地方调用的，此适配器内不会被调用到
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public TabRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
        TextView view = holder.getView(R.id.tab_name);
        View view1=holder.getView(R.id.line);
        if (positions == getthisPosition()) {
            view.setEnabled(true);
            view.setTextSize(18f);
            view1.setBackgroundColor(mContext.getResources().getColor(R.color.tabline_color));
        } else {
            view.setEnabled(false);
            view.setTextSize(12f);
            view1.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
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