package com.example.triviaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.triviaapp.databinding.ActivityMenuBinding;
import com.example.triviaapp.fragment.NewGameBottomSheetFragment;
import com.example.triviaapp.listener.StartGameListener;
import com.example.triviaapp.model.Game;
import com.example.triviaapp.model.QuestionCategory;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MenuActivity extends AppCompatActivity implements StartGameListener {

    public final String TAG = "Menu Activity";
    private ActivityMenuBinding binding;

    NewGameBottomSheetFragment newGameBottomSheetFragment;

    private final int REQUEST_CODE = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);

        Log.d(TAG, "onCreate: ");

        newGameBottomSheetFragment = new NewGameBottomSheetFragment(this);
        ConstraintLayout constraintLayout = findViewById(R.id.bottom_sheet_fragment_constraint_layout);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        binding.menuNewGameBtn.setOnClickListener(v -> showBottomSheetFragment());
    }

    private void showBottomSheetFragment(){
        newGameBottomSheetFragment.show(getSupportFragmentManager(), newGameBottomSheetFragment.getTag());
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

    @Override
    public void startGame(Game game) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        intent.putExtra("game", game);
        startActivityForResult(intent, REQUEST_CODE);
    }

}