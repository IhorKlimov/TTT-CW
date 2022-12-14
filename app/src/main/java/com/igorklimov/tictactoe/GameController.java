package com.igorklimov.tictactoe;

import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Handler;

import com.igorklimov.tictactoe.model.Field;
import com.igorklimov.tictactoe.model.Game;
import com.igorklimov.tictactoe.databinding.ActivityGameBinding;
import com.igorklimov.tictactoe.single.AI;
import com.igorklimov.tictactoe.view.MainActivity;

public class GameController {

    public static String playersName;
    public static String opponentsName;
    public static boolean playersTurn;

    private final MainActivity activity;
    private final ActivityGameBinding mBinding;

    private final Game game = new Game();
    private final Field field = new Field();
    private AI ai;

    public GameController(MainActivity activity, ActivityGameBinding mBinding) {
        this.activity = activity;
        this.mBinding = mBinding;

    }

    public void checkVictory() {
        if (field.getField()[0][0] == field.getField()[0][1] && field.getField()[0][1] == field.getField()[0][2] && field.getField()[0][0] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawHLine(0);
                checkWinner(0, 0, false);
            }, 100);
            return;
        }
        if (field.getField()[1][0] == field.getField()[1][1] && field.getField()[1][1] == field.getField()[1][2] && field.getField()[1][0] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawHLine(1);
                checkWinner(1, 0, false);
            }, 100);
            return;
        }
        if (field.getField()[2][0] == field.getField()[2][1] && field.getField()[2][1] == field.getField()[2][2] && field.getField()[2][0] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawHLine(2);
                checkWinner(2, 0, false);
            }, 100);
            return;
        }
        if (field.getField()[0][0] == field.getField()[1][0] && field.getField()[1][0] == field.getField()[2][0] && field.getField()[0][0] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawVLine(0);
                checkWinner(0, 0, false);
            }, 100);
            return;
        }
        if (field.getField()[0][1] == field.getField()[1][1] && field.getField()[1][1] == field.getField()[2][1] && field.getField()[0][1] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawVLine(1);
                checkWinner(0, 1, false);
            }, 100);
            return;
        }
        if (field.getField()[0][2] == field.getField()[1][2] && field.getField()[1][2] == field.getField()[2][2] && field.getField()[0][2] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawVLine(2);
                checkWinner(0, 2, false);
            }, 100);
            return;
        }
        if (field.getField()[0][0] == field.getField()[1][1] && field.getField()[1][1] == field.getField()[2][2] && field.getField()[0][0] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawDLine(false);
                checkWinner(0, 0, false);
            }, 100);
            return;
        }
        if (field.getField()[0][2] == field.getField()[1][1] && field.getField()[1][1] == field.getField()[2][0] && field.getField()[0][2] != 0) {
            new Handler().postDelayed(() -> {
                activity.drawDLine(true);
                checkWinner(0, 2, false);
            }, 100);
            return;
        }
        if (game.getTurnCount() == 9 && !game.isDone()) {
            new Handler().postDelayed(() -> checkWinner(0, 0, true), 100);
        }
    }

    @SuppressWarnings("WrongConstant")
    private void checkWinner(int row, int col, boolean draw) {
        game.setDone();
        if (draw) {
            mBinding.result.setTextColor(Color.GRAY);
            mBinding.result.setText(R.string.Draw);
        } else if (field.getField()[row][col] == game.playersChar) {
            mBinding.result.setTextColor(Color.parseColor("#4CAF50"));
            mBinding.result.setText(R.string.YouWin);
            game.incrementPlayerScore();
            mBinding.score.setText(String.format("Score %d:%d", game.getPlayersScore(), game.getOpponentsScore()));
        } else {
            mBinding.result.setTextColor(Color.parseColor("#b22222"));
            mBinding.result.setText(R.string.YouLose);
            game.incrementOpponentScore();
            mBinding.score.setText(String.format("Score %d:%d", game.getPlayersScore(), game.getOpponentsScore()));
        }
        mBinding.result.setVisibility(VISIBLE);
        if (!game.isWifiGame()) {
            mBinding.reset.setVisibility(VISIBLE);
        }
    }

    public void aiTurn() {
        ai.makeDecision();
        int row = ai.getRow();
        int col = ai.getCol();
        activity.setText(row, col);
    }

    public void setGame(int playerSide, int opponentSide, String playersName, String opponentsName) {
        game.playersChar = playerSide;
        game.opponentChar = opponentSide;
        this.playersName = playersName;
        this.opponentsName = opponentsName;
    }

    public void setUpAi() {
        ai = new AI(field, game);
        if (game.playerFirst % 2 != 0) {
            playersTurn = false;
            Handler handler = new Handler();
            handler.postDelayed(() -> aiTurn(), 500);
        } else {
            playersTurn = true;
        }
    }

    public Field getField() {
        return field;
    }

    public Game getGame() {
        return game;
    }
}
