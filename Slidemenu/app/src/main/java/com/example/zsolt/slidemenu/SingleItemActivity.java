package com.example.zsolt.slidemenu;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.Date;

/**
 * Created by zsolt on 2014.08.06..
 */
public class SingleItemActivity extends MainActivity {
    View singleProductView;
    String tempItemId;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleproductview);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        set(navMenuTitles,navMenuIcons);

        Intent prodIntent = getIntent();
        Bundle bundle = prodIntent.getExtras();

        TextView productTitle = (TextView) findViewById(R.id.product_title);
        ImageView productImage = (ImageView) findViewById(R.id.product_image);
        TextView productBody = (TextView) findViewById(R.id.value);
        TextView productPrice = (TextView) findViewById(R.id.product_price);
        TextView productRegion = (TextView) findViewById(R.id.product_region);
        TextView productTime = (TextView) findViewById(R.id.text);

        if(bundle != null ){
            tempItemId = (String) bundle.get("ItemId");
            String tempTitle = (String) bundle.get("Title");
            productTitle.setText(tempTitle);

            String tempImage = (String) bundle.get("Image");
            ImageLoader.getInstance().displayImage(tempImage,productImage);

            String tempBody = (String) bundle.get("Body");
            productBody.setText(tempBody);

            String tempPrice = (String) bundle.get("Price");
            productPrice.setText(tempPrice);

            String tempRegion = (String) bundle.get("Region");
            productRegion.setText(tempRegion);

            String tempTime = (String) bundle.get("Time");
            productTime.setText(tempTime);
        }

        ExpandablePanel panel = (ExpandablePanel) findViewById(R.id.foo);
        singleProductView = (View) findViewById(R.id.singleproductsview);
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener(){
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

    private String calcTime(String timeStamp) {
        String result = "";
        int timeStampNow = Integer.parseInt(timeStamp);
        Date curDate = new Date();

        return result;
    }
}
