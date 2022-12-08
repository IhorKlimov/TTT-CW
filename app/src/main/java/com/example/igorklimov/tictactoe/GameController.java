package com.example.igorklimov.tictactoe;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;

import com.example.igorklimov.tictactoe.Game.Side;
import com.example.igorklimov.tictactoe.databinding.ActivityGameBinding;
import com.example.igorklimov.tictactoe.single.AI;

class GameController {
    @Side
    int[][] field = new int[3][3];
    private MainActivity activity;
    private ActivityGameBinding mBinding;
    int turnCount = 0;
    boolean done = false;
    @Side
    public static int playersChar;
    @Side
    public static int opponentChar;
    static int playersScore = 0;
    static int opponentsScore = 0;
    public static int playerFirst = 2;
    private AI mAi;
    private String opponentsRegId;
    private Context mContext;
    boolean wifiGame;
    private boolean isOrganizer;
    boolean[][] isTaken = new boolean[3][3];
    public static String playersName;
    public static String opponentsName;
    static boolean playersTurn;

    public GameController(MainActivity activity, ActivityGameBinding mBinding) {
        this.activity = activity;
        this.mContext = activity;
        this.mBinding = mBinding;
    }

    void checkVictory() {
        if (field[0][0] == field[0][1] && field[0][1] == field[0][2] && field[0][0] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawHLine(0);
                    checkWinner(0, 0, false);
                }
            }, 100);
            return;
        }
        if (field[1][0] == field[1][1] && field[1][1] == field[1][2] && field[1][0] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawHLine(1);
                    checkWinner(1, 0, false);
                }
            }, 100);
            return;
        }
        if (field[2][0] == field[2][1] && field[2][1] == field[2][2] && field[2][0] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawHLine(2);
                    checkWinner(2, 0, false);
                }
            }, 100);
            return;
        }
        if (field[0][0] == field[1][0] && field[1][0] == field[2][0] && field[0][0] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawVLine(0);
                    checkWinner(0, 0, false);
                }
            }, 100);
            return;
        }
        if (field[0][1] == field[1][1] && field[1][1] == field[2][1] && field[0][1] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawVLine(1);
                    checkWinner(0, 1, false);
                }
            }, 100);
            return;
        }
        if (field[0][2] == field[1][2] && field[1][2] == field[2][2] && field[0][2] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawVLine(2);
                    checkWinner(0, 2, false);
                }
            }, 100);
            return;
        }
        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[0][0] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawDLine(false);
                    checkWinner(0, 0, false);
                }
            }, 100);
            return;
        }
        if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[0][2] != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.drawDLine(true);
                    checkWinner(0, 2, false);
                }
            }, 100);
            return;
        }
        if (turnCount == 9 && !done) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkWinner(0, 0, true);
                }
            }, 100);
        }
    }

    @SuppressWarnings("WrongConstant")
    private void checkWinner(int row, int col, boolean draw) {
        done = true;
        if (draw) {
            mBinding.result.setTextColor(Color.GRAY);
            mBinding.result.setText(R.string.Draw);
        } else if (field[row][col] == playersChar) {
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
        if (!wifiGame || isOrganizer) {
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
        mAi = new AI(isTaken, playersChar, opponentChar, field);
        if (playerFirst % 2 != 0) {
            playersTurn = false;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    aiTurn();
                }
            }, 500);
        } else {
            playersTurn = true;
        }
    }
}
