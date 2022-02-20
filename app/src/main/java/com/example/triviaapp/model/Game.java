package com.example.triviaapp.model;

import java.io.Serializable;

public class Game implements Serializable {
    private String username;
    private int numberOfQuestions;
    private QuestionCategory questionCategory;
    private String difficulty;

    public Game(String username, int numberOfQuestions, QuestionCategory questionCategory, String difficulty) {
        this.username = username;
        this.numberOfQuestions = numberOfQuestions;
        this.questionCategory = questionCategory;
        this.difficulty = difficulty;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
