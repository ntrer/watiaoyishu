package com.aliyun.apsara.alivclittlevideo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.R;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VH>{

    private Random mRandom=new Random();
    private String[] avatar={
            "https://www.feizl.com/upload2007/allimg/180821/1934564642-3.jpg",
            "http://img5q.duitang.com/uploads/item/201505/26/20150526033548_NjZxS.thumb.224_0.jpeg",
            "https://img2.woyaogexing.com/2018/08/26/4e10d2d5ea0d45ffb4afe463a96b22e0!400x400.jpeg",
            "http://diy.qqjay.com/u2/2012/1015/ce912cbb8f78ab9f77846dac2797903b.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1462121211,2082143757&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1700214908,2470782977&fm=26&gp=0.jpg",
            "https://img2.woyaogexing.com/2018/08/25/03fab6cb97cc42ab93b53d0ec6d4a967!400x400.jpeg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1515722289,3500721683&fm=26&gp=0.jpg",
            "http://tupian.qqjay.com/tou2/2017/0120/39b35eed7d7000fc214d3f5198032f11.jpg"};

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView name;
        public CircleImageView mCircleImageView;
        public VH(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.user_name);
            mCircleImageView = v.findViewById(R.id.user_pic);
        }
    }

    private List<String> mDatas;
    private Context mContext;
    public CommentAdapter(Context context, List<String> data) {
        this.mDatas = data;
        this.mContext = context;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.name.setText(mDatas.get(position));
        Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(holder.mCircleImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new VH(v);
    }
}

