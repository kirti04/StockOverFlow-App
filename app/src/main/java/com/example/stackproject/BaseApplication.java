package com.example.stackproject;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.stackproject.di.component.ApplicationComponent;
import com.example.stackproject.di.component.DaggerApplicationComponent;

import java.util.ArrayList;
import java.util.List;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    public static BaseApplication instance;
    public static ArrayList<Integer> blocked_list = new ArrayList<>();
    public static ArrayList<Integer> followed_list =  new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;



    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);
        return component;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }
    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}

