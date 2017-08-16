package com.example.vb.test_gson_retrofit_realm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by VB on 10.08.2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
    }
}
