package com.gravityrd.jofogas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.model.GravityProducts;
import com.gravityrd.jofogas.util.Client;
import com.gravityrd.jofogas.util.ExpandablePanel;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SingleItemActivity extends BaseActivity implements View.OnClickListener {
    View singleProductView;
    String tempItemId;
    private List<GravityProducts> gravityProductsList = null;
    String[] region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent prodIntent = getIntent();
        Bundle bundle = prodIntent.getExtras();

        region = getResources().getStringArray(R.array.location_items);
        TextView productTitle = (TextView) findViewById(R.id.product_title);
        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productBody = (TextView) findViewById(R.id.value);
        TextView productPrice = (TextView) findViewById(R.id.product_price);
        TextView productRegion = (TextView) findViewById(R.id.product_region);
        TextView productTime = (TextView) findViewById(R.id.text);

        if (bundle != null) {
            tempItemId = (String) bundle.get("ItemId");
            String tempTitle = (String) bundle.get("Title");
            productTitle.setText(tempTitle);

            String tempImage = (String) bundle.get("Image");
            ImageLoader.getInstance().displayImage(tempImage, productImage);

            String tempBody = (String) bundle.get("Body");
            productBody.setText(tempBody);

            Long tempPrice = (Long) bundle.get("Price");
            productPrice.setText(tempPrice.toString() + " HUF");

            String tempRegion = (String) bundle.get("Region");
            productRegion.setText(region[Integer.parseInt(tempRegion)]);

            Long tempTime = (Long) bundle.get("Time");
            productTime.setText(tempTime.toString());
        }

        ExpandablePanel panel = (ExpandablePanel) findViewById(R.id.foo);
        singleProductView = findViewById(R.id.singleproductsview);
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            @Override
            public void onExpand(View handle, View content) {
                ImageView imw = (ImageView) handle;
                imw.setImageResource(R.drawable.ic_action_collapse);

            }

            @Override
            public void onCollapse(View handle, View content) {
                ImageView imw = (ImageView) handle;
                imw.setImageResource(R.drawable.ic_action_expand);
            }
        });
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    gravityProductsList = Client.getSimilarItem("MOBIL_ITEM_PAGE", 3,tempItemId);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                try{
                    TextView textView1 = (TextView) findViewById(R.id.itempage_text1);
                    textView1.setText(gravityProductsList.get(0).getProductTitle());
                    TextView textView2 = (TextView) findViewById(R.id.itempage_text2);
                    textView2.setText(gravityProductsList.get(1).getProductTitle());
                    TextView textView3 = (TextView) findViewById(R.id.itempage_text3);
                    textView3.setText(gravityProductsList.get(2).getProductTitle());
                    TextView textPrice1 = (TextView) findViewById(R.id.itempage_price1);
                    textPrice1.setText(gravityProductsList.get(0).getProductPrice() + " HUF");
                    TextView textPrice2 = (TextView) findViewById(R.id.itempage_price2);
                    textPrice2.setText(gravityProductsList.get(1).getProductPrice() + " HUF");
                    TextView textPrice3 = (TextView) findViewById(R.id.itempage_price3);
                    textPrice3.setText(gravityProductsList.get(2).getProductPrice() + " HUF");
                    ImageView imageView1 = (ImageView) findViewById(R.id.itempage_imageView1);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(0).getProductImageUrl(), imageView1);
                    ImageView imageView2 = (ImageView) findViewById(R.id.itempage_imageView2);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(1).getProductImageUrl(), imageView2);
                    ImageView imageView3 = (ImageView) findViewById(R.id.itempage_imageView3);
                    ImageLoader.getInstance().displayImage(gravityProductsList.get(2).getProductImageUrl(), imageView3);


                }catch (Exception e){
                    Log.e("itt vagyok", e.getMessage());
                }
            }
        }.execute();

        findViewById(R.id.productpage_item1).setOnClickListener( this);
        findViewById(R.id.productpage_item2).setOnClickListener( this);
        findViewById(R.id.productpage_item3).setOnClickListener( this);



    }

    public void onClick(View v) {
    Intent i = null;
        switch (v.getId()) {
            case R.id.productpage_item1:
                i = singleItemViewIntent(gravityProductsList.get(0));
                break;
            case R.id.productpage_item2:
                i = singleItemViewIntent(gravityProductsList.get(1));
                break;
            case R.id.productpage_item3:
                i = singleItemViewIntent(gravityProductsList.get(2));
                break;
        }
        if (i != null)
            startActivity(i);

    }

    private Intent singleItemViewIntent(GravityProducts gravityProducts) {
        Log.i("Inent", "itt vagyok");
        Intent intent = new Intent(getApplicationContext(), SingleItemActivity.class);
        intent.putExtra("ItemId", gravityProducts.getProductItemId());
        intent.putExtra("Title", gravityProducts.getProductTitle());
        intent.putExtra("Body", gravityProducts.getProductBody());
        intent.putExtra("Image", gravityProducts.getProductImageUrl());
        intent.putExtra("Price", gravityProducts.getProductPrice());
        intent.putExtra("Region", gravityProducts.getProductRegion());
        intent.putExtra("Time", gravityProducts.getProductUpdateTimeStamp());
        return intent;
    }



    @Override
    protected int getView() {
        return R.layout.singleproductview;
    }

}
