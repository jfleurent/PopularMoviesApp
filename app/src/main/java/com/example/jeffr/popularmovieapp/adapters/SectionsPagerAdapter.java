package com.example.jeffr.popularmovieapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jeffr.popularmovieapp.MainActivity;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        MainActivity.PlaceholderFragment.fragments.add(MainActivity.PlaceholderFragment.newInstance(position));
        return MainActivity.PlaceholderFragment.fragments.get(position);
    }



    @Override
    public int getCount() {
        return 2;
    }
}