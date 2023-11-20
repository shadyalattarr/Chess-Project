package com.company.chesscore;

import java.util.ArrayList;

public class Pawn extends Piece {
 

    public Pawn(int color) {
        setColor(color);
    }
    

    // need to create a get all valid moves method for each piece
    public boolean isValidMove(int myPosition, int nextPostion) {
        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int rowDifference = myRow - nextRow;
        int colDifference = myCol - nextCol;
       
        if (getColor().equals("Black")) {
            if (firstMove) {
                //can go down 2 or 1 or diags
                //nn check isillegal cuz first move
                if (((rowDifference == -2 && colDifference == 0)
                        && (Board.getBoardSquare(nextPostion - 8).getPiece().getColor().equals("Empty Square")))
                        || ((rowDifference == -1 && colDifference == 0))) {
                    firstMove = false;
                    return true;
                }
            }
            //not first move
            if ((rowDifference == -1 && colDifference == 0)) {
                return true;
            } else if ((rowDifference == -1 && colDifference == 1)
                    && (Board.getBoardSquare(nextPostion).getPiece().getColor().equals("White"))) {
                return true;
            } else if ((rowDifference == -1 && colDifference == -1)
                    && (Board.getBoardSquare(nextPostion).getPiece().getColor().equals("White"))) {// SAME GOSE HERE
                return true;
            } else {
                return false;
            }
        } else {

            if (firstMove) {

                if (((rowDifference == 2 && colDifference == 0)
                        && (Board.getBoardSquare(nextPostion + 8).getPiece().getColor().equals("Empty Square")))
                        || ((rowDifference == 1 && colDifference == 0))) {
                    firstMove = false;
                    return true;
                }
            }
            if ((rowDifference == 1 && colDifference == 0)) {
                return true;
            } else if ((rowDifference == 1 && colDifference == 1)
                    && (Board.getBoardSquare(nextPostion).getPiece().getColor().equals("Black"))) {
                return true;
            } else if ((rowDifference == 1 && colDifference == -1)
                    && (Board.getBoardSquare(nextPostion).getPiece().getColor().equals("Black"))) {
                return true;

            } else {
                return false;
            }

        }

    }

    @Override
    boolean checkPath(int myPosition, int nextPostion) {

        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int colDifference = myCol - nextCol;
        int rowDifference = myRow - nextRow;
        BoardSquare nextBoardSquare = Board.getBoardSquare(nextPostion);
        if (colDifference == 0) {
            //fwd
            if (nextBoardSquare.getPiece().getColor().equals("Empty Square"))// it can move only when its empty
                if (rowDifference > 1) {//going down.. a black pawn fwd
                    if (Board.getBoardSquare(nextPostion + 8).getPiece().getColor().equals("Empty Square"))
                        return true;//black pawn move fwd 2
                } else if (rowDifference < -1) {
                    if (Board.getBoardSquare(nextPostion - 8).getPiece().getColor().equals("Empty Square"))
                        return true;//pawn move fwd 2 as white
                } else
                    return true;//pawn move fwd 1
            else
                return false;

        } else if(colDifference == 1 || colDifference == -1){//diags to eat
            if (nextBoardSquare.getPiece().getColor().equals(this.getColor())
                    || nextBoardSquare.getPiece().getColor().equals("Empty Square"))// if same color piece or empty
                                                                                    // square
                return false;
            else
                return true;
        }
        return false;

    }

    @Override
    public String toString() {
        if (getColor().equals("Black"))
            return "p";
        return "P";
    }

    // fall in last col and first col
    @Override
    public ArrayList<String> getAllValidMovesFromPiece(int myPosition) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition / 8;
        int c = myPosition % 8;
        // needs to adjust if its last col can only move up and down not out of board
        // int rowChange, colChange, newR, newC;
        // boolean blackOutOfBoard = Board.isIllegal(r + 1, c) || Board.isIllegal(r + 1, c + 1)
        //         || Board.isIllegal(r + 1, c - 1);
        // boolean whiteOutOfBoard = Board.isIllegal(r - 1, c) || Board.isIllegal(r - 1, c + 1)
        //         || Board.isIllegal(r - 1, c - 1);

        if (firstMove) {
            //nn check illegal
            if (getColorNum() == 0) {//blck can go down 1 or 2 or diags eat
                if (Board.getBoardSquare(myPosition + 8).getPiece().getColor().equals("Empty Square")) {
                    if (Board.getBoardSquare(myPosition + 16).getPiece().getColor().equals("Empty Square"))
                        validMoves.add(Board.createMoveString(r, c, r + 2, c));//2 fwd
                    validMoves.add(Board.createMoveString(r, c, r + 1, c));//1 fwd
                }
                if (Board.getBoardSquare(myPosition + 7).getPiece().getColor().equals("White") && (c > 0))//checkin left diag except 0
                    validMoves.add(Board.createMoveString(r, c, r + 1, c - 1));
                if (Board.getBoardSquare(myPosition + 9).getPiece().getColor().equals("White") && (c < 7))//checkig right diag excet 7
                    validMoves.add(Board.createMoveString(r, c, r + 1, c + 1));
            } else {
                //wjite first move go up
                if (Board.getBoardSquare(myPosition - 8).getPiece().getColor().equals("Empty Square")) {
                    {

                        if (Board.getBoardSquare(myPosition - 16).getPiece().getColor().equals("Empty Square"))
                            validMoves.add(Board.createMoveString(r, c, r - 2, c));
                    }
                    validMoves.add(Board.createMoveString(r, c, r - 1, c));
                    if (Board.getBoardSquare(myPosition - 7).getPiece().getColor().equals("Black") && (c < 7))
                        validMoves.add(Board.createMoveString(r, c, r - 1, c + 1));
                    if (Board.getBoardSquare(myPosition - 9).getPiece().getColor().equals("Black") && (c > 0))
                        validMoves.add(Board.createMoveString(r, c, r - 1, c - 1));
                }

            }
        } else {//not first move
            if (getColorNum() == 0 && (r < 7)) {//black and not in end// if in end pawn promo
                //can have pawn promo here-- eads to it
                if (Board.getBoardSquare(myPosition + 8).getPiece().getColor().equals("Empty Square")) {
                    validMoves.add(Board.createMoveString(r, c, r + 1, c));
                }
                if (Board.getBoardSquare(myPosition + 7).getPiece().getColor().equals("White") && (c > 0))
                    validMoves.add(Board.createMoveString(r, c, r + 1, c - 1));
                if (Board.getBoardSquare(myPosition + 9).getPiece().getColor().equals("White") && (c < 7))
                    validMoves.add(Board.createMoveString(r, c, r + 1, c + 1));
            } else {//will neer have apawn in black and at bottom
                if (r > 0) {
                    if (Board.getBoardSquare(myPosition - 8).getPiece().getColor().equals("Empty Square"))
                        validMoves.add(Board.createMoveString(r, c, r - 1, c));
                    if (Board.getBoardSquare(myPosition - 7).getPiece().getColor().equals("Black") && (c < 7))
                        validMoves.add(Board.createMoveString(r, c, r - 1, c + 1));
                    if (Board.getBoardSquare(myPosition - 9).getPiece().getColor().equals("Black") && (c > 0))
                        validMoves.add(Board.createMoveString(r, c, r - 1, c - 1));

                }
            }
        }
        return validMoves;
    }


    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

}
