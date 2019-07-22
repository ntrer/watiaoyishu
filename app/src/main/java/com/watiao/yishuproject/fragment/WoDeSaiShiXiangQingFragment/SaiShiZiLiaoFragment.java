package com.watiao.yishuproject.fragment.WoDeSaiShiXiangQingFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.MengWaZhuYeActivity;
import com.watiao.yishuproject.activity.ShiPinXiangQingActivity;
import com.watiao.yishuproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SaiShiZiLiaoFragment extends BaseFragment {


    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.zuobiao_img)
    ImageView mZuobiaoImg;
    @BindView(R.id.zuibiao_dizhi)
    TextView mZuibiaoDizhi;
    @BindView(R.id.year)
    TextView mYear;
    @BindView(R.id.shengao)
    TextView mShengao;
    @BindView(R.id.tizhong)
    TextView mTizhong;
    @BindView(R.id.user_headr)
    RelativeLayout mUserHeadr;
    @BindView(R.id.piaoshu)
    TextView mPiaoshu;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tianjia)
    ImageView mTianjia;
    @BindView(R.id.edit)
    ImageView mEdit;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    Unbinder unbinder;
    @BindView(R.id.depiaoshu)
    RelativeLayout mDepiaoshu;
    @BindView(R.id.fenshu)
    TextView mFenshu;
    @BindView(R.id.defenshu)
    RelativeLayout mDefenshu;
    @BindView(R.id.paihang)
    TextView mPaihang;
    @BindView(R.id.paiming)
    RelativeLayout mPaiming;
    @BindView(R.id.defen)
    RelativeLayout mDefen;
    @BindView(R.id.xinxi)
    LinearLayout mXinxi;
    @BindView(R.id.shipin_pic)
    RoundedImageView mShipinPic;
    @BindView(R.id.jianjie)
    TextView mJianjie;
    @BindView(R.id.shipin)
    RelativeLayout mShipin;
    Unbinder unbinder1;


    private boolean isEnd = false;
    private String end;

    public SaiShiZiLiaoFragment() {
        // Required empty public constructor
    }

    public static SaiShiZiLiaoFragment newInstance(String end) {
        SaiShiZiLiaoFragment fragment = new SaiShiZiLiaoFragment();
        Bundle args = new Bundle();
        args.putString("end", end);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        end = getArguments().getString("end");
        if (end != null && end.equals("true")) {
            mDepiaoshu.setVisibility(View.GONE);
            mDefenshu.setVisibility(View.VISIBLE);
            mPaiming.setVisibility(View.VISIBLE);
        } else if (end != null && end.equals("false")) {
            mDepiaoshu.setVisibility(View.VISIBLE);
            mDefenshu.setVisibility(View.GONE);
            mPaiming.setVisibility(View.GONE);
        }
        Glide.with(this).load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2163100570,3803850188&fm=26&gp=0.jpg").placeholder(R.mipmap.remensaishi_bg).into(mShipinPic);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.ffragment_wodesaishidetail, null);
        return view;
    }

    @OnClick(R.id.shipin_pic)
    void shipin(){
        Intent intent=new Intent(getContext(),ShiPinXiangQingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.user_headr)
    void user(){
        Intent intent=new Intent(getContext(),MengWaZhuYeActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
