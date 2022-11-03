package com.example.igorklimov.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.igorklimov.tictactoe.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStartBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        context = this;
    }

    public void startOnePlayerGame(View view) {
        if (System.currentTimeMillis() % 2 == 0) {
            MainActivity.playersChar = Game.X;
            MainActivity.opponentChar = Game.O;
        } else {
            MainActivity.playersChar = Game.O;
            MainActivity.opponentChar = Game.X;
        }
        MainActivity.playersName = getString(R.string.you);
        MainActivity.opponentsName = getString(R.string.AI);
        Intent singleGame = new Intent(this, MainActivity.class);
        startActivity(singleGame);
    }
}
