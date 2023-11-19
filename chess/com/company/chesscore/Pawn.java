package com.company.chesscore;

import java.util.ArrayList;

public class Pawn extends Piece {
    
    public Pawn(int color)
    {
        setColor(color);
    }

    //need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition,int nextPostion)
    {
        //to be done
        return true;
    }
    
    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        //idk
        return false;
    }

    @Override
    public String toString()
    {
        if(getColor().equals("Black"))
            return "p"; 
        return "P";  
    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllValidMovesFromPiece'");
    }
    
}
