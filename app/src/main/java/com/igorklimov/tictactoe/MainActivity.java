package com.igorklimov.tictactoe;

import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.igorklimov.tictactoe.databinding.ActivityGameBinding;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "TTT";

    public static boolean btGame;
    private static Typeface sMakeOut;
    private static Typeface sRosemary;
    private ActivityGameBinding mBinding;
    private GameController gameController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_game);

        gameController = new GameController(this, mBinding);

        if (sMakeOut == null) {
            sMakeOut = Typeface.createFromAsset(getAssets(), "fonts/MakeOut.ttf");
            sRosemary = Typeface.createFromAsset(getAssets(), "fonts/Rosemary.ttf");
        }
        //
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameController.setGame(
                    extras.getInt(Utils.SIDE),
                    extras.getInt(Utils.OPPONENT_SIDE),
                    extras.getString(Utils.NAME),
                    extras.getString(Utils.OPPONENT_NAME)
            );
        }

        mBinding.result.setTypeface(sRosemary);
        mBinding.you.append(gameController.playersName + ": " + Game.toString(gameController.playersChar));
        mBinding.opponent.append(gameController.opponentsName + ": " + Game.toString(gameController.opponentChar));
        mBinding.score.append(gameController.playersScore + ":" + gameController.opponentsScore);

        if (!btGame && !gameController.isWifiGame) {
            gameController.setUpAi();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, gameController.playersTurn
                        ? getString(R.string.your_turn)
                        : getString(R.string.opponents_turn), LENGTH_SHORT)
                .show();
    }

    public void cellClick(View view) {
        if (gameController.playersTurn && !gameController.isDone) {
            TextView v = (TextView) view;
            String cellId = view.getResources()
                    .getResourceEntryName(view.getId()).replace("cell", "");
            int row = Integer.valueOf(cellId.substring(0, 1));
            int col = Integer.valueOf(cellId.substring(1, 2));
            if (!gameController.getField().isTaken(row, col)) {
                v.setText(Game.toString(gameController.playersChar));
                v.setTextColor(Game.getColor(gameController.playersChar));
                v.setTypeface(sMakeOut);
                gameController.getField().getField()[row][col] = gameController.playersChar;
                gameController.getField().setTaken(row, col, true);
                gameController.playersTurn = false;
                gameController.turnCount++;
                gameController.checkVictory();
                if (!btGame && gameController.turnCount != 9 && !gameController.isDone && !gameController.isWifiGame) {
                    new Handler().postDelayed(() -> gameController.aiTurn(), 500);
                }
            }
        }
    }


    void setText(int row, int col) {
        Log.d(LOG_TAG, "setText: " + row + " " + col);
        TextView view = null;
        switch (row) {
            case 0:
                if (col == 0) {
                    view = mBinding.cell00;
                } else if (col == 1) {
                    view = mBinding.cell01;
                } else if (col == 2) {
                    view = mBinding.cell02;
                }
                break;
            case 1:
                if (col == 0) {
                    view = mBinding.cell10;
                } else if (col == 1) {
                    view = mBinding.cell11;
                } else if (col == 2) {
                    view = mBinding.cell12;
                }
                break;
            case 2:
                if (col == 0) {
                    view = mBinding.cell20;
                } else if (col == 1) {
                    view = mBinding.cell21;
                } else if (col == 2) {
                    view = mBinding.cell22;
                }
                break;
        }

        gameController.getField().setTaken(row, col, true);
        if (!gameController.isDone) {
            view.setText(Game.toString(gameController.opponentChar));
            view.setTextColor(Game.getColor(gameController.opponentChar));
            view.setTypeface(sMakeOut);
            gameController.getField().getField()[row][col] = gameController.opponentChar;
            gameController.playersTurn = true;
            gameController.turnCount++;
            gameController.checkVictory();
        }
    }

    void drawHLine(int row) {
        int left = mBinding.grid.getLeft();
        int right = mBinding.grid.getRight();
        Rect r = new Rect();

        if (row == 0) {
            mBinding.cell00.getGlobalVisibleRect(r);
        } else if (row == 1) {
            mBinding.cell10.getGlobalVisibleRect(r);
        } else if (row == 2) {
            mBinding.cell20.getGlobalVisibleRect(r);
        }
        int y = ((r.bottom - r.top) / 2) + r.top;

        drawLine(left, y, right, y);
    }

    void drawVLine(int col) {
        int top = mBinding.grid.getTop();
        int bottom = mBinding.grid.getBottom();
        Rect r = new Rect();

        if (col == 0) {
            mBinding.cell00.getGlobalVisibleRect(r);
        } else if (col == 1) {
            mBinding.cell01.getGlobalVisibleRect(r);
        } else if (col == 2) {
            mBinding.cell02.getGlobalVisibleRect(r);
        }

        int x = ((r.right - r.left) / 2) + r.left;

        drawLine(x, top, x, bottom);
    }

    private void drawLine(int startX, int startY, int endX, int endY) {
        mBinding.lineView.setRect(new Rect(startX, startY, endX, endY));
        mBinding.lineView.setVisibility(VISIBLE);
        mBinding.lineView.invalidate();
    }

    void drawDLine(boolean rising) {
        int left = mBinding.grid.getLeft();
        int right = mBinding.grid.getRight();
        int start;
        int finish;

        if (rising) {
            start = mBinding.grid.getBottom();
            finish = mBinding.grid.getTop();
        } else {
            start = mBinding.grid.getTop();
            finish = mBinding.grid.getBottom();
        }

        drawLine(left, start, right, finish);
    }

    public void resetGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        gameController.playerFirst++;
        startActivity(intent);
        finish();
    }

}