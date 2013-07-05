package com.steelheads.easyride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    Button nearbyStops;
    Button busNum;
    Button favourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nearbyStops = (Button) findViewById(R.id.button_nearbyStops);
        busNum = (Button) findViewById(R.id.button_busNum);
        favourites = (Button) findViewById(R.id.button_favourites);
        buttonActions();
    }

    private void buttonActions() {
        nearbyStops.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NearbyStops_Activity.class);
                startActivity(intent);
            }
        });

        busNum.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BusListActivity.class);
                startActivity(intent);
            }
        });

        favourites.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
