package com.nabilla.muviin.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.nabilla.muviin.Views.CastFragment;
import com.nabilla.muviin.Views.InfoFragment;
import com.nabilla.muviin.Views.ReviewFragment;

public class DetailPageAdapter extends FragmentPagerAdapter{

    private static final DetailPageAdapter.Section[] SECTIONS = {
        new DetailPageAdapter.Section("Detail"),
        new DetailPageAdapter.Section("Cast"),
        new DetailPageAdapter.Section("Review")
    };

    public DetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return SECTIONS.length;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InfoFragment();
                break;
            case 1:
                fragment = new CastFragment();
                break;
            case 2:
                fragment = new ReviewFragment();
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getSection(position).getTitle();
    }

    private Section getSection(int position){
        return SECTIONS[position];
    }


    private static class Section{
        private final String title;

        public Section(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
