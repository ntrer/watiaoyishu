package com.watiao.yishuproject.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.bean.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import cn.refactor.library.SmoothCheckBox;
import de.hdodenhof.circleimageview.CircleImageView;

public class LaoShiSouSuoAdapter extends BaseQuickAdapter<Test.DataListBean,BaseViewHolder> {

    private CircleImageView mCircleImageView;
    private String[] avatar={
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2870892873,1432451025&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1042316691,3941325689&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1409701386,2786606302&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4147676435,3267878866&fm=11&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2054337674,1316511824&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=508188173,3069176955&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3732150855,3004554843&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1443955896,2133586123&fm=26&gp=0.jpg"
    };

    private Random mRandom=new Random();

    private List<Test.DataListBean> datas;
    public static HashMap<Integer, Boolean> isSelected;
    private SmoothCheckBox view;

    public LaoShiSouSuoAdapter(int layoutResId, @Nullable List<Test.DataListBean> data) {
        super(layoutResId, data);
        this.datas=data;
        init();
    }

    // 初始化 设置所有item都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < datas.size(); i++) {
            isSelected.put(i, false);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Test.DataListBean item) {
        try {
            view = helper.getView(R.id.checkbox);
            view.setChecked(isSelected.get(helper.getAdapterPosition()),false);
            mCircleImageView=helper.getView(R.id.avatar);
            Glide.with(mContext).load(avatar[mRandom.nextInt(avatar.length)]).placeholder(R.mipmap.remensaishi_bg).into(mCircleImageView);
            helper.setText(R.id.name,item.getChuangjianrenName());

        }
        catch (Exception e){
            ToastUtils.showLong(e.toString());
        }
    }
}
