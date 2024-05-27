package com.example.servicenovigrad.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.servicenovigrad.Fragment.HoursFragment;
import com.example.servicenovigrad.Fragment.ProfileFragment;
import com.example.servicenovigrad.Fragment.ServiceFragment;
import com.example.servicenovigrad.R;

import org.jetbrains.annotations.NotNull;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    //followed this tutorial for this code https://www.youtube.com/watch?v=h4HwU_ENXYM&t=4s
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.profile_tab, R.string.service_tab, R.string.hours_tab};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                fragment = new ServiceFragment();
                break;
            case 2:
                fragment = new HoursFragment();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getItemPosition(@NotNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }
}