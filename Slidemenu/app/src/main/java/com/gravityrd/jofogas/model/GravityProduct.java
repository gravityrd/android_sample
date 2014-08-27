package com.gravityrd.jofogas.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GravityProduct implements Parcelable {
    private String productTitle;
    private String productBody;
    private String productImageUrl;
    private String productRegion;
    private String productItemType;
    private String productItemId;
    private long productUpdateTimeStamp;
    private long productPrice;

    public GravityProduct(Parcel in) {
        List<String> strings = new ArrayList<String>();
        in.readStringList(strings);
        productTitle = strings.get(0);
        productBody = strings.get(1);
        productImageUrl = strings.get(2);
        productRegion = strings.get(3);
        productItemType = strings.get(4);
        productItemId = strings.get(5);

        long[] numbers = new long[2];
        in.readLongArray(numbers);
        productUpdateTimeStamp = numbers[0];
        productPrice = numbers[1];

    }

    public GravityProduct() {
    }

    public String getProductRegion() {
        return productRegion;
    }

    public void setProductRegion(String productRegion) {
        this.productRegion = productRegion;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductBody() {
        return productBody;
    }

    public void setProductBody(String productBody) {
        this.productBody = productBody;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public long getProductUpdateTimeStamp() {
        return productUpdateTimeStamp;
    }

    public void setProductUpdateTimeStamp(long productUpdateTimeStamp) {
        this.productUpdateTimeStamp = productUpdateTimeStamp;
    }

    public String getProductItemType() {
        return productItemType;
    }

    public void setProductItemType(String productItemType) {
        this.productItemType = productItemType;
    }

    public String getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(String productItemId) {
        this.productItemId = productItemId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        List<String> strings = new ArrayList<String>();

        strings.add(productTitle);
        strings.add(productBody);
        strings.add(productImageUrl);
        strings.add(productRegion);
        strings.add(productItemType);
        strings.add(productItemId);
        dest.writeStringList(strings);

        long[] numbers = new long[2];
        numbers[0] = productUpdateTimeStamp;
        numbers[1] = productPrice;
        dest.writeLongArray(numbers);
    }

    public static final Parcelable.Creator<GravityProduct> CREATOR
            = new Parcelable.Creator<GravityProduct>() {
        public GravityProduct createFromParcel(Parcel in) {
            return new GravityProduct(in);
        }

        public GravityProduct[] newArray(int size) {
            return new GravityProduct[size];
        }
    };
}
