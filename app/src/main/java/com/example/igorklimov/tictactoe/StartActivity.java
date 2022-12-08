package com.example.igorklimov.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class StartActivity extends AppCompatActivity {
    Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_start);
        context = this;
    }

    public void startOnePlayerGame(View view) {
        Bundle extras = new Bundle();
        if (System.currentTimeMillis() % 2 == 0) {
            extras.putInt(Utils.SIDE, Game.X);
            extras.putInt(Utils.OPPONENT_SIDE, Game.O);
        } else {
            extras.putInt(Utils.SIDE, Game.O);
            extras.putInt(Utils.OPPONENT_SIDE, Game.X);
        }
        extras.putString(Utils.NAME, getString(R.string.you));
        extras.putString(Utils.OPPONENT_NAME, getString(R.string.AI));

        Intent singleGame = new Intent(this, MainActivity.class);
        singleGame.putExtras(extras);
        startActivity(singleGame);
    }
}
