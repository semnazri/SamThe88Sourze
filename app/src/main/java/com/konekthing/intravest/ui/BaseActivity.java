/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.konekthing.intravest.App;
import com.konekthing.intravest.R;
import com.securepreferences.SecurePreferences;

/**
 * Created by renjaya on 4/7/15.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private static final long ANIMATION_DURATION = 300;
    private Toolbar mActionBarToolbar;
    private TextView mActionBarToolbarTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private int mActionBarToolbarAlpha;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = new SecurePreferences(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApp().setActivityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getApp().setActivityPaused();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mActionBarToolbarTitle != null) {
            mActionBarToolbarTitle.setText(title);
        }
        super.setTitle(null);
    }

    protected SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
                mActionBarToolbarTitle = (TextView) mActionBarToolbar.findViewById(R.id.toolbar_title);
                mActionBarToolbarAlpha = 255;
            }
        }
        return mActionBarToolbar;
    }

    protected void setDrawerToggle(ActionBarDrawerToggle toggle) {
        mDrawerToggle = toggle;
    }

    protected void setDrawerToggleEnabled(boolean enable) {
        if (mDrawerToggle != null) {
            mDrawerToggle.setDrawerIndicatorEnabled(enable);
        }
    }

    protected void changeToolbarAlpha(int alpha) {
        if (mActionBarToolbar != null) {
            mActionBarToolbar.getBackground().setAlpha(alpha);
            mActionBarToolbarAlpha = alpha;
            mActionBarToolbarTitle.setAlpha((float) alpha / 255);
        }
    }

    protected void restoreToolbarAlpha() {
        if (mActionBarToolbar != null && mActionBarToolbarAlpha < 255) {
            mActionBarToolbar.getBackground().setAlpha(255);
            mActionBarToolbarTitle.setAlpha(1);
            mActionBarToolbarAlpha = 255;
        }
    }

    protected void toolbarScrollTrick(final ScrollView scrollView) {
    }

    protected void crossfade(final View contentView, final View loadingView) {
        if (contentView == null || loadingView == null) {
            return;
        }
        if (contentView.getVisibility() != View.GONE) {
            return;
        }
        contentView.setAlpha(0f);
        contentView.setVisibility(View.VISIBLE);
        contentView.animate()
                .alpha(1f)
                .setDuration(ANIMATION_DURATION)
                .setListener(null);

        loadingView.animate()
                .alpha(0f)
                .setDuration(ANIMATION_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingView.setVisibility(View.GONE);
                    }
                });
    }

    protected void crossfadeInverse(final View contentView, final View loadingView) {
        if (contentView == null || loadingView == null) {
            return;
        }
        if (loadingView.getVisibility() != View.GONE) {
            return;
        }
        loadingView.setAlpha(1f);
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    private App getApp() {
        return (App) getApplication();
    }
}
