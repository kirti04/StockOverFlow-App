package com.example.stackproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityReceiver extends BroadcastReceiver {
    public static ConnectivityReceiverListener connectivityReceiverListener;
    public static boolean isConnected;
    public ConnectivityReceiver()
    {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        isConnected = BaseApplication.hasNetwork();
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }

    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }


}
