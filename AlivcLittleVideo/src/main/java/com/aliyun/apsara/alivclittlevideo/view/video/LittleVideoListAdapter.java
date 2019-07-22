package com.aliyun.apsara.alivclittlevideo.view.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.R;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo;
import com.aliyun.apsara.alivclittlevideo.view.musicnote.MusicalNoteLayout;
import com.aliyun.apsara.alivclittlevideo.view.video.videolist.BaseVideoListAdapter;
import com.aliyun.svideo.base.widget.MarqueeText;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo.VideoListBean.STATUS_CENSOR_FAIL;
import static com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo.VideoListBean.STATUS_CENSOR_ON;
import static com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo.VideoListBean.STATUS_CENSOR_SUCCESS;
import static com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo.VideoListBean.STATUS_CENSOR_WAIT;

/**
 * 视频列表的adapter
 * @author xlx
 */
public class LittleVideoListAdapter extends BaseVideoListAdapter<LittleVideoListAdapter.MyHolder,LittleMineVideoInfo.VideoListBean> {
    public static final String TAG = LittleVideoListAdapter.class.getSimpleName();
    private Bitmap bitmap;
    private Bitmap myBitmap;
    private OnItemBtnClick mItemBtnClick;
    private OnItemLikeClick mItemLikeClick;
    private OnItemCommentClick mItemCommentClick;
    private OnItemUnLikeClick mItemUnLikeClick;
    private OnItemGuanZhuClick mItemGuanZhuClick;
    private OnItemAvatarClick mItemAvatarClick;
    private boolean isLike=false;
    public LittleVideoListAdapter(Context context,
                                  List<LittleMineVideoInfo.VideoListBean> urlList) {
        super(context, urlList);
    }

    public LittleVideoListAdapter(Context context) {
        super(context);
    }



    @NonNull
    @Override
    public LittleVideoListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_pager, viewGroup, false);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int position) {
        super.onBindViewHolder(myHolder, position);
        myHolder.mIvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemBtnClick!=null){
                    mItemBtnClick.onDownloadClick(position);
                }
            }
        });

        myHolder.mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemAvatarClick!=null){
                    mItemAvatarClick.onAvatarClick(position);
                }
            }
        });

        myHolder.mJiaguanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemGuanZhuClick!=null){
                    mItemGuanZhuClick.onGuanZhuClick(position);
                    myHolder.mJiaguanzhu.setVisibility(View.GONE);
                }
            }
        });

        myHolder.mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemLikeClick!=null){
                    mItemLikeClick.onLikeClick(position,list.get(position));
                    if(!isLike){
                        myHolder.mLikeButton.setImageResource(R.mipmap.yidianzan);
                        isLike=!isLike;
                    }
                    else {
                        myHolder.mLikeButton.setImageResource(R.mipmap.weidianzan);
                        isLike=!isLike;
                    }
                }
            }
        });

//        myHolder.mLikeButton.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//                if (mItemLikeClick!=null){
//                    mItemLikeClick.onLikeClick(position);
//                }
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//                if (mItemUnLikeClick!=null){
//                    mItemUnLikeClick.onUnLikeClick(position);
//                }
//            }
//        });

//        myHolder.mLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mItemLikeClick!=null){
//                    mItemLikeClick.onLikeClick(position);
//                }
//            }
//        });

        myHolder.mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemCommentClick!=null){
                    mItemCommentClick.onLikeClick(position);
                }
            }
        });


//        new ImageLoaderImpl().loadImage(context,list.get(position).getUser().getAvatarUrl()).into(myHolder.mAvatar);
        Glide.with(context).load(list.get(position).getCoverUrl()).into(myHolder.mAvatar);
        new Thread() {
            public void run() {
                    try {
                        myBitmap = Glide.with(context)
                                .asBitmap()
                                .load(list.get(position).getCoverUrl())
                                .submit(100, 100).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

        }.start();
        myHolder.mMusicalNoteLayout.post(new Runnable() {
            @Override
            public void run() {
                myHolder.mMusicalNoteLayout.setImageBitmap(myBitmap);
                myHolder.mMusicalNoteLayout.invalidate();
            }
        });

        if (STATUS_CENSOR_ON.equals(list.get(position).getCensorStatus())||STATUS_CENSOR_WAIT.equals(list.get(position).getCensorStatus())) {
            myHolder.mCensorLable.setVisibility(View.VISIBLE);
            myHolder.mCensorLable.setText("审核中");
            myHolder.mCensorLable.setBackgroundResource(R.drawable.little_mine_video_item_orange_shape);
        } else if (STATUS_CENSOR_SUCCESS.equals(list.get(position).getCensorStatus())) {
            myHolder.mCensorLable.setVisibility(View.GONE);
        } else if (STATUS_CENSOR_FAIL.equals(list.get(position).getCensorStatus())) {
            myHolder.mCensorLable.setVisibility(View.VISIBLE);
            myHolder.mCensorLable.setText("未通过");
            myHolder.mCensorLable.setBackgroundResource(R.drawable.little_mine_video_item_red_shape);
        }else {

        }
    }



    public final class  MyHolder extends BaseVideoListAdapter.BaseHolder {
        private TextView mCensorLable;
        private ImageView thumb;

        public FrameLayout playerView;
        private ImageView mIvDownload;
        private ViewGroup mRootView;
        private MusicalNoteLayout mMusicalNoteLayout;
        private ImageView mAvatar,mLike,mComment,mJiaguanzhu;
        private TextView mNickname,mTitle;
        private MarqueeText musicName;
        private ImageView mLikeButton;

        private ImageView mIvNarrow;
        MyHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG,"new PlayerManager");
            thumb = itemView.findViewById(R.id.img_thumb);
            playerView = itemView.findViewById(R.id.player_view);
            mIvDownload = itemView.findViewById(R.id.iv_download);
            mRootView = itemView.findViewById(R.id.root_view);
            mAvatar=itemView.findViewById(R.id.iv_avatar);
            mJiaguanzhu=itemView.findViewById(R.id.jiaguanzhu);
//            mLike=itemView.findViewById(R.id.iv_like);
            mLikeButton=itemView.findViewById(R.id.iv_like);
            musicName=itemView.findViewById(R.id.music_name);
            mComment=itemView.findViewById(R.id.iv_comment);
            mNickname=itemView.findViewById(R.id.tv_user_nick_name);
            mTitle=itemView.findViewById(R.id.tv_title);
            mMusicalNoteLayout=itemView.findViewById(R.id.music_note_layout);
            mCensorLable = itemView.findViewById(R.id.tv_censor_lable);
            mIvNarrow = itemView.findViewById(R.id.iv_narrow);
        }

        @Override
        public ImageView getCoverView() {
            return thumb;
        }

        @Override
        public ViewGroup getContainerView() {
            return mRootView;
        }

    }


    public interface OnItemBtnClick{
        void onDownloadClick(int position);
    }

    public void setItemBtnClick(OnItemBtnClick mItemBtnClick) {
        this.mItemBtnClick = mItemBtnClick;
    }



    public interface OnItemAvatarClick{
        void onAvatarClick(int position);
    }

    public void setItemAvatarClick(OnItemAvatarClick mItemAvatarClick) {
        this.mItemAvatarClick = mItemAvatarClick;
    }


    public interface OnItemGuanZhuClick{
        void onGuanZhuClick(int position);
    }

    public void setItemGuanZhuClick(OnItemGuanZhuClick mItemGuanZhuClick) {
        this.mItemGuanZhuClick = mItemGuanZhuClick;
    }


    public interface OnItemLikeClick{
        void onLikeClick(int position, LittleMineVideoInfo.VideoListBean videoListBean);
    }

    public void setItemLikeClick(OnItemLikeClick mItemLikeClick) {
        this.mItemLikeClick = mItemLikeClick;
    }

    public interface OnItemUnLikeClick{
        void onUnLikeClick(int position);
    }

    public void setItemUnLikeClick(OnItemUnLikeClick mItemUnLikeClick) {
        this.mItemUnLikeClick = mItemUnLikeClick;
    }


    public interface OnItemCommentClick{
        void onLikeClick(int position);
    }

    public void setItemCommentClick(OnItemCommentClick mItemCommentClick) {
        this.mItemCommentClick = mItemCommentClick;
    }


}
