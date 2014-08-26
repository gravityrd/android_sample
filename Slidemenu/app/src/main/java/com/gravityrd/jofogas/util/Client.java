package com.gravityrd.jofogas.util;

import android.provider.Settings;

import com.gravityrd.jofogas.model.GravityProducts;
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

    private static List<GravityProducts> getItemRecommendation(GravityRecommendationContext recomandationContext) {
        List<GravityProducts> gravityProductsList = new ArrayList<GravityProducts>();
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

            for (GravityItem item : itemRecommendation.items) {

                GravityProducts product = new GravityProducts();
                product.setProductItemId(item.itemId);

                for (GravityNameValue itemNameValue : item.nameValues) {
                    if (itemNameValue.name.equalsIgnoreCase("Title")) {
                        product.setProductTitle(itemNameValue.value);
                    }
                    if (itemNameValue.name.equalsIgnoreCase("body")) {
                        product.setProductBody(itemNameValue.value);
                    }
                    if (itemNameValue.name.equalsIgnoreCase("image")) {
                        product.setProductImageUrl(itemNameValue.value.replace("thumbs", "images"));
                    }
                    if (itemNameValue.name.equalsIgnoreCase("ItemType")) {
                        product.setProductItemType(itemNameValue.value);
                    }
                    if (itemNameValue.name.equalsIgnoreCase("region")) {
                        product.setProductRegion(itemNameValue.value);
                    }
                    if (itemNameValue.name.equalsIgnoreCase("price")) {
                        product.setProductPrice(Long.parseLong(itemNameValue.value));
                    }
                    if (itemNameValue.name.equalsIgnoreCase("updateTimeStamp")) {
                        product.setProductUpdateTimeStamp(Long.parseLong(itemNameValue.value));
                    }
                }
                gravityProductsList.add(product);
            }

        }
        return gravityProductsList;
    }

    private static List<GravityProducts> getItemRecommendationFromServerWithKeyVale(String scenario, int count, GravityNameValue[] nameValues) {
        GravityRecommendationContext recommendationContext = new GravityRecommendationContext();
        recommendationContext.scenarioId = scenario;
        recommendationContext.numberLimit = count;
        recommendationContext.recommendationTime = (int) (System.currentTimeMillis() / 1000);
        recommendationContext.resultNameValues = new String[]{"Title", "body", "image", "ItemType", "price", "region", "updateTimeStamp", "itemId"};
        recommendationContext.nameValues = nameValues;
        return getItemRecommendation(recommendationContext);

    }


    public static List<GravityProducts> getDataFromServer(String scenario, int count) {
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[0]);

    }

    public static List<GravityProducts> getCategoryDataFromServer(String scenario, int count, String categoryType) {
        GravityNameValue filter = new GravityNameValue("filter.categoryId", categoryType);
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[]{filter});
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
                        temp = String.valueOf((itemNameValue.value).charAt(0));
                    }
                    categoryRecomandation[count] = temp;
                    count++;
                }
            }

        }
        return categoryRecomandation;

    }
}