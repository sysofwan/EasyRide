package com.steelheads.easyride;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SySofwan
 * Date: 6/29/13
 * Time: 11:40 AM
 */

public class BusListActivity extends Activity {

    private static final String KEY_HEAD = "routeName";
    private static final String KEY_DIRECTION = "direction";
    private static final String KEY_OBJECTID = "objectId";

    ConnectionDetector cd;
    AlertDialogManager alertDialog;
    ArrayList<HashMap<String, String>> busDataItems;
    ListView listView;
    ProgressDialog progressDialog;
    List<BusData> busDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_template);
        Log.d("Created", "BusList activity created");
        cd = new ConnectionDetector(getApplicationContext());
        alertDialog = new AlertDialogManager();
        busDataItems = new ArrayList<HashMap<String, String>>();
        Log.d("Init", "finished init");

        listView = (ListView) findViewById(R.id.fullscreen_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), busDataItems.get(position).get(KEY_OBJECTID), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!cd.isConnected()) {
            alertDialog.showAlertDialog(this, "Internet error", "Please enable internet", false);
            return;
        }
        new LoadBusList().execute();

    }

    class LoadBusList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(BusListActivity.this);
            progressDialog.setMessage(Html.fromHtml("<b>Loading</b><br/>Loading bus list..."));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        protected String doInBackground(String... args) {
            try {
                busDatas = BusDataHandler.GetAllData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (busDatas != null && !busDatas.isEmpty()) {
                        busDataItems.clear();
                        for (BusData busData : busDatas) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            Log.d("busData", busData.getRouteName());
                            map.put(KEY_HEAD, busData.getRouteNum() + "  " + busData.getRouteName());
                            map.put(KEY_DIRECTION, busData.getDirection());
                            map.put(KEY_OBJECTID, busData.getObjectId());
                            busDataItems.add(map);
                        }
                        ListAdapter adapter = new SimpleAdapter(BusListActivity.this, busDataItems,
                                android.R.layout.simple_list_item_2, new String[]{KEY_HEAD, KEY_DIRECTION},
                                new int[]{android.R.id.text1, android.R.id.text2});
                        listView.setAdapter(adapter);
                    } else {
                        alertDialog.showAlertDialog(BusListActivity.this, "Error", "Sorry, something has gone wrong", false);
                    }
                }
            });
        }
    }

}
