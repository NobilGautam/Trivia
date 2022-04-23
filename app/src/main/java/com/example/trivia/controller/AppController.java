package com.example.trivia.controller;

import android.app.Application;

import com.android.volley.*;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static AppController instance;
    private RequestQueue requestQueue;

    public static synchronized AppController getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
