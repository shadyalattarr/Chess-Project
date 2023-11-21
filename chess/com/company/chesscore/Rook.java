package com.company.chesscore;

import java.util.ArrayList;

public class Rook extends Piece {
   

    public Rook(int color,Board board) {
        setColor(color);
        this.board = board;
    }

    // need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition, int nextPostion) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int rowDifference = (int) Math.abs(myRow - nextRow);
        int colDifference = (int) Math.abs(myCol - nextCol);
        if ((rowDifference == 0 && colDifference > 0) || (colDifference == 0 && rowDifference > 0)) {
            firstMove = false;
            return true;
        }
        return false;
    }

    @Override
    boolean checkPath(int myPosition, int nextPosition) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPosition / 8;
        int nextCol = nextPosition % 8;
        int rowChange = (nextRow > myRow) ? 1 : (nextRow < myRow) ? -1 : 0;// 1 going down -1 goin up 0 not vertical
        int colChange = (nextCol > myCol) ? 1 : (nextCol < myCol) ? -1 : 0;// 1 going right -1 going left 0 not
                                                                           // horizontal
        // dayman one of them is 0 and other is 1 or -1.... kae sure in game
        int currentRow = myRow + rowChange;
        int currentCol = myCol + colChange;

        while (currentRow != nextRow || currentCol != nextCol) {// lazem or ith
            BoardSquare nextBoardSquare = board.getBoardSquare(currentRow * 8 + currentCol);
            if (nextBoardSquare.getPiece().getColorNum() != -1) {// check if no pieces in paht
                return false;
            }
            currentRow += rowChange;
            currentCol += colChange;
        }

        BoardSquare nextBoardSquare = board.getBoardSquare(nextPosition);
        if (nextBoardSquare.getPiece().getColor().equals(this.getColor())) {
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

    @Override
    public ArrayList<String> getAllValidMovesFromPiece() {
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition / 8;
        int c = myPosition % 8;
        int newR, newC;
        int newPosition;
        int temp = 1;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++)// i and j for "direction"
            {
                if (!((i == 0 && j != 0) || (i != 0 && j == 0)))// ixor//lazem one of them 0-- 01 10 // if none is 0 so
                                                                // diag.
                    continue;// no movement
                try {
                    temp = 1;
                    newR = r + temp * i;
                    newC = c + temp * j;
                    if (board.isIllegal(newR, newC))// reach end of board
                        throw new IllegalArgumentException();
                    newPosition = board.getIntPosition(newR,newC);
                    while (board.getBoardSquare(board.getIntPosition(newR, newC)).getPiece().getColorNum() == -1)// while
                                                                                                                 // empy
                                                                                                                 // sqr
                    {
                        // need to check if king is safe
                        if(isKingSafeFromMyMove(myPosition, newPosition))
                            validMoves.add(board.createMoveString(r, c, newR, newC));
                        temp++;
                        newR = r + temp * i;
                        newC = c + temp * j;
                        if (board.isIllegal(newR, newC))// reach end of board
                            throw new IllegalArgumentException();
                        newPosition = board.getIntPosition(newR,newC);
                    }
                    // reaching this means we hit a piece @newRnewC
                    if (board.getBoardSquare(board.getIntPosition(newR, newC)).getPiece().getColorNum() != this
                            .getColorNum()) {
                        // oppo color, can capture
                        // need to check if king is safe
                        if(isKingSafeFromMyMove(myPosition, newPosition))
                            validMoves.add(board.createMoveString(r, c, newR, newC));
                    }
                    // end of possible path here, change path
                } catch (IllegalArgumentException e) {
                    // reached end of board
                }
            }
        }

        return validMoves;

    }

}
