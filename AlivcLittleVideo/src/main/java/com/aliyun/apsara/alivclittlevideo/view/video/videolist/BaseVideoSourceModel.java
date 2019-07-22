package com.aliyun.apsara.alivclittlevideo.view.video.videolist;

import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidSts;

/**
 * @author zsy_18 data:2018/12/10
 */
public abstract class BaseVideoSourceModel implements IVideoSourceModel{

    @Override
    public UrlSource getUrlSource() {
        return null;
    }

    @Override
    public VidSts getVidStsSource() {
        return null;
    }

}
