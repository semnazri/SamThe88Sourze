/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ScrollView;

import com.konekthing.intravest.R;
import com.konekthing.intravest.ui.widget.NotifyingScrollView;

/**
 * Created by renjaya on 4/7/15.
 */
public class BaseFragment extends Fragment {

    private static final long ANIMATION_DURATION = 300;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getTag() == null) {
            throw new IllegalStateException("This fragment doesn't have tag");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(getTag());
    }

    protected SharedPreferences getSharedPreferences() {
        return getBaseActivity().getSharedPreferences();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void setTitle(CharSequence title) {
        getBaseActivity().setTitle(title);
    }

    protected void changeToolbarAlpha(int alpha) {
        getBaseActivity().changeToolbarAlpha(alpha);
    }

    protected void setDrawerToggleEnabled(boolean enable) {
        getBaseActivity().setDrawerToggleEnabled(enable);
    }

    protected void crossfade(final View contentView, final View loadingView) {
        if (contentView == null || loadingView == null) {
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
        loadingView.setAlpha(1f);
        loadingView.setVisibility(View.VISIBLE);
        contentView.setVisibility(View.GONE);
    }

    protected void toolbarScrollTrick(final View headerView, final NotifyingScrollView scrollView) {
        if (headerView == null || scrollView == null) {
            return;
        }
        changeToolbarAlpha(0);
        final int toolbarHeight = getResources().getDimensionPixelSize(
                R.dimen.abc_action_bar_default_height_material);
        scrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                final int headerHeight = headerView.getHeight() - toolbarHeight;
                final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
                final int newAlpha = (int) (ratio * 255);
                changeToolbarAlpha(newAlpha);
            }
        });
    }

    protected int dipsToPix(float dps) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps,
                getResources().getDisplayMetrics());
    }
}
