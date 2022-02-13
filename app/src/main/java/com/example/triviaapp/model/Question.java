package com.example.triviaapp.model;

import androidx.annotation.NonNull;

public class Question {
    private String questionText;
    private boolean answerTrue;

    public Question(String questionText, boolean answerTrue) {
        this.questionText = questionText;
        this.answerTrue = answerTrue;
    }

    public Question() {}

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "answer='" + questionText + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
