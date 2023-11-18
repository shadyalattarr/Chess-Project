package com.company.chesscore;

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
        int rowChange = (nextRow > myRow) ? 1 : -1;
        int colChange = (nextCol > myCol) ? 1 : -1;
        EmptySquare emptySquare = new EmptySquare();

        int currentRow = myRow + rowChange;
        int currentCol = myCol + colChange;
        // checking if there is a piece in the path
        while (currentRow != nextRow || currentCol != nextCol) {
            BoardSquare nextBoardSquare = Board.getBoardSquare(currentRow * 8 + currentCol);
            if (nextBoardSquare.getPiece() != emptySquare) {
                return false;
            }
            currentRow += rowChange;
            currentCol += colChange;
        }

        // checking if the sqaure is empty
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPosition);
        if (nextBoardSquare.getPiece().getColor().equals(getColor()))
            return false;

        else
            return true;
    }

    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "b";
        return "B";
    }
}
