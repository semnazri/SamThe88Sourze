package com.konekthing.intravest.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.konekthing.intravest.R;
import com.konekthing.intravest.model.NavDrawerItem;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.ui.adapters.NavDrawerAdapter_new;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by semmy on 2/26/2017.
 */

public class FragmentDawer extends BaseFragment {

    public static List<NavDrawerItem> data;
    public static NavDrawerAdapter_new adapter;
    private static TypedArray icon;
    private static String[] ch_titles = null;
    private RecyclerView rvCH;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private TextView name, position;
    private CircleImageView photo;

    public FragmentDawer() {

    }

    public static List<NavDrawerItem> getData1() {
        data = new ArrayList<>();
        NavDrawerItem navItem = null;

        // preparing navigation drawer items
        for (int i = 0; i < ch_titles.length; i++) {
            navItem = new NavDrawerItem(true, ch_titles[i], icon.getResourceId(i, -1));

            data.add(navItem);
        }

        return data;
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels

        ch_titles = getActivity().getResources().getStringArray(R.array.nav_menu);
        icon = getResources().obtainTypedArray(R.array.nav_menu_icon);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_drawer_layout, container, false);
        rvCH = (RecyclerView) layout.findViewById(R.id.drawerRv);

        photo = (CircleImageView) layout.findViewById(R.id.drawer_image);
        name = (TextView) layout.findViewById(R.id.drawer_name);
        position = (TextView) layout.findViewById(R.id.drawer_position);
        
        View header = inflater.inflate(R.layout.header_drawer, rvCH, false);


        SharedPreferences sp = getSharedPreferences();
        name.setText(sp.getString(ProfileFragment.PREF_SALUTATION, null) + " " + sp.getString(ProfileFragment.PREF_FIRSTNAME, ProfileFragment.PREF_FIRSTNAME) + " " + sp.getString(ProfileFragment.PREF_LASTNAME, ProfileFragment.PREF_LASTNAME) +
                "\n" + sp.getString(ProfileFragment.PREF_COMPANY, ProfileFragment.PREF_COMPANY));
        position.setText(sp.getString(ProfileFragment.PREF_POSITION, null) + "\n" + sp.getString(ProfileFragment.PREF_EMAIL, null) + "\n" + sp.getString(ProfileFragment.PREF_PHONE, null));

        Connection.getInstance(getActivity()).getImageLoader()
                .get(sp.getString(ProfileFragment.PREF_PHOTO, ""), ImageLoader.getImageListener(photo, R.drawable.intravest, R.drawable.intravest));


        adapter = new NavDrawerAdapter_new(getActivity(), getData1());


        rvCH.setAdapter(adapter);
        rvCH.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCH.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvCH, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (position != 2) {
                    drawerListener.onDrawerItemSelected(view, position);
                    mDrawerLayout.closeDrawer(containerView);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

}
