package com.gravityrd.jofogas.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.activities.SingleItemActivity;
import com.gravityrd.jofogas.activities.StaggeredListActivity;
import com.gravityrd.jofogas.model.Categories;
import com.gravityrd.jofogas.model.Category;
import com.gravityrd.jofogas.model.GravityProducts;
import com.gravityrd.jofogas.util.Client;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ProgressDialog progressDialog;
    Context cont;
    TextView recCategory1Text;
    TextView recCategory2Text;
    TextView recCategory3Text;

    private List<GravityProducts> gravityProductsList = null;
    private String[] categoryRecomandation = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("HomeFragment", "OK");
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        cont = getActivity();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Adatok letőltése");
                progressDialog.setMessage("Képek betőltése...");
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... param) {
                Log.i("HomeFragment", "ez megvan");
                try {
                    gravityProductsList = Client.getDataFromServer("MOBIL_MAIN_PAGE", 3);
                    categoryRecomandation = Client.getCategoryFromServer();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                try {
                    TextView textTitle1 = (TextView) rootView.findViewById(R.id.prod_title1);
                    textTitle1.setText(gravityProductsList.get(0).getProductTitle());
                    TextView textTitle2 = (TextView) rootView.findViewById(R.id.prod_title2);
                    textTitle2.setText(gravityProductsList.get(1).getProductTitle());
                    TextView textTitle3 = (TextView) rootView.findViewById(R.id.prod_title3);
                    textTitle3.setText(gravityProductsList.get(2).getProductTitle());
                    TextView textPrice1 = (TextView) rootView.findViewById(R.id.price1);
                    textPrice1.setText(gravityProductsList.get(0).getProductPrice() + " HUF");
                    TextView textPrice2 = (TextView) rootView.findViewById(R.id.price2);
                    textPrice2.setText(gravityProductsList.get(1).getProductPrice() + " HUF");
                    TextView textPrice3 = (TextView) rootView.findViewById(R.id.price3);
                    textPrice3.setText(gravityProductsList.get(2).getProductPrice() + " HUF");
                    ImageView imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(0).getProductImageUrl(), imageView1);
                    ImageView imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(1).getProductImageUrl(), imageView2);
                    ImageView imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(2).getProductImageUrl(), imageView3);

                    Category c1 = Categories.INSTANCE.get(Integer.parseInt(categoryRecomandation[0]));
                    recCategory1Text = (TextView) rootView.findViewById(R.id.rec_cat1_text);
                    recCategory1Text.setText(c1.name);
                    ImageView recCategory1Image = (ImageView) rootView.findViewById(R.id.reccategory1);
                    recCategory1Image.setImageResource(c1.imageResourceId);

                    Category c2 = Categories.INSTANCE.get(Integer.parseInt(categoryRecomandation[1]));
                    recCategory2Text = (TextView) rootView.findViewById(R.id.rec_cat2_text);
                    recCategory2Text.setText(c2.name);
                    ImageView recCategory2Image = (ImageView) rootView.findViewById(R.id.reccategory2);
                    recCategory2Image.setImageResource(c2.imageResourceId);

                    Category c3 = Categories.INSTANCE.get(Integer.parseInt(categoryRecomandation[2]));
                    recCategory3Text = (TextView) rootView.findViewById(R.id.rec_cat3_text);
                    recCategory3Text.setText(c3.name);
                    ImageView recCategory3Image = (ImageView) rootView.findViewById(R.id.reccategory3);
                    recCategory3Image.setImageResource(c3.imageResourceId);
                } catch (Exception e) {
                    Log.e("itt vagyok", e.getMessage());
                }
                progressDialog.dismiss();
            }
        }.execute();
        rootView.findViewById(R.id.rec_prod1).setOnClickListener(this);
        rootView.findViewById(R.id.rec_prod2).setOnClickListener(this);
        rootView.findViewById(R.id.rec_prod3).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat1).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat2).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat3).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat4).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat5).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat6).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat7).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat8).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat9).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat10).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat11).setOnClickListener(this);
        rootView.findViewById(R.id.rec_cat12).setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.rec_prod1:
                i = singleItemViewIntent(gravityProductsList.get(0));
                break;
            case R.id.rec_prod2:
                i = singleItemViewIntent(gravityProductsList.get(1));
                break;
            case R.id.rec_prod3:
                i = singleItemViewIntent(gravityProductsList.get(2));
                break;
            case R.id.rec_cat1:
                i = categoryViewIntent(categoryRecomandation[0]);
                break;
            case R.id.rec_cat2:
                i = categoryViewIntent(categoryRecomandation[1]);
                break;
            case R.id.rec_cat3:
                i = categoryViewIntent(categoryRecomandation[2]);
                break;
            case R.id.rec_cat4:
                i = categoryViewIntent("1");
                break;
            case R.id.rec_cat5:
                i = categoryViewIntent("2");
                break;

            case R.id.rec_cat6:
                i = categoryViewIntent("3");
                break;

            case R.id.rec_cat7:
                i = categoryViewIntent("5");
                break;

            case R.id.rec_cat8:
                i = categoryViewIntent("4");
                break;

            case R.id.rec_cat9:
                i = categoryViewIntent("8");
                break;

            case R.id.rec_cat10:
                i = categoryViewIntent("6");
                break;
            case R.id.rec_cat11:
                i = categoryViewIntent("7");
                break;
            case R.id.rec_cat12:
                i = categoryViewIntent("9");
                break;
        }
        if (i != null)
            startActivity(i);

    }

    private Intent singleItemViewIntent(GravityProducts gravityProducts) {
        Log.i("Inent", "itt vagyok");
        Intent intent = new Intent(cont.getApplicationContext(), SingleItemActivity.class);
        intent.putExtra("ItemId", gravityProducts.getProductItemId());
        intent.putExtra("Title", gravityProducts.getProductTitle());
        intent.putExtra("Body", gravityProducts.getProductBody());
        intent.putExtra("Image", gravityProducts.getProductImageUrl());
        intent.putExtra("Price", gravityProducts.getProductPrice());
        intent.putExtra("Region", gravityProducts.getProductRegion());
        intent.putExtra("Time", gravityProducts.getProductUpdateTimeStamp());
        return intent;
    }

    private Intent categoryViewIntent(String category) {
        Intent i = new Intent(getActivity(), StaggeredListActivity.class);
        i.putExtra("category", category);
        return i;
    }


}

