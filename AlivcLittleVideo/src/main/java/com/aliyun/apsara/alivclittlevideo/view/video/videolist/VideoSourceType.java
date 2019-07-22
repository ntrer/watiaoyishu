package com.aliyun.apsara.alivclittlevideo.view.video.videolist;

/**
 * @author zsy_18 data:2018/12/10
 */
public enum VideoSourceType {
    /**
     * 选用url播放
     */
    TYPE_URL,
    /**
     * 选用sts方式播放
     */
    TYPE_STS,
    /**
     * 错误的视频，不在列表中显示
     */
    TYPE_ERROR_NOT_SHOW
}