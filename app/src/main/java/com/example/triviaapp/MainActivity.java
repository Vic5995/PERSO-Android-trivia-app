package com.example.triviaapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.triviaapp.data.Repository;
import com.example.triviaapp.databinding.ActivityMainBinding;
import com.example.triviaapp.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SCORE_ID = "Score_pref";
    private ActivityMainBinding binding;
    private List<Question> questionBank;
    private int currentQuestionIndex = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            new Repository().getQuestionsWithParameters(questionArrayList -> {
                Log.d("BUNDLE", "onCreate: PARAMS");
                questionBank = questionArrayList;
                updateQuestion();
            }, extra.getInt("amount"), extra.getInt("category"), extra.getString("difficulty"));
        } else {
            new Repository().getQuestions(questionArrayList -> {
                Log.d("BUNDLE", "onCreate: FAIL");
                questionBank = questionArrayList;
                updateQuestion();
            });
        }



        binding.nextQuestionBtn.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.size();
            updateQuestion();
        });
        binding.answerTrueBtn.setOnClickListener(view -> {
            checkAnswer(true);
            updateQuestion();
        });
        binding.answerFalseBtn.setOnClickListener(view -> {
            checkAnswer(false);
            updateQuestion();
        });

        sharedPreferences = getSharedPreferences(SCORE_ID, MODE_PRIVATE); // only this application can access
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCORE_ID, 15);
        editor.apply();  // saving to disk

        // Get data back from SharedPref
        int value = sharedPreferences.getInt(SCORE_ID, 0);
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
            fadeAnimation();
        } else {
            toastIdMsg = R.string.incorrect_answer;
            shakeAnimation();
        }
        Snackbar.make(binding.questionCv, toastIdMsg, Snackbar.LENGTH_SHORT).show();
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
                binding.questionCv.setCardBackgroundColor(getColor(R.color.blue_gray_200));
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
}