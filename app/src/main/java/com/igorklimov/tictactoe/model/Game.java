package com.igorklimov.tictactoe.model;

import android.graphics.Color;
import androidx.annotation.IntDef;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Game {
    @Retention(RetentionPolicy.CLASS)
    @IntDef({X, O})
    public @interface Side {
    }

    public static final int X = 500;
    public static final int O = 600;

    boolean isWifiGame;
    int turnCount = 0;
    boolean isDone = false;
    @Game.Side
    public static int playersChar;
    @Game.Side
    public static int opponentChar;
    static int playersScore = 0;
    static int opponentsScore = 0;
    public static int playerFirst = 2;

    public static String toString(@Side int s) {
        if (s == X) {
            return "X";
        } else {
            return "O";
        }
    }

    public static int getColor(@Side int s) {
        if (s == X) {
            return Color.parseColor("#27d38b");
        } else {
            return Color.parseColor("#00c5cd");
        }
    }

    public boolean isWifiGame() {
        return isWifiGame;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public boolean isDone() {
        return isDone;
    }

    public static int getPlayersChar() {
        return playersChar;
    }

    public static int getOpponentChar() {
        return opponentChar;
    }

    public static int getPlayersScore() {
        return playersScore;
    }

    public static int getOpponentsScore() {
        return opponentsScore;
    }

    public static int getPlayerFirst() {
        return playerFirst;
    }

    public void setDone() {
        isDone = true;
    }

    public void incrementPlayerScore() {
        playersScore++;
    }

    public void incrementOpponentScore() {
        opponentsScore++;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public void incrementPlayerFirst() {
        playerFirst++;
    }
}
