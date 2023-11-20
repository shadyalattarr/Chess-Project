package com.company.chesscore;

import java.util.ArrayList;

public class ChessGame {
    Board board;
    int player;

    ChessGame() {
        this.board = new Board();
        this.player = 1;// white starts
    }

    public boolean isValidMove() {
        return false;

    }

    // before i call this method isValied and checkPath should be called
    public static boolean promotePawn(int myPosition, int nextPosition, Piece newPiece) {
        BoardSquare promotionSquare = Board.getBoardSquare(nextPosition);
        Piece oldPiece = Board.getBoardSquare(myPosition).getPiece();

        if (oldPiece instanceof Pawn) {
            int row = nextPosition / 8;

            // Check if the pawn reached the end of the board
            if (oldPiece.getColor().equals("White") && row == 0) {
                promotionSquare.setPiece(newPiece);
                return true;
            } else if (oldPiece.getColor().equals("Black") && row == 7) {
                promotionSquare.setPiece(newPiece);
                return true;
            }
        }
        return false;
    }

    // just needs to check isKingSafe
    // first move dose not work
    public static boolean isValiedCastle(int myPosition, int nextPosition) {

        BoardSquare kingSquare = Board.getBoardSquare(myPosition);
        BoardSquare rookSquare = Board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        System.out.println(king instanceof King);
        if (king instanceof King && rook instanceof Rook) {
            // System.out.println("king and rook");
            if (king.getColor().equals(rook.getColor())) {
                // System.out.println("same color");
                if (king.getColor().equals("White")) {
                    // System.out.println("white");
                    if (myPosition == 60 && nextPosition == 63) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(61).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(62).getPiece().getColor().equals("Empty Square")) {
                                return true;
                            }
                        }
                    } else if (myPosition == 60 && nextPosition == 56) {
                        // System.out.println("right place");
                        // System.out.println(kingSquare.getPiece().firstMove);
                        // System.out.println(rookSquare.getPiece().firstMove);
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(59).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(58).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(57).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                } else {
                    if (myPosition == 4 && nextPosition == 7) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(5).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(6).getPiece().getColor().equals("Empty Square")) {
                                return true;
                            }
                        }
                    } else if (myPosition == 4 && nextPosition == 0) {
                        if (kingSquare.getPiece().firstMove && rookSquare.getPiece().firstMove) {
                            if (Board.getBoardSquare(3).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(2).getPiece().getColor().equals("Empty Square")
                                    && Board.getBoardSquare(1).getPiece().getColor().equals("Empty Square"))
                                return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    public static boolean castle(int myPosition, int nextPosition) {
        if (!isValiedCastle(myPosition, nextPosition))
            return false;
        BoardSquare kingSquare = Board.getBoardSquare(myPosition);
        BoardSquare rookSquare = Board.getBoardSquare(nextPosition);
        Piece king = kingSquare.getPiece();
        Piece rook = rookSquare.getPiece();
        if (myPosition == 60 && nextPosition == 63 && Board.isMyKingSafe(62, king.getColorNum())) {
            Board.getBoardSquare(61).setPiece(rook);
            Board.getBoardSquare(62).setPiece(king);
            Board.getBoardSquare(60).setPiece(new EmptySquare());
            Board.getBoardSquare(63).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 60 && nextPosition == 56 && Board.isMyKingSafe(58, king.getColorNum())) {
            Board.getBoardSquare(59).setPiece(rook);
            Board.getBoardSquare(58).setPiece(king);
            Board.getBoardSquare(60).setPiece(new EmptySquare());
            Board.getBoardSquare(56).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 4 && nextPosition == 7 && Board.isMyKingSafe(6, king.getColorNum())) {
            Board.getBoardSquare(5).setPiece(rook);
            Board.getBoardSquare(6).setPiece(king);
            Board.getBoardSquare(4).setPiece(new EmptySquare());
            Board.getBoardSquare(7).setPiece(new EmptySquare());
            return true;
        } else if (myPosition == 4 && nextPosition == 0 && Board.isMyKingSafe(2, king.getColorNum())) {
            Board.getBoardSquare(3).setPiece(rook);
            Board.getBoardSquare(2).setPiece(king);
            Board.getBoardSquare(4).setPiece(new EmptySquare());
            Board.getBoardSquare(0).setPiece(new EmptySquare());
            return true;
        } else
            return false;

    }

    public static void main(String[] args) {
        System.out.println("----Chess Board----");
        Board testBoard = new Board();
        ArrayList<String> validMoves = new ArrayList<String>();
        // test castle
        // Piece whiteKing = testBoard.squares[7][4].getPiece();
        // System.out.println(whiteKing.firstMove + "he");
        // Piece whiteRook = testBoard.squares[7][0].getPiece();
        // promotion test
        // Board.getBoardSquare(8).setPiece(testBoard.getBoardSquare(50).getPiece());
        // Board.getBoardSquare(0).setPiece(new EmptySquare());

        // System.out.println(promotePawn(8, 0, new Queen(1)));
        System.out.println(testBoard.getBoardSquare(8).getPiece().firstMove);

        // testBoard.getBoardSquare(57).setPiece(new EmptySquare());
        // testBoard.getBoardSquare(58).setPiece(new EmptySquare());
        // testBoard.getBoardSquare(59).setPiece(new EmptySquare());
        testBoard.getBoardSquare(57).setPiece(new EmptySquare());
        testBoard.getBoardSquare(58).setPiece(new EmptySquare());
        testBoard.getBoardSquare(59).setPiece(new EmptySquare());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8 * i + j + ":" + testBoard.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }
        
        castle(60, 56);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8 * i + j + ":" + testBoard.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }

    }

    static void printArrayList(ArrayList<String> validMoves) {
        if (validMoves.size() == 0)
            System.out.println("array list is empty ");
        System.out.println(validMoves.size());
        for (String move : validMoves) {
            System.out.println(move);
        }
    }
}
