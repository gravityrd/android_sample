package com.example.zsolt.slidemenu; /**
 * Created by zsolt on 2014.08.13..
 */


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.example.zsolt.slidemenu.model.GravityProducts;

public class StaggeredListActivity extends MainActivity implements AbsListView.OnScrollListener{

    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    public static final int ITEM_ON_PAGE = 100;

    private StaggeredGridView mGridView;
    private boolean loadingMore = true;
    private boolean stopLoading = false;
    int current_page = 1;

    private SampleAdapter mAdapter;

    private List<GravityProducts> mData;
    private String categoryType;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    boolean error = false;
    private String content =  null;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        Intent categoryIntent = getIntent();
        Bundle bundle = categoryIntent.getExtras();
        if( bundle != null){
            if(bundle.get("Ingatlan").equals("1000")) {
                categoryType = "1";
                setTitle("Ingatlan");
            }
            if(bundle.get("Jarmu").equals("2000")){
                categoryType = "2";
                setTitle("Jármű");
            }
            if(bundle.get("Othon").equals("3000")){
                categoryType = "3";
                setTitle("Otthon, háztartás");
            }
            if( bundle.get("Sport").equals("4000") ){
                categoryType = "4";
                setTitle("Szabadidő, sport");
            }
            if( bundle.get("Elektronika").equals("5000") ){
                categoryType = "5";
                setTitle("Számítástechnika, elektronika");
            }
            if( bundle.get("Munka").equals("6000") ){
                categoryType = "6";
                setTitle("Állás, munka");
            }
            if( bundle.get("Egyeb").equals("7000") ){
                categoryType = "7";
                setTitle("Egyéb");
            }

            if( bundle.get("Hasznalati").equals("8000") ){
                categoryType = "8";
                setTitle("Használati tárgyak");
            }

            if( bundle.get("Uzlet").equals("9000") ){
                categoryType = "9";
                setTitle("Üzlet");
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
                if(stopLoading == false){
                    new loadMoreItems().execute();
                }
            }

    }


    private class GetCategoryContentTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
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
            try{
                mData = Client.getCategoryDataFromServer("ITEM_PAGE", 100,categoryType);//SEARCH_PAGE_ORDERING
            }catch (Exception e){
                e.printStackTrace();
                content = e.getMessage();
                error = true;
            }
            return null;
        }

        protected void onCancelled(){
            progressDialog.dismiss();
            Toast toast = Toast.makeText(StaggeredListActivity.this,
                    "Hiba tőrtént az adatok lekérésében, " +
                    "a szerver nem elérhető vagy nincs engedélyezve az Internetes hozáférés", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        @Override
        protected void onPostExecute(Void result){
            progressDialog.dismiss();
            if(error){
                Toast toast = Toast.makeText(StaggeredListActivity.this, content, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }else{
                mGridView = (StaggeredGridView) findViewById(R.id.grid_view);
                mAdapter = new SampleAdapter(StaggeredListActivity.this,mData);
                mGridView.setAdapter(mAdapter);
            }
            loadingMore = false;


        }
    }

    private class loadMoreItems extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            loadingMore = true;
            current_page += 1;
            mData = new ArrayList<GravityProducts>();
            try{
                mData = Client.getCategoryDataFromServer("SEARCH_PAGE_ORDERING", 100,categoryType);//SEARCH_PAGE_ORDERING
            }catch (Exception e){
                e.printStackTrace();
                content = e.getMessage();
                error = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            int currentPosition = mGridView.getFirstVisiblePosition();
            mAdapter = new SampleAdapter(StaggeredListActivity.this,mData);
            mGridView.setAdapter(mAdapter);
            mGridView.setSelection(currentPosition + 1);
            loadingMore = false;
        }
    }
}