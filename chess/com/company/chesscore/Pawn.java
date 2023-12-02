package com.company.chesscore;

import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean enPassantEligible;

    public Pawn(int color, Board board) {
        setColor(color);
        enPassantEligible = false;
        this.board = board;
    }

    public boolean isEnPassantEligible() {
        return enPassantEligible;
    }

    public void setEnPassantEligible(boolean enPassantEligible) {
        this.enPassantEligible = enPassantEligible;
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
                // can go down 2 or 1 or diags
                // nn check isillegal cuz first move
                if (((rowDifference == -2 && colDifference == 0))
                        || ((rowDifference == -1 && colDifference == 0))) {
                    // firstMove = false;
                    return true;
                }
            }
            // not first move
            if ((rowDifference == -1 && colDifference == 0)) {
                return true;
            } else if ((rowDifference == -1 && colDifference == 1)) {
                return true;
            } else if ((rowDifference == -1 && colDifference == -1)) {// SAME GOSE HERE
                return true;
            } else {
                return false;
            }
        } else {

            if (firstMove) {

                if (((rowDifference == 2 && colDifference == 0))
                        || ((rowDifference == 1 && colDifference == 0))) {
                    // firstMove = false;
                    return true;
                }
            }
            if ((rowDifference == 1 && colDifference == 0)) {
                return true;
            } else if ((rowDifference == 1 && colDifference == 1)) {
                return true;
            } else if ((rowDifference == 1 && colDifference == -1)) {
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
        BoardSquare nextBoardSquare = board.getBoardSquare(nextPostion);
        if (colDifference == 0) {
            // fwd
            if (nextBoardSquare.getPiece().getColor().equals("Empty Square"))// it can move only when its empty
                if (rowDifference > 1) {// going down.. a black pawn fwd
                    if (board.getBoardSquare(nextPostion + 8).getPiece().getColor().equals("Empty Square"))
                        return true;// black pawn move fwd 2
                } else if (rowDifference < -1) {
                    if (board.getBoardSquare(nextPostion - 8).getPiece().getColor().equals("Empty Square"))
                        return true;// pawn move fwd 2 as white
                } else
                    return true;// pawn move fwd 1
            else
                return false;

        } else if (colDifference == 1 || colDifference == -1) {// diags to eat
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

    public boolean checkDiagsCapture(int nextPosition) {

        return false;
    }

    // check king is safe and change first move
    public boolean captureEnPassant(int myPosition, int nextPostion) {

        int myRow = myPosition / 8;
        int myCol = myPosition % 8;
        int nextRow = nextPostion / 8;
        int nextCol = nextPostion % 8;
        int colDifference = nextCol - myCol;
        if (isValidMove(myPosition, nextPostion)) {
            // System.out.println("kkk");
            if (getColor().equals("White")) {

                if (colDifference == -1) {
                    // System.out.println("kkk");
                    if (myRow == 3 && board.getBoardSquare(nextPostion).getPiece() instanceof EmptySquare
                            && board.getBoardSquare(myPosition - 1).getPiece() instanceof Pawn) {
                        Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition - 1).getPiece();
                        if (opponentPawn.isEnPassantEligible()) {
                            board.movePiece(myPosition, nextPostion);
                            board.capture(opponentPawn);
                            board.getBoardSquare(myPosition - 1).setPiece(new EmptySquare());
                            return true;
                        }
                    }
                } else if (colDifference == 1) {
                    if (myRow == 3 && board.getBoardSquare(nextPostion).getPiece() instanceof EmptySquare
                            && board.getBoardSquare(myPosition + 1).getPiece() instanceof Pawn) {
                        Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition + 1).getPiece();
                        if (opponentPawn.isEnPassantEligible()) {
                            board.movePiece(myPosition, nextPostion);
                            board.capture(opponentPawn);
                            board.getBoardSquare(myPosition + 1).setPiece(new EmptySquare());
                            return true;
                        }
                    }
                }
            } else {
                if (colDifference == -1) {
                    if (myRow == 4 && board.getBoardSquare(nextPostion).getPiece() instanceof EmptySquare
                            && board.getBoardSquare(myPosition - 1).getPiece() instanceof Pawn) {
                        Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition - 1).getPiece();
                        if (opponentPawn.isEnPassantEligible()) {
                            board.movePiece(myPosition, nextPostion);
                            board.capture(opponentPawn);
                            board.getBoardSquare(myPosition - 1).setPiece(new EmptySquare());
                            return true;
                        }
                    }
                } else if (colDifference == 1) {
                    if (myRow == 4 && board.getBoardSquare(nextPostion).getPiece() instanceof EmptySquare
                            && board.getBoardSquare(myPosition + 1).getPiece() instanceof Pawn) {
                        Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition + 1).getPiece();
                        if (opponentPawn.isEnPassantEligible()) {
                            board.movePiece(myPosition, nextPostion);
                            board.capture(opponentPawn);
                            board.getBoardSquare(myPosition + 1).setPiece(new EmptySquare());
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean isEnPassantCandidate(int myPosition, int nextPosition) {
        int myRow = myPosition / 8;
        int nextRow = nextPosition / 8;
        int myCol = myPosition % 8;
        int nextCol = nextPosition % 8;

        int rowDifference = Math.abs(myRow - nextRow);
        int colDifference = Math.abs(myCol - nextCol);
        // if (!firstMove)
        // return false;
        // System.out.println(myPosition + " " + nextPosition);
        if (rowDifference == 2 && colDifference == 0) {
            // Move is a two-step forward move

            if (getColor().equals("Black") && myRow == 1) {

                // Black pawn is on the fifth rank
                if (nextCol > 0 && board.getBoardSquare(nextPosition - 1).getPiece() instanceof Pawn &&
                        board.getBoardSquare(nextPosition - 1).getPiece().getColor().equals("White")) {

                    setEnPassantEligible(true);
                    return true;

                }
                if (nextCol < 7 && board.getBoardSquare(nextPosition + 1).getPiece() instanceof Pawn &&
                        board.getBoardSquare(nextPosition + 1).getPiece().getColor().equals("White")) {

                    setEnPassantEligible(true);
                    return true;

                }
            } else if (getColor().equals("White") && myRow == 6) {
                // White pawn is on the fifth rank
                if (nextCol > 0 && board.getBoardSquare(nextPosition - 1).getPiece() instanceof Pawn &&
                        board.getBoardSquare(nextPosition - 1).getPiece().getColor().equals("Black")) {

                    setEnPassantEligible(true);
                    return true;

                }
                if (nextCol < 7 && board.getBoardSquare(nextPosition + 1).getPiece() instanceof Pawn &&
                        board.getBoardSquare(nextPosition + 1).getPiece().getColor().equals("Black")) {

                    setEnPassantEligible(true);
                    return true;

                }
            }
        }
        return false;
    }

    // fall in last col and first col
    @Override
    public ArrayList<String> getAllValidMovesFromPiece() {
        ArrayList<String> validMoves = new ArrayList<String>();
        int r = myPosition / 8;
        int c = myPosition % 8;
        if (firstMove) {
            // nn check illegal
            if (getColorNum() == 0) {// blck can go down 1 or 2 or diags eat
                if (board.getBoardSquare(myPosition + 8).getPiece().getColor().equals("Empty Square")) {
                    if (board.getBoardSquare(myPosition + 16).getPiece().getColor().equals("Empty Square")
                            && isKingSafeFromMyMove(myPosition, myPosition + 16))
                        validMoves.add(board.createMoveString(r, c, r + 2, c));// 2 fwd
                    if (isKingSafeFromMyMove(myPosition, myPosition + 8))
                        validMoves.add(board.createMoveString(r, c, r + 1, c));// 1 fwd
                }
                if (board.getBoardSquare(myPosition + 7).getPiece().getColor().equals("White") && (c > 0)
                        && isKingSafeFromMyMove(myPosition, myPosition + 7))// checkin left diag except 0
                    validMoves.add(board.createMoveString(r, c, r + 1, c - 1));
                if (board.getBoardSquare(myPosition + 9).getPiece().getColor().equals("White") && (c < 7)
                        && isKingSafeFromMyMove(myPosition, myPosition + 9))// checkig right diag excet 7
                    validMoves.add(board.createMoveString(r, c, r + 1, c + 1));
            } else {
                // wjite first move go up
                if (board.getBoardSquare(myPosition - 8).getPiece().getColor().equals("Empty Square")) {
                    {

                        if (board.getBoardSquare(myPosition - 16).getPiece().getColor().equals("Empty Square")
                                && isKingSafeFromMyMove(myPosition, myPosition - 16))
                            validMoves.add(board.createMoveString(r, c, r - 2, c));
                    }
                    if (isKingSafeFromMyMove(myPosition, myPosition - 8))
                        validMoves.add(board.createMoveString(r, c, r - 1, c));
                    if (board.getBoardSquare(myPosition - 7).getPiece().getColor().equals("Black") && (c < 7)
                            && isKingSafeFromMyMove(myPosition, myPosition - 7))
                        validMoves.add(board.createMoveString(r, c, r - 1, c + 1));
                    if (board.getBoardSquare(myPosition - 9).getPiece().getColor().equals("Black") && (c > 0)
                            && isKingSafeFromMyMove(myPosition, myPosition - 9))
                        validMoves.add(board.createMoveString(r, c, r - 1, c - 1));
                }

            }
        } else {// not first move
            if (getColorNum() == 0 && (r < 7)) {// black and not in end// if in end pawn promo
                // can have pawn promo here-- eads to it
                if (board.getBoardSquare(myPosition + 8).getPiece().getColor().equals("Empty Square")
                        && isKingSafeFromMyMove(myPosition, myPosition + 8)) {
                    validMoves.add(board.createMoveString(r, c, r + 1, c));
                }
                if (board.getBoardSquare(myPosition + 7).getPiece().getColor().equals("White") && (c > 0)
                        && isKingSafeFromMyMove(myPosition, myPosition + 7))
                    validMoves.add(board.createMoveString(r, c, r + 1, c - 1));
                if (board.getBoardSquare(myPosition + 9).getPiece().getColor().equals("White") && (c < 7)
                        && isKingSafeFromMyMove(myPosition, myPosition + 9))
                    validMoves.add(board.createMoveString(r, c, r + 1, c + 1));
            } else {// will neer have apawn in black and at bottom
                if (r > 0) {
                    if (board.getBoardSquare(myPosition - 8).getPiece().getColor().equals("Empty Square")
                            && isKingSafeFromMyMove(myPosition, myPosition - 8))
                        validMoves.add(board.createMoveString(r, c, r - 1, c));
                    if (board.getBoardSquare(myPosition - 7).getPiece().getColor().equals("Black") && (c < 7)
                            && isKingSafeFromMyMove(myPosition, myPosition - 7))
                        validMoves.add(board.createMoveString(r, c, r - 1, c + 1));
                    if (board.getBoardSquare(myPosition - 9).getPiece().getColor().equals("Black") && (c > 0)
                            && isKingSafeFromMyMove(myPosition, myPosition - 9))
                        validMoves.add(board.createMoveString(r, c, r - 1, c - 1));

                }
            }
        }
        // int nextCol = nextPostion % 8;
        // int colDifference = nextCol - myCol;

        // System.out.println("kkk");
        if (getColor().equals("White")) {

            // System.out.println("kkk");
            if (r == 3 && board.getBoardSquare(myPosition - 9).getPiece() instanceof EmptySquare
                    && board.getBoardSquare(myPosition - 1).getPiece() instanceof Pawn
                    && isKingSafeFromMyMove(myPosition, myPosition - 9)) {
                Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition - 1).getPiece();
                if (opponentPawn.isEnPassantEligible()) {
                    validMoves.add(board.createMoveString(r, c, r - 1, c - 1));
                }
            }

            if (r == 3 && board.getBoardSquare(myPosition - 7).getPiece() instanceof EmptySquare
                    && board.getBoardSquare(myPosition + 1).getPiece() instanceof Pawn
                    && isKingSafeFromMyMove(myPosition, myPosition - 7)) {
                Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition + 1).getPiece();
                if (opponentPawn.isEnPassantEligible()) {
                    validMoves.add(board.createMoveString(r, c, r - 1, c + 1));
                }
            }

        } else {

            if (r == 4 && board.getBoardSquare(myPosition + 7).getPiece() instanceof EmptySquare
                    && board.getBoardSquare(myPosition - 1).getPiece() instanceof Pawn
                    && isKingSafeFromMyMove(myPosition, myPosition + 7)) {
                Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition - 1).getPiece();
                if (opponentPawn.isEnPassantEligible()) {
                    validMoves.add(board.createMoveString(r, c, r + 1, c - 1));
                }

            }
            if (r == 4 && board.getBoardSquare(myPosition + 9).getPiece() instanceof EmptySquare
                    && board.getBoardSquare(myPosition + 1).getPiece() instanceof Pawn
                    && isKingSafeFromMyMove(myPosition, myPosition + 9)) {
                Pawn opponentPawn = (Pawn) board.getBoardSquare(myPosition + 1).getPiece();
                if (opponentPawn.isEnPassantEligible()) {
                    validMoves.add(board.createMoveString(r, c, r + 1, c + 1));
                }

            }

        }
        return validMoves;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
    public String pieceName() {
        return translateColor(getColorNum())+"Pawn";
    }
}
