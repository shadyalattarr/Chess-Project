package com.company.chesscore;

public class Rook extends Piece {

    public Rook(int color) {
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
        if ((rowDifference == 0 && colDifference > 0) || (colDifference == 0 && rowDifference > 0))
            return true;
        return false;
    }

    @Override
    boolean checkPath(int myPosition, int nextPosition) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPosition / 8;
        int nextCol = nextPosition % 8;
        int rowChange = (nextRow > myRow) ? 1 : (nextRow < myRow) ? -1 : 0;
        int colChange = (nextCol > myCol) ? 1 : (nextCol < myCol) ? -1 : 0;
        EmptySquare emptySquare = new EmptySquare();
    
    
    
        int currentRow = myRow + rowChange;
        int currentCol = myCol + colChange;
    
        while (currentRow != nextRow || currentCol != nextCol) {
            BoardSquare nextBoardSquare = Board.getBoardSquare(currentRow * 8 + currentCol);
            if (nextBoardSquare.getPiece() != emptySquare) {
                return false; 
            }
            currentRow += rowChange;
            currentCol += colChange;
        }
    
     
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPosition);
        if (nextBoardSquare.getPiece().getColor().equals(getColor())) {
            return false; 
        }
    
        return true; 
    }
    

    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "r";
        return "R";
    }

}
