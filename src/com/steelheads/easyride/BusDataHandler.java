package com.steelheads.easyride;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SySofwan
 * Date: 6/29/13
 * Time: 11:48 AM
 */

public class BusDataHandler {

    public static List<BusData> GetAllData() {
        ParseRequestHandler parseRequestHandler = new ParseRequestHandler();
        List<Object> dataObjects = parseRequestHandler.MakeRequest("BusData", null, BusData.class);
        List<BusData> busData = new ArrayList<BusData>();

        for (Object obj : dataObjects) {
            if (obj instanceof BusData) {
                busData.add((BusData) obj);
            }
        }
        return busData;
    }

}
