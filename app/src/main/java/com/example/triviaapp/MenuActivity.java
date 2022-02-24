package com.example.triviaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.triviaapp.databinding.ActivityMenuBinding;
import com.example.triviaapp.model.Game;
import com.example.triviaapp.model.QuestionCategory;
import com.google.android.material.snackbar.Snackbar;

public class MenuActivity extends AppCompatActivity {

    public final String TAG = "Menu Activity";
    private ActivityMenuBinding binding;

    private QuestionCategory category = QuestionCategory.GENERAL_KNOWLEDGE;
    private int numberOfQuestions = 10;
    private String difficulty = "easy";

    private final int REQUEST_CODE = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);

        setSpinner();
        setDifficultyRadios();
        setNumberOfQuestionsRadios();

        binding.menuNewGameBtn.setOnClickListener(v -> {
            String username = binding.newGameUserInput.getText().toString().trim();
            if (!TextUtils.isEmpty(username)) {
                Game myGame = new Game(
                        username,
                        numberOfQuestions,
                        category,
                        difficulty);
                binding.newGameUserInput.setText("");

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                intent.putExtra("game", myGame);
                startActivityForResult(intent, REQUEST_CODE);

            } else {
                Snackbar.make(binding.menuNewGameBtn, "Username field must not be empty", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Toast.makeText(this, String.format(getString(R.string.score_final_text_toast),
                        data.getIntExtra("finalScore", -1)), Toast.LENGTH_SHORT).show();
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(this, "Error in initialisation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.newGameThemeSpinner.setAdapter(adapter);
        binding.newGameThemeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence selected = (CharSequence) parent.getSelectedItem();
                category = QuestionCategory.getQuestionCategoryFromLabel(selected.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDifficultyRadios() {
        binding.newGameDifficultyChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.newGameDifficultyEasy.getId()) {
                difficulty = "easy";
            } else if (checkedId == binding.newGameDifficultyMedium.getId()) {
                difficulty = "medium";
            } else {
                difficulty = "hard";
            }

            Log.d("RADIO", "onCheckedChanged: " + difficulty);
        });
    }

    private void setNumberOfQuestionsRadios() {

        binding.newGameQuestionsNumberChipGroup.check(binding.newGameQuestionsChip10.getId());
        binding.newGameQuestionsNumberChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.newGameQuestionsChip10.getId()) {
                numberOfQuestions = 15;
            } else if (checkedId == binding.newGameQuestionsChip20.getId()) {
                numberOfQuestions = 30;
            } else {
                numberOfQuestions = 60;
            }
        });
    }


}