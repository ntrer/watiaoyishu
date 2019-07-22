package com.watiao.yishuproject.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PicAdapter2 extends BaseQuickAdapter<Bitmap,BaseViewHolder> {

    private ImageView mImageView;
    private boolean isEdit=false;




    public PicAdapter2(int layoutResId, @Nullable List<Bitmap> data) {
        super(layoutResId, data);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bitmap item) {
        mImageView=helper.getView(R.id.pic);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        item.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(mContext).load(bytes).into(mImageView);
        mImageView.setTag(R.id.pic,item);
        helper.addOnClickListener(R.id.shanchu);
    }

}
