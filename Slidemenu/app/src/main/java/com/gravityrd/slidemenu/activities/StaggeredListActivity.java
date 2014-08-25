package com.gravityrd.slidemenu.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.gravityrd.slidemenu.R;
import com.gravityrd.slidemenu.util.SampleAdapter;
import com.gravityrd.slidemenu.model.GravityProducts;
import com.gravityrd.slidemenu.slidemenu.SlideMenu;
import com.gravityrd.slidemenu.util.Client;

import java.util.ArrayList;
import java.util.List;

public class StaggeredListActivity extends MainActivity implements AbsListView.OnScrollListener {

    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    public static final int ITEM_ON_PAGE = 100;
    private static final String TAG = "StaggeredGridActivity";
    int current_page = 1;
    boolean error = false;
    ProgressDialog progressDialog;
    private StaggeredGridView mGridView;
    private boolean loadingMore = true;
    private boolean stopLoading = false;
    private SampleAdapter mAdapter;
    private List<GravityProducts> mData;
    private String categoryType;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private String content = null;
    private SlideMenu slideMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        slideMenu = new SlideMenu(this);


        Intent categoryIntent = getIntent();
        Bundle bundle = categoryIntent.getExtras();
        if (bundle != null) {
            if(bundle.keySet().contains("category")){
                categoryType = (String) bundle.get("category");
            }
        }

        new GetCategoryContentTask().execute();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {

        int lastInScreen = firstVisibleItem + visibleItemCount;
        if ((lastInScreen == totalItemCount) && !(loadingMore)) {
            if (stopLoading == false) {
                new loadMoreItems().execute();
            }
        }

    }


    private class GetCategoryContentTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(StaggeredListActivity.this);
            progressDialog.setTitle("Jófogás!");
            progressDialog.setMessage("Tőltés folyamatban...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            loadingMore = true;
            mData = new ArrayList<GravityProducts>();
            try {
                mData = Client.getCategoryDataFromServer("ITEM_PAGE", 100, categoryType);//SEARCH_PAGE_ORDERING
            } catch (Exception e) {
                e.printStackTrace();
                content = e.getMessage();
                error = true;
            }
            return null;
        }

        protected void onCancelled() {
            progressDialog.dismiss();
            Toast toast = Toast.makeText(StaggeredListActivity.this,
                    "Hiba tőrtént az adatok lekérésében, " +
                            "a szerver nem elérhető vagy nincs engedélyezve az Internetes hozáférés", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            if (error) {
                Toast toast = Toast.makeText(StaggeredListActivity.this, content, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else {
                mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
                mAdapter = new SampleAdapter(StaggeredListActivity.this, mData);
                mGridView.setAdapter(mAdapter);
            }
            loadingMore = false;


        }
    }

    private class loadMoreItems extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            loadingMore = true;
            current_page += 1;
            mData = new ArrayList<GravityProducts>();
            try {
                mData = Client.getCategoryDataFromServer("SEARCH_PAGE_ORDERING", 100, categoryType);//SEARCH_PAGE_ORDERING
            } catch (Exception e) {
                e.printStackTrace();
                content = e.getMessage();
                error = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int currentPosition = mGridView.getFirstVisiblePosition();
            mAdapter = new SampleAdapter(StaggeredListActivity.this, mData);
            mGridView.setAdapter(mAdapter);
            mGridView.setSelection(currentPosition + 1);
            loadingMore = false;
        }
    }
}