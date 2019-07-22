package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.CitySelectAdapter.CityListAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.bean.CitySelect.AreasBean;
import com.watiao.yishuproject.bean.CitySelect.City;
import com.watiao.yishuproject.bean.CitySelect.CityPickerBean;
import com.watiao.yishuproject.bean.CitySelect.LocateState;
import com.watiao.yishuproject.ui.CitySelect.SideLetterBar;
import com.watiao.yishuproject.utils.GsonUtil;
import com.watiao.yishuproject.utils.PinyinUtils;
import com.watiao.yishuproject.utils.ReadAssetsFileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityPickerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listview_all_city)
    ListView mListviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar mSideLetterBar;
    private CityListAdapter mCityAdapter;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
        getLocation();
    }



    protected void initView() {
        mSideLetterBar.setOverlay(mTvLetterOverlay);
        mSideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListviewAllCity.setSelection(position);
            }
        });
        mCityAdapter = new CityListAdapter(this);
        mListviewAllCity.setAdapter(mCityAdapter);
    }

    public void getCityData() {
        String json = ReadAssetsFileUtil.getJson(this, "city.json");
        CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
        HashSet<City> citys = new HashSet<>();
        for (AreasBean areasBean : bean.data.areas) {
            String name = areasBean.name.replace("　", "");
            citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name), areasBean.is_hot == 1));
            for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
                citys.add(new City(childrenBeanX.id, childrenBeanX.name, PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
            }
        }
        //set转换list
        ArrayList<City> cities = new ArrayList<>(citys);
        //按照字母排序
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City city, City t1) {
                return city.getPinyin().compareTo(t1.getPinyin());
            }
        });
        mCityAdapter.setData(cities);
    }

    protected void initData() {
        getCityData();
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {//选择城市
                Intent intent=new Intent();
                intent.putExtra("city",name);
                setResult(RESULT_OK,intent);
                finish();
            }

            @Override
            public void onLocateClick() {//点击定位按钮
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                getLocation();//重新定位
                ToastUtils.showLong("重新定位");
            }
        });
    }

    /**
     * 得到位置信息(高德地图)
     */
    private void getLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    if (amapLocation.getCity() != null && !amapLocation.getCity().equals("")) {
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, amapLocation.getCity().replace("市", ""));
                    } else {
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("高德地图", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }


    @Override
    public int setLayout() {
        return R.layout.activity_city_picker;
    }

    @Override
    public void init() {

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
