package com.igorklimov.tictactoe;

import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Handler;

import com.igorklimov.tictactoe.Game.Side;
import com.igorklimov.tictactoe.databinding.ActivityGameBinding;
import com.igorklimov.tictactoe.single.AI;

class GameController {

    public static String playersName;
    public static String opponentsName;
    static boolean playersTurn;

    private final MainActivity activity;
    private final ActivityGameBinding mBinding;

    int turnCount = 0;
    boolean isDone = false;
    @Side
    public static int playersChar;
    @Side
    public static int opponentChar;
    static int playersScore = 0;
    static int opponentsScore = 0;
    public static int playerFirst = 2;

    private AI mAi;
    private final Field field = new Field();
    boolean isWifiGame;

    public GameController(MainActivity activity, ActivityGameBinding mBinding) {
        this.activity = activity;
        this.mBinding = mBinding;

    }

    void checkVictory() {
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
        if (turnCount == 9 && !isDone) {
            new Handler().postDelayed(() -> checkWinner(0, 0, true), 100);
        }
    }

    @SuppressWarnings("WrongConstant")
    private void checkWinner(int row, int col, boolean draw) {
        isDone = true;
        if (draw) {
            mBinding.result.setTextColor(Color.GRAY);
            mBinding.result.setText(R.string.Draw);
        } else if (field.getField()[row][col] == playersChar) {
            mBinding.result.setTextColor(Color.parseColor("#4CAF50"));
            mBinding.result.setText(R.string.YouWin);
            playersScore++;
            mBinding.score.setText(String.format("Score %d:%d", playersScore, opponentsScore));
        } else {
            mBinding.result.setTextColor(Color.parseColor("#b22222"));
            mBinding.result.setText(R.string.YouLose);
            opponentsScore++;
            mBinding.score.setText(String.format("Score %d:%d", playersScore, opponentsScore));
        }
        mBinding.result.setVisibility(VISIBLE);
        if (!isWifiGame) {
            mBinding.reset.setVisibility(VISIBLE);
        }
    }

    void aiTurn() {
        mAi.makeDecision();
        int row = mAi.getRow();
        int col = mAi.getCol();
        activity.setText(row, col);
    }

    public void setGame(int playerSide, int opponentSide, String playersName, String opponentsName) {
        playersChar = playerSide;
        opponentChar = opponentSide;
        this.playersName = playersName;
        this.opponentsName = opponentsName;
    }

    public void setUpAi() {
        mAi = new AI(field, playersChar, opponentChar);
        if (playerFirst % 2 != 0) {
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

}
