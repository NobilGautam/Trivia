package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;

public interface QuestionListAsyncResponse {
    public void processFinished(ArrayList<Question> questions);
}
