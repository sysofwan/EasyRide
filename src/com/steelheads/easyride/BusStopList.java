package com.steelheads.easyride;

import java.io.Serializable;
import java.util.List;

import com.google.api.client.util.Key;


public class BusStopList implements Serializable {

    @Key
    private String status;

    public String getStatus() {
        return status;
    }

    @Key
    private List<BusStop> results;

    public List<BusStop> getResults() {
        return results;
    }

}
