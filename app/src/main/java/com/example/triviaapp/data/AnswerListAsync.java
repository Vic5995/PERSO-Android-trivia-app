package com.example.triviaapp.data;

import com.example.triviaapp.model.Question;

import java.util.ArrayList;

public interface AnswerListAsync {
    void processFinished(ArrayList<Question> questionArrayList);

    void processFinishedWithError(ArrayList<Question> questionArrayList, int responseCode);
}
