/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.konekthing.intravest.R;
import com.konekthing.intravest.ui.adapters.HomePagerAdapter;
import com.konekthing.intravest.ui.widget.ParallaxViewPager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by renjaya on 4/7/15.
 */
public class HomeActivity extends BaseActivity implements PageNavigator {

    private ParallaxViewPager mViewPager;
    private CirclePageIndicator mIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(getString(R.string.app_name));
        SharedPreferences sp = getSharedPreferences();
        if (sp.getBoolean(LoginFragment.PREF_IS_LOGIN, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        mViewPager = (ParallaxViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
//        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        mIndicator.setOnPageChangeListener(mViewPager.getOnPageChangeListener());
//        mIndicator.setViewPager(mViewPager);
    }

    @Override
    public void goToPage(int position) {
        mViewPager.setCurrentItem(position, true);
    }
}
