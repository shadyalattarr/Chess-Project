package com.company.chesscore;

import java.util.ArrayList;

abstract public class Piece {
    private int color;//0 black and 1 white //-1 invlaid
    public boolean firstMove= true;
    
    abstract boolean isValidMove(int myPosition,int nextPostion);//a move that puts the king in danger
    //need to create a get all valid moves method for each piece
    //tye is string? array of string more like a list to be dynamic?

    //tostring shuld be deleted ith

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

    private String translateColor(int color)
    {
        if(color==1)//1
            return "White";
        else if(color == 0)
            return "Black";//else is 0
        return "Empty Square";
    }


    abstract public ArrayList<String> getAllValidMovesFromPiece(int myPosition);

    public void setFirstMove(boolean isFirstMove)
    {
        this.firstMove = isFirstMove;
    }
}
