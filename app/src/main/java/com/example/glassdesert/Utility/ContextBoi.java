package com.example.glassdesert.Utility;

import android.app.Application;
import android.content.Context;

public class ContextBoi extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextBoi.context = getApplicationContext();
    }

    public static Context getContext() {
        return ContextBoi.context;
    }
}
