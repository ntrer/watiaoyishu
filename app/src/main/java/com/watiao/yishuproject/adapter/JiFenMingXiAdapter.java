package com.watiao.yishuproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.watiao.yishuproject.base.BaseViewPagerAdapter;


public class JiFenMingXiAdapter extends BaseViewPagerAdapter {

    public JiFenMingXiAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }
}
