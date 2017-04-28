/*
 * Copyright (c) 2015 Konekthing.
 */

package com.konekthing.intravest.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.konekthing.intravest.R;
import com.konekthing.intravest.network.Connection;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
//    private NavigationDrawerFragment mNavigationDrawerFragment;
    NavigationView navigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    private TextView name, position;
    private CircleImageView photo;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.timeline:
                    Toast.makeText(MainActivity.this, "Timeline", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.inbox:
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.container, new OpportunityFragment(), "cart").addToBackStack("menu");
                    fragmentTransaction.commit();
                    Toast.makeText(MainActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.opportunities:
                    Toast.makeText(MainActivity.this, "Opportunities", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.suppliers:
                    Toast.makeText(MainActivity.this, "Supplier", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_burger2);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        mDrawerLayout.setDrawerListener(toggle);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        photo = (CircleImageView) header.findViewById(R.id.drawer_image);
        name = (TextView) header.findViewById(R.id.drawer_name);
        position = (TextView) header.findViewById(R.id.drawer_position);

        SharedPreferences sp = getSharedPreferences();

        name.setText(sp.getString(ProfileFragment.PREF_SALUTATION, null) + " " + sp.getString(ProfileFragment.PREF_FIRSTNAME, ProfileFragment.PREF_FIRSTNAME) + " " + sp.getString(ProfileFragment.PREF_LASTNAME, ProfileFragment.PREF_LASTNAME) +
                "\n" + sp.getString(ProfileFragment.PREF_COMPANY, ProfileFragment.PREF_COMPANY));
        position.setText(sp.getString(ProfileFragment.PREF_POSITION, null) + "\n" + sp.getString(ProfileFragment.PREF_EMAIL, null) + "\n" + sp.getString(ProfileFragment.PREF_PHONE, null));

        Connection.getInstance(this).getImageLoader()
                .get(sp.getString(ProfileFragment.PREF_PHOTO, ""), ImageLoader.getImageListener(photo, R.drawable.intravest, R.drawable.intravest));
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

//        mNavigationDrawerFragment = (NavigationDrawerFragment)
//                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
//
//        // Set up the drawer.
//        mNavigationDrawerFragment.setUp(
//                R.id.navigation_drawer,
//                (DrawerLayout) findViewById(R.id.drawer_layout));

//        SharedPreferences sp = getSharedPreferences();
//        if (sp.getBoolean(LoginFragment.PREF_IS_LOGIN, false)) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//            return;
//        }
    }

//    @Override
//    public void onNavigationDrawerItemSelected(int position, NavDrawerItem drawerItem) {
//        // update the main content by replacing fragments
//        if (drawerItem.fragment == null) {
//            if (drawerItem.icon == R.drawable.ic_log_out_tint) {
//                SharedPreferences sp = getSharedPreferences();
//                sp.edit().putBoolean(LoginFragment.PREF_IS_LOGIN, false)
//                        .putString(ProfileFragment.PREF_EMAIL, null)
//                        .putString(ProfileFragment.PREF_PASSWORD, null)
//                        .apply();
//                finish();
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            }
//            return;
//        }
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
//        boolean addToBackStack = fragment != null && !(drawerItem.fragment instanceof HomeFragment);
//        if (!addToBackStack) {
//            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.container, drawerItem.fragment, drawerItem.title);
//        if (addToBackStack) {
//            fragmentTransaction.addToBackStack(drawerItem.title);
//        }
//        fragmentTransaction.commit();
//    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }

//        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.home) {
            // Handle the camera action
//            fragment = new FragmentHome();
            fragment = new HomeFragment();
        } else if (id == R.id.cimpany_profile) {
            fragment = new CompanyFragment();
        } else if (id == R.id.edit_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.inbox) {
//            fragment = new CompanyFragment();
        } else if (id == R.id.account_setings) {
            fragment = new SettingsFragment();
        } else if (id == R.id.list_siplier) {
            fragment = new ListAsSupplierFragment();
        } else if (id == R.id.list_opportunity) {
            fragment = new OpportunityFragment();
        } else if (id == R.id.favorite) {
            fragment = new MyFavoritesFragment();
        } else if (id == R.id.logout) {
//            fragment = new MyFavoritesFragment();
            doLogout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, "darimenu").addToBackStack("menu");
            fragmentTransaction.commit();


            // set the toolbar title
            getSupportActionBar().setTitle("");

        }
        return true;
    }

    private void doLogout() {

        SharedPreferences sp = getSharedPreferences();
        sp.edit().putBoolean(LoginFragment.PREF_IS_LOGIN, false)
                .putString(ProfileFragment.PREF_EMAIL, null)
                .putString(ProfileFragment.PREF_PASSWORD, null)
                .apply();
        finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }
}
