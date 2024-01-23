package com.winewizard.winewizard.service;

import com.google.gson.*;
import com.winewizard.winewizard.model.ZipCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ApiClientZipCodes {

    private static final String API_URL = "https://api.upcdatabase.org/search/";
    private static String apiKey;

    static {
        try (InputStream input = ApiClientZipCodes.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            else {
                prop.load(input);
                apiKey = prop.getProperty("api.key.zipcodes");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static final String API = "https://app.zipcodebase.com/api/v1/";
    private static final Gson gson = new Gson();

    /**
     * Return an exact match or null if none found
     * @param zipCode Zip code like 93333
     * @return ZipCode
     * @throws InputMismatchException
     */
    public ZipCode getGermanZipInformation(String zipCode) throws InputMismatchException {
        ArrayList<ZipCode> codes = getZipInformation(zipCode);
        for (var code :
                codes) {
            if (code.getCountryCode().equals("DE") && code.getZipCode().equals(zipCode)) {
                return code;
            }
        }
        return  null;
    }

    public ArrayList<ZipCode> getZipInformation(String zipCode) throws InputMismatchException{
        if(zipCode.contains(",")  || zipCode.contains(" ")) {
            throw new InputMismatchException("Invalid input");
        }

        ArrayList<ZipCode> zipCodes =  new ArrayList<ZipCode>();
        try {
            String encodedQuery = URLEncoder.encode(zipCode, StandardCharsets.UTF_8.toString());
            String fullUrl = API + "search?codes=" + encodedQuery + "&apikey=" + apiKey;
            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    try {
                        JsonObject jsonResponse = gson.fromJson(in, JsonObject.class);
                        JsonObject allResults = jsonResponse.getAsJsonObject("results");
                        JsonArray resultsForInput = allResults.getAsJsonArray(zipCode);

                        for (JsonElement zipCodeResult : resultsForInput) {
                            ZipCode code = getZipCode(zipCodeResult);
                            if (code != null) {
                                zipCodes.add(code);
                                System.out.println(code.toString());
                            }
                        }
                    }catch (ClassCastException e) {
                        System.out.println("No results found");
                    }
                }
            } else {
                System.out.println("GET request not worked, Response Code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zipCodes;
    }

    private static ZipCode getZipCode(JsonElement zipCodeResult) {
        JsonObject zipObject = zipCodeResult.getAsJsonObject();
        var city = zipObject.has("city") ? zipObject.get("city") : null;
        var state = zipObject.has("state")? zipObject.get("state"): null;

        if(city != null && !city.isJsonNull() && state != null && !state.isJsonNull()) {
            ZipCode code = new ZipCode();
            code.setZipCode(zipObject.has("postal_code") ? zipObject.get("postal_code").getAsString() : "No code");
            code.setCountryCode(zipObject.has("country_code") ? zipObject.get("country_code").getAsString() : "No country code");
            code.setCity(city.getAsString());
            code.setState(state.getAsString());
            return code;
        }

        return null;
    }


    // Main method for testing
    public static void main(String[] args) {
        ApiClientZipCodes client = new ApiClientZipCodes();
         client.getZipInformation("93333");
        //System.out.println(response);
    }
}
