package com.watiao.yishuproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.beiing.flikerprogressbar.FlikerProgressBar;
import com.watiao.yishuproject.R;
import com.watiao.yishuproject.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressBarActivity extends BaseActivity implements Runnable{


    @BindView(R.id.download)
    Button mDownload;
    @BindView(R.id.round_flikerbar)
    FlikerProgressBar mRoundFlikerbar;

    Thread downLoadThread;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mRoundFlikerbar.setProgress(msg.arg1);
            if(msg.arg1 == 100){
                mRoundFlikerbar.finishLoad();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_progress_bar;
    }

    @Override
    public void init() {

    }


    @OnClick(R.id.download)
    void  download(){
        mDownload.setVisibility(View.GONE);
        mRoundFlikerbar.setVisibility(View.VISIBLE);
        downLoad();
    }

    @OnClick(R.id.round_flikerbar)
    void toggle(){
        if(!mRoundFlikerbar.isFinish()){
            mRoundFlikerbar.toggle();
            if(mRoundFlikerbar.isStop()){
                downLoadThread.interrupt();
            } else {
                downLoad();
            }

        }
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    @Override
    public void run() {
        try {
            while( ! downLoadThread.isInterrupted()){
                float progress = mRoundFlikerbar.getProgress();
                progress  += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if(progress == 100){
                    break;
                }
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
