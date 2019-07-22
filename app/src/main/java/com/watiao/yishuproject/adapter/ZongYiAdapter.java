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

public class ZongYiAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private RoundedImageView view;
    private String[] avatar={
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=681595219,2718451334&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=448600705,4137103051&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=133386525,3445961887&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3978690721,2436380795&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462121211,2082143757&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3011362627,2771859711&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1739394362,3896328523&fm=26&gp=0.jpg",
            };

    private Random mRandom=new Random();

    public ZongYiAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.name,"2018年第六强大赛");
            view = helper.getView(R.id.pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).skipMemoryCache(false).into(view);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

