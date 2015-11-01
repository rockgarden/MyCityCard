package com.eastcom.mycitycard.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by rockgarden on 15/10/31.
 */
public class cardFragmentAdapter extends FragmentStatePagerAdapter {
    private List<String> mTitles;
    private List<Fragment> mFragments;
    public cardFragmentAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments){
        super(fm);
        mTitles=titles;
        mFragments=fragments;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mTitles.get(position);
    }
}
