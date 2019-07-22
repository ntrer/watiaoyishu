package com.aliyun.svideo.editor.util;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.video.common.utils.DensityUtils;

import java.lang.reflect.Field;

public class TabLayoutUtil {
    public static void setTabLayoutIndicator(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = tabLayout.getClass().getDeclaredField("mTabStrip");
                    field.setAccessible(true);
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout tabStrip = (LinearLayout) field.get(tabLayout);
                    for (int i = 0, count = tabStrip.getChildCount(); i < count; i++) {
                        View tabView = tabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView textView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int textWidth = 0;
                        textWidth = textView.getWidth();
                        if (textWidth == 0) {
                            textView.measure(0, 0);
                            textWidth = textView.getMeasuredWidth();
                        }
                        int tabWidth = 0;
                        tabWidth = tabView.getWidth();
                        if (tabWidth == 0) {
                            tabView.measure(0, 0);
                            tabWidth = tabView.getMeasuredWidth();
                        }
                        LinearLayout.LayoutParams tabViewParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        int margin = (tabWidth - textWidth) / 2;
                        //LogUtils.d("textWidth=" + textWidth + ", tabWidth=" + tabWidth + ", margin=" + margin);
                        tabViewParams.leftMargin = margin;
                        tabViewParams.rightMargin = margin;
                        tabView.setLayoutParams(tabViewParams);
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置{@link TabLayout}的每项之间的分隔线
     */
    public static void setTabLayoutDivider(TabLayout tabLayout) {
        setTabLayoutDivider(tabLayout, 12);
    }

    /**
     * 设置{@link TabLayout}的每项之间的分隔线
     */
    public static void setTabLayoutDivider(TabLayout tabLayout, int paddingDip) {
        LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
        mTabStrip.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mTabStrip.setDividerPadding(DensityUtils.dip2px(tabLayout.getContext(),paddingDip));
    }
}
