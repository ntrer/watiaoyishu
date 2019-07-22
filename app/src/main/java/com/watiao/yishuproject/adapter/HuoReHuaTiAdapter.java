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

public class HuoReHuaTiAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private Random mRandom=new Random();
    private RoundedImageView view;
    private CircleImageView view2;
    private String[] avatar={
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2559127466,189081508&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1954565452,3019814628&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=4075524659,371985529&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=823437387,4045356394&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3188245964,2690302008&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1604511439,676553279&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2753917429,1891999323&fm=26&gp=0.jpg",
            };

    public HuoReHuaTiAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.name,"王小鱼");
            view = helper.getView(R.id.pic);
            view2 = helper.getView(R.id.user_pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view2);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

