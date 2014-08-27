package com.gravityrd.jofogas.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.jofogas.util.Client;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class HomeFragment extends Fragment {
    ProgressDialog progressDialog;

    private List<GravityProduct> gravityProductList = null;
    private String[] categoryRecomandation = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final Fragment fragment = this;
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
                    gravityProductList = Client.getDataFromServer("MOBIL_MAIN_PAGE", 3);
                    categoryRecomandation = Client.getCategoryFromServer();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            void setItem(final GravityProduct item, int viewTitleId, int viewPriceId, int viewImageId, int viewItemId) {
                try {
                    TextView textTitle1 = (TextView) rootView.findViewById(viewTitleId);
                    textTitle1.setText(item.getProductTitle());

                    TextView textPrice1 = (TextView) rootView.findViewById(viewPriceId);
                    textPrice1.setText(item.getProductPrice() + " HUF");

                    ImageView imageView1 = (ImageView) rootView.findViewById(viewImageId);
                    ImageLoader.getInstance().displayImage(item.getProductImageUrl(), imageView1);

                    rootView.findViewById(viewItemId).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            SingleItemActivity.startSingleView(fragment, item);
                        }
                    });
                } catch (Exception e) {
                    Log.e("failed to show item" + item, e.getMessage());
                }
            }

            void setCategory(final String category, int viewTitleId, int viewImageId, int viewItemId) {
                try {
                    Category c1 = Categories.INSTANCE.get(Integer.parseInt(category));
                    TextView recCategory1Text = (TextView) rootView.findViewById(viewTitleId);
                    recCategory1Text.setText(c1.name);
                    ImageView recCategory1Image = (ImageView) rootView.findViewById(viewImageId);
                    recCategory1Image.setImageResource(c1.imageResourceId);
                    setClickForCategory(rootView, viewItemId, category);
                } catch (Exception e) {
                    Log.e("failed to show category " + category, e.getMessage());
                }

            }


            @Override
            protected void onPostExecute(Void result) {
                if (gravityProductList.size() > 0)
                    setItem(gravityProductList.get(0), R.id.prod_title1, R.id.price1, R.id.imageView1, R.id.rec_prod1);
                if (gravityProductList.size() > 1)
                    setItem(gravityProductList.get(1), R.id.prod_title2, R.id.price2, R.id.imageView2, R.id.rec_prod2);
                if (gravityProductList.size() > 2)
                    setItem(gravityProductList.get(2), R.id.prod_title3, R.id.price3, R.id.imageView3, R.id.rec_prod3);
                if (categoryRecomandation.length > 0)
                    setCategory(categoryRecomandation[0], R.id.rec_cat1_text, R.id.reccategory1, R.id.rec_cat1);
                if (categoryRecomandation.length > 1)
                    setCategory(categoryRecomandation[1], R.id.rec_cat2_text, R.id.reccategory2, R.id.rec_cat2);
                if (categoryRecomandation.length > 2)
                    setCategory(categoryRecomandation[2], R.id.rec_cat3_text, R.id.reccategory3, R.id.rec_cat3);
                progressDialog.dismiss();
            }
        }.execute();
        setClickForCategory(rootView, R.id.rec_cat4, "1");
        setClickForCategory(rootView, R.id.rec_cat5, "2");
        setClickForCategory(rootView, R.id.rec_cat6, "3");
        setClickForCategory(rootView, R.id.rec_cat7, "5");
        setClickForCategory(rootView, R.id.rec_cat8, "4");
        setClickForCategory(rootView, R.id.rec_cat9, "8");
        setClickForCategory(rootView, R.id.rec_cat10, "6");
        setClickForCategory(rootView, R.id.rec_cat11, "9");
        setClickForCategory(rootView, R.id.rec_cat12, "7");

        return rootView;
    }

    void setClickForCategory(View view, int viewItemId, final String category) {
        view.findViewById(viewItemId).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = categoryViewIntent(category);
                startActivity(i);
            }
        });
    }

    private Intent categoryViewIntent(String category) {
        Intent i = new Intent(getActivity(), StaggeredListActivity.class);
        i.putExtra("category", category);
        return i;
    }


}

