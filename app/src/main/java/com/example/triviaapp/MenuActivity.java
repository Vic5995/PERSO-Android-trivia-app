package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.triviaapp.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private ActivityMenuBinding binding;
    private int categoryNumber = 9;
    private String difficulty = "easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);

        setSpinner();
        setDifficultyRadios();
        setNumberPicker();

        binding.startQuizBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            intent.putExtra("category", categoryNumber);
            intent.putExtra("amount", binding.inputNumberQuestion.getValue());
            intent.putExtra("difficulty", difficulty);
            startActivity(intent);
        });
    }

    private void setNumberPicker() {
        NumberPicker numberPicker = binding.inputNumberQuestion;
        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(100);
    }

    private void setSpinner() {
        Spinner spinner = binding.spinnerTheme;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharSequence selected = (CharSequence) parent.getSelectedItem();
                categoryNumber = getCategoryNumber(selected);
                Log.d("SPINNER", "onItemSelected: " + categoryNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setDifficultyRadios() {
        RadioGroup radioGroup = binding.selectDifficulty;
        radioGroup.check(R.id.easy);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.medium:
                    difficulty = "medium";
                    break;
                case R.id.hard:
                    difficulty = "hard";
                    break;
                default:
                    difficulty = "easy";
            }
            Log.d("RADIO", "onCheckedChanged: " + difficulty);
        });
    }

    private int getCategoryNumber(CharSequence selectedCategory) {
        switch (selectedCategory.toString()) {
            case "Entertainment: Books": return 10;
            case "Entertainment: Film": return 11;
            case "Entertainment: Music": return 12;
            case "Entertainment: Musicals & Theatres": return 13;
            case "Entertainment: Television": return 14;
            case "Entertainment: Video Games": return 15;
            case "Entertainment: Board Games": return 16;
            case "Science & Nature": return 17;
            case "Science: Computers": return 18;
            case "Science: Mathematics": return 19;
            case "Mythology": return 20;
            case "Sports": return 21;
            case "Geography": return 22;
            case "History": return 23;
            case "Politics": return 24;
            case "Art": return 25;
            case "Celebrities": return 26;
            case "Animals": return 27;
            case "Vehicles": return 28;
            case "Entertainment: Comics": return 29;
            case "Science: Gadgets": return 30;
            case "Entertainment: Japanese Anime & Manga": return 31;
            case "Entertainment: Cartoon & Animations": return 32;
            default: return 9;
        }

    }

}