package com.company.chesscore;
public class BoardSquare {
    private Piece piece;
    private int position;

    public BoardSquare(Piece piece,int position)
    {
        setPiece(piece);
        setSquarePosition(position);
    }
    
    public void setSquarePosition(int pos)
    {
        this.position = pos;
    }

    public int getSquarePostion()
    {
        return this.position;//from this can calculate row and column
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
}

