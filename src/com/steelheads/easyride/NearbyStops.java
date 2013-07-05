package com.steelheads.easyride;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class NearbyStops {
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    //private static final double RADIUS = 5000; //in meters
    private static final String API_KEY = "AIzaSyD9kl3qDsh6BBzCH2MVOQ10ngtv07oXNj8";
    private static final String TYPE = "bus_station";

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    private Context context;

    public NearbyStops(Context context) {
        this.context = context;
    }

    public BusStopList search(double latitude, double longitude) {
        try {
            HttpRequestFactory httpRequestFactory = createRequestFactory();
            HttpRequest request = httpRequestFactory.
                    buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
            request.getUrl().put("key", API_KEY);
            request.getUrl().put("location", latitude + "," + longitude);
            //request.getUrl().put("radius", RADIUS);
            request.getUrl().put("sensor", true);
            request.getUrl().put("rankby", "distance");
            request.getUrl().put("types", TYPE);
            BusStopList busStops = request.execute().parseAs(BusStopList.class);
            Log.d("Nearby Stops status", busStops.getStatus());
            Log.d("places url", request.getUrl().toString());
            return busStops;
        } catch (HttpResponseException e) {
            Log.e("search error", e.getMessage());
            return null;
        } catch (IOException e) {
            Log.e("IO Error", e.getMessage());
            return null;
        }
    }

    private HttpRequestFactory createRequestFactory() {
        return HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {

            @Override
            public void initialize(HttpRequest arg0) throws IOException {
                HttpHeaders header = new HttpHeaders();
                header.setUserAgent(context.getString(R.string.app_name));
                arg0.setHeaders(header);
                JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
                arg0.setParser(parser);
            }
        });
    }

}
