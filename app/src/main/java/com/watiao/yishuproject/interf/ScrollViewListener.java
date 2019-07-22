package com.watiao.yishuproject.interf;

import com.watiao.yishuproject.ui.ObservableScrollView;

public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
