package com.company.chesscore;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("----Chess Board----");
        Board board = new Board();// makes no sense how we dealing wih it
        // kolo 2awel 7arf but king Aa and WHITE black
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(8*i+j +":"+board.squares[i][j].getPiece() + "\t");
            }
            System.out.println();
        }


        ArrayList<String> validMoves = new ArrayList<String>();
        //testing white king getall validmoves
       /*  Piece whiteKing = board.squares[7][3].getPiece();
        System.out.println("from 63");
        validMoves = whiteKing.getAllValidMovesFromPiece(31);
        printArrayList(validMoves);
*/

        //tsting white knightgetall
        /*Piece whiteKnight = Board.getBoardSquare(62).getPiece();
        validMoves = whiteKnight.getAllValidMovesFromPiece(1);
        printArrayList(validMoves); */

        //testing black queen getall
        /*Piece blackQueen = Board.getBoardSquare(3).getPiece();
        validMoves = blackQueen.getAllValidMovesFromPiece(32);
        printArrayList(validMoves);*/
        //testing white bishop getall
        /*Piece whiteBishop = Board.getBoardSquare(58).getPiece();
        validMoves = whiteBishop.getAllValidMovesFromPiece(39);
        printArrayList(validMoves);*/
        //tesign black rook getall
        /*Piece blackRook = Board.getBoardSquare(7).getPiece();
        validMoves = blackRook.getAllValidMovesFromPiece(27);
        printArrayList(validMoves);*/
        //testing king 59 white movements
        /* 
        Piece whiteKing = board.squares[7][3].getPiece();
        System.out.println(whiteKing.isValidMove(27, 18));
        System.out.println(whiteKing.isValidMove(27, 19));
        System.out.println(whiteKing.isValidMove(27, 20));
        System.out.println(whiteKing.isValidMove(27, 26));
        System.out.println(whiteKing.isValidMove(27, 28));
        System.out.println(whiteKing.isValidMove(27, 34));
        System.out.println(whiteKing.isValidMove(27, 35));
        System.out.println(whiteKing.isValidMove(27, 36));
        System.out.println();
        System.out.println(whiteKing.isValidMove(27, 29));
        System.out.println(whiteKing.isValidMove(27, 25));
        System.out.println(whiteKing.isValidMove(27, 24));
        System.out.println(whiteKing.isValidMove(27, 37));
        
        System.out.println();

        System.out.println(whiteKing.checkPath(59,60));
        System.out.println(whiteKing.checkPath(59,58));
        System.out.println(whiteKing.checkPath(59,50));
        System.out.println(whiteKing.checkPath(59,51));
        System.out.println(whiteKing.checkPath(59,52));
        */
        //testing black knight 1 movements
        /* 
        Piece blackKnight = board.squares[0][1].getPiece();
        System.out.println(blackKnight.isValidMove(27, 10));
        System.out.println(blackKnight.isValidMove(27, 12));
        System.out.println(blackKnight.isValidMove(27, 21));
        System.out.println(blackKnight.isValidMove(27, 37));
        System.out.println(blackKnight.isValidMove(27, 17));
        System.out.println(blackKnight.isValidMove(27, 33));
        System.out.println(blackKnight.isValidMove(27, 44));
        System.out.println(blackKnight.isValidMove(27, 42));

        System.out.println();

        System.out.println(blackKnight.isValidMove(27, 29));
        System.out.println(blackKnight.isValidMove(27, 25));
        System.out.println(blackKnight.isValidMove(27, 24));
        System.out.println(blackKnight.isValidMove(27, 38));
        
        System.out.println("check path");

        System.out.println(blackKnight.checkPath(1,16));
        System.out.println(blackKnight.checkPath(1,18));
        System.out.println(blackKnight.checkPath(1,11));
        */
        //testing white bishop
        /* 
        Piece whiteBishop = board.getBoardSquare(58).getPiece(); 
        System.out.println(whiteBishop.isValidMove(27, 20));
        System.out.println(whiteBishop.isValidMove(27, 13));
        System.out.println(whiteBishop.isValidMove(27, 6));
        System.out.println(whiteBishop.isValidMove(27, 18));
        System.out.println(whiteBishop.isValidMove(27, 9));
        System.out.println(whiteBishop.isValidMove(27, 0));
        System.out.println(whiteBishop.isValidMove(27, 36));
        System.out.println(whiteBishop.isValidMove(27, 45));
        System.out.println(whiteBishop.isValidMove(27, 54));
        System.out.println(whiteBishop.isValidMove(27, 63));
        System.out.println(whiteBishop.isValidMove(27, 34));
        System.out.println(whiteBishop.isValidMove(27, 41));
        System.out.println(whiteBishop.isValidMove(27, 48));

        System.out.println();
        System.out.println(whiteBishop.isValidMove(27, 28));
        System.out.println(whiteBishop.isValidMove(27, 29));
        System.out.println(whiteBishop.isValidMove(27, 35));
        System.out.println(whiteBishop.isValidMove(27, 12));
        System.out.println(whiteBishop.isValidMove(27, 42));
        System.out.println("Check path assume bishop @ 27");
        board.squares[3][3].setPiece(whiteBishop);
        System.out.println(whiteBishop.checkPath(27, 20));
        System.out.println(whiteBishop.checkPath(27, 13));
        System.out.println(whiteBishop.checkPath(27, 6));
        System.out.println(whiteBishop.checkPath(27, 18));
        System.out.println(whiteBishop.checkPath(27, 9));
        System.out.println(whiteBishop.checkPath(27, 0));
        System.out.println(whiteBishop.checkPath(27, 36));
        System.out.println(whiteBishop.checkPath(27, 45));
        System.out.println(whiteBishop.checkPath(27, 54));
        System.out.println(whiteBishop.checkPath(27, 63));
        System.out.println(whiteBishop.checkPath(27, 34));
        System.out.println(whiteBishop.checkPath(27, 41));
        System.out.println(whiteBishop.checkPath(27, 48));
        */
        //testing black rook
        /*Piece blackRook = board.getBoardSquare(0).getPiece();
        System.out.println(blackRook.isValidMove(27, 28));
        System.out.println(blackRook.isValidMove(27, 29));
        System.out.println(blackRook.isValidMove(27, 30));
        System.out.println(blackRook.isValidMove(27, 31));
        System.out.println(blackRook.isValidMove(27, 24));
        System.out.println(blackRook.isValidMove(27, 25));
        System.out.println(blackRook.isValidMove(27, 26));
        System.out.println(blackRook.isValidMove(27, 19));
        System.out.println(blackRook.isValidMove(27, 11));
        System.out.println(blackRook.isValidMove(27, 3));
        System.out.println(blackRook.isValidMove(27, 35));
        System.out.println(blackRook.isValidMove(27, 43));
        System.out.println(blackRook.isValidMove(27, 51));
        System.out.println(blackRook.isValidMove(27, 59));

        System.out.println();
        System.out.println(blackRook.isValidMove(27, 0));
        System.out.println(blackRook.isValidMove(27, 21));
        System.out.println(blackRook.isValidMove(27, 20));
        System.out.println(blackRook.isValidMove(27, 34));
        System.out.println(blackRook.isValidMove(27, 44));

        System.out.println(blackRook.checkPath(0, 1));
        System.out.println(blackRook.checkPath(0, 8));
        System.out.println(blackRook.checkPath(0, 16));
        System.out.println(blackRook.checkPath(0, 32));
        System.out.println(blackRook.checkPath(0, 48));
        System.out.println("assume black rook at 27");
        board.squares[3][3].setPiece(blackRook);
        System.out.println(blackRook.checkPath(27, 28));
        System.out.println(blackRook.checkPath(27, 29));
        System.out.println(blackRook.checkPath(27, 30));
        System.out.println(blackRook.checkPath(27, 31));
        System.out.println(blackRook.checkPath(27, 24));
        System.out.println(blackRook.checkPath(27, 25));
        System.out.println(blackRook.checkPath(27, 26));
        System.out.println(blackRook.checkPath(27, 19));
        System.out.println(blackRook.checkPath(27, 11));
        System.out.println(blackRook.checkPath(27, 3));
        System.out.println(blackRook.checkPath(27, 35));
        System.out.println(blackRook.checkPath(27, 43));
        System.out.println(blackRook.checkPath(27, 51));
        System.out.println(blackRook.checkPath(27, 59));
        */




    }


    static void printArrayList(ArrayList<String> validMoves)
    {
        if(validMoves.size() == 0)
            System.out.println("array list is empty ");
        System.out.println(validMoves.size());
        for (String move : validMoves) {
            System.out.println(move);
        }
    }
}