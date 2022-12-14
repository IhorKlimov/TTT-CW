package com.igorklimov.tictactoe.model;

public class Field {
    private final boolean[][] isTaken = new boolean[3][3];
    @Game.Side
    private final int[][] field = new int[3][3];

    public int[][] getField() {
        return field;
    }

    public boolean isTaken(int row, int col) {
        return isTaken[row][col];
    }

    public void setTaken(int row, int col, boolean isTaken) {
        this.isTaken[row][col] = isTaken;
    }
}
