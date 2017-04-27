/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.konekthing.intravest.R;
import com.konekthing.intravest.network.Connection;
import com.konekthing.intravest.ui.adapters.NavDrawerAdapter;
import com.konekthing.intravest.ui.adapters.item.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends BaseFragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private NavDrawerAdapter mDrawerAdapter;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAdapter();

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = getSharedPreferences();
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);

        View header = inflater.inflate(R.layout.header_drawer, mDrawerListView, false);

        TextView name = (TextView) header.findViewById(R.id.drawer_name);
        TextView position = (TextView) header.findViewById(R.id.drawer_position);
        ImageView photo = (ImageView) header.findViewById(R.id.drawer_image);


        SharedPreferences sp = getSharedPreferences();
        name.setText(sp.getString(ProfileFragment.PREF_SALUTATION, null) + " " + sp.getString(ProfileFragment.PREF_FIRSTNAME, ProfileFragment.PREF_FIRSTNAME) + " " + sp.getString(ProfileFragment.PREF_LASTNAME, ProfileFragment.PREF_LASTNAME) +
                "\n" + sp.getString(ProfileFragment.PREF_COMPANY, ProfileFragment.PREF_COMPANY));
        position.setText(sp.getString(ProfileFragment.PREF_POSITION, null) + "\n" + sp.getString(ProfileFragment.PREF_EMAIL, null) + "\n" + sp.getString(ProfileFragment.PREF_PHONE, null));

        Connection.getInstance(getActivity()).getImageLoader()
                .get(sp.getString(ProfileFragment.PREF_PHOTO, ""), ImageLoader.getImageListener(photo, R.drawable.intravest, R.drawable.intravest));

        mDrawerListView.addHeaderView(header);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position > 0 ? position - 1 : 1);
            }
        });
        mDrawerListView.setAdapter(mDrawerAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition + 1, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setScrimColor(0x40000000);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = getSharedPreferences();
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        getBaseActivity().setDrawerToggle(mDrawerToggle);

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void initAdapter() {
        List<NavDrawerItem> items = new ArrayList<>();
        items.add(new NavDrawerItem("Home", R.drawable.ic_home_tint, new HomeFragment(), true));
        items.add(new NavDrawerItem("Company Profile", R.drawable.ic_company_tint, new CompanyFragment(), true));
        items.add(new NavDrawerItem("Edit Profile", R.drawable.ic_person_tint, new ProfileFragment(), true));
        items.add(new NavDrawerItem("Inbox", R.drawable.ic_inbox_tint, null, true));
        items.add(new NavDrawerItem("Account Settings", R.drawable.ic_settings_tint, new SettingsFragment(), true));
        items.add(new NavDrawerItem("List as Supplier", R.drawable.ic_supplier_tint, new ListAsSupplierFragment(), true));
        items.add(new NavDrawerItem("List an Opportunity", R.drawable.ic_list_opportunity_tint, new OpportunityFragment(), true));
        items.add(new NavDrawerItem("My Favorites", R.drawable.ic_favorite_tint, new MyFavoritesFragment(), false));
        items.add(new NavDrawerItem("Upgrade to Premium", R.drawable.ic_upgrade_tint, null, false));
        items.add(new NavDrawerItem("Help", R.drawable.ic_help_tint, null, false));
        items.add(new NavDrawerItem("Log out", R.drawable.ic_log_out_tint, null, false));
        mDrawerAdapter = new NavDrawerAdapter(getActivity(), items);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;

        if (mDrawerAdapter != null) {
            mDrawerAdapter.setSelected(mCurrentSelectedPosition);
            mDrawerAdapter.notifyDataSetChanged();
        }

        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position + 1, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, mDrawerAdapter.getDrawerItem(position));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    private ActionBar getActionBar() {
        return getBaseActivity().getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position, NavDrawerItem drawerItem);
    }
}
