package com.example.zsolt.slidemenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.zsolt.slidemenu.model.GravityProducts;
import com.gravityrd.receng.web.webshop.jsondto.GravityItem;
import com.gravityrd.receng.web.webshop.jsondto.GravityItemRecommendation;
import com.gravityrd.receng.web.webshop.jsondto.GravityNameValue;
import com.gravityrd.receng.web.webshop.jsondto.GravityRecEngException;
import com.gravityrd.receng.web.webshop.jsondto.GravityRecommendationContext;
import com.gravityrd.recengclient.webshop.GravityClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static final String userID = "testUser1";
    public static final String cookieID = Settings.Secure.ANDROID_ID;
    private static final GravityClient client;

    static {
        client = new GravityClient();
        client.setRemoteUrl("https://saas.gravityrd.com/grrec-jofogas-war/WebshopServlet");
        client.setUserName("jofogas");
        client.setPassword("U272KF29tO");
     }

    public static List<GravityProducts> getDataFromServer(String scenario, int count) {
        List<GravityProducts> gravityProductsList = new ArrayList<GravityProducts>();
        GravityRecommendationContext recomandationContext = new GravityRecommendationContext();
        recomandationContext.scenarioId = scenario;
        recomandationContext.numberLimit = count;
        recomandationContext.recommendationTime = (int) (System.currentTimeMillis() / 1000);
        recomandationContext.resultNameValues = new String[]{"Title", "body","image","ItemType","price","region","updateTimeStamp"};
        GravityItemRecommendation itemRecommendation = null;
        try {
            itemRecommendation = client.getItemRecommendation(userID, cookieID, recomandationContext);
        } catch (GravityRecEngException e) {
            System.err.println("Error happened by getting the item recommendation!");
            System.err.println("Message: " + e.getMessage() + " Fault info: " + e.faultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemRecommendation != null) {

            for(GravityItem item:itemRecommendation.items){

                GravityProducts product = new GravityProducts();
                GravityNameValue[] itemNameValues = item.nameValues;

                for (GravityNameValue itemNameValue: itemNameValues){

                    if(itemNameValue.name.equalsIgnoreCase("Title")){
                        product.setProductTitle(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("body")){
                        product.setProductBody(itemNameValue.name);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("image")){
                        product.setProductImageUrl(itemNameValue.name);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("ItemType")){
                        product.setProductItemType(itemNameValue.name);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("region")){
                        product.setProductRegion(itemNameValue.name);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("price")){
                        product.setProductRegion(itemNameValue.name);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("updateTimeStamp")){
                        product.setProductRegion(itemNameValue.name);
                    }
                }
                gravityProductsList.add(product);
            }

        }
        return gravityProductsList;
    }

    public static List<GravityProducts> getCategoryDataFromServer(String scenario, int count, String categoryType) {
        List<GravityProducts> gravityProductsList = new ArrayList<GravityProducts>();
        GravityRecommendationContext recomandationContext = new GravityRecommendationContext();
        recomandationContext.scenarioId = scenario;
        recomandationContext.numberLimit = count;
        recomandationContext.recommendationTime = (int) (System.currentTimeMillis() / 1000);
        recomandationContext.resultNameValues = new String[]{"Title", "body","image","ItemType","price","region","updateTimeStamp"};
        GravityNameValue filter = new GravityNameValue("filter.categoryId",categoryType);
        recomandationContext.nameValues = new GravityNameValue[]{filter};
        GravityItemRecommendation itemRecommendation = null;
        try {
            itemRecommendation = client.getItemRecommendation(userID, cookieID, recomandationContext);
        } catch (GravityRecEngException e) {
            System.err.println("Error happened by getting the item recommendation!");
            System.err.println("Message: " + e.getMessage() + " Fault info: " + e.faultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemRecommendation != null) {

            for(GravityItem item:itemRecommendation.items){

                GravityProducts product = new GravityProducts();
                GravityNameValue[] itemNameValues = item.nameValues;

                for (GravityNameValue itemNameValue: itemNameValues){
                    if(itemNameValue.name.equalsIgnoreCase("itemId")){
                        product.setProductItemId(itemNameValue.value);
                       Log.i("Homefragment",itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("Title")){
                        product.setProductTitle(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("body")){
                        product.setProductBody(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("image")){
                        product.setProductImageUrl(itemNameValue.value);
                        System.out.println("ItemId: " + item.itemId + " ImageUrl: " + itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("ItemType")){
                        product.setProductItemType(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("region")){
                        product.setProductRegion(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("price")){
                        product.setProductRegion(itemNameValue.value);
                    }
                    if(itemNameValue.name.equalsIgnoreCase("updateTimeStamp")){
                        product.setProductRegion(itemNameValue.value);
                    }
                }
                gravityProductsList.add(product);
            }

        }
        return gravityProductsList;
    }


    public static String[] getCategoryFromServer() {
        String[] categoryRecomandation = new String[3];
        GravityRecommendationContext recomandationContext = new GravityRecommendationContext();
        recomandationContext.scenarioId = "TOP_CATEGORIES";
        recomandationContext.numberLimit = 3;
        recomandationContext.recommendationTime = (int) (System.currentTimeMillis() / 1000);
        recomandationContext.resultNameValues = new String[]{"ItemType"};
        GravityItemRecommendation itemRecommendation = null;
        try {
            itemRecommendation = client.getItemRecommendation(userID, cookieID, recomandationContext);
        } catch (GravityRecEngException e) {
            System.err.println("Error happened by getting the item recommendation!");
            System.err.println("Message: " + e.getMessage() + " Fault info: " + e.faultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (itemRecommendation != null) {
            int count = 0;
            for (GravityItem item : itemRecommendation.items) {
                String temp = " ";
                GravityNameValue[] itemNameValues = item.nameValues;

                for (GravityNameValue itemNameValue : itemNameValues) {
                    if (itemNameValue.name.equalsIgnoreCase("ItemType")) {
                        temp = String.valueOf((itemNameValue.name).charAt(0));
                        Log.i(temp,"nem");
                        System.out.println(temp);
                    }
                    categoryRecomandation[count] = temp;
                    count++;
                }
            }

        }
        return categoryRecomandation;

    }
}