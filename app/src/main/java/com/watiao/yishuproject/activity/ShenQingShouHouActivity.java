package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.PicAdapter2;
import com.watiao.yishuproject.base.BaseActivity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShenQingShouHouActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.dingdanhao)
    TextView mDingdanhao;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.dingdan_pic)
    RoundedImageView mDingdanPic;
    @BindView(R.id.dingdan_name)
    TextView mDingdanName;
    @BindView(R.id.biaozhi)
    TextView mBiaozhi;
    @BindView(R.id.danjia)
    TextView mDanjia;
    @BindView(R.id.jifen)
    TextView mJifen;
    @BindView(R.id.jifen_biaozhi)
    TextView mJifenBiaozhi;
    @BindView(R.id.count)
    TextView mCount;
    @BindView(R.id.dingdan_content)
    RelativeLayout mDingdanContent;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.yunyin)
    RelativeLayout mYunyin;
    @BindView(R.id.pic_title)
    TextView mPicTitle;
    @BindView(R.id.header)
    RelativeLayout mHeader;
    @BindView(R.id.rv_pic)
    RecyclerView mRvPic;
    @BindView(R.id.pic)
    RelativeLayout mPic;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.tianjia)
    ImageView mTianjia;


    private PicAdapter2 mPicAdapter;
    private Image mImage;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private ImageView mImageView, mImageView2;
    private Uri fileUri = null;
    private File imgFile;
    private Bitmap mBitmap;
    private Bitmap newBitmap;
    private List<Bitmap> mapList = new ArrayList<>();
    private List<Bitmap> NewmapList = new ArrayList<>();
    private List<File> mapFile = new ArrayList<>();
    private ArrayList<String> img = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2975436939,2465391407&fm=26&gp=0.jpg").into(mDingdanPic);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_shen_qing_shou_hou;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.submit)
    void submit(){
        Intent intent=new Intent(ShenQingShouHouActivity.this,ShouHouFanKuiActivity.class);
        startActivity(intent);
        finish();
    }



    @OnClick(R.id.tianjia)
    void tianjia() {
//        mAlbumFiles.clear();
        Album.image(ShenQingShouHouActivity.this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(5)
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
            mPicAdapter = new PicAdapter2(R.layout.item_pic, data);
            final LinearLayoutManager manager = new LinearLayoutManager(ShenQingShouHouActivity.this, LinearLayoutManager.HORIZONTAL, false);
            mRvPic.setLayoutManager(manager);
            mRvPic.setAdapter(mPicAdapter);
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


    private File getFile(Bitmap bitmap) {
        String pictureDir = "";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        File file = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();
            String saveDir = Environment.getExternalStorageDirectory()
                    + "/dreamtownImage";
            File dir = new File(saveDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(saveDir, "photo.jpg");
            file.delete();
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            pictureDir = file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return file;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                hideSoftKeyboard(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
