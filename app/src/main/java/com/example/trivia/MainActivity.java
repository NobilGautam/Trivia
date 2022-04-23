package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.QuestionListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        List<Question> questionBank = new Repository().getQuestions(new QuestionListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questions) {
                //Initializing the UI
                binding.questionIndexTextView.setText("Question: " + (currentQuestionIndex+1) + "/" + questions.size());
                binding.questionTextView.setText(questions.get(currentQuestionIndex).getQuestion());
            }
        });

        //Checking answer
        binding.trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionBank.get(currentQuestionIndex).getAnswer() == true) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionBank.get(currentQuestionIndex).getAnswer() == false) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        //Updating Question
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.size();
                binding.questionIndexTextView.setText("Question: " + (currentQuestionIndex+1) + "/" + questionBank.size());
                binding.questionTextView.setText(questionBank.get(currentQuestionIndex).getQuestion());
            }
        });
    }
}