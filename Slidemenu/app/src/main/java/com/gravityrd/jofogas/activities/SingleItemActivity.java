package com.gravityrd.jofogas.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.jofogas.util.Client;
import com.gravityrd.jofogas.util.DynamicHImageView;
import com.gravityrd.jofogas.util.ExpandablePanel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

public class SingleItemActivity extends BaseActivity {
    View singleProductView;
    private List<GravityProduct> similarItems = null;
    private GravityProduct item = null;
    final static String[] region = App.getContext().getResources().getStringArray(R.array.location_items);

    void setProduct(GravityProduct item) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
        this.item = item;
        TextView productTitle = (TextView) findViewById(R.id.product_title);
        productTitle.setText(item.getProductTitle());

        TextView productBody = (TextView) findViewById(R.id.value);
        productBody.setText(item.getProductBody());

        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        ImageLoader.getInstance().displayImage(item.getProductImageUrl(), productImage,options);

        TextView productPrice = (TextView) findViewById(R.id.product_price);
        productPrice.setText(item.getProductPrice() + " HUF");

        TextView productRegion = (TextView) findViewById(R.id.product_region);
        productRegion.setText(region[Integer.parseInt(item.getProductRegion())]);

        TextView productTime = (TextView) findViewById(R.id.text);
        productTime.setText(item.getProductUpdateTimeStamp() + "");
        Client.addViewItemAsync(item);
        getSimilarRecommendations();
    }

    void loadPassedOptions() {
        Intent prodIntent = getIntent();
        Bundle bundle = prodIntent.getExtras();
        if (bundle != null) {
            GravityProduct item = bundle.getParcelable("item");
            if (item != null)
                setProduct(item);
        }
    }

    void setExpandable() {
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
    }

    void getSimilarRecommendations() {
        final Activity activity = this;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    similarItems = Client.getSimilarItem("MOBIL_ITEM_PAGE", 3, item.getProductItemId());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }

            void handleItem(final GravityProduct item, int viewTitleId, int viewPriceId, int viewImageId, int viewItemId) {
                try {
                    TextView title = (TextView) findViewById(viewTitleId);
                    title.setText(item.getProductTitle());
                    TextView price = (TextView) findViewById(viewPriceId);
                    price.setText(item.getProductPrice() + " HUF");
                    DynamicHImageView image = (DynamicHImageView) findViewById(viewImageId);
                    DisplayImageOptions options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
                    ImageLoader.getInstance().displayImage(item.getProductImageUrl(), image, options );
                    findViewById(viewItemId).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            startSingleView(activity, item);
                        }
                    });

                } catch (Exception e) {
                    Log.e("itt vagyok", e.getMessage());
                }

            }

            @Override
            protected void onPostExecute(Void result) {
                if (similarItems == null) return;
                if (similarItems.size() > 0)
                    handleItem(similarItems.get(0), R.id.itempage_text1, R.id.itempage_price1, R.id.itempage_imageView1, R.id.productpage_item1);
                if (similarItems.size() > 1)
                    handleItem(similarItems.get(1), R.id.itempage_text2, R.id.itempage_price2, R.id.itempage_imageView2, R.id.productpage_item2);
                if (similarItems.size() > 2)
                    handleItem(similarItems.get(2), R.id.itempage_text3, R.id.itempage_price3, R.id.itempage_imageView3, R.id.productpage_item3);
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadPassedOptions();
        setExpandable();
    }

    public static void startSingleView(Activity activity, GravityProduct gravityProduct) {
        Intent intent = new Intent(App.getContext(), SingleItemActivity.class);
        intent.putExtra("item", gravityProduct);
        activity.startActivity(intent);
    }


    @Override
    protected int getView() {
        return R.layout.singleproductview;
    }

}
