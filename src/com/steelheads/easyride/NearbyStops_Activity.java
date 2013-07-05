package com.steelheads.easyride;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class NearbyStops_Activity extends Activity {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DISTANCE = "distance";

    ConnectionDetector cd;
    AlertDialogManager alertDialog;
    NearbyStops nearbyStops;
    BusStopList busStops;
    GPSTracker gps;
    ListView listView;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> busStopItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        Log.d("Created", "activity created");
        cd = new ConnectionDetector(getApplicationContext());
        alertDialog = new AlertDialogManager();
        busStopItems = new ArrayList<HashMap<String, String>>();
        Log.d("finish init", "init finished");
        gps = new GPSTracker(this); // CHANGED

/*		if (!cd.isConnected()) {
            alertDialog.showAlertDialog(this, "Internet Error",
					"Please enable internet", false);
			return;
		}
		Log.d("gps", "checking gps");
		gps = new GPSTracker(this);
		if (gps.canGetLocation()) {
			Log.d("Your location", "longitude:"+ gps.getLongitude() + 
					", latitude:" + gps.getLatitude());
		} else {
			gps.showSettingsAlert();
			return;
		}*/

        listView = (ListView) findViewById(R.id.fullscreen_list);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  TODO: setOnClick
                Toast.makeText(getBaseContext(), busStopItems.get(position).get(KEY_ID), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class LoadStops extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NearbyStops_Activity.this);
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Nearby Stops..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {
            nearbyStops = new NearbyStops(NearbyStops_Activity.this);
            try {
                busStops = nearbyStops.search(gps.getLatitude(), gps.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    String status = busStops.getStatus();
                    if (status.equals("OK")) {
                        if (busStops.getResults() != null) {
                            busStopItems.clear();
                            for (BusStop stops : busStops.getResults()) {
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put(KEY_NAME, stops.getName());
                                map.put(KEY_ID, stops.getId());
                                map.put(KEY_DISTANCE, Double.toString(stops.getDistanceFrom(gps.getLongitude(), gps.getLatitude())) + " miles");
                                busStopItems.add(map);
                            }
                            ListAdapter adapter = new SimpleAdapter(NearbyStops_Activity.this, busStopItems,
                                    android.R.layout.simple_list_item_2, new String[]{KEY_NAME, KEY_DISTANCE},
                                    new int[]{android.R.id.text1, android.R.id.text2});
                            listView.setAdapter(adapter);
                        }
                    } else if (status.equals("ZERO_RESULTS")) {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Bus stop", "Sorry, no bus stop is found", false);
                    } else if (status.equals("UKNOWN_ERROR")) {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Search Error", "Sorry,  an uknown error occured", false);
                    } else if (status.equals("OVER_QUERY_LIMIT")) {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Search Error", "Sorry,  query limit has been reached", false);
                    } else if (status.equals("REQUEST_DENIED")) {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Search Error", "Sorry,  request is denied", false);
                    } else if (status.equals("INVALID_REQUEST")) {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Search Error", "Sorry,  request is invalid (ask developer)", false);
                    } else {
                        alertDialog.showAlertDialog(NearbyStops_Activity.this,
                                "Search Error", "Sorry,  uknown error (ask developer)", false);
                    }

                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        gps.stopGps();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!cd.isConnected()) {
            alertDialog.showAlertDialog(this, "Internet Error",
                    "Please enable internet", false);
            return;
        }
        gps.getLocation();
        if (gps.canGetLocation()) {
            Log.d("Your location", "longitude:" + gps.getLongitude() +
                    ", latitude:" + gps.getLatitude());
        } else {
            gps.showSettingsAlert();
            return;
        }
        new LoadStops().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearby_stops_, menu);
        return true;
    }

}
