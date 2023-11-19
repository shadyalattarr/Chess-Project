package com.company.chesscore;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(int color) {
        setColor(color);
    }

    // need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition, int nextPostion) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int rowDifference = (int) Math.abs(myRow - nextRow);
        int colDifference = (int) Math.abs(myCol - nextCol);
        if (rowDifference == colDifference)
            return true;
        return false;
    }

    @Override
    boolean checkPath(int myPosition, int nextPosition) {
        // i want to check also if the move is valid
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPosition / 8;
        int nextCol = nextPosition % 8;
        int rowChange = (nextRow > myRow) ? 1 : -1;//row change is up -1 down 1
        int colChange = (nextCol > myCol) ? 1 : -1;//col change is right 1 left -1

        //current square checking
        int currentRow = myRow + rowChange;
        int currentCol = myCol + colChange;
        // checking if there is a piece in the path
        while (currentRow != nextRow && currentCol != nextCol) {//till we reach our square
            BoardSquare nextBoardSquare = Board.getBoardSquare(currentRow * 8 + currentCol);
            
            //System.out.println(currentRow * 8 + currentCol +" "+ nextBoardSquare.getPiece());
            
            if (nextBoardSquare.getPiece().getColorNum() != -1) {//if we meet a piece in path its over
                return false;
            }
            currentRow += rowChange;
            currentCol += colChange;
        }
        //System.out.println("done with path");
        // checking if the sqaure destination is empty
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPosition);
        if (nextBoardSquare.getPiece().getColor().equals(getColor()))
            return false;//f in destiantion is of my own i cant go there
        else
            return true;//if its empty or enemty aight
    }

    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "b";
        return "B";
    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllValidMovesFromPiece'");
    }
}
