package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;
import java.util.Random;

public class DingDanAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private RoundedImageView view2;
    private Random mRandom=new Random();
    private String[] avatar={
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2975436939,2465391407&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3182413199,1316731390&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3092067609,1415086667&fm=26&gp=0.jpg",
            };
   private String leixing;
    public DingDanAdapter(int layoutResId, @Nullable List<Test.DataListBean> data, String leixing) {
        super(layoutResId, data);
        this.leixing=leixing;
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.dingdan_name,"2019年第十届少儿唱比赛16强大区2019年第十届少儿唱比赛16强大区");
            view2= helper.getView(R.id.dingdan_pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view2);
            if(leixing.equals("全部")||leixing.equals("待发货")){
                helper.getView(R.id.jiesuan).setVisibility(View.VISIBLE);
                helper.getView(R.id.jiesuan2).setVisibility(View.GONE);
                helper.getView(R.id.chakan).setVisibility(View.GONE);
                helper.getView(R.id.chakan2).setVisibility(View.GONE);
            }
           else if(leixing.equals("待收货")){
                helper.getView(R.id.jiesuan).setVisibility(View.GONE);
                helper.getView(R.id.jiesuan2).setVisibility(View.VISIBLE);
                helper.getView(R.id.chakan).setVisibility(View.GONE);
                helper.getView(R.id.chakan2).setVisibility(View.VISIBLE);
                helper.setText(R.id.chakan2,"查看物流");
            }
            else {
                helper.getView(R.id.jiesuan).setVisibility(View.VISIBLE);
                helper.getView(R.id.jiesuan2).setVisibility(View.GONE);
                helper.getView(R.id.chakan).setVisibility(View.VISIBLE);
                helper.getView(R.id.chakan2).setVisibility(View.GONE);
                helper.setText(R.id.chakan,"待评价");
            }

            helper.addOnClickListener(R.id.chakan);
            helper.addOnClickListener(R.id.chakan2);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

