package com.watiao.yishuproject.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.List;
import java.util.Random;

public class ReMenShangPinAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private TextView view1;
    private String[] avatar={
            "http://img2.imgtn.bdimg.com/it/u=2325712637,2787833671&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=4103292765,2114076975&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2687791492,3950643482&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=19823225,978633438&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=77925804,3551342478&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=223104370,3654300560&fm=26&gp=0.jpg"
            };

    private Random mRandom=new Random();

    public ReMenShangPinAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            helper.setText(R.id.goods_name,"音乐辅导音乐辅导音乐辅导音乐辅导");
            view1 = helper.getView(R.id.yuanjia);
            view1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            RoundedImageView view = helper.getView(R.id.pic);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(view);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

