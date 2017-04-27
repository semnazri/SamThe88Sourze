/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.konekthing.intravest.ui.BlankFragment;
import com.konekthing.intravest.ui.LoginFragment;
import com.konekthing.intravest.ui.OpportunitiesSearchFragment;
import com.konekthing.intravest.ui.SupplierSearchFragment;

/**
 * Created by renjaya on 4/7/15.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 1;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new LoginFragment();
        }
        return new BlankFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
