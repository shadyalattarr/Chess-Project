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
        if (nextBoardSquare.getPiece().getColor().equals(this.getColor()))
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
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition/8;
        int c = myPosition%8;
        int newR,newC;
        int rowFactor,colFactor;
        for(int rc = 0;rc<=1;rc++)
            {   
                if(rc==0)
                {   
                    rowFactor = 1; colFactor = 2;
                }
                else
                {
                    rowFactor = 2; colFactor =1;
                }
                for(int i =-1;i<=1;i+=2)
                {
                    for(int j=-1;j<=1;j+=2)
                    {  
                        newR = r+i*rowFactor;
                        newC = c+j*colFactor;
                        try{//try catch lazem in loop cuz if out of lop it ends
                            if(Board.isIllegal(newR, newC))
                                throw new IllegalArgumentException();
                            
                            if(this.checkPath(myPosition, Board.getIntPosition(newR,newC)))//need to add iskingsafe
                                validMoves.add(Board.createMoveString(r, c, newR, newC));
                        }
                        catch(IllegalArgumentException e)
                        {
                            //exited board
                        }
                    }
                }
            }        
        return validMoves;
    }

}
