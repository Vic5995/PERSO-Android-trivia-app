package com.example.triviaapp;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.triviaapp.data.Repository;
import com.example.triviaapp.databinding.ActivityMainBinding;
import com.example.triviaapp.model.Question;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Question> questionBank;
    private int currentQuestionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        new Repository().getQuestions(questionArrayList -> {
            questionBank = questionArrayList;
            updateQuestion();
        });

        binding.nextQuestionBtn.setOnClickListener(view -> {
            currentQuestionIndex++;
            updateQuestion();
        });
        binding.answerTrueBtn.setOnClickListener(view -> {
            checkAnswer(true);
        });
        binding.answerFalseBtn.setOnClickListener(view -> {
            checkAnswer(false);
        });
    }

    private void updateQuestion() {
        binding.questionTv.setText(Html.fromHtml(questionBank.get(currentQuestionIndex).getQuestionText()));
        binding.numberQuestionTv.setText(String.format(getString(R.string.number_question_text), currentQuestionIndex +1 , questionBank.size()));
    }

    private void checkAnswer(boolean userAnswer) {
        boolean answer = questionBank.get(currentQuestionIndex).isAnswerTrue();
        if (answer != userAnswer) {
            shakeAnimation();
        }
        currentQuestionIndex++;
        updateQuestion();
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.questionCv.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}