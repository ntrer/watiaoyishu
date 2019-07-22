package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;

import java.util.List;

public class SouSuoShiPinAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public SouSuoShiPinAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            helper.setText(R.id.name,"陆军");
            RoundedImageView view = helper.getView(R.id.pic);
            Glide.with(mContext).load(item).skipMemoryCache(false).dontAnimate().into(view);
            helper.addOnClickListener(R.id.like);
        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}

