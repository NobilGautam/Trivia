package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    String url = "https://opentdb.com/api.php?amount=10&type=boolean";
    ArrayList<Question> questions = new ArrayList<>();

    public List<Question> getQuestions(final QuestionListAsyncResponse callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Question question = new Question(jsonObject.getString("question"),
                                    jsonObject.getBoolean("correct_answer"));
                            questions.add(question);
                        }
                        callback.processFinished(questions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> Log.d("JSONData", "Couldn't fetch data"));

        AppController.getInstance().addRequest(jsonObjectRequest);

        return questions;
    }
}
