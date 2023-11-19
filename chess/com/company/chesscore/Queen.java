package com.company.chesscore;

import java.util.ArrayList;

public class Queen extends Piece {
    
    public Queen(int color)
    {
        setColor(color);
    }

    //need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition,int nextPostion)
    {
        int myRow = myPosition/8;
        int myCol = myPosition%8;
        int nextRow = nextPostion/8;
        int nextCol = nextPostion%8;
        int rowDifference = (int)Math.abs(myRow-nextRow);
        int colDifference = (int)Math.abs(myCol-nextCol);
        if((rowDifference == 0 && colDifference>0) || (colDifference==0 && rowDifference>0)
             || (colDifference == rowDifference))
            return true;
        return false;
    }
    
    @Override
    boolean checkPath(int myPosition, int nextPosition) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPosition / 8;
        int nextCol = nextPosition % 8;
        int rowChange = (nextRow > myRow) ? 1 : (nextRow < myRow) ? -1 : 0;//1 down,-1 up 0 no vertical
        int colChange = (nextCol > myCol) ? 1 : (nextCol < myCol) ? -1 : 0;//1 right -1 left 0 o horizontal

        int currentRow = myRow + rowChange;
        int currentCol = myCol + colChange;
    
        //if not empty then invalied move
        while (currentRow != nextRow || currentCol != nextCol) {
            BoardSquare nextBoardSquare = Board.getBoardSquare(currentRow * 8 + currentCol);
            if (nextBoardSquare.getPiece().getColorNum() != -1) {
                return false; 
            }
        currentRow += rowChange;
        currentCol += colChange;
    }

   
    BoardSquare nextBoardSquare = Board.getBoardSquare(nextPosition);
    if (nextBoardSquare.getPiece().getColorNum()==(this.getColorNum())) {
        return false; 
    }

    return true; 
}


    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "q"; 
        return "Q";  
    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition/8;
        int c = myPosition%8;
        int newR,newC;
        int temp = 1;
        for(int i = -1;i<=1;i++)
        {
            for(int j= -1;j<=1;j++)//i and j for "direction"
            {
                if(i==0 && j==0)
                    continue;//no movement
                try{
                    temp=1;
                    newR = r+temp*i;
                    newC = c+ temp*j;
                    if(Board.isIllegal(newR, newC))//reach end of board
                        throw new IllegalArgumentException(); 
                    while(Board.getBoardSquare(Board.getIntPosition(newR,newC)).getPiece().getColorNum() == -1)//while empy sqr
                    {
                        //need to check if king is safe
                        validMoves.add(Board.createMoveString(r, c, newR, newC));
                        temp++;
                        newR = r+ temp*i;
                        newC =c+ temp*j;
                        if(Board.isIllegal(newR, newC))//reach end of board
                            throw new IllegalArgumentException();
                         
                    }
                    //reaching this means we hit a piece @newRnewC
                    if(Board.getBoardSquare(Board.getIntPosition(newR, newC)).getPiece().getColorNum()
                        != this.getColorNum())
                    {
                        //oppo color, can capture
                        //need to check if king is safe
                        validMoves.add(Board.createMoveString(r, c, newR, newC));
                    }
                        // end of possible path here, change path
                }
                catch(IllegalArgumentException e)
                {
                    //reached end of board
                }
            }
        }

        return validMoves;
    }
    
}   
