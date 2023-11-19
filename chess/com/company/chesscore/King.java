package com.company.chesscore;

import java.util.ArrayList;

public class King extends Piece {

    public King(int color) {
        setColor(color);
    }

    // need to create a get all valid moves method for each piece
    @Override
    public boolean isValidMove(int myPosition, int nextPostion) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int rowDifference = (int) Math.abs(myRow - nextRow);
        int colDifference = (int) Math.abs(myCol - nextCol);
        if (rowDifference <= 1 && colDifference <= 1) {
            return true;// 7araka sa7
        }
        return false;
    }

    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPostion);
        if (nextBoardSquare.getPiece().getColor().equals(this.getColor()))//if same color piece
            return false;
        else
            return true;

    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition)
    {
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition/8;
        int c = myPosition%8;
        int rowChange,colChange,newR,newC;
        //still didnt include castling
        for(int i =0;i<9;i++)//for 9 moves possible
        {
            try{
                if(i!=4)//4 is no move
                {
                    newR = i/3;
                    newC = i%3;
                    rowChange = newR-1;
                    colChange = newC-1;
                    if(Board.isIllegal(r+rowChange, c+colChange))
                        throw new IllegalArgumentException();
                    int nextPos = Board.getIntPosition(r+rowChange, c+colChange);
                    int nextPosColor = Board.getBoardSquare(nextPos).getPiece().getColorNum();
                    if(nextPosColor != this.getColorNum())//if not same color
                    {
                        //valid move
                        if(Board.isMyKingSafe(nextPos, this.getColorNum()))
                        {
                            validMoves.add(Board.createMoveString(r,c, r+rowChange, c+colChange));
                            System.out.println("king safe");
                        }
                        else
                        {
                            System.out.println("king is NOT safe");
                        }
                    }
                }
            }
            catch(IllegalArgumentException e)
            {
                //System.out.println("illegal position");
            }
        }


        return validMoves;
    }
    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "a";
        return "A";
    }
}
