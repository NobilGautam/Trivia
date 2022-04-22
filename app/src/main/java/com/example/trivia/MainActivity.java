package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.trivia.controller.AppController;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private String url = "https://opentdb.com/api.php?amount=10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {

                }, error -> {

                });
        AppController.getInstance().addRequest(jsonObjectRequest);
    }
}