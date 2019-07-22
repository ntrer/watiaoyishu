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

import de.hdodenhof.circleimageview.CircleImageView;

public class ZongHeSouSuoAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private CircleImageView view2;
    private String[] avatar={
            "https://www.feizl.com/upload2007/allimg/180821/1934564642-3.jpg",
            "http://img5q.duitang.com/uploads/item/201505/26/20150526033548_NjZxS.thumb.224_0.jpeg",
            "https://img2.woyaogexing.com/2018/08/26/4e10d2d5ea0d45ffb4afe463a96b22e0!400x400.jpeg",
            "http://diy.qqjay.com/u2/2012/1015/ce912cbb8f78ab9f77846dac2797903b.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462121211,2082143757&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1700214908,2470782977&fm=26&gp=0.jpg",
            "https://img2.woyaogexing.com/2018/08/25/03fab6cb97cc42ab93b53d0ec6d4a967!400x400.jpeg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1515722289,3500721683&fm=26&gp=0.jpg",
            "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg",
            "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg"
    };

    private Random mRandom=new Random();


    public ZongHeSouSuoAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.user_name,"野史小哥");
            RoundedImageView view1= helper.getView(R.id.pic);
            view2= helper.getView(R.id.user_pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view1);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view2);
            helper.addOnClickListener(R.id.user_pic);
            helper.addOnClickListener(R.id.pic);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

