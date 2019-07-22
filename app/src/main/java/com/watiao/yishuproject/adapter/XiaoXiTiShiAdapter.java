package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;

public class XiaoXiTiShiAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

   private LinearLayout type1,type2,type3,type4,type5;
    public XiaoXiTiShiAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            type1=helper.getView(R.id.type1);
            type2=helper.getView(R.id.type2);
            type3=helper.getView(R.id.type3);
            type4=helper.getView(R.id.type4);
            type5=helper.getView(R.id.type5);
            if(helper.getAdapterPosition()==0){
                type1.setVisibility(View.VISIBLE);
                helper.setText(R.id.name,"系统通知");
                helper.setText(R.id.message,"后台自定义消息");
                type2.setVisibility(View.GONE);
                type3.setVisibility(View.GONE);
                type4.setVisibility(View.GONE);
                type5.setVisibility(View.GONE);
            }
            else if(helper.getAdapterPosition()==1){
                type1.setVisibility(View.GONE);
                helper.setText(R.id.name2,"私信");
                helper.setText(R.id.message2,"鬼工上的所有未拉黑用户给你发消息 ");
                type2.setVisibility(View.VISIBLE);
                type3.setVisibility(View.GONE);
                type4.setVisibility(View.GONE);
                type5.setVisibility(View.GONE);
            }
            else if(helper.getAdapterPosition()==2){
                type1.setVisibility(View.GONE);
                helper.setText(R.id.name3,"@消息");
                helper.setText(R.id.message3,"小美@你发并表评论“我知道了我整明白了，你好呀”");
                type2.setVisibility(View.GONE);
                type3.setVisibility(View.VISIBLE);
                type4.setVisibility(View.GONE);
                type5.setVisibility(View.GONE);
            }
            else if(helper.getAdapterPosition()==3){
                type1.setVisibility(View.GONE);
                helper.setText(R.id.name4,"@订单消息");
                helper.setText(R.id.message4,"您的订单20190823001已经发货");
                type2.setVisibility(View.GONE);
                type3.setVisibility(View.GONE);
                type4.setVisibility(View.VISIBLE);
                type5.setVisibility(View.GONE);
            }
            else if(helper.getAdapterPosition()==5){
                type1.setVisibility(View.GONE);
                helper.setText(R.id.name5,"@消积分通知");
                helper.setText(R.id.message5,"恭喜您，您完成了XXX任务获得234积分");
                type2.setVisibility(View.GONE);
                type3.setVisibility(View.GONE);
                type4.setVisibility(View.GONE);
                type5.setVisibility(View.VISIBLE);
            }
            else {
                type1.setVisibility(View.VISIBLE);
                helper.setText(R.id.name,"系统通知");
                helper.setText(R.id.message,"后台自定义消息");
                type2.setVisibility(View.GONE);
                type3.setVisibility(View.GONE);
                type4.setVisibility(View.GONE);
                type5.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

