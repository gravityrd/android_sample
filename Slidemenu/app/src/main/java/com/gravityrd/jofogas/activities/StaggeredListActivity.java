package com.gravityrd.jofogas.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.AbsListView;

import com.etsy.android.grid.StaggeredGridView;
import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.jofogas.util.Client;
import com.gravityrd.jofogas.util.SampleAdapter;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StaggeredListActivity extends BaseActivity implements AbsListView.OnScrollListener {

    private static final String categoryMeta = "category";
    private static final String searchMeta = "searchString";
    private static final String initialItemsMeta = "initialItemsMeta";
    private ArrayList<GravityProduct> items = new ArrayList<GravityProduct>();
    private String searchString;

    public static void startCategoryAsync(final Activity a, final String categoryType) {
        final Intent i = new Intent();
        i.putExtra(categoryMeta, categoryType);

        final ProgressDialog progressDialog = new ProgressDialog(a);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setTitle("Jófogás!");
                progressDialog.setMessage("Tőltés folyamatban...");
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                ArrayList<GravityProduct> result = new ArrayList<GravityProduct>();
                try {
                    result = Client.getCategoryDataFromServer("MOBIL_LISTING", 50, categoryType, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i.putParcelableArrayListExtra(initialItemsMeta, result);
                a.startActivity(i);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                progressDialog.dismiss();
            }
        }.execute();
    }

    public static void startSearchAsync(final Activity a, final String searchText, ArrayList<GravityProduct> items) {
        final Intent i = new Intent();
        i.putExtra(searchMeta, searchText);
        i.putParcelableArrayListExtra(initialItemsMeta, items);
        a.startActivity(i);
    }

    private StaggeredGridView mGridView;

    private enum Type {
        CATEGORY, SEARCH
    }

    private Type type;
    private String categoryType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

        Intent categoryIntent = getIntent();
        Bundle bundle = categoryIntent.getExtras();
        if (bundle != null) {
            if (bundle.keySet().contains(categoryMeta)) {
                categoryType = (String) bundle.get(categoryMeta);
                type = Type.CATEGORY;
            }
            if (bundle.keySet().contains(searchMeta)) {
                searchString = (String) bundle.get(searchMeta);
                type = Type.SEARCH;
            }
            items = bundle.getParcelableArrayList(initialItemsMeta);
        }
        update();
    }

    void update() {
        int currentPosition = mGridView.getFirstVisiblePosition();
        mGridView.setAdapter(new SampleAdapter(StaggeredListActivity.this, items));
        mGridView.setSelection(currentPosition + 1);
    }


    @Override
    protected int getView() {
        return R.layout.activity_my;
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
    }

    private final Semaphore available = new Semaphore(1, true);

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        int lastInScreen = firstVisibleItem + visibleItemCount;
        if (lastInScreen >= totalItemCount) {
            boolean acquired = available.tryAcquire();
            if (acquired) {
                loadMoreAsync();
            }
        }
    }

    public void loadMoreAsync() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                ArrayList<GravityProduct> result = new ArrayList<GravityProduct>();
                switch (type) {
                    case CATEGORY:
                        result = Client.getCategoryDataFromServer("MOBILE_LISTING", 50, categoryType, items.size());
                        break;
                    case SEARCH:
                        result = Client.getTextSuggestion(searchString, null, null, items.size());
                }
                items.addAll(result);
                available.release();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                update();
            }
        }.execute();
    }
}