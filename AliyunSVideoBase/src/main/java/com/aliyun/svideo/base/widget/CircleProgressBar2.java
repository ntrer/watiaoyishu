package com.aliyun.svideo.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.aliyun.svideo.base.R;
import com.aliyun.video.common.utils.DensityUtils;

public class CircleProgressBar2 extends View {


    // 录制时的环形进度条
    private Paint mRecordPaint;
    // 录制时点击的圆形按钮
    private Paint mBgPaint;
    // 画笔宽度
    private int mStrokeWidth;
    // 圆形按钮半径
    private int mRadius;
    //控件宽度
    private int mWidth;
    //控件高度
    private int mHeight;
    // 圆的外接圆
    private RectF mRectF;
    //progress max value
    private int mMaxValue=100;
    //per progress value
    private int mProgressValue;
    //是否开始record
    private boolean mIsStartRecord=false;
    //Arc left、top value
    private int mArcValue;
    //录制 time
    private long mRecordTime;


    public CircleProgressBar2(Context context) {
        this(context, null);
    }

    public CircleProgressBar2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
    }
    //初始化画笔操作
    private void initParams(Context context){
        mArcValue=mStrokeWidth = DensityUtils.dip2px(context, 3);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(context.getResources().getColor(R.color.alivc_base_white));
        mBgPaint.setStrokeWidth(mStrokeWidth);
        mBgPaint.setStyle(Paint.Style.FILL);

        mRecordPaint = new Paint();
        mRecordPaint.setAntiAlias(true);
        mRecordPaint.setColor(context.getResources().getColor(R.color.qupai_text_dialog_green_default));
        mRecordPaint.setStrokeWidth(mStrokeWidth);
        mRecordPaint.setStyle(Paint.Style.STROKE);

        mRadius = DensityUtils.dip2px(context, 30);
        mRectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth=getWidth();
        mHeight=getHeight();
        if (mWidth != mHeight) {
            int min = Math.min(mWidth, mHeight);
            mWidth=min;
            mHeight=min;
        }

        if (mIsStartRecord) {

            canvas.drawCircle(mWidth / 2, mHeight / 2, (float) (mRadius*1.2), mBgPaint);

            if(mProgressValue<=mMaxValue) {
                //left--->距Y轴的距离
                //top--->距X轴的距离
                //right--->距Y轴的距离
                //bottom--->距X轴的距离
                mRectF.left = mArcValue;
                mRectF.top = mArcValue;
                mRectF.right = mWidth - mArcValue;
                mRectF.bottom = mHeight - mArcValue;
                canvas.drawArc(mRectF, -90, ((float)mProgressValue / mMaxValue) * 360, false, mRecordPaint);

                if (mProgressValue == mMaxValue) {
                    mProgressValue = 0;
                    mIsStartRecord = false;
                    //这里可以回调出去表示已到录制时间最大值
                    //code.....
                }
            }
        }else{
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBgPaint);
        }
    }

}
