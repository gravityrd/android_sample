package com.gravityrd.jofogas.util;

import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.gravityrd.jofogas.model.GravityProduct;
import com.gravityrd.receng.web.webshop.jsondto.GravityEvent;
import com.gravityrd.receng.web.webshop.jsondto.GravityItem;
import com.gravityrd.receng.web.webshop.jsondto.GravityItemRecommendation;
import com.gravityrd.receng.web.webshop.jsondto.GravityNameValue;
import com.gravityrd.receng.web.webshop.jsondto.GravityRecEngException;
import com.gravityrd.receng.web.webshop.jsondto.GravityRecommendationContext;
import com.gravityrd.recengclient.webshop.GravityClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

    private static ArrayList<GravityProduct> getItemRecommendation(GravityRecommendationContext recomandationContext) {
        ArrayList<GravityProduct> gravityProductList = new ArrayList<GravityProduct>();
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

                GravityProduct product = new GravityProduct();
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
                gravityProductList.add(product);
            }

        }
        return gravityProductList;
    }

    private static ArrayList<GravityProduct> getItemRecommendationFromServerWithKeyVale(String scenario, int count, GravityNameValue[] nameValues) {
        GravityRecommendationContext recommendationContext = new GravityRecommendationContext();
        recommendationContext.scenarioId = scenario;
        recommendationContext.numberLimit = count;
        recommendationContext.recommendationTime = (int) (System.currentTimeMillis() / 1000);
        recommendationContext.resultNameValues = new String[]{"Title", "body", "image", "ItemType", "price", "region", "updateTimeStamp", "itemId"};
        recommendationContext.nameValues = nameValues;
        return getItemRecommendation(recommendationContext);

    }


    public static List<GravityProduct> getDataFromServer(String scenario, int count) {
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[0]);

    }
    public static ArrayList<GravityProduct> getVisitedDataFromServer(String scenario, int count){
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[0]);
    }

    public static ArrayList<GravityProduct> getCategoryDataFromServer(String scenario, int count, String categoryType, int offset) {
        GravityNameValue filter = new GravityNameValue("filter.categoryId", categoryType);
        GravityNameValue of = new GravityNameValue("pagingOffset", "" + offset);
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[]{filter, of});
    }

    public static List<GravityProduct> getSimilarItem(String scenario, int count, String itemId) {
        GravityNameValue pageItemId = new GravityNameValue("currentItemId", itemId);
        return getItemRecommendationFromServerWithKeyVale(scenario, count, new GravityNameValue[]{pageItemId});
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

    public static ArrayList<GravityProduct> getTextSuggestion(String text, String region, String category, int offset) {
        Collection<GravityNameValue> values = new ArrayList<GravityNameValue>();
        values.add(new GravityNameValue("pagingOffset", "" + offset));
        if (category != null) values.add(new GravityNameValue("filter.categoryId", category));
        if (region != null) values.add(new GravityNameValue("Filter.neighborRegion", region));
        values.add(new GravityNameValue("searchString", text));
        return getItemRecommendationFromServerWithKeyVale("MOBIL_LISTING", 50, values.toArray(new GravityNameValue[values.size()]));
    }

    public static void sendEventAsync(final GravityEvent event) {
        event.cookieId = cookieID;
        event.userId = userID;
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    client.addEvents(new GravityEvent[]{event}, true);
                } catch (GravityRecEngException e) {
                    Log.e("event", "failed to send event", e);
                } catch (IOException e) {
                    Log.e("event", "failed to send event with IO", e);
                }
                return null;
            }
        }.execute();
    }

    public static void addViewItemAsync(GravityProduct product) {
        GravityEvent event = new GravityEvent();
        event.eventType = "VIEW";
        event.itemId = product.getProductItemId();
        sendEventAsync(event);
    }

    public static void addSearchAsync(String text) {
        GravityEvent event = new GravityEvent();
        event.eventType = "SEARCH";
        event.nameValues = new GravityNameValue[]{new GravityNameValue("searchString", text)};
        sendEventAsync(event);
    }
}