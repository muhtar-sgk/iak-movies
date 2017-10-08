package com.nabilla.muviin.Utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nabilla.muviin.Views.MovieComingSoon;
import com.nabilla.muviin.Views.MovieNowPlaying;
import com.nabilla.muviin.Views.MoviePopular;


public class SectionPageAdapter extends FragmentPagerAdapter {

    private static final SectionPageAdapter.Section[] SECTIONS = {
            new SectionPageAdapter.Section("Now Playing"),
            new SectionPageAdapter.Section("Popular"),
            new SectionPageAdapter.Section("Coming Soon")
    };

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new MovieNowPlaying();
                break;
            case 1:
                fragment = new MoviePopular();
                break;
            case 2:
                fragment = new MovieComingSoon();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return SECTIONS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getSection(position).getTitle();
    }


    private Section getSection(int position) {
        return SECTIONS[position];
    }

    private static class Section {
        private final String title;

        private Section(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
