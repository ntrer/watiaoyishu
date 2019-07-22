package com.watiao.yishuproject.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PicAdapter  extends BaseQuickAdapter<Bitmap,BaseViewHolder> {

    private ImageView mImageView;
    private boolean isEdit=false;


    public void setDelete(boolean isEdit) {
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }

    public PicAdapter(int layoutResId, @Nullable List<Bitmap> data) {
        super(layoutResId, data);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
        if(!item.equals(mImageView.getTag(R.id.pic))){
            Glide.with(mContext).load(bytes).into(mImageView);
            mImageView.setTag(R.id.pic,item);
        }


        if(isEdit){
            helper.getView(R.id.shanchu).setVisibility(View.VISIBLE);
        }
        else {
            helper.getView(R.id.shanchu).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.shanchu);
    }

}
