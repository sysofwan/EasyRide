package com.steelheads.easyride;

import android.util.Log;
import com.google.gson.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SySofwan
 * Date: 6/27/13
 * Time: 7:08 PM
 */


public class ParseRequestHandler {

    private static final String PARSE_APPLICATION_ID = "cky1SJo83ANSVN1L3dmXYKcXxyfFGn8mUzyFYTIh";
    private static final String PARSE_REST_KEY = "yXX2pSsACdnrwSjqtYagbttnFi0ZI30l3mFPVc1q";

    private static final String PARSE_URL = "https://api.parse.com/1/classes/";

    private static final String RESULTS = "results";

    public List<Object> MakeRequest(String className, List<NameValuePair> params, Class<?> parseClass) {
        URL url;
        HttpURLConnection connection;
        try {

            if (params != null) {
                url = new URL(PARSE_URL + className + "?" + URLEncodedUtils.format(params, "utf-8"));
            } else {
                url = new URL(PARSE_URL + className);
            }

            Log.d("request url", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Parse-Application-Id", PARSE_APPLICATION_ID);
            connection.setRequestProperty("X-Parse-REST-API-Key", PARSE_REST_KEY);
            connection.setUseCaches(true);
            connection.setDoInput(true);

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            String result = response.toString();
            Log.d("JSON response", result);
            //  TODO: Error Handling
            JsonArray array = new JsonParser()
                    .parse(result)
                    .getAsJsonObject()
                    .get(RESULTS)
                    .getAsJsonArray();
            List<Object> parseObjList = new ArrayList<Object>();

            for (JsonElement elt : array) {
                Object obj = new Gson().fromJson(elt, parseClass);
                parseObjList.add(obj);
            }

            Log.d("parseObj", parseObjList.get(0).getClass().toString());

            return parseObjList;

        } catch (JsonSyntaxException e) {
            Log.d("Json error", "Json parse error");
            Log.getStackTraceString(e);
        } catch (Exception e) {
            Log.d("connection", "Connection exception");
            Log.getStackTraceString(e);
        }
        return null;
    }


}
