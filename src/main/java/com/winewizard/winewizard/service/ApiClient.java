package com.winewizard.winewizard.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ApiClient {

    private static final String API_URL = "https://api.upcdatabase.org/search/";
    private static String apiKey;

    static {
        try (InputStream input = ApiClient.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            else {
                prop.load(input);
                apiKey = prop.getProperty("api.key");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static final String OFF_API = "https://world.openfoodfacts.org/cgi/search.pl";
    private static final Gson gson = new Gson();

    public List<Map<String, String>> searchProducts(String query) {
        List<Map<String, String>> productsList = new ArrayList<>();
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String fullUrl = OFF_API + "?search_terms=" + encodedQuery + "&search_simple=1&action=process&json=1&page_size=20";
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    JsonObject jsonResponse = gson.fromJson(in, JsonObject.class);
                    JsonArray products = jsonResponse.getAsJsonArray("products");

                    for (JsonElement productElement : products) {
                        JsonObject productObject = productElement.getAsJsonObject();
                        Map<String, String> productDetails = new HashMap<>();

                        productDetails.put("Name", productObject.has("product_name") ? productObject.get("product_name").getAsString() : "No name");
                        productDetails.put("EAN", productObject.get("code").getAsString());
                        productDetails.put("Description", productObject.has("generic_name") ? productObject.get("generic_name").getAsString() : "No description");
                        productDetails.put("Categories", productObject.has("categories") ? productObject.get("categories").getAsString() : "No categories");

                        productsList.add(productDetails);
                    }
                }
            } else {
                System.out.println("GET request not worked, Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    public String searchProduct(String searchTerm) {
        try {
            String queryUrl = API_URL + "?query=" + encodeValue(searchTerm) + "&page=1";
            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return content.toString();
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRandomFact(){
        // generate request to GET https://uselessfacts.jsph.pl/api/v2/facts/today with text/plain
        try {
            String queryUrl = "https://uselessfacts.jsph.pl/api/v2/facts/today";
            URL url = new URL(queryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/plain");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    // answer is this: {"id":"bc185f4257b4996266eaa3e18c490ae4","text":"Oak trees do not produce acorns until they are fifty years of age or older.","source":"djtech.net","source_url":"http://www.djtech.net/humor/useless_facts.htm","language":"en","permalink":"https://uselessfacts.jsph.pl/api/v2/facts/bc185f4257b4996266eaa3e18c490ae4"}
                    // extract text
                    String[] parts = content.toString().split("\"");
                    return parts[7];
                    // return content.toString();
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    private String encodeValue(String value) {
        try {
            return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



}
