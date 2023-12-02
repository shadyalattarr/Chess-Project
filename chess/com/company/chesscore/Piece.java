package com.company.chesscore;

import java.util.ArrayList;

abstract public class Piece {
    private int color;//0 black and 1 white //-1 invlaid
    public boolean firstMove= true;
    Board board;
    int myPosition;

    
    public void setPosition(int newPos)
    {
        myPosition = newPos;
    }
    public int getPiecePosition() {
        return myPosition;
    }
    abstract boolean isValidMove(int myPosition,int nextPostion);//a move that puts the king in danger
    //need to create a get all valid moves method for each piece
    //tye is string? array of string more like a list to be dynamic?
    public abstract String pieceName();
    //tostring shuld be deleted ith
    public boolean isKingSafeFromMyMove(int myPosition,int newPosition)
    {
        //temporary move
        Piece capturedPiece;
        boolean returnValue = false;
        capturedPiece = board.movePiece(myPosition, newPosition);
       // System.out.println(capturedPiece.toString());
        //after movement is king safe? // need my king pos and my color
     //   System.out.println(board.kingPosition[this.getColorNum()]);
        if(board.isMyKingSafe(board.kingPosition[this.getColorNum()], this.getColorNum()))
            {
                returnValue = true;
            }
        //return piece
        //my pos mafrood empty
        board.movePiece(newPosition, myPosition);
        //lazem neraga3aha lw empty square ez lw enemy put it back as if nothng happened
        board.getBoardSquare(newPosition).setPiece(capturedPiece);
        return returnValue;
    }
    abstract boolean checkPath(int myPosition,int nextPostion);

    public void setColor(int color)
    {
        if(color == 0 || color == 1)
            this.color = color;
        else
            this.color = -1;//empty square
    }
    public int getColorNum()
    {
        return this.color;
    }
    public String getColor()
    {
        return translateColor(this.color);
    }

    public String translateColor(int color)
    {
        if(color==1)//1
            return "White";
        else if(color == 0)
            return "Black";//else is 0
        return "Empty Square";
    }
    


    abstract public ArrayList<String> getAllValidMovesFromPiece();

    public void setFirstMove(boolean isFirstMove)
    {
        this.firstMove = isFirstMove;
    }
}
