package com.company.chesscore;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(int color) {
        setColor(color);
    }

    public boolean isValidMove(int myPosition, int nextPostion) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int rowDifference = (int) Math.abs(myRow - nextRow);
        int colDifference = (int) Math.abs(myCol - nextCol);
        if ((colDifference == 2 && rowDifference == 1) || (colDifference == 1 && rowDifference == 2))
            return true;
        return false;
    }

    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPostion);
        if (nextBoardSquare.getPiece().getColor().equals(getColor()))
            return false;
        else
            return true;

    }

    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "k";
        return "K";
    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllValidMovesFromPiece'");
    }

}
