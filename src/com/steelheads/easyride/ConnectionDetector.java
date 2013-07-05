package com.steelheads.easyride;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionDetector {
    private Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnected() {
        ConnectivityManager connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Log.d("Connection", "Checking connection");
        if (connection != null) {
            NetworkInfo[] info = connection.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    Log.d("Connection", "have connection");
                    return true;
                }
            }
        }
        Log.d("Connection", "no connection");
        return false;
    }
}
