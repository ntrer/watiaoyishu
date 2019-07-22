package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.BiaoQianAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.ui.CommonItemDecoration2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TianJiaBiaoQianActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.rv_recycleview)
    RecyclerView mRvRecycleview;
    private BiaoQianAdapter mBiaoQianAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<String> label = new ArrayList<>();
        label.add("前端");
        label.add("前端");
        label.add("前端前端");
        label.add("前端");
        label.add("后台");
        label.add("前端前端");
        label.add("前端");
        label.add("前端");
        label.add("前端");
        label.add("前端");
        getData(label);
//        mLabels.setLabels(label);
    }

    private void getData(ArrayList<String> label) {
        try {
            mBiaoQianAdapter = new BiaoQianAdapter(R.layout.lable_item, label);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(TianJiaBiaoQianActivity.this,3);
            mRvRecycleview.setLayoutManager(gridLayoutManager);
            mRvRecycleview.addItemDecoration(new CommonItemDecoration2(30,30));
            mRvRecycleview.setAdapter(mBiaoQianAdapter);
            mBiaoQianAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    TextView viewById = view.findViewById(R.id.tab_name);
                    if(!mBiaoQianAdapter.isSelected.get(position)){
                        mBiaoQianAdapter.isSelected.put(position, true);
                        view.setBackground(getResources().getDrawable(R.drawable.layout_border5));
                        viewById.setTextColor(getResources().getColor(R.color.color_w));
                    }
                    else {
                        mBiaoQianAdapter.isSelected.put(position, false);
                        view.setBackground(getResources().getDrawable(R.drawable.layout_border6));
                        viewById.setTextColor(getResources().getColor(R.color.textColor4));
                    }
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @Override
    public int setLayout() {
        return R.layout.activity_tian_jia_biao_qian;
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
