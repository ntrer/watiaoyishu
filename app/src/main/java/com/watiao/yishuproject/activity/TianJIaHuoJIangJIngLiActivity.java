package com.watiao.yishuproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TianJIaHuoJIangJIngLiActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text)
    EditText mText;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.quit)
    RelativeLayout mQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        //设置支持toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String hintStr = "请输入文字描述\n如：2010年5月，饰演小猫。";
        SpannableString ss = new SpannableString(hintStr);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);
        //editText.setHintTextColor(R.color.colorPrimary);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setHint(new SpannedString(ss));
    }

    @Override
    public int setLayout() {
        return R.layout.activity_tian_jia_huo_jiang_jing_li;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.login)
    void submit(){
        if(!mText.getText().toString().equals("")){
            Intent intent=new Intent();
            intent.putExtra("jingli",mText.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        }
        else {
            ToastUtils.showLong("请输入内容后再提交");
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideSoftKeyboard(TianJIaHuoJIangJIngLiActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
