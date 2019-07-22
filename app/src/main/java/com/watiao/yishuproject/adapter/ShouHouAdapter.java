package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;
import java.util.Random;

public class ShouHouAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {


    private RoundedImageView view2;
    private Random mRandom=new Random();
    private String[] avatar={
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2975436939,2465391407&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3182413199,1316731390&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3092067609,1415086667&fm=26&gp=0.jpg",
    };

    public ShouHouAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            view2= helper.getView(R.id.pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view2);
            helper.setText(R.id.dingdanhao,"订单号");
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

