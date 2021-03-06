package com.gravityrd.jofogas.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.jofogas.util.Client;
import com.gravityrd.jofogas.util.SampleAdapter;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class StaggeredListActivity extends BaseActivity {

    private static final String categoryMeta = "category";
    private static final String searchMeta = "searchString";
    private static final String initialItemsMeta = "initialItemsMeta";
    private ArrayList<GravityProduct> items = new ArrayList<GravityProduct>();
    private String searchString;
    private boolean emptyMore = false;
    Spinner categorySpinner, regionSpinner;
    private static int categoryTypeFromSpinner = 0;
    private static int  regionTypeFromSpinner = 0;


    public static void startCategoryAsync(final Activity a, final String categoryType) {
        final Intent i = new Intent(App.getContext(), StaggeredListActivity.class);
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
        final Intent i = new Intent(App.getContext(), StaggeredListActivity.class);
        i.putExtra(searchMeta, searchText);
        i.putParcelableArrayListExtra(initialItemsMeta, items);
        a.startActivity(i);
    }

    public static void startVisitedAsync(final Activity a, final String scenarioName){
        final Intent i = new Intent(App.getContext(), StaggeredListActivity.class);
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
                    result = Client.getVisitedDataFromServer(scenarioName, 50);
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
    private SampleAdapter listAdapter;

    private enum Type {
        CATEGORY, SEARCH
    }

    private Type type = Type.CATEGORY;
    private String categoryType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StaggeredGridView mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(emptyMore) return;
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (lastInScreen >= totalItemCount) {
                    boolean acquired = available.tryAcquire();
                    if (acquired) {
                        loadMoreAsync();
                    }
                }
            }
        });

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

        categorySpinner = (Spinner) findViewById(R.id.category_spinerId);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String holder;
                //categoryTypeFromSpinner = categorySpinner.getSelectedItemPosition();
                Toast.makeText(getBaseContext(), categorySpinner.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show();
                holder = Integer.toString(position);

                if(count >= 1) {
                    listAdapter.refreshView(holder);

                }
                count++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categorySpinner.setSelection(0);
            }
        });

        regionSpinner = (Spinner) findViewById(R.id.location_spinerId);
        listAdapter = new SampleAdapter(StaggeredListActivity.this, items);
        listAdapter.activtiy = this;
        mGridView.setAdapter(listAdapter);

    }

    private void categorySelector(String categoryId){
        startCategoryAsync(this, categoryId);
    }

    @Override
    protected int getView() {
        return R.layout.activity_my;
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private final Semaphore available = new Semaphore(1, true);

    public void loadMoreAsync() {
        new AsyncTask<Void, Void, ArrayList<GravityProduct>>() {
            @Override
            protected void onPreExecute() {
                Toast.makeText(getApplicationContext(), "loading more items ", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected ArrayList<GravityProduct> doInBackground(Void... params) {

                ArrayList<GravityProduct> result = new ArrayList<GravityProduct>();
                switch (type) {
                    case CATEGORY:
                        result = Client.getCategoryDataFromServer("MOBIL_LISTING", 50, categoryType, items.size());
                        break;
                    case SEARCH:
                        result = Client.getTextSuggestion(searchString, null, null, items.size());
                }
                if(result.size() == 0)
                    emptyMore = true;
                available.release();
                return result;
            }

            @Override
            protected void onPostExecute(ArrayList<GravityProduct> result) {
                if (result.size() > 0) {
                    items.addAll(result);
                    listAdapter.notifyDataSetChanged();
                }
                Toast.makeText(getApplicationContext(), "loaded " + result.size() + " items, overall " + items.size(), Toast.LENGTH_SHORT).show();
            }
    }.execute();
    }
}