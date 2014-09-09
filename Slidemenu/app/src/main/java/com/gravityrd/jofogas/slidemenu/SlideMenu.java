package com.gravityrd.jofogas.slidemenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.activities.StaggeredListActivity;
import com.gravityrd.jofogas.fragments.HomeFragment;
import com.gravityrd.jofogas.fragments.LastVisitedFragment;
import com.gravityrd.jofogas.model.NavDrawerItem;

import java.util.ArrayList;

public class SlideMenu {
    private final Activity activity;
    private final String[] navMenuTitles;
    private final TypedArray navMenuIcons;
    private final Search search;
    protected DrawerLayout layout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    public SlideMenu(Activity activity) {
        navMenuTitles = activity.getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = activity.getResources().obtainTypedArray(R.array.nav_drawer_icons);
        this.activity = activity;
        search = new Search(activity);
        drawSlideMenu();
    }

    private void drawSlideMenu() {
        final CharSequence title = activity.getTitle();
        layout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) activity.findViewById(R.id.list_slidermenu);
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
        if (navMenuIcons == null) {
            for (String navMenuTitle : navMenuTitles) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitle));
            }
        } else {
            for (int itr = 0; itr < navMenuTitles.length; itr++) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[itr], navMenuIcons.getResourceId(itr, -1)));
            }
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(activity.getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(activity, layout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                activity.getActionBar().setTitle(title);
                activity.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                activity.getActionBar().setTitle(title);
                activity.invalidateOptionsMenu();
            }
        };
        layout.setDrawerListener(mDrawerToggle);
    }

    public void displayView(int position) {
        // update the main content by replacing fragments

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
               StaggeredListActivity.startVisitedAsync(activity,"MOBIL_LAST_VISITED");
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            activity.setTitle(navMenuTitles[position]);
            layout.closeDrawer(mDrawerList);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void setHeaderState(Menu menu) {
        boolean drawerOpen = layout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
    }

    public void createOptions(Menu menu) {
        search.create(menu, activity);
    }

    public void postCreate() {
        mDrawerToggle.syncState();
    }

    public void configChanged(Configuration newConfig) {
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean optionSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.menu_search:
                search.focus();
                return true;
            default:
                return activity.onOptionsItemSelected(item);
        }
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
}
