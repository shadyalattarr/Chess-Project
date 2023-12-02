package com.company.chesscore;

import java.util.ArrayList;

public class EmptySquare extends Piece{

    EmptySquare()//color lazem -1 so it is ignored
    {
        super();
        setColor(-1);
        //board not used
    }

    @Override
    public String toString()
    {
        return "e";
    }

    @Override
    boolean isValidMove(int myPosition,int nextPostion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    @Override
    boolean checkPath(int myPosition, int nextPostion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chackPath'");
    }

    @Override
    public ArrayList<String> getAllValidMovesFromPiece() {
        ArrayList<String> valudMoves = new ArrayList<String>();
        return  valudMoves;//lazem keda send empty list
    }
    public String pieceName()
    {
        return "EmptySquare";
    }
}
