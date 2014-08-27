package com.gravityrd.jofogas.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gravityrd.jofogas.R;
import com.gravityrd.jofogas.activities.SingleItemActivity;
import com.gravityrd.jofogas.activities.StaggeredListActivity;
import com.gravityrd.jofogas.model.GravityProduct;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends BaseAdapter {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    Context context;
    LayoutInflater inflater;
    private ArrayList<GravityProduct> details = null;
    public StaggeredListActivity activtiy;

    public SampleAdapter(Context context, ArrayList<GravityProduct> detalis) {
        this.context = context;
        this.details = detalis;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return details.size();
    }

    @Override
    public Object getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_grid_item, null);
            holder.titleProduct = (TextView) convertView.findViewById(R.id.title);
            holder.priceProduct = (TextView) convertView.findViewById(R.id.description);
            holder.imageProduct = (ImageView) convertView.findViewById(R.id.imgView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GravityProduct item = details.get(position);

        holder.titleProduct.setText(item.getProductTitle());
        holder.priceProduct.setText(item.getProductPrice() + "HUF");

        String tempImg = item.getProductImageUrl();
        imageLoader.displayImage(tempImg, holder.imageProduct);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleItemActivity.startSingleView(activtiy, item);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView titleProduct;
        TextView priceProduct;
        ImageView imageProduct;
    }
}