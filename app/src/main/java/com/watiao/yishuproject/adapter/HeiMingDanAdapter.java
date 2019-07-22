package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class HeiMingDanAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {


    private CircleImageView view;
    private String[] avatar={
            "https://www.feizl.com/upload2007/allimg/180821/1934564642-3.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1424823011,978222784&fm=26&gp=0.jpg",
            "https://img2.woyaogexing.com/2018/08/26/4e10d2d5ea0d45ffb4afe463a96b22e0!400x400.jpeg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2607115084,1552254985&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2201347361,3771616725&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=185314036,1423291746&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3591130792,3892287987&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2391983213,1526643229&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1343580836,2315574162&fm=26&gp=0.jpg"};

    private Random mRandom=new Random();

    public HeiMingDanAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.name,item.getChuangjianrenName());
            view= helper.getView(R.id.avatar);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

