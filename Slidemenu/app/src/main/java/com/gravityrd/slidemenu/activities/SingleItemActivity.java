package com.gravityrd.slidemenu.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gravityrd.slidemenu.slidemenu.SlideMenu;
import com.gravityrd.slidemenu.util.ExpandablePanel;
import com.gravityrd.slidemenu.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SingleItemActivity extends Activity {
    View singleProductView;
    String tempItemId;
    private SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleproductview);
        slideMenu = new SlideMenu(this);


        Intent prodIntent = getIntent();
        Bundle bundle = prodIntent.getExtras();

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
            productPrice.setText(tempPrice.toString());

            String tempRegion = (String) bundle.get("Region");
            productRegion.setText(tempRegion);

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        slideMenu.createOptions(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        return slideMenu.optionSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        slideMenu.setHeaderState(menu);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        slideMenu.postCreate();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        slideMenu.configChanged(newConfig);
    }


}
