package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

public class VideoAdapter extends BaseQuickAdapter<AlbumFile,BaseViewHolder> {

    private ImageView mImageView;
    private boolean isEdit=false;


    public void setDelete(boolean isEdit) {
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }

    public VideoAdapter(int layoutResId, @Nullable ArrayList<AlbumFile> data) {
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
    protected void convert(BaseViewHolder helper, AlbumFile item) {
        mImageView=helper.getView(R.id.video);
        if(!item.equals(mImageView.getTag(R.id.pic))){
            Glide.with(mContext).load(item.getThumbPath()).into(mImageView);
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
