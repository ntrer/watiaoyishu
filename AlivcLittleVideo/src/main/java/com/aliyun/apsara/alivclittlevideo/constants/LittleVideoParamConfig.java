package com.aliyun.apsara.alivclittlevideo.constants;

import android.os.Environment;

import com.aliyun.svideo.base.AlivcSvideoEditParam;
import com.aliyun.svideo.sdk.external.struct.common.CropKey;
import com.aliyun.svideo.sdk.external.struct.common.VideoDisplayMode;
import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.encoder.VideoCodecs;
import com.aliyun.svideo.sdk.external.struct.recorder.CameraType;
import com.aliyun.svideo.sdk.external.struct.recorder.FlashType;
import com.aliyun.svideo.sdk.external.struct.snap.AliyunSnapVideoParam;

/**
 * 跳转到短视频录制和编辑模块的默认参数
 *
 * @author xlx
 */
public class LittleVideoParamConfig {

    /**
     * ========================录制参数==============================
     */
    public static class Recorder {

        /**
         * resolutionMode: 画质, 720p
         */
        public static final int RESOLUTION_MODE = AliyunSnapVideoParam.RESOLUTION_720P;

        /**
         * ratioMode: 纵横比, 9 : 16
         */
        public static final int RATIO_MODE = AliyunSnapVideoParam.RATIO_MODE_9_16;

        /**
         * recordMode: RECORD_MODE_AUTO
         */
        public static final int RECORD_MODE = AliyunSnapVideoParam.RECORD_MODE_AUTO;

        /**
         * beautyLevel: 美颜级别, 80
         */
        public static final int BEAUTY_LEVEL = 80;

        /**
         * beautyStatus: 美颜开关, false 视频录制会默认开启faceUnity的高级美颜, 所以aliyunSDK自带的美颜默认关闭
         */
        public static final boolean BEAUTY_STATUS = false;

        /**
         * cameraType: 摄像头模式, front
         */
        public static final CameraType CAMERA_TYPE = CameraType.FRONT;

        /**
         * flashType: 闪光灯模式, on
         */
        public static final FlashType FLASH_TYPE = FlashType.ON;

        /**
         * needClip: 是否裁剪, true
         */
        public static final boolean NEED_CLIP = true;

        /**
         * maxDuration: 最大时长: 15000ms
         */
        public static final int MAX_DURATION = 15000;
        /**
         * minDuration: 最小时长: 2000ms
         */
        public static final int MIN_DURATION = 2000;

        /**
         * 视频质量: sd
         */
        public static final VideoQuality VIDEO_QUALITY = VideoQuality.HD;

        /**
         * gop: 5
         */
        public static final int GOP = 5;

        /**
         * videoBitrte: 2000
         */
        public static final int VIDEO_BITRATE = 2000;

        /**
         * videoCodec:编码方式, VideoCodecs.H264_HARDWARE
         */
        public static final VideoCodecs VIDEO_CODEC = VideoCodecs.H264_HARDWARE;

    }

    /**
     * ========================裁剪参数==============================
     */
    public static class Crop {

        /**
         * minVideoDuration: 最短视频时间, 4000ms
         */
        public static final int MIN_VIDEO_DURATION = 4000;

        /**
         * maxVideoDuration: 最长视频时间, 29*1000 ms
         */
        public static final int MAX_VIDEO_DURATION = 29 * 1000;

        /**
         * minCropDuration: 最短裁剪时间, 3000ms
         */
        public static final int MIN_CROP_DURATION = 29 * 1000;

        /**
         * frameRate: 25
         */
        public static final int FRAME_RATE = 25;

        /**
         * cropMode: 裁剪模式, VideoDisplayMode.SCALE
         */
        public static final VideoDisplayMode CROP_MODE = VideoDisplayMode.SCALE;
    }

    /**
     * ========================编辑参数==============================
     */
    public static class Editor {

        /**
         * VIDEO_RATIO: 纵横比, CropKey.RATIO_MODE_9_16
         */
        public static final int VIDEO_RATIO = AlivcSvideoEditParam.RATIO_MODE_ORIGINAL;

        /**
         * VIDEO_SCALE: 裁剪模式, CropKey.SCALE_FILL
         */
        public static final VideoDisplayMode VIDEO_SCALE  = CropKey.SCALE_FILL;

        /**
         * VIDEO_QUALITY: 清晰度, VideoQuality.HD
         */
        public static final VideoQuality VIDEO_QUALITY  = VideoQuality.HD;

        /**
         * VIDEO_FRAMERATE:帧率,  25
         */
        public static final int VIDEO_FRAMERATE  = 25;

        /**
         * VIDEO_GOP: 125
         */
        public static final int VIDEO_GOP  = 125;

        /**
         * VIDEO_BITRATE: 0
         */
        public static final int VIDEO_BITRATE  = 0;


    }
    public static class Player{
        /**
         * 视频播放的region
         */
        public static String REGION = "cn-shanghai";
        /**
         * 是否打开播放器日志开关
         */
        public static boolean LOG_ENABLE = true;
    }

    /**
     * 视频列表缓存目录
     */
    public static String DIR_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/cache";
    /**
     * 视频下载目录
     */
    public static String DIR_DOWNLOAD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/download";
    /**
     * 视频合成目录
     */
    public static String DIR_COMPOSE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/compose";




}
