package com.watiao.yishuproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ijkplayer.player.IjkVideoView;
import com.example.ijkplayer.player.PlayerConfig;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.VideoBean;
import com.watiao.yishuproject.controller.VideoController;
import com.watiao.yishuproject.ui.musicnote.MusicalNoteLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Jshunjie
 * @date 2018/5/25
 * @description 视频数据适配器
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder> {
    private List<VideoBean> videos;
    private Context context;
    private OnItemBtnClick mItemBtnClick;
    private OnItemLikeClick mItemLikeClick;
    private OnItemCommentClick mItemCommentClick;
    private Bitmap bitmap;
    public VideoRecyclerViewAdapter(List<VideoBean> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_video_play, parent, false);
        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {
        VideoBean videoBean = videos.get(position);
        Glide.with(context)
                .load(videoBean.getThumb())
                .into(holder.controller.getThumb());
        holder.ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        holder.ijkVideoView.setUrl(videoBean.getUrl());
        holder.ijkVideoView.setTitle(videoBean.getTitle());

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemBtnClick!=null){
                    mItemBtnClick.onDownloadClick(position);
                }
            }
        });

        holder.mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemLikeClick!=null){
                    mItemLikeClick.onLikeClick(position);
                }
            }
        });

        holder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemCommentClick!=null){
                    mItemCommentClick.onLikeClick(position);
                }
            }
        });
        Bitmap bitmap = returnBitMap("https://imgs.aixifan.com/style/image/201907/p4FFYini8Rb96ODE1obErnPTbM7d8zpn.jpg");
        holder.mMusicalNoteLayout.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private IjkVideoView ijkVideoView;
        private VideoController controller;
        private PlayerConfig mPlayerConfig;
        private MusicalNoteLayout mMusicalNoteLayout;
        private ImageView mAvatar,mLike,mComment,mShare;
        VideoHolder(View itemView) {
            super(itemView);
            ijkVideoView = itemView.findViewById(R.id.video_view);
            ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
            controller = new VideoController(context);
            ijkVideoView.setVideoController(controller);
            mAvatar=itemView.findViewById(R.id.iv_avatar);
            mLike=itemView.findViewById(R.id.iv_like);
            mComment=itemView.findViewById(R.id.iv_comment);
            mMusicalNoteLayout=itemView.findViewById(R.id.music_note_layout);
            mShare=itemView.findViewById(R.id.iv_share);
            mPlayerConfig = new PlayerConfig.Builder()
                    .enableCache()
                    .setLooping()
                    .savingProgress()
                    .usingSurfaceView()
                    .addToPlayerManager()
                    .build();
            ijkVideoView.setPlayerConfig(mPlayerConfig);
        }
    }

    public interface OnItemBtnClick{
        void onDownloadClick(int position);
    }

    public void setItemBtnClick(OnItemBtnClick mItemBtnClick) {
        this.mItemBtnClick = mItemBtnClick;
    }



    public interface OnItemLikeClick{
        void onLikeClick(int position);
    }

    public void setItemLikeClick(OnItemLikeClick mItemLikeClick) {
        this.mItemLikeClick = mItemLikeClick;
    }


    public interface OnItemCommentClick{
        void onLikeClick(int position);
    }

    public void setItemCommentClick(OnItemCommentClick mItemCommentClick) {
        this.mItemCommentClick = mItemCommentClick;
    }

    public Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }

}