package com.gravityrd.jofogas.model;

/**
 * Created by zsolt on 2014.08.14..
 */
public class GravityProducts {
    private String productTitle;
    private String productBody;
    private long productPrice;
    private String productImageUrl;
    private String productRegion;
    private long productUpdateTimeStamp;
    private String productItemType;
    private String productItemId;

    public GravityProducts() {
    }

    public GravityProducts(String productItemId, String productTitle, String productBody, long productPrice, long productUpdateTimeStamp, String productItemType, String productImageUrl, String productRegion) {
        this.productItemId =
                this.productTitle = productTitle;
        this.productBody = productBody;
        this.productPrice = productPrice;
        this.productUpdateTimeStamp = productUpdateTimeStamp;
        this.productItemType = productItemType;
        this.productImageUrl = productImageUrl;
        this.productRegion = productRegion;
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
}