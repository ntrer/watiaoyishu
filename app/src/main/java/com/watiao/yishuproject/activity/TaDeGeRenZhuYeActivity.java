package com.watiao.yishuproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.TaDeGeRenZhuYeAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.fragment.TaDeGeRenZhuYeFragment.MengWaFragment;
import com.watiao.yishuproject.fragment.TaDeGeRenZhuYeFragment.ShiPinFragment;
import com.watiao.yishuproject.ui.popmenu.DropPopMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TaDeGeRenZhuYeActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener {


    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.firstline)
    LinearLayout mFirstline;
    @BindView(R.id.id)
    TextView mId;
    @BindView(R.id.secondline)
    LinearLayout mSecondline;
    @BindView(R.id.jiagaunzhu)
    Button mJiagaunzhu;
    @BindView(R.id.yighuanzhu)
    RelativeLayout mYighuanzhu;
    @BindView(R.id.user_xinxi)
    RelativeLayout mUserXinxi;
    @BindView(R.id.huozan_count)
    TextView mHuozanCount;
    @BindView(R.id.huozan)
    LinearLayout mHuozan;
    @BindView(R.id.fensi_count)
    TextView mFensiCount;
    @BindView(R.id.fensi)
    LinearLayout mFensi;
    @BindView(R.id.guanzhu_count)
    TextView mGuanzhuCount;
    @BindView(R.id.guanzhu)
    LinearLayout mGuanzhu;
    @BindView(R.id.count)
    LinearLayout mCount;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_collapsing_toolbar)
    CollapsingToolbarLayout mMovieCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
//    @BindView(R.id.line)
//    View mLine;

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.menu)
    ImageView mMenu;
//    @BindView(R.id.line)
//    View mLine;
    @BindView(R.id.shipin_text)
    TextView mShipinText;
    @BindView(R.id.shipin_line)
    View mShipinLine;
    @BindView(R.id.shipin)
    RelativeLayout mShipin;
    @BindView(R.id.mengwa_text)
    TextView mMengwaText;
    @BindView(R.id.mengwa_line)
    View mMengwaLine;
    @BindView(R.id.mengwa)
    RelativeLayout mMengwa;
    private QMUIPopup mNormalPopup;
    AlertDialog dlg;
    private DropPopMenu mDropPopMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dlg = new AlertDialog.Builder(TaDeGeRenZhuYeActivity.this).create();
        mDropPopMenu = new DropPopMenu(this);
        mDropPopMenu.setOnItemClickListener(new DropPopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, com.watiao.yishuproject.ui.popmenu.MenuItem menuItem) {
                ToastUtils.showLong("asdafasfa");
            }
        });
        SetUpViewPager(mViewpager);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mShipinText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mMengwaText.setTextColor(getResources().getColor(R.color.textColor6));
                    mShipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                    mMengwaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                } else if (position == 1) {
                    mShipinText.setTextColor(getResources().getColor(R.color.textColor6));
                    mMengwaText.setTextColor(getResources().getColor(R.color.app_text_color));
                    mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
                    mMengwaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_ta_de_ge_ren_zhu_ye2;
    }

    @Override
    public void init() {

    }

    private void SetUpViewPager(ViewPager bookViewpager) {
        TaDeGeRenZhuYeAdapter adapter = new TaDeGeRenZhuYeAdapter(getSupportFragmentManager());
        adapter.addFragment(ShiPinFragment.newInstance(), "消费记录");
        adapter.addFragment(MengWaFragment.newInstance(), "充值记录");
        bookViewpager.setAdapter(adapter);
        bookViewpager.setCurrentItem(0, true);
        bookViewpager.setOffscreenPageLimit(2);
    }




    @OnClick(R.id.shipin)
    void shipin(){
        mShipinText.setTextColor(getResources().getColor(R.color.app_text_color));
        mMengwaText.setTextColor(getResources().getColor(R.color.textColor6));
        mShipinLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mMengwaLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mViewpager.setCurrentItem(0);
    }


    @OnClick(R.id.mengwa)
    void mengwa(){
        mShipinText.setTextColor(getResources().getColor(R.color.textColor6));
        mMengwaText.setTextColor(getResources().getColor(R.color.app_text_color));
        mShipinLine.setBackgroundColor(getResources().getColor(R.color.color_w));
        mMengwaLine.setBackgroundColor(getResources().getColor(R.color.weixin));
        mViewpager.setCurrentItem(1);
    }



    @OnClick(R.id.fensi)
    void fensi() {
        startActivity(new Intent(TaDeGeRenZhuYeActivity.this, TaDeFenSiActivity.class));
    }

    @OnClick(R.id.guanzhu)
    void guanzhu() {
        startActivity(new Intent(TaDeGeRenZhuYeActivity.this, TaGuanZhuActivity.class));
    }

    @OnClick(R.id.huozan)
    void huozan() {
        showSureDlg(TaDeGeRenZhuYeActivity.this,"","知道了",false);
    }


    @OnClick(R.id.menu)
    void menu() {
        mDropPopMenu.setTriangleIndicatorViewColor(R.drawable.bg_drop_pop_menu_shap);
        mDropPopMenu.setBackgroundResource(R.drawable.bg_drop_pop_menu_shap);
        mDropPopMenu.setItemTextColor(Color.WHITE);
        mDropPopMenu.setIsShowIcon(true);
        mDropPopMenu.setMenuList(getIconMenuList());
        mDropPopMenu.show(mMenu);
    }

    private List<com.watiao.yishuproject.ui.popmenu.MenuItem> getIconMenuList() {
        List<com.watiao.yishuproject.ui.popmenu.MenuItem> list = new ArrayList<>();
        list.add( new com.watiao.yishuproject.ui.popmenu.MenuItem(R.mipmap.sixin,1,"私信"));
        list.add( new com.watiao.yishuproject.ui.popmenu.MenuItem(R.mipmap.lahei,2,"拉黑"));
        return list;
    }



    public  void showSureDlg(Context context, String msg, String btnTxt, boolean isCancelable) {
        dlg.show();
        dlg.setContentView(R.layout.dialog_layout3);
        dlg.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView contentTxt = (TextView) dlg
                .findViewById(R.id.huozan_count);
        if(msg!=null&&!msg.equals("")){
            contentTxt.setText(msg);
        }
        Button btn = (Button) dlg.findViewById(R.id.sure);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });

        dlg.setCancelable(isCancelable);
        dlg.setCancelable(false);
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



    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return false;
    }
}
