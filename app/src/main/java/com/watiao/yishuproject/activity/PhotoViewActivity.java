package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.watiao.yishuproject.R;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;

public class PhotoViewActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.big_img_vp)
    ViewPager mBigImgVp;
    @BindView(R.id.title)
    TextView mTitle;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private String pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(PhotoViewActivity.this, getResources().getColor(R.color.black));
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAlbumFiles = getIntent().getParcelableArrayListExtra("showphoto");
        pos = getIntent().getStringExtra("pos");
        mTitle.setText(Integer.parseInt(pos) +1+ "/" + mAlbumFiles.size());
        initView();
    }


    private void initView() {
        mBigImgVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mAlbumFiles == null ? 0 : mAlbumFiles.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View adView = LayoutInflater.from(PhotoViewActivity.this).inflate(R.layout.item_big_img, null);
                PhotoView icon = (PhotoView) adView.findViewById(R.id.flaw_img);
                icon.setBackgroundColor(getResources().getColor(R.color.black));
                Glide.with(PhotoViewActivity.this)
                        .load(mAlbumFiles.get(position).getPath())
                        .into(icon);
                container.addView(adView);
                return adView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        mBigImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTitle.setText(position + 1 + "/" + mAlbumFiles.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        mBigImgVp.setCurrentItem(Integer.parseInt(pos), true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return true;
    }


}


