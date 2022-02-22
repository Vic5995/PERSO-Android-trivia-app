package com.example.triviaapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.triviaapp.data.AnswerListAsync;
import com.example.triviaapp.data.Repository;
import com.example.triviaapp.databinding.ActivityMainBinding;
import com.example.triviaapp.model.Game;
import com.example.triviaapp.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AnswerListAsync {

    private ActivityMainBinding binding;
    private List<Question> questionBank;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Game currGame = (Game) getIntent().getSerializableExtra("game");
        if (currGame != null) {
            new Repository().getQuestionsWithParameters(currGame, this);
        } else {
            new Repository().getQuestions(this);
        }

        binding.nextQuestionBtn.setOnClickListener(view -> {
            if (currentQuestionIndex < questionBank.size() - 1) {
                currentQuestionIndex++;
                updateQuestion();
            } else {
                updateHigherScore();
            }
        });
        binding.answerTrueBtn.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();
        });
        binding.answerFalseBtn.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();
        });
    }

    private void updateQuestion() {
        String question = questionBank.get(currentQuestionIndex).getQuestionText();
        binding.questionTv.setText(Html.fromHtml(question));
        binding.numberQuestionTv.setText(String.format(getString(R.string.number_question_text), currentQuestionIndex +1 , questionBank.size()));
    }

    private void checkAnswer(boolean userAnswer) {
        boolean answer = questionBank.get(currentQuestionIndex).isAnswerTrue();
        int toastIdMsg;
        if (userAnswer == answer) {
            toastIdMsg = R.string.correct_answer;
            score++;
            fadeAnimation();
        } else {
            toastIdMsg = R.string.incorrect_answer;
            shakeAnimation();
        }
        Snackbar.make(binding.questionCv, toastIdMsg, Snackbar.LENGTH_SHORT).show();
    }

    private void updateHigherScore() {
        String SCORE_ID = "Score_pref";
        SharedPreferences sharedPreferences = getSharedPreferences(SCORE_ID, MODE_PRIVATE); // only this application can access
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int finalScore = score * 100 / questionBank.size();
        int value = sharedPreferences.getInt(SCORE_ID, -1);
        if (finalScore > value) {
            editor.putInt(SCORE_ID, finalScore);
            editor.apply();  // saving to disk
        }
        Intent intent  = new Intent().putExtra("finalScore", finalScore);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        binding.questionCv.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionCv.setCardBackgroundColor(Color.GREEN);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionCv.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.questionCv.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTv.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTv.setTextColor(Color.BLACK);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    public void processFinished(ArrayList<Question> questionArrayList) {
        questionBank = questionArrayList;
        if (questionArrayList.size() > 0) {
            updateQuestion();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void processFinishedWithError(ArrayList<Question> questionArrayList, int responseCode) {
        questionBank = questionArrayList;
        if (questionArrayList.size() > 0) {
            updateQuestion();
            if (responseCode == 1) {
                Snackbar.make(binding.questionCv, "Not enough question on the database for your selection", Snackbar.LENGTH_SHORT).show();

            }
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}