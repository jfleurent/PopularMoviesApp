package com.example.jeffr.popularmovieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jeffr.popularmovieapp.MainActivity;
import com.example.jeffr.popularmovieapp.PlaceholderFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PlaceholderFragment.fragments.add(PlaceholderFragment.newInstance(position));
        return PlaceholderFragment.fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}