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
import com.gravityrd.jofogas.model.GravityProduct;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SampleAdapter extends BaseAdapter {
    protected ImageLoader imageLoader;
    Context context;
    LayoutInflater inflater;
    private List<GravityProduct> details = null;
    private ArrayList<GravityProduct> arrayList;

    public SampleAdapter(Context context, List<GravityProduct> detalis) {
        this.context = context;
        this.details = detalis;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<GravityProduct>();
        this.arrayList.addAll(detalis);
        imageLoader = ImageLoader.getInstance();
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
        holder.titleProduct.setText(details.get(position).getProductTitle());
        holder.priceProduct.setText(details.get(position).getProductPrice() + "HUF");
        String tempImg;
        tempImg = details.get(position).getProductImageUrl();
        imageLoader.displayImage(tempImg, holder.imageProduct);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleItemActivity.class);
                intent.putExtra("ItemId", details.get(position).getProductItemId());
                intent.putExtra("Title", details.get(position).getProductTitle());
                intent.putExtra("Body", details.get(position).getProductBody());
                intent.putExtra("Image", details.get(position).getProductImageUrl());
                intent.putExtra("Price", details.get(position).getProductPrice());
                intent.putExtra("Region", details.get(position).getProductRegion());
                intent.putExtra("Time", details.get(position).getProductUpdateTimeStamp());
                context.startActivity(intent);
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