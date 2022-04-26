package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.QuestionListAsyncResponse;
import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.util.Prefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_SCORE = "CurrentScore";
    private ActivityMainBinding binding;
    List<Question> questionBank;
    Prefs prefs = new Prefs(MainActivity.this);
    private int currentQuestionIndex = 0;
    int currentScore = 0;
    int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        questionBank = new Repository().getQuestions(new QuestionListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questions) {
                //Initializing the UI
                binding.questionIndexTextView.setText("Question: " + (currentQuestionIndex+1) + "/" + questions.size());
                binding.questionTextView.setText(questions.get(currentQuestionIndex).getQuestion());
            }
        });

        binding.currentScoreTextView.setText("Current Score: " + currentScore);

        highScore = prefs.getHighestScore();
        binding.highScoreTextView.setText("High Score: " + String.valueOf(highScore));

        //Checking answer
        binding.trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionBank.get(currentQuestionIndex).getAnswer() == true) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                    currentScore++;
                    fadeAnimation();
                    prefs.saveHighestScore(currentScore);
                } else {
                    Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
                    shakeAnimation();
                    if(currentScore != 0) {
                        currentScore--;
                    }
                }
                binding.currentScoreTextView.setText("Current Score: " + currentScore);
                updateQues();
            }
        });
        binding.falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionBank.get(currentQuestionIndex).getAnswer() == false) {
                    Toast.makeText(MainActivity.this, "That's correct!", Toast.LENGTH_SHORT).show();
                    currentScore++;
                    fadeAnimation();
                    prefs.saveHighestScore(currentScore);
                } else {
                    Toast.makeText(MainActivity.this, "That's wrong!", Toast.LENGTH_SHORT).show();
                    shakeAnimation();
                    if(currentScore != 0) {
                        currentScore--;
                    }
                }
                binding.currentScoreTextView.setText("Current Score: " + currentScore);
                updateQues();
            }
        });

        //Updating Question
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQues();
            }
        });
    }

    private void updateQues() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.size();
        binding.questionIndexTextView.setText("Question: " + (currentQuestionIndex+1) + "/" + questionBank.size());
        binding.questionTextView.setText(questionBank.get(currentQuestionIndex).getQuestion());
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(getResources().getColor(R.color.textColor));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.cardView.setAnimation(shake);
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(150);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(1);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextView.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextView.setTextColor(getResources().getColor(R.color.textColor));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.cardView.setAnimation(alphaAnimation);
    }
}