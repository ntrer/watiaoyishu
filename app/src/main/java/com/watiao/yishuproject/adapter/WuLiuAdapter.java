package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aliyun.video.common.utils.DensityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.util.List;

public class WuLiuAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    private ImageView mdot;
    private View topLine,bottomLine;
    private RelativeLayout rlWuliu;
    private RelativeLayout.LayoutParams lp,lp2;
    private List<String> data;
    public WuLiuAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            helper.setText(R.id.content,item);
            mdot = helper.getView(R.id.dot);
            topLine=helper.getView(R.id.line_top);
            bottomLine=helper.getView(R.id.line_bottom);
            rlWuliu=helper.getView(R.id.rl_wuliu);
            lp= new RelativeLayout.LayoutParams(DensityUtils.dip2px(mContext,16), DensityUtils.dip2px(mContext,16));
            lp2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext,60));
            lp.setMargins(0,DensityUtils.dip2px(mContext,7),0,0);
            if(data.size()==1){
                bottomLine.setVisibility(View.GONE);
                topLine.setVisibility(View.GONE);
            }
            else if(data.size()>1){
                if(item.contains("运输中")){
                    topLine.setVisibility(View.VISIBLE);
                    bottomLine.setVisibility(View.VISIBLE);
                    mdot.setImageResource(R.mipmap.yiyundao);
                }
                else if(item.contains("开始处理")){
                    topLine.setVisibility(View.VISIBLE);
                    bottomLine.setVisibility(View.GONE);
                    mdot.setImageResource(R.mipmap.yichuli);
                }
                else if(item.contains("已签收")){
                    mdot.setImageResource(R.mipmap.yiqianshou);
                    mdot.setLayoutParams(lp);
                    topLine.setVisibility(View.VISIBLE);
                    bottomLine.setVisibility(View.VISIBLE);
                }
                else if(item.contains("收货地址")){
                    mdot.setImageResource(R.mipmap.yiyundao);
                    rlWuliu.setLayoutParams(lp2);
                    topLine.setVisibility(View.INVISIBLE);
                    bottomLine.setVisibility(View.VISIBLE);
                    helper.getView(R.id.content).setVisibility(View.GONE);
                    helper.getView(R.id.time).setVisibility(View.GONE);
                    helper.setText(R.id.zhuangtai,item);
                }
            }

        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

