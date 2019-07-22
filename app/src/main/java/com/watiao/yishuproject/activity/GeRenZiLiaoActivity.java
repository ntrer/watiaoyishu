package com.watiao.yishuproject.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder2;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView2;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.gson.Gson;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.JsonBean;
import com.watiao.yishuproject.ui.SLEditTextView;
import com.watiao.yishuproject.ui.dialog.ActionSheetDialog;
import com.watiao.yishuproject.utils.Blur.BlurDialog;
import com.watiao.yishuproject.utils.Blur.BlurView;
import com.watiao.yishuproject.utils.Json.GetJsonDataUtil;

import org.json.JSONArray;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.NO_ID;

public class GeRenZiLiaoActivity extends BaseActivity {

    private static final int SCAN_OPEN_PHONE = 1021;
    private static final int PHONE_CAMERA = 1022;
    private static final int PHONE_CROP = 1023;
    private static final int OPEN_ACTIVITY = 1024;
    private Uri mCutUri;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.logo)
    CircleImageView mLogo;
    @BindView(R.id.tips)
    TextView mTips;
    @BindView(R.id.txt_name)
    SLEditTextView mTxtName;
    @BindView(R.id.nicheng)
    RelativeLayout mNicheng;
    @BindView(R.id.txt_phone)
    SLEditTextView mTxtPhone;
    @BindView(R.id.shoujihao)
    RelativeLayout mShoujihao;
    @BindView(R.id.bangding)
    TextView mBangding;
    @BindView(R.id.bangdingweixin)
    RelativeLayout mBangdingweixin;
    @BindView(R.id.qingxuanze)
    TextView mQingxuanze;
    @BindView(R.id.chengshi)
    RelativeLayout mChengshi;
    @BindView(R.id.zhanghao_title)
    TextView mZhanghaoTitle;
    //    @BindView(R.id.zhanghao)
//    TextView mZhanghao;
    @BindView(R.id.guigongzhanghao)
    RelativeLayout mGuigongzhanghao;
    @BindView(R.id.zhanghao_edit)
    SLEditTextView mZhanghaoEdit;
    @BindView(R.id.nichdeng)
    TextView mNichdeng;
    @BindView(R.id.phone_num)
    TextView mPhoneNum;
    private AlertDialog dlg;
    private Dialog mDialog;
    private BlurView mBlurView;
    private BlurDialog mBlurDialog;

    private  ArrayList<String> label = new ArrayList<>();
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dlg = new AlertDialog.Builder(this).create();
        mDialog = new Dialog(this) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                TextView tv = new TextView(GeRenZiLiaoActivity.this);
                tv.setGravity(Gravity.TOP);
                tv.setBackgroundColor(0x7E7D7D);
                setContentView(tv, new ViewGroup.LayoutParams(1, 1));
            }
        };
        BlurDialog(GeRenZiLiaoActivity.this);
        mZhanghaoEdit.setText("gg123456");
        mZhanghaoEdit.clearDeleteIcon();
        mZhanghaoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTxtName.setText("王小明");
        mTxtName.clearDeleteIcon();
        mTxtPhone.setVisibility(View.GONE);
//        mNichdeng.setVisibility(View.VISIBLE);
        mPhoneNum.setVisibility(View.VISIBLE);
//        mNichdeng.setText("王小明");
        mPhoneNum.setText("18637280068");
        initJsonData();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ge_ren_zi_liao;
    }

    @Override
    public void init() {

    }

    @OnClick(R.id.phone_num)
    void xiugaishouji(){
        if (mBlurView != null) {
            mBlurView.blur();
            mBlurView.show();
        }
        mDialog.show();
        ShowDialog();
    }

    @OnClick(R.id.bangdingweixin)
    void bangdingweixin() {
        if (mBlurView != null) {
            mBlurView.blur();
            mBlurView.show();
        }
        mDialog.show();
        ShowDialog2();
    }

    @OnClick(R.id.chengshi)
    void chengshi() {
        showPickerView();
    }


    @OnClick(R.id.logo)
    void touxiang(){
        new ActionSheetDialog(GeRenZiLiaoActivity.this)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue,
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, SCAN_OPEN_PHONE);
                    }
                })
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                openCamera();
                            }
                        })
                .show();
    }

    @OnClick(R.id.baocun)
    void baocun(){
        finish();
    }


    private void openCamera() {
        //创建一个file，用来存储拍照后的照片
        File outputfile = new File(GeRenZiLiaoActivity.this.getExternalCacheDir(), "output.png");
        try {
            if (outputfile.exists()) {
                outputfile.delete();//删除
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageuri;
        if (Build.VERSION.SDK_INT >= 24) {
            imageuri = FileProvider.getUriForFile(GeRenZiLiaoActivity.this,
                    GeRenZiLiaoActivity.this.getPackageName() + ".fileProvider", //可以是任意字符串
                    outputfile);
        } else {
            imageuri = Uri.fromFile(outputfile);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, PHONE_CAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SCAN_OPEN_PHONE: //从相册图片后返回的uri
                    //启动裁剪
                    startActivityForResult(CutForPhoto(data.getData()), PHONE_CROP);
                    break;
                case PHONE_CAMERA: //相机返回的 uri
                    //启动裁剪
                    String path = GeRenZiLiaoActivity.this.getExternalCacheDir().getPath();
                    String name = "output.png";
                    startActivityForResult(CutForCamera(path, name), PHONE_CROP);
                    break;
                case PHONE_CROP:
                    try {
                        //获取裁剪后的图片，并显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                GeRenZiLiaoActivity.this.getContentResolver().openInputStream(mCutUri));
                        File file = getFile(bitmap);
//                        upLoadImg(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else if(requestCode==OPEN_ACTIVITY){
//            getGenRenXinXi();
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


    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", ConvertUtils.dp2px(GeRenZiLiaoActivity.this, 200)); //200dp
            intent.putExtra("outputY", ConvertUtils.dp2px(GeRenZiLiaoActivity.this, 200));
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Intent CutForCamera(String camerapath, String imgname) {
        try {

            //设置裁剪之后的图片路径文件
            File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
                    "cutcamera.png"); //随便命名一个
            if (cutfile.exists()) { //如果已经存在，则先删除,这里应该是上传到服务器，然后再删除本地的，没服务器，只能这样了
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = null; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Intent intent = new Intent("com.android.camera.action.CROP");
            //拍照留下的图片
            File camerafile = new File(camerapath, imgname);
            if (Build.VERSION.SDK_INT >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imageUri = FileProvider.getUriForFile(GeRenZiLiaoActivity.this,
                        GeRenZiLiaoActivity.this.getPackageName() + ".fileProvider",
                        camerafile);
            } else {
                imageUri = Uri.fromFile(camerafile);
            }
            outputUri = Uri.fromFile(cutfile);
            //把这个 uri 提供出去，就可以解析成 bitmap了
            mCutUri = outputUri;
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", ConvertUtils.dp2px(GeRenZiLiaoActivity.this, 200));
            intent.putExtra("outputY", ConvertUtils.dp2px(GeRenZiLiaoActivity.this, 200));
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    private void showPickerView() {// 弹出选择器（省市区三级联动）
        OptionsPickerView2 pvOptions = new OptionsPickerBuilder2(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mQingxuanze.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

            }
        })
                .setTitleText("选择区域")
                .setCancelColor(getResources().getColor(R.color.weixin))
                .setSubmitText("保存")
                .setTitleBgColor(getResources().getColor(R.color.color_w))
                .setSubmitColor(getResources().getColor(R.color.weixin))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        if(isNavigationBarExist(this)){
            int navigationBarHeight = getNavigationBarHeight(this);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pvOptions.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin =navigationBarHeight;
            pvOptions.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
        pvOptions.show();
    }


    private static final String NAVIGATION= "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public static  boolean isNavigationBarExist(@NonNull Activity activity){
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId()!= NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }




    public static int getNavigationBarHeight(Context activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }




    private void ShowDialog() {
        dlg.show();
        dlg.setContentView(R.layout.dialog_layout1);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        TextView titleTxt = (TextView) dlg.findViewById(R.id.ext_dialog_title);
        final EditText phone = dlg.findViewById(R.id.txt_phone);
        final EditText yzm = dlg.findViewById(R.id.txt_yzm);
        final Button btn = (Button) dlg.findViewById(R.id.next);
        View hasTitle = dlg.findViewById(R.id.ext_dialog_hasTitle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (mBlurView != null) {
                    mBlurView.hide();
                }
                mDialog.dismiss();
            }
        });


        yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!phone.getText().toString().equals("")&&!yzm.getText().toString().equals("")&&yzm.getText().toString().length()==6){
                    btn.setBackground(getResources().getDrawable(R.drawable.btn_border2));
                    btn.setClickable(true);
                }
            }
        });


        dlg.setCancelable(false);
    }


    private void ShowDialog2() {
        dlg.show();
        dlg.setContentView(R.layout.dialog_layout2);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        Button btn1 = (Button) dlg.findViewById(R.id.sure);
        Button btn2 = (Button) dlg.findViewById(R.id.cancle);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (mBlurView != null) {
                    mBlurView.hide();
                }
                mDialog.dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
                if (mBlurView != null) {
                    mBlurView.hide();
                }
                mDialog.dismiss();
            }
        });

        dlg.setCancelable(false);
    }


    private void BlurDialog(Activity context) {
        ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
        mBlurView = decorView.findViewById(R.id.blur_dialog_bg);
        if (mBlurView == null) {
            mBlurView = new BlurView(context);
            mBlurView.setId(R.id.blur_dialog_bg);
            mBlurView.setAlpha(0f);
            decorView.addView(mBlurView, new ViewGroup.LayoutParams(-1, -1));
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
