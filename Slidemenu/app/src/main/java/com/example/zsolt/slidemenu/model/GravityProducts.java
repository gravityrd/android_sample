package com.example.zsolt.slidemenu.model;

/**
 * Created by zsolt on 2014.08.14..
 */
public class GravityProducts {
    private String productTitle;
    private String productBody;
    private float productPrice;
    private String productImageUrl;
    private String productRegion;
    private long productUpdateTimeStamp;
    private String productItemType;
    private String productItemId;

    public GravityProducts() {
    }

    public GravityProducts(String productItemId, String productTitle, String productBody, float productPrice, long productUpdateTimeStamp, String productItemType, String productImageUrl, String productRegion) {
        this.productItemId =
        this.productTitle = productTitle;
        this.productBody = productBody;
        this.productPrice = productPrice;
        this.productUpdateTimeStamp = productUpdateTimeStamp;
        this.productItemType = productItemType;
        this.productImageUrl = productImageUrl;
        this.productRegion = productRegion;
    }

    public void setProductItemId(String productItemId) {this.productItemId = productItemId;}

    public void setProductRegion(String productRegion){
        this.productRegion = productRegion;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductBody(String productBody) {
        this.productBody = productBody;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public void setProductUpdateTimeStamp(long productUpdateTimeStamp) {
        this.productUpdateTimeStamp = productUpdateTimeStamp;
    }

    public void setProductItemType(String productItemType) {
        this.productItemType = productItemType;
    }

    public String getProductRegion(){
        return productRegion;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public String getProductBody() {
        return productBody;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public long getProductUpdateTimeStamp() {
        return productUpdateTimeStamp;
    }

    public String getProductItemType() {
        return productItemType;
    }

    public String getProductItemId(){ return productItemId;}
}
