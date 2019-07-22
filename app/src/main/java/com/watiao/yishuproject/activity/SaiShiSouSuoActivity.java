package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.adapter.SaiShiSouSuoAdapter;
import com.watiao.yishuproject.base.BaseActivity;
import com.watiao.yishuproject.ui.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaiShiSouSuoActivity extends BaseActivity {

    @BindView(R.id.txt_souuo)
    AppCompatEditText mTxtSouuo;
    @BindView(R.id.search_title)
    RelativeLayout mSearchTitle;
    @BindView(R.id.cancle)
    TextView mCancle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.huati_title)
    TextView mHuatiTitle;
    @BindView(R.id.rv_huati)
    RecyclerView mRvRecycleview;

    private List<String> list = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();
    private SaiShiSouSuoAdapter mSaiShiSouSuoAdapter;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        list.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=92afee66fd36afc3110c39658318eb85/908fa0ec08fa513db777cf78376d55fbb3fbd9b3.jpg");
        list.add("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=b6c6be44c9fdfc03fa78e5b8e43e87a9/8b82b9014a90f6030db7d4fd3312b31bb051ed01.jpg");
        list.add("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=d985fb87d81b0ef473e89e5eedc551a1/b151f8198618367aa7f3cc7424738bd4b31ce525.jpg");
        list.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=4b5cf38a692762d09f3ea2bf90ed0849/5243fbf2b211931352ceb3196f380cd790238d8e.jpg");

        list2.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=8f4dee5d336d55fbdac670265d234f40/96dda144ad3459821b6d671102f431adcaef844a.jpg");
        list2.add("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=f3812a96b2315c605c956defbdb0cbe6/a5c27d1ed21b0ef408aa1eddd3c451da80cb3ef6.jpg");
        list2.add("https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=a1969894da2a28345ca6300b6bb4c92e/e61190ef76c6a7ef0fd420aff3faaf51f2de664d.jpg");
        list2.add("https://ss1.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=907f6e689ddda144c5096ab282b6d009/dc54564e9258d1092f7663c9db58ccbf6c814d30.jpg");

        shouData(list);

        mTxtSouuo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftKeyboard(SaiShiSouSuoActivity.this);
                    mHuatiTitle.setText("搜索结果");
                    shouData(list2);
                    return true;
                }
                return false;
            }
        });

        mTxtSouuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    hideSoftKeyboard(SaiShiSouSuoActivity.this);
                    mHuatiTitle.setText("#火热赛事");
                    shouData(list);
                }
            }
        });

    }


    @Override
    public int setLayout() {
        return R.layout.activity_sai_shi_sou_suo;
    }

    @Override
    public void init() {

    }


    private void shouData(final List<String> data) {
        try {
            mSaiShiSouSuoAdapter = new SaiShiSouSuoAdapter(R.layout.item_saishi_sousuo, data);
            final GridLayoutManager manager = new GridLayoutManager(SaiShiSouSuoActivity.this, 2);
            if (isFirst) {
                mRvRecycleview.addItemDecoration(new MarginDecoration(this));
                isFirst = false;
            }
            mRvRecycleview.setLayoutManager(manager);
            mRvRecycleview.setAdapter(mSaiShiSouSuoAdapter);
            mSaiShiSouSuoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    startActivity(new Intent(SaiShiSouSuoActivity.this, SaiShiXiangQingActivity.class));
                }
            });
        } catch (Exception e) {
            ToastUtils.showLong(e.toString());
        }
    }


    @OnClick(R.id.cancle)
    void cancle() {
        finish();
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
