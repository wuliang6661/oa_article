package com.article.oa_article.view.main.mine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author : wuliang
 * e-mail : wuliang6661@163.com
 * date   : 2019/5/1516:04
 * desc   :
 * version: 1.0
 */
public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    String[] tabs;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] tabs) {
        super(fm);
        this.fragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return android.support.v4.view.PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //设置标题
        return tabs[position];
    }
}
