package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.trivia.data.QuestionListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.model.Question;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repository = (Repository) new Repository().getQuestions(questions ->
                Log.d("DATA", questions.toString()));

    }
}