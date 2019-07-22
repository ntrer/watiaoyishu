package com.aliyun.apsara.alivclittlevideo.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.apsara.alivclittlevideo.AppContext;
import com.aliyun.apsara.alivclittlevideo.R;
import com.aliyun.apsara.alivclittlevideo.view.ExtAlertDialog;
import com.aliyun.common.global.AliyunTag;
import com.aliyun.querrorcode.AliyunErrorCode;
import com.aliyun.qupai.editor.AliyunICompose;
import com.aliyun.qupai.editor.AliyunIComposeCallBack;
import com.aliyun.svideo.editor.publish.ComposeFactory;
import com.aliyun.svideo.editor.publish.CoverEditActivity;
import com.aliyun.svideo.sdk.external.struct.common.AliyunVideoParam;
import com.aliyun.svideo.sdk.external.struct.common.VideoDisplayMode;
import com.aliyun.svideo.sdk.external.thumbnail.AliyunIThumbnailFetcher;
import com.aliyun.svideo.sdk.external.thumbnail.AliyunThumbnailFetcherFactory;
import com.aliyun.video.common.utils.DateTimeUtils;
import com.aliyun.video.common.utils.DensityUtils;
import com.aliyun.video.common.utils.ToastUtils;
import com.aliyun.video.common.utils.image.ImageLoaderImpl;
import com.aliyun.video.common.utils.image.ImageLoaderOptions;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * @author zhushiyuan 编辑完成之后的发布界面，用于编辑视频描述
 */
public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_PARAM_CONFIG = "project_json_path";
    public static final String KEY_PARAM_THUMBNAIL = "svideo_thumbnail";
    public static final String KEY_PARAM_DESCRIBE = "svideo_describe";
    public static final String KEY_PARAM_VIDEO_RATIO = "key_param_video_ratio";
    private static final String KEY_PARAM_VIDEO_PARAM = "videoParam";
    public static final String KEY_PARAM_VIDEO_WIDTH = "key_param_video_width";
    public static final String KEY_PARAM_VIDEO_HEIGHT = "key_param_video_height";

    private ImageView mIvLeft;
    /**
     * 发布按钮
     */
    private TextView mTvPublish;
    /**
     * 显示封面
     */
    private ImageView mIvCover;
    /**
     * 输入视频描述
     */
    private EditText mEtVideoDescribe;
    /**
     * 配置文件地址
     */
    private String mConfigPath;
    /**
     * 封面图片地址
     */
    private String mThumbnailPath;
    private float videoRatio;
    private AliyunVideoParam mVideoPram;
    private View statusBarView;

    /**
     * 合成文件的输出目录
     */
    private final static String OUTPUT_PATH_DIR = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator;
    /**
     * 合成文件后缀
     */
    private final static String OUTPUT_PATH_SUFFIX = "-compose.mp4";
    private String mOutputPath = "";


    private Toolbar mToolbar;
    private Button mPublish;
    private ProgressBar mProgress;
    private ImageView mCoverImage, mCoverBlur;
    private EditText mVideoDesc;
    private View mCoverSelect;
    private View mComposeProgressView;
    private TextView mComposeProgress;
    private View mComposeIndiate;
    private TextView mComposeStatusText, mComposeStatusTip;
    private TextView mHuati,mTextCount;


    private AliyunICompose mCompose;
    private boolean mComposeCompleted;
    private AsyncTask<String, Void, Bitmap> mAsyncTaskOnCreate;
    private AsyncTask<String, Void, Bitmap> mAsyncTaskResult;

    private int videoWidth;
    private int videoHeight;
    private Dialog mRequestDialog;
    /**
     * 视频缩略图截取，不同于MediaMetadataRetriever，可精准获取视频非关键帧图片
     */
    private AliyunIThumbnailFetcher aliyunIThumbnailFetcher;

    /**
     * 视频描述文字最大输入长度
     */
    private static final int MAX_INPUT_DESC_LENGTH = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isStatusBar()) {
            initStatusBar();
            getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    initStatusBar();
                }
            });
        }
        setContentView(R.layout.alivc_little_activity_publish2);
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppContext.getInstance().init(this);
        mRequestDialog = ExtAlertDialog.creatRequestDialog2(PublishActivity.this, "视频合成中...");
        mRequestDialog.setCancelable(false);
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        mConfigPath = intent.getStringExtra(KEY_PARAM_CONFIG);
        mThumbnailPath = intent.getStringExtra(KEY_PARAM_THUMBNAIL);
        videoRatio = intent.getFloatExtra(KEY_PARAM_VIDEO_RATIO, 0f);
        mVideoPram = (AliyunVideoParam)intent.getSerializableExtra(KEY_PARAM_VIDEO_PARAM);
        videoWidth = getIntent().getIntExtra(KEY_PARAM_VIDEO_WIDTH, 0);
        videoHeight = getIntent().getIntExtra(KEY_PARAM_VIDEO_HEIGHT, 0);
        new ImageLoaderImpl().loadImage(this, mThumbnailPath,
            new ImageLoaderOptions.Builder().
                skipMemoryCache().
                skipDiskCacheCache().
                build()).into(mCoverImage);

        aliyunIThumbnailFetcher = AliyunThumbnailFetcherFactory.createThumbnailFetcher();

        mCompose = ComposeFactory.INSTANCE.getInstance();
        mCompose.init(this.getApplicationContext());

        //开始合成
        String time = DateTimeUtils.getDateTimeFromMillisecond(System.currentTimeMillis());
        mOutputPath = OUTPUT_PATH_DIR + time + OUTPUT_PATH_SUFFIX;
        int ret = mCompose.compose(mConfigPath, mOutputPath, mCallback);
        if (ret != AliyunErrorCode.OK) {
            return;
        }
        mAsyncTaskOnCreate = new MyAsyncTask(this).execute(mThumbnailPath);
    }


    static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<PublishActivity> ref;
        private float maxWidth;

        MyAsyncTask(PublishActivity activity) {
            ref = new WeakReference<>(activity);
            maxWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    240, activity.getResources().getDisplayMetrics());
        }

        @Override
        protected Bitmap doInBackground(String... thumbnailPaths) {
            Bitmap bmp = null;
            if (ref != null) {
               PublishActivity publishActivity = ref.get();
                if (publishActivity != null) {
                    String path = thumbnailPaths[0];
                    if (TextUtils.isEmpty(path)) {
                        return null;
                    }
                    File thumbnail = new File(path);
                    if (!thumbnail.exists()) {
                        return null;
                    }
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path, opt);
                    float bw = opt.outWidth;
                    float bh = opt.outHeight;
                    float scale;
                    if (bw > bh) {
                        scale = bw / maxWidth;
                    } else {
                        scale = bh / maxWidth;
                    }
                    boolean needScaleAfterDecode = scale != 1;
                    opt.inJustDecodeBounds = false;
                    bmp = BitmapFactory.decodeFile(path, opt);
                    if (bmp != null && needScaleAfterDecode) {
                        bmp = publishActivity.scaleBitmap(bmp, scale);
                    }
                }
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null && ref != null && ref.get() != null) {
                ref.get().initThumbnail(bitmap);
            }
        }
    }

    private Bitmap scaleBitmap(Bitmap bmp, float scale) {
        Matrix mi = new Matrix();
        mi.setScale(1 / scale, 1 / scale);
        Bitmap temp = bmp;
        bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), mi, false);
        temp.recycle();
        return bmp;
    }


    private final AliyunIComposeCallBack mCallback = new AliyunIComposeCallBack() {
        @Override

        public void onComposeError(int errorCode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

        @Override
        public void onComposeProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRequestDialog.show();
                }
            });
        }

        @Override
        public void onComposeCompleted() {
            MediaScannerConnection.scanFile(getApplicationContext(),
                    new String[] {mOutputPath}, new String[] {"video/mp4"}, null);
            mComposeCompleted = true;
            mRequestDialog.dismiss();
            aliyunIThumbnailFetcher.addVideoSource(mOutputPath, 0, Integer.MAX_VALUE, 0);
            aliyunIThumbnailFetcher.setParameters(videoWidth, videoHeight,
                    AliyunIThumbnailFetcher.CropMode.Mediate, VideoDisplayMode.FILL, 8);
            requestThumbnailImage(0);

        }
    };

    private void requestThumbnailImage(int index) {
        Log.e("frameBitmap", "requestThumbnailImage" + index);
        aliyunIThumbnailFetcher.requestThumbnailImage(new long[] {index}, mThumbnailCallback);
    }


    private void initThumbnail(Bitmap thumbnail) {
        RelativeLayout.LayoutParams para;
        para = (RelativeLayout.LayoutParams) mCoverImage.getLayoutParams();
        para.width = DensityUtils.dip2px(this,106);
        para.height = DensityUtils.dip2px(this,135);

        mCoverImage.setLayoutParams(para);
        mCoverImage.setImageBitmap(thumbnail);

    }


    private final AliyunIThumbnailFetcher.OnThumbnailCompletion mThumbnailCallback = new AliyunIThumbnailFetcher.OnThumbnailCompletion() {
        private int vecIndex = 1;
        private int mInterval = 100;

        @Override
        public void onThumbnailReady(Bitmap frameBitmap, long time) {
            if (frameBitmap != null && !frameBitmap.isRecycled()) {
                initThumbnail(frameBitmap);
                mPublish.setEnabled(mComposeCompleted);

            } else {
                vecIndex = vecIndex + mInterval;
                requestThumbnailImage(vecIndex);
            }

        }

        @Override
        public void onError(int errorCode) {

        }
    };



    private void initView() {
        mToolbar = findViewById(com.aliyun.svideo.editor.R.id.action_bar);
        mPublish = findViewById(com.aliyun.svideo.editor.R.id.submit);
//        mProgress = (ProgressBar) findViewById(com.aliyun.svideo.editor.R.id.publish_progress);
//        mComposeProgressView = findViewById(com.aliyun.svideo.editor.R.id.compose_progress_view);
//        mCoverBlur = (ImageView) findViewById(R.id.publish_cover_blur);
        mCoverImage = (ImageView) findViewById(com.aliyun.svideo.editor.R.id.publish_cover_image);
        mVideoDesc = (EditText) findViewById(com.aliyun.svideo.editor.R.id.publish_desc);
//        mComposeIndiate = findViewById(com.aliyun.svideo.editor.R.id.image_compose_indicator);
        mHuati=findViewById(com.aliyun.svideo.editor.R.id.xuanzehuati);
        mTextCount=findViewById(com.aliyun.svideo.editor.R.id.text_content);
        mPublish.setOnClickListener(this);
//        mCoverSelect = findViewById(com.aliyun.svideo.editor.R.id.publish_cover_select);
        mCoverImage.setOnClickListener(this);
//        mComposeProgress = (TextView) findViewById(com.aliyun.svideo.editor.R.id.compose_progress_text);
//        mComposeStatusText = (TextView) findViewById(com.aliyun.svideo.editor.R.id.compose_status_text);
//        mComposeStatusTip = (TextView) findViewById(com.aliyun.svideo.editor.R.id.compose_status_tip);
        mVideoDesc.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        mHuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    //参数是包名，类全限定名，注意直接用类名不行
                    ComponentName cn=new ComponentName("com.watiao.yishuproject",
                            "com.watiao.yishuproject.activity.HuaTiSouSuoActivity");
                    intent.setComponent(cn);
                    startActivity(intent);
                }catch (Exception e)
                {
                    ToastUtils.show(PublishActivity.this,e.toString());
                }
            }
        });

        mVideoDesc.addTextChangedListener(new TextWatcher() {

            private int start;
            private int end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                start = mVideoDesc.getSelectionStart();
                end = mVideoDesc.getSelectionEnd();
                int count = count(s.toString());
                // 限定EditText只能输入10个数字
                if (count > 50 && start > 0) {
                    Log.d(AliyunTag.TAG, "超过10个以后的数字");

                    s.delete(start - 1, end);
                    mVideoDesc.setText(s);
                    mVideoDesc.setSelection(s.length());
                }
                mTextCount.setText(s.toString().length()+"");
            }
        });


    }


    private int count(String text) {
        int len = text.length();
        int skip;
        int letter = 0;
        int chinese = 0;
        for (int i = 0; i < len; i += skip) {
            int code = text.codePointAt(i);
            skip = Character.charCount(code);
            if (code == 10) {
                continue;
            }
            String s = text.substring(i, i + skip);
            if (isChinese(s)) {
                chinese++;
            } else {
                letter++;
            }

        }
        letter = letter % 2 == 0 ? letter / 2 : (letter / 2 + 1);
        int result = chinese + letter;
        return result;
    }

    // 完整的判断中文汉字和符号
    private boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }


    protected boolean isStatusBar() {
        return true;
    }


    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundColor(getResources().getColor(com.aliyun.svideo.editor.R.color.action_bar_bg_50pct2));
        }
    }






    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_left) {
            onBackPressed();
        } else if (i == R.id.submit) {
            String describe = mVideoDesc.getText().toString();
            if(describe.equals("")){
                ToastUtils.show(this,"请输入视频简介");
                return;
            }

            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            //参数是包名，类全限定名，注意直接用类名不行
            ComponentName cn=new ComponentName("com.watiao.yishuproject",
                    "com.watiao.yishuproject.MainActivity");
            intent.setComponent(cn);

//            Intent intent = new Intent(PublishActivity.this, VideoListActivity.class);
            intent.putExtra(KEY_PARAM_THUMBNAIL, mThumbnailPath);
            intent.putExtra(KEY_PARAM_CONFIG, mConfigPath);

            intent.putExtra(KEY_PARAM_DESCRIBE, describe);
            startActivity(intent);

        } else if (i == R.id.publish_cover_image) {
            Log.d("asdsssssasd",mOutputPath);
            Intent intent = new Intent(this, CoverEditActivity.class);
            intent.putExtra(CoverEditActivity.KEY_PARAM_VIDEO, mOutputPath);
            startActivityForResult(intent, 0);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mThumbnailPath = data.getStringExtra(CoverEditActivity.KEY_PARAM_RESULT);
            mAsyncTaskResult = new MyAsyncTask(this).execute(mThumbnailPath);
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
