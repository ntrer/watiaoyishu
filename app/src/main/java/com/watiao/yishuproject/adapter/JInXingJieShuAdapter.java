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

public class JInXingJieShuAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private RoundedImageView view;
    private Random mRandom=new Random();
    private String[] avatar={
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=681595219,2718451334&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=448600705,4137103051&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=133386525,3445961887&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3978690721,2436380795&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462121211,2082143757&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3011362627,2771859711&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1739394362,3896328523&fm=26&gp=0.jpg",
    };
    public JInXingJieShuAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.saishi_name,"2019年第十届少儿唱比赛16强大区2019年第十届少儿唱比赛16强大区");
            view= helper.getView(R.id.saishi_pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.saishifenlei_ph).into(view);
            helper.getView(R.id.yijieshu).setVisibility(View.GONE);
            helper.getView(R.id.jinxingzhong).setVisibility(View.VISIBLE);

        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

