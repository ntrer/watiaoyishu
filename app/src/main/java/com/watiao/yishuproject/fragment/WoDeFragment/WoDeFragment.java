package com.watiao.yishuproject.fragment.WoDeFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.activity.GeRenZiLiaoActivity;
import com.watiao.yishuproject.activity.JiFenRenWuActivity;
import com.watiao.yishuproject.activity.MengWaZhuYeActivity;
import com.watiao.yishuproject.activity.MengWaZiLiaoActivity;
import com.watiao.yishuproject.activity.SettingActivity;
import com.watiao.yishuproject.activity.ShangChengActivity;
import com.watiao.yishuproject.activity.ShouHuoDiZhiActivity;
import com.watiao.yishuproject.activity.WoDeDingDanActivity;
import com.watiao.yishuproject.activity.WoDeGuanZhuActivity;
import com.watiao.yishuproject.activity.WoDeShiPinActivity;
import com.watiao.yishuproject.activity.WodeDianZanActivity;
import com.watiao.yishuproject.activity.WodeFenSiActivity;
import com.watiao.yishuproject.activity.WodeSaiShiActivity;
import com.watiao.yishuproject.activity.XIaoXiTiShiActivity;
import com.watiao.yishuproject.adapter.TaDeMengWaAdapter;
import com.watiao.yishuproject.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class WoDeFragment extends BaseFragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
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
    @BindView(R.id.yiqiandao)
    RelativeLayout mYiqiandao;
    @BindView(R.id.rili)
    ImageView mRili;
    @BindView(R.id.qiandao)
    RelativeLayout mQiandao;
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
    @BindView(R.id.line)
    View mLine;
    @BindView(R.id.tianjia)
    ImageView mTianjia;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.rv_mengwa)
    RecyclerView mRvMengwa;
    @BindView(R.id.dianzan)
    LinearLayout mDianzan;
    @BindView(R.id.wodeshipin)
    LinearLayout mWodeshipin;
    @BindView(R.id.wodeshangcheng)
    LinearLayout mWodeshangcheng;
    @BindView(R.id.wodesaishi)
    LinearLayout mWodesaishi;
    @BindView(R.id.wode)
    LinearLayout mWode;
    @BindView(R.id.pic1)
    ImageView mPic1;
    @BindView(R.id.jifenrenwu)
    RelativeLayout mJifenrenwu;
    @BindView(R.id.pic2)
    ImageView mPic2;
    @BindView(R.id.wodedingdan)
    RelativeLayout mWodedingdan;
    @BindView(R.id.pic3)
    ImageView mPic3;
    @BindView(R.id.shouhuodizhi)
    RelativeLayout mShouhuodizhi;
    @BindView(R.id.pic4)
    ImageView mPic4;
    @BindView(R.id.shezhi)
    RelativeLayout mShezhi;
    Unbinder unbinder;
    @BindView(R.id.xiaoxitongzhi)
    ImageView mXiaoxitongzhi;
    private List<String> list = new ArrayList<>();
    private TaDeMengWaAdapter mTaDeMengWaAdapter;
    private AlertDialog dlg;
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        list.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b6c6be44c9fdfc03fa78e5b8e43e87a9/8b82b9014a90f6030db7d4fd3312b31bb051ed01.jpg");
        list.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg");
        list.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4b5cf38a692762d09f3ea2bf90ed0849/5243fbf2b211931352ceb3196f380cd790238d8e.jpg");
        dlg = new AlertDialog.Builder(getContext()).create();
        shouData(list);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_wode, null);
        return view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    private void shouData(final List<String> data) {
        try {
            mTaDeMengWaAdapter = new TaDeMengWaAdapter(R.layout.item_tade_mengwa2, data);
            final LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRvMengwa.setLayoutManager(manager);
            mRvMengwa.setAdapter(mTaDeMengWaAdapter);
            mRvMengwa.scrollToPosition(0);
            mTaDeMengWaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(getContext(), MengWaZhuYeActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.xiaoxitongzhi)
    void xiaoxitongzhi(){
        startActivity(new Intent(getContext(), XIaoXiTiShiActivity.class));
    }

    @OnClick(R.id.avatar)
    void ziliao() {
        startActivity(new Intent(getContext(), GeRenZiLiaoActivity.class));
    }


    @OnClick(R.id.fensi)
    void fensi() {
        startActivity(new Intent(getContext(), WodeFenSiActivity.class));
    }

    @OnClick(R.id.guanzhu)
    void guanzhu() {
        startActivity(new Intent(getContext(), WoDeGuanZhuActivity.class));
    }


    @OnClick(R.id.tianjia)
    void tianjia() {
        startActivity(new Intent(getContext(), MengWaZiLiaoActivity.class));
    }


    @OnClick(R.id.jifenrenwu)
    void jifenrenwu() {
        startActivity(new Intent(getContext(), JiFenRenWuActivity.class));
    }

    @OnClick(R.id.dianzan)
    void dianzan() {
        startActivity(new Intent(getContext(), WodeDianZanActivity.class));
    }


    @OnClick(R.id.huozan)
    void huozan() {
        showSureDlg(getActivity(),"","知道了",false);
    }

    @OnClick(R.id.wodeshipin)
    void wodeshipin() {
        startActivity(new Intent(getContext(), WoDeShiPinActivity.class));
    }


    @OnClick(R.id.shouhuodizhi)
    void shouhuodizhi() {
        startActivity(new Intent(getContext(), ShouHuoDiZhiActivity.class));
    }


    @OnClick(R.id.shezhi)
    void shezhi() {
        startActivity(new Intent(getContext(), SettingActivity.class));
    }


    @OnClick(R.id.wodesaishi)
    void wodesaishi() {
        startActivity(new Intent(getContext(), WodeSaiShiActivity.class));
    }

    @OnClick(R.id.wodedingdan)
    void wodedingdan() {
        startActivity(new Intent(getContext(), WoDeDingDanActivity.class));
    }

    @OnClick(R.id.wodeshangcheng)
    void wodeshangcheng() {
        startActivity(new Intent(getContext(), ShangChengActivity.class));
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
