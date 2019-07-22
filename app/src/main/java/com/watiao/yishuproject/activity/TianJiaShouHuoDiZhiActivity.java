package com.watiao.yishuproject.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.JsonBean;
import com.watiao.yishuproject.ui.SLEditTextView;
import com.watiao.yishuproject.utils.Json.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.refactor.library.SmoothCheckBox;

import static android.view.View.NO_ID;

public class TianJiaShouHuoDiZhiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txt_name)
    SLEditTextView mTxtName;
    @BindView(R.id.shoujianren)
    RelativeLayout mShoujianren;
    @BindView(R.id.txt_phone)
    SLEditTextView mTxtPhone;
    @BindView(R.id.shoujihao)
    RelativeLayout mShoujihao;
    @BindView(R.id.dizhi_text)
    TextView mDizhiText;
    @BindView(R.id.dizhi)
    RelativeLayout mDizhi;
    @BindView(R.id.text)
    EditText mText;
    @BindView(R.id.content)
    LinearLayout mContent;
    @BindView(R.id.checkbox)
    SmoothCheckBox mCheckbox;
    @BindView(R.id.moren)
    RelativeLayout mMoren;
    @BindView(R.id.shanchu)
    RelativeLayout mShanchu;
    @BindView(R.id.baocun)
    TextView mBaocun;
    @BindView(R.id.title)
    TextView mTitle;

    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区

    private String bianji, tianjia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bianji = getIntent().getStringExtra("bianji");
        tianjia = getIntent().getStringExtra("tianjia");
        if (bianji != null && !bianji.equals("")) {
            mTitle.setText("编辑收货地址");
            mShanchu.setVisibility(View.VISIBLE);
            mTxtName.setText("王小明");
            mTxtPhone.setText("18637280068");
            mDizhiText.setText("北京市海淀区");
            mText.setText("万达中心广场 写字楼A座 608号");
            mTxtName.clearDeleteIcon();
            mTxtPhone.clearDeleteIcon();
        } else if (tianjia != null && !tianjia.equals("")) {
            mTitle.setText("添加收货地址");
            mShanchu.setVisibility(View.GONE);
        }
        initJsonData();
    }

    @Override
    public int setLayout() {
        return R.layout.activity_tian_jia_shou_huo_di_zhi;
    }

    @Override
    public void init() {

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


    @OnClick(R.id.baocun)
    void baocun() {
        finish();
    }

    @OnClick(R.id.moren)
    void moren() {
        if (mCheckbox.isChecked()) {
            mCheckbox.setChecked(false);
        } else {
            mCheckbox.setChecked(true);
        }
    }

    @OnClick(R.id.dizhi)
    void dizhi() {
        hideSoftKeyboard(this);
        showPickerView();
    }


    private void showPickerView() {// 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                mDizhiText.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));

            }
        })
                .setTitleText("选择区域")
                .setCancelColor(getResources().getColor(R.color.weixin))
                .setSubmitText("保存")
                .setTitleBgColor(getResources().getColor(R.color.color_w))
                .setSubmitColor(getResources().getColor(R.color.weixin))
                .setDividerColor(getResources().getColor(R.color.line_color))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setLineSpacingMultiplier(2.0f)
                .setContentTextSize(18)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        if (isNavigationBarExist(this)) {
            int navigationBarHeight = getNavigationBarHeight(this);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) pvOptions.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin = navigationBarHeight;
            pvOptions.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
        pvOptions.show();
        pvOptions.show();
    }


    private static final String NAVIGATION = "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public static boolean isNavigationBarExist(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
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
