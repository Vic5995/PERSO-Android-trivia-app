package com.example.triviaapp.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.model.Game;
import com.example.triviaapp.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private int apiResponseCode = 0;

    ArrayList<Question> questionArrayList = new ArrayList<>();

    String URL = "https://opentdb.com/api.php?amount=30&category=9&type=boolean";
    String URL_PARAMS = "https://opentdb.com/api.php?";
    //https://opentdb.com/api.php?amount=10&category=10&difficulty=medium&type=boolean

    public List<Question> getQuestions(final AnswerListAsync callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject cur = (JSONObject)results.get(i);
                            Question question = new Question(cur.getString("question"), cur.getBoolean("correct_answer"));
                            questionArrayList.add(question);
                        }
                        if (null != callback) {
                            if (this.apiResponseCode == 0) {
                                callback.processFinished(this.questionArrayList);
                            } else {
                                callback.processFinishedWithError(this.questionArrayList, this.apiResponseCode);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Main", "FAIL" + error.toString()));

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return this.questionArrayList;
    }

    public List<Question> getQuestionsWithParameters(Game game, final AnswerListAsync callback) {
        String url = URL_PARAMS
                + "amount=" + game.getNumberOfQuestions()
                + "&category=" + game.getQuestionCategory().getId()
                + "&difficulty=" + game.getDifficulty()
                + "&type=boolean";
        Log.d("REPOSITORY", "getQuestionsWithParameters: " + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        int responseCode = response.getInt("response_code");
                        if (responseCode == 0) {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject cur = (JSONObject)results.get(i);
                                Question question = new Question(cur.getString("question"), cur.getBoolean("correct_answer"));
                                questionArrayList.add(question);
                            }
                            if (null != callback) callback.processFinished(this.questionArrayList);
                        } else {
                            Log.d("REPOSITORY", "getQuestionsWithParameters: response code " + responseCode);
                            this.apiResponseCode = responseCode;
                            this.getQuestions(callback);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Main", "FAIL" + error.toString()));

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return this.questionArrayList;
    }
}
