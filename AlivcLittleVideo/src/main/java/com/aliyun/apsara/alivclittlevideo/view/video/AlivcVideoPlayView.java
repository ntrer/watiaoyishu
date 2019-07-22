package com.aliyun.apsara.alivclittlevideo.view.video;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.apsara.alivclittlevideo.R;
import com.aliyun.apsara.alivclittlevideo.adapter.CommentAdapter;
import com.aliyun.apsara.alivclittlevideo.constants.LittleVideoParamConfig;
import com.aliyun.apsara.alivclittlevideo.net.data.LittleMineVideoInfo;
import com.aliyun.apsara.alivclittlevideo.sts.StsTokenInfo;
import com.aliyun.apsara.alivclittlevideo.view.ShareDialog;
import com.aliyun.apsara.alivclittlevideo.view.popuplayout.PopupLayout;
import com.aliyun.apsara.alivclittlevideo.view.video.videolist.AlivcVideoListView;
import com.aliyun.apsara.alivclittlevideo.view.video.videolist.IVideoSourceModel;
import com.aliyun.apsara.alivclittlevideo.view.video.videolist.OnTimeExpiredErrorListener;
import com.aliyun.common.utils.DensityUtil;
import com.aliyun.downloader.JniDownloader;
import com.aliyun.player.IPlayer;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.svideo.base.widget.CircleProgressBar;
import com.aliyun.video.common.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 播放界面, 负责initPlayerSDK以及各种view
 *
 * @author xlx
 */
public class AlivcVideoPlayView extends FrameLayout {


    private static final String TAG = "AlivcVideoPlayView";
    private Context context;
    private AlivcVideoListView videoListView;

    /**
     * 刷新数据listener (下拉刷新和上拉加载)
     */
    private AlivcVideoListView.OnRefreshDataListener onRefreshDataListener;
    /**
     * 视频缓冲加载view
     */
    private LoadingView mLoadingView;


    /**
     * 分享选择提示窗
     *
     * @param context
     */

    private ShareDialog mShareDialog;

    private View bottomMenu,bottomMenu2;
    private PopupLayout mPopupLayout,mPopupLayout2;
    private boolean useRadius=true;//是否使用圆角特性
    private RecyclerView mRecyclerView;
    private ImageView mImageView,mAt;
    private TextView mTextView;
    private String str;
    private ArrayList<String> data=new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    private String name;
    private EditText ed_comment;
    private ImageView at;
    private boolean isLike=false;
    /**
     * 视频删除点击事件
     */
    private OnVideoDeleteListener mOutOnVideoDeleteListener;
    private LittleVideoListAdapter mVideoAdapter;

    public AlivcVideoPlayView(@NonNull Context context) {
        this(context, null);
    }

    public AlivcVideoPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        for (int i = 0; i < 30; i++) {
            data.add("@郑秀兰");
        }
        init();
    }






    private void init() {
//        bottomMenu=View.inflate(context,R.layout.comment_layout,null);
        bottomMenu2=View.inflate(context,R.layout.share_layout,null);
//        mRecyclerView=bottomMenu.findViewById(R.id.rv_comments);
//        mImageView=bottomMenu.findViewById(R.id.close);
//        mTextView=bottomMenu.findViewById(R.id.write_comment);
//        mPopupLayout=PopupLayout.init(context,bottomMenu);
        mPopupLayout2=PopupLayout.init(context,bottomMenu2);
//        mPopupLayout.setUseRadius(useRadius);
        mPopupLayout2.setUseRadius(useRadius);

        initPlayListView();
        initLoadingView();
    }
    private Dialog mDownloadDialog;
    private CircleProgressBar mProgressBar;
    private TextView mTvProgress;
    private FrameLayout mDownloadContent;

    private void showDownloadDialog() {
        if (mDownloadDialog == null) {
            mDownloadDialog = new Dialog(getContext(), com.aliyun.svideo.editor.aliyunvideocommon.R.style.CustomDialogStyle);



            View view = View.inflate(context, R.layout.alivc_little_dialog_progress, null);
            mProgressBar = view.findViewById(R.id.alivc_little_progress);
            mProgressBar.setProgress(0);
            mTvProgress = view.findViewById(R.id.alivc_little_tv_progress);
            mTvProgress.setText("0%");
            mDownloadContent = view.findViewById(R.id.alivc_tittle_fl_download_content);
            view.findViewById(R.id.alivc_little_iv_cancel_download).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissDownloadProgress();

                    if (mDownloadManager != null) {
                        mDownloadManager.stop();
                    }
                }
            });
            mDownloadDialog.setCancelable(false);
            mDownloadDialog.setContentView(view);
            Window dialogWindow = mDownloadDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = d.widthPixels; // 高度设置为屏幕的高
            lp.height = d.heightPixels; // 高度设置为屏幕的宽
            lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
            dialogWindow.setAttributes(lp);
        }
        mDownloadDialog.show();

    }



    private void showComment() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.comment_layout);
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        View root=bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior=BottomSheetBehavior.from(root);
        mImageView=bottomSheetDialog.findViewById(R.id.close);
        mTextView=bottomSheetDialog.findViewById(R.id.write_comment);
        mRecyclerView=bottomSheetDialog.findViewById(R.id.rv_comments);
        mAt=bottomSheetDialog.findViewById(R.id.at);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mCommentAdapter = new CommentAdapter(context,data);
        mRecyclerView.setAdapter(mCommentAdapter);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mAt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    //参数是包名，类全限定名，注意直接用类名不行
                    ComponentName cn=new ComponentName("com.watiao.yishuproject",
                            "com.watiao.yishuproject.activity.GuanZhuDeRenActivity");
                    intent.setComponent(cn);
                    context.startActivity(intent);
                    videoListView.pausePlay();
                }catch (Exception e)
                {
                    ToastUtils.show(context,e.toString());
                }
            }
        });

        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.bottomdialog_bg));
        bottomSheetDialog.show();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }



    private void showDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.comment_dialog);
        ed_comment=bottomSheetDialog.findViewById(R.id.ed_comment);
        at=bottomSheetDialog.findViewById(R.id.at);
        ed_comment.setText(name);
        View root=bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior=BottomSheetBehavior.from(root);
        behavior.setHideable(false);
        bottomSheetDialog.show();
        at.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    //参数是包名，类全限定名，注意直接用类名不行
                    ComponentName cn=new ComponentName("com.watiao.yishuproject",
                            "com.watiao.yishuproject.activity.GuanZhuDeRenActivity");
                    intent.setComponent(cn);
                    context.startActivity(intent);
                    videoListView.pausePlay();
                }catch (Exception e)
                {
                    ToastUtils.show(context,e.toString());
                }
            }
        });

        ed_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    ToastUtils.show(context,"发送");
                    return true;
                }
                return false;
            }
        });
//        final Dialog dialog =new Dialog(context);
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        dialog.setContentView(R.layout.comment_dialog);
//
//        Window dialogWindow = dialog.getWindow();
//
//        dialogWindow.setGravity(Gravity.BOTTOM);
//
//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
//
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        dialogWindow.setAttributes(lp);
//
//        dialog.show();
//
//        //查找控件
//
//        final EditText edComment = dialog.findViewById(R.id.ed_comment);
////        final Button btnSubmit=dialog.findViewById(R.id.fasong);
////        TextView btnFaBu = dialog.findViewById(R.id.btn_fabu_pl);
//
////        btnSubmit.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////
////            public void onClick(View v) {
////
////                dialog.dismiss();
////
////                //提交之后要讲str清空
////
////                str ="";
////
////                //展示框复原
////
////                mTextView.setText("请输入您的评论内容");
////
////            }
////
////        });
//
//        //判断有没有编辑过评论内容 如果编辑过就在显示出来
//
//        if (!TextUtils.isEmpty(str)) {
//
//            edComment.setText(str);
//
//            edComment.setSelection(str.length());//将光标移至文字末尾
//
//        }
//
////添加监听
//
//        edComment.addTextChangedListener(new TextWatcher() {
//
//            @Override
//
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//
//            public void afterTextChanged(Editable editable) {
//
//                str =edComment.getText().toString();
//
//                mTextView.setText(str);
//
//            }
//
//        });

        showSoft();

//        dismissSofo(dialog);

    }


    private void showSoft() {

        Handler handle =new Handler();

        handle.postDelayed(new Runnable() {

            @Override

            public void run() {

                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }

        }, 100);

    }



    public void setName(String name){
        this.name=name;
        if(name!=null){
            mTextView.setText("@"+name);
        }
    }


    public void rePlay(){
        videoListView.resumePlay();
    }


    /**
     * 下载sdk核心类
     */
    private JniDownloader mDownloadManager;

    private int mClickPosition;

    /**
     * 初始化视频列表
     */
    private void initPlayListView() {
        videoListView = new AlivcVideoListView(context);
        //创建adapter，需要继承BaseVideoListAdapter
        mVideoAdapter = new LittleVideoListAdapter(context);

        mVideoAdapter.setItemBtnClick(new LittleVideoListAdapter.OnItemBtnClick() {
            @Override
            public void onDownloadClick(int position) {
//                mClickPosition = position;
//
//                if (mShareDialog == null) {
//                    mShareDialog = new ShareDialog();
//                    mShareDialog.setItemSelectedListenr(new ShareDialog.OnItemSelectedListener() {
//                        @Override
//                        public void onDownloadClick() {
//                            // 2018/12/3 开始下载
//                            LittleMineVideoInfo.VideoListBean video = mVideoAdapter.getDataList().get(mClickPosition);
//                            if (STATUS_CENSOR_ON.equals(video.getCensorStatus()) || STATUS_CENSOR_WAIT.equals(video.getCensorStatus())) {
//                                ToastUtils.show(context, "视频正在审核中，暂不支持下载");
//                            } else if (STATUS_CENSOR_FAIL.equals(video.getCensorStatus())) {
//                                ToastUtils.show(context, "视频审核未通过，暂不支持下载");
//                            } else {
//                                //每次下载都调用该方法
//                                if (mDownloadManager == null) {
//                                    initDownloadManager();
//                                }
//                                VidSts vidSts = video.getVidStsSource();
//                                vidSts.setRegion("cn-shanghai");
//                                mDownloadManager.prepare(vidSts);
//                            }
//                        }
//
//                        @Override
//                        public void onDeleteClick() {
//                            if (mOutOnVideoDeleteListener != null) {
//                                List<LittleMineVideoInfo.VideoListBean> dataList = mVideoAdapter.getDataList();
//                                if (dataList != null && dataList.size() > 0 && mClickPosition >= 0
//                                        && mClickPosition < dataList.size()) {
//                                    mOutOnVideoDeleteListener.onDeleteClick(dataList.get(mClickPosition));
//                                }
//                            }
//                        }
//
//                    });
//                }
//                LittleMineVideoInfo.VideoListBean video = mVideoAdapter.getDataList().get(mClickPosition);
//                mShareDialog.show(getFragmentManager(), "ShareDialog");
//                if (video.getUser().getUserId().equals(AlivcLittleUserManager.getInstance().getUserInfo(getContext()).getUserId())) {
//                    mShareDialog.setDeleteVisible(true);
//                } else {
//                    mShareDialog.setDeleteVisible(false);
//                }

                mPopupLayout2.show(PopupLayout.POSITION_BOTTOM);

            }
        });



        mVideoAdapter.setItemLikeClick(new LittleVideoListAdapter.OnItemLikeClick() {
            @Override
            public void onLikeClick(int position, LittleMineVideoInfo.VideoListBean videoListBean) {
                if(!isLike){
                    ToastUtils.show(context,"你點贊了");
                    isLike=!isLike;
                }
                else {
                    isLike=!isLike;
                    ToastUtils.show(context,"你取消點贊了");
                }
            }
        });
//
//        mVideoAdapter.setItemUnLikeClick(new LittleVideoListAdapter.OnItemUnLikeClick() {
//            @Override
//            public void onUnLikeClick(int position) {
//
//                ToastUtil.showToast(context,"你取消喜欢");
//            }
//        });

        mVideoAdapter.setItemAvatarClick(new LittleVideoListAdapter.OnItemAvatarClick() {
            @Override
            public void onAvatarClick(int position) {
                try
                {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    //参数是包名，类全限定名，注意直接用类名不行
                    ComponentName cn=new ComponentName("com.watiao.yishuproject",
                            "com.watiao.yishuproject.activity.TaDeGeRenZhuYeActivity");
                    intent.setComponent(cn);
                    context.startActivity(intent);
                    videoListView.pausePlay();
                }catch (Exception e)
                {
                    ToastUtils.show(context,e.toString());
                }

            }
        });

        mVideoAdapter.setItemGuanZhuClick(new LittleVideoListAdapter.OnItemGuanZhuClick() {
            @Override
            public void onGuanZhuClick(int position) {
              ToastUtils.show(context,"已关注");
            }
        });

        mVideoAdapter.setItemCommentClick(new LittleVideoListAdapter.OnItemCommentClick() {
            @Override
            public void onLikeClick(int position) {
                showComment();
//             mPopupLayout.show(PopupLayout.POSITION_BOTTOM);
            }
        });


        mVideoAdapter.setItemCommentClick(new LittleVideoListAdapter.OnItemCommentClick() {
            @Override
            public void onLikeClick(int position) {
                showComment();
//             mPopupLayout.show(PopupLayout.POSITION_BOTTOM);
            }
        });


        //给AlivcVideoListView实例化对象添加adapter
        videoListView.setAdapter(mVideoAdapter);
        videoListView.setVisibility(VISIBLE);
        //设置sdk播放器实例化对象数量
        videoListView.setPlayerCount(1);
        //设置下拉、上拉监听进行加载数据
        videoListView.setOnRefreshDataListener(new AlivcVideoListView.OnRefreshDataListener() {
            @Override
            public void onRefresh() {
                if (onRefreshDataListener != null) {
                    onRefreshDataListener.onRefresh();
                }
            }

            @Override
            public void onLoadMore() {
                if (onRefreshDataListener != null) {
                    onRefreshDataListener.onLoadMore();
                }
            }
        });
        //设置视频缓冲监听
        videoListView.setLoadingListener(new IPlayer.OnLoadingStatusListener() {
            @Override
            public void onLoadingBegin() {
                mLoadingView.start();
            }

            @Override
            public void onLoadingEnd() {
                mLoadingView.cancle();
            }

            @Override
            public void onLoadingProgress(int var1, float var2) {

            }
        });
        //设置鉴权过期监听，刷新鉴权信息
        videoListView.setTimeExpiredErrorListener(new OnTimeExpiredErrorListener() {

            @Override
            public void onTimeExpiredError() {
                if (mStsInfoExpiredListener != null) {
                    mStsInfoExpiredListener.onTimeExpired();
                }
            }
        });
        //添加到布局中
        addSubView(videoListView);
    }


    /**
     * 播放、下载、上传过程中鉴权过期时提供的回调消息
     */
    private OnStsInfoExpiredListener mStsInfoExpiredListener;

    public void setOnStsInfoExpiredListener(
        OnStsInfoExpiredListener mTimeExpiredErrorListener) {
        this.mStsInfoExpiredListener = mTimeExpiredErrorListener;
    }

    /**
     * 初始化下载管理器
     */
    private void initDownloadManager() {
        mDownloadManager = new JniDownloader(context);

        mDownloadManager.setOnPreparedListener(new JniDownloader.OnPreparedListener() {
            @Override
            public void onPrepared(MediaInfo mediaInfo) {
                List<TrackInfo> mTrackInfo = mediaInfo.getTrackInfos();
                if (mTrackInfo == null || mTrackInfo.size() == 0) {
                    Toast.makeText(context, "暂无资源", Toast.LENGTH_SHORT).show();
                    dismissDownloadProgress();
                    return;
                }
                mDownloadManager.selectItem(pickDownloadQualityMedia(mTrackInfo));
                //File videoFile = new File(mDownloadManager.getFilePath());
                //if (videoFile.exists()) {
                //    Toast.makeText(context, "视频已存在", Toast.LENGTH_SHORT).show();
                //    dismissDownloadProgress();
                //    return;
                //}
                mDownloadManager.start();
                showDownloadDialog();
            }
        });
        mDownloadManager.setOnProgressListener(new JniDownloader.OnProgressListener() {
            @Override
            public void onDownloadingProgress(int i) {
                Log.d(TAG,  "onDownloadingProgress:" + i);
                mTvProgress.setText(i + "%");
                mProgressBar.setProgress(i);
            }

            @Override
            public void onProcessingProgress(int i) {
                Log.d(TAG,  "onProcessingProgress:" + i);
            }
        });
        mDownloadManager.setOnCompletionListener(new JniDownloader.OnCompletionListener() {
            @Override
            public void onCompletion() {
                Log.d(TAG, "onCompletion");
                MediaScannerConnection.scanFile(context, new String[] {mDownloadManager.getFilePath()},
                    new String[] {"video/*"},
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
                dismissDownloadProgress();
                //mDownloadManager.release();
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
            }
        });
        mDownloadManager.setOnErrorListener(new JniDownloader.OnErrorListener() {
            @Override
            public void onError(ErrorInfo errorInfo) {

                dismissDownloadProgress();
                Toast.makeText(context, "下载失败," + errorInfo.getMsg(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError" + mDownloadManager.getFilePath() + errorInfo.getMsg());
            }
        });

        //设置保存路径。请确保有SD卡访问权限。
        File file = new File(LittleVideoParamConfig.DIR_DOWNLOAD);
        if (!file.exists()) {
            file.mkdir();
        }
        mDownloadManager.setSaveDir(file.getAbsolutePath());
    }

    private void dismissDownloadProgress() {
        if (mDownloadDialog != null) {
            mDownloadDialog.dismiss();
            mTvProgress.setText("0%");
            mProgressBar.setProgress(0);
        }
    }
    /**
     * 获取视频质量最高的视频
     *
     * @param list
     * @return
     */
    private int pickDownloadQualityMedia(List<TrackInfo> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            TrackInfo trackInfo = list.get(i);
            if ("LD".equals(trackInfo.getVodDefinition())){
                index = trackInfo.getIndex();
            }
        }
        return index;
    }

    private void initLoadingView() {
        mLoadingView = new LoadingView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                5);
        params.setMargins(0, 0, 0, DensityUtil.dip2px(getContext(), 4));
        params.gravity = Gravity.BOTTOM;
        addView(mLoadingView, params);
    }

    /**
     * addSubView 添加子view到布局中
     *
     * @param view 子view
     */
    private void addSubView(View view) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        addView(view, params);
    }

    /**
     * 刷新视频列表数据
     *
     * @param datas
     */
    public void refreshVideoList(List<LittleMineVideoInfo.VideoListBean> datas) {
        List<IVideoSourceModel> videoList = new ArrayList<>();
        videoList.addAll(datas);
        videoListView.refreshData(videoList);
        //取消加载loading
        mLoadingView.cancle();

    }

    /**
     * 刷新视频列表数据
     *
     * @param datas
     * @param position
     */
    public void refreshVideoList(List<LittleMineVideoInfo.VideoListBean> datas, int position) {
        List<IVideoSourceModel> videoList = new ArrayList<>();
        videoList.addAll(datas);
        videoListView.refreshData(videoList, position);
        //取消加载loading
        mLoadingView.cancle();
    }



    /**
     * 添加更多视频
     *
     * @param datas
     */
    public void addMoreData(List<LittleMineVideoInfo.VideoListBean> datas) {
        List<IVideoSourceModel> videoList = new ArrayList<>();
        videoList.addAll(datas);
        videoListView.addMoreData(videoList);
        //取消加载loading
        mLoadingView.cancle();
    }

    /**
     * 设置下拉刷新数据listener
     *
     * @param listener OnRefreshDataListener
     */
    public void setOnRefreshDataListener(AlivcVideoListView.OnRefreshDataListener listener) {
        this.onRefreshDataListener = listener;
    }

    public void onStart() {

    }

    public void onResume() {
        videoListView.resumePlay();
    }

    public void onStop() {
        mLoadingView.cancle();
    }

    public void onPause() {

        videoListView.pausePlay();

    }

    public void onDestroy() {
        context = null;

        //if (mDownloadManager != null) {
        //    mDownloadManager.clearDownloadInfoListener();
        //}
    }

    /**
     * 视频列表获取失败
     */
    public void loadFailure() {
        mLoadingView.cancle();
        videoListView.loadFailure();
    }



    private FragmentActivity mActivity;

    private FragmentManager getFragmentManager() {
        FragmentManager fm = null;
        if (mActivity != null) {
            fm = mActivity.getSupportFragmentManager();
        } else {
            Context mContext = getContext();
            if (mContext instanceof FragmentActivity) {
                fm = ((FragmentActivity)mContext).getSupportFragmentManager();
            }
        }
        return fm;
    }

    /**
     * 刷新sts信息
     *
     * @param tokenInfo
     */
    public void refreshStsInfo(StsTokenInfo tokenInfo) {
    }

    /**
     * 删除按钮点击listener
     */
    public interface OnVideoDeleteListener {
        /**
         * 删除视频
         *
         * @param videoId 视频id
         */
        void onDeleteClick(LittleMineVideoInfo.VideoListBean videoId);
    }

    public void setOnVideoDeleteListener(
        OnVideoDeleteListener mOutOnVideoDeleteListener) {
        this.mOutOnVideoDeleteListener = mOutOnVideoDeleteListener;
    }
    /**
     * 移除当前播放的视频
     */
    public void removeCurrentPlayVideo() {
        videoListView.removeCurrentPlayVideo();
    }

}
