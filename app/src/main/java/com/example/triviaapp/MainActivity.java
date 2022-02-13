package com.example.triviaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.triviaapp.data.Repository;
import com.example.triviaapp.model.Question;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questions = new Repository().getQuestions();
    }
}