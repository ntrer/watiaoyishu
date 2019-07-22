package com.watiao.yishuproject.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.adapter.CommentAdapter;
import com.aliyun.apsara.alivclittlevideo.view.popuplayout.PopupLayout;
import com.aliyun.common.utils.ToastUtil;
import com.aliyun.video.common.utils.ToastUtils;
import com.example.ijkplayer.player.IjkVideoView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.VideoRecyclerViewAdapter;
import com.watiao.yishuproject.utils.DataUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

public class ShiPinXiangQing2Activity extends AppCompatActivity {

    private static final int REQUEST_AT = 1010;
    private IjkVideoView ijkVideoView;
    private RecyclerView recyclerView;
    private ImageView iv_back;
    private View bottomMenu,bottomMenu2;
    private PopupLayout mPopupLayout,mPopupLayout2;
    private boolean useRadius=true;//是否使用圆角特性
    private ImageView mImageView,mAt;
    private TextView mTextView;
    private String str;
    private ArrayList<String> data=new ArrayList<>();
    private CommentAdapter mCommentAdapter;
    private String name;
    private EditText ed_comment;
    private ImageView at;
    private RecyclerView mRecyclerView,mRootView;
    private VideoRecyclerViewAdapter mVideoRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
        setContentView(R.layout.activity_shi_pin_xiang_qing2);
        initView();
        bottomMenu2=View.inflate(this, com.aliyun.apsara.alivclittlevideo.R.layout.share_layout,null);
        mPopupLayout2=PopupLayout.init(this,bottomMenu2);
        mPopupLayout2.setUseRadius(useRadius);
        for (int i = 0; i < 30; i++) {
            data.add("@郑秀兰");
        }
    }

    private View getRootView() {
        return findViewById(R.id.content_container);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        iv_back=findViewById(R.id.back);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mVideoRecyclerViewAdapter=new VideoRecyclerViewAdapter(DataUtil.getDouYinVideoList(), this);
        final PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mVideoRecyclerViewAdapter);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                 ijkVideoView = view.findViewById(R.id.video_view);
                if (ijkVideoView != null) {
                    ijkVideoView.stopPlayback();
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE: //滚动停止
                        ijkVideoView = snapHelper.findSnapView(layoutManager).findViewById(R.id.video_view);
                        if (ijkVideoView != null) {
                            ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
                            ijkVideoView.start();
                        }
                        break;
                }
            }
        });

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //自动播放第一个
            View view = recyclerView.getChildAt(0);
            ijkVideoView = view.findViewById(R.id.video_view);
            ijkVideoView.start();
            }
        },300);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ijkVideoView!=null){
                    finish();
                }
            }
        });

        mVideoRecyclerViewAdapter.setItemBtnClick(new VideoRecyclerViewAdapter.OnItemBtnClick() {
            @Override
            public void onDownloadClick(int position) {
                mPopupLayout2.show(PopupLayout.POSITION_BOTTOM);
            }
        });

        mVideoRecyclerViewAdapter.setItemLikeClick(new VideoRecyclerViewAdapter.OnItemLikeClick() {
            @Override
            public void onLikeClick(int position) {

                ToastUtil.showToast(ShiPinXiangQing2Activity.this,"你点击了喜欢");
            }
        });


        mVideoRecyclerViewAdapter.setItemCommentClick(new VideoRecyclerViewAdapter.OnItemCommentClick() {
            @Override
            public void onLikeClick(int position) {
                showComment();
            }
        });

    }



    private void showComment() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(com.aliyun.apsara.alivclittlevideo.R.layout.comment_layout);
        bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        View root=bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior=BottomSheetBehavior.from(root);
        mImageView=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.close);
        mTextView=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.write_comment);
        mRecyclerView=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.rv_comments);
        mAt=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.at);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
//        mCommentAdapter = new CommentAdapter(context, data);
        mRecyclerView.setAdapter(mCommentAdapter);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        mAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent=new Intent(ShiPinXiangQing2Activity.this,GuanZhuDeRenActivity.class);
                   startActivityForResult(intent,REQUEST_AT);
                }catch (Exception e)
                {
                    ToastUtils.show(ShiPinXiangQing2Activity.this,e.toString());
                }
            }
        });

        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(com.aliyun.apsara.alivclittlevideo.R.color.bottomdialog_bg));
        bottomSheetDialog.show();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ijkVideoView != null) {
            ijkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        }
    }

    private void showDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(com.aliyun.apsara.alivclittlevideo.R.layout.comment_dialog);
        ed_comment=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.ed_comment);
        at=bottomSheetDialog.findViewById(com.aliyun.apsara.alivclittlevideo.R.id.at);
        ed_comment.setText(name);
        View root=bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior=BottomSheetBehavior.from(root);
        behavior.setHideable(false);
        bottomSheetDialog.show();
        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent=new Intent(ShiPinXiangQing2Activity.this,GuanZhuDeRenActivity.class);
                    startActivityForResult(intent,REQUEST_AT);
                }catch (Exception e)
                {
                    ToastUtils.show(ShiPinXiangQing2Activity.this,e.toString());
                }
            }
        });

        ed_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    ToastUtils.show(ShiPinXiangQing2Activity.this,"发送");
                    return true;
                }
                return false;
            }
        });

        showSoft();
    }


    private void showSoft() {

        Handler handle =new Handler();

        handle.postDelayed(new Runnable() {

            @Override

            public void run() {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }

        }, 100);

    }



    public void setName(String name){
        this.name=name;
        if(name!=null){
            mTextView.setText("@"+name);
            ed_comment.setText("@"+name);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_AT){
            if(!ijkVideoView.isPlaying()){
                ijkVideoView.start();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(ijkVideoView!=null){
            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(ijkVideoView!=null){
                    ijkVideoView.release();
                }
            }
        });
    }


}
