package com.steelheads.easyride;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.api.client.util.Key;

public class BusStop implements Serializable {

    private static final double KM_TO_MILE = 0.621371;

    @Key
    private String id;

    public String getId() {
        return id;
    }

    @Key
    private String reference;

    public String getReference() {
        return reference;
    }

    @Key
    private String name;

    public String getName() {
        return name;
    }

    @Key
    private String formatted_address;

    public String getAddress() {
        return formatted_address;
    }

    @Key
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public String toString() {
        return name + "-" + id + "-" + reference;
    }

    public double getDistanceFrom(double longitude, double latitude) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(latitude, longitude,
                geometry.location.lat, geometry.location.lng, results);
        BigDecimal bd = new BigDecimal((results[0] / 1000) * KM_TO_MILE).setScale(2, RoundingMode.HALF_UP);
        return (double) (bd.doubleValue());
    }

    public static class Geometry implements Serializable {
        @Key
        private Location location;

        private Location getLocation() {
            return location;
        }
    }

    public static class Location implements Serializable {
        @Key
        private double lat;

        public double getLatitude() {
            return lat;
        }

        @Key
        private double lng;

        public double getLongitude() {
            return lng;
        }
    }


}
