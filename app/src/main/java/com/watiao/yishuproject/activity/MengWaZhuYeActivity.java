package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.donkingliang.labels.LabelsView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.PicAdapter;
import com.watiao.yishuproject.adapter.VideoAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.ui.MarginDecoration3;
import com.watiao.yishuproject.ui.MarginDecoration4;
import com.watiao.yishuproject.ui.WorkaroundNestedScrollView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MengWaZhuYeActivity extends BaseActivity {


    private static final int REQUEST_GERENZILIAO = 1011;
    private static final int REQUEST_PAISHE = 1012;
    private static final int REQUEST_YANYI = 1013;
    private static final int REQUEST_HUOJIANG = 1014;
    @BindView(R.id.movie_image)
    ImageView mMovieImage;
    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.dingwei)
    ImageView mDingwei;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.zhuye)
    TextView mZhuye;
    @BindView(R.id.zhuye_line)
    View mZhuyeLine;
    @BindView(R.id.gerenjingli)
    TextView mGerenjingli;
    @BindView(R.id.gerenjingli_line)
    View mGerenjingliLine;
    @BindView(R.id.shipin)
    TextView mShipin;
    @BindView(R.id.shipin_line)
    View mShipinLine;
    @BindView(R.id.year)
    TextView mYear;
    @BindView(R.id.shengao)
    TextView mShengao;
    @BindView(R.id.tizhong)
    TextView mTizhong;
    @BindView(R.id.title)
    LinearLayout mTitle;
    @BindView(R.id.labels)
    LabelsView mLabels;
    @BindView(R.id.title_img1)
    ImageView mTitleImg1;
    @BindView(R.id.edit1)
    ImageView mEdit1;
    @BindView(R.id.paishe_img1)
    ImageView mPaisheImg1;
    @BindView(R.id.paishejingli)
    TextView mPaishejingli;
    @BindView(R.id.paishe)
    RelativeLayout mPaishe;
    @BindView(R.id.edit2)
    ImageView mEdit2;
    @BindView(R.id.title_img2)
    ImageView mTitleImg2;
    @BindView(R.id.yanyi_img)
    ImageView mYanyiImg;
    @BindView(R.id.yangyijingli)
    TextView mYangyijingli;
    @BindView(R.id.yanyi)
    RelativeLayout mYanyi;
    @BindView(R.id.title_img3)
    ImageView mTitleImg3;
    @BindView(R.id.edit3)
    ImageView mEdit3;
    @BindView(R.id.huojiang_img)
    ImageView mHuojiangImg;
    @BindView(R.id.huojiangjingli)
    TextView mHuojiangjingli;
    @BindView(R.id.huojiang)
    RelativeLayout mHuojiang;
    @BindView(R.id.line_photo)
    View mLinePhoto;
    @BindView(R.id.photo_title)
    TextView mPhotoTitle;
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    @BindView(R.id.img_edit)
    ImageView mImgEdit;
    @BindView(R.id.title2)
    RelativeLayout mTitle2;
    @BindView(R.id.rv_pic)
    RecyclerView mRvPic;
    @BindView(R.id.line_video)
    View mLineVideo;
    @BindView(R.id.video_title)
    TextView mVideoTitle;
    @BindView(R.id.vid_add)
    ImageView mVidAdd;
    @BindView(R.id.vid_edit)
    ImageView mVidEdit;
    @BindView(R.id.title3)
    RelativeLayout mTitle3;
    @BindView(R.id.rv_video)
    RecyclerView mRvVideo;
    @BindView(R.id.logo)
    ImageView mLogo;

    @BindView(R.id.edit_user)
    ImageView mEditUser;
    @BindView(R.id.nslv)
    WorkaroundNestedScrollView mNslv;
    @BindView(R.id.nan)
    ImageView mNan;
    @BindView(R.id.nv)
    ImageView mNv;

    private int nestedScrollViewTop;
    private boolean isExpand = true;


    private PicAdapter mPicAdapter;
    private VideoAdapter mVideoAdapter;
    private Image mImage;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ArrayList<AlbumFile> mAlbumFiles2 = new ArrayList<>();
    private ArrayList<AlbumFile> mAlbumFiles3 = new ArrayList<>();
    private ImageView mImageView, mImageView2;
    private Uri fileUri = null;
    private File imgFile;
    private Bitmap mBitmap;
    private Bitmap newBitmap;
    private List<Bitmap> mapList = new ArrayList<>();
    private ArrayList<Bitmap> NewmapList = new ArrayList<>();
    private List<File> mapFile = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();
    private boolean isFirstPic = true;
    private boolean isFirstVid = true;
    private boolean isEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setStatusbar();
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mZhuye.setTextColor(getResources().getColor(R.color.textColor4));
        ArrayList<String> label = new ArrayList<>();
        label.add("Android");
        label.add("IOS");
        label.add("前端");
        label.add("微信小程序开发");
        label.add("后台");
        label.add("微信开发");
        label.add("游戏开发");
        mLabels.setLabels(label);

        mMovieImage.setImageResource(R.mipmap.memgwa);

    }


    private void setStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //下面这一行呢是android4.0起推荐大家使用的将布局延伸到状态栏的代码，配合5.0的设置状态栏颜色可谓天作之合
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @Override
    public int setLayout() {
        return R.layout.activity_meng_wa_zhu_ye;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.edit1)
    void editPaishe() {
        Intent intent = new Intent(MengWaZhuYeActivity.this, TianJiaPaiSheJIngLiActivity.class);
        startActivityForResult(intent, REQUEST_PAISHE);
    }

    @OnClick(R.id.edit2)
    void editYanyi() {
        Intent intent = new Intent(MengWaZhuYeActivity.this, TianJiaYanYiJIngLiActivity.class);
        startActivityForResult(intent, REQUEST_YANYI);
    }

    @OnClick(R.id.edit3)
    void editHuojiang() {
        Intent intent = new Intent(MengWaZhuYeActivity.this, TianJIaHuoJIangJIngLiActivity.class);
        startActivityForResult(intent, REQUEST_HUOJIANG);
    }

    @OnClick(R.id.zhuye)
    void zhuye() {
        mZhuye.setTextColor(getResources().getColor(R.color.textColor4));
        mGerenjingli.setTextColor(getResources().getColor(R.color.textColor2));
        mShipin.setTextColor(getResources().getColor(R.color.textColor2));
        mZhuyeLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mGerenjingliLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mAppbar.setExpanded(true, true);
        mNslv.fling(0);//添加上这句滑动才有效
        mNslv.smoothScrollTo(0, 0);
    }

    @OnClick(R.id.gerenjingli)
    void gerenjingli() {
        mZhuye.setTextColor(getResources().getColor(R.color.textColor2));
        mGerenjingli.setTextColor(getResources().getColor(R.color.textColor4));
        mShipin.setTextColor(getResources().getColor(R.color.textColor2));
        mZhuyeLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mGerenjingliLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mAppbar.setExpanded(false);
        mNslv.fling(450);//添加上这句滑动才有效
        mNslv.smoothScrollTo(0, 450);
    }

    @OnClick(R.id.shipin)
    void shipin() {
        mZhuye.setTextColor(getResources().getColor(R.color.textColor2));
        mGerenjingli.setTextColor(getResources().getColor(R.color.textColor2));
        mShipin.setTextColor(getResources().getColor(R.color.textColor4));
        mZhuyeLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mGerenjingliLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mShipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mAppbar.setExpanded(false);
        mNslv.fling(mTitle3.getTop());//添加上这句滑动才有效
        mNslv.smoothScrollTo(0, mTitle3.getTop());
    }


    public void scrollByDistance(int dy) {
        if (nestedScrollViewTop == 0) {
            int[] intArray = new int[2];
            mNslv.getLocationOnScreen(intArray);
            nestedScrollViewTop = intArray[1];
        }
        int distance = dy - nestedScrollViewTop;//必须算上nestedScrollView本身与屏幕的距离
        mNslv.fling(distance);//添加上这句滑动才有效
        mNslv.smoothScrollBy(0, distance);
    }


    @OnClick(R.id.edit_user)
    void edit_user() {
        Intent intent = new Intent(MengWaZhuYeActivity.this, MengWaZiLiaoActivity.class);
        startActivityForResult(intent, REQUEST_GERENZILIAO);
    }


    @OnClick(R.id.img_add)
    void img_add() {
        Album.image(MengWaZhuYeActivity.this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(10)
                .checkedList(mAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        getBitmap(mAlbumFiles);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @OnClick(R.id.img_edit)
    void img_edit() {
        if (NewmapList.size() != 0) {
            isEdit = !isEdit;
            mPicAdapter.setDelete(isEdit);
        } else {
            ToastUtils.showLong("请先选择图片");
        }
    }


    @OnClick(R.id.vid_add)
    void vid_add() {
        Album.video(MengWaZhuYeActivity.this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(10)
                .checkedList(mAlbumFiles2)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles2 = result;
                        mAlbumFiles3 = result;
                        showData2(mAlbumFiles3);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }


    @OnClick(R.id.vid_edit)
    void vid_edit() {
        if (mAlbumFiles2.size() != 0) {
            isEdit = !isEdit;
            mVideoAdapter.setDelete(isEdit);
        } else {
            ToastUtils.showLong("请先选择视频");
        }
    }

    private void showData2(final ArrayList<AlbumFile> data2) {
        try {
            mVideoAdapter = new VideoAdapter(R.layout.item_video, data2);
            final GridLayoutManager manager = new GridLayoutManager(MengWaZhuYeActivity.this, 3);
            mRvVideo.setLayoutManager(manager);
            mRvVideo.setNestedScrollingEnabled(false);
            mVideoAdapter.setHasStableIds(true);
            ((DefaultItemAnimator) mRvVideo.getItemAnimator()).setSupportsChangeAnimations(false); // 取消动画效
            if (isFirstVid) {
                mRvVideo.addItemDecoration(new MarginDecoration4(MengWaZhuYeActivity.this));
                isFirstVid = false;
            }
            mRvVideo.setAdapter(mVideoAdapter);
            mVideoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    Log.d("sizeyd", mAlbumFiles2.size() + "====" + data2.size() + "=====" + position);
                    mAlbumFiles2.remove(position);
                    mVideoAdapter.notifyItemRemoved(position);
                    mVideoAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }

    private void getBitmap(ArrayList<AlbumFile> albumFiles) {
        try {
            NewmapList.clear();
            mapFile.clear();
            for (int i = 0; i < albumFiles.size(); i++) {
                mBitmap = getSmallBitmap(albumFiles.get(i).getPath());
                int bitmapDegree = getBitmapDegree(albumFiles.get(i).getPath());
                newBitmap = rotateBitmapByDegree(mBitmap, bitmapDegree);
                NewmapList.add(newBitmap);
            }

            shouData(NewmapList);

        } catch (Exception e) {
            ToastUtils.showLong("" + e);
        }
    }


    private void shouData(final List<Bitmap> data) {
        try {
            mPicAdapter = new PicAdapter(R.layout.item_pic2, data);
            final GridLayoutManager manager = new GridLayoutManager(MengWaZhuYeActivity.this, 5);
            mRvPic.setLayoutManager(manager);
            mRvPic.setNestedScrollingEnabled(false);
            mPicAdapter.setHasStableIds(true);
            ((DefaultItemAnimator) mRvPic.getItemAnimator()).setSupportsChangeAnimations(false); // 取消动画效
            if (isFirstPic) {
                mRvPic.addItemDecoration(new MarginDecoration3(MengWaZhuYeActivity.this));
                isFirstPic = false;
            }
            mRvPic.setAdapter(mPicAdapter);
            mPicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(MengWaZhuYeActivity.this, PhotoViewActivity.class);
                    intent.putExtra("pos", position + "");
                    intent.putParcelableArrayListExtra("showphoto", mAlbumFiles);
                    startActivity(intent);
                }
            });
            mPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    mAlbumFiles.remove(position);
                    data.remove(position);
                    mPicAdapter.notifyItemRemoved(position);
                    mPicAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        if (bitmap != null) {
            // 根据旋转角度，生成旋转矩阵
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        if (bitmap != null & !bitmap.isRecycled()) {
//            bitmap.recycle();
//        }
            return newBitmap;
        } else {
            return null;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
