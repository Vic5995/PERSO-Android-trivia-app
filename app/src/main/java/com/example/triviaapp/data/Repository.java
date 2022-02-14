package com.example.triviaapp.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
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
                        if (null != callback) callback.processFinished(this.questionArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Main", "FAIL" + error.toString()));

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return this.questionArrayList;
    }

    public List<Question> getQuestionsWithParameters(final AnswerListAsync callback, int amount, int category, String difficulty) {
        String url = URL_PARAMS + "amount=" + amount + "&category=" + category + "&difficulty=" + difficulty + "&type=boolean";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject cur = (JSONObject)results.get(i);
                            Question question = new Question(cur.getString("question"), cur.getBoolean("correct_answer"));
                            questionArrayList.add(question);
                        }
                        if (null != callback) callback.processFinished(this.questionArrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("Main", "FAIL" + error.toString()));

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return this.questionArrayList;
    }
}
